// Σταύρου Ιωάννης - icsd14190

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ServerOperations extends Remote
{
    public void Notify(MyEvent event) throws RemoteException;
    public void Subscribe(GuardOperations guard, String name) throws RemoteException;
    public void Unsubscribe (GuardOperations guard, String name) throws RemoteException;
}