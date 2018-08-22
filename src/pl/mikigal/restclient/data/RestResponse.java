package pl.mikigal.restclient.data;

public class RestResponse {

    private final int code;
    private final String response;

    public RestResponse(int code, String response) {
        this.code = code;
        this.response = response;
    }

    public int getCode() {
        return code;
    }

    public String getResponse() {
        return response;
    }
}
