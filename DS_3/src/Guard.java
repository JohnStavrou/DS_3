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
import java.rmi.server.UnicastRemoteObject;

public class Guard extends UnicastRemoteObject implements Runnable, GuardOperations
{
    private String name; // Το όνομα του Thread.
    private boolean online; // Αναγνωριστικό για τον αν ο φύλακας είναι εγγεγραμμένος στο σύστημα.
    
    ServerOperations op;
    GuardOperations guard;
    JLabel Image = new JLabel();
    JFrame Frame = new JFrame();
    JButton Button = new JButton();
    JPanel MainPanel = new JPanel();
    JPanel Row2Panel = new JPanel();
    JPanel ImagePanel = new JPanel();
    JPanel ButtonPanel = new JPanel();
    JButton UnsubButton = new JButton();
    JTextArea TextArea = new JTextArea();
    JScrollPane ScrollPane = new JScrollPane(TextArea);

    public Guard(String name) throws RemoteException
    {
        try
        {
            this.name = name;
            this.online = false;
        
            /* Κρατάω το interface στη μεταβλητή guard για να το στείλω αργότερα στο
               σύστημα για να ξέρει για ποιόν φύλακα πρόκειται. */
            guard = (GuardOperations) this; // Κραταω το interface

            op = (ServerOperations) Naming.lookup("//localhost/Server");
        }
        catch (NotBoundException | MalformedURLException | RemoteException ex) { }
    }

    public void InitializeFrame()
    {
        /* Το πρώτο μέρος του γραφικού είναι ενα JTextArea που εμπεριέχεται μέσα σε
           σε ένα JScrollPane και στο οποίο αναγράφονται τα συμβάντα. */
        TextArea.setFont(new Font("TimesRoman", Font.BOLD, 12));
        TextArea.setEditable(false);
        /* Η παρακάτω εντολή ενημερώνει το γραφικό έτσι ώστε όταν γεμίσει ο κάθετος
           διαθέσιμος χώρος και εμφανιστεί η ScrollBar, δεν μένει στάσιμο ενώ συνεχίζει
           να γεμίζει, αλλά ανανεώνεται και πηγαίνει πάντα στο κάτω μέρος που βρίσκονται
           τα καινούρια συμβάντα. */
        ((DefaultCaret) TextArea.getCaret()).setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
        
        // Το κουμπί εγγραφής/απεγγραφής.
        Button.setFocusable(false);
        Button.setText("Subscribe");
        Button.setPreferredSize(new Dimension(340, 40));
        Button.setFont(new Font("TimesRoman", Font.BOLD, 15));
        Button.addMouseListener(new java.awt.event.MouseAdapter()
        {
            public void mouseClicked(java.awt.event.MouseEvent evt)
            {
                try
                {
                    if(online)
                    {
                        online = false;
                        Button.setText("Subscribe");
                        op.Unsubscribe(guard, name);
                    }
                    else
                    {
                        online = true;
                        Button.setText("Unsubscribe");
                        op.Subscribe(guard, name);
                    }
                }
                catch (RemoteException ex) { }
            }
        });
       
        // Το JPanel που περιέχει την εικόνα.
        ImagePanel.setBackground(Color.WHITE);
        ImagePanel.setPreferredSize(new Dimension(50, 290));
        ImagePanel.add(Image);
        
        // Το JPanel που περιέχει τo κουμπί εγγραφής/απεγγραφής.
        ButtonPanel.setBackground(Color.WHITE);
        ButtonPanel.add(Button);
        
        /* Το JPanel που περιέχει τα 2 παραπάνω Panel και αναπαριστά τη δεύτερη σειρά
           του συνολικού MainPanel. */ 
        Row2Panel.setLayout(new BoxLayout(Row2Panel, BoxLayout.Y_AXIS));
        Row2Panel.add(ImagePanel);
        Row2Panel.add(ButtonPanel);
                
        MainPanel.setLayout(new GridLayout(2,0));
        MainPanel.add(ScrollPane);
        MainPanel.add(Row2Panel);
        
        Frame.setSize(360, 700);
        Frame.setTitle(name);
        Frame.setBackground(Color.GRAY);
        Frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Frame.setResizable(false);
        Frame.setLocationRelativeTo(null);
        Frame.add(MainPanel);
        Frame.setVisible(true);
    }
    
    @Override
    public void run()
    {
        InitializeFrame();
        System.out.println(name + " initialized");
    }

    @Override
    public void getEvent(MyEvent event) throws RemoteException
    {
        TextArea.setText(TextArea.getText() + event.getName() + "\n");
        Image.setIcon(event.getImage());
        Image.repaint();
    }
}