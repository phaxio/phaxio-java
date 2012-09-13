package com.phaxio;

import com.google.gson.JsonObject;
import com.phaxio.exception.PhaxioException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author jnankin
 */
public class PhaxCode {

    String metadata;
    InputStream is;

    public static InputStream attachToPdf(int x, int y, File inputFile, Map<String, Object> options) throws PhaxioException{
        if (options == null){
            options = new HashMap<String,Object>();
        }

        options.put("filename", inputFile);
        options.put("x", x);
        options.put("y", y);
        options.put("page_number", y);

        return Phaxio.doRequestForInputStream("testReceive", options, "POST");
    }

    public static PhaxCode create(String metadata) throws PhaxioException{
        Map<String, Object> options = new HashMap<String,Object>();
        PhaxCode phaxCode = new PhaxCode();

        if (metadata != null){
            options.put("metadata", metadata);
            phaxCode.metadata = metadata;
        }

        options.put("redirect", true);


        phaxCode.is = Phaxio.doRequestForInputStream("createPhaxCode", options, "POST");
        return phaxCode;
    }

}
