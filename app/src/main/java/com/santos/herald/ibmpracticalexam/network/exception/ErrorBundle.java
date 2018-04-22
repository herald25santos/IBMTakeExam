package com.santos.herald.ibmpracticalexam.network.exception;

public interface ErrorBundle {
    Exception getException();

    String getErrorMessage();
}