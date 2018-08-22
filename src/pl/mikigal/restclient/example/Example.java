package pl.mikigal.restclient.example;

import pl.mikigal.restclient.RestFactory;
import pl.mikigal.restclient.validators.RestValidator;

public class Example {

    public static void main(String[] args) {
        new RestValidator(Example.class).validate();
        Hentai api = RestFactory.loadRest(Hentai.class);
    }

}
