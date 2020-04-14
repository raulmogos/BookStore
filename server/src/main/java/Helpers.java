import api.Request;
import api.Service;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class Helpers {
    public static void addHandlersToServer(TCPServer server, Service service) {
        // ADD BOOK
        server.addHandler(Service.ADD_BOOK, (request) -> {
            try {
                String payload = request.getJsonPayload();
                JSONObject jsonObject = (JSONObject) JSONValue.parse(payload);
                String title = jsonObject.get("title").toString();
                String author = jsonObject.get("author").toString();
                int price = Integer.parseInt(jsonObject.get("price").toString());
                Future<String> response = service.addBook(title, author, price);
                return new Request(Request.SUCCESS_STATUS, response.get());
            } catch (Exception e) {
                e.printStackTrace();
                return new Request(Request.ERROR_STATUS, e.toString());
            }
        });
        // ADD CLIENT
        server.addHandler(Service.ADD_CLIENT, (request) -> {
            try {
                String payload = request.getJsonPayload();
                JSONObject jsonObject = (JSONObject) JSONValue.parse(payload);
                String firstname = jsonObject.get("firstname").toString();
                String lastname = jsonObject.get("lastname").toString();
                Future<String> response = service.addClient(firstname, lastname);
                return new Request(Request.SUCCESS_STATUS, response.get());
            } catch (Exception e) {
                e.printStackTrace();
                return new Request(Request.ERROR_STATUS, e.toString());
            }
        });
        // ADD PURCHASE
        server.addHandler(Service.ADD_PURCHASE, (request) -> {
            try {
                String payload = request.getJsonPayload();
                JSONObject jsonObject = (JSONObject) JSONValue.parse(payload);
                Long bookID = Long.parseLong(jsonObject.get("bookID").toString());
                Long clientID = Long.parseLong(jsonObject.get("clientID").toString());
                Future<String> response = service.addPurchase(bookID, clientID);
                return new Request(Request.SUCCESS_STATUS, response.get());
            } catch (Exception e) {
                e.printStackTrace();
                return new Request(Request.ERROR_STATUS, e.toString());
            }
        });
        // GET ALL BOOKS
        server.addHandler(Service.GET_ALL_BOOKS, (request) -> {
            try {
                Future<String> response = service.getAllBooks();
                return new Request(Request.SUCCESS_STATUS, response.get());
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
                return new Request(Request.ERROR_STATUS, e.toString());
            }
        });
        // GET ALL CLIENTS
        server.addHandler(Service.GET_ALL_CLIENTS, (request) -> {
            try {
                Future<String> response = service.getAllClients();
                return new Request(Request.SUCCESS_STATUS, response.get());
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
                return new Request(Request.ERROR_STATUS, e.toString());
            }
        });
        // GET ALL PURCHASES
        server.addHandler(Service.GET_ALL_PURCHASES, (request) -> {
            try {
                Future<String> response = service.getAllPurchases();
                return new Request(Request.SUCCESS_STATUS, response.get());
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
                return new Request(Request.ERROR_STATUS, e.toString());
            }
        });
        // UPDATE BOOK
        server.addHandler(Service.UPDATE_BOOK, (request) -> {
            try {
                String payload = request.getJsonPayload();
                JSONObject jsonObject = (JSONObject) JSONValue.parse(payload);
                Long ID = Long.parseLong(jsonObject.get("ID").toString());
                String title = jsonObject.get("title").toString();
                String author = jsonObject.get("author").toString();
                int price = Integer.parseInt(jsonObject.get("price").toString());
                Future<String> response = service.updateBook(ID, title, author, price);
                return new Request(Request.SUCCESS_STATUS, response.get());
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
                return new Request(Request.ERROR_STATUS, e.toString());
            }
        });
        // UPDATE CLIENT
        server.addHandler(Service.UPDATE_CLIENT, (request) -> {
            try {
                String payload = request.getJsonPayload();
                JSONObject jsonObject = (JSONObject) JSONValue.parse(payload);
                Long ID = Long.parseLong(jsonObject.get("ID").toString());
                String firstname = jsonObject.get("firstname").toString();
                String lastname = jsonObject.get("lastname").toString();
                int spendings = Integer.parseInt(jsonObject.get("spendings").toString());
                Future<String> response = service.updateClient(ID, firstname, lastname, spendings);
                return new Request(Request.SUCCESS_STATUS, response.get());
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
                return new Request(Request.ERROR_STATUS, e.toString());
            }
        });
        // DELETE BOOK
        server.addHandler(Service.DELETE_BOOK, (request) -> {
            try {
                String payload = request.getJsonPayload();
                JSONObject jsonObject = (JSONObject) JSONValue.parse(payload);
                Long ID = Long.parseLong(jsonObject.get("ID").toString());
                Future<String> response = service.deleteBook(ID);
                return new Request(Request.SUCCESS_STATUS, response.get());
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
                return new Request(Request.ERROR_STATUS, e.toString());
            }
        });
        // DELETE CLIENT
        server.addHandler(Service.DELETE_CLIENT, (request) -> {
            try {
                String payload = request.getJsonPayload();
                JSONObject jsonObject = (JSONObject) JSONValue.parse(payload);
                Long ID = Long.parseLong(jsonObject.get("ID").toString());
                Future<String> response = service.deleteClient(ID);
                return new Request(Request.SUCCESS_STATUS, response.get());
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
                return new Request(Request.ERROR_STATUS, e.toString());
            }
        });
        // DELETE PURCHASE
        server.addHandler(Service.DELETE_PURCHASE, (request) -> {
            try {
                String payload = request.getJsonPayload();
                JSONObject jsonObject = (JSONObject) JSONValue.parse(payload);
                Long ID = Long.parseLong(jsonObject.get("ID").toString());
                Future<String> response = service.deletePurchase(ID);
                return new Request(Request.SUCCESS_STATUS, response.get());
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
                return new Request(Request.ERROR_STATUS, e.toString());
            }
        });
        // FILTER BOOKS BY AUTHOR
        server.addHandler(Service.FILTER_BOOKS_AUTHOR, (request) -> {
            try {
                String payload = request.getJsonPayload();
                JSONObject jsonObject = (JSONObject) JSONValue.parse(payload);
                String author = jsonObject.get("author").toString();
                Future<String> response = service.filterBooksAuthor(author);
                return new Request(Request.SUCCESS_STATUS, response.get());
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
                return new Request(Request.ERROR_STATUS, e.toString());
            }
        });
        // FILTER BOOKS BY PRICE
        server.addHandler(Service.FILTER_BOOKS_PRICE, (request) -> {
            try {
                String payload = request.getJsonPayload();
                JSONObject jsonObject = (JSONObject) JSONValue.parse(payload);
                String minPrice = jsonObject.get("minPrice").toString();
                String maxPrice = jsonObject.get("maxPrice").toString();
                Future<String> response = service.filterBooksPrice(Integer.parseInt(minPrice), Integer.parseInt(maxPrice));
                return new Request(Request.SUCCESS_STATUS, response.get());
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
                return new Request(Request.ERROR_STATUS, e.toString());
            }
        });
        // FILTER CLIENTS BY NAME
        server.addHandler(Service.FILTER_CLIENTS_NAME, (request) -> {
            try {
                String payload = request.getJsonPayload();
                JSONObject jsonObject = (JSONObject) JSONValue.parse(payload);
                String name = jsonObject.get("name").toString();
                Future<String> response = service.filterClientsName(name);
                return new Request(Request.SUCCESS_STATUS, response.get());
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
                return new Request(Request.ERROR_STATUS, e.toString());
            }
        });
        // FILTER TOP CLIENTS
        server.addHandler(Service.FILTER_TOP_CLIENTS, (request) -> {
            try {
                Future<String> response = service.filterTopClients();
                return new Request(Request.SUCCESS_STATUS, response.get());
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
                return new Request(Request.ERROR_STATUS, e.toString());
            }
        });
    }
}
