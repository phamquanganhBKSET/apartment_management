package model.socket;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class WriteServer extends Thread {
	
	@Override
	public void run() {
		DataOutputStream dos = null;
		Scanner sc = new Scanner(System.in);
		
		try {
			while(true) {
				String sms = sc.nextLine();
				for (Socket item : Server.listAdminSocket) {
					dos = new DataOutputStream(item.getOutputStream());
					dos.writeUTF(sms);
				}
				for (Socket item : Server.listUserSocket) {
					dos = new DataOutputStream(item.getOutputStream());
					dos.writeUTF(sms);
				}
			}
		} catch(IOException e) {
			try {
				dos.close();
			} catch(IOException ex) {
				System.out.println("[SERVER] Disconnect!");
			}
		}
	}
}
