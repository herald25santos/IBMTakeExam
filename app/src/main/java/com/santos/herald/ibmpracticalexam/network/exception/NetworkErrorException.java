package com.santos.herald.ibmpracticalexam.network.exception;

public class NetworkErrorException extends Exception {

    public NetworkErrorException() {
        super();
    }

    public NetworkErrorException(final String message) {
        super(message);
    }

    public NetworkErrorException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public NetworkErrorException(final Throwable cause) {
        super(cause);
    }

}