package com.santos.herald.ibmpracticalexam.network.exception;

public class NetworkUknownHostException extends Exception{

    public NetworkUknownHostException() {
        super();
    }

    public NetworkUknownHostException(final String message) {
        super(message);
    }

    public NetworkUknownHostException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public NetworkUknownHostException(final Throwable cause) {
        super(cause);
    }
}