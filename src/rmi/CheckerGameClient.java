package rmi;

import java.rmi.Naming;

public class CheckerGameClient {

    private CheckerGameClient() {}

    public static void main(String[] args) {

        try {
            CheckerGameRemote stub = (CheckerGameRemote) Naming.lookup("rmi://10.26.159.67:1099/CheckerGame");
            String response = stub.sayHello();
            System.out.println("response: " + response);
        } catch (Exception e) {
            System.err.println("Client exception: " + e.toString());
            e.printStackTrace();
        }
    }
}
