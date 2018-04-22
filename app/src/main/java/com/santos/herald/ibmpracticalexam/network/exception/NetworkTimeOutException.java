package com.santos.herald.ibmpracticalexam.network.exception;

public class NetworkTimeOutException extends Exception {

    public NetworkTimeOutException() {
        super();
    }

    public NetworkTimeOutException(final String message) {
        super(message);
    }

    public NetworkTimeOutException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public NetworkTimeOutException(final Throwable cause) {
        super(cause);
    }
}