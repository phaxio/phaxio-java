package com.phaxio.helpers;

import com.google.common.base.Charsets;
import com.google.common.io.Files;

import java.io.File;
import java.io.IOException;
import java.net.URL;

public class Fixtures {
    public static File file(String name) {
        URL url = Fixtures.class.getResource(name);
        return new File(url.getFile());
    }

    public static String json(String name) {
        try {
            URL url = Fixtures.class.getResource(name);
            String json = Files.toString(new File(url.getFile()), Charsets.UTF_8);

            // Remove the BOM
            if (json.startsWith("\uFEFF")) {
                json = json.substring(1);
            }

            return json;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
