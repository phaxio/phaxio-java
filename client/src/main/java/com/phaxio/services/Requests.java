package com.phaxio.services;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.phaxio.entities.Paging;
import com.phaxio.exceptions.*;
import com.phaxio.restclient.BasicAuthorization;
import com.phaxio.restclient.RestClient;
import com.phaxio.restclient.entities.Method;
import com.phaxio.restclient.entities.RestRequest;
import com.phaxio.restclient.entities.RestResponse;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.Proxy;
import java.util.*;

public class Requests {
    private final RestClient client;

    public Requests(String key, String secret, String endpoint, int port) {
    	this(key, secret, endpoint, port, null);
    }
    
    public Requests(String key, String secret, String endpoint, int port, Proxy proxy) {
        String endpointWithPort = String.format(endpoint, port);

        client = new RestClient(endpointWithPort, proxy);
        client.setAuthorization(new BasicAuthorization(key, secret));
    }

    public <T> T get(RestRequest request, Class clazz) {
        request.setMethod(Method.GET);

        RestResponse response = execute(request);

        return getData(response, clazz);
    }

    public <T> Iterable<T> list(final RestRequest request, final Class clazz) {
        request.setMethod(Method.GET);

        return new Iterable<T>() {

            @Override
            public Iterator<T> iterator() {
                return new Iterator<T>() {

                    {
                        load();
                    }

                    private void load() {
                        try {
                            RestResponse response = execute(request);

                            JsonNode json = response.toJson();

                            ArrayNode arrayNode = (ArrayNode)json.get("data");

                            paging = response.getMapper().readValue(json.get("paging").toString(), Paging.class);

                            list = getList(arrayNode, response.getMapper(), clazz);

                            iterator = null;
                        } catch (IOException e) {
                            throw new ApiConnectionException("Could not connect to the Phaxio API", e);
                        }
                    }

                    private Paging paging;
                    private List<T> list;
                    private Iterator<T> iterator;

                    @Override
                    public boolean hasNext() {
                        return getIter().hasNext() || hasNextPage();
                    }

                    @Override
                    public T next() {
                        if (getIter().hasNext()) {
                            return getIter().next();
                        } else if (hasNextPage()) {
                            request.addOrReplaceParameter("page", paging.page + 1);
                            load();
                            return getIter().next();
                        } else {
                            throw new NoSuchElementException();
                        }
                    }

                    @Override
                    public void remove() {
                        throw new UnsupportedOperationException();
                    }

                    private Iterator<T> getIter () {
                        if (iterator == null) {
                            iterator = list.iterator();
                        }

                        return iterator;
                    }

                    private boolean hasNextPage() {
                        return totalPages() > paging.page;
                    }

                    private int totalPages () {
                        BigDecimal total = new BigDecimal(paging.total);
                        BigDecimal perPage = new BigDecimal(paging.perPage);
                        return (int)total.divide(perPage, RoundingMode.UP).longValue();
                    }
                };
            }
        };
    }

    public <T> T post(RestRequest request, Class clazz) {
        request.setMethod(Method.POST);

        RestResponse response = execute(request);

        return getData(response, clazz);
    }

    public void post(RestRequest request) {
        request.setMethod(Method.POST);

        execute(request);
    }

    public byte[] download(RestRequest request) {
        request.setMethod(Method.GET);

        RestResponse response = execute(request);

        try {
            return response.getRawBytes();
        } catch (IOException e ) {
            throw new ApiConnectionException("Could not connect to the Phaxio API", e);
        }
    }

    public void delete(RestRequest request) {
        request.setMethod(Method.DELETE);

        execute(request);
    }

    private RestResponse execute(RestRequest request) {
        RestResponse response = client.execute(request);

        // Check connection errors
        if (response.getException() != null) {
            throw new ApiConnectionException("Could not connection the Phaxio API", response.getException());
        }

        // Check API errors
        try {
            switch (response.getStatusCode()) {
                case 200:
                case 201:
                    return response;
                case 401:
                    throw new AuthenticationException(getMessage(response));
                case 404:
                    throw new NotFoundException(getMessage(response));
                case 429:
                    throw new RateLimitException(getMessage(response));
                case 422:
                    throw new InvalidRequestException(getMessage(response));
                default:
                    throw new ServerException(getMessage(response));
            }
        } catch (IOException e) {
            throw new ApiConnectionException("Could not connect to the Phaxio API", e);
        }
    }

    private String getMessage(RestResponse response) throws IOException {
        if (response.getContentType().startsWith("application/json")) {
            return response.toJson().get("message").asText();
        } else {
            return "";
        }
    }

    private <T> T getData(RestResponse response, Class clazz) {
        try {
            T object = (T)response.getMapper().readValue(response.toJson().get("data").toString(), clazz);

            addClient(object, clazz);

            return object;
        } catch (IOException e) {
            throw new ApiConnectionException("Could not connect to the Phaxio API", e);
        }
    }

    private <T> List<T> getList(ArrayNode arrayNode, ObjectMapper mapper, Class clazz) throws IOException {
        ArrayList<T> list = new ArrayList<T>();

        for (JsonNode node : arrayNode) {
            T object = (T)mapper.readValue(node.toString(), clazz);

            addClient(object, clazz);

            list.add(object);
        }

        return list;
    }

    private void addClient(Object object, Class clazz) {
        try {
            java.lang.reflect.Method[] methods = clazz.getMethods();

            java.lang.reflect.Method method = null;

            Class[] parameterTypes = new Class[] {Requests.class};

            for (java.lang.reflect.Method m : methods) {
                if (m.getName().equals("setClient") && Arrays.equals(m.getParameterTypes(), parameterTypes)) {
                    method = m;
                }
            }

            if (method != null) {
                method.invoke(object, this);
            }
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }
}
