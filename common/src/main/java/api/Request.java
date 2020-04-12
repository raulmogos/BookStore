package api;

import java.io.*;

public class Request {
    public static final String SUCCESS_STATUS = "SUCCESS";
    public static final String ERROR_STATUS = "ERROR";

    public static final int PORT = 1234;
    public static final String HOST = "localhost";

    private String method;
    private String jsonPayload;

    // @ Moldovanu Dragos - check this
    // https://www.w3schools.in/json/json-java/

    public Request() {
    }

    public Request(String method, String jsonPayload) {
        this.method = method;
        this.jsonPayload = jsonPayload;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getJsonPayload() {
        return jsonPayload;
    }

    public void setJsonPayload(String jsonPayload) {
        this.jsonPayload = jsonPayload;
    }

    public void writeTo(OutputStream os) throws IOException {
        os.write((method + System.lineSeparator() + jsonPayload + System.lineSeparator()).getBytes());
    }

    public void readFrom(InputStream is) throws IOException {
        var br = new BufferedReader(new InputStreamReader(is));
        method = br.readLine();
        jsonPayload = br.readLine();
    }

    @Override
    public String toString() {
        return "Request{" +
                "method='" + method + '\'' +
                ", jsonPayload='" + jsonPayload + '\'' +
                '}';
    }
}
