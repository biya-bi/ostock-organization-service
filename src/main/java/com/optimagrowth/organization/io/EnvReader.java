package com.optimagrowth.organization.io;

import java.io.IOException;

import org.rainbow.io.EnvFileReader;

public final class EnvReader {

    // Fields made package-private for unit testing
    static final String SPRING_SECURITY_OAUTH2_RESOURCE_SERVER_JWT_ISSUER_URI_PROP = "spring.security.oauth2.resourceserver.jwt.issuerUri";
    static final String SPRING_DATASOURCE_URL_PROP = "spring.datasourcer.url";
    static final String SPRING_DATASOURCE_USERNAME_PROP = "spring.datasourcer.username";
    static final String SPRING_DATASOURCE_PASSWORD_PROP = "spring.datasourcer.password";

    static final String JWT_ISSUER_URI_FILE = "JWT_ISSUER_URI_FILE";
    static final String SPRING_DATASOURCE_URL_FILE = "SPRING_DATASOURCE_URL_FILE";
    static final String SPRING_DATASOURCE_USERNAME_FILE = "SPRING_DATASOURCE_USERNAME_FILE";
    static final String SPRING_DATASOURCE_PASSWORD_FILE = "SPRING_DATASOURCE_PASSWORD_FILE";

    private EnvReader() {
    }

    public static void read() throws IOException {
        System.setProperty(SPRING_SECURITY_OAUTH2_RESOURCE_SERVER_JWT_ISSUER_URI_PROP,
                EnvFileReader.read(JWT_ISSUER_URI_FILE));
        System.setProperty(SPRING_DATASOURCE_URL_PROP, EnvFileReader.read(SPRING_DATASOURCE_URL_FILE));
        System.setProperty(SPRING_DATASOURCE_USERNAME_PROP, EnvFileReader.read(SPRING_DATASOURCE_USERNAME_FILE));
        System.setProperty(SPRING_DATASOURCE_PASSWORD_PROP, EnvFileReader.read(SPRING_DATASOURCE_PASSWORD_FILE));
    }

}