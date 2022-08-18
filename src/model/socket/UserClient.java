package model.socket;

import java.io.IOException;
import java.net.Socket;

import controller.UserMainPageController;

public class UserClient {
	private UserMainPageController mainController;
	private String host;
	private int port;
	private String clientName;
	
	// Constructor
	public UserClient(String host, int port, String clientName) {
		super();
		this.host = host;
		this.port = port;
		this.clientName = clientName;
	}
	
	public void setMainController(UserMainPageController mainController) {
		this.mainController = mainController;
	}
	
	// Run admin client
	public void execute() {
		try {
			Socket clientUser = new Socket(host, port);
			// Read thread
			ReadUserClient read = new ReadUserClient(clientUser, clientName, mainController);
			read.start(); // Start read thread
			// Write thread
			WriteUserClient write = new WriteUserClient(clientUser, clientName, mainController);
			write.start();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
}
