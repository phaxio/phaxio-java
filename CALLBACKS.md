# Writing callbacks (webhooks)

Phaxio can send you a message via callback (or webhook) every time you send or recieve a fax.
Writing a callback is the way you get notified you have a new fax sent to one of your numbers.
Using Web API, it's simple to write a callback. Checkout the phaxio-java-callback-example repository for
a full implementation.

## Example code

Let's take a look at the `getFaxFromFormData` method:



Finally, here's the `FaxReceipt` class:


## Testing your callback

To test a send callback, just send a fax. To trigger a recieve callback, use the TestRecieveCallback method:

```Java
File testFax = new File("test-fax.pdf");
phaxio.fax.testRecieveCallback(testFax);
```

&copy; 2016-2017 Phaxio