package pl.mikigal.restclient.example;

import pl.mikigal.restclient.annotations.*;
import pl.mikigal.restclient.data.RestResponse;
import pl.mikigal.restclient.enums.HttpMethod;

@RestApi("http://localhost:8080/")
public interface Test {

    @Endpoint(name = "/test", method = HttpMethod.POST, contentType = "text/json")
    public RestResponse test(@RawData String test);

}
