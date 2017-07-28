# Phaxio !!!ALPHA!!!

[![Build Status](https://travis-ci.org/phaxio/phaxio-java.svg?branch=master)](https://travis-ci.org/phaxio/phaxio-java)

Phaxio is the only cloud based fax API designed for developers. This is the Java client library for Phaxio.

## Getting started

First, [sign up](https://console.phaxio.com/signup) if you haven't already.

Second, go to [api settings](https://console.phaxio.com/apiSettings) and get your key and your secret.

Third, add this library as a dependency in your pom.xml:

    <dependency>
        <groupId>com.phaxio</groupId>
        <artifactId>phaxio-client</artifactId>
        <version>1.0</version>
    </dependency>

Use [this guide](QUICKSTART.md) for basic usage.

## Migration from previous library version

This is a complete re-write and starts from scratch in its design. Please see the above documentation about how to use the new library and its calls.

## Migration from API V1 to V2

This library now uses Phaxio API V2, so these methods have been removed and have no equivalent:

- Phaxcode.attachToPdf was removed
- HostedDocument was removed

## Errors

Operations that connect to Phaxio will throw an exception if an error is encountered.

RateLimitException happens if you have made too many requests per second.
InvalidRequestException is throw if the data sent to Phaxio is not correct
AuthenticationException gets thrown when your credentials are invalid
NotFoundException is throw when you try to retrieve a resource by ID but it isn't found
ApiConnectionException occurs when there is a network issue
ServerException happens if the server is not working
PhaxioClientException occurs when there's a problem with this library. Do not try to handle this error - report any instances
to support.

### Rate limiting

The Phaxio API is rate limited. If you make too many requests too quickly, you might receive this error.
Check the exception message, wait a second, and then try your request again.

## Writing callbacks (webhooks)

Writing a callback to get fax send or receive events is simple. Read this [handy guide](CALLBACKS.md) to get started.

&copy; 2016-2017 Phaxio