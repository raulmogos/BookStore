import api.Request;
import api.Service;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import java.util.concurrent.Future;

public class Helpers {
    public static void addHandlersToServer(TCPServer server, Service service) {
        // ADD BOOK
        server.addHandler(Service.ADD_BOOK, (request) -> {
            try {
                String payload = request.getJsonPayload();
                JSONObject jsonObject = (JSONObject) JSONValue.parse(payload);
                String title = (String) jsonObject.get("title");
                String author = (String) jsonObject.get("author");
                int price = Integer.parseInt(jsonObject.get("price").toString());
                Future<String> response = service.addBook(title, author, price);
                return new Request(Request.SUCCESS_STATUS, response.get());
            } catch (Exception e) {
                e.printStackTrace();
                return new Request(Request.ERROR_STATUS, e.toString());
            }
        });
    }
}
