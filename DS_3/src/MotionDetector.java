// Σταύρου Ιωάννης - icsd14190

import java.rmi.Naming;
import java.util.Random;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import java.rmi.RemoteException;
import java.rmi.NotBoundException;
import java.net.MalformedURLException;

public class MotionDetector extends Thread
{
    // Η λίστα Images περιέχει τις 4 δοσμένες εικόνες.
    ArrayList<ImageIcon> Images = new ArrayList<>();
        
    Random r = new Random();

    @Override
    public void run()
    {
        /* Γεμίζω τη λίστα Images. Για να τρέξει ο κώδικας αρκεί να υπάρχει ένας φάκελος
           frames μέσα στο φάκελο του project που να περιέχει τις 4 δοσμένες εικόνες. */
        Images.add(new ImageIcon("frames/tile000.png"));
        Images.add(new ImageIcon("frames/tile001.png"));
        Images.add(new ImageIcon("frames/tile002.png"));
        Images.add(new ImageIcon("frames/tile003.png"));
        
        try
        {
            /* Ορίζουμε σε ποια διεύθυνση μπορεί το σύστημα ανίχνευσης κίνησης να
               βρει το σύστημα παρακολούθησης. */
            ServerOperations op = (ServerOperations) Naming.lookup("//localhost/Server");

            while(true)
            {
                /* Περιμένει κάθε φορά τυχαίο χρόνο από 2 μέχρι 5 δευτερόλεπτα για
                   να παράξει ένα νέο γεγονός. */
                Thread.sleep((r.nextInt(4) + 2) * 1000);

                // Παράγει και στέλνει στο σύστημα ένα νέο συμβάν.
                op.Notify(new MyEvent(Images.get(r.nextInt(4))));
            }
        }
        catch (InterruptedException | NotBoundException | MalformedURLException | RemoteException ex) { }
    }
}