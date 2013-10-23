package com.phaxio.test;

import com.phaxio.util.PagedList;
import com.phaxio.Fax;
import com.phaxio.PhaxioTestAbstract;
import com.phaxio.exception.PhaxioException;
import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.Test;
import static org.junit.Assert.*;


/**
 * MAKE SURE YOUR CLOCK IS SYNCHED PROPERLY BEFORE RUNNING THESE TESTS
 * @author jnankin
 */
public class FaxTest extends PhaxioTestAbstract {


    @Test
    public void sendTest() throws PhaxioException {
        assertNotNull(sendATestFax());
    }

    private long sendATestFax() throws PhaxioException {
        List<String> phoneNumbers = new ArrayList<String>();
        phoneNumbers.add("4141234567");

        Map<String,Object> options = new HashMap<String,Object>();
        options.put("string_data", "test sending a fax");

        return Fax.send(phoneNumbers, null, options);
    }


    @Test
    public void listTest() throws PhaxioException, InterruptedException {
        //we offset the start date by 20 seconds as a buffer for lagging clocks
        long start = System.currentTimeMillis() - 20000;

        Long faxId = sendATestFax();

        //wait until it completes
        Thread.sleep(1000);
        long end = System.currentTimeMillis();

        PagedList<Fax> list = Fax.list(null);

        boolean found = false;
        for (Fax f : list.getElements()){
            if (f.getId() == faxId){
                found = true;
                break;
            }
        }
        assertTrue(found);

        Thread.sleep(1000);
        list = Fax.list(new Date(start), new Date(end), null);
        found = false;
        for (Fax f : list.getElements()){
            if (f.getId() == faxId){
                found = true;
            }
        }
    }

    @Test
    public void cancelTest() throws PhaxioException, InterruptedException {
        List<String> phoneNumbers = new ArrayList<String>();
        phoneNumbers.add("4141234567");

        Map<String,Object> options = new HashMap<String,Object>();
        options.put("string_data", "text");
        options.put("batch", true);
        options.put("batch_delay", 60);

        Thread.sleep(2000);
        Long faxId = Fax.send(phoneNumbers, null, options);
        assertNotNull(faxId);

        Thread.sleep(2000);     //gotta sleep here so we dont trigger any rate limiting problems.
        Fax.cancel(String.valueOf(faxId));
    }

}
