package com.eli.urlshortener;

import org.junit.jupiter.api.Test;
import org.springframework.modulith.core.ApplicationModules;

public class ModularityTests {

    @Test
    void verifiesModularStructure(){
        ApplicationModules.of(UrlShortenerApplication.class).verify();
    }
}
