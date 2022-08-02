package controller;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class EditUserInforController {
	private Scene EditUserIn4;
	
    @FXML
    private ImageView back;

    @FXML
    private Button change;

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
    private TextField phoneNumber;

    @FXML
    private TextField roomNumber;

    @FXML
    private TextField username;

    @FXML
    void ActionChangeInfo(MouseEvent event) {

    }

    @FXML
    void goBack(MouseEvent event) {
    	try {
			Scene currScene = (Scene)((Node) event.getSource()).getScene();
			Stage currStage = (Stage)currScene.getWindow();
			currStage.setScene(EditUserIn4);
			currStage.centerOnScreen();
			currStage.show();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
    }

    @FXML
    private void handleClose(MouseEvent event) {
    	System.exit(0);
    }
    
    @FXML
    private void handleMinimize(MouseEvent event) {
    	Stage stage = (Stage) minimize.getScene().getWindow();
        stage.setIconified(true);
    }

    @FXML
    void maleClicked(MouseEvent event) {
    	if (male.isSelected()) {
			female.setSelected(false);
//			gender = male.getText();
		}
    }
    
    @FXML
    void femaleClicked(MouseEvent event) {
    	if (female.isSelected()) {
			male.setSelected(false);
		}
//    	gender = female.getText();
    }
    
	public void setScene(Scene EditUserIn4) {
		this.EditUserIn4 = EditUserIn4;
	}

}
