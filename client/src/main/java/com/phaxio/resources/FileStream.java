package com.phaxio.resources;


import java.io.InputStream;


public class FileStream {
  private final InputStream inputStream;

  private final String fileName;

  public FileStream(InputStream inputStream, String fileName) {
    this.inputStream = inputStream;
    this.fileName = fileName;
  }

  public InputStream getInputStream() {
    return inputStream;
  }

  public String getFileName() {
    return fileName;
  }
}
