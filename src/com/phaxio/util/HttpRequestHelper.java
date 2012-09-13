/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.phaxio.util;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.Map;

/**
 *
 * @author jnankin
 */
public class HttpRequestHelper {

    private final String lineEnd = "\r\n";
    private final String twoHyphens = "--";
    private final String boundary = "*****";
    private final int maxBufferSize = 1 * 1024 * 1024;

    private int statusCode;
    private String contentType;

    public InputStream doRequest(String urlString, Map<String, Object> params, String method)
            throws ProtocolException, MalformedURLException, IOException {

        URL url = new URL(urlString);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setDoInput(true);
        conn.setDoOutput(true);
        conn.setUseCaches(false);
        conn.setRequestMethod(method);
        conn.setRequestProperty("Connection", "Keep-Alive");

        if (method.equals("POST")) {
            conn.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);
        }

        DataOutputStream dos = new DataOutputStream(conn.getOutputStream());

        if (params != null && params.size() > 0) {
            for (Map.Entry<String, Object> entry : params.entrySet()) {
                addParameterToRequest(dos, entry.getKey(), entry.getValue());
            }
        }

        dos.flush();
        dos.close();

        if (conn.getResponseCode() >= 400){
            return conn.getErrorStream();
        }

        return conn.getInputStream();
    }

    private void addParameterToRequest(DataOutputStream dos, String key, Object value) throws IOException {
        if (value instanceof File) {
            addFileParameterToRequest(dos, key, (File) value);
        } else {
            addNormalParameterToRequest(dos, key, value);
        }

    }

    private void addNormalParameterToRequest(DataOutputStream dos, String key, Object value) throws IOException {
        dos.writeBytes(twoHyphens + boundary + lineEnd);
        dos.writeBytes("Content-Disposition: form-data; name=\"" + key + "\"" + lineEnd + lineEnd);
        dos.writeBytes(value.toString() + lineEnd);
    }

    private void addFileParameterToRequest(DataOutputStream dos, String key, File file) throws FileNotFoundException, IOException {
        int bytesRead, bytesAvailable, bufferSize;

        byte[] buffer;
        // Send a binary file
        dos.writeBytes(twoHyphens + boundary + lineEnd);
        dos.writeBytes("Content-Disposition: form-data; name=\"" + key + "\";filename=\"" + file.getName() + "\"" + lineEnd);
        dos.writeBytes(lineEnd);

        FileInputStream fileInputStream = new FileInputStream(file);
        bytesAvailable = fileInputStream.available();
        bufferSize = Math.min(bytesAvailable, maxBufferSize);
        buffer = new byte[bufferSize];
        bytesRead = fileInputStream.read(buffer, 0, bufferSize);

        while (bytesRead > 0) {
            dos.write(buffer, 0, bufferSize);
            bytesAvailable = fileInputStream.available();
            bufferSize = Math.min(bytesAvailable, maxBufferSize);
            bytesRead = fileInputStream.read(buffer, 0, bufferSize);
        }

        dos.writeBytes(lineEnd);
        dos.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);
        fileInputStream.close();
    }

    public int getStatusCode(){
        return statusCode;
    }

    public String getContentType(){
        return contentType;
    }
}
