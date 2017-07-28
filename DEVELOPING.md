# Developing this client

## Publishing to Maven

TODO: document process

## Testing

### Scenarios

To run them, you must first place your API keys in an a file called "testing.properties" in test/resources.
(This file will not be checked into Git, as it is ignored).
See the sample file testing.properties.sample for an example.

### Mocked tests

We use (WireMock)[https://stackoverflow.com/questions/11005279/how-do-i-unit-test-code-which-calls-the-jersey-client-api]