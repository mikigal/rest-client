package pl.mikigal.restclient.example;

import pl.mikigal.restclient.RestFactory;
import pl.mikigal.restclient.data.Header;
import pl.mikigal.restclient.data.RestResponse;

public class Example {

    public static void main(String[] args) {
        Test api = RestFactory.init(Test.class);

        RestResponse response = api.test("{}");

        System.out.println("HTTP response code: " + response.getCode());
        System.out.println("Response value: ");
        System.out.println(response.getResponse());
    }

}
