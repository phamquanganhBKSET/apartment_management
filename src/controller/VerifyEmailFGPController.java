package controller;

import java.net.URL;
import java.util.Date;
import java.util.Properties;
import java.util.Random;
import java.util.ResourceBundle;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class VerifyEmailFGPController implements Initializable {
	private double offset_x;
    private double offset_y;
	private int verificationCode;
	private String username;
	private String emailFrom;
	private String emailTo;
	private String emailPass;
	private Scene loginScene;
	private boolean changePassUser; // Specify reset password for admin or for user

	@FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Hyperlink back;

    @FXML
    private Label close;

    @FXML
    private Label loginLabel;

    @FXML
    private Label minimize;

    @FXML
    private Button requestReset;

    @FXML
    private Hyperlink sendBack;

    @FXML
    private TextField verifyCode;

    // Close window
    @FXML
    void handleClose(MouseEvent event) {
    	System.exit(0);
    }

    // Minimize window
    @FXML
    void handleMinimize(MouseEvent event) {
    	Stage stage = (Stage) minimize.getScene().getWindow();
        stage.setIconified(true);
    }

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		changePassUser = false;
	}
    
	// Back to login page
	@FXML
	public void handleBack(ActionEvent e) {
		try {
			Scene currScene = (Scene)((Node) e.getSource()).getScene();
			Stage currStage = (Stage)currScene.getWindow();
			currStage.setScene(loginScene);
			currStage.centerOnScreen();
			currStage.show();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	// Handle verify verification code
	@FXML
	public void handleVerify(ActionEvent e) {
		try {
			int vcode = Integer.parseInt(verifyCode.getText());
			System.out.println("vcode: " + vcode);
			if (vcode == this.verificationCode) { // Verification code is true
				System.out.println("Verification code is true!");
				try {
					Stage currStage = (Stage)((Node) e.getSource()).getScene().getWindow();
					
					// Go to reset password page
					FXMLLoader loader = new FXMLLoader();
					loader.setLocation(getClass().getResource("/fxml/NewPassword.fxml"));
					Parent root = loader.load();
					
					NewPasswordController controller = loader.getController();
					Scene scene = new Scene(root);
					scene.getStylesheets().add(getClass().getResource("/css/newPassword.css").toExternalForm());
					
					// Drag scene
					scene.setOnMousePressed(event -> {
			            offset_x = event.getSceneX();
			            offset_y = event.getSceneY();
			        });
			        scene.setOnMouseDragged(event -> {
			        	currStage.setX(event.getScreenX() - offset_x);
			        	currStage.setY(event.getScreenY() - offset_y);
			        });
			        
					currStage.setScene(scene);
					currStage.centerOnScreen();
					currStage.setResizable(false);
					controller.setLoginScene(loginScene);
					controller.setUsername(username);
					controller.setChangePassUser(changePassUser);
					currStage.show();
				} catch(Exception ex) {
					ex.printStackTrace();
				}
			} else { // Verification code is not true
				Alert alert = new Alert(Alert.AlertType.INFORMATION);
				alert.setTitle("Check verification code");
				alert.setHeaderText("Verification code is not true!");
				alert.showAndWait();
			}
		} catch (NumberFormatException ex) { // Number format error
			Alert alert = new Alert(Alert.AlertType.INFORMATION);
			alert.setTitle("Number format alert");
			alert.setHeaderText("The number you just entered is not an integer!");
			alert.showAndWait();
			return;
		}
	}
	
	// Send email again if requested
	@FXML
	public void handleSendAgain(ActionEvent e) {
		Random random = new Random();
		int vcode = random.nextInt((10000 - 100) + 1) + 100;
		String subject = "Apartment Management - Reset password";
		String body = "Your verification code is " + vcode;
		this.sendEmail(this.emailFrom, this.emailPass, this.emailTo, subject, body);
	}
	
	public void setLoginScene(Scene loginScene) {
		this.loginScene = loginScene;
	}
	
	public void setVerificationCode(int vcode) {
		this.verificationCode = vcode;
	}
	
	public void setEmail(String emailFrom, String emailTo, String emailPass) {
		this.emailFrom = emailFrom;
		this.emailTo = emailTo;
		this.emailPass = emailPass;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
	
	public void setChangePassUser(boolean changePassUser) {
		this.changePassUser = changePassUser;
	}
	
	// Send email
	public void sendEmail(String emailFrom, String pass, String emailTo, String subject, String body) {
		Properties properties = new Properties();
		properties.put("mail.smtp.host", "smtp.gmail.com"); // SMTP host
		properties.put("mail.smtp.port", "587"); //TLS Port
		properties.put("mail.smtp.auth", "true"); //enable authentication
		properties.put("mail.smtp.starttls.enable", "true"); //enable STARTTLS
		
		// Email authentication
		Authenticator auth = new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(emailFrom, pass);
            }
        };
        Session session = Session.getInstance(properties, auth);
        
        try {
        	MimeMessage msg = new MimeMessage(session);
        	msg.addHeader("Content-type", "text/HTML; charset=UTF-8");
            msg.addHeader("format", "flowed");
            msg.addHeader("Content-Transfer-Encoding", "8bit");
            msg.setFrom(new InternetAddress(emailFrom, "Apartment Management"));
            msg.setReplyTo(InternetAddress.parse(emailFrom, false));
            msg.setSubject(subject, "UTF-8");
            msg.setText(body, "UTF-8");
            msg.setSentDate(new Date());
            msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(emailTo, false));
            
            // Send email through transport layer
            Transport.send(msg);
            System.out.println("Gui mail thanh cong: " + body);
            
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
			alert.setTitle("Request reset password");
			alert.setHeaderText("We have sent the verification code to your email. Let's check!");
			alert.showAndWait();
        } catch(Exception ex) {
        	Alert alert = new Alert(Alert.AlertType.INFORMATION);
			alert.setTitle("Request reset password");
			alert.setHeaderText("Send email failed!");
			alert.showAndWait();
        	ex.printStackTrace();
        }
	}
}
