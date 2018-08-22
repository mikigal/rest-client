package pl.mikigal.restclient.exceptions;

import java.lang.reflect.Parameter;

public class TooMuchAnnotationsException extends RuntimeException {

    public TooMuchAnnotationsException(Parameter parameter) {
        super("Parameter " + parameter.getClass().getName() + "." + parameter.getName() + " has @RequestParam and @PathVariable annotations! ");
    }

}
