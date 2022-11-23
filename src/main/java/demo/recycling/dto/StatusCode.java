package demo.recycling.dto;

import org.springframework.stereotype.Service;

@Service
public class StatusCode {
    public static final int OK = 200;
    public static final int CREATED = 201;
    public static final int NOT_EXIST = 203;
    public static final int NO_CONTENT = 204;

    public static final int COUNT_OVER = 205;

    public static final int COUNT_OK = 206;

    public static final int BAD_REQUEST =  400;
    public static final int UNAUTHORIZED = 401;
    public static final int FORBIDDEN = 403;
    public static final int NOT_FOUND = 404;
    public static final int INTERNAL_SERVER_ERROR = 500;
    public static final int SERVICE_UNAVAILABLE = 503;
    public static final int DB_ERROR = 600;
}
