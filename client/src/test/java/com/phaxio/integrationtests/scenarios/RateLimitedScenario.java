package com.phaxio.integrationtests.scenarios;

import org.junit.Before;

public class RateLimitedScenario {
    @Before
    public void sleep () throws InterruptedException {
        Thread.sleep(1000);
    }
}
