package model.socket;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import controller.UserMainPageController;
import javafx.application.Platform;

public class WriteUserClient extends Thread {
		private UserMainPageController mainController;
		private String clientName;
		private Socket userClientSocket;

		// Constructor
		public WriteUserClient(Socket userClientSocket, String clientName, UserMainPageController mainController) {
			super();
			this.userClientSocket = userClientSocket;
			this.clientName = clientName;
			this.mainController = mainController;
		}
		
		@Override
		public void run() {
			DataOutputStream dos = null;
			
			try {
				dos = new DataOutputStream(userClientSocket.getOutputStream()); // Output stream
				dos.writeUTF("Create User:" + clientName);
				while(true) {
					try {
						Thread.sleep(1000); // Delay 1000 ms
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					
					if (this.mainController.getMessageToAdmin().length() > 1) { // Send message to user: From User:username:Message:To:all
						dos.writeUTF("From User:" + clientName + ":" + mainController.getMessageToAdmin() + ":To:all");
						Platform.runLater(() -> { // Reset message
							mainController.setMessageToAdmin("");
						});
					}
				}
			} catch(IOException e) {
				try {
					dos.close();
					userClientSocket.close(); // Close client socket
				} catch(IOException ex) {
					System.out.println("[UserClient - " + clientName + "] Disconnect to server!");
				}
			}
		}
	}
