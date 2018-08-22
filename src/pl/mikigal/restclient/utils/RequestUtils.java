package pl.mikigal.restclient.utils;

import pl.mikigal.restclient.annotations.Endpoint;
import pl.mikigal.restclient.annotations.RestApi;
import pl.mikigal.restclient.data.Argument;
import pl.mikigal.restclient.enums.ArgumentType;

import java.util.List;

public class RequestUtils {

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
                url += (addedParam ? "&" : "?") + a.getName() + "=" + escape(a.getValue().toString());
                addedParam = true;
            }
        }

        return url;
    }


    private static String escape(String raw) {
        //TODO: Encode params
        return raw;
    }
}
