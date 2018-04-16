//icsd11039
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

import javax.swing.JOptionPane;

import java.net.ServerSocket;

public class ServerMain {
	// BufferedReader for reading the client-output
	static BufferedReader br = null;
	// BufferedWriter for sending data to the client
	static BufferedWriter bw = null;
	static int numberClient = 0;
	static DataOutputStream out = null;
	static final ServerThreadClass[] threads = new ServerThreadClass[10];
	static String UserChoice = "";
	static String TextTitle = "";
	static Socket Sock;

	public static void main(String args[]) throws IOException {
		ServerSocket server = new ServerSocket(4444);
		System.out.println("Server sets connection...");
		//Sock = server.accept();		
		while (true) {
			try {
				System.out.println();
				numberClient++;
				int i;
				Sock = server.accept();
				JOptionPane.showMessageDialog(null, "Server connected with client number: " + numberClient);
				br = new BufferedReader(new InputStreamReader(Sock.getInputStream()));
				bw = new BufferedWriter(new OutputStreamWriter(Sock.getOutputStream()));
				bw.write(numberClient);				
				// reads client's choice through the bufferedReader				
				// reads client's choice
				UserChoice = br.readLine();
				TextTitle = br.readLine();
				for (i = 0; i < 10; i++) {
					if (threads[i] == null) {
						System.out.println("Created ServerThread " + i);
						(threads[i] = new ServerThreadClass(UserChoice, TextTitle, Sock, threads)).start();
						//threads[i].manage();
						break;
					}
				}
				if (i == 10) { // maximum number of allowed users
					JOptionPane.showMessageDialog(null, "Server is busy, maximum users... bye");
					server.close();
				}
				// server.close(); // safe server close
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
