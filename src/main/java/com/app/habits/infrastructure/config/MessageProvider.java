package com.app.habits.infrastructure.config;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

@Component
public class MessageProvider {

    private final MessageSource messageSource;

    public MessageProvider(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    public String get(String code, Object... args) {
        return messageSource.getMessage(code, args, LocaleContextHolder.getLocale());
    }
}