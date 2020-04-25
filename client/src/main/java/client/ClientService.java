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

    @Override
    public Future<String> addClient(String firstname, String lastname) {
        return executorService.submit(() -> {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("firstname", firstname);
            jsonObject.put("lastname", lastname);
            String payload = JSONValue.toJSONString(jsonObject);
            Request request = new Request(Service.ADD_CLIENT, payload);

            System.out.println("sending request: " + request);
            Request response = tcpClient.sendAndReceive(request);
            System.out.println("received response: " + response);
            return response.getJsonPayload();
        });
    }

    @Override
    public Future<String> addPurchase(Long bookID, Long clientID) {
        return executorService.submit(() -> {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("bookID", bookID);
            jsonObject.put("clientID", clientID);
            String payload = JSONValue.toJSONString(jsonObject);
            Request request = new Request(Service.ADD_PURCHASE, payload);

            System.out.println("sending request: " + request);
            Request response = tcpClient.sendAndReceive(request);
            System.out.println("received response: " + response);
            return response.getJsonPayload();
        });
    }

    @Override
    public Future<String> getAllBooks() {
        return executorService.submit(() -> {
            Request request = new Request(Service.GET_ALL_BOOKS, "");

            System.out.println("Sending request: " + request);
            Request response = tcpClient.sendAndReceive(request);
            System.out.println("Received response: " + response);
            return response.getJsonPayload();
        });
    }

    @Override
    public Future<String> getAllClients() {
        return executorService.submit(() -> {
            Request request = new Request(Service.GET_ALL_CLIENTS, "");

            System.out.println("Sending request: " + request);
            Request response = tcpClient.sendAndReceive(request);
            System.out.println("Received response: " + response);
            return response.getJsonPayload();
        });
    }

    @Override
    public Future<String> getAllPurchases() {
        return executorService.submit(() -> {
            Request request = new Request(Service.GET_ALL_PURCHASES, "");

            System.out.println("Sending request: " + request);
            Request response = tcpClient.sendAndReceive(request);
            System.out.println("Received response: " + response);
            return response.getJsonPayload();
        });
    }

    @Override
    public Future<String> updateBook(Long ID, String title, String author, int price) {
        return executorService.submit(() -> {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("ID", ID);
            jsonObject.put("title", title);
            jsonObject.put("author", author);
            jsonObject.put("price", price);
            String payload = JSONValue.toJSONString(jsonObject);
            Request request = new Request(Service.UPDATE_BOOK, payload);

            System.out.println("sending request: " + request);
            Request response = tcpClient.sendAndReceive(request);
            System.out.println("received response: " + response);
            return response.getJsonPayload();
        });
    }

    @Override
    public Future<String> updateClient(Long ID, String firstname, String lastname, int spendings) {
        return executorService.submit(() -> {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("ID", ID);
            jsonObject.put("firstname", firstname);
            jsonObject.put("lastname", lastname);
            jsonObject.put("spendings", spendings);
            String payload = JSONValue.toJSONString(jsonObject);
            Request request = new Request(Service.UPDATE_CLIENT, payload);

            System.out.println("sending request: " + request);
            Request response = tcpClient.sendAndReceive(request);
            System.out.println("received response: " + response);
            return response.getJsonPayload();
        });
    }

    @Override
    public Future<String> deleteBook(Long ID) {
        return executorService.submit(() -> {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("ID", ID);
            String payload = JSONValue.toJSONString(jsonObject);
            Request request = new Request(Service.DELETE_BOOK, payload);

            System.out.println("sending request: " + request);
            Request response = tcpClient.sendAndReceive(request);
            System.out.println("received response: " + response);
            return response.getJsonPayload();
        });
    }

    @Override
    public Future<String> deleteClient(Long ID) {
        return executorService.submit(() -> {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("ID", ID);
            String payload = JSONValue.toJSONString(jsonObject);
            Request request = new Request(Service.DELETE_CLIENT, payload);

            System.out.println("sending request: " + request);
            Request response = tcpClient.sendAndReceive(request);
            System.out.println("received response: " + response);
            return response.getJsonPayload();
        });
    }

    @Override
    public Future<String> deletePurchase(Long ID) {
        return executorService.submit(() -> {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("ID", ID);
            String payload = JSONValue.toJSONString(jsonObject);
            Request request = new Request(Service.DELETE_PURCHASE, payload);

            System.out.println("sending request: " + request);
            Request response = tcpClient.sendAndReceive(request);
            System.out.println("received response: " + response);
            return response.getJsonPayload();
        });
    }

    @Override
    public Future<String> filterBooksAuthor(String author) {
        return executorService.submit(() -> {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("author", author);
            String payload = JSONValue.toJSONString(jsonObject);
            Request request = new Request(Service.FILTER_BOOKS_AUTHOR, payload);

            System.out.println("sending request: " + request);
            Request response = tcpClient.sendAndReceive(request);
            System.out.println("received response: " + response);
            return response.getJsonPayload();
        });
    }

    @Override
    public Future<String> filterBooksPrice(int minPrice, int maxPrice) {
        return executorService.submit(() -> {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("minPrice", minPrice);
            jsonObject.put("maxPrice", maxPrice);
            String payload = JSONValue.toJSONString(jsonObject);
            Request request = new Request(Service.FILTER_BOOKS_PRICE, payload);

            System.out.println("sending request: " + request);
            Request response = tcpClient.sendAndReceive(request);
            System.out.println("received response: " + response);
            return response.getJsonPayload();
        });
    }

    @Override
    public Future<String> filterClientsName(String name) {
        return executorService.submit(() -> {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("name", name);
            String payload = JSONValue.toJSONString(jsonObject);
            Request request = new Request(Service.FILTER_CLIENTS_NAME, payload);

            System.out.println("sending request: " + request);
            Request response = tcpClient.sendAndReceive(request);
            System.out.println("received response: " + response);
            return response.getJsonPayload();
        });
    }

    @Override
    public Future<String> filterTopClients() {
        return executorService.submit(() -> {
            Request request = new Request(Service.FILTER_CLIENTS_NAME, "");

            System.out.println("sending request: " + request);
            Request response = tcpClient.sendAndReceive(request);
            System.out.println("received response: " + response);
            return response.getJsonPayload();
        });
    }
}
