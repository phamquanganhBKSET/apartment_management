package controller;

import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class UpdateDebtController {
	
	private Scene updateDebtScene;
	Connection connection;
	Statement statement;
	String room;
	
	public void setScene(Scene updateDebtScene) {
		this.updateDebtScene = updateDebtScene;
	}
	

    @FXML
    private CheckBox checkWater;

    @FXML
    private ImageView back;

    @FXML
    private CheckBox checkElec;

    @FXML
    private CheckBox checkEnvi;

    @FXML
    private CheckBox checkVeh;

    @FXML
    private Label close;

    @FXML
    private TextField electricity;

    @FXML
    private TextField envi;

    @FXML
    private Label minimize;

    @FXML
    private TextField month;

    @FXML
    private Button update;

    @FXML
    private TextField vehicle;

    @FXML
    private TextField water;

    @FXML
    private TextField year;
    
    @FXML
    private Button find;
    
    // Update room
    void load(String room)  {
    	this.room = room;
    }
    
    // Find data throw Date then set to textfields
    @FXML
    void findData(ActionEvent event) {
    	if (month.getText().isEmpty()) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setHeaderText("Month is empty!");
			alert.showAndWait();
		}
    	else if (year.getText().isEmpty()) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setHeaderText("Year is empty!");
			alert.showAndWait();
		}
    	else {

    		// Query data throw room and date
	    	String s = "select Ma_dich_vu, Da_dong from apartment_manager.dich_vu where Ma_phong = " + room 
	    			+ " and Thang = \'" + year.getText() + "-" + month.getText() + "-1\'";
	    	System.out.println(s);
	    	String s2 = "select Da_dong from apartment_manager.xe where Ma_phong = " + room 
	    			+ " and Thang = \'" + year.getText() + "-" + month.getText() + "-1\'";
	    	System.out.println(s2);
	    	try {
	    		// Set text to textfield
				ResultSet rs = statement.executeQuery(s);
				while (rs.next()) {
					int index = rs.getInt(1);
					boolean paid = rs.getBoolean(2);
					System.out.println("------------------");
					System.out.println("index: " + index);
					System.out.println("pad: " + paid);
					if (index == 1) {
						checkElec.setSelected(paid);
					}
					else if (index == 2) {
						checkWater.setSelected(paid);
					}
					else {
						checkEnvi.setSelected(paid);
					}
				}
				rs = statement.executeQuery(s2);
				boolean check = true;
				boolean hasData = false;
				while (rs.next()) {
					hasData = true;
					check = check & rs.getBoolean(1);
				}
				if (hasData) {
					checkVeh.setSelected(check);
				}
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	}
    }

    // Function Update data for database
    @FXML
    void actionUpdate(MouseEvent event) throws SQLException {
    	String veh = "false";
    	String elec = "false";
    	String wat = "false";
    	String env = "false";
    	if (checkElec.isSelected()) {
    		elec = "true";
		}
    	if (checkWater.isSelected()) {
			wat = "true";
		}
    	if (checkEnvi.isSelected()) {
			env = "true";
		}
    	if (checkVeh.isSelected()) {
			veh = "true";
		}
    	String s1 = "update apartment_manager.xe set Da_dong = " + veh + " where apartment_manager.xe.Ma_phong = " + room + " and apartment_manager.xe.Thang = \'" + year.getText() + "-" + month.getText() + "-1\'";
    	statement.executeUpdate(s1);
    	s1 = "update apartment_manager.dich_vu set Da_dong = " + elec + " where dich_vu.Ma_phong = " + room + " and Ma_dich_vu = 1" + " and apartment_manager.dich_vu.Thang = \'" + year.getText() + "-" + month.getText() + "-1\'";
    	statement.executeUpdate(s1);
    	s1 = "update apartment_manager.dich_vu set Da_dong = " + wat + " where dich_vu.Ma_phong = " + room + " and Ma_dich_vu = 2" + " and apartment_manager.dich_vu.Thang = \'" + year.getText() + "-" + month.getText() + "-1\'";
    	statement.executeUpdate(s1);
    	s1 = "update apartment_manager.dich_vu set Da_dong = " + env + " where dich_vu.Ma_phong = " + room + " and Ma_dich_vu = 3" + " and apartment_manager.dich_vu.Thang = \'" + year.getText() + "-" + month.getText() + "-1\'";
    	statement.executeUpdate(s1);
    	// Alert
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setHeaderText("Success!");
		alert.showAndWait();
		try {
			Scene currScene = (Scene)((Node) event.getSource()).getScene();
			Stage currStage = (Stage)currScene.getWindow();
			currStage.setScene(updateDebtScene);
			currStage.centerOnScreen();
			currStage.show();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
    }

	// Go to previous stage when button goback is clicked
    @FXML
    void goBack(MouseEvent event) {
    	try {
			Scene currScene = (Scene)((Node) event.getSource()).getScene();
			Stage currStage = (Stage)currScene.getWindow();
			currStage.setScene(updateDebtScene);
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

	// Minimize stage when - is clicked
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
			electricity.setDisable(true);
			water.setDisable(true);
			envi.setDisable(true);
			vehicle.setDisable(true);
		} catch (SQLException e) {
			e.printStackTrace();
		}
    }
}
