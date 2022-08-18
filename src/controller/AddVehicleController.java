package controller;

import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class AddVehicleController {
	
	private UserMainPageController mainController;
	private Scene addVehicleScene;
	Connection connection;
	Statement statement;
	
	// Function set scene
	public void setScene(Scene addVehicleScene) {
		this.addVehicleScene = addVehicleScene;
	}


    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button add;

    @FXML
    private ImageView back;

    @FXML
    private Label close;

    @FXML
    private TextField color;

    @FXML
    private TextField licensePlates;

    @FXML
    private Label minimize;

    @FXML
    private TextField owner;

    @FXML
    private TextField roomNumber;

    String vehicleType = "";

    @FXML
    private RadioButton motorcycle;
    
    @FXML
    private RadioButton bicycle;

    @FXML
    private RadioButton car;
    
    @FXML
    private TextField ticket;
    
    
    public void setMainController(UserMainPageController mainController) {
    	this.mainController = mainController;
    }
    
    // If bicycle is tick -> untick car and motor
    @FXML
    void setBicycle(MouseEvent event) {
    	if (bicycle.isSelected()) {
			car.setSelected(false);
			motorcycle.setSelected(false);
			vehicleType = "xe_dap";
		}
    }

    // If car is tick -> untick bycicle and motor
    @FXML
    void setCar(MouseEvent event) {
    	if (car.isSelected()) {
    		bicycle.setSelected(false);
			motorcycle.setSelected(false);
			vehicleType = "o_to";
		}
    }

    // If motor is tick -> untick bycicle and car
    @FXML
    void setMotor(MouseEvent event) {
    	if (motorcycle.isSelected()) {
    		bicycle.setSelected(false);
			car.setSelected(false);
			vehicleType = "xe_may";
		}
    }
    
    int ticketMax;
    
	// Function to load data from sql to display in textfileds    
	public void setRoom(String room) {
		roomNumber.setText(room);
		try {
			// Get max value of Ve_xe from table Xe in database
			String s = "select MAX(Ve_xe) from apartment_manager.xe";
			ResultSet rs = statement.executeQuery(s);
			if (rs.next()) {
				ticketMax = rs.getInt(1) + 1;
			}
			else {
				ticketMax = 1;
			}
			ticket.setText(String.valueOf(ticketMax));
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	// Function request a new vehicle from user and send to admin throw server
    @FXML
    void actionAddVehicle(MouseEvent event) {
    	// Alert if owner field is empty
    	if (owner.getText().isEmpty()) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setHeaderText("Owner is empty!");
			alert.showAndWait();
		}
    	// Alert if roomNumber field is empty
    	else if (roomNumber.getText().isEmpty()) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setHeaderText("Room Number is empty!");
			alert.showAndWait();
		}
    	// Alert if vehicleType field is empty
    	else if (vehicleType == "") {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setHeaderText("Vehicle type is empty!");
			alert.showAndWait();
		}
    	
    	else {
    		// Get current time
    		LocalDate currentime = LocalDate.now();
    		int month = currentime.getMonthValue();
    		int year = currentime.getYear();
	    	
    		// Set message which is sent to server
	    	String message = owner.getText() + ":" + roomNumber.getText() + ":" + vehicleType + ":"
	    				   + licensePlates.getText() + ":" + color.getText() + ":" + String.valueOf(year) + "-" + String.valueOf(month);
	    	
	    	mainController.setMessageToAdmin(message);
	    	
	    	// Alert wait
	    	Alert alert = new Alert(AlertType.INFORMATION);
			alert.setHeaderText("Please wait for admin's response!");
			alert.showAndWait();
			
			// Go back
			Scene currScene = (Scene)((Node) event.getSource()).getScene();
			Stage currStage = (Stage)currScene.getWindow();
			currStage.setScene(addVehicleScene);
			currStage.centerOnScreen();
			currStage.show();
    	}
    }

	// Go to previous stage when button goback is clicked
    @FXML
    void goBack(MouseEvent event) {
    	try {
			Scene currScene = (Scene)((Node) event.getSource()).getScene();
			Stage currStage = (Stage)currScene.getWindow();
			currStage.setScene(addVehicleScene);
			currStage.centerOnScreen();
			currStage.show();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
    }

	// Close stage when X is clicked
    @FXML
    void handleClose(MouseEvent event) {
    	System.exit(0);
    }

	// Minimize stage when X is clicked
    @FXML
    void handleMinimize(MouseEvent event) {
    	Stage stage = (Stage) minimize.getScene().getWindow();
        stage.setIconified(true);
    }

    @FXML
    void initialize() {
		try {
			// Connect to database
			connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/apartment_manager", "root", library.password);
			statement = connection.createStatement();
			roomNumber.setDisable(true);
			ticket.setDisable(true);
		} catch (SQLException e) {
			e.printStackTrace();
		}
    }

}
