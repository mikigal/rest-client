package pl.mikigal.restclient.proxy;

import pl.mikigal.restclient.validators.RestValidator;
import pl.mikigal.restclient.validators.Validator;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class RestInvocationHandler<T> implements InvocationHandler {

    private Class<T> type;
    private Validator restValidator;

    public RestInvocationHandler(Class<T> type) {
        this.type = type;

        this.restValidator = new RestValidator(this.type);
        this.restValidator.validate();
    }


    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

        return null;
    }
}
