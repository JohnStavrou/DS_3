// Σταύρου Ιωάννης - icsd14190

import java.util.Date;
import java.util.ArrayList;
import java.io.Serializable;
import javax.swing.ImageIcon;

public class MyEvent implements Serializable
{
    private Date date;
    private String name;
    private ImageIcon image;
    private ArrayList<String> notified; 
    
    public Date getDate()
    {
        return date;
    }
    
    public String getName()
    {
        return name;
    }
    
    public ImageIcon getImage()
    {
        return image;
    }
    
    public ArrayList<String> getNotified()
    {
        return notified;
    }
}