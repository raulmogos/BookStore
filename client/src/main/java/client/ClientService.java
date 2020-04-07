package client;

import api.Service;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

public class ClientService implements Service {

    private ExecutorService executorService;
    private TCPClient tcpClient;

    public ClientService(ExecutorService executorService, TCPClient tcpClient) {
        this.executorService = executorService;
        this.tcpClient = tcpClient;
    }

    @Override
    public Future<String> addBook(String name) {
        return null;
    }
}
