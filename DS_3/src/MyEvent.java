// Σταύρου Ιωάννης - icsd14190

import java.util.Date;
import java.util.Random;
import java.util.ArrayList;
import java.io.Serializable;
import javax.swing.ImageIcon;
import java.text.SimpleDateFormat;

public class MyEvent implements Serializable
{
    private Date date;
    private String name;
    private ImageIcon image;
    private ArrayList<String> notified; 
    
    Random r = new Random();
    SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy");
    
    public MyEvent()
    {
        /*
        Κάθε νέο γεγονός αρχικοποιείται με: 
        ~ date: Ημερομηνία καταγραφής του.
        ~ name: Συμβολοσειρά που αναπαριστά το γεγονός στο γραφικό.
        ~ image: Μια τυχαία από τις 4 δοσμένες εικόνες.
        ~ notified: Μια λίστα που περιέχει τα ID των thread που έχουν ενημερωθεί από
                    το συγκεκριμένο γεγονός.
        */
        this.date = new Date();
        this.name = "Movement event caught at " + sdf.format(date);
        this.image = SurveillanceSystem.Images.get(r.nextInt(4));
        this.notified = new ArrayList<>();
    }
    
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