package pl.mikigal.restclient.example;

import pl.mikigal.restclient.annotations.RequestParam;
import pl.mikigal.restclient.annotations.Endpoint;
import pl.mikigal.restclient.annotations.RestApi;
import pl.mikigal.restclient.data.RestResponse;
import pl.mikigal.restclient.enums.HttpMethod;

@RestApi("https://nekobot.xyz/api/")
public interface Hentai {

    @Endpoint(name = "/image", method = HttpMethod.GET)
    public RestResponse getImage(@RequestParam("type") String type);
}
