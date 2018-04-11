import java.io.*;;
import java.util.*;
import java.rmi.*;
import java.rmi.server.*;

public class SurveillanceSystem extends UnicastRemoteObject implements Operations
{
    private ArrayList<Contact> directory;

    public SurveillanceSystem() throws RemoteException
    {
        super();

        String newline;
        String str[];

       /* try
        {
            directory = new ArrayList<Contact>();
            BufferedReader instream = new BufferedReader(new FileReader("phonedirectory.txt"));

            while( (newline = instream.readLine()) != null )
            {
                str = newline.split(":");
                directory.add(new Contact(str[0], str[1], str[2]));
            }
            
            for(Contact c : directory)
                System.out.println(c.getName() + " " + c.getNumber());
        }
        catch (IOException ex)
        {
            ex.printStackTrace();
        }*/
    }
    
    public synchronized Contact searchNumber(String name) throws RemoteException
    {
        Contact ret = null;
        for(int i = 0; i < directory.size(); i++)
            if(name.equals(directory.get(i).getName()))
                ret=directory.get(i);
        return ret;
    }
    
    public synchronized boolean insertContact(String name, String address, String number) throws RemoteException
    {
        for(int i=0; i<directory.size();i++)
            if(name.equals(directory.get(i).getName()))
                return false;
        directory.add(new Contact(name, address, number));
        System.out.println("Inserting "+ name);
    return true;
    }
}