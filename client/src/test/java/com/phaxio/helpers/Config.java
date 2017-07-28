package com.phaxio.helpers;

import java.io.FileInputStream;
import java.net.URL;
import java.util.Properties;

public class Config {

    public static String get(String key) {
        if (properties == null) {
            URL testFileUrl = Config.class.getResource("/testing.properties");

            properties = new Properties();
            try {
                properties.load(new FileInputStream(testFileUrl.getFile()));
            } catch (Exception e) {}
        }

        return properties.getProperty(key);
    }

    private static Properties properties;
}
