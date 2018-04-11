import static java.lang.Thread.sleep;
import java.rmi.*;
import java.net.MalformedURLException;

public class Guard extends Thread
{
    public static void main(String[] args) throws InterruptedException
    {
        try
        {
            String name = "//localhost/Server";
            Operations look_op = (Operations) Naming.lookup(name);
            System.out.println("Hello");
            sleep(10000);
        }
        catch (NotBoundException | MalformedURLException | RemoteException ex)
        {
            ex.printStackTrace();
        }
       
    }
}