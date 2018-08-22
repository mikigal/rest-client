package pl.mikigal.restclient.validators;

import pl.mikigal.restclient.annotations.Endpoint;
import pl.mikigal.restclient.data.RestResponse;
import pl.mikigal.restclient.exceptions.InvalidReturnTypeException;
import pl.mikigal.restclient.exceptions.MissingAnnotationException;

import java.lang.reflect.Method;

public class EndpointValidator implements Validator {

    private Method method;

    public EndpointValidator(Method method) {
        this.method = method;
    }

    @Override
    public void validate() {
        if(!method.isAnnotationPresent(Endpoint.class))
            throw new MissingAnnotationException("Method " + method.getName() + " does not have @Endpoint annotation!");

        if(!method.getReturnType().equals(RestResponse.class))
            throw new InvalidReturnTypeException(method);
    }
}
