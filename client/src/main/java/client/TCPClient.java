package client;

import api.Request;
import api.TcpConnectionError;

import java.io.IOException;
import java.net.Socket;

public class TCPClient {
    public Request sendAndReceive(Request request) {
        try (
                var socket = new Socket(Request.HOST, Request.PORT);
                var is = socket.getInputStream();
                var os = socket.getOutputStream()
        ) {
            System.out.println("sendAndReceive - sending request: " + request);
            request.writeTo(os);
            Request response = new Request();
            response.readFrom(is);
            System.out.println("sendAndReceive - received response: " + response);
            return response;
        } catch (IOException e) {
            throw new TcpConnectionError("error connection to server " + e.getMessage(), e);

        }
    }
}
