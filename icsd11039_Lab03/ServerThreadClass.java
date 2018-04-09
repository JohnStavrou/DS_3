import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;

import javax.swing.JOptionPane;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.Socket;
import javax.swing.JOptionPane;
//icsd11039
public class ServerThreadClass extends Thread {
	// BufferedReader for reading the client-output

	static BufferedReader br;
	// BufferedWriter for sending data to the client
	static BufferedWriter bw = null;
	static DataOutputStream out = null;
	boolean flag;
	static Socket clientSocket = null;
	ServerThreadClass[] threads;
	static String choice;
	String TextTitle;

	public ServerThreadClass(String ch, String tt, Socket socket, ServerThreadClass[] threads) {
		choice = ch;
		clientSocket = socket;
		this.threads = threads;
		this.TextTitle = tt;
		System.out.println("Server: Thread Constructor");
	}

	@Override
	public void run() {
		try {
			System.out.println("Server: Run");
			manage();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public synchronized void manage() throws InterruptedException {
		flag = true;
		System.out.println("Server: Method manage");
		while (flag) {
			try {
				if (choice.equals("1")) {
					// a new empty file is created in the server's file
					System.out.println("createEmptyFile with name: " + " " + TextTitle);
					// with the bufferedReader we are taking from the client the
					// file's name
					if ((new File(TextTitle)).exists()) {
						JOptionPane.showMessageDialog(null, "File with same name already exists in server");
					} else {
						try {
							// we are creating a empty file in server's space
							// with
							// given
							// title
							bw = new BufferedWriter(new FileWriter(TextTitle));
							JOptionPane.showMessageDialog(null, "Empty file created in server");
						} catch (Exception e) {
							e.printStackTrace();
						}
						flag = false;
					}
				} else if (choice.equals("3")) {
					File f = new File(TextTitle);
					// we are writing and sending back to client the file's
					// title
					bw.write(TextTitle);
					// but only if the file's title exists
					if (!(f.exists())) {
						JOptionPane.showMessageDialog(null, "There is no such file in server");
					} else {
						JOptionPane.showMessageDialog(null, "File found and is ready to send to client");
						String tempText = "";
						String wholeText = "";
						// we are reading now the file's content
						try (BufferedReader br2 = new BufferedReader(new FileReader(f))) {
							// like EOF
							while ((tempText = br2.readLine()) != null) {
								// we are add the whole existing file's content
								// to a
								// string
								wholeText += tempText;
							}
						}
						// we are converting the String to bytes for a better
						// packet
						byte[] mybytearray = new byte[(int) f.length()];
						mybytearray = wholeText.getBytes();
						// out = new DataOutputStream(socket.getOutputStream());
						// we are sending back the file's content, bytes instead
						// of
						// string
						out.write(mybytearray);
						// safe close
						flag = false;
						notifyAll();
						out.close();
					}
				} else if (choice.equals("2")) {
					System.out.println("Server: Lets Delete");
					if (!(new File(TextTitle).exists())) {
						JOptionPane.showMessageDialog(null, "There is no such file in server");
					} else {
						System.out.println("Server: File found to be deleted");
						new File(TextTitle).delete();
						if (!(new File(TextTitle).exists())) {
							JOptionPane.showMessageDialog(null, "File found and deleted from server");
						} else {
							JOptionPane.showMessageDialog(null, "Delete operation is failed.");
						}
					}
					out = new DataOutputStream(clientSocket.getOutputStream());
					flag = false;
					// out.writeBytes(1 + "\n");
				} else if (choice.equals("4")) {
					// with the bufferedReader we are taking also a new content
					br = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
					String newContent = br.readLine();
					// to have access to the file
					File f = new File(TextTitle);
					if (!f.exists()) {
						JOptionPane.showMessageDialog(null, "There is no such file in server");
					} else {
						try {
							String tempText = "";
							String existingText = "";
							br = new BufferedReader(new FileReader(f));
							// we are add the whole existing file's content to a
							// string
							while ((tempText = br.readLine()) != null) {
								existingText += tempText;
							}
							System.out.println(existingText);
							// we are open the existing file in server
							FileWriter fw = new FileWriter(TextTitle);
							bw = new BufferedWriter(fw);
							// if something was written before, go to next line
							if (existingText.length() != 0) {
								// we are writing first the given string (file's
								// content)
								bw.write(existingText);
								bw.newLine();
							}
							// new we are adding the extra/new content to the
							// file
							// in server
							bw.append(newContent);
							// bufferedReader safe close
							bw.close();
							JOptionPane.showMessageDialog(null, "New content added successfully");
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
					flag = false;
				} else if (choice.equals("5")) {
					System.out.println("Server: Lets Update sth");
					// with bufferedReader we take from client the file's name
					br = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
					String content = br.readLine();
					if (!(new File(TextTitle)).exists()) {
						JOptionPane.showMessageDialog(null, "File not found in server");
					} else {
						try {
							FileWriter fw = new FileWriter(TextTitle);
							bw = new BufferedWriter(fw);
							bw.append(content);
							bw.close();
							JOptionPane.showMessageDialog(null, "New content overwritten in server");
							out.writeBytes(1 + "\n");
							// notifyAll();
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
					flag = false;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			wait();
		}
		if(flag==false)
		{
			notifyAll(); //safe exit
		}
	}
}
