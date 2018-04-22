// Σταύρου Ιωάννης - icsd14190

import java.rmi.RemoteException;

public class MainGuard 
{
    public static void main(String args[]) throws RemoteException
    {
        // Αρχικοποιώ και τρέχω τα γραφικά των τεσσάρων φυλάκων του κήπου.
        new Thread(new Guard("Guard 1")).start();
        new Thread(new Guard("Guard 2")).start();
        new Thread(new Guard("Guard 3")).start();
        new Thread(new Guard("Guard 4")).start();
    }
}