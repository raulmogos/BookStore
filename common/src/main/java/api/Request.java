package api;

import java.io.*;

public class Request {
    public static final int PORT = 1234;
    public static final String HOST = "localhost";

    private String method;
    private String payload; // todo: change this type

    public Request() {
    }

    public Request(String method, String payload) {
        this.method = method;
        this.payload = payload;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getPayload() {
        return payload;
    }

    public void setPayload(String payload) {
        this.payload = payload;
    }

    public void writeTo(OutputStream os) throws IOException {
        // todo
        os.write((method + System.lineSeparator() + payload + System.lineSeparator()).getBytes());
    }

    public void readFrom(InputStream is) throws IOException {
        // todo
        var br = new BufferedReader(new InputStreamReader(is));
        method = br.readLine();
        payload = br.readLine();
    }

    @Override
    public String toString() {
        return "Message{" +
                "header='" + method + '\'' +
                ", body='" + payload + '\'' +
                '}';
    }
}
