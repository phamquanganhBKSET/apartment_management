package model.socket;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server {

	private int port;
	public static int adminCount = 0; // Number of admin clients
	public static int userCount = 0; // Number of user clients
	public static ArrayList<Socket> listUserSocket = new ArrayList<>(); // List user client socket
	public static ArrayList<String> listUserClientName = new ArrayList<>(); // List user client name
	public static ArrayList<Socket> listAdminSocket = new ArrayList<>(); // List admin client socket
	public static ArrayList<String> listAdminClientName = new ArrayList<>(); // List admin client name
	public static ArrayList<String> toAdmin = new ArrayList<String>(); // Message sent to admin client in waiting list
	public static ArrayList<String> toUser = new ArrayList<String>(); // Message sent to user client in waiting list
	
	public Server(int port) {
		super();
		this.port = port;
	}

	private void execute() {
		try {
			ServerSocket server = new ServerSocket(port); // Create server socket
			WriteServer write = new WriteServer(); // Write thread
			write.start(); // Start write thread
			while(true) {
				Socket socket = server.accept(); // Wait for socket to connect
				System.out.println("Client " + socket + " has connected!");
    			ReadServer read = new ReadServer(socket); // Read thread
    			read.start(); // Start read thread
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public static void main(String[] args) {
        Server server = new Server(SocketLibrary.port); // Create server
        System.out.println("Server is ready!");
        server.execute();
	}

}
