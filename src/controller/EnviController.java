package controller;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collections;
import java.sql.Date;
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

public class EnviController {
	
	private Scene enviScene;
	
	public void setScene(Scene enviScene) {
		this.enviScene = enviScene;
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
	private TableColumn<ModelDichVu, CheckBox> col_paid;

	@FXML
	private TableColumn<ModelDichVu, Integer> col_total;
	
	@FXML
	private TableView<ModelDichVu> tableEnvi;

	@FXML
	private LineChart<?, ?> lineChart;

    @FXML
    void goBack(MouseEvent event) {
    	try {
			Scene currScene = (Scene)((Node) event.getSource()).getScene();
			Stage currStage = (Stage)currScene.getWindow();
			currStage.setScene(enviScene);
			currStage.centerOnScreen();
			currStage.show();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
    }
    
    ObservableList<ModelDichVu> listEnvi;

	void load(Connection connection, Statement statement, String phong) {
		try {
			// Add data to listDichVu and listSummaries
			// Just add date and room to listSummaries
			String query = "select * from apartment_manager.dich_vu where apartment_manager.dich_vu.Ma_phong = " + phong
					+ " and apartment_manager.dich_vu.Ma_dich_vu = 3  order by apartment_manager.dich_vu.Thang desc";
			ResultSet rs = statement.executeQuery(query);
			listEnvi = FXCollections.observableArrayList();
//			listSummaries = new ArrayList<>();
			while (rs.next()) {
				listEnvi.add(new ModelDichVu(rs.getInt(1), rs.getInt(2), rs.getInt(3), rs.getInt(4), rs.getDate(5),
						rs.getBoolean(6)));
			}

			col_month.setCellValueFactory(new PropertyValueFactory<>("thang"));
			col_total.setCellValueFactory(new PropertyValueFactory<>("tienVS"));
			col_paid.setCellValueFactory(new PropertyValueFactory<>("select"));
			tableEnvi.setItems(listEnvi);

			// Line chart
			ObservableList<ModelDichVu> listReverse = FXCollections.observableArrayList();
			for (ModelDichVu i : listEnvi) {
				listReverse.add(i);
			}
			Collections.reverse(listReverse);
			XYChart.Series series = new XYChart.Series();

			for (ModelDichVu i : listReverse) {
				series.getData().add(new XYChart.Data(i.getThang().toString(), i.getTienVS()));
			}

			lineChart.getData().addAll(series);

		} catch (SQLException e) {
			e.printStackTrace();
			Logger.getLogger(WaterController.class.getName()).log(Level.SEVERE, null, e);
		}
	}


    @FXML
    void initialize() {
        assert back != null : "fx:id=\"back\" was not injected: check your FXML file 'EnviCharge.fxml'.";

    }

}
