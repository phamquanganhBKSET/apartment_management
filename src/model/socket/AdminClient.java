package model.socket;

import java.io.IOException;
import java.net.Socket;

import controller.AdminMainPageController;

public class AdminClient {
	private AdminMainPageController mainController;
	private String host;
	private int port;
	private String clientName;
	
	public AdminClient(String host, int port, String clientName) {
		super();
		this.host = host;
		this.port = port;
		this.clientName = clientName;
	}
	
	public void execute() {
		try {
			Socket clientUser = new Socket(host, port);
			ReadAdminClient read = new ReadAdminClient(clientUser, clientName, mainController);
			read.start();
			WriteAdminClient write = new WriteAdminClient(clientUser, clientName, mainController);
			write.start();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public void setMainController(AdminMainPageController mainController) {
		this.mainController = mainController;
	}

//	public static void main(String[] args) {
//		System.out.print("(Admin) Enter your username: ");
//		try {
//			AdminClient adminCLient = new AdminClient("localhost", 1234, clientName);
//			System.out.println("Admin: " + clientName);
//			adminCLient.execute();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
}
