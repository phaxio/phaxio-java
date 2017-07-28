package com.phaxio.helpers;

import com.phaxio.restclient.entities.RestRequest;
import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class RestClientHelper {
    public static void addFile (File file, RestRequest request) {
        try {
            byte[] bytes = IOUtils.toByteArray(new FileInputStream(file));
            request.addFile("file[]", bytes, file.getName(), null);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
