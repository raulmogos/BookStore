package api;

import java.util.concurrent.Future;

public interface Service {
     String ADD_BOOK = "ADD_BOOK";
     String ADD_CLIENT = "ADD_CLIENT";
     String ADD_PURCHASE = "ADD_PURCHASE";
     String GET_ALL_BOOKS = "GET_ALL_BOOKS";
     String GET_ALL_CLIENTS = "GET_ALL_CLIENTS";
     String GET_ALL_PURCHASES = "GET_ALL_PURCHASES";
     String UPDATE_BOOK = "UPDATE_BOOK";
     String UPDATE_CLIENT = "UPDATE_CLIENT";
     String DELETE_BOOK = "DELETE_BOOK";
     String DELETE_CLIENT = "DELETE_CLIENT";
     String DELETE_PURCHASE = "DELETE_PURCHASE";
     String FILTER_BOOKS_AUTHOR = "FILTER_BOOKS_AUTHOR";
     String FILTER_BOOKS_PRICE = "FILTER_BOOKS_PRICE";
     String FILTER_CLIENTS_NAME = "FILTER_CLIENTS_NAME";
     String FILTER_TOP_CLIENTS = "FILTER_TOP_CLIENTS";

     Future<String> addBook(String title, String author, int price);
     Future<String> addClient(String firstname, String lastname);
     Future<String> addPurchase(Long bookID, Long clientID);
     Future<String> getAllBooks();
     Future<String> getAllClients();
     Future<String> getAllPurchases();
     Future<String> updateBook(Long ID, String title, String author, int price);
     Future<String> updateClient(Long ID, String firstname, String lastname, int spendings);
     Future<String> deleteBook(Long ID);
     Future<String> deleteClient(Long ID);
     Future<String> deletePurchase(Long ID);
     Future<String> filterBooksAuthor(String author);
     Future<String> filterBooksPrice(int minPrice, int maxPrice);
     Future<String> filterClientsName(String name);
     Future<String> filterTopClients();
}
