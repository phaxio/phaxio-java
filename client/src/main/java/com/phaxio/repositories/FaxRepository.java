package com.phaxio.repositories;

import com.phaxio.services.Requests;
import com.phaxio.resources.Fax;
import com.phaxio.restclient.entities.RestRequest;
import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class FaxRepository {
    private Requests client;

    public FaxRepository (Requests client) {
        this.client = client;
    }

    /**
     * Sends a fax
     * @param options A HashMap representing all the parameters for the fax. See the Phaxio documentation for the exact parameter names.
     *                Note that for fields that allow multiple items, use square brackets in the key and a Collection as the value.
     * @return The newly created Fax
     */
    public Fax create (Map<String, Object> options) {
        RestRequest request = new RestRequest();
        request.resource = "faxes";

        for (Entry<String, Object> option : options.entrySet()) {
            if (option.getKey().equals("to[]")) {
                Collection<String> recipients = (Collection<String>)option.getValue();
                for (String recipient : recipients) {
                    request.addParameter(option.getKey(), recipient);
                }
            } else if (option.getKey().equals("content_url[]")) {
                Collection<String> urls = (Collection<String>)option.getValue();
                for (String url : urls) {
                    request.addParameter(option.getKey(), url);
                }
            } else if (option.getKey().equals("file")) {
                addFile((File)option.getValue(), request);
            } else if (option.getKey().equals("file[]")) {
                Collection<File> files = (Collection<File>)option.getValue();
                for (File file : files) {
                    addFile(file, request);
                }
            } else {
                request.addParameter(option.getKey(), option.getValue());
            }
        }

        Fax fax = client.post(request, Fax.class);

        fax.setClient(client);

        return fax;
    }

    /**
     * Retrieves a fax by id
     * @param id The ID of the fax you'd like to retrieve
     * @return The fax
     */
    public Fax retrieve(int id) {
        RestRequest request = new RestRequest();
        request.resource = "faxes/" + id;

        return client.get(request, Fax.class);
    }

    /**
     * Lists sent faxes
     * @return A list of faxes
     */
    public Iterable<Fax> list() {
        return list(new HashMap<String, Object>());
    }

    /**
     * Lists sent faxes that match the given filters
     * @param filters The filters to apply (see the API documentation for parameters)
     * @return A list of faxes
     */
    public Iterable<Fax> list(Map<String, Object> filters) {
        RestRequest request = new RestRequest();
        request.resource = "faxes";

        for (Entry<String, Object> option : filters.entrySet()) {
            request.addParameter(option.getKey(), option.getValue());
        }

        return client.list(request, Fax.class);
    }

    /**
     * Sends a request to Phaxio to test a callback (web hook).
     * @param options The options for the callback - see the API documentation for the parameter names
     */
    public void testReceiveCallback(Map<String, Object> options)
    {
        RestRequest request = new RestRequest();
        request.resource = "faxes";

        request.addParameter("direction", "received");

        for (Entry<String, Object> option : options.entrySet()) {
            if (option.getKey().equals("file")) {
                addFile((File)option.getValue(), request, false);
            } else {
                request.addParameter(option.getKey(), option.getValue());
            }
        }

        client.post(request);
    }

    private void addFile (File file, RestRequest request) {
        addFile(file, request, true);
    }

    private void addFile (File file, RestRequest request, boolean multiple) {
        String param = multiple ? "file[]" : "file";

        try {
            byte[] bytes = IOUtils.toByteArray(new FileInputStream(file));
            request.addFile(param, bytes, file.getName(), null);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
