package com.phaxio.integrationtests.scenarios;

import com.phaxio.Phaxio;
import com.phaxio.helpers.Config;
import com.phaxio.resources.PhaxCode;
import org.junit.Test;

import java.io.IOException;

public class PhaxCodeRepositoryScenario {
    @Test
    public void createPhaxCodeAndRetrievePng () throws IOException, InterruptedException {
        Phaxio phaxio = new Phaxio(Config.get("key"), Config.get("secret"));

        PhaxCode code = phaxio.phaxCode.create("1234");

        Thread.sleep(1000);

        code.png();
    }

    @Test
    public void retrieveDefaultPhaxCodeAndPng () throws IOException, InterruptedException {
        Phaxio phaxio = new Phaxio(Config.get("key"), Config.get("secret"));

        PhaxCode code = phaxio.phaxCode.retrieve();

        Thread.sleep(1000);

        code.png();
    }
}
