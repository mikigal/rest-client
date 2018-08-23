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
import java.io.OutputStream;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Scanner;
import java.util.StringJoiner;

public class RequestUtils {

    public static RestResponse connect(String url, RestApi restApi, Endpoint endpoint, List<Argument> post,String rawData, String auth, Header[] headers, List<Header> endpointHeaders) throws IOException {
        HttpURLConnection conn = (HttpURLConnection) new URL(url).openConnection();

        conn.setRequestMethod(endpoint.method().name());
        conn.addRequestProperty("User-Agent", restApi.userAgent());
        conn.addRequestProperty("Content-Type", endpoint.contentType());

        if(auth != null)
            conn.addRequestProperty("Authorization", auth);

        for(Header h : endpointHeaders)
            if(conn.getRequestProperty(h.getName()) == null)
                conn.setRequestProperty(h.getName(), h.getValue());

        for(Header h : headers)
            if(conn.getRequestProperty(h.getName()) == null)
                conn.addRequestProperty(h.getName(), h.getValue());

        if(endpoint.method() == HttpMethod.POST) {
            byte[] out;
            if(rawData == null) {
                StringJoiner sj = new StringJoiner("&");
                for(Argument a : post)
                    sj.add(URLEncoder.encode(a.getName(), "UTF-8") + "=" + URLEncoder.encode(a.getValue().toString(), "UTF-8"));

                out = sj.toString().getBytes(StandardCharsets.UTF_8);
            }
            else
                out = rawData.getBytes(StandardCharsets.UTF_8);

            conn.setFixedLengthStreamingMode(out.length);
            conn.setDoInput(true);
            conn.setDoOutput(true);
            conn.connect();

            try(OutputStream os = conn.getOutputStream()) {
                os.write(out);
            }
        }

        Scanner scanner = new Scanner(conn.getInputStream());
        String content = "";

        while (scanner.hasNextLine())
            content += scanner.nextLine();

        scanner.close();

        return new RestResponse(conn.getResponseCode(), content);
    }

    public static String parse(RestApi restApi, Endpoint endpoint, List<Argument> arguments) {
        String base = restApi.value();

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
