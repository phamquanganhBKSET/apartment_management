package controller;

import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class NewPasswordController implements Initializable {
	private String username;
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
    private Button changePassword;

    @FXML
    private Label close;

    @FXML
    private PasswordField confirmNewPassword;

    @FXML
    private Label loginLabel;

    @FXML
    private Label minimize;

    @FXML
    private PasswordField newPassword;

    @FXML
    public void handleBack(ActionEvent event) {
    	try {
			Scene currScene = (Scene)((Node) event.getSource()).getScene();
			Stage currStage = (Stage)currScene.getWindow();
			currStage.setScene(loginScene);
			currStage.centerOnScreen();
			currStage.show();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
    }

    @FXML
    public void handleChangePassword(ActionEvent event) {
    	String newPass = newPassword.getText();
    	String confirmPass = confirmNewPassword.getText();
    	
    	if (newPass.equals(confirmPass)) {
    		try {
    			String sqlString = "update apartment_manager.admin "
      			 		 		 + "set password = \'" + newPass + "\' "
      			 		 		 + "where ID_admin = \'" + this.username + "\'";
				statement.executeUpdate(sqlString);
				
				Alert alert = new Alert(Alert.AlertType.INFORMATION);
				alert.setTitle("Change password Information");
				alert.setHeaderText("Change password successed!");
				alert.showAndWait();
				
				try {
					Scene currScene = (Scene)((Node) event.getSource()).getScene();
					Stage currStage = (Stage)currScene.getWindow();
					currStage.setScene(loginScene);
					currStage.centerOnScreen();
					currStage.show();
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
    	} else {
    		Alert alert = new Alert(Alert.AlertType.INFORMATION);
			alert.setTitle("Change password Information");
			alert.setHeaderText("Change Password failed!");
			alert.setContentText("Password and re-entered password do not match!");
			alert.showAndWait();
			return;
    	}
    }

    @FXML
    public void handleClose(MouseEvent event) {
    	System.exit(0);
    }

    @FXML
    public void handleMinimize(MouseEvent event) {
    	Stage stage = (Stage) minimize.getScene().getWindow();
        stage.setIconified(true);
    }
    
    public void setLoginScene(Scene loginScene) {
		this.loginScene = loginScene;
	}
    
    public void setUsername(String username) {
    	this.username = username;
    }

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		changePassword.setDisable(true);
		newPassword.textProperty().addListener((observable, oldValue, newValue) -> {
			changePassword.setDisable(newValue.trim().isEmpty());
		});
		
		try {
			connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/apartment_manager", "root", library.password);
			statement = connection.createStatement();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
