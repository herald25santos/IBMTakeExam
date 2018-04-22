package com.santos.herald.ibmpracticalexam.network.response;

public class ResponseStatus {

    public static final int SUCCESSFULLY = 200;
    public static final int FAILED = 202;
    public static final int NO_RESULT = 204;
    public static final int PAGINATED_SUCCESS = 206;
    public static final int BAD_REQUEST = 400;
    public static final int UNAUTHORIZED = 401;
    public static final int FORBIDDEN = 403;
    public static final int NOT_FOUND = 404;
    public static final int INTERNAL_SERVER_ERROR = 500;
    public static final int SERVICE_UNAVAILABLE = 5003;

    public static final String INVALID_CREDENTIAL_MESSAGE = "401 Unauthorized";
    public final static String SERVER_ERROR_MESSAGE = "HTTP 500 Internal Server Error";
    public final static String BAD_REQUEST_MESSAGE = "400 Bad Request";
    public final static String CREDENTIAL_NOT_EXIST = "401 Credential Doesn't Exist";

}