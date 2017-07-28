package com.phaxio.integrationtests.mocked;

import com.github.tomakehurst.wiremock.junit.WireMockRule;
import com.phaxio.Phaxio;
import com.phaxio.fixtures.BinaryFixtures;
import com.phaxio.helpers.Responses;
import com.phaxio.resources.FaxFile;
import com.phaxio.services.Requests;
import org.junit.Rule;
import org.junit.Test;

import java.io.IOException;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.junit.Assert.assertArrayEquals;

public class FaxFileTest {
    private static final int TEST_PORT = 8089;

    @Rule
    public WireMockRule wireMockRule = new WireMockRule(TEST_PORT);

    @Test
    public void getsFaxFile () throws IOException {
        byte[] fileBytes = Responses.file("/test.pdf");

        stubFor(get(urlEqualTo("/v2/faxes/1/file?api_secret=SECRET&api_key=KEY"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/pdf")
                        .withBody(fileBytes)));

        Requests client = new Requests("KEY", "SECRET", "http://localhost:%s/v2/", TEST_PORT);

        FaxFile file = new FaxFile(1);

        file.setClient(client);

        assertArrayEquals(fileBytes, file.getBytes());
    }

    @Test
    public void getsLargeThumbnail () throws IOException {
        byte[] fileBytes = BinaryFixtures.getTestPhaxCode();

        stubFor(get(urlEqualTo("/v2/faxes/1/file?thumbnail=l&api_secret=SECRET&api_key=KEY"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/octet")
                        .withBody(fileBytes)));

        Requests client = new Requests("KEY", "SECRET", "http://localhost:%s/v2/", TEST_PORT);

        FaxFile file = new FaxFile(1);

        file.setClient(client);

        assertArrayEquals(fileBytes, file.largeJpeg().getBytes());
    }

    @Test
    public void getsSmallThumbnail () throws IOException {
        byte[] fileBytes = BinaryFixtures.getTestPhaxCode();

        stubFor(get(urlEqualTo("/v2/faxes/1/file?thumbnail=s&api_secret=SECRET&api_key=KEY"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/octet")
                        .withBody(fileBytes)));

        Requests client = new Requests("KEY", "SECRET", "http://localhost:%s/v2/", TEST_PORT);

        FaxFile file = new FaxFile(1);

        file.setClient(client);

        assertArrayEquals(fileBytes, file.smallJpeg().getBytes());
    }

    @Test
    public void deletesFax () throws IOException {
        String json = Responses.json("/generic_success.json");

        stubFor(delete(urlEqualTo("/v2/faxes/1/file?api_secret=SECRET&api_key=KEY"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json; charset=utf-8")
                        .withBody(json)));

        Requests client = new Requests("KEY", "SECRET", "http://localhost:%s/v2/", TEST_PORT);

        FaxFile file = new FaxFile(1);
        file.setClient(client);
        file.delete();

        verify(deleteRequestedFor(urlEqualTo("/v2/faxes/1/file?api_secret=SECRET&api_key=KEY")));
    }
}
