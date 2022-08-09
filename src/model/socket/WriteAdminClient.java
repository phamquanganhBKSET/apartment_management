package model.socket;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class WriteAdminClient extends Thread {
		private String clientName;
		private Socket adminClientSocket;

		public WriteAdminClient(Socket adminClientSocket, String clientName) {
			super();
			this.adminClientSocket = adminClientSocket;
			this.clientName = clientName;
		}
		
		@Override
		public void run() {
			DataOutputStream dos = null;
			Scanner sc = null;
			
			try {
				dos = new DataOutputStream(adminClientSocket.getOutputStream());
				sc = new Scanner(System.in);
				dos.writeUTF("Create Admin:" + clientName);
				while(true) {
					String sms = sc.nextLine();
					dos.writeUTF(sms);
				}
			} catch(IOException e) {
				try {
					dos.close();
					adminClientSocket.close();
				} catch(IOException ex) {
					System.out.println("[AdminClient - " + clientName + "] Disconnect to server!");
				}
			}
		}
	}
