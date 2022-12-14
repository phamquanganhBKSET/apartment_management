package model.socket;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ReadServer extends Thread {
	private Socket serverSocket;
	private String sms;

	// Constructor
	public ReadServer(Socket serverSocket) {
		super();
		this.serverSocket = serverSocket;
	}
	
	@Override
	public void run() {
		DataInputStream dis = null;
		
		try {
			dis = new DataInputStream(serverSocket.getInputStream()); // Input stream
			
			// First check if there are any messages that were not sent before
			// Send those first then read data from unput stream after that
			while(true) {
				if (Server.adminCount != 0) {
					for (int i = 0; i < Server.toAdmin.size(); i++) {
						sms = Server.toAdmin.get(i);
						String[] parts = sms.split(":");
						System.out.println(parts[parts.length-1]);
						
						if (parts[parts.length-1].equals("all")) {
							Server.toAdmin.remove(i);
							if (Server.toAdmin.size() > 0) {
								i -= 1;
							}
							for (Socket item : Server.listAdminSocket) {
								DataOutputStream dos = new DataOutputStream(item.getOutputStream());
								dos.writeUTF(sms);
							}
						} else {
							for (int j = 0; j < Server.listAdminClientName.size(); j++) {
								System.out.println(Server.listAdminClientName.get(j));
								if (Server.listAdminClientName.get(j).equals(parts[parts.length-1])) {
									Server.toAdmin.remove(i);
									if (Server.toAdmin.size() > 0) {
										i -= 1;
									}
									DataOutputStream dos = new DataOutputStream(Server.listAdminSocket.get(j).getOutputStream());
									dos.writeUTF(sms);
								}
							}
						}
						System.out.println(sms);
					}
				} 
				
				if (Server.userCount != 0) {
					for (int i = 0; i < Server.toUser.size(); i++) {
						sms = Server.toUser.get(i);
						String[] parts = sms.split(":");
						System.out.println(parts[parts.length-1]);
						
						if (parts[parts.length-1].equals("all")) {
							Server.toUser.remove(i);
							if (Server.toUser.size() > 0) {
								i -= 1;
							}
							for (Socket item : Server.listUserSocket) {
								DataOutputStream dos = new DataOutputStream(item.getOutputStream());
								dos.writeUTF(sms);
							}
						} else {
							for (int j = 0; j < Server.listUserClientName.size(); j++) {
								System.out.println(Server.listUserClientName.get(j));
								if (Server.listUserClientName.get(j).equals(parts[parts.length-1])) {
									Server.toUser.remove(i);
									if (Server.toUser.size() > 0) {
										i -= 1;
									}
									DataOutputStream dos = new DataOutputStream(Server.listUserSocket.get(j).getOutputStream());
									dos.writeUTF(sms);
								}
							}
						}
						System.out.println(sms);
					}
				}
				
				System.out.println("Server.toAdmin.size: " + Server.toAdmin.size());
				System.out.println("Server.toUser.size: " + Server.toUser.size());
				
				sms = dis.readUTF(); // Read data from input stream
				String[] parts = sms.split(":"); // Separate fields of message
				
				if (sms.contains("Create User:")) { //From User:username:Message:To:admin_username or From User:username:Message:To:all
    				Server.listUserSocket.add(serverSocket);
    				Server.listUserClientName.add(parts[1]);
    				Server.userCount++;
    				System.out.println("Create new user socket: " + parts[1] + ", Server.userCount = " + Server.userCount);
    				continue;
    			} else if (sms.contains("Create Admin:")) { //From Admin:admin_username:Message:To:username or From Admin:admin_username:Message:To:all
    				Server.listAdminSocket.add(serverSocket);
    				Server.listAdminClientName.add(parts[1]);
    				Server.adminCount++;
    				System.out.println("Create new admin socket: " + parts[1] + ", Server.aminCount = " + Server.adminCount);
    				continue;
    			}
				
				if (parts[0].equals("From User")) { // If the message is from user
					System.out.println(sms);
					if (Server.adminCount != 0) { // If there are some admins online
						if (parts[parts.length-1].equals("all")) { // Send to all admins
							for (Socket item : Server.listAdminSocket) {
								DataOutputStream dos = new DataOutputStream(item.getOutputStream());
								dos.writeUTF(sms);
							}
						} else {
							for (int i = 0; i < Server.listAdminClientName.size(); i++) {// Send to only one admin
								if (Server.listAdminClientName.get(i).equals(parts[parts.length-1])) {
									DataOutputStream dos = new DataOutputStream(Server.listAdminSocket.get(i).getOutputStream());
									dos.writeUTF(sms);
								}
							}
						}
					} else {
						// If there are not any admins online 
						System.out.println("There aren't any admins -> put sms to queue!");
						Server.toAdmin.add(sms);
					}
				} else { // If the message is from admin
					System.out.println(sms);
					if (Server.userCount != 0) {
						if (parts[parts.length-1].equals("all")) { // Send to all users
							for (Socket item : Server.listUserSocket) {
								DataOutputStream dos = new DataOutputStream(item.getOutputStream());
								dos.writeUTF(sms);
							}
						} else {
							for (int i = 0; i < Server.listUserClientName.size(); i++) { // Send to only one user
								if (Server.listUserClientName.get(i).equals(parts[parts.length-1])) {
									DataOutputStream dos = new DataOutputStream(Server.listUserSocket.get(i).getOutputStream());
									dos.writeUTF(sms);
								}
							}
						}
					} else {
						System.out.println("There aren't any users -> put sms to queue!");
						Server.toUser.add(sms);
					}
				}
			}
		} catch(IOException e) {
			try {
				dis.close();
				
				// Close sockets for admins
				for(int i = 0; i < Server.listAdminSocket.size(); i++) {
					if (serverSocket.getPort() == Server.listAdminSocket.get(i).getPort()) {
						Server.listAdminSocket.remove(i);
						Server.listAdminClientName.remove(i);
						Server.adminCount--;
						serverSocket.close();
						System.out.println("Close a admin socket! Server.adminCount = " + Server.adminCount);
						continue;
					}
				}
				
				// Close sockets for users
				for(int i = 0; i < Server.listUserSocket.size(); i++) {
					if (serverSocket.getPort() == Server.listUserSocket.get(i).getPort()) {
						Server.listUserSocket.remove(i);
						Server.listUserClientName.remove(i);
						Server.userCount--;
						serverSocket.close();
						System.out.println("Close a user socket! Server.userCount = " + Server.userCount);
						continue;
					}
				}
			} catch(IOException ex) {
				System.out.println("[SERVER] Disconnect!");
			}
		}
	}
}
