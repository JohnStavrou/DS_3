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
    private String name; // Το όνομα του Thread.
    private boolean online; // Αναγνωριστικό για τον αν ο φύλακας είναι εγγεγραμμένος στο σύστημα.
    
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
        this.online = false; // Ο φύλακας αρχικοποιείται ως μη εγγεγραμμένος.
        
        Initialize();
    }

    public void Initialize()
    {
        setSize(360, 700);
        setBackground(Color.GRAY);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setResizable(false);
        setLocationRelativeTo(null);

        try
        {
            // Ορίζουμε σε ποια διεύθυνση μπορεί ο κάθε φύλακας να βρει το σύστημα παρακολούθησης.
            op = (Operations) Naming.lookup("//localhost/Server");
            System.out.println("~ Initializing " + name);
        }
        catch (NotBoundException | MalformedURLException | RemoteException ex) { }
        
        CreateMainPanel();
    }
    
    public void CreateMainPanel()
    {
        /* Το πρώτο μέρος του γραφικού είναι ενα JTextArea που εμπεριέχεται μέσα σε
           σε ένα JScrollPane και στο οποίο αναγράφονται τα γεγονότα. */
        TextArea.setFont(new Font("TimesRoman", Font.BOLD, 12));
        TextArea.setEditable(false);
        TextArea.setText(TextArea.getText() + "~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ OFFLINE ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~\n\n");
        /* Η παρακάτω εντολή ενημερώνει το γραφικό έτσι ώστε όταν γεμίσει ο κάθετος
           διαθέσιμος χώρος και εμφανιστεί η ScrollBar, δεν μένει στάσιμο ενώ συνεχίζει
           να γεμίζει, αλλά ανανεώνεται και πηγαίνει πάντα στο κάτω μέρος που βρίσκονται
           τα καινούρια γεγονότα. */
        ((DefaultCaret) TextArea.getCaret()).setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
        
        // Το κουμπί εγγραφής.
        SubButton.setFocusable(false);
        SubButton.setText("Subscribe");
        SubButton.setPreferredSize(new Dimension(340, 40));
        SubButton.setFont(new Font("TimesRoman", Font.BOLD, 15));
        SubButton.addMouseListener(new java.awt.event.MouseAdapter()
        {
            public void mouseClicked(java.awt.event.MouseEvent evt)
            {
                UnsubButton.setVisible(true);
                SubButton.setVisible(false);

                online = true;
                System.out.println("~ " + name + " is now online");
                Append();
            }
        });
        
        // Το κουμπί απεγγραφής.
        UnsubButton.setVisible(false);
        UnsubButton.setFocusable(false);
        UnsubButton.setText("Unsubscribe");
        UnsubButton.setPreferredSize(new Dimension(340, 40));
        UnsubButton.setFont(new Font("TimesRoman", Font.BOLD, 15));
        UnsubButton.addMouseListener(new java.awt.event.MouseAdapter()
        {
            public void mouseClicked(java.awt.event.MouseEvent evt)
            {
                SubButton.setVisible(true);
                UnsubButton.setVisible(false);

                online = false;
                System.out.println("~ " + name + " is now offline");
                Append();
            }
        });

        // Το JPanel περιέχει την εικόνα.
        ImagePanel.setBackground(Color.WHITE);
        ImagePanel.setPreferredSize(new Dimension(50, 290));
        ImagePanel.add(Image);
        
        // Το JPanel περιέχει τα κουμπιά εγγραφής/απεγγραφής.
        ButtonPanel.setBackground(Color.WHITE);
        ButtonPanel.add(SubButton);
        ButtonPanel.add(UnsubButton);
        
        /* Το JPanel που περιέχει τα 2 παραπάνω Panel και αναπαριστά τη δεύτερη σειρά
           του συνολικού MainPanel όλου του JFrame. */ 
        Row2Panel.setLayout(new BoxLayout(Row2Panel, BoxLayout.Y_AXIS));
        Row2Panel.add(ImagePanel);
        Row2Panel.add(ButtonPanel);
                
        MainPanel.setLayout(new GridLayout(2,0));
        MainPanel.add(ScrollPane);
        MainPanel.add(Row2Panel);
        
        add(MainPanel);
        setVisible(true);
    }

    /* Η συνάρτηση run τρέχει συνεχώς και ζητάει γεγονότα από το σύστημα ασφαλείας. */
    @Override
    public void run()
    {
        MyEvent event;
        while(true)
            try
            {
                /* Περιμένει πολυ λίγο για να ενημερωθεί σωστά η μεταβλητή online,
                   αλλιώς δεν αναγνωρίζει την αλλαγή της. */
                Thread.sleep(100);
                // Ελέγχουμε αν ο φύλακας είναι online.
                if(online)
                {
                    // Αν είναι, ζητάμε ένα γεγονός από το σύστημα ασφαλείας. 
                    event = op.getEvent(getName());
                    /* Ξαναελέγχουμε αν ο φύλακας είναι online επειδή υπάρχει περίπτωση
                       μέχρι το σύστημα να επιστρέψει κάποιο γεγονός ο φύλακας να
                       έχει απεγγραφεί. Αν είναι online το γεγονός αναγράφεται. */
                    if(online)
                        Append(event);
                }
            }
            catch (RemoteException | InterruptedException ex) {}
    }
    
    /* Η συνάρτηση Append με όρισμα ένα γεγονός, αναγράφει το γεγονός στο γραφικό και
       ανανεώνει και εμφανίζει την εικόνα του γεγονότος. */
    public void Append(MyEvent event)
    {
        TextArea.setText(TextArea.getText() + event.getName() + "\n");
        Image.setIcon(event.getImage());
        Image.repaint();
    }
    
    /* Η συνάρτηση Append χωρίς ορίσματα, αναγράφει στο γραφικό αν ο φύλακας είναι
       εγγεγραμμένος ή όχι στην υπηρεσία. */
    public void Append()
    {
        if(online)
            TextArea.setText(TextArea.getText() + "~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ONLINE ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~\n");
        else
            TextArea.setText(TextArea.getText() + "\n~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ OFFLINE ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~\n\n");
    }
}