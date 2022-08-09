package model.socket;

import java.io.IOException;
import java.net.Socket;
import java.util.UUID;

public class AdminClient {

	private String host;
	private int port;
	private String clientName;
	
	public AdminClient(String host, int port, String clientName) {
		super();
		this.host = host;
		this.port = port;
		this.clientName = clientName;
	}
	
	private void execute() {
		try {
			Socket clientUser = new Socket(host, port);
			ReadAdminClient read = new ReadAdminClient(clientUser, clientName);
			read.start();
			WriteAdminClient write = new WriteAdminClient(clientUser, clientName);
			write.start();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
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
