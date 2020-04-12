package api;

import java.util.concurrent.Future;

public interface Service {
     String ADD_BOOK = "ADD_BOOK";

     // todo: continue

     Future<String> addBook(String title, String author, int price);
}
