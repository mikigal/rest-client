package pl.mikigal.restclient.validators;

import pl.mikigal.restclient.annotations.RestApi;
import pl.mikigal.restclient.exceptions.NonInterfaceException;
import pl.mikigal.restclient.exceptions.NonRestApiException;

public class RestValidator implements Validator {

    private Class clazz;

    public RestValidator(Class clazz) {
        this.clazz = clazz;
    }

    @Override
    public void validate() {
        if(!clazz.isInterface())
            throw new NonInterfaceException(clazz);

        if(!clazz.isAnnotationPresent(RestApi.class))
            throw new NonRestApiException(clazz);
    }
}
