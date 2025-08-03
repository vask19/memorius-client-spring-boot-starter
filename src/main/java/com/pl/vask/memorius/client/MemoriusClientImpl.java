package com.pl.vask.memorius.client;

import com.pl.vask.memorius.client.resp.RESPReader;
import com.pl.vask.memorius.client.resp.RESPWriter;

import java.io.*;
import java.net.Socket;

public class MemoriusClientImpl implements MemoriusClient {

    private final String host;
    private final int port;

    public MemoriusClientImpl(String host, int port) {
        this.host = host;
        this.port = port;
    }

    private synchronized Object sendCommand(Object... command) throws IOException {
        try (Socket socket = new Socket(host, port);
             OutputStream out = socket.getOutputStream();
             InputStream in = socket.getInputStream()) {

            RESPWriter writer = new RESPWriter(out);
            RESPReader reader = new RESPReader(in);

            writer.writeArrayRaw(command);
            return reader.read();
        }
    }

    @Override
    public String record(String key, String value) {
        try {
            Object response = sendCommand("RECORD", key, value);
            return response != null ? response.toString() : null;
        } catch (IOException e) {
            throw new RuntimeException("RECORD failed", e);
        }
    }

    @Override
    public String retrieve(String key) {
        try {
            Object response = sendCommand("RETRIEVE", key);
            return response != null ? response.toString() : null;
        } catch (IOException e) {
            throw new RuntimeException("RETRIEVE failed", e);
        }
    }

    @Override
    public boolean exists(String key) {
        try {
            Object response = sendCommand("EXISTS", key);
            return "1".equals(response);
        } catch (IOException e) {
            throw new RuntimeException("EXISTS failed", e);
        }
    }

    @Override
    public String purge(String... keys) {
        try {
            Object[] command = new Object[keys.length + 1];
            command[0] = "PURGE";
            System.arraycopy(keys, 0, command, 1, keys.length);
            Object response = sendCommand(command);
            return response != null ? response.toString() : null;
        } catch (IOException e) {
            throw new RuntimeException("PURGE failed", e);
        }
    }

    @Override
    public int appendToList(String key, String value) {
        try {
            Object response = sendCommand("APPEND", key, value);
            return Integer.parseInt(response.toString());
        } catch (IOException e) {
            throw new RuntimeException("APPEND failed", e);
        }
    }

    @Override
    public int countList(String key) {
        try {
            Object response = sendCommand("COUNT", key);
            return Integer.parseInt(response.toString());
        } catch (IOException e) {
            throw new RuntimeException("COUNT failed", e);
        }
    }

    @Override
    public String summon(String key) {
        try {
            Object response = sendCommand("SUMMON", key);
            return response != null ? response.toString() : null;
        } catch (IOException e) {
            throw new RuntimeException("SUMMON failed", e);
        }
    }

    @Override
    public String expiate(String key, long seconds) {
        try {
            Object response = sendCommand("EXPIATE", key, seconds);
            return response != null ? response.toString() : null;
        } catch (IOException e) {
            throw new RuntimeException("EXPIATE failed", e);
        }
    }
}
