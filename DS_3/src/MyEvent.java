// Σταύρου Ιωάννης - icsd14190

import java.util.Date;
import java.io.Serializable;
import javax.swing.ImageIcon;
import java.text.SimpleDateFormat;

public class MyEvent implements Serializable
{
    private Date date;
    private String name;
    private ImageIcon image;
    
    SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy");
    
    public MyEvent(ImageIcon image)
    {
        /*
        Κάθε νέο συμβάν αρχικοποιείται με: 
        ~ date: Ημερομηνία καταγραφής του.
        ~ name: Συμβολοσειρά που αναπαριστά το συμβάν στο γραφικό.
        ~ image: Μια τυχαία από τις 4 δοσμένες εικόνες.
        */
        this.date = new Date();
        this.name = "Movement event caught at " + sdf.format(date);
        this.image = image;
    }
    
    public String getName()
    {
        return name;
    }
    
    public ImageIcon getImage()
    {
        return image;
    }
}