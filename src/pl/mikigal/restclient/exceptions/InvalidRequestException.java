package pl.mikigal.restclient.exceptions;

import java.net.URISyntaxException;

public class InvalidRequestException extends RuntimeException {

    public InvalidRequestException(String raw, URISyntaxException e) {
        super("Can not parse request: " + raw + " Message:" + e.getMessage());
    }

}
