import api.Service;
import controller.Controller;

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
    public Future<String> addBook(String name) {
        return executorService.submit(() -> {
            controller.addBook(name, name, 1);
            return "book successfully added";
        });
    }
}
