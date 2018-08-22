package pl.mikigal.restclient.validators;

import pl.mikigal.restclient.annotations.Endpoint;
import pl.mikigal.restclient.annotations.PathVariable;
import pl.mikigal.restclient.annotations.RequestParam;
import pl.mikigal.restclient.exceptions.IllegalParameterTypeException;
import pl.mikigal.restclient.exceptions.MissingAnnotationException;
import pl.mikigal.restclient.exceptions.MissingPathVariableException;
import pl.mikigal.restclient.exceptions.TooMuchAnnotationsException;

import java.lang.annotation.Annotation;
import java.lang.reflect.Parameter;

public class ArgumentValidator implements Validator {

    private Class restApi;
    private Endpoint endpoint;
    private Parameter parameter;

    public ArgumentValidator(Class restApi, Endpoint endpoint, Parameter parameter) {
        this.restApi = restApi;
        this.endpoint = endpoint;
        this.parameter = parameter;
    }

    @Override
    public void validate() {
        if(!this.isPathVariable() && !this.isRequestParam())
            throw new MissingAnnotationException("Parameter " + restApi.getName() + "." + parameter.getName() + " has not any annotations! Every argument in REST API interface needs to have @RequestParam or @PathVariable annotation");

        if(this.isPathVariable() && this.isRequestParam())
            throw new TooMuchAnnotationsException(this.parameter);

        if(this.isPathVariable() && !endpoint.name().contains("{" + getAsPathVariable().value() + "}"))
            throw new MissingPathVariableException(endpoint, getAsPathVariable());

        if(!(parameter.getType().isPrimitive() || parameter.getType().equals(String.class)))
            throw new IllegalParameterTypeException(parameter);

    }

    public PathVariable getAsPathVariable() {
        return parameter.getAnnotation(PathVariable.class);
    }

    private boolean isPathVariable() {
        return parameter.isAnnotationPresent(PathVariable.class);
    }

    private boolean isRequestParam() {
        return parameter.isAnnotationPresent(RequestParam.class);
    }
}
