package controller;

import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.Properties;
import java.util.Random;
import java.util.ResourceBundle;

import javax.mail.*;
import javax.mail.internet.*;

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

public class ForgotPasswordController implements Initializable {
	private double offset_x;
    private double offset_y;
    private final String emailFrom = "amanagement20212@gmail.com";
    private final String emailPass = "iwhfaqgtnghalsln";
    private Scene loginScene;
    private Connection connection;
	private Statement statement;
	
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Hyperlink back;

    @FXML
    private Label close;

    @FXML
    private TextField username;

    @FXML
    private Label loginLabel;

    @FXML
    private Label minimize;

    @FXML
    private Button requestReset;

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
		requestReset.setDisable(true);
		
		// Check if username field is not empty
		username.textProperty().addListener((observable, oldValue, newValue) -> {
			requestReset.setDisable(newValue.trim().isEmpty());
		});
		
		try {
			// Connect to database
			connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/apartment_manager", "root", library.password);
			statement = connection.createStatement();
		} catch (SQLException e) {
			e.printStackTrace();
		}
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
	
	// If request reset password
	@FXML
	public void handleRequestReset(MouseEvent e) {
		Random verificationCode = new Random();
		String userID= username.getText();
		String emailTo = "";
		String sqlString = "select * from apartment_manager.admin where ID_admin = \'" + userID + "\'";
		
		try {
			ResultSet rs = statement.executeQuery(sqlString);
			if (rs.next()) { // If admin
				emailTo = rs.getString(3);
				
				// Send verification code to email
				int vcode = verificationCode.nextInt((10000 - 100) + 1) + 100;
				String subject = "Apartment Management - Reset password";
				String body = "Your verification code is " + vcode;
				this.sendEmail(this.emailFrom, this.emailPass, emailTo, subject, body);
				
				try {
					Stage currStage = (Stage)((Node) e.getSource()).getScene().getWindow();
					
					// Go to verification email page
					FXMLLoader loader = new FXMLLoader();
					loader.setLocation(getClass().getResource("/fxml/VerifyEmailFGP.fxml"));
					Parent root = loader.load();
					
					VerifyEmailFGPController controller = loader.getController();
					Scene scene = new Scene(root);
					scene.getStylesheets().add(getClass().getResource("/css/verifyEmailFGP.css").toExternalForm());
					
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
					controller.setVerificationCode(vcode);
					controller.setEmail(emailFrom, emailTo, emailPass);
					controller.setUsername(userID);
					controller.setChangePassUser(false);
					currStage.show();
				} catch(Exception ex) {
					ex.printStackTrace();
				}
			} else { // Else if user
				sqlString = "select * from apartment_manager.chu_so_huu where ID_chu_so_huu = \'" + userID + "\'";
				rs = statement.executeQuery(sqlString);
				if (rs.next()) {
					emailTo = rs.getString(5);
					
					// Send verification code to email
					int vcode = verificationCode.nextInt((10000 - 100) + 1) + 100;
					String subject = "Apartment Management - Reset password";
					String body = "Your verification code is " + vcode;
					this.sendEmail(this.emailFrom, this.emailPass, emailTo, subject, body);
					
					try {
						Stage currStage = (Stage)((Node) e.getSource()).getScene().getWindow();
						
						FXMLLoader loader = new FXMLLoader();
						loader.setLocation(getClass().getResource("/fxml/VerifyEmailFGP.fxml"));
						Parent root = loader.load();
						
						// Go to verification email page
						VerifyEmailFGPController controller = loader.getController();
						Scene scene = new Scene(root);
						scene.getStylesheets().add(getClass().getResource("/css/verifyEmailFGP.css").toExternalForm());
						
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
						controller.setVerificationCode(vcode);
						controller.setEmail(emailFrom, emailTo, emailPass);
						controller.setUsername(userID);
						controller.setChangePassUser(true);
						currStage.show();
					} catch(Exception ex) {
						ex.printStackTrace();
					}
				} else {
					Alert alert = new Alert(Alert.AlertType.INFORMATION);
					alert.setTitle("Request reset password");
					alert.setHeaderText("Username " + userID + " doesn't exist!");
					alert.showAndWait();
				}
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
	}
	
	public void setLoginScene(Scene loginScene) {
		this.loginScene = loginScene;
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
