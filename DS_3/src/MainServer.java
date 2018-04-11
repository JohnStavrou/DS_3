import java.rmi.Naming;
import java.rmi.RemoteException;
import java.net.MalformedURLException;
import java.rmi.registry.LocateRegistry;

public class MainServer 
{
    public static void main(String args[]) throws RemoteException
    {
        try
        {
            LocateRegistry.createRegistry(1099);
            Naming.rebind("//localhost/Server", new SurveillanceSystem());
            System.out.println("Server up and running...\n");
        }
        catch (MalformedURLException | RemoteException ex)
        {
            System.out.println("Server not connected!");
        }
    }
}