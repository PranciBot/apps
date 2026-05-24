package com.prancibot.chatserver.utils;

import com.prancibot.common.exception.EntityNotFoundException;

public class ExceptionFactory {
    public static EntityNotFoundException entityNotFoundException(String entityName, Object identifier) {
        return new EntityNotFoundException(String.format("%s with identifier %s not found", entityName, identifier));
    }
}
