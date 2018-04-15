// Σταύρου Ιωάννης - icsd14190

import java.util.ArrayList;
import javax.swing.ImageIcon;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class SurveillanceSystem extends UnicastRemoteObject implements Operations
{
    // Η λίστα Events περιέχει όλα τα καταγεγραμμένα γεγονότα.
    public static ArrayList<MyEvent> Events = new ArrayList<>();
    
    // Η λίστα Images περιέχει τις 4 δοσμένες εικόνες.
    public static ArrayList<ImageIcon> Images = new ArrayList<>();

    public SurveillanceSystem() throws RemoteException
    {
        super();
        
        /* Γεμίζω τη λίστα Images. Για να τρέξει ο κώδικας αρκεί να υπάρχει ένας φάκελος
           frames μέσα στο φάκελο του project που να περιέχει τις 4 δοσμένες εικόνες. */
        Images.add(new ImageIcon("frames/tile000.png"));
        Images.add(new ImageIcon("frames/tile001.png"));
        Images.add(new ImageIcon("frames/tile002.png"));
        Images.add(new ImageIcon("frames/tile003.png"));
    }
        
    /* Η συνάρτηση getEvent επιστρέφει στον εκάστοτε φύλακα ένα γεγενός κίνησης που
       το σύστημα έχει ανιχνεύσει. */
    @Override
    public synchronized MyEvent getEvent(Thread thread) throws RemoteException
    {
        MyEvent event = null;
        try
        {
            // Αν δεν έχει καταγραφεί κάποιο γεγονός το thread περιμένει.
            if(Events.isEmpty())
                wait();

            /* Παίρνουμε το τελευταίο γεγονός που έχει καταγραφεί και ελέγχουμε μέσω
               της λίστας notified του γεγονότος πόσοι φύλακες έχουν ενημερωθεί από
               αυτό. Αν η λίστα περιέχει το ID του εκάστοτε thread, σημαίνει οτι ο
               φύλακας (thread) έχει λάβει το γεγονός. Τότε το thread περιμένει μέχρι
               να γίνει notify από την παραγωγή ενός νέου γεγονότος. */
            event = Events.get(Events.size() - 1);
            while(event.getNotified().contains(thread.getId()))
            {
                wait();
                event = Events.get(Events.size() - 1);
            }
            
            // Ενημερώνω τη λίστα του γεγονότος οτι έχει ενημερώσει κάποιον φύλακα.
            event.getNotified().add(thread.getId());
        }
        catch (InterruptedException ex) { }
        
        // Τελικά, επιστρέφω το γεγονός σε όποιον το ζήτησε.
        return event;
    }

    /* Η συνάρτηση Notify καλείται από το σύστημα ανίχνευσης κίνησης κάθε φορά που
       καταγράφει κάποιο γεγονός για να κάνει notify τα threads των φυλάκων. */
    @Override
    public synchronized void Notify() throws RemoteException
    {
        notifyAll();
    }
}