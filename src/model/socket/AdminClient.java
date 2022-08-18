package model.socket;

import java.io.IOException;
import java.net.Socket;

import controller.AdminMainPageController;

public class AdminClient {
	private AdminMainPageController mainController;
	private String host;
	private int port;
	private String clientName;
	
	// Constructor
	public AdminClient(String host, int port, String clientName) {
		super();
		this.host = host;
		this.port = port;
		this.clientName = clientName;
	}
	
	// Run admin client
	public void execute() {
		try {
			Socket clientUser = new Socket(host, port);
			// Read thread
			ReadAdminClient read = new ReadAdminClient(clientUser, clientName, mainController);
			read.start(); // Start read thread
			// Write thread
			WriteAdminClient write = new WriteAdminClient(clientUser, clientName, mainController);
			write.start(); // Start write thread
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public void setMainController(AdminMainPageController mainController) {
		this.mainController = mainController;
	}
}
