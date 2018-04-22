// Σταύρου Ιωάννης - icsd14190

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface GuardOperations extends Remote
{
    public void getEvent(MyEvent event) throws RemoteException;
}