package rmi;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class CheckerGameServer implements CheckerGameRemote {

    public CheckerGameServer() {}

    @Override
    public String sayHello() throws RemoteException {
        return "Hello, world!";
    }

    public static void main (String[] argv) {
        try {
            CheckerGameServer obj = new CheckerGameServer();
            CheckerGameRemote stub = (CheckerGameRemote) UnicastRemoteObject.exportObject(obj, 0);

            System.setProperty("java.rmi.server.hostname", "2610:130:110:1520:c0b6:4bad:5a28:2bc");
            Registry registry = LocateRegistry.createRegistry(1099);
            registry.rebind("CheckerGameRemote", stub);

            System.err.println("CheckerGameServer ready");
        } catch (Exception e) {
            System.err.println("CheckerGameServer exception: " + e.toString());
            e.printStackTrace();
        }
    }
}