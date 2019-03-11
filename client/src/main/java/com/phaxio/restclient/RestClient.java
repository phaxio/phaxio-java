package com.phaxio.restclient;

import com.phaxio.restclient.entities.*;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.OutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.net.*;

import java.io.File;
import com.google.common.io.Files;

/**
 * Internally used REST client. Not part of the public API, subject to change at any time.
 */
public class RestClient {
    private static final int TIMEOUT = 30000;
    private final String endpoint;
    private BasicAuthorization auth;
    final private  Proxy proxy;
    
    public RestClient(String endpoint, Proxy proxy) {
        this.endpoint = endpoint;
        this.proxy = proxy;
    }
    
    public void setAuthorization(BasicAuthorization auth) {
        this.auth = auth;
    }

    public RestResponse execute(RestRequest request) {
        RestResponse response = new RestResponse();
        HttpURLConnection conn = null;
        try {
            String queryString = "";

            switch (request.method) {
                case GET:
                case DELETE:
                    queryString = getQueryString(request);
            }

            URL url = new URL(endpoint + request.getResource() + queryString);
            conn = (HttpURLConnection)openConnection(url);
            conn.setUseCaches(false);
            conn.setAllowUserInteraction(false);
            conn.setConnectTimeout(TIMEOUT);
            conn.setReadTimeout(TIMEOUT);

            if (auth != null) {
                conn.setRequestProperty ("Authorization", auth.toHeader());
            }

            switch (request.method) {
                case GET:
                    conn.setRequestMethod("GET");
                    conn.setRequestProperty("Content-length", "0");
                    conn.connect();
                    break;
                case DELETE:
                    conn.setRequestMethod("DELETE");
                    conn.setRequestProperty("Content-length", "0");
                    conn.connect();
                    break;
                case POST:
                    conn.setRequestMethod("POST");
                    conn.setDoOutput(true);
                    conn.setRequestProperty("Connection", "Keep-Alive");
                    conn.setRequestProperty("Cache-Control", "no-cache");

                    if (!request.files.isEmpty()) {
                        String boundary = "--------------------------------" + System.currentTimeMillis();
                        conn.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);

                        ByteArrayOutputStream baos = new ByteArrayOutputStream();

                        for (Parameter param : request.parameters) {
                            writeUTF8ToOs(baos,"--" + boundary + "\r\n");
                            writeUTF8ToOs(baos, String.format("Content-Disposition: form-data; name=\"%s\"\r\n", param.getName()));
                            writeUTF8ToOs(baos, "Content-Type: text/plain; charset=utf-8\r\n");
                            writeUTF8ToOs(baos, "\r\n");
                            writeUTF8ToOs(baos, param.getValue().toString());
                            writeUTF8ToOs(baos, "\r\n");
                            writeUTF8ToOs(baos, "--" + boundary + "\r\n");
                        }

                        boolean first = true;

                        for (FileParameter file : request.files) {
                            if (first) {
                                first = false;
                            } else {
                                writeUTF8ToOs(baos, "\r\n");
                            }

                            writeUTF8ToOs(baos, "--" + boundary + "\r\n");
                            writeUTF8ToOs(baos, String.format("Content-Disposition: form-data; name=\"%s\"; filename=\"%s\"\r\n", file.name, file.fileName));
                            writeUTF8ToOs(baos, "Content-Type: application/octet-stream\r\n");

                            writeUTF8ToOs(baos, "\r\n");
                            baos.write(file.bytes);
                            writeUTF8ToOs(baos, "\r\n");
                            writeUTF8ToOs(baos, "--" + boundary);
                        }

                        writeUTF8ToOs(baos, "--\r\n");

                        conn.setRequestProperty("Content-Length", Integer.toString(baos.size()));

                        DataOutputStream dos = new DataOutputStream(conn.getOutputStream());

                        baos.writeTo(dos);

                        dos.flush();
                        dos.close();
                    } else if (!request.parameters.isEmpty()) {
                        byte[] body = getQueryString(request).substring(1).getBytes("UTF-8");

                        conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                        conn.setRequestProperty("charset", "utf-8");
                        conn.setRequestProperty("Content-Length", Integer.toString(body.length));

                        DataOutputStream dos = new DataOutputStream(conn.getOutputStream());

                        dos.write(body);
                        dos.flush();
                        dos.close();
                    } else {
                        conn.setRequestProperty("Content-Length", "0");
                    }

                    break;
            }

            response.setHttpURLConnectionAndRead(conn);
        } catch (MalformedURLException e) {
            response.setException(e);
        } catch (ProtocolException e) {
            response.setException(e);
        } catch (IOException e) {
            response.setException(e);
        } finally {
            if (conn != null) {
                conn.disconnect();
            }
        }

        return response;
    }
    
    private URLConnection openConnection(URL url) throws IOException {
    	URLConnection connection;

    	if (proxy == null) {
            connection = url.openConnection();
    	} else {
            connection = url.openConnection(proxy);
    	}
    	
    	return connection;
    }

    private String getQueryString(RestRequest request) {
        if (request.parameters.isEmpty()) {
            return "";
        }

        StringBuilder sb = new StringBuilder("?");

        String separator = "";

        String charset = "UTF-8";
        for (Parameter param : request.parameters) {
            try {
                String encodedName = URLEncoder.encode(param.getName(), charset);
                String encodedValue = URLEncoder.encode(param.getValue().toString(), charset);
                sb.append(separator);
                sb.append(encodedName);
                sb.append("=");
                sb.append(encodedValue);
            } catch (UnsupportedEncodingException e) {
                throw new RuntimeException(e);
            }

            separator = "&";
        }

        return sb.toString();
    }

    public static String escape (String str) {
        try {
            return URLEncoder.encode(str, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

    private void writeUTF8ToOs (OutputStream os, String str) throws java.io.IOException {
        os.write(utfStringToBytes(str));
    }

    private byte[] utfStringToBytes (String str) {
        return str.getBytes(Charset.forName("UTF-8"));
    }
}
