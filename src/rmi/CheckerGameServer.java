package rmi;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
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

            LocateRegistry.createRegistry(1099);
            Naming.rebind("rmi://10.26.159.67:1099/CheckerGame", stub);

            System.err.println("CheckerGameServer ready");
        } catch (Exception e) {
            System.err.println("CheckerGameServer exception: " + e.toString());
            e.printStackTrace();
        }
    }
}