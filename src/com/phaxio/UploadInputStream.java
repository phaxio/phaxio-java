/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.phaxio;

import java.io.InputStream;

/**
 *
 * @author Josh Nankin <josh@admorphous.com>
 */
public class UploadInputStream {
    InputStream inputStream;
    String filename;

    public UploadInputStream(InputStream stream, String filename){
        this.inputStream = stream;
        this.filename = filename;
    }
    
        public InputStream getInputStream() {
        return inputStream;
    }

    public String getFilename() {
        return filename;
    }
}
