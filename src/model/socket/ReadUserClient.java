package model.socket;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;

public class ReadUserClient extends Thread {
	private String clientName;
	private Socket userClientSocket;

	public ReadUserClient(Socket userClientSocket, String clientName) {
		super();
		this.userClientSocket = userClientSocket;
		this.clientName = clientName;
	}
	
	@Override
	public void run() {
		DataInputStream dis = null;
		
		try {
			dis = new DataInputStream(userClientSocket.getInputStream());
			while(true) {
				String sms = dis.readUTF();
				System.out.println(sms);
			}
		} catch(IOException e) {
			try {
				dis.close();
				userClientSocket.close();
			} catch(IOException ex) {
				System.out.println("[UserClient - " + clientName + "] Disconnect to server!");
			}
		}
	}
}
