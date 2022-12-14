package controller;

import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class AddAdminController implements Initializable {
	private Connection conn;
	private Statement stmt;
	
	private AdminMainPageController mainController;
	
    @FXML
    private ImageView back;

    @FXML
    private Label close;

    @FXML
    private TextField email;

    @FXML
    private Label minimize;

    @FXML
    private PasswordField password;

    @FXML
    private TextField phoneNumber;

    @FXML
    private Button signUp;

    @FXML
    private TextField username;

    public void setMainController(AdminMainPageController mainController) {
    	this.mainController = mainController;
    }
    
    @FXML
    private void handleClose(MouseEvent event) { // Close window
    	Stage stage = (Stage) minimize.getScene().getWindow();
    	stage.close();
    }
    
    @FXML
    private void handleMinimize(MouseEvent event) { // Minimize window
    	Stage stage = (Stage) minimize.getScene().getWindow();
        stage.setIconified(true);
    }
    
    @FXML
    void goBack(MouseEvent event) {

    }
    
    @FXML
    void ActionSignUp(MouseEvent event) {
    	// Alert if username field is empty
    	if (username.getText().isEmpty()) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setHeaderText("Username is empty!");
			alert.showAndWait();
		}
    	// Alert if password field is empty
    	else if (password.getText().isEmpty()) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setHeaderText("Password is empty!");
			alert.showAndWait();
		}
    	// Alert if email field is empty
    	else if (email.getText().isEmpty()) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setHeaderText("Email is empty!");
			alert.showAndWait();
		}
      	else {
    		// Add data into admin
    		try {
    			// Check if username has existed
    			String query = "select ID_admin from admin where ID_admin = \'" + username.getText() + "\'";
    			ResultSet rs = stmt.executeQuery(query);
    			if (rs.next()) {
    				Alert alert = new Alert(AlertType.WARNING);
    				alert.setHeaderText("Username already exists!");
    				alert.showAndWait();
				}
    			else {
    				// Insert new record to database
    				stmt.executeUpdate ("Insert into admin" 
        		       + "(ID_admin, So_dien_thoai, Email, Password)"
    				   + "Values(\'" + username.getText() + "\',\'" + phoneNumber.getText() + "\',\'" 
    				   + email.getText() + "\',\'" + password.getText() +"\');");
    				
    				// Alert
    				Alert alert = new Alert(AlertType.INFORMATION);
    				alert.setHeaderText("Success!");
    				alert.showAndWait();
    				
    				mainController.updateDataUser();
    				
    				// Go back
    				System.out.println("goback");
    				
    				Stage stage = (Stage) minimize.getScene().getWindow();
    		    	stage.close();
    			}

			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
    }
    
    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
		try {
			// COnnect database
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/apartment_manager", "root", library.password);
			stmt = conn.createStatement();
		} catch (SQLException e) {
			e.printStackTrace();
		}
    }
}
