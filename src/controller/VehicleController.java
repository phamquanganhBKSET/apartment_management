package controller;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class VehicleController {
	
	private Scene vehicleScene;
	
	public void setScene(Scene vehicleScene) {
		this.vehicleScene = vehicleScene;
	}

    @FXML
    private Button add;

    @FXML
    private Hyperlink back;

    @FXML
    void addVehicle(MouseEvent event) {

    }

    @FXML
    void goBack(MouseEvent event) {
    	try {
			Scene currScene = (Scene)((Node) event.getSource()).getScene();
			Stage currStage = (Stage)currScene.getWindow();
			currStage.setScene(vehicleScene);
			currStage.centerOnScreen();
			currStage.show();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
    }

}
