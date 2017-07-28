package com.phaxio.integrationtests.scenarios;

import com.phaxio.Phaxio;
import com.phaxio.helpers.Config;
import com.phaxio.resources.Fax;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class FaxRepositoryScenario {
    @Test
    public void createsFax () throws IOException {
        Phaxio phaxio = new Phaxio(Config.get("key"), Config.get("secret"));

        Map<String, Object> options = new HashMap<String, Object>();

        options.put("to", "2088675309");

        URL testFileUrl = this.getClass().getResource("/test.pdf");
        File testFile = new File(testFileUrl.getFile());

        options.put("file", testFile);

        Fax fax = phaxio.fax.create(options);

        assertTrue(fax.id != 0);

        Fax retrievedFax = phaxio.fax.retrieve(fax.id);

        assertEquals(fax.id, retrievedFax.id);

        boolean threwException = false;

        try {
            fax.cancel();
        } catch (RuntimeException exception) {
            threwException = true;
        }

        assertTrue(threwException);

        fax.resend();

        fax.file().getBytes();

        fax.file().largeJpeg().getBytes();

        fax.file().smallJpeg().getBytes();

        fax.file().delete();

        fax.delete();
    }

    @Test
    public void testRecieveCallback () throws IOException {
        Phaxio phaxio = new Phaxio(Config.get("key"), Config.get("secret"));

        Map<String, Object> options = new HashMap<String, Object>();

        URL testFileUrl = this.getClass().getResource("/test.pdf");
        File testFile = new File(testFileUrl.getFile());

        options.put("file", testFile);
        options.put("to", "2088675309");

        phaxio.fax.testReceiveCallback(options);
    }

    @Test
    public void list () throws IOException {
        Phaxio phaxio = new Phaxio(Config.get("key"), Config.get("secret"));

        Iterable<Fax> faxes = phaxio.fax.list();

        List<Fax> faxList = new ArrayList<Fax>();

        for (Fax fax : faxes) {
            faxList.add(fax);
        }

        assertTrue(faxList.size() > 1);
    }
}
