package com.phaxio.integrationtests.scenarios;

import com.phaxio.Phaxio;
import com.phaxio.helpers.Config;
import com.phaxio.resources.PhoneNumber;
import org.junit.Ignore;
import org.junit.Test;

import java.io.IOException;

@Ignore
public class PhoneNumberRepositoryTests {
    @Test
    public void listsRetrievesAndReleasesNumbers () throws IOException, InterruptedException {
        Phaxio phaxio = new Phaxio(Config.get("key"), Config.get("secret"));

        PhoneNumber number = phaxio.phoneNumber.list().iterator().next();

        Thread.sleep(1000);

        phaxio.phoneNumber.retrieve(number.number);

        Thread.sleep(1000);

        number.release();
    }

    @Test
    public void createsNumber () throws IOException {
        Phaxio phaxio = new Phaxio(Config.get("key"), Config.get("secret"));

        phaxio.phoneNumber.create("1", "808");
    }
}
