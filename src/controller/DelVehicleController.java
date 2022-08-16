package controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class DelVehicleController {

	
	private UserMainPageController mainController;
	private Scene delVehicleScene;
	Connection connection;
	Statement statement;
	
	public void setScene(Scene delVehicleScene) {
		this.delVehicleScene = delVehicleScene;
	}
	

    public void setMainController(UserMainPageController mainController) {
    	this.mainController = mainController;
    }
    


    @FXML
    private ImageView back;

    @FXML
    private Label close;

    @FXML
    private Button del;

    @FXML
    private Label minimize;

    @FXML
    private TextField roomNumber;

    @FXML
    private TextField ticket;
    

	public void setRoom(String room) {
		roomNumber.setText(room);
	}

    @FXML
    void actionDelVehicle(MouseEvent event) {
    	if (ticket.getText().isEmpty()) {
    		Alert alert = new Alert(AlertType.ERROR);
			alert.setHeaderText("Ticket is empty!");
			alert.showAndWait();
		}
    	else {
    		
    	}
    }

    @FXML
    void goBack(MouseEvent event) {
    	try {
			Scene currScene = (Scene)((Node) event.getSource()).getScene();
			Stage currStage = (Stage)currScene.getWindow();
			currStage.setScene(delVehicleScene);
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
