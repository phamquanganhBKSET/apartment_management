package controller;

import com.jfoenix.controls.JFXCheckBox;
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
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class LoginController implements Initializable {
	Connection connection;
	Statement statement;
	
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
    private JFXCheckBox rememberMe;

    @FXML
    private TextField username;
    
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
		login.setDisable(true);
		username.textProperty().addListener((observable, oldValue, newValue) -> {
			login.setDisable(newValue.trim().isEmpty());
		});
		
		try {
			connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/apartment_manager", "root", "root");
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
	public void handleLogin(ActionEvent e) {
		String userID = username.getText();
		String pass = password.getText();
		String sqlString = "select * from apartment_manager.admin where ID_admin = \'" + userID + "\'";
		
		try {
			ResultSet rs = statement.executeQuery(sqlString);
			if (rs.next()) {
				String passw = rs.getString(4);
				if (pass.equals(passw)) {
					Alert alert = new Alert(Alert.AlertType.INFORMATION);
					alert.setTitle("Login Information");
					alert.setHeaderText("Login successed!");
					alert.showAndWait();
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
