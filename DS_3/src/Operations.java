// Σταύρου Ιωάννης - icsd14190

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Operations extends Remote
{
    public MyEvent getEvent(Thread thread) throws RemoteException;
    public void Notify() throws RemoteException;
}