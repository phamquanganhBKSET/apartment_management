package model.socket;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;

import controller.AdminMainPageController;
import javafx.application.Platform;

public class ReadAdminClient extends Thread {
	private AdminMainPageController mainController;
	private String clientName;
	private Socket adminClientSocket;

	// Constructor
	public ReadAdminClient(Socket adminClientSocket, String clientName, AdminMainPageController mainController) {
		super();
		this.adminClientSocket = adminClientSocket;
		this.clientName = clientName;
		this.mainController = mainController;
	}
	
	@Override
	public void run() {
		DataInputStream dis = null;
		
		try {
			dis = new DataInputStream(adminClientSocket.getInputStream()); // Input stream to socket
			while(true) {
				String sms = dis.readUTF(); // Read data from socket
				
				if(sms.length() > 1) { // If sms is not empty
					System.out.println(sms);
					Platform.runLater(() -> { // Notify admin
						mainController.handleNotify(sms);
					});
				}
			}
		} catch(IOException e) {
			try {
				dis.close();
				adminClientSocket.close(); // Close socket
			} catch(IOException ex) {
				System.out.println("[AdminClient - " + clientName + "] Disconnect to server!");
			}
		}
	}
	
	public void setMainController(AdminMainPageController mainController) {
		this.mainController = mainController;
	}
}
