package pl.mikigal.restclient.exceptions;

import java.lang.reflect.Method;

public class InvalidReturnTypeException extends RuntimeException {

    public InvalidReturnTypeException(Method method) {
        super("Method " + method.getName() + " needs to have return type RestResponse");
    }

}
