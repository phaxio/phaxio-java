package com.phaxio.integrationtests.scenarios;

import com.phaxio.Phaxio;
import com.phaxio.helpers.Config;
import org.junit.Test;

import java.io.IOException;

public class AccountRepositoryScenario {
    @Test
    public void getsAccountStatus () throws IOException {
        Phaxio phaxio = new Phaxio(Config.get("key"), Config.get("secret"));

        phaxio.account.status();
    }
}
