package pl.mikigal.restclient.example;

import pl.mikigal.restclient.annotations.Authorization;
import pl.mikigal.restclient.annotations.Endpoint;
import pl.mikigal.restclient.annotations.RequestBody;
import pl.mikigal.restclient.annotations.RestApi;
import pl.mikigal.restclient.data.RestResponse;
import pl.mikigal.restclient.enums.HttpMethod;

@RestApi("http://localhost:8080/")
public interface Test {

    @Endpoint(name = "/test", method = HttpMethod.POST)
    public RestResponse test(@RequestBody("test") String test, @Authorization String auth);

}
