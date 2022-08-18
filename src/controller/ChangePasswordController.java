package controller;

import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class ChangePasswordController implements Initializable{
	private String username;
	private Connection connection;
	private Statement statement;

	@FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

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
    private PasswordField oldPassword;

    // Change user's password
    @FXML
    public void handleChangePassword(ActionEvent event) {
    	String oldPass = oldPassword.getText();
    	String newPass = newPassword.getText();
    	String confirmPass = confirmNewPassword.getText();
    	
    	// Get information of current user
    	String sqlString = "select * from chu_so_huu where ID_chu_so_huu = \'" + username + "\' ";
    	try {
    		ResultSet rs = statement.executeQuery(sqlString);
    		rs.next();
    		String currPass = rs.getString(7);
    		
    		// If old password is not true
    		if (!currPass.equals(oldPass)) {
    			Alert alert = new Alert(Alert.AlertType.INFORMATION);
    			alert.setTitle("Error");
    			alert.setHeaderText("Change password failed!");
    			alert.setContentText("Old password is not true!");
    			alert.showAndWait();
    			return;
    		}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
    	
    	// If new password and confirmed password are the same
    	if (newPass.equals(confirmPass)) {
    		try {
    			// Update new password to database
    			sqlString = "update apartment_manager.chu_so_huu "
      			 		  + "set password = \'" + newPass + "\' "
      			 		  + "where ID_chu_so_huu = \'" + this.username + "\' ";
				statement.executeUpdate(sqlString);
				
				Alert alert = new Alert(Alert.AlertType.INFORMATION);
				alert.setTitle("Change password Information");
				alert.setHeaderText("Change password successed!");
				alert.showAndWait();
				
				Stage stage = (Stage) minimize.getScene().getWindow();
				stage.close();
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

    // Close window
    @FXML
    public void handleClose(MouseEvent event) {
    	Stage stage = (Stage) minimize.getScene().getWindow();
    	stage.close();
    }

    // Minimize window
    @FXML
    public void handleMinimize(MouseEvent event) {
    	Stage stage = (Stage) minimize.getScene().getWindow();
        stage.setIconified(true);
    }
    
    public void setUsername(String username) {
    	this.username = username;
    }

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		changePassword.setDisable(true);
		
		// Check if new password field is not empty
		newPassword.textProperty().addListener((observable, oldValue, newValue) -> {
			changePassword.setDisable(newValue.trim().isEmpty());
		});
		
		// Check if old password field is not empty
		oldPassword.textProperty().addListener((observable, oldValue, newValue) -> {
			changePassword.setDisable(newValue.trim().isEmpty());
		});
		
		try {
			// Connect to database
			connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/apartment_manager", "root", library.password);
			statement = connection.createStatement();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
