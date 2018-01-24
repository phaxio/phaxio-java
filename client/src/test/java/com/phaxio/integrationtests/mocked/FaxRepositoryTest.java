package com.phaxio.integrationtests.mocked;

import com.github.tomakehurst.wiremock.junit.WireMockRule;
import com.phaxio.Phaxio;
import com.phaxio.entities.Recipient;
import com.phaxio.helpers.Responses;
import com.phaxio.resources.Fax;
import com.phaxio.resources.FileStream;
import com.phaxio.services.Requests;
import org.junit.Rule;
import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class FaxRepositoryTest {
    private static final int TEST_PORT = 8089;

    @Rule
    public WireMockRule wireMockRule = new WireMockRule(TEST_PORT);

    @Test
    public void createsFaxWithSingleFile () throws IOException {
        String json = Responses.json("/fax_send.json");

        stubFor(post(urlEqualTo("/v2/faxes"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json; charset=utf-8")
                        .withBody(json)));

        Phaxio phaxio = new Phaxio("KEY", "SECRET", "http://localhost:%s/v2/", TEST_PORT);

        Map<String, Object> options = new HashMap<String, Object>();

        options.put("to", "2088675309");

        URL testFileUrl = this.getClass().getResource("/test.pdf");
        File testFile = new File(testFileUrl.getFile());

        options.put("file", testFile);

        ArrayList<String> urls = new ArrayList<String>();
        urls.add("http://google.com");

        options.put("content_url[]", urls);

        Fax fax = phaxio.fax.create(options);

        verify(postRequestedFor(urlEqualTo("/v2/faxes"))
                .withHeader("Content-Type", containing("multipart/form-data;"))
                .withRequestBody(containing("test.pdf"))
                .withRequestBody(containing("file"))
                .withRequestBody(containing("to"))
                .withRequestBody(containing("2088675309"))
                .withRequestBody(containing("content_url[]"))
                .withRequestBody(containing("http://google.com"))
        );

        assertTrue(fax.id == 1234);
    }

    @Test
    public void createsFaxWithMultipleFiles () throws IOException {
        String json = Responses.json("/fax_send.json");

        stubFor(post(urlEqualTo("/v2/faxes"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json; charset=utf-8")
                        .withBody(json)));

        Phaxio phaxio = new Phaxio("KEY", "SECRET", "http://localhost:%s/v2/", TEST_PORT);

        Map<String, Object> options = new HashMap<String, Object>();

        options.put("to", "2088675309");

        URL testFileUrl1 = this.getClass().getResource("/test.pdf");
        URL testFileUrl2 = this.getClass().getResource("/test2.pdf");
        URL testFileUrl3 = this.getClass().getResource("/test3.pdf");
        File testFile = new File(testFileUrl1.getFile());

        File testFile3 = new File(testFileUrl3.getFile());
        FileStream fileStream = new FileStream(new FileInputStream(testFile3), testFile3.getName());
        Collection<Object> files = Arrays.asList(testFile, testFileUrl2.getFile(), fileStream);

        options.put("file[]", files);

        ArrayList<String> urls = new ArrayList<String>();
        urls.add("http://google.com");

        options.put("content_url[]", urls);

        Fax fax = phaxio.fax.create(options);

        verify(postRequestedFor(urlEqualTo("/v2/faxes"))
                .withHeader("Content-Type", containing("multipart/form-data;"))
                .withRequestBody(containing("test.pdf"))
                .withRequestBody(containing("test2.pdf"))
                .withRequestBody(containing("test3.pdf"))
                .withRequestBody(containing("file[]"))
                .withRequestBody(containing("to"))
                .withRequestBody(containing("2088675309"))
                .withRequestBody(containing("content_url[]"))
                .withRequestBody(containing("http://google.com"))
        );

        assertTrue(fax.id == 1234);
    }

    @Test
    public void retrievesFax () throws IOException, ParseException {
        String json = Responses.json("/fax_info.json");

        stubFor(get(urlEqualTo("/v2/faxes/1?api_secret=SECRET&api_key=KEY"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json; charset=utf-8")
                        .withBody(json)));

        Phaxio phaxio = new Phaxio("KEY", "SECRET", "http://localhost:%s/v2/", TEST_PORT);

        Fax fax = phaxio.fax.retrieve(1);

        DateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");

        Date createdAt = format.parse("2015-09-02T11:28:02-0500");
        Date completedAt = format.parse("2015-09-02T11:28:54-0500");

        assertEquals(123456, fax.id);
        assertEquals("sent", fax.direction);
        assertEquals(3, fax.pageCount);
        assertEquals("success", fax.status);
        assertTrue(fax.isTest);
        assertEquals(createdAt, fax.createdAt);
        assertEquals(completedAt, fax.completedAt);
        assertEquals(21, fax.costInCents);
        assertEquals("123", fax.fromNumber);
        assertEquals("1234", fax.tags.get("order_id"));
        assertEquals("321", fax.toNumber);
        assertEquals("Caller", fax.callerId);
        assertEquals(42, fax.errorId);
        assertEquals("error_type", fax.errorType);
        assertEquals("error_message", fax.errorMessage);

        Recipient recipient = fax.recipients.get(0);

        assertEquals("+14141234567", recipient.phoneNumber);
        assertEquals(completedAt, recipient.completedAt);
        assertEquals("success", recipient.status);
        assertEquals(41, recipient.errorId);
        assertEquals("recipient_error_type", recipient.errorType);
        assertEquals("recipient_error_message", recipient.errorMessage);
        assertEquals(3, recipient.retryCount);
        assertEquals(14400, recipient.bitrate);
        assertEquals(8040, recipient.resolution);
    }

    @Test
    public void testRecieveCallback () throws IOException {
        String json = Responses.json("/generic_success.json");

        stubFor(post(urlEqualTo("/v2/faxes"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json; charset=utf-8")
                        .withBody(json)));

        Phaxio phaxio = new Phaxio("KEY", "SECRET", "http://localhost:%s/v2/", TEST_PORT);

        Map<String, Object> options = new HashMap<String, Object>();

        URL testFileUrl = this.getClass().getResource("/test.pdf");
        File testFile = new File(testFileUrl.getFile());

        options.put("file", testFile);
        options.put("to", "2088675309");

        phaxio.fax.testReceiveCallback(options);

        verify(postRequestedFor(urlEqualTo("/v2/faxes"))
                .withHeader("Content-Type", containing("multipart/form-data;"))
                .withRequestBody(containing("test.pdf"))
                .withRequestBody(containing("file"))
                .withRequestBody(containing("to"))
                .withRequestBody(containing("2088675309"))
                .withRequestBody(containing("direction"))
                .withRequestBody(containing("received"))
        );
    }

    @Test
    public void deletesFax () throws IOException {
        String json = Responses.json("/generic_success.json");

        stubFor(delete(urlEqualTo("/v2/faxes/1?api_secret=SECRET&api_key=KEY"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json; charset=utf-8")
                        .withBody(json)));

        Requests client = new Requests("KEY", "SECRET", "http://localhost:%s/v2/", TEST_PORT);

        Fax fax = new Fax();
        fax.id = 1;
        fax.setClient(client);

        fax.delete();

        verify(deleteRequestedFor(urlEqualTo("/v2/faxes/1?api_secret=SECRET&api_key=KEY")));
    }

    @Test
    public void listsFax () throws IOException {
        String json = Responses.json("/fax_list.json");

        stubFor(get(urlEqualTo("/v2/faxes?api_secret=SECRET&api_key=KEY"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json; charset=utf-8")
                        .withBody(json)));

        Phaxio phaxio = new Phaxio("KEY", "SECRET", "http://localhost:%s/v2/", TEST_PORT);

        Iterable<Fax> faxes = phaxio.fax.list();

        List<Fax> faxList = new ArrayList<Fax>();

        for (Fax fax : faxes) {
            faxList.add(fax);
        }

        assertEquals(3, faxList.size());
    }
}
