package model.socket;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class WriteUserClient extends Thread {
		private String clientName;
		private Socket userClientSocket;

		public WriteUserClient(Socket userClientSocket, String clientName) {
			super();
			this.userClientSocket = userClientSocket;
			this.clientName = clientName;
		}
		
		@Override
		public void run() {
			DataOutputStream dos = null;
			Scanner sc = null;
			
			try {
				dos = new DataOutputStream(userClientSocket.getOutputStream());
				sc = new Scanner(System.in);
				dos.writeUTF("Create User:" + clientName);
				while(true) {
					String sms = sc.nextLine();
					dos.writeUTF(sms);
				}
			} catch(IOException e) {
				try {
					dos.close();
					userClientSocket.close();
				} catch(IOException ex) {
					System.out.println("[UserClient - " + clientName + "] Disconnect to server!");
				}
			}
		}
	}
