package model.socket;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;

public class ReadAdminClient extends Thread {
	private String clientName;
	private Socket adminClientSocket;

	public ReadAdminClient(Socket adminClientSocket, String clientName) {
		super();
		this.adminClientSocket = adminClientSocket;
		this.clientName = clientName;
	}
	
	@Override
	public void run() {
		DataInputStream dis = null;
		
		try {
			dis = new DataInputStream(adminClientSocket.getInputStream());
			while(true) {
				String sms = dis.readUTF();
				System.out.println(sms);
			}
		} catch(IOException e) {
			try {
				dis.close();
				adminClientSocket.close();
			} catch(IOException ex) {
				System.out.println("[AdminClient - " + clientName + "] Disconnect to server!");
			}
		}
	}
}
