phaxio-java
===========

Phaxio API Java Client Library


Requirements
===========
Java 1.5 or later.

Installation
============

You'll need to manually install the following JARs:

* The Phaxio JAR
* [Google Gson](http://code.google.com/p/google-gson/) from <http://google-gson.googlecode.com/files/google-gson-2.2.4-release.zip>.

Creating the jar
================
1. Clone or download the repository
2. cd phaxio-java
3. mkdir lib && cd lib && wget http://google-gson.googlecode.com/files/google-gson-2.2.4-release.zip && unzip google-gson-2.2.4-release.zip
4. run 'ant jar'
5. jar can be found in the dist directory

Usage
=====

    Phaxio.apiKey = "your_api_key";
    Phaxio.apiSecret = "your_api_secret";
    
    Map<String,Object> options = new HashMap<String,Object>();
    options.put("string_data", "asdf");

    List<String> phoneNumbers = new ArrayList<String>();
    phoneNumbers.add("4141234567");

    List<File> files = new ArrayList<File>();
    files.add(new File("./test/apple.pdf"));

    Long faxId = Fax.send(phoneNumbers, files, options);
    
    
