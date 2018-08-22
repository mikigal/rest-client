package pl.mikigal.restclient.exceptions;

import java.lang.reflect.Parameter;

public class MissingAnnotationException extends RuntimeException {

    public MissingAnnotationException(String msg) {
        super(msg);
    }

}
