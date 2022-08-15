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
			dis = new DataInputStream(userClientSocket.getInputStream());
			while(true) {
				String sms = dis.readUTF();
				
				if(sms.length() > 1) {
					System.out.println(sms);
					Platform.runLater(() -> {
						mainController.handleNotify(sms);
					});
				}
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
