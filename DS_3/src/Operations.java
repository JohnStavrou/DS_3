import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Operations extends Remote
{
    public MyEvent getEvent() throws RemoteException;
    public void Notify() throws RemoteException;
    public void Wait() throws RemoteException;
    public void ManageUsers(boolean online) throws RemoteException;
}