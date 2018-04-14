import java.util.Date;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.NotBoundException;

import java.net.MalformedURLException;

public class MotionDetector extends Thread
{
    public void run()
    {
        try
        {
            Operations op = (Operations) Naming.lookup("//localhost/Server");

            MyEvent event;
            while(true)
            {
                Thread.sleep(2000);
                event = new MyEvent(new Date());
                SurveillanceSystem.Events.add(event);
                System.out.println(event.getName());
                op.Notify();
            }
        }
        catch (InterruptedException | NotBoundException | MalformedURLException | RemoteException ex) { }
    }
}