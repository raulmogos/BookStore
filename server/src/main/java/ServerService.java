import api.Service;
import controller.Controller;
import models.Book;
import models.Client;
import models.Purchase;
import models.validation.Validator;
import models.validation.ValidatorException;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

public class ServerService implements Service {

    private ExecutorService executorService;
    private Controller controller;

    public ServerService(ExecutorService executorService, Controller controller) {
        this.executorService = executorService;
        this.controller = controller;
    }

    @Override
    public Future<String> addBook(String title, String author, int price) {
        return executorService.submit(() -> {
            try {
                this.controller.addBook(title, author, price);
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("message", "Book added successfully!");
                return JSONValue.toJSONString(jsonObject);
            } catch (ValidatorException ve) {
                return ve.toString();
            }
        });
    }

    @Override
    public Future<String> addClient(String firstname, String lastname) {
        return executorService.submit(() -> {
            try {
                this.controller.addClient(firstname, lastname);
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("message", "Client added successfully!");
                return JSONValue.toJSONString(jsonObject);
            } catch (ValidatorException ve) {
                return ve.toString();
            }
        });
    }

    @Override
    public Future<String> addPurchase(Long bookID, Long clientID) {
        return executorService.submit(() -> {
            try {
                this.controller.addPurchase(bookID, clientID);
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("message", "Purchase registered successfully!");
                return JSONValue.toJSONString(jsonObject);
            } catch (ValidatorException ve) {
                return ve.toString();
            }
        });
    }

    @Override
    public Future<String> getAllBooks() {
        return executorService.submit(() -> {
            Iterable<Book> books = this.controller.getAllBooks();
            ArrayList<String> strings = new ArrayList<>();
            books.forEach(book -> strings.add(book.toString()));
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("data", strings);
            return jsonObject.toJSONString();
        });
    }

    @Override
    public Future<String> getAllClients() {
        return executorService.submit(() -> {
            Iterable<Client> clients = this.controller.getAllClients();
            ArrayList<String> strings = new ArrayList<>();
            clients.forEach(client -> strings.add(client.toString()));
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("data", strings);
            return jsonObject.toJSONString();
        });
    }

    @Override
    public Future<String> getAllPurchases() {
        return executorService.submit(() -> {
            Iterable<Purchase> purchases = this.controller.getAllPurchases();
            ArrayList<String> strings = new ArrayList<>();
            purchases.forEach(purchase -> strings.add(purchase.toString()));
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("data", strings);
            return jsonObject.toJSONString();
        });
    }

    @Override
    public Future<String> updateBook(Long ID, String title, String author, int price) {
        return executorService.submit(() -> {
            this.controller.updateBook(ID, title, author, price);
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("message", "Book updated successfully!");
            return JSONValue.toJSONString(jsonObject);
        });
    }

    @Override
    public Future<String> updateClient(Long ID, String firstname, String lastname, int spendings) {
        return executorService.submit(() -> {
            this.controller.updateClient(ID, firstname, lastname, spendings);
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("message", "Client updated successfully!");
            return JSONValue.toJSONString(jsonObject);
        });
    }

    @Override
    public Future<String> deleteBook(Long ID) {
        return executorService.submit(() -> {
            this.controller.deleteBook(ID);
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("message", "Book deleted successfully!");
            return JSONValue.toJSONString(jsonObject);
        });
    }

    @Override
    public Future<String> deleteClient(Long ID) {
        return executorService.submit(() -> {
            this.controller.deleteClient(ID);
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("message", "Client deleted successfully!");
            return JSONValue.toJSONString(jsonObject);
        });
    }

    @Override
    public Future<String> deletePurchase(Long ID) {
        return executorService.submit(() -> {
            this.controller.deletePurchase(ID);
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("message", "Purchase deleted successfully!");
            return JSONValue.toJSONString(jsonObject);
        });
    }

    @Override
    public Future<String> filterBooksAuthor(String author) {
        return executorService.submit(() -> {
            Iterable<Book> books = this.controller.filterBookAuthor(author);
            ArrayList<String> strings = new ArrayList<>();
            books.forEach(book -> strings.add(book.toString()));
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("data", strings);
            return jsonObject.toJSONString();
        });
    }

    @Override
    public Future<String> filterBooksPrice(int minPrice, int maxPrice) {
        return executorService.submit(() -> {
            Iterable<Book> books = this.controller.filterBookPrice(minPrice, maxPrice);
            ArrayList<String> strings = new ArrayList<>();
            books.forEach(book -> strings.add(book.toString()));
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("data", strings);
            return jsonObject.toJSONString();
        });
    }

    @Override
    public Future<String> filterClientsName(String name) {
        return executorService.submit(() -> {
            Iterable<Client> clients = this.controller.filterClientsByName(name);
            ArrayList<String> strings = new ArrayList<>();
            clients.forEach(client -> strings.add(client.toString()));
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("data", strings);
            return jsonObject.toJSONString();
        });
    }

    @Override
    public Future<String> filterTopClients() {
        return executorService.submit(() -> {
            Iterable<Client> clients = this.controller.topNClientsOnMoneySpent(10);
            ArrayList<String> strings = new ArrayList<>();
            clients.forEach(client -> strings.add(client.toString()));
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("data", strings);
            return jsonObject.toJSONString();
        });
    }
}
