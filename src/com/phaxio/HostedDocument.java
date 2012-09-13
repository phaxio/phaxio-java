package com.phaxio;

import com.phaxio.HostedDocument;
import com.phaxio.exception.PhaxioException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class HostedDocument {
    String name;
    String metadata;
    InputStream is;

    public static HostedDocument retrieve(String name, File outputFile) throws PhaxioException {
        return retrieve(name, null, outputFile);
     }

    public static HostedDocument retrieve(String name, String metadata, File outputFile) throws PhaxioException {
        Map<String,Object> options = new HashMap<String,Object>();

        if (metadata != null){
            options.put("metadata", metadata);
        }

        options.put("name", name);

        InputStream is = Phaxio.doRequestForInputStream("getHostedDocument", options, "POST");
        HostedDocument doc = new HostedDocument();
        doc.name = name;
        doc.metadata = metadata;
        doc.is = is;

        return doc;
    }

    public InputStream getIs() {
        return is;
    }

    public String getMetadata() {
        return metadata;
    }

    public String getName() {
        return name;
    }

}
