package com.santos.herald.ibmpracticalexam.network.exception;

import android.accounts.NetworkErrorException;
import android.content.Context;

import com.santos.herald.ibmpracticalexam.R;

public class ErrorMessageFactory {

    /*
        Empty Constructor
     */
    private ErrorMessageFactory(){
    }

    /**
     * Creates a String representing an error message.
     *
     * @param context Context needed to retrieve string resources.
     * @param exception An exception used as a condition to retrieve the correct error message.
     * @return {@link String} an error message.
     */
    public static String create(Context context, Exception exception) {
        String message;
        if (exception instanceof NetworkErrorException) {
            message = context.getString(R.string.error_network_internet);
        } else if(exception instanceof NetworkUknownHostException) {
            message = context.getString(R.string.error_host);
        } else if (exception instanceof NetworkTimeOutException) {
            message = context.getString(R.string.error_network_timeout);
        } else if (exception instanceof NullPointerException) {
           message = context.getString(R.string.error_saving);
        } else {
            message = context.getString(R.string.error_server);
        }

        return message;
    }
}