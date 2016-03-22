package com.lexmark.apollo.api.util;

public class ApolloServiceException extends Exception {

  
    private static final long serialVersionUID = 8813599756448573057L;

    public ApolloServiceException() {
    }

    public ApolloServiceException(String message, Throwable cause, boolean enableSuppression,
            boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public ApolloServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    public ApolloServiceException(String message) {
        super(message);
    }

    public ApolloServiceException(Throwable cause) {
        super(cause);
    }
    

    
    
}
