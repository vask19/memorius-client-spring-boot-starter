package com.pl.vask.memorius.client.resp;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class RESPReader {
    private final BufferedInputStream input;

    public RESPReader(InputStream in) {
        this.input = new BufferedInputStream(in);
    }

    public Object read() throws IOException {
        int prefix = input.read();
        if (prefix == -1) {
            throw new EOFException("End of stream");
        }

        switch (prefix) {
            case '+':
                return readSimpleString();
            case '-':
                return readError();
            case ':':
                return readInteger();
            case '$':
                return readBulkString();
            case '*':
                return readArrayAsList();
            default:
                throw new IOException("Unknown RESP type: " + (char) prefix);
        }
    }

    private String readLine() throws IOException {
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        int b;
        while ((b = input.read()) != -1) {
            if (b == '\r') {
                int next = input.read();
                if (next == '\n') {
                    break;
                } else {
                    buffer.write(b);
                    buffer.write(next);
                }
            } else {
                buffer.write(b);
            }
        }
        return buffer.toString(StandardCharsets.UTF_8);
    }

    private String readSimpleString() throws IOException {
        return readLine();
    }

    private String readError() throws IOException {
        return readLine();
    }

    private long readInteger() throws IOException {
        String line = readLine();
        return Long.parseLong(line);
    }

    private String readBulkString() throws IOException {
        int length = (int) readInteger();
        if (length == -1) {
            return null;
        }
        byte[] buffer = new byte[length];
        input.read(buffer);
        input.read(); // skip \r
        input.read(); // skip \n
        return new String(buffer, StandardCharsets.UTF_8);
    }

    private List<Object> readArrayAsList() throws IOException {
        int length = (int) readInteger();
        if (length == -1) {
            return null;
        }
        List<Object> result = new ArrayList<>(length);
        for (int i = 0; i < length; i++) {
            result.add(read());
        }
        return result;
    }
}
