package model.socket;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Vector;

public class Server {

	private int port;
	public static int adminCount = 0;
	public static int userCount = 0;
	public static ArrayList<Socket> listUserSocket = new ArrayList<>();
	public static ArrayList<String> listUserClientName = new ArrayList<>();
	public static ArrayList<Socket> listAdminSocket = new ArrayList<>();
	public static ArrayList<String> listAdminClientName = new ArrayList<>();
	public static ArrayList<String> toAdmin = new ArrayList<String>();
	public static ArrayList<String> toUser = new ArrayList<String>();
	
	public Server(int port) {
		super();
		this.port = port;
	}

	private void execute() {
		try {
			ServerSocket server = new ServerSocket(port);
			WriteServer write = new WriteServer();
			write.start();
			while(true) {
				Socket socket = server.accept();
				System.out.println("Client " + socket + " has connected!");
    			ReadServer read = new ReadServer(socket);
    			read.start();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public static void main(String[] args) {
        Server server = new Server(SocketLibrary.port);
        System.out.println("Server is ready!");
        server.execute();
	}

}
