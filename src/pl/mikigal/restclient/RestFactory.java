package pl.mikigal.restclient;

import pl.mikigal.restclient.proxy.RestInvocationHandler;

import java.lang.reflect.Proxy;

public class RestFactory {

    public static <T> T loadRest(Class<T> clazz) {
        return (T) Proxy.newProxyInstance(clazz.getClassLoader(), new Class[]{clazz}, new RestInvocationHandler<>(clazz));
    }
}
