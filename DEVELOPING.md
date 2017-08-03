# Developing this client

## Publishing to Maven

Add your Sonatype credentials in ~/.m2/settings.xml or client/settings.xml (see client/settings.xml.sample), then run:

mvn release:clean release:prepare release:perform -B -e

This will also tag your git repo with the release number, but not push it to GitHub.

Note that settings.xml in the .m2 folder with override a local settings.xml

Run this command: `mvn release:clean release:prepare release:perform -B -e`

The package will be released to the Sonatype repo and then synced to Maven Central.

## Testing

### Scenarios

To run them, you must first place your API keys in an a file called "testing.properties" in test/resources.
(This file will not be checked into Git, as it is ignored).
See the sample file testing.properties.sample for an example.

### Mocked tests

We use (WireMock)[https://stackoverflow.com/questions/11005279/how-do-i-unit-test-code-which-calls-the-jersey-client-api]