package com.eli.urlshortener.integrationtests;

import com.eli.urlshortener.urlshortener.googlesafebrowsing.SafeBrowsingClient;
import com.eli.urlshortener.urlshortener.googlesafebrowsing.ThreatOptions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class SafeBrowsingClientTest {

    @Autowired
    private SafeBrowsingClient safeBrowsingClient;

    @Test
    void test1(){
        assertNull(safeBrowsingClient.findThreats("https://youtube.com").matches());
        assertEquals(ThreatOptions.ThreatType.MALWARE,
                safeBrowsingClient.
                        findThreats("https://testsafebrowsing.appspot.com/s/malware.html")
                        .matches()
                        .getFirst()
                        .threatType());
    }
}
