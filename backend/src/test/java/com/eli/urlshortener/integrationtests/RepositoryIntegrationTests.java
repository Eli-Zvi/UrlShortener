package com.eli.urlshortener.integrationtests;

import com.eli.urlshortener.urlanalytics.services.AnalyticsService;
import com.eli.urlshortener.urlshortener.services.UrlService;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.junit.jupiter.api.Assertions.assertTrue;
//TODO: Add url repository/service integration test
/*
@SpringBootTest(
        properties = {"spring.jpa.hibernate.ddl-auto=create-drop",
            "spring.jpa.properties.hibernate.dialect= org.hibernate.dialect.PostgreSQLDialect"}
)
@Testcontainers
public class RepositoryIntegrationTests {

    @Container
    @ServiceConnection
    private static PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>(
            "postgres:16-alpine").withDatabaseName("testdb").withUsername("test").withPassword("test");

    @MockitoBean
    private AnalyticsService analyticsService;

    @Autowired
    private UrlService urlService;


    @Test
    @DisplayName("""
            Check docker image inits successfully
            """)
    void initTest(){
        assertTrue(postgreSQLContainer.isRunning());
    }

    @Test
    @DisplayName("""
            """)
    void test1(){

    }

    @AfterAll
    static void afterAll(){
        postgreSQLContainer.stop();
    }
}
*/