package pl.mikigal.restclient.example;

import pl.mikigal.restclient.RestFactory;

public class Example {

    public static void main(String[] args) {
        Hentai api = RestFactory.loadRest(Hentai.class);
    }

}
