package com.phaxio;

import com.google.gson.JsonObject;
import com.sun.java.util.jar.pack.Package.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Fax extends ApiResource {

    public static final String HTML = "html";
    public static final String TEXT = "text";
    public static final String URL = "url";

    public static long send(List<String> phoneNumbers, List<File> files, Map<String, Object> options){
        if (options == null){
            options = new HashMap<String,Object>();
        }

        for(String number : phoneNumbers){
            options.put("to[]", number);
        }

        if (files != null){
            for (File file : files){

            }
        }

        return 0;
    }

    void mapJsonToSelf(JsonObject object){

    }
}
