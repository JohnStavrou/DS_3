// Σταύρου Ιωάννης - icsd14190

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
            // Αρχικοποιώ το σύστημα ασφαλείας.
            SurveillanceSystem ss = new SurveillanceSystem();
            
            /* Ορίζω τη διεύθυνση στην οποία θα βρίσκεται το αντικείμενο που θα είναι
               διαθέσιμο προς αναζήτηση από τους πελάτες για την κλήση των επιθυμητών υπηρεσιών.*/
            LocateRegistry.createRegistry(1099);
            Naming.rebind("//localhost/Server", ss);
            System.out.println("Server up and running...\n");
            Thread.sleep(1000);
            
            // Αρχικοποιώ και τρέχω τα γραφικά των τεσσάρων φυλάκων του κήπου.
            Thread Guard1 = new Thread(new Guard("Guard 1"));
            Guard1.start();
            Thread.sleep(500);
            Thread Guard2 = new Thread(new Guard("Guard 2"));
            Guard2.start();
            Thread.sleep(500);
            Thread Guard3 = new Thread(new Guard("Guard 3"));
            Guard3.start();
            Thread.sleep(500);
            Thread Guard4 = new Thread(new Guard("Guard 4"));
            Guard4.start();
            Thread.sleep(500);
            
            // Τρέχω ακόμα ένα thread που αναπαριστά το σύστημα ανίχνευσης κίνησης του συστήματος.
            System.out.println("\n~ ~ ~ ~ ~ ~ ~ ~ Now detecting movement ~ ~ ~ ~ ~ ~ ~ ~");
            new MotionDetector().start();
        }
        catch (MalformedURLException | InterruptedException ex) { }
    }
}