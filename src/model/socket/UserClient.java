package model.socket;

import java.io.IOException;
import java.net.Socket;
import java.util.UUID;

public class UserClient {

	private String host;
	private int port;
	private String clientName;
	
	public UserClient(String host, int port, String clientName) {
		super();
		this.host = host;
		this.port = port;
		this.clientName = clientName;
	}
	
	public void execute() {
		try {
			Socket clientUser = new Socket(host, port);
			ReadUserClient read = new ReadUserClient(clientUser, clientName);
			read.start();
			WriteUserClient write = new WriteUserClient(clientUser, clientName);
			write.start();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public static void main(String[] args) {
		System.out.print("(User) Enter your username: ");
		String username = UUID.randomUUID().toString();
		try {
			UserClient userCLient = new UserClient("localhost", 1234, "test1");
			System.out.println("User: test");
			userCLient.execute();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
