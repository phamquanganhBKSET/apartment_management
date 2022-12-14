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
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class AddUserController implements Initializable{
	Connection conn;
	Statement stmt;
	String gender = "";
	
	private AdminMainPageController mainController;

    @FXML
    private ImageView back;

    @FXML
    private TextField citizenID;

    @FXML
    private Label close;
    
    @FXML
    private TextField email;

    @FXML
    private RadioButton female;

    @FXML
    private TextField fullName;

    @FXML
    private RadioButton male;

    @FXML
    private Label minimize;

    @FXML
    private PasswordField password;

    @FXML
    private TextField phoneNumber;

    @FXML
    private TextField roomNumber;

    @FXML
    private Button signUp;

    @FXML
    private TextField username;
    
    @FXML
    private TextField numPeople;


    public void setMainController(AdminMainPageController mainController) {
    	this.mainController = mainController;
    }

    // Close the stage when X is clicked    
    @FXML
    private void handleClose(MouseEvent event) {
    	Stage stage = (Stage) minimize.getScene().getWindow();
    	stage.close();
    }
    
    // Minimize the Stage when - is clicked
    @FXML
    private void handleMinimize(MouseEvent event) {
    	Stage stage = (Stage) minimize.getScene().getWindow();
        stage.setIconified(true);
    }
    
    // if male is clicked -> untick female clicked
    @FXML
    void maleClicked(MouseEvent event) {
    	if (male.isSelected()) {
			female.setSelected(false);
			gender = "Nam";
		}
    }


    // if femle is clicked -> untick male
    @FXML
    void femaleClicked(MouseEvent event) {
    	if (female.isSelected()) {
			male.setSelected(false);
			gender = "Nu";
		}
    }
    
    @FXML
    void goBack(MouseEvent event) {

    }
    
    // Function Signup new User account
    @FXML
    void ActionSignUp(MouseEvent event) {
    	// Alert if username field is empty
    	if (username.getText().isEmpty()) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setHeaderText("Username is empty!");
			alert.showAndWait();
		}
    	// Alert if citizenID field is empty
    	else if (citizenID.getText().isEmpty()) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setHeaderText("Citizen ID is empty!");
			alert.showAndWait();
		}
    	// Alert if fullName field is empty
    	else if (fullName.getText().isEmpty()) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setHeaderText("Full name is empty!");
			alert.showAndWait();
		}
    	// Alert if password field is empty
    	else if (password.getText().isEmpty()) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setHeaderText("Password is empty!");
			alert.showAndWait();
		}
    	// Alert if roomNumber field is empty
    	else if (roomNumber.getText().isEmpty()) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setHeaderText("Room number is empty!");
			alert.showAndWait();
		}
    	// Alert if numPeople field is empty
    	else if (numPeople.getText().isEmpty()) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setHeaderText("Number of people is empty!");
			alert.showAndWait();
		}
    	else {
    		// Add data into chu_so_huu
    		try {
    			String query = "select Id_chu_so_huu from Chu_so_huu where Id_chu_so_huu = \'" + username.getText() + "\'";
    			ResultSet rs = stmt.executeQuery(query);
                // if user name already exits -> WARNING
    			if (rs.next()) {
    				Alert alert = new Alert(AlertType.WARNING);
    				alert.setHeaderText("Username already exists!");
    				alert.showAndWait();
				}
    			else {
                    // Insert new user account into database
    				query = "select * from phong where Ma_phong = \'" + roomNumber.getText() + "\'";
        			rs = stmt.executeQuery(query);
    				if (rs.next()) {
    					// Alert if this room is full
    					if (rs.getInt(5) == 0) {
	    					Alert alert = new Alert(AlertType.WARNING);
	        				alert.setHeaderText("Room " + roomNumber.getText() + " is full now!");
	        				alert.showAndWait();
    					}
    					else {
    						stmt.executeUpdate("SET FOREIGN_KEY_CHECKS=0;");
    						
    						// Insert new record to table chu_so_huu
    	    				stmt.executeUpdate ("Insert into Chu_so_huu" 
    	        		       + "(Id_chu_so_huu, CCCD, Ten, So_dien_thoai, Email, Gioi_tinh, Password)"
    	    				   + "Values(\'" + username.getText() + "\',\'" + citizenID.getText() + "\',\'"
    	    				   + fullName.getText() + "\',\'" + phoneNumber.getText() + "\',\'" + email.getText() + "\',\'"
    	    				   + gender + "\',\'" + password.getText() +"\');");
    	    				
    	    				// Update record of table phong
    	    				stmt.executeUpdate ("Update Phong "
    	 	        		       + "set Id_chu_so_huu = \'" + username.getText() + "\', So_nguoi = " + numPeople.getText() + ", Trang_thai_phong = 0 "
    	 	        		       + "where Ma_phong = " + roomNumber.getText());
    	    				
    	    				// Alert
    	    				Alert alert = new Alert(AlertType.INFORMATION);
    	    				alert.setHeaderText("Success!");
    	    				alert.showAndWait();
    	    				
    	    				mainController.updateDataUser();
    	    				mainController.updateDataRoom();
    	    				
    	    				// Go back
    	    				System.out.println("goback");
    	    				
    	    				Stage stage = (Stage) minimize.getScene().getWindow();
    	    		    	stage.close();
        				}
        			} else {
        				Alert alert = new Alert(AlertType.WARNING);
        				alert.setHeaderText("Room number " + roomNumber.getText() + " doesn't exist!");
        				alert.showAndWait();
        			}
    			}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
    	
    }
    
    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
		try {
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/apartment_manager", "root", library.password);
			stmt = conn.createStatement();
		} catch (SQLException e) {
			e.printStackTrace();
		}
    }

}
