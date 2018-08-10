package com.phaxio.integrationtests.mocked;

import com.github.tomakehurst.wiremock.junit.WireMockRule;
import com.phaxio.Phaxio;
import com.phaxio.entities.AreaCode;
import com.phaxio.helpers.Auth;
import com.phaxio.helpers.Responses;
import org.junit.Rule;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class AreaCodeRepositoryTest {
    private static final int TEST_PORT = 8089;

    @Rule
    public WireMockRule wireMockRule = new WireMockRule(TEST_PORT);

    @Test
    public void listsAreaCodes () throws IOException {
        String json = Responses.json("/list_area_codes.json");

        stubFor(get(urlEqualTo("/ver/public/area_codes"))
                .withHeader("Authorization", Auth.VALID_AUTH_MATCHER)
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json; charset=utf-8")
                        .withBody(json)));

        Phaxio phaxio = new Phaxio(Auth.VALID_KEY, Auth.VALID_SECRET, "http://localhost:%s/ver/", TEST_PORT);

        Iterable<AreaCode> codes = phaxio.publicInfo.areaCode.list();

        List<AreaCode> codeList = new ArrayList<AreaCode>();

        for (AreaCode code : codes) {
            codeList.add(code);
        }

        assertEquals(3, codeList.size());

        AreaCode code = codeList.get(2);

        assertEquals("1", code.countryCode);
        assertEquals("203", code.areaCode);
        assertEquals("Bridgeport, Danbury, Meriden", code.city);
        assertEquals("Connecticut", code.state);
        assertEquals("United States", code.country);
        assertFalse(code.tollFree);
    }
}
