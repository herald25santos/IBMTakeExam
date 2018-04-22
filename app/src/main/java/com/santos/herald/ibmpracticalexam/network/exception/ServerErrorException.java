package com.santos.herald.ibmpracticalexam.network.exception;

public class ServerErrorException extends Exception {

    public ServerErrorException() {
        super();
    }

    public ServerErrorException(final String message) {
        super(message);
    }

    public ServerErrorException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public ServerErrorException(final Throwable cause) {
        super(cause);
    }
}