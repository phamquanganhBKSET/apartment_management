package controller;

import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collections;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import model.ModelDichVu;

public class WaterController {

	private Scene waterScene;

	public void setScene(Scene waterScene) {
		this.waterScene = waterScene;
	}

	@FXML
	private ResourceBundle resources;

	@FXML
	private URL location;

	@FXML
	private Hyperlink back;

	@FXML
	private TableColumn<ModelDichVu, Date> col_month;

	@FXML
	private TableColumn<ModelDichVu, Integer> col_newNum;

	@FXML
	private TableColumn<ModelDichVu, Integer> col_oldNum;

	@FXML
	private TableColumn<ModelDichVu, CheckBox> col_paid;

	@FXML
	private TableColumn<ModelDichVu, Integer> col_total;

	@FXML
	private TableView<ModelDichVu> tableWater;

	@FXML
	private LineChart<?, ?> lineChart;

	// Go to previous stage when button goback is clicked
	@FXML
	void goBack(MouseEvent event) {
		try {
			Scene currScene = (Scene) ((Node) event.getSource()).getScene();
			Stage currStage = (Stage) currScene.getWindow();
			currStage.setScene(waterScene);
			currStage.centerOnScreen();
			currStage.show();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	ObservableList<ModelDichVu> listWater;

	// Function to load data from sql to display in textfileds    
	void load(Connection connection, Statement statement, String phong) {
		try {
			// Add data to listDichVu and listSummaries
			// Just add date and room to listSummaries
			String query = "select * from apartment_manager.dich_vu where apartment_manager.dich_vu.Ma_phong = " + phong
					+ " and apartment_manager.dich_vu.Ma_dich_vu = 2  order by apartment_manager.dich_vu.Thang desc";
			ResultSet rs = statement.executeQuery(query);
			listWater = FXCollections.observableArrayList();
//			listSummaries = new ArrayList<>();
			while (rs.next()) {
				listWater.add(new ModelDichVu(rs.getInt(1), rs.getInt(2), rs.getInt(3), rs.getInt(4), rs.getDate(5),
						rs.getBoolean(6)));
			}

			// link each columns to the corresponding variable of listSummary
			col_month.setCellValueFactory(new PropertyValueFactory<>("thang"));
			col_oldNum.setCellValueFactory(new PropertyValueFactory<>("soCu"));
			col_newNum.setCellValueFactory(new PropertyValueFactory<>("soMoi"));
			col_total.setCellValueFactory(new PropertyValueFactory<>("tienNuoc"));
			col_paid.setCellValueFactory(new PropertyValueFactory<>("select"));
			tableWater.setItems(listWater);

			// Line chart
			ObservableList<ModelDichVu> listReverse = FXCollections.observableArrayList();
			for (ModelDichVu i : listWater) {
				listReverse.add(i);
			}
			Collections.reverse(listReverse);
			XYChart.Series series = new XYChart.Series();

			for (ModelDichVu i : listReverse) {
				series.getData().add(new XYChart.Data(i.getThang().toString(), i.getTienNuoc()));
			}

			lineChart.getData().addAll(series);

		} catch (SQLException e) {
			e.printStackTrace();
			Logger.getLogger(WaterController.class.getName()).log(Level.SEVERE, null, e);
		}
	}

	@FXML
	void initialize() {

	}

}
