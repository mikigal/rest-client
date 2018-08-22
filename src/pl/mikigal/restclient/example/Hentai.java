package pl.mikigal.restclient.example;

import pl.mikigal.restclient.annotations.PathVariable;
import pl.mikigal.restclient.annotations.RequestParam;
import pl.mikigal.restclient.annotations.Endpoint;
import pl.mikigal.restclient.annotations.RestApi;
import pl.mikigal.restclient.data.RestResponse;
import pl.mikigal.restclient.enums.HttpMethod;

@RestApi(base = "https://nekobot.xyz/api/")
public interface Hentai {

    @Endpoint(name = "/{test}/image", method = HttpMethod.GET)
    public RestResponse getImage(@RequestParam("type") String type, @RequestParam("someParam") String param, @PathVariable("test") String lol);
}
