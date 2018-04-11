import java.rmi.*;

public interface Operations extends Remote
{
    public Contact searchNumber(String name) throws RemoteException;
    public boolean insertContact (String name, String address, String number) throws RemoteException;
}