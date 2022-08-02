package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class ChangePasswordController {
	
	private Scene changePassScene;

    @FXML
    private Hyperlink back;

    @FXML
    private Button changePassword;

    @FXML
    private Label close;

    @FXML
    private PasswordField confirmNewPassword;

    @FXML
    private Label loginLabel;

    @FXML
    private Label minimize;

    @FXML
    private PasswordField newPassword;

    @FXML
    private PasswordField newPassword1;

    @FXML
    void handleBack(ActionEvent event) {
    	try {
			Scene currScene = (Scene)((Node) event.getSource()).getScene();
			Stage currStage = (Stage)currScene.getWindow();
			currStage.setScene(changePassScene);
			currStage.centerOnScreen();
			currStage.show();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
    }

    @FXML
    void handleChangePassword(ActionEvent event) {

    }

    @FXML
    public void handleClose(MouseEvent event) {
    	System.exit(0);
    }

    @FXML
    public void handleMinimize(MouseEvent event) {
    	Stage stage = (Stage) minimize.getScene().getWindow();
        stage.setIconified(true);
    }
    
	public void setScene(Scene changePassScene) {
		this.changePassScene = changePassScene;
	}

}
