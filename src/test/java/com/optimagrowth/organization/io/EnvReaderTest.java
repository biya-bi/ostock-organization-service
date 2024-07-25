package com.optimagrowth.organization.io;

import static com.optimagrowth.organization.io.EnvReader.JWT_ISSUER_URI_FILE;
import static com.optimagrowth.organization.io.EnvReader.SPRING_DATASOURCE_PASSWORD_FILE;
import static com.optimagrowth.organization.io.EnvReader.SPRING_DATASOURCE_PASSWORD_PROP;
import static com.optimagrowth.organization.io.EnvReader.SPRING_DATASOURCE_URL_FILE;
import static com.optimagrowth.organization.io.EnvReader.SPRING_DATASOURCE_URL_PROP;
import static com.optimagrowth.organization.io.EnvReader.SPRING_DATASOURCE_USERNAME_FILE;
import static com.optimagrowth.organization.io.EnvReader.SPRING_DATASOURCE_USERNAME_PROP;
import static com.optimagrowth.organization.io.EnvReader.SPRING_SECURITY_OAUTH2_RESOURCE_SERVER_JWT_ISSUER_URI_PROP;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mockStatic;

import java.io.IOException;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.Test;
import org.rainbow.io.EnvFileReader;

class EnvReaderTest {

    @Test
    void read_AllEnvironmentVariablePropertiesAreSet_SetSystemProperties() throws IOException {
        var jwtIssuerUri = RandomStringUtils.random(10);
        var dataSourceUrl = RandomStringUtils.random(10);
        var dataSourceUserName = RandomStringUtils.random(10);
        var dataSourcePassword = RandomStringUtils.random(10);

        try (var envFileReaderMockedStatic = mockStatic(EnvFileReader.class)) {
            envFileReaderMockedStatic.when(() -> EnvFileReader.read(JWT_ISSUER_URI_FILE)).thenReturn(jwtIssuerUri);
            envFileReaderMockedStatic.when(() -> EnvFileReader.read(SPRING_DATASOURCE_URL_FILE))
                    .thenReturn(dataSourceUrl);
            envFileReaderMockedStatic.when(() -> EnvFileReader.read(SPRING_DATASOURCE_USERNAME_FILE))
                    .thenReturn(dataSourceUserName);
            envFileReaderMockedStatic.when(() -> EnvFileReader.read(SPRING_DATASOURCE_PASSWORD_FILE))
                    .thenReturn(dataSourcePassword);

            EnvReader.read();

            assertEquals(jwtIssuerUri, System.getProperty(SPRING_SECURITY_OAUTH2_RESOURCE_SERVER_JWT_ISSUER_URI_PROP));
            assertEquals(dataSourceUrl,
                    System.getProperty(SPRING_DATASOURCE_URL_PROP));
            assertEquals(dataSourceUserName,
                    System.getProperty(SPRING_DATASOURCE_USERNAME_PROP));
            assertEquals(dataSourcePassword,
                    System.getProperty(SPRING_DATASOURCE_PASSWORD_PROP));
        }
    }

}
