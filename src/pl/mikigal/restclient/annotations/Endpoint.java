package pl.mikigal.restclient.annotations;

import pl.mikigal.restclient.enums.HttpMethod;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Endpoint {

    public String name();
    public HttpMethod method() default HttpMethod.GET;
    public String contentType() default "application/x-www-form-urlencoded; charset=UTF-8";
    public String headers() default "";
}
