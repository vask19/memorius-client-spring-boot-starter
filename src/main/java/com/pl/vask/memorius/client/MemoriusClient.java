package com.pl.vask.memorius.client;

public interface MemoriusClient {
    String record(String key, String value);
    String retrieve(String key);
    boolean exists(String key);
    String purge(String... keys);
    int appendToList(String key, String value);
    int countList(String key);
    String summon(String key);
    String expiate(String key, long seconds);
}
