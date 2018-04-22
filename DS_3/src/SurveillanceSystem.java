// Σταύρου Ιωάννης - icsd14190

import java.util.Iterator;
import java.util.ArrayList;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class SurveillanceSystem extends UnicastRemoteObject implements ServerOperations
{
    // Περιέχει τους συνδεδεμένους φύλακες.
    public ArrayList<GuardOperations> Guards = new ArrayList<>();

    public SurveillanceSystem() throws RemoteException
    {
        super();
    }

    /* Η Notify δέχεται ένα νέο συμβάν από το σύστημα ανίχνευσης κίνησης και ενημερώνει
       τους ενεργούς φύλακες. */
    @Override
    public void Notify(MyEvent event) throws RemoteException
    {
        System.out.println(event.getName());

        Iterator<GuardOperations> iterator = Guards.iterator();
        while(iterator.hasNext())
            try
            {
                iterator.next().getEvent(event);
            }
            catch (RemoteException ex) { }
    }
    
    // Η Subscribe καλείται όταν ένα φύλακας εγγράφεται στο σύστημα.
    @Override
    public void Subscribe(GuardOperations guard, String name) throws RemoteException
    {
        Guards.add(guard);
        System.out.println(name + " is now online");
    }
    
    // Η Unsubscribe καλείται όταν ένα φύλακας απεγγράφεται από το σύστημα.
    @Override
    public void Unsubscribe(GuardOperations guard, String name) throws RemoteException
    {
        Guards.remove(guard);
        System.out.println(name + " is now offline");
    }
}