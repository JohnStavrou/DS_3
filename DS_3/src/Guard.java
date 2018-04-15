// Σταύρου Ιωάννης - icsd14190

import java.awt.Font;
import java.awt.Color;
import java.rmi.Naming;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.Dimension;
import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.BoxLayout;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import java.rmi.RemoteException;
import java.rmi.NotBoundException;
import javax.swing.text.DefaultCaret;
import java.net.MalformedURLException;

public class Guard extends JFrame implements Runnable
{
    private String name;
    private boolean online;
    
    Operations op;
    JLabel Image = new JLabel();
    JPanel MainPanel = new JPanel();
    JPanel Row2Panel = new JPanel();
    JPanel ImagePanel = new JPanel();
    JPanel ButtonPanel = new JPanel();
    JButton SubButton = new JButton();
    JButton UnsubButton = new JButton();
    JTextArea TextArea = new JTextArea();
    JScrollPane ScrollPane = new JScrollPane(TextArea);

    public Guard(String name)
    {
        super(name);
        
        this.name = name;
        this.online = false;
        
        setSize(350, 700);
        setBackground(Color.GRAY);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setResizable(false);
        setLocationRelativeTo(null);
        setVisible(true);

        try
        {
            op = (Operations) Naming.lookup("//localhost/Server");
            System.out.println("~ Initializing " + name);
        }
        catch (NotBoundException | MalformedURLException | RemoteException ex) { }
        
        CreateMainPanel();
    }
    
    public void CreateMainPanel()
    {
        TextArea.setFont(new Font("TimesRoman", Font.BOLD, 12));
        TextArea.setEditable(false);
        TextArea.setText(TextArea.getText() + "~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ OFFLINE ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~\n\n");
        ((DefaultCaret) TextArea.getCaret()).setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
        
        SubButton.setFocusable(false);
        SubButton.setText("Subscribe");
        SubButton.setPreferredSize(new Dimension(340, 40));
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
                    System.out.println("~ " + name + " is now online");
                    Thread.sleep(500);
                    Append();
                }
                catch (InterruptedException ex) { }
            }
        });
        
        UnsubButton.setVisible(false);
        UnsubButton.setFocusable(false);
        UnsubButton.setText("Unsubscribe");
        UnsubButton.setPreferredSize(new Dimension(340, 40));
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
                    System.out.println("~ " + name + " is now offline");
                    Thread.sleep(500);
                    Append();
                }
                catch (InterruptedException ex) { }
            }
        });

        ImagePanel.setBackground(Color.WHITE);
        ImagePanel.setPreferredSize(new Dimension(50, 290));
        ImagePanel.add(Image);
        
        ButtonPanel.setBackground(Color.WHITE);
        ButtonPanel.add(SubButton);
        ButtonPanel.add(UnsubButton);
        
        Row2Panel.setLayout(new BoxLayout(Row2Panel, BoxLayout.Y_AXIS));
        Row2Panel.add(ImagePanel);
        Row2Panel.add(ButtonPanel);
                
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
                    Append(op.getEvent((Thread)(Runnable) this));
            }
            catch (RemoteException | InterruptedException ex) {}
    }
    
    public void Append(MyEvent event)
    {
        TextArea.setText(TextArea.getText() + event.getName() + "\n");
        Image.setIcon(event.getImage());
        Image.repaint();
    }
    
    public void Append()
    {
        if(online)
            TextArea.setText(TextArea.getText() + "~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ONLINE ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~\n");
        else
            TextArea.setText(TextArea.getText() + "\n~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ OFFLINE ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~\n\n");
    }
}