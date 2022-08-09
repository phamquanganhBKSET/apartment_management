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
	
	private Scene addVehicleScene;
	Connection connection;
	Statement statement;
	
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
    void setBicycle(MouseEvent event) {
    	if (bicycle.isSelected()) {
			car.setSelected(false);
			motorcycle.setSelected(false);
			vehicleType = "xe_dap";
		}
    }

    @FXML
    void setCar(MouseEvent event) {
    	if (car.isSelected()) {
    		bicycle.setSelected(false);
			motorcycle.setSelected(false);
			vehicleType = "o_to";
		}
    }

    @FXML
    void setMotor(MouseEvent event) {
    	if (motorcycle.isSelected()) {
    		bicycle.setSelected(false);
			car.setSelected(false);
			vehicleType = "xe_may";
		}
    }
    
	public void setRoom(String room) {
		roomNumber.setText(room);
	}

    @FXML
    void actionAddVehicle(MouseEvent event) {
    	if (owner.getText().isEmpty()) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setHeaderText("Owner is empty!");
			alert.showAndWait();
		}
    	else if (roomNumber.getText().isEmpty()) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setHeaderText("Room Number is empty!");
			alert.showAndWait();
		}
    	else if (vehicleType == "") {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setHeaderText("Vehicle type is empty!");
			alert.showAndWait();
		}
    	else {
    		// Add data into xe table
    		try {
    			LocalDate currentime = LocalDate.now();
    			int month = currentime.getMonthValue();
    			int year = currentime.getYear();
    			String s = "insert into apartment_manager.xe (Ten_chu_xe, Ma_phong, Loai_xe, Bien_so_xe, Mau_sac, Thang, Da_dong) "
    					+ "values (\'" + owner.getText() + "\', " + roomNumber.getText() + ",\'" + vehicleType + "\', \'"
    					+ licensePlates.getText() + "\', \'" + color.getText() + "\', \'" + String.valueOf(year)+"-"
    					+ String.valueOf(month)+"-01\', 0)";
    			statement.executeUpdate (s);
    			// Alert
    			Alert alert = new Alert(AlertType.INFORMATION);
    			alert.setHeaderText("Success!");
    			alert.showAndWait();
    			
    			// Go back
    			Scene currScene = (Scene)((Node) event.getSource()).getScene();
    			Stage currStage = (Stage)currScene.getWindow();
    			currStage.setScene(addVehicleScene);
    			currStage.centerOnScreen();
    			currStage.show();
    			
			} catch (Exception ex) {
				// TODO Auto-generated catch block
				ex.printStackTrace();
			}
		}
    	
    }

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

    @FXML
    void handleClose(MouseEvent event) {
    	System.exit(0);
    }

    @FXML
    void handleMinimize(MouseEvent event) {
    	Stage stage = (Stage) minimize.getScene().getWindow();
        stage.setIconified(true);
    }

    @FXML
    void initialize() {
		try {
			connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/apartment_manager", "root", library.password);
			statement = connection.createStatement();
			roomNumber.setDisable(true);
		} catch (SQLException e) {
			e.printStackTrace();
		}
    }

}
