package rmi;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class CheckerGameClient {

    private CheckerGameClient() {}

    public static void main(String[] args) {

        try {
            Registry registry = LocateRegistry.getRegistry("2610:130:110:1520:c0b6:4bad:5a28:2bc", 1099);
            CheckerGameRemote stub = (CheckerGameRemote) registry.lookup("CheckerGameRemote");
            String response = stub.sayHello();
            System.out.println("response: " + response);
        } catch (Exception e) {
            System.err.println("Client exception: " + e.toString());
            e.printStackTrace();
        }
    }
}
