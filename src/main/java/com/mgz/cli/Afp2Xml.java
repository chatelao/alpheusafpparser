/*
Copyright 2015 Rudolf Fiala

This file is part of Alpheus AFP Parser.

Alpheus AFP Parser is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

Alpheus AFP Parser is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with Alpheus AFP Parser.  If not, see <http://www.gnu.org/licenses/>
*/

package com.mgz.cli;

import com.mgz.afp.base.StructuredField;
import com.mgz.afp.base.handler.HandlerFactory;
import com.mgz.afp.base.handler.StructuredFieldHandler;
import com.mgz.afp.parser.AFPParser;
import com.mgz.afp.parser.AFPParserConfiguration;
import com.mgz.util.MnemonicPerformanceMonitor;
import com.mgz.util.DirectBufferOutputStream;
import com.mgz.xml.XmlHandlerFactory;
import com.mgz.xml.OrderedOutputOrchestrator;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.nio.channels.Channels;
import java.nio.channels.GatheringByteChannel;
import java.nio.channels.WritableByteChannel;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Command line utility to convert AFP files to XML.
 */
public class Afp2Xml {
  /**
   * Main entry point for the CLI.
   *
   * @param args Command line arguments.
   */
  public static void main(String[] args) {
    System.exit(execute(args));
  }

  /**
   * Executes the CLI logic.
   *
   * @param args Command line arguments.
   * @return Exit code.
   */
  public static int execute(String[] args) {
    if (args.length < 1) {
      printUsage(System.out);
      return 0;
    }

    var isDirectoryMode = false;
    var measure = false;
    var ptxDebug = false;
    var parallel = false;
    var aggressiveIo = false;
    var useCharsetOptimizations = false;
    var threadCount = 0;
    String inputPath = null;
    String outputPath = null;
    String xpathExpression = null;

    for (var i = 0; i < args.length; i++) {
      var arg = args[i];
      switch (arg) {
        case "-h", "--help" -> {
          printUsage(System.out);
          return 0;
        }
        case "-d", "--directory" -> {
          isDirectoryMode = true;
          if (i + 1 < args.length && !args[i + 1].startsWith("-")) {
            inputPath = args[++i];
          }
        }
        case "-x", "--xpath" -> {
          if (i + 1 < args.length) {
            xpathExpression = args[++i];
          } else {
            System.err.println("Error: --xpath requires an expression.");
            printUsage(System.err);
            return 1;
          }
        }
        case "-m", "--measure" -> {
          measure = true;
        }
        case "-p", "--ptx-debug" -> {
          ptxDebug = true;
        }
        case "-c", "--charset-opt" -> {
          useCharsetOptimizations = true;
        }
        case "-P", "--parallel" -> {
          parallel = true;
        }
        case "-a", "--aggressive-io" -> {
          aggressiveIo = true;
        }
        case "-t", "--threads" -> {
          if (i + 1 < args.length) {
            try {
              threadCount = Integer.parseInt(args[++i]);
            } catch (NumberFormatException e) {
              System.err.println("Error: --threads requires an integer.");
              return 1;
            }
          } else {
            System.err.println("Error: --threads requires a value.");
            return 1;
          }
        }
        default -> {
          if (arg.startsWith("-") && !"-".equals(arg)) {
            System.err.println("Unknown option: " + arg);
            printUsage(System.err);
            return 1;
          }
          if (inputPath == null) {
            inputPath = arg;
          } else if (outputPath == null) {
            outputPath = arg;
          } else {
            System.err.println("Too many arguments.");
            printUsage(System.err);
            return 1;
          }
        }
      }
    }

    if (inputPath == null) {
      printUsage(System.err);
      return 1;
    }

    if (measure) {
      MnemonicPerformanceMonitor.setEnabled(true);
    }
    if (ptxDebug) {
      com.mgz.util.PTXPerformanceMonitor.setEnabled(true);
    }

    try {
      var input = new File(inputPath);
      if (!input.exists()) {
        System.err.println("Input not found: " + inputPath);
        return 1;
      }

      if (input.isDirectory()) {
        isDirectoryMode = true;
      }

      var isTxt = xpathExpression != null && xpathExpression.endsWith("/text()");
      var extension = isTxt ? ".txt" : ".xml";

      if (isDirectoryMode) {
        if (!input.isDirectory()) {
          System.err.println("Input is not a directory: " + inputPath);
          return 1;
        }

        var files = input.listFiles((dir, name) -> name.toLowerCase().endsWith(".afp"));
        if (files == null || files.length == 0) {
          System.err.println("No .afp files found in directory: " + inputPath);
          return 0;
        }

        // Sort files to ensure deterministic output order in stdout mode
        Arrays.sort(files, Comparator.comparing(File::getName));

        if (threadCount <= 0) {
          threadCount = Runtime.getRuntime().availableProcessors();
        }

        if (!"-".equals(outputPath)) {
          System.out.println("Processing " + files.length + " files using " + threadCount + " threads...");
        } else {
          System.err.println("Processing " + files.length + " files using " + threadCount + " threads...");
        }

        var hasErrors = new AtomicBoolean(false);
        ExecutorService executor = Executors.newFixedThreadPool(threadCount);
        final HandlerFactory handlerFactory = new XmlHandlerFactory(xpathExpression);

        final OrderedOutputOrchestrator orchestrator;
        if ("-".equals(outputPath)) {
          WritableByteChannel stdoutChannel = Channels.newChannel(System.out);
          if (stdoutChannel instanceof GatheringByteChannel gbc) {
            orchestrator = new OrderedOutputOrchestrator(System.out, gbc);
          } else {
            orchestrator = new OrderedOutputOrchestrator(System.out);
          }
        } else {
          orchestrator = null;
        }

        try {
          final int totalThreads = threadCount;
          final boolean finalParallel = parallel;
          final boolean finalAggressiveIo = aggressiveIo;
          final boolean finalCharsetOpt = useCharsetOptimizations;

          for (var f : files) {
            final boolean finalPtxDebug = ptxDebug;
            final String finalOutputPath = outputPath;
            final int streamId = (orchestrator != null) ? orchestrator.registerStream() : -1;

            executor.submit(() -> {
              var outputFileName = f.getName() + extension;
              File outputFile;
              if (finalOutputPath != null && !"-".equals(finalOutputPath)) {
                outputFile = new File(finalOutputPath, outputFileName);
              } else {
                outputFile = new File(f.getParentFile(), outputFileName);
              }

              // Distribute threads: if we have more cores than files, allow internal parallelism
              int threadsPerFile = 0;
              boolean useInternalParallel = finalParallel;
              if (finalParallel) {
                  threadsPerFile = Math.max(1, totalThreads / files.length);
              }

              try {
                if ("-".equals(finalOutputPath)) {
                  try (OutputStream os = new BufferedOutputStream(orchestrator.createStreamOutputStream(streamId))) {
                    convertToXml(f, os, handlerFactory, finalPtxDebug, useInternalParallel, threadsPerFile, finalCharsetOpt);
                    os.flush();
                  }
                } else {
                  try (var fos = new FileOutputStream(outputFile)) {
                    if (finalAggressiveIo) {
                        long estimatedSize = com.mgz.util.SFSizeEstimator.estimateXmlSize(f.length());
                        if (estimatedSize > 0) {
                            fos.getChannel().position(estimatedSize - 1);
                            fos.write(0);
                            fos.getChannel().position(0);
                        }
                    }
                    if (useInternalParallel) {
                        convertToXml(f, fos, handlerFactory, finalPtxDebug, useInternalParallel, threadsPerFile, finalCharsetOpt);
                    } else {
                        try (OutputStream os = finalAggressiveIo ?
                            new DirectBufferOutputStream(128 * 1024, fos.getChannel()) :
                            new BufferedOutputStream(fos)) {
                            convertToXml(f, os, handlerFactory, finalPtxDebug, useInternalParallel, threadsPerFile, finalCharsetOpt);
                            os.flush();
                        }
                    }
                    if (finalAggressiveIo) {
                        fos.getChannel().truncate(fos.getChannel().position());
                    }
                  }
                  System.out.println("Export successful: " + outputFile.getPath());
                }
              } catch (Exception e) {
                var msg = e.getMessage() != null ? e.getMessage() : e.getClass().getSimpleName();
                synchronized (System.err) {
                  System.err.println("[" + Thread.currentThread().getName() + "] Error processing file "
                      + f.getName() + ": " + msg);
                }
                hasErrors.set(true);
              } finally {
                MnemonicPerformanceMonitor.merge();
                com.mgz.util.PTXPerformanceMonitor.merge();
              }
            });
          }
        } finally {
          executor.shutdown();
          if (!executor.awaitTermination(1, TimeUnit.HOURS)) {
            System.err.println("Timeout waiting for tasks to complete.");
            hasErrors.set(true);
          }
        }
        return hasErrors.get() ? 1 : 0;
      } else {
        HandlerFactory handlerFactory = new XmlHandlerFactory(xpathExpression);
        if ("-".equals(outputPath)) {
          WritableByteChannel wbc = null;
          if (aggressiveIo) {
            wbc = Channels.newChannel(System.out);
          }
          OutputStream os = (aggressiveIo && wbc != null) ?
              new DirectBufferOutputStream(128 * 1024, wbc) :
              new BufferedOutputStream(System.out);
          try {
            convertToXml(input, os, handlerFactory, ptxDebug, parallel, threadCount, useCharsetOptimizations);
            os.flush();
          } finally {
            if (!(os instanceof DirectBufferOutputStream)) {
              os.close();
            } else {
              // DirectBufferOutputStream.close() flushes and releases but doesn't close underlying channel
              os.close();
            }
          }
        } else {
          var outputFilePath = outputPath != null ? outputPath : inputPath + extension;
          var outputFile = new File(outputFilePath);
          try (var fos = new FileOutputStream(outputFile)) {
            if (aggressiveIo) {
                long estimatedSize = com.mgz.util.SFSizeEstimator.estimateXmlSize(input.length());
                if (estimatedSize > 0) {
                    fos.getChannel().position(estimatedSize - 1);
                    fos.write(0);
                    fos.getChannel().position(0);
                }
            }
            if (parallel) {
                convertToXml(input, fos, handlerFactory, ptxDebug, parallel, threadCount, useCharsetOptimizations);
            } else {
                try (OutputStream os = aggressiveIo ?
                    new DirectBufferOutputStream(128 * 1024, fos.getChannel()) :
                    new BufferedOutputStream(fos)) {
                    convertToXml(input, os, handlerFactory, ptxDebug, parallel, threadCount, useCharsetOptimizations);
                    os.flush();
                }
            }
            if (aggressiveIo) {
                fos.getChannel().truncate(fos.getChannel().position());
            }
          }
          System.out.println("Export successful: " + outputFile.getPath());
        }
        return 0;
      }
    } catch (Exception e) {
      System.err.println("Error during XML export: " + e.getMessage());
      e.printStackTrace();
      return 1;
    } finally {
      if (measure) {
        MnemonicPerformanceMonitor.printSummary();
      }
      if (ptxDebug) {
        com.mgz.util.PTXPerformanceMonitor.printSummary();
      }
    }
  }

  private static void printUsage(PrintStream out) {
    out.println("Usage: java -jar alpheus-afp-parser-cli.jar "
        + "[-d|--directory <dir>] [-x|--xpath <expression>] [-m|--measure] "
        + "[-p|--ptx-debug] [-c|--charset-opt] [-P|--parallel] [-a|--aggressive-io] [-t|--threads <n>] <input-afp-file/dir> [output-xml-file]");
    out.println("Options:");
    out.println("  -d, --directory <dir>     Convert all .afp files in the specified directory "
        + "to XML.");
    out.println("                            If a directory is provided as a positional "
        + "argument, directory mode is enabled automatically.");
    out.println("  -x, --xpath <expression>  Filter the generated XML using an XPath expression.");
    out.println("  -m, --measure             Measure and sum up the time needed to parse and "
        + "write each mnemonic.");
    out.println("  -p, --ptx-debug           Detailed PTX/PTOCA performance analysis.");
    out.println("  -c, --charset-opt         Enable optimized character set decoding.");
    out.println("  -P, --parallel            Enable parallel conversion for single files.");
    out.println("  -a, --aggressive-io       Enable experimental high-performance I/O (pre-allocation).");
    out.println("  -t, --threads <n>         Number of threads for parallel processing.");
    out.println("                            Defaults to the number of available processors.");
    out.println("  -h, --help                Show this help message.");
  }

  private static void convertToXml(File inputFile, java.io.OutputStream os, HandlerFactory handlerFactory,
      boolean ptxDebug, boolean parallel, int threadCount, boolean useCharsetOptimizations) throws Exception {
    var config = new AFPParserConfiguration();
    config.setAFPFile(inputFile);
    config.setEscalateParsingErrors(false);
    config.setPtxDebug(ptxDebug);
    config.setUseCharsetOptimizations(useCharsetOptimizations);

    if (parallel) {
      var converter = new com.mgz.afp.parser.ParallelAfpConverter(config, threadCount, handlerFactory);
      converter.convert(os);
      return;
    }

    var parser = new AFPParser(config);
    try {
      try (StructuredFieldHandler handler = handlerFactory.createHandler(os, false)) {
        StructuredField sf;
        while ((sf = parser.parseNextSF()) != null) {
          if (sf instanceof com.mgz.afp.base.StructuredFieldErrornouslyBuilt errSf) {
            synchronized (System.err) {
              System.err.println("[" + Thread.currentThread().getName() + "] Error parsing file "
                  + inputFile.getName() + ": " + errSf.getErrorMessage());
            }
          }
          handler.handle(sf);
          sf.release();
        }
      }
      if (parser.getNrOfSFBuiltWithErrors() > 0) {
        throw new Exception("Failed to process " + parser.getNrOfSFBuiltWithErrors()
            + " structured fields correctly.");
      }
    } finally {
      parser.quitParsing();
    }
  }
}
