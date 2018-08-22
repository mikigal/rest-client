package pl.mikigal.restclient.exceptions;

public class NonRestApiException extends RuntimeException {

    public NonRestApiException(Class clazz) {
        super("Type " + clazz.getName() + " has not @RestApi annotation");
    }

}
