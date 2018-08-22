package pl.mikigal.restclient.validators;

import pl.mikigal.restclient.annotations.Endpoint;
import pl.mikigal.restclient.annotations.PathVariable;
import pl.mikigal.restclient.annotations.RequestParam;
import pl.mikigal.restclient.exceptions.MissingAnnotationException;
import pl.mikigal.restclient.exceptions.MissingPathVariableException;
import pl.mikigal.restclient.exceptions.TooMuchAnnotationsException;

import java.lang.annotation.Annotation;
import java.lang.reflect.Parameter;

public class ArgumentValidator implements Validator {

    private Endpoint endpoint;
    private Parameter parameter;
    private Annotation[] annotations;

    public ArgumentValidator(Endpoint endpoint, Parameter parameter, Annotation[] annotations) {
        this.endpoint = endpoint;
        this.parameter = parameter;
        this.annotations = annotations;
    }

    @Override
    public boolean validate() {
        if(!isPathVariable() && !isRequestParam())
            throw new MissingAnnotationException(this.parameter);

        if(this.isPathVariable() && this.isRequestParam())
            throw new TooMuchAnnotationsException(this.parameter);

        if(this.isPathVariable() && endpoint.name().contains("{" + get(PathVariable.class).value() + "}"))
            throw new MissingPathVariableException(endpoint, get(PathVariable.class));

        return false;
    }

    private <T> T get(Class<T> type) {
        for(Annotation a : annotations)
            if(a.annotationType().equals(type))
                return (T) a;

        return null;
    }

    private boolean isPathVariable() {
        return get(PathVariable.class) != null;
    }

    private boolean isRequestParam() {
        return get(RequestParam.class) != null;
    }
}
