package pl.mikigal.restclient.exceptions;

public class NonInterfaceException extends RuntimeException {

    public NonInterfaceException(Class clazz) {
        super("Type " + clazz.getName() + " is not an interface");
    }

}
