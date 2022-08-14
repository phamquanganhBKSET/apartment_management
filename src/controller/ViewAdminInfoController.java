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
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class ViewAdminInfoController implements Initializable {
	
	private String username;
	private String emailAdmin;
	private String phoneAdmin;
	
	private Connection connection;
	private Statement statement;
	
	private double offset_x;
    private double offset_y;
	
	@FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button apply;

    @FXML
    private Hyperlink changePass;

    @FXML
    private Label close;

    @FXML
    private Button editEmail;

    @FXML
    private Button editName;

    @FXML
    private Button editPhone;

    @FXML
    private TextField email;

    @FXML
    private Label minimize;

    @FXML
    private TextField name;

    @FXML
    private TextField phone;

    @FXML
    public void handleClose(MouseEvent e) {
    	Stage stage = (Stage) minimize.getScene().getWindow();
    	stage.close();
    }
    
    @FXML
    public void handleMinimize(MouseEvent e) {
    	Stage stage = (Stage) minimize.getScene().getWindow();
        stage.setIconified(true);
    }
    
    @FXML
    public void handleChangePassword(ActionEvent e) {
    	try {
	    	Stage stage = new Stage();
	    	stage.initStyle(StageStyle.UNDECORATED);
	    	
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("/fxml/ChangePassAdmin.fxml"));
			Parent root = loader.load();
			
			ChangePassAdminController controller = loader.getController();
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("/css/newPassword.css").toExternalForm());
			
			// Drag scene
			scene.setOnMousePressed(event -> {
	            offset_x = event.getSceneX();
	            offset_y = event.getSceneY();
	        });
	        scene.setOnMouseDragged(event -> {
	        	stage.setX(event.getScreenX() - offset_x);
	        	stage.setY(event.getScreenY() - offset_y);
	        });
	        
			stage.setScene(scene);
			stage.centerOnScreen();
			stage.setResizable(false);
			controller.setUsername(username);
			stage.show();
    	} catch (Exception ex) {
    		ex.printStackTrace();
    	}
    }

    @FXML
    public void handleEditEmail(ActionEvent event) {
    	if (editEmail.getText().equals("Edit")) {
	    	email.setDisable(false);
	    	email.setText("");
	    	editEmail.setText("Exit");
    	} else {
    		email.setDisable(true);
	    	email.setText(emailAdmin);
	    	editEmail.setText("Edit");
    	}
    }

    @FXML
    public void handleEditName(ActionEvent event) {
    	if (editName.getText().equals("Edit")) {
	    	name.setDisable(false);
	    	name.setText("");
	    	editName.setText("Exit");
    	} else {
    		name.setDisable(true);
	    	name.setText(username);
	    	editName.setText("Edit");
    	}
    }

    @FXML
    public void handleEditPhone(ActionEvent event) {
    	if (editPhone.getText().equals("Edit")) {
	    	phone.setDisable(false);
	    	phone.setText("");
	    	editPhone.setText("Exit");
    	} else {
    		phone.setDisable(true);
	    	phone.setText(phoneAdmin);
	    	editPhone.setText("Edit");
    	}
    }
    
    @FXML
    public void handleApply(ActionEvent event) {
    	String newName = name.getText();
    	String newEmail = email.getText();
    	String newPhone = phone.getText();
    	
    	if (newName.equals(username) & newEmail.equals(emailAdmin) & newPhone.equals(phoneAdmin)) {
    		return;
    	} else if (newName.equals("") | newEmail.equals("") | newPhone.equals("")) {
    		Alert alert = new Alert(Alert.AlertType.INFORMATION);
			alert.setTitle("Error");
			alert.setHeaderText("Edit failed!");
			alert.setContentText("One or many fields are empty!");
			alert.showAndWait();
			return;
    	}
    	
    	try {
    		String sqlString = "select * from admin";
			ResultSet rs = statement.executeQuery(sqlString);
			
			while (rs.next()) {
				if (rs.getString(1).equals(newName) & (!newName.equals(username))) {
					Alert alert = new Alert(Alert.AlertType.INFORMATION);
					alert.setTitle("Error");
					alert.setHeaderText("Edit failed!");
					alert.setContentText("Username " + newName + " has already existed!");
					alert.showAndWait();
					return;
				}
			}
			
			sqlString = "update admin "
					 + "set ID_admin = \'" + newName + "\', "
					 + "so_dien_thoai = " + newPhone + ", "
					 + "email = \'" + newEmail + "\' "
					 + "where ID_admin = \'" + username + "\'";
			
			statement.executeUpdate(sqlString);
			
			username = newName;
			name.setText(newName);
			name.setDisable(true);
			email.setText(newEmail);
			email.setDisable(true);
			phone.setText(newPhone);
			phone.setDisable(true);
			
			editEmail.setText("Edit");
			editName.setText("Edit");
			editPhone.setText("Edit");
			Alert alert = new Alert(Alert.AlertType.INFORMATION);
			alert.setTitle("Update information");
			alert.setHeaderText("Successfully update!");
			alert.showAndWait();
		} catch (SQLException e) {
			e.printStackTrace();
		}
    }

    public void setUsername(String username) {
    	this.username = username;
    	name.setText(username);
    	try {
			connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/apartment_manager", "root", library.password);
			statement = connection.createStatement();
			
			String sqlString;
			
			sqlString = "select * from admin where ID_admin = \'" + username + "\'";
			ResultSet rs = statement.executeQuery(sqlString);
			if (rs.next()) {
				phoneAdmin = rs.getString(2);
				emailAdmin = rs.getString(3);
			}
			phone.setText(phoneAdmin);
			email.setText(emailAdmin);
			name.setDisable(true);
			email.setDisable(true);
			phone.setDisable(true);
		} catch (SQLException e) {
			e.printStackTrace();
		}
    }
    
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		apply.setDisable(true);
		name.textProperty().addListener((observable, oldValue, newValue) -> {
			apply.setDisable(newValue.trim().isEmpty());
		});
		email.textProperty().addListener((observable, oldValue, newValue) -> {
			apply.setDisable(newValue.trim().isEmpty());
		});
		phone.textProperty().addListener((observable, oldValue, newValue) -> {
			apply.setDisable(newValue.trim().isEmpty());
		});
	}
}
