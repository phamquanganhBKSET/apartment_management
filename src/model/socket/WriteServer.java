package model.socket;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class WriteServer extends Thread {
	
	@Override
	public void run() {
		DataOutputStream dos = null;
		Scanner sc = new Scanner(System.in); // Read from System.in
		
		try {
			while(true) {
				String sms = sc.nextLine();
				for (Socket item : Server.listAdminSocket) { // Send to all admins
					dos = new DataOutputStream(item.getOutputStream());
					dos.writeUTF(sms);
				}
				for (Socket item : Server.listUserSocket) { // Send to all users
					dos = new DataOutputStream(item.getOutputStream());
					dos.writeUTF(sms);
				}
			}
		} catch(IOException e) {
			try {
				dos.close(); // Close output stream
			} catch(IOException ex) {
				System.out.println("[SERVER] Disconnect!");
			}
		}
	}
}
