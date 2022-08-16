package controller;

import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import model.ModelDichVu;
import model.ModelSummary;
import model.ModelXe;

public class VehicleController {
	
	private UserMainPageController mainController;
	private Scene vehicleScene;
	
	private double offset_x;
    private double offset_y;
	
	public void setScene(Scene vehicleScene) {
		this.vehicleScene = vehicleScene;
	}

    @FXML
    private Button add;
    
    @FXML
    private Button delete;

    @FXML
    private Hyperlink back;
    
    @FXML
    private TableColumn<ModelXe, String> col_carOwner;

    @FXML
    private TableColumn<ModelXe, String> col_color;

    @FXML
    private TableColumn<ModelXe, String> col_licensePlates;

    @FXML
    private TableColumn<ModelXe, Integer> col_money;

    @FXML
    private TableColumn<ModelXe, Date> col_month;

    @FXML
    private TableColumn<ModelXe, CheckBox> col_paid;

    @FXML
    private TableColumn<ModelXe, Integer> col_room;

    @FXML
    private TableColumn<ModelXe, String> col_type;

    @FXML
    private LineChart<?, ?> lineChart;

    @FXML
    private TableView<ModelXe> tableXe;
    
    String room;
    
	
	public void disableAdd(boolean dis) {
		if (dis) {
			add.setDisable(true);
		}
	}

    public void setMainController(UserMainPageController mainController) {
    	this.mainController = mainController;
    }
    
    @FXML
    void addVehicle(MouseEvent e) {
    	try {
	    	Scene currScene = (Scene)((Node) e.getSource()).getScene();
			Stage currStage = (Stage)currScene.getWindow();
			
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("/fxml/AddVehicle.fxml"));
			Parent root = loader.load();
			
			AddVehicleController controller = loader.getController();
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("/css/AddAdmin.css").toExternalForm());
			
			// Drag scene
			scene.setOnMousePressed(event -> {
	            offset_x = event.getSceneX();
	            offset_y = event.getSceneY();
	        });
	        scene.setOnMouseDragged(event -> {
	        	currStage.setX(event.getScreenX() - offset_x);
	        	currStage.setY(event.getScreenY() - offset_y);
	        });
	        
			currStage.setScene(scene);
			currStage.centerOnScreen();
			currStage.setResizable(false);
			controller.setScene(currScene);
			controller.setMainController(mainController);
			controller.setRoom(room);
			currStage.show();
    	} catch (Exception ex) {
    		ex.printStackTrace();
    	}
   }
    
    @FXML
    void deleteVehicle(MouseEvent e) {
    	try {
	    	Scene currScene = (Scene)((Node) e.getSource()).getScene();
			Stage currStage = (Stage)currScene.getWindow();
			
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("/fxml/DelVehicle.fxml"));
			Parent root = loader.load();
			
			DelVehicleController controller = loader.getController();
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("/css/AddAdmin.css").toExternalForm());
			
			// Drag scene
			scene.setOnMousePressed(event -> {
	            offset_x = event.getSceneX();
	            offset_y = event.getSceneY();
	        });
	        scene.setOnMouseDragged(event -> {
	        	currStage.setX(event.getScreenX() - offset_x);
	        	currStage.setY(event.getScreenY() - offset_y);
	        });
	        
			currStage.setScene(scene);
			currStage.centerOnScreen();
			currStage.setResizable(false);
			controller.setScene(currScene);
			controller.setMainController(mainController);
			controller.setRoom(room);
			currStage.show();
    	} catch (Exception ex) {
    		ex.printStackTrace();
    	}
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
    
    void load (Connection connection, Statement statement, String phong) {
    	try {
    		this.room = phong;
			// Add data to listXe
			String query = "SELECT apartment_manager.xe.Ten_chu_xe, apartment_manager.xe.Ma_phong, apartment_manager.xe.Loai_xe, apartment_manager.xe.Bien_so_xe, apartment_manager.xe.Mau_sac, apartment_manager.xe.Thang, apartment_manager.xe.Da_dong, apartment_manager.don_gia_gui_xe.Don_gia"
					+ " FROM apartment_manager.xe, apartment_manager.don_gia_gui_xe" 
					+ " where apartment_manager.xe.Ma_phong = "+ phong +" and apartment_manager.xe.Loai_xe = apartment_manager.don_gia_gui_xe.Loai_xe"
					+ " order by apartment_manager.xe.Thang desc;";
			ResultSet rs = statement.executeQuery(query);
			
			ObservableList<ModelXe> listXe = FXCollections.observableArrayList();
			ArrayList<ModelSummary> listSummaries = new ArrayList<>();
			ArrayList<String> dateArrayList = new ArrayList<>();

			while (rs.next()) {
				listXe.add(new ModelXe(rs.getString(1), rs.getInt(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getDate(6), rs.getBoolean(7), rs.getInt(8)));
				if (!dateArrayList.contains(rs.getDate(6).toString())) {
					dateArrayList.add(rs.getDate(6).toString());
					listSummaries.add(new ModelSummary(rs.getDate(6), Integer.valueOf(rs.getString(2)), 0, 0, 0, 0));
				}
			}
			
			// Update data for listSummaries
			for (ModelSummary sum : listSummaries) {
				for (ModelXe xe : listXe) {
					if (sum.getThang().compareTo(xe.getThang()) == 0) {
						System.out.println(xe.getTienXe());
						sum.updateVehicle(xe.getTienXe());
					}
				}
				sum.updateTotal();
			}
			
	    	col_carOwner.setCellValueFactory(new PropertyValueFactory<>("tenChuXe"));
	    	col_room.setCellValueFactory(new PropertyValueFactory<>("maPhong"));
	    	col_type.setCellValueFactory(new PropertyValueFactory<>("loaiXe"));
	    	col_licensePlates.setCellValueFactory(new PropertyValueFactory<>("bienSoXe"));
	    	col_color.setCellValueFactory(new PropertyValueFactory<>("mauSac"));
	    	col_month.setCellValueFactory(new PropertyValueFactory<>("thang"));
	    	col_paid.setCellValueFactory(new PropertyValueFactory<>("select"));
	    	col_money.setCellValueFactory(new PropertyValueFactory<>("tienXe"));
	    	tableXe.setItems(listXe);
	    	
	    	// Line chart
			
			ArrayList<ModelSummary> listReverse = new ArrayList<>();
			for (ModelSummary i : listSummaries) {
				listReverse.add(i);
			}
			
			Collections.reverse(listReverse);
			XYChart.Series series = new XYChart.Series();
			for(ModelSummary i : listReverse) {
				series.getData().add(new XYChart.Data(i.getThang().toString(), i.getTotal()));
			}
			

			lineChart.getData().addAll(series);
			
		} catch (Exception ex) {
			// TODO: handle exception
			ex.printStackTrace();
		}
    }

}
