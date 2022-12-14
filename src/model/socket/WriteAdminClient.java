package model.socket;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import controller.AdminMainPageController;
import javafx.application.Platform;

public class WriteAdminClient extends Thread {
		private AdminMainPageController mainController;
		private String clientName;
		private Socket adminClientSocket;

		// Constructor
		public WriteAdminClient(Socket adminClientSocket, String clientName, AdminMainPageController mainController) {
			super();
			this.adminClientSocket = adminClientSocket;
			this.clientName = clientName;
			this.mainController = mainController;
		}
		
		@Override
		public void run() {
			DataOutputStream dos = null;
			
			try {
				dos = new DataOutputStream(adminClientSocket.getOutputStream()); // Output stream
				dos.writeUTF("Create Admin:" + clientName);
				while(true) {
					try {
						Thread.sleep(100); // Delay 100 ms
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					if (mainController.getMessageToUser().length() > 1) { // Send message to user: From Admin:admin_username:Message:To:username
						dos.writeUTF("From Admin:" + clientName + ":" + mainController.getMessageToUser() + ":To:" + mainController.getToUser());
						Platform.runLater(() -> { // Reset message
							mainController.setMessageToUser("");
						});
					}
				}
			} catch(IOException e) {
				try {
					dos.close();
					adminClientSocket.close(); // Close socket
				} catch(IOException ex) {
					System.out.println("[AdminClient - " + clientName + "] Disconnect to server!");
				}
			}
		}
	}
