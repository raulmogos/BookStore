package ui;

import client.ClientService;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class Console {

    private ClientService clientService;

    public Console(ClientService clientService) {
        this.clientService = clientService;
    }

    public void run() {
        // TODO: 08/04/2020
        addBook();
    }

    public void addBook() {
        Future<String> out = this.clientService.addBook("aa", "hh", 12);
        try {
            System.out.println(out.get());
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }
}
