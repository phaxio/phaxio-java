package com.phaxio.restclient.entities;

public class FileParameter {
    public final String name;
    public final byte[] bytes;
    public final String fileName;
    public final String contentType;

    public FileParameter (String name, byte[] bytes, String fileName, String contentType) {
        this.name = name;
        this.bytes = bytes;
        this.fileName = fileName;
        this.contentType = contentType;
    }
}
