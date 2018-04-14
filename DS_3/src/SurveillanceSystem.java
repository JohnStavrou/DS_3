import java.util.ArrayList;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class SurveillanceSystem extends UnicastRemoteObject implements Operations
{
    public static ArrayList<MyEvent> Events = new ArrayList<>();
    public static int toNotify = 0;

    public SurveillanceSystem() throws RemoteException
    {
        super();
    }
        
    @Override
    public synchronized MyEvent getEvent() throws RemoteException
    {
        MyEvent event = null;
        try
        {
            if(Events.isEmpty())
                wait();

            event = Events.get(Events.size() - 1);
            while(event.getNotified() == toNotify)
            {
                wait();
                event = Events.get(Events.size() - 1);
            }
            
            event.setNotified(event.getNotified() + 1);
        }
        catch (InterruptedException ex) { }
        
        return event;
    }

    @Override
    public synchronized void Notify() throws RemoteException
    {
        notifyAll();
    }

    @Override
    public synchronized void Wait() throws RemoteException
    {
        try
        {
            wait();
        }
        catch (InterruptedException ex) { }
    }

    @Override
    public void ManageUsers(boolean online) throws RemoteException
    {
        if(online)
            toNotify++;
        else
            toNotify--;
    }
}