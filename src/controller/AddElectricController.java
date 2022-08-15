package controller;

import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
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

public class AddElectricController {
	private Scene addElecScene;
	Connection connection;
	Statement statement;
	
	
	public void setScene(Scene addElecScene) {
		this.addElecScene = addElecScene;
	}

	@FXML
    private Button add;

    @FXML
    private ImageView back;

    @FXML
    private Label close;

    @FXML
    private Label minimize;

    @FXML
    private TextField month;

    @FXML
    private TextField newElecNum;

    @FXML
    private TextField oldElecNum;

    @FXML
    private TextField roomNumber;
    
    void load(Connection connection, Statement statement, String room) throws SQLException {
    	this.connection = connection;
    	this.statement = statement;
    	roomNumber.setText(room);
		LocalDate currentime = LocalDate.now();
		month.setText(currentime.toString());
		String s = "select MAX(apartment_manager.dich_vu.Thang) from apartment_manager.dich_vu where apartment_manager.dich_vu.Ma_phong = " + room;
		ResultSet rs = statement.executeQuery(s);
		rs.next();
		System.out.println(rs.getString(1));
		if (rs.getString(1) == null) {
			oldElecNum.setText("0");
		}
		else {
			s = "select apartment_manager.dich_vu.So_moi from apartment_manager.dich_vu where Ma_phong = " + room 
					+ " and Ma_dich_vu = 1 and Thang = \'" + rs.getString(1) + "\'";
			System.out.println(s);
			ResultSet rs2 = statement.executeQuery(s);
			rs2.next();
			oldElecNum.setText(rs2.getString(1));
			System.out.println("CO PHONG NE");
		}
    }


	@FXML
	void actionAddElectric(MouseEvent event) {
		if (newElecNum.getText().isEmpty()) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setHeaderText("New Electric Number is empty!");
			alert.showAndWait();
		}
		else {
			// Add data into dich_vu table
			if ((Integer.valueOf(newElecNum.getText())) < Integer.valueOf(oldElecNum.getText())) {
				// Alert
				Alert alert = new Alert(AlertType.ERROR);
				alert.setHeaderText("Check Electric Number!");
				alert.showAndWait();
			}
			else {
				try {
					String s = "insert into apartment_manager.dich_vu(Ma_phong, Ma_dich_vu, So_cu, So_moi, Thang, Da_dong) "
							+ "values (" + roomNumber.getText() + ", 1, " + oldElecNum.getText() + ", " + newElecNum.getText() + ", \'" 
							+ month.getText() + "\', 0)";
					statement.executeUpdate(s);
					// Alert
					Alert alert = new Alert(AlertType.INFORMATION);
					alert.setHeaderText("Success!");
					alert.showAndWait();

					// Go back
					Scene currScene = (Scene) ((Node) event.getSource()).getScene();
					Stage currStage = (Stage) currScene.getWindow();
					currStage.setScene(addElecScene);
					currStage.centerOnScreen();
					currStage.show();

				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		}

	}

	@FXML
	void goBack(MouseEvent event) {
		try {
			Scene currScene = (Scene) ((Node) event.getSource()).getScene();
			Stage currStage = (Stage) currScene.getWindow();
			currStage.setScene(addElecScene);
			currStage.centerOnScreen();
			currStage.show();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	@FXML
	void handleClose(MouseEvent event) {
		Stage stage = (Stage) minimize.getScene().getWindow();
		stage.close();
	}

	@FXML
	void handleMinimize(MouseEvent event) {
		Stage stage = (Stage) minimize.getScene().getWindow();
		stage.setIconified(true);
	}

	@FXML
	void initialize() {
		roomNumber.setDisable(true);
		month.setDisable(true);
		oldElecNum.setDisable(true);
	}

}
