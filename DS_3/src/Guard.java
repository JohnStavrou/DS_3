import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class Guard extends JFrame implements Runnable
{
    private String name;
    private boolean online;
    private SurveillanceSystem ss;
    
    Operations op;
    JPanel MainPanel = new JPanel();
    JButton SubButton = new JButton();
    JButton UnsubButton = new JButton();
    JTextArea TextArea = new JTextArea();
    FlowLayout flowLayout = new FlowLayout();
    JScrollPane ScrollPane = new JScrollPane(TextArea);

    JPanel Row2Panel = new JPanel();

    public Guard(SurveillanceSystem ss, String name)
    {
        super(name);
        
        this.name = name;
        this.online = false;
        this.ss = ss;
        
        setSize(400, 650);
        setBackground(Color.GRAY);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setResizable(false);
        setLocationRelativeTo(null);
        setVisible(true);
        
        CreateMainPanel();

        try
        {
            op = (Operations) Naming.lookup("//localhost/Server");
            System.out.println(name + " initialized");
        }
        catch (NotBoundException | MalformedURLException | RemoteException ex) { }
    }
    
    public void CreateMainPanel() // Δημιουργία του βασικού panel.
    {
        TextArea.setFont(new Font("TimesRoman", Font.BOLD, 12));
        TextArea.setEditable(false);
        
        SubButton.setFocusable(false);
        SubButton.setText("Subscribe");
        SubButton.setPreferredSize(new Dimension(200, 40));
        SubButton.setFont(new Font("TimesRoman", Font.BOLD, 15));
        SubButton.addMouseListener(new java.awt.event.MouseAdapter()
        {
            public void mouseClicked(java.awt.event.MouseEvent evt)
            {
                try
                {
                    UnsubButton.setVisible(true);
                    SubButton.setVisible(false);
                    
                    online = true;
                    op.ManageUsers(online);
                    System.out.println(name + " is now online");
                }
                catch (RemoteException ex) { }
            }
        });
        Row2Panel.add(SubButton);

        UnsubButton.setVisible(false);
        UnsubButton.setFocusable(false);
        UnsubButton.setText("Unsubscribe");
        UnsubButton.setPreferredSize(new Dimension(200, 40));
        UnsubButton.setFont(new Font("TimesRoman", Font.BOLD, 15));
        UnsubButton.addMouseListener(new java.awt.event.MouseAdapter()
        {
            public void mouseClicked(java.awt.event.MouseEvent evt)
            {
                try
                {
                    SubButton.setVisible(true);
                    UnsubButton.setVisible(false);
                
                    online = false;
                    op.ManageUsers(online);
                    System.out.println(name + " is now offline");
                }
                catch (RemoteException ex) { }
            }
        });
        Row2Panel.add(UnsubButton);
        
        MainPanel.setLayout(new GridLayout(2,0));
        MainPanel.add(ScrollPane);
        MainPanel.add(Row2Panel);
        
        add(MainPanel);
    }

    @Override
    public void run()
    {
        while(true)
            try
            {
                Thread.sleep(100);
                if(online)
                {
                    MyEvent event = op.getEvent();
                    Append(event);
                }
            }
            catch (RemoteException | InterruptedException ex) {}
    }
    
    public void Append(MyEvent event)
    {
        TextArea.setText(TextArea.getText() + event.getName() + "\n");
    }
    
    public void Append()
    {
        if(online)
            TextArea.setText(TextArea.getText() + "~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~\n");
        else
            TextArea.setText(TextArea.getText() + "~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~\n");

    }
}