package com.optimagrowth.organization.io;

import java.io.IOException;

import org.rainbow.io.EnvFileReader;

public final class EnvReader {

    // Fields made package-private for unit testing
    static final String SPRING_SECURITY_OAUTH2_RESOURCE_SERVER_JWT_ISSUER_URI_PROP = "spring.security.oauth2.resourceserver.jwt.issuerUri";
    static final String SPRING_DATASOURCE_URL_PROP = "spring.datasource.url";
    static final String SPRING_DATASOURCE_USERNAME_PROP = "spring.datasource.username";
    static final String SPRING_DATASOURCE_PASSWORD_PROP = "spring.datasource.password";

    static final String JWT_ISSUER_URI_FILE = "JWT_ISSUER_URI_FILE";
    static final String DATASOURCE_URL_FILE = "DATASOURCE_URL_FILE";
    static final String DATASOURCE_USERNAME_FILE = "DATASOURCE_USERNAME_FILE";
    static final String DATASOURCE_PASSWORD_FILE = "DATASOURCE_PASSWORD_FILE";

    private EnvReader() {
    }

    public static void read() throws IOException {
        System.setProperty(SPRING_SECURITY_OAUTH2_RESOURCE_SERVER_JWT_ISSUER_URI_PROP,
                EnvFileReader.read(JWT_ISSUER_URI_FILE));
        System.setProperty(SPRING_DATASOURCE_URL_PROP, EnvFileReader.read(DATASOURCE_URL_FILE));
        System.setProperty(SPRING_DATASOURCE_USERNAME_PROP, EnvFileReader.read(DATASOURCE_USERNAME_FILE));
        System.setProperty(SPRING_DATASOURCE_PASSWORD_PROP, EnvFileReader.read(DATASOURCE_PASSWORD_FILE));
    }

}
