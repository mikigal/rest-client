package pl.mikigal.restclient.exceptions;

import java.lang.reflect.Method;

public class InvalidGetRequestException extends RuntimeException {

    public InvalidGetRequestException(Method method) {
        super("Method " + method.getName() + " is GET but has @RawData or @RequestBody parameter!");
    }

}
