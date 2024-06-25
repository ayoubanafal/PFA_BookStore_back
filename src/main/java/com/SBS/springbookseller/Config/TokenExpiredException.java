package com.SBS.springbookseller.Config;

public class TokenExpiredException extends RuntimeException {
    public TokenExpiredException(String message, Throwable cause) {
        super(message, cause);
    }
}

