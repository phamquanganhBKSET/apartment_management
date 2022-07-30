package controller;

import com.jfoenix.controls.JFXCheckBox;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class LoginController implements Initializable {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Label close;

    @FXML
    private Button forgotPassword;

    @FXML
    private Label loginLabel;

    @FXML
    private Label minimize;

    @FXML
    private PasswordField password;

    @FXML
    private JFXCheckBox rememberMe;

    @FXML
    private TextField username;
    
    @FXML
    private void handleClose(MouseEvent event) {
    	System.exit(0);
    }
    
    @FXML
    private void handleMinimize(MouseEvent event) {
    	Stage stage = (Stage) minimize.getScene().getWindow();
        stage.setIconified(true);
    }
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		
	}
	
}
