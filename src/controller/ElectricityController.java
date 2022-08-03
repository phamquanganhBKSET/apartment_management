package controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Hyperlink;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class ElectricityController {
	
	private Scene electricScene;
	
	public void setScene(Scene electricScene) {
		this.electricScene = electricScene;
	}

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Hyperlink back;

    @FXML
    void goBack(MouseEvent event) {
    	try {
			Scene currScene = (Scene)((Node) event.getSource()).getScene();
			Stage currStage = (Stage)currScene.getWindow();
			currStage.setScene(electricScene);
			currStage.centerOnScreen();
			currStage.show();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
    }

    @FXML
    void initialize() {
        assert back != null : "fx:id=\"back\" was not injected: check your FXML file 'Electricity.fxml'.";
       
    }

}
