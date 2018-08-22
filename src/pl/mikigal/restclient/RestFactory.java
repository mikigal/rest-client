package pl.mikigal.restclient;

import javafx.util.Pair;
import pl.mikigal.restclient.data.Header;
import pl.mikigal.restclient.proxy.RestInvocationHandler;

import java.lang.reflect.Proxy;

public class RestFactory {

    public static <T> T init(Class<T> clazz, Header... headers) {
        return (T) Proxy.newProxyInstance(clazz.getClassLoader(), new Class[]{clazz}, new RestInvocationHandler<>(clazz, headers));
    }
}
