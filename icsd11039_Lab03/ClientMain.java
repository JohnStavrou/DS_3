import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

//icsd11039
@SuppressWarnings("serial")
public class ClientMain extends JFrame {

	static BufferedReader br;
	static int currentClient = 0;
	// client's choice
	public int choice;
	// window's contents for our frame
	private JPanel createFilePanel;
	private JPanel localhostInfoPanel;
	private JPanel downloadFilePanel;
	private JPanel addContentPanel;
	private JPanel deleteFilePanel;
	private JPanel updateContentPanel;
	private JPanel connectionInfoPanel;

	private JTextField createFileField;
	private JTextField addContentField;
	private JTextField deleteFileField;
	private JTextField downloadFileField;
	private JTextField updateContentField;

	private JLabel localhostInfoLabel;
	private JLabel addContentLabel;
	private JLabel connectionInfoLabel;
	private JLabel downloadFileLabel;
	private JLabel deleteFileLabel;
	private JLabel createFileLabel;
	private JLabel updateContentLabel;

	private JButton addContentButton;
	private JButton downloadFileButton;
	private JButton deleteFileButton;
	private JButton updateContentButton;
	private JButton createFileButton;

	// creates a socket for the client-server connection
	static public Socket connection;
	// creates a reference of MainWindow class to show the choices-window
	// we will take data from the server with an input
	static DataInputStream dis;
	// writes and sends data to servers
	static DataOutputStream out;

	public static void main(String[] args) {
		try {
			// if the connection is accepted, a socket is successfully created
			// and the client can use the socket to communicate with the server.
			connection = new Socket("localhost", 4444);
			// we will take data from the server with an input
			// dis = new DataInputStream(connection.getInputStream());
			br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			// we will write to an output source
			out = new DataOutputStream(connection.getOutputStream());
			// String numberClient = br.readLine();
			// System.out.println(numberClient);
			System.out.println("Client: Lets make the window");
			new ClientMain();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public ClientMain() throws IOException {
		super("Client Choices");
		// build and edit our frame/window
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setLayout(new GridLayout(7, 3));
		setMinimumSize(new Dimension(600, 600));
		setSize(700, 700);

		localhostInfoPanel = new JPanel();
		connectionInfoPanel = new JPanel();
		createFilePanel = new JPanel();
		downloadFilePanel = new JPanel();
		addContentPanel = new JPanel();
		deleteFilePanel = new JPanel();
		updateContentPanel = new JPanel();

		createFileField = new JTextField(10);
		downloadFileField = new JTextField(10);
		updateContentField = new JTextField(10);
		addContentField = new JTextField(10);
		deleteFileField = new JTextField(10);

		addContentButton = new JButton("PRESS");
		downloadFileButton = new JButton("PRESS");
		deleteFileButton = new JButton("PRESS");
		createFileButton = new JButton("PRESS");
		updateContentButton = new JButton("PRESS");

		connectionInfoLabel = new JLabel("Local socket address: " + connection.getLocalSocketAddress().toString());
		localhostInfoLabel = new JLabel("Server is connected to localhost in port 5555");
		createFileLabel = new JLabel("Create a file in server");
		addContentLabel = new JLabel("Add a content to a file in server");
		deleteFileLabel = new JLabel("Delete a file in server");
		updateContentLabel = new JLabel("Update a content in a file in server");
		downloadFileLabel = new JLabel("Download file from server to client");

		localhostInfoLabel.setForeground(Color.BLUE);
		localhostInfoLabel.setFont(new Font("TimesRoman", Font.BOLD, 20));
		connectionInfoLabel.setForeground(Color.BLUE);
		connectionInfoLabel.setFont(new Font("TimesRoman", Font.BOLD, 20));

		createFileLabel.setFont(new Font("TimesRoman", Font.BOLD, 22));
		addContentLabel.setFont(new Font("TimesRoman", Font.BOLD, 22));
		deleteFileLabel.setFont(new Font("TimesRoman", Font.BOLD, 22));
		updateContentLabel.setFont(new Font("TimesRoman", Font.BOLD, 22));
		downloadFileLabel.setFont(new Font("TimesRoman", Font.BOLD, 22));

		connectionInfoPanel.add(connectionInfoLabel);
		localhostInfoPanel.add(localhostInfoLabel);
		createFilePanel.add(createFileLabel);
		createFilePanel.add(createFileField);
		createFilePanel.add(createFileButton);

		downloadFilePanel.add(downloadFileLabel);
		downloadFilePanel.add(downloadFileField);
		downloadFilePanel.add(downloadFileButton);

		deleteFilePanel.add(deleteFileLabel);
		deleteFilePanel.add(deleteFileField);
		deleteFilePanel.add(deleteFileButton);

		addContentPanel.add(addContentLabel);
		addContentPanel.add(addContentField);
		addContentPanel.add(addContentButton);

		updateContentPanel.add(updateContentLabel);
		updateContentPanel.add(updateContentField);
		updateContentPanel.add(updateContentButton);

		add(connectionInfoPanel);
		add(localhostInfoPanel);
		add(createFilePanel);
		add(downloadFilePanel);
		add(deleteFilePanel);
		add(addContentPanel);
		add(updateContentPanel);

		// auto customization for a better window view
		pack();

		createFileField.setText("name of file");
		downloadFileField.setText("name of file");
		updateContentField.setText("name of file");
		addContentField.setText("name of file");
		deleteFileField.setText("name of file");
		System.out.println("Client: Lets manage");
		manage(true);
	}

	public synchronized void manage(boolean stat) {
		while (stat == true) {
			// mouse listeners for our fields in order to send text from the
			// field
			createFileField.addMouseListener(new MouseListener() {
				@Override
				public void mouseReleased(MouseEvent e) {
				}

				@Override
				public void mousePressed(MouseEvent e) {
					createFileField.setText("");
				}

				@Override
				public void mouseExited(MouseEvent e) {
				}

				@Override
				public void mouseEntered(MouseEvent e) {
				}

				@Override
				public void mouseClicked(MouseEvent e) {
				}
			});

			deleteFileField.addMouseListener(new MouseListener() {
				@Override
				public void mouseReleased(MouseEvent e) {
				}

				@Override
				public void mousePressed(MouseEvent e) {
					deleteFileField.setText("");
				}

				@Override
				public void mouseExited(MouseEvent e) {
				}

				@Override
				public void mouseEntered(MouseEvent e) {
				}

				@Override
				public void mouseClicked(MouseEvent e) {
				}
			});
			addContentField.addMouseListener(new MouseListener() {
				@Override
				public void mouseReleased(MouseEvent e) {
				}

				@Override
				public void mousePressed(MouseEvent e) {
					addContentField.setText("");
				}

				@Override
				public void mouseExited(MouseEvent e) {
				}

				@Override
				public void mouseEntered(MouseEvent e) {
				}

				@Override
				public void mouseClicked(MouseEvent e) {
				}
			});

			updateContentField.addMouseListener(new MouseListener() {
				@Override
				public void mouseReleased(MouseEvent e) {
				}

				@Override
				public void mousePressed(MouseEvent e) {
					updateContentField.setText("");
				}

				@Override
				public void mouseExited(MouseEvent e) {
				}

				@Override
				public void mouseEntered(MouseEvent e) {
				}

				@Override
				public void mouseClicked(MouseEvent e) {
				}
			});
			downloadFileField.addMouseListener(new MouseListener() {
				@Override
				public void mouseReleased(MouseEvent e) {
				}

				@Override
				public void mousePressed(MouseEvent e) {
					downloadFileField.setText("");
				}

				@Override
				public void mouseExited(MouseEvent e) {
				}

				@Override
				public void mouseEntered(MouseEvent e) {
				}

				@Override
				public void mouseClicked(MouseEvent e) {
				}
			});

			// we will send from the client the wanted file-title to server
			createFileButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					// we take the text from the field
					String fileName = createFileField.getText();
					if (fileName.length() == 0) {
						JOptionPane.showMessageDialog(null, "Please fill the gap");
					} else {
						try {
							fileName += ".txt";
							System.out.println("Client: You are trying to create a file named:" + fileName);
							// for creating the file we send to server number 1
							out.writeBytes(1 + "\n");
							// clean the DataOutputStream
							out.flush();
							// we send the filename to server with
							// DataOutputStream
							out.writeBytes(fileName + "\n");
							// clean the DataOutputStream
							out.flush();
							// remake the client-server connection
							// setVisible(false);
						} catch (IOException e1) {
							e1.printStackTrace();
						}
					}
				}
			});

			// we will send the wanted title in order to delete it in server's
			// space
			deleteFileButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					// we take the wanted text from the keyboard/field
					String fileName = deleteFileField.getText() + ".txt";
					if (fileName.length() == 0) {
						JOptionPane.showMessageDialog(null, "Please fill the gap");
					} else {
						try {
							System.out.println("Client: You are trying to delete a file named:" + fileName);
							// for creating file we send to server choice/number
							// 2
							out.writeBytes(2 + "\n");
							// clean the DataOutputStream
							out.flush();
							// we send the filename to server with
							// DataOutputStream
							out.writeBytes(fileName + "\n");
							out.flush();
						} catch (IOException e1) {
							e1.printStackTrace();
						}
					}
				}
			});
			// we will send the wanted title and some extra text
			// in order to add the extra text in the file in the server
			addContentButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					String fileName = addContentField.getText() + ".txt";
					if (fileName.length() == 0) {
						JOptionPane.showMessageDialog(null, "Please fill the gap");
					} else {
						try {
							out.writeBytes(4 + "\n");
							// clean the DataOutputStream
							out.flush();
							// for add some text to the file we send number 4
							out.writeBytes(fileName + "\n");
							out.flush();
							String extraContent = JOptionPane.showInputDialog(null, "Write the content you wish");
							// write/send also the extra text
							out.writeBytes(extraContent + "\n");
							out.flush();
							// setVisible(false);
						} catch (IOException e1) {
							e1.printStackTrace();
						}
					}
				}
			});

			// we will send the wanted title and some text
			// in order to overwrite with the extra text the file in the server
			updateContentButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					String fileName = updateContentField.getText() + ".txt";
					if (fileName.length() == 0) {
						JOptionPane.showMessageDialog(null, "Please fill the gap");
					} else {
						try {
							// send choice 5 to server
							out.writeBytes(5 + "\n");
							// clean the DataOutputStream
							out.flush();
							// write/send the file title to the server
							out.writeBytes(fileName + "\n");
							// clean the DataOutputStream;
							out.flush();
							String extraContent = JOptionPane.showInputDialog(null, "Write the content you wish");
							// write/send also the new text
							out.writeBytes(extraContent + "\n");
							out.flush();
							setVisible(false);
						} catch (IOException e1) {
							e1.printStackTrace();
						}
					}
				}
			});
			// we will send the wanted file-title to the server
			// and if the file exists, we will transfer it here in client
			downloadFileButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					String fileName = downloadFileField.getText() + ".txt";
					if (downloadFileField.getText().length() == 0) {
						JOptionPane.showMessageDialog(null, "Please fill the gap");
					} else {
						{
							try {
								// send choice 3 to server
								out.writeBytes(3 + "\n");
								// clean the DataOutputStream
								out.flush();
								// we send the title of the wanted-file to
								// server
								out.writeBytes(fileName + "\n");
								// clean the DataOutputStream
								out.flush();
							} catch (IOException e1) {
								e1.printStackTrace();
							}
						}
						// we will take back the file and its content
						try {
							// we take the content with bytes
							byte[] ArrayText = new byte[6022386];
							InputStream is = connection.getInputStream();
							FileOutputStream fos = new FileOutputStream(fileName);
							BufferedOutputStream bos = new BufferedOutputStream(fos);
							// we take/read the length of the array/file
							int bytesRead = is.read(ArrayText, 0, ArrayText.length);
							int current = bytesRead;
							// we read the bytes from server and create a string
							do {
								bytesRead = is.read(ArrayText, bytesRead, (ArrayText.length - current));
								if (bytesRead >= 0)
									current += bytesRead;
							} while (bytesRead > -1);// as long is open
							// write the text to the file
							bos.write(ArrayText, 0, current);
							// clear the bos
							bos.flush();
							bos.close();
							// setVisible(false);
						} catch (IOException e1) {
							e1.printStackTrace();
						}
					}
				}
			});
			stat = false;
		}
	}

}
