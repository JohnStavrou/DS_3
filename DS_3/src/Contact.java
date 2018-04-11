import java.io.Serializable;

public class Contact implements Serializable
{
    private String name;
    private String address;
    private String number;
    
    public Contact (String name, String address, String number)
    {
        this.name = name;
        this.address = address;
        this.number = number;
    }
    
    public String getName()
    {
        return this.name;
    }
    
    public String getNumber()
    {
        return this.number;
    }
}