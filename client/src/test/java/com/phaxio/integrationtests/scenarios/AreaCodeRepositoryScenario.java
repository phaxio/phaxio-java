package com.phaxio.integrationtests.scenarios;

import com.phaxio.Phaxio;
import com.phaxio.entities.AreaCode;
import com.phaxio.helpers.Config;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertTrue;

public class AreaCodeRepositoryScenario {
    @Test
    public void listsAreaCodes () throws IOException {
        Phaxio phaxio = new Phaxio(Config.get("key"), Config.get("secret"));

        Iterable<AreaCode> codes = phaxio.publicInfo.areaCode.list();

        List<AreaCode> codeList = new ArrayList<AreaCode>();

        for (AreaCode code : codes) {
            codeList.add(code);
        }

        assertTrue(codeList.size() > 5);
    }
}
