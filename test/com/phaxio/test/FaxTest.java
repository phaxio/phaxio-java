package com.phaxio.test;

import com.phaxio.util.PagedList;
import com.phaxio.Fax;
import com.phaxio.PhaxioTestAbstract;
import com.phaxio.exception.PhaxioException;
import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.Test;
import static org.junit.Assert.*;

public class FaxTest extends PhaxioTestAbstract {


    @Test
    public void sendTest() throws PhaxioException {
        List<String> phoneNumbers = new ArrayList<String>();
        phoneNumbers.add("4141234567");

        List<File> files = new ArrayList<File>();
        files.add(new File("./test/apple.pdf"));

        Map<String,Object> options = new HashMap<String,Object>();

        Long faxId = Fax.send(phoneNumbers, files, options);
        assertNotNull(faxId);
    }

    @Test
    public void cancelTest() throws PhaxioException, InterruptedException {
        List<String> phoneNumbers = new ArrayList<String>();
        phoneNumbers.add("4141234567");

        Map<String,Object> options = new HashMap<String,Object>();
        options.put("string_data", "text");
        options.put("batch", true);
        options.put("batch_delay", 60);

        Long faxId = Fax.send(phoneNumbers, null, options);
        assertNotNull(faxId);
        Thread.sleep(2000);
        Fax.cancel(String.valueOf(faxId));
    }

}
