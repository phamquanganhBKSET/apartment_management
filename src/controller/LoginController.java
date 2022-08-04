package controller;

import java.util.prefs.*;
import com.jfoenix.controls.JFXCheckBox;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.Statement;
import java.util.ResourceBundle;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

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
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class LoginController implements Initializable {
	private Connection connection;
	private Statement statement;
	private boolean showPass;
	
	private double offset_x;
    private double offset_y;
	
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Label close;

    @FXML
    private Hyperlink forgotPassword;

    @FXML
    private Label loginLabel;

    @FXML
    private Label minimize;

    @FXML
    private Button login;
    
    @FXML
    private PasswordField password;
    
    @FXML
    private TextField passwordText;

    @FXML
    private JFXCheckBox rememberMe;

    @FXML
    private TextField username;
    
    @FXML
    private ImageView show;
    
    @FXML
    private ImageView hide;

    
    @FXML
    private void handleClose(MouseEvent event) {
    	System.exit(0);
    }
    
    @FXML
    private void handleMinimize(MouseEvent event) {
    	Stage stage = (Stage) minimize.getScene().getWindow();
        stage.setIconified(true);
    }
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		showPass = false;
		password.setVisible(true);
		passwordText.setVisible(false);
		login.setDisable(true);
		username.textProperty().addListener((observable, oldValue, newValue) -> {
			login.setDisable(newValue.trim().isEmpty());
		});
		
		if (Preferences.userRoot().get("userID", null) != null) {
			username.setText(Preferences.userRoot().get("userID", null));
		}
		
		if (Preferences.userRoot().get("pass", null) != null) {
			password.setText(Preferences.userRoot().get("pass", null));
		}
		
		try {
			connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/apartment_manager", "root", library.password);
			statement = connection.createStatement();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	@FXML
	public void handleForgotPassword(ActionEvent e) {
		try {
			Scene currScene = (Scene)((Node) e.getSource()).getScene();
			Stage currStage = (Stage)currScene.getWindow();
			
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("/fxml/ForgotPassword.fxml"));
			Parent root = loader.load();
			
			ForgotPasswordController controller = loader.getController();
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("/css/forgotPassword.css").toExternalForm());
			
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
			controller.setLoginScene(currScene);
			currStage.show();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	@FXML
	public void handleShow(MouseEvent event) {
		show.setVisible(false);
		hide.setVisible(true);
		
		passwordText.setVisible(false);
		password.setVisible(true);
		String passNow = passwordText.getText();
		password.setText(passNow);
		
		showPass = false;
	}
	
	@FXML
	public void handleHide(MouseEvent event) {
		hide.setVisible(false);
		show.setVisible(true);
		
		password.setVisible(false);
		passwordText.setVisible(true);
		String passNow = password.getText();
		passwordText.setText(passNow);
		
		showPass = true;
	}
	
	@FXML
	public void handleLogin(ActionEvent e) throws IOException {
		String userID = username.getText();
		String pass;
		if (showPass) {
			pass = passwordText.getText();
		} else {
			pass = password.getText();
		}
		String sqlString = "select * from apartment_manager.admin where ID_admin = \'" + userID + "\'";
		
		try {
			ResultSet rs = statement.executeQuery(sqlString);
			if (rs.next()) {
				String passw = rs.getString(4);
				if (pass.equals(passw)) {
					
					Scene currScene = (Scene)((Node) e.getSource()).getScene();
					Stage currStage = (Stage)currScene.getWindow();
					
					FXMLLoader loader = new FXMLLoader();
					loader.setLocation(getClass().getResource("/fxml/AdminMainPage.fxml"));
					Parent root = loader.load();
					
					AdminMainPageController controller = loader.getController();
					Scene scene = new Scene(root);
					scene.getStylesheets().add(getClass().getResource("/css/admin.css").toExternalForm());
					
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
					controller.setLoginScene(currScene);
					controller.setUsername(userID);
					currStage.show();
					
					// Remember me
					if(rememberMe.isSelected()) {
				        // Let's validate the username field isn't empty (Optional)
				        if(!username.getText().isEmpty()){
				            Preferences pref = Preferences.userRoot();
				            String userName = username.getText();
				            String _pass_ = password.getText();
				            pref.put("userID", userName);
				            pref.put("pass", _pass_);
				        }
				    }
				} else {
					Alert alert = new Alert(Alert.AlertType.INFORMATION);
					alert.setTitle("Login Information");
					alert.setHeaderText("Login failed!");
					alert.setContentText("Username or Password is wrong!");
					alert.showAndWait();
				}
			} else {
				sqlString = "select * from apartment_manager.chu_so_huu where ID_chu_so_huu = \'" + userID + "\'";
				rs = statement.executeQuery(sqlString);
				if (rs.next()) {
					String passw = rs.getString(7);
					if (pass.equals(passw)) {
						Alert alert = new Alert(Alert.AlertType.INFORMATION);
						alert.setTitle("Login Information");
						alert.setHeaderText("Login successed!");
						alert.showAndWait();
						if(rememberMe.isSelected()) {
					        // Let's validate the username field isn't empty (Optional)
					        if(!username.getText().isEmpty()){
					            Preferences pref = Preferences.userRoot();
					            String userName = username.getText();
					            pref.put("userID", userName);
					        }
					    }
					} else {
						Alert alert = new Alert(Alert.AlertType.INFORMATION);
						alert.setTitle("Login Information");
						alert.setHeaderText("Login failed!");
						alert.setContentText("Username or Password is wrong!");
						alert.showAndWait();
					}
				} else {
					Alert alert = new Alert(Alert.AlertType.INFORMATION);
					alert.setTitle("Login Information");
					alert.setHeaderText("Login failed!");
					alert.setContentText("Username or Password is wrong!");
					alert.showAndWait();
				}
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
	}
}
