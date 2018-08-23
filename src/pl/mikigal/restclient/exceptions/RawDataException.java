package pl.mikigal.restclient.exceptions;

import java.lang.reflect.Method;

public class RawDataException extends RuntimeException {

    public RawDataException(Method method) {
        super("Method " + method.getName() + "has @RawData and @RequestBody parameters");
    }

}
