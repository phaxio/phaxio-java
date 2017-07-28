package com.phaxio.integrationtests.scenarios;

import com.phaxio.Phaxio;
import com.phaxio.helpers.Config;
import org.junit.Ignore;
import org.junit.Test;

import java.io.IOException;

@Ignore
public class AccountRepositoryTests {
    @Test
    public void getsAccountStatus () throws IOException {
        Phaxio phaxio = new Phaxio(Config.get("key"), Config.get("secret"));

        phaxio.account.status();
    }
}
