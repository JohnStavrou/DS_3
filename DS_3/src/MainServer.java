import java.rmi.Naming;
import java.rmi.RemoteException;
import java.net.MalformedURLException;
import java.rmi.registry.LocateRegistry;

public class MainServer 
{
    public static void main(String args[]) throws RemoteException, InterruptedException
    {
        try
        {
            SurveillanceSystem ss = new SurveillanceSystem();
            LocateRegistry.createRegistry(1099);
            Naming.rebind("//localhost/Server", ss);
            System.out.println("Server up and running...");
            Thread.sleep(1000);
            
            System.out.println("Initializing guards...\n");
            Thread.sleep(500);
            
            Thread Guard1 = new Thread(new Guard(ss, "Guard 1"));
            Guard1.start();
            Thread.sleep(500);
            Thread Guard2 = new Thread(new Guard(ss, "Guard 2"));
            Guard2.start();
            Thread.sleep(500);
            Thread Guard3 = new Thread(new Guard(ss, "Guard 3"));
            Guard3.start();
            Thread.sleep(500);
            Thread Guard4 = new Thread(new Guard(ss, "Guard 4"));
            Guard4.start();
            Thread.sleep(500);
            
            System.out.println("\n~ ~ ~ ~ ~ ~ ~ ~ Now detecting movement ~ ~ ~ ~ ~ ~ ~ ~");
            new MotionDetector().start();
        }
        catch (MalformedURLException | InterruptedException ex) { }
    }
}