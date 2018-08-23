package pl.mikigal.restclient.exceptions;

import java.lang.reflect.Method;

public class InvalidHeadersException extends RuntimeException {

    public InvalidHeadersException(Method method) {
        super("Method " + method.getName() + " has invalid headers in @Endpoint");
    }

}
