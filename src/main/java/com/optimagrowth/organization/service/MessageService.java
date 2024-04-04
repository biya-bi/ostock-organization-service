package com.optimagrowth.organization.service;

import java.util.Locale;

public interface MessageService {
    String getMessage(String key, Locale locale, Object... args);
}
