package rmi;

import java.net.InetAddress;
import java.net.UnknownHostException;
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

            String ipAddress = null;
            try {
                ipAddress = InetAddress.getLocalHost().toString();
            } catch (UnknownHostException e) {
                System.err.println(e.toString());
            }
            LocateRegistry.createRegistry(1099);
            assert ipAddress != null;
            String name = "rmi://" + ipAddress.substring(ipAddress.indexOf("/") + 1) + "/CheckerGame";
            System.err.println(name);
            Naming.rebind(name, stub);

            System.err.println("CheckerGameServer ready");
        } catch (Exception e) {
            System.err.println("CheckerGameServer exception: " + e.toString());
            e.printStackTrace();
        }
    }
}