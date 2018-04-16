// Σταύρου Ιωάννης - icsd14190

import java.rmi.Naming;
import java.util.Random;
import java.rmi.RemoteException;
import java.rmi.NotBoundException;
import java.net.MalformedURLException;

public class MotionDetector extends Thread
{
    Random r = new Random();

    public void run()
    {
        try
        {
            /* Ορίζουμε σε ποια διεύθυνση μπορεί το σύστημα ανίχνευσης κίνησης να
               βρει το σύστημα παρακολούθησης. */
            Operations op = (Operations) Naming.lookup("//localhost/Server");

            MyEvent event;
            while(true)
            {
                /* Περιμένει κάθε φορά τυχαίο χρόνο από 2 μέχρι 5 δευτερόλεπτα για
                   να παράξει ένα νέο γεγονός. */
                Thread.sleep((r.nextInt(4) + 2) * 1000);
                /* Δημιουργεί ένα γεγονός και το προσθέτει σε μια global λίστα που
                   διαθέτει το σύστημα ασφαλείας. */
                event = new MyEvent();
                SurveillanceSystem.Events.add(event);
                System.out.println(event.getName());
                op.Notify(); // Ενημερώνει το σύστημα οτι ένα νέο γεγονός έχει παραχθεί.
            }
        }
        catch (InterruptedException | NotBoundException | MalformedURLException | RemoteException ex) { }
    }
}