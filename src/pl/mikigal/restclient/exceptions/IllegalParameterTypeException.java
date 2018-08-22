package pl.mikigal.restclient.exceptions;

import java.lang.reflect.Parameter;

public class IllegalParameterTypeException extends RuntimeException {

    public IllegalParameterTypeException(Parameter parameter) {
        super("Parameter"  + parameter.getName() + " must be primitive type or String");
    }

}
