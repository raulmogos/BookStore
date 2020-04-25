import client.ClientService;
import client.TCPClient;
import ui.Console;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class AppClient {
    public static void main(String[] args) {
        System.out.println("start client app");

        ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

        TCPClient tcpClient = new TCPClient();
        ClientService clientService = new ClientService(executorService, tcpClient);
        Console console = new Console(clientService);
        console.run();

        executorService.shutdown();

        System.out.println("client stopped");
    }
}
