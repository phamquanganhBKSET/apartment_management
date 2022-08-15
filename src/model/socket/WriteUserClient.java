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
				dos = new DataOutputStream(userClientSocket.getOutputStream());
				dos.writeUTF("Create User:" + clientName);
				while(true) {
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					
					if (this.mainController.getMessageToAdmin().length() > 1) {
						dos.writeUTF("From User:" + clientName + ":" + mainController.getMessageToAdmin() + ":To:all");
						Platform.runLater(() -> {
							mainController.setMessageToAdmin("");
						});
					}
				}
			} catch(IOException e) {
				try {
					dos.close();
					userClientSocket.close();
				} catch(IOException ex) {
					System.out.println("[UserClient - " + clientName + "] Disconnect to server!");
				}
			}
		}
	}
