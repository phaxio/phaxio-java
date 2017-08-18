package com.phaxio.restclient;

import com.phaxio.restclient.entities.*;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.*;

/**
 * Internally used REST client. Not part of the public API, subject to change at any time.
 */
public class RestClient {
    private static final int TIMEOUT = 30000;
    private final String endpoint;
    private BasicAuthentication auth;

    public RestClient(String endpoint) {
        this.endpoint = endpoint;
    }

    public void setAuthentication(BasicAuthentication auth) {
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
            conn = (HttpURLConnection)url.openConnection();
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

                        DataOutputStream dos = new DataOutputStream(conn.getOutputStream());


                        for (Parameter param : request.parameters) {
                            dos.writeBytes("--" + boundary + "\r\n");
                            dos.writeBytes(String.format("Content-Disposition: form-data; name=\"%s\"\r\n", param.getName()));
                            dos.writeBytes("\r\n");
                            dos.writeBytes(param.getValue().toString());
                            dos.writeBytes("\r\n");
                            dos.writeBytes("--" + boundary + "\r\n");
                        }

                        boolean first = true;

                        for (FileParameter file : request.files) {
                            if (first) {
                                first = false;
                            } else {
                                dos.writeBytes("\r\n");
                            }

                            dos.writeBytes("--" + boundary + "\r\n");
                            dos.writeBytes(String.format("Content-Disposition: form-data; name=\"%s\";filename=\"%s\"\r\n", file.name, file.fileName));
                            dos.writeBytes("\r\n");
                            dos.write(file.bytes);
                            dos.writeBytes("\r\n");
                            dos.writeBytes("--" + boundary);
                        }

                        dos.writeBytes("--\r\n");
                        dos.flush();
                        dos.close();
                    } else {
                        byte[] body = getQueryString(request).substring(1).getBytes("UTF-8");

                        conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                        conn.setRequestProperty("charset", "utf-8");
                        conn.setRequestProperty("Content-Length", Integer.toString(body.length));

                        DataOutputStream dos = new DataOutputStream(conn.getOutputStream());

                        dos.write(body);
                        dos.flush();
                        dos.close();
                    }

                    break;
            }

            response.setHttpURLConnection(conn);
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
}
