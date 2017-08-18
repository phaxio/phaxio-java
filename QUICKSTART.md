# Usage examples

## Basics

The Phaxio class is the entry point for any Phaxio operation.

    Phaxio phaxio = new Phaxio(key, secret);
    
### Getting your account status

    AccountStatus status = phaxio.account().status();
    System.out.println(String.format("Balance: %1$d", status.balance));

## Faxes

### Sending a fax

At the heart of the Phaxio API is the ability to send a fax. You pass in a Map<String, Object> to create your fax:

    HashMap<String, Object> faxParams = new HashMap<>();
    faxParams.put("to", "8088675309");
    faxParams.put("file", new File("form1234.pdf"));
    Fax fax = phaxio.fax.create(faxParams);

The fax object can be used to reference your fax later. Well, now, wasn't that simple?
    
If you have more than one file, you can pass in a list and Phaxio will concatenate them into one fax:

	HashMap<String, Object> faxParams = new HashMap<>();
    faxParams.put("to", "8088675309");

    List<File> files = Arrays.asList(pdf1, pdf2);
    faxParams.put("files", files);

    faxParams.put("caller_id", "2125552368");

    Fax fax = phaxio.fax.create(faxParams);

If you have a bunch of faxes going to one number, you might want to check out [batching](https://www.phaxio.com/docs/api/send/batching/).
You first specify a batch delay. Then, you send as many faxes as you'd like to the number in question, 
and when you're finished and the batch delay is expired, Phaxio will send them all as one long fax. Here's what that would look like:
    
    HashMap<String, Object> fax1Params = new HashMap<>();
    fax1Params.put("to", "8088675309");
    fax1Params.put("file", pdf1);
    fax1Params.put("batch_delay", 30);
    
    HashMap<String, Object> fax2Params = new HashMap<>();
    fax2Params.put("to", "8088675309");
    fax2Params.put("file", pdf1);
    fax2Params.put("batch_delay", 30);

    phaxio.fax.create(fax1Params);
    phaxio.fax.create(fax2Params);

The machine at 808-867-5309 will see pdf1 and pdf2 as one long fax.

### Querying sent faxes

To see your sent faxes after you've sent it, call Retrieve on the Faxes property:

    Fax fax = phaxio.fax.retrieve("1234");
    
This returns a single Fax or throws a NotFound exception. You can also add filters:

    HashMap<String, Object> filters = new HashMap<>();
    filters.put("created_before", new Date());

    List<Fax> faxes = phaxio.fax.list(filters);

This returns a List<Fax>.

### Downloading a fax

To retrieve the fax file after you've sent it, call the File method on the Fax instance:

    FaxFile file = fax.file();
    
File is a FaxFile object representing your fax. You can call the Bytes property which returns a byte array of the PDF that you can write to disk or store in a database.

You can also specify which format you'd like using the File property on the Fax instance:

    FaxFile thumbnail = fax.file().smallJpeg();
    
Call `smallJpeg()` for a small JPEG, `largeJpeg()` for a large JPEG, or `pdf()` for PDF.

### Resending a fax

You can resend a fax:

    fax.resend();

### Cancelling a fax

You can cancel a fax:

    fax.cancel();

### Deleting a fax's files

You can delete a fax's files:

    fax.file().delete();

### Deleting a fax

You can delete a fax:

    fax.delete();

## Numbers

### Getting area codes 

If you want to know what area codes are available for purchase, you can call this property:

    List<AreaCode> areaCodes = phaxio.public.areaCode().list();
    
This returns a IEnumberable of AreaCode objects. You can also optionally request tollfree numbers:

    HashMap<String, Object> filters = new HashMap<>();
    filters.put("toll_free", true);

    List<AreaCode> areaCodes = phaxio.public.areaCode().list(filters);

You can specify the state (and you must specify either the country code or the country if you do):

    HashMap<String, Object> filters = new HashMap<>();
    filters.put("state", "MA");
    filters.put("country", "US");

    List<AreaCode> areaCodes = phaxio.public.areaCode().list(filters);

Or both:

    HashMap<String, Object> filters = new HashMap<>();
    filters.put("toll_free", true);
    filters.put("state", "MA");
    filters.put("country", "US");

    List<AreaCode> areaCodes = phaxio.public.areaCode().list(filters);

### Provisioning a number

You can ask Phaxio to get you a new number (you must specify an country code and area code):

    PhoneNumber newNumber = phaxio.phoneNumber.create("1", "808");

The call returns a PhoneNumber object representing your new number.

You can also specify a callback URL that will be called when a fax is recieved
at the new number (this will override the default callback URL).

    PhoneNumber newNumber = phaxio.phoneNumber.create("1", "808", "https://example.com/callback");
    
### Listing your numbers

To get a list of your numbers, you can run this method:

    List<PhoneNumber> numbers = phaxio.phoneNumber.list();

which will return a List<PhoneNumber> with all of your numbers.

You can specify an country code to search in:

    HashMap<String, Object> filters = new HashMap<>();
    filters.put("country_code", "1");

    List<PhoneNumber> numbers = phaxio.phoneNumber.list(filters);

You can specify an area code to search in (you must also specify the country code):

    HashMap<String, Object> filters = new HashMap<>();
    filters.put("country_code", "1");
    filters.put("area_code", "808");

    List<PhoneNumber> numbers = phaxio.phoneNumber.list(filters);

or you can retrieve a specific number:
    
    PhoneNumber number = phaxio.phoneNumber.retrieve("8088675309");
    
### Release number

You can a release number (give it back to Phaxio):

    number.release();

This operation will throw an exception if the number cannot be released.
    
## PhaxCodes

## Creating a PhaxCode

Creating a PhaxCode is simple, just pass the metadata that you want associated with your PhaxCode:

    PhaxCode phaxCode = phaxio.phaxCode.create("code-for-form1234");

You can also get the image directly:

    FileOutputStream stream = new FileOutputStream("/tmp/phaxCode.png");
    stream.write(phaxCode.png());
    stream.close();

To get the properties of the newly generated code:

    PhaxCode phaxCode = phaxio.phaxCode.retrieve(codeId);

To download the PNG of this newly generated code:

    byte[] codeBytes = phaxio.phaxCode.retrieve(codeId).png();

## Misc

### Getting supported countries

If you want to know what countries are supported by Phaxio, you can call this method:

    List<Country> supportedCountries = phaxio.public.supportedCountry.list();
    
This returns a List of Country objects that have pricing and the services available.
 
### Testing callbacks (web hooks)

So you've written a callback or a webhook you'd like tested. It's simple to have Phaxio send you a fax:

    Fax fax = phaxio.fax.testRecieveCallback(new File("test-fax.pdf"));
    
This returns a Fax object. This will call your default account callback. If you've specified a callback
for an individual number and you'd like to test that callback, you can specify it with the toNumber parameter:

    HashMap<String, Object> options = new HashMap<>();
    options.put("to", "8088675309");

    Fax fax = phaxio.Fax.testRecieveCallback(new File("test-fax.pdf"), options);

You can also fake who the fax is from:

    HashMap<String, Object> options = new HashMap<>();
    options.put("from", "2125552368");

    Fax fax = phaxio.Fax.testRecieveCallback(new File("test-fax.pdf"), options);

&copy; 2016-2017 Phaxio