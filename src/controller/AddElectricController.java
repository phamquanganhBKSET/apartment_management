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
	
	private ViewRoomInfoController mainController;

	public void setScene(Scene addElecScene) {
		this.addElecScene = addElecScene;
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
    private TextField electricNumber;

	@FXML
	private Label minimize;

	@FXML
	private TextField roomNumber;
	
	@FXML
	private TextField soMoi;
	
	public void setMainController(ViewRoomInfoController mainController) {
    	this.mainController = mainController;
    }

	@FXML
	void actionAddElectric(MouseEvent event) {
		if (roomNumber.getText().isEmpty()) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setHeaderText("Room Number is empty!");
			alert.showAndWait();
		}
		else {
			// Add data into dich_vu table
			try {
				LocalDate currentime = LocalDate.now();
				int month = currentime.getMonthValue();
				int year = currentime.getYear();
				String query = "select apartment_manager.dich_vu.So_moi from apartment_manager.dich_vu(Ma_phong, Ma_dich_vu, So_cu, So_moi, Thang, Da_dong) where apartment_manager.dich_vu.Ma_dich_vu=1 and apartment_manager.dich_vu.Thang=month-1";
				ResultSet rs = statement.executeQuery(query);
				int soCu = rs.getInt(3);
				String s = "insert into apartment_manager.dich_vu(Ma_phong, Ma_dich_vu, So_cu, So_moi, Thang, Da_dong) "
						+ "values (\'" + roomNumber.getText() + "\', 1, \'" + soCu + soMoi.getText() + "\', \'" +String.valueOf(year) + "-"
						+ String.valueOf(month) + "-01\', 0,)";
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
			connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/apartment_manager", "root",
					library.password);
			statement = connection.createStatement();
			roomNumber.setDisable(true);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
