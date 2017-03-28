package rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface CheckerGameRemote extends Remote {
    String sayHello() throws RemoteException;
}
