package com.phaxio.resources;

import com.phaxio.services.Requests;
import com.phaxio.restclient.entities.RestRequest;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;

public class FaxFile
{
    public void setClient(Requests client) {
        this.client = client;
    }

    private Requests client;
    private int faxId;
    private String thumbnail;

    public FaxFile(int faxId)
    {
        this(faxId, null);
    }

    public FaxFile(int faxId, String thumbnail)
    {
        this.faxId = faxId;
        this.thumbnail = thumbnail;
    }

    public File toFile(String name) throws IOException {
        File file = new File(name);

        FileUtils.writeByteArrayToFile(file, getBytes());

        return file;
    }

    public byte[] getBytes()
    {
        RestRequest request = new RestRequest();
        request.resource = "faxes/" + faxId + "/file";

        if (thumbnail != null) {
            request.addParameter("thumbnail", thumbnail);
        }

        return client.download(request);
    }

    public FaxFile smallJpeg()
    {
        FaxFile file = new FaxFile(faxId, "s");
        file.setClient(client);
        return file;
    }

    public FaxFile largeJpeg()
    {
        FaxFile file = new FaxFile(faxId, "l");
        file.setClient(client);
        return file;
    }

    public FaxFile pdf() {
        FaxFile file = new FaxFile(faxId);
        file.setClient(client);
        return file;
    }

    /**
     * Deletes a fax's file
     */
    public void delete()
    {
        RestRequest request = new RestRequest();
        request.resource = "faxes/" + faxId + "/file";

        client.delete(request);
    }
}