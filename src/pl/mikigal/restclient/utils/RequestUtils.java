package pl.mikigal.restclient.utils;

import pl.mikigal.restclient.annotations.Endpoint;
import pl.mikigal.restclient.annotations.RestApi;
import pl.mikigal.restclient.data.Argument;
import pl.mikigal.restclient.data.Header;
import pl.mikigal.restclient.data.RestResponse;
import pl.mikigal.restclient.enums.ArgumentType;
import pl.mikigal.restclient.enums.HttpMethod;
import pl.mikigal.restclient.exceptions.InvalidRequestException;

import java.io.IOException;
import java.net.*;
import java.util.List;
import java.util.Scanner;

public class RequestUtils {

    private static final Header USER_AGENT_HEADER = new Header("User-Agent", "Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/39.0.2171.95 Safari/537.36");

    public static RestResponse connect(String url, HttpMethod method, Header[] headers) throws IOException {
        HttpURLConnection conn = (HttpURLConnection) new URL(url).openConnection();

        conn.setRequestMethod(method.name());

        boolean customAgent = false;
        for(Header h : headers) {
            conn.addRequestProperty(h.getName(), h.getValue());
            if(h.getName().equalsIgnoreCase("User-Agent"))
                customAgent = true;
        }

        if(!customAgent)
            conn.addRequestProperty(USER_AGENT_HEADER.getName(), USER_AGENT_HEADER.getValue());

        Scanner scanner = new Scanner(conn.getInputStream());
        String content = "";

        while (scanner.hasNextLine())
            content += scanner.nextLine();

        scanner.close();

        return new RestResponse(conn.getResponseCode(), content);
    }

    public static String parseGet(RestApi restApi, Endpoint endpoint, List<Argument> arguments) {
        String base = restApi.base();

        if(base.endsWith("/"))
            base = base.substring(0, base.length() - 1); // http://mikigal.pl

        String name = endpoint.name();
        if(!name.startsWith("/"))
            name = "/" + name; 

        if(name.endsWith("/"))
            name = name.substring(0, name.length() - 1); // /endpoint

        String url = base + name; // http://mikigal.pl/endpoint

        boolean addedParam = false;
        for(Argument a : arguments) {
            if(a.getType() == ArgumentType.PATH_VARIABLE)
                url = url.replace("{" + a.getName() + "}", escape(a.getValue().toString()));
            else {
                url += (addedParam ? "&" : "?") + a.getName() + "=" + a.getValue().toString();
                addedParam = true;
            }
        }

        return escape(url);
    }


    private static String escape(String raw) {
        try {
            return new URI(null, raw, null).toASCIIString();
        } catch (URISyntaxException e) {
            throw new InvalidRequestException(raw, e);
        }
    }
}
