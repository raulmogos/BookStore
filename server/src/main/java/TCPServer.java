import api.Request;
import api.TcpConnectionError;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.function.UnaryOperator;

public class TCPServer {

    private ExecutorService executorService;
    private Map<String, UnaryOperator<Request>> methodHandlers;

    public TCPServer(ExecutorService executorService) {
        this.executorService = executorService;
        this.methodHandlers = new HashMap<>();
    }

    public void addHandler(String method, UnaryOperator<Request> handler) {
        methodHandlers.put(method, handler);
    }

    public void startServer() {
        try (var serverSocket = new ServerSocket(Request.PORT)) {
            while (true) {
                Socket client = serverSocket.accept();
                executorService.submit(new ClientHandler(client));
            }
        } catch (IOException e) {
            throw new TcpConnectionError("file -> TCPServer.java", e);
        }
    }

    private class ClientHandler implements Runnable {
        private Socket socket;

        public ClientHandler(Socket client) {
            this.socket = client;
        }

        @Override
        public void run() {
            try (var is = socket.getInputStream(); var os = socket.getOutputStream()) {
                Request request = new Request();
                request.readFrom(is);
                System.out.println("SERVER received request: " + request);
                Request response = methodHandlers.get(request.getMethod()).apply(request);
                response.writeTo(os);
                System.out.println("SERVER sending response: " + response);
            } catch (IOException e) {
                throw new TcpConnectionError("error processing client", e);
            }
        }
    }
}
