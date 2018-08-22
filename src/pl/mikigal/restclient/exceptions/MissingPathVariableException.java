package pl.mikigal.restclient.exceptions;

import pl.mikigal.restclient.annotations.Endpoint;
import pl.mikigal.restclient.annotations.PathVariable;

public class MissingPathVariableException extends RuntimeException {

    public MissingPathVariableException(Endpoint endpoint, PathVariable pathVariable) {
        super("Missing {" + pathVariable.value() + "} in Endpoint: " + endpoint.name());
    }

}
