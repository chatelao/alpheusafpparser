package com.mgz.testplugin;

import com.mgz.afp.base.StructuredField;
import com.mgz.afp.base.handler.HandlerFactory;
import com.mgz.afp.base.handler.StructuredFieldHandler;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Map;

public class TestHandlerFactory implements HandlerFactory {
    @Override
    public String getDefaultExtension(Map<String, String> options) {
        return ".test";
    }

    @Override
    public StructuredFieldHandler createHandler(OutputStream os, boolean fragmentMode) throws Exception {
        return new StructuredFieldHandler() {
            private final PrintWriter writer = new PrintWriter(os);
            @Override
            public void handle(StructuredField sf) throws Exception {
                String mnemonic = "UNKNOWN";
                if (sf.getStructuredFieldIntroducer() != null && sf.getStructuredFieldIntroducer().getSFTypeID() != null) {
                    mnemonic = sf.getStructuredFieldIntroducer().getSFTypeID().name();
                }
                writer.println("TEST: " + mnemonic);
            }
            @Override
            public void close() throws Exception {
                writer.flush();
            }
        };
    }

    @Override
    public long estimateOutputSize(long inputSize) {
        return inputSize;
    }

    @Override
    public String getFormatName() {
        return "testformat";
    }
}
