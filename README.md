# AFP Parser [![Build](https://github.com/chatelao/alpheusafpparser/actions/workflows/ci.yml/badge.svg)](https://github.com/chatelao/alpheusafpparser/actions)

The AFP Parser is a library and parser for the IBM Advanced Function Presentation (AFP) 
document/print stream format.

> [!NOTE]
> This Java library is an independent open-source development. _It is **not** provided, endorsed, sponsored, or maintained by International Business Machines Corporation (IBM). The "AFP" and "Advanced Function Presentation" are trademarks and architectural standards originally developed by IBM._

![Architecture Diagram](https://www.plantuml.com/plantuml/proxy?cache=no&src=https://raw.githubusercontent.com/chatelao/alpheusafpparser/master/TOP_LEVEL.puml)

Alpheus covers all AFP specifications: MO:DCA, BCOCA, CMOCA, FOCA, GOCA, IOCA, and PTOCA.
It is a complete implementation. Every Structured Field, Repeating Group, and Triplet is fully implemented as Java class.
Alpheus AFP Parser was written from scratch and has no external dependencies.

To use Alpheus in your project add the following dependency:

    <dependency>
      <groupId>com.github.chatelao</groupId>
      <artifactId>alpheusafpparser</artifactId>
      <version>${version}</version>
    </dependency>

## Acknowledgements

This project is a continuation of the [Alpheus AFP Parser](https://github.com/afpdev/alpheusafpparser) originally developed by Rudolf Fiala. We gratefully acknowledge his work in creating this comprehensive AFP parsing library.

Copyright since 2026 Olivier Chatelain

Copyright 2015-2019  Rudolf Fiala

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

## Bonus

Videos about the standard:
- AFP Technical Series #1 Introduction to AFP: https://www.youtube.com/watch?v=X6cqw_TfjE0
- AFP Technical Series #2 Know your OCA: https://www.youtube.com/watch?v=YaZHAKP9eDg
- AFP Technical Series #3 AFP to PDF Conversion Demo: https://www.youtube.com/watch?v=sawL0DV67b0
- AFP Technical Series #4 AFP Indexing Demo: https://www.youtube.com/watch?v=7kzIemQAUUk

## External Components & Performance Alternatives

While the core Alpheus AFP Parser is designed to be dependency-free, certain external components are used for XML export, testing, and build automation. Below is an evaluation of these components and potential high-speed alternatives.

| Component | Usage | Alternatives for Speed |
| :--- | :--- | :--- |
| **JAXB** (`jaxb-api`, `jaxb-runtime`) | XML serialization of AFP objects in `Afp2Xml` and `AfpStreamingXmlWriter`. | 1. **Jackson XML**: High-performance streaming and data binding with lower overhead than JAXB. <br> 2. **Aalto XML**: Ultra-fast StAX implementation optimized for throughput. <br> 3. **Manual StAX Writing**: Bypassing reflection-based binding for hand-optimized XML generation. |
| **JUnit** (`junit-jupiter`, `junit-vintage`) | Execution of the test suite (350+ tests). | 1. **Parallel JUnit 5**: Leveraging concurrent execution to reduce total test time. <br> 2. **TestNG**: Offers advanced parallelization features for large-scale suites. <br> 3. **Spock**: Efficient for high-density data-driven tests. |
| **Checkstyle** | Enforcement of coding standards and static analysis. | 1. **Spotless**: Faster formatting-only tool that can be used in place of heavy linting. <br> 2. **Error Prone**: Hooks into the compiler for zero-overhead checks during compilation. <br> 3. **PMD**: Known for an efficient rule engine that can be tuned for speed. |
| **Shadow Jar** | Packaging the CLI application as a single executable JAR. | 1. **Gradle Application Plugin**: Native packaging (zip/tar) to avoid the overhead of merging JARs. <br> 2. **Capsule**: Provides a fast-launching alternative for modular deployments. <br> 3. **ProGuard**: Shrinks and optimizes the bytecode to reduce JAR size and I/O during startup. |
