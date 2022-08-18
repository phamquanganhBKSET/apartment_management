package model.socket;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;

import controller.UserMainPageController;
import javafx.application.Platform;

public class ReadUserClient extends Thread {
	private UserMainPageController mainController;
	private String clientName;
	private Socket userClientSocket;

	public ReadUserClient(Socket userClientSocket, String clientName, UserMainPageController addVehicleController) {
		super();
		this.userClientSocket = userClientSocket;
		this.clientName = clientName;
		this.mainController = addVehicleController;
	}
	
	public void setMainController(UserMainPageController mainController) {
		this.mainController = mainController;
	}
	
	@Override
	public void run() {
		DataInputStream dis = null;
		
		try {
			dis = new DataInputStream(userClientSocket.getInputStream()); // Input stream to socket
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
				userClientSocket.close(); // Close socket
			} catch(IOException ex) {
				System.out.println("[UserClient - " + clientName + "] Disconnect to server!");
			}
		}
	}
}
