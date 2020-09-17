package com.nickmafra.concurrent;

import java.io.IOException;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;

public class Pipe {

    private final PipedInputStream pipedInputStream;
    private final PipedOutputStream pipedOutputStream;

    public Pipe() throws IOException {
        pipedInputStream = new PipedInputStream();
        pipedOutputStream = new PipedOutputStream(pipedInputStream);
    }

    public PipedInputStream getInputStream() {
        return pipedInputStream;
    }

    public PipedOutputStream getOutputStream() {
        return pipedOutputStream;
    }
}
