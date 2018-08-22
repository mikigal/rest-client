package pl.mikigal.restclient.exceptions;

import java.lang.reflect.Parameter;

public class MissingAnnotationException extends RuntimeException {

    public MissingAnnotationException(Parameter parameter) {
        super("Parameter " + parameter.getClass().getName() + "." + parameter.getName() + " has not any annotations! Every argument in REST API interface needs to have @RequestParam or @PathVariable annotation");
    }

}
