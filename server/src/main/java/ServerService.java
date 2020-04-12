import api.Service;
import controller.Controller;
import models.validation.Validator;
import models.validation.ValidatorException;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

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
                jsonObject.put("message", "Book added successfully !");
                return JSONValue.toJSONString(jsonObject);
            } catch (ValidatorException ve) {
                return ve.toString();
            }
        });
    }
}
