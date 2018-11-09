package com.chat.server.core.exception;

public class ChatClientException extends RuntimeException {

    private static final long serialVersionUID = 6402209473229756857L;

    public ChatClientException() {
        super();
    }

    public ChatClientException(String message) {
        super(message);
    }

    public ChatClientException(String message, Throwable cause) {
        super(message, cause);
    }

    public ChatClientException(Throwable cause) {
        super(cause);
    }

    protected ChatClientException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
