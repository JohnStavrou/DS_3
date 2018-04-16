// Σταύρου Ιωάννης - icsd14190

public class Main
{
    public static void main(String[] args)
    {
        try
        {
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
        }
        catch (InterruptedException ex) { }
    }
}