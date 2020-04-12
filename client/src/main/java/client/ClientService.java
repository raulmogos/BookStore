package client;

import api.Request;
import api.Service;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

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
    public Future<String> addBook(String title, String author, int price) {
        //create a request
        //send request to server
        //get response
        return executorService.submit(() -> {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("title", title);
            jsonObject.put("author", author);
            jsonObject.put("price", price);
            String payload = JSONValue.toJSONString(jsonObject);
            Request request = new Request(Service.ADD_BOOK, payload);

            System.out.println("sending request: " + request);
            Request response = tcpClient.sendAndReceive(request);
            System.out.println("received response: " + response);
            return response.getJsonPayload();
        });
    }
}
