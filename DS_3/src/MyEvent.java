import java.util.Date;
import java.io.Serializable;
import java.text.SimpleDateFormat;

public class MyEvent implements Serializable
{
    private Date date;
    private String name;
    private int notified;
    
    SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy");
    
    public MyEvent(Date date)
    {
        this.date = date;
        this.name = "Movement event caught at " + sdf.format(date);
        this.notified = 0;
    }
    
    public Date getDate()
    {
        return date;
    }
    
    public String getName()
    {
        return name;
    }
    
    public int getNotified()
    {
        return notified;
    }

    public void setNotified(int notified)
    {
        this.notified = notified;
    }
}