package pl.mikigal.restclient.example;

import pl.mikigal.restclient.RestFactory;
import pl.mikigal.restclient.data.RestResponse;

public class Example {

    public static void main(String[] args) {
        Hentai api = RestFactory.init(Hentai.class);

        RestResponse response = api.getImage("hentai", "abc", "xd");

        /*System.out.println("HTTP response code: " + response.getCode());
        System.out.println("Response value: ");
        System.out.println(response.getResponse());*/
    }

}
