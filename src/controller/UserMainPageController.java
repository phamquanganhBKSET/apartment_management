package controller;

import java.io.File;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import model.ModelDichVu;
import model.ModelSummary;
import model.ModelXe;
import model.socket.SocketLibrary;
import model.socket.UserClient;

public class UserMainPageController {

	private String mesageToAdmin = "";
	private Connection connection;
	private Statement statement;
	
		
	private String gender = "male";

	private double offset_x;
    private double offset_y;
    
    @FXML
    private ImageView avt_male;
    
    @FXML
    private Label username;

    @FXML
    private Hyperlink change_password;

    @FXML
    private TableColumn<ModelSummary, Integer> col_elec_bill;

    @FXML
    private TableColumn<ModelSummary, Integer> col_envi;

    @FXML
    private TableColumn<ModelSummary, Date> col_month;

    @FXML
    private TableColumn<ModelSummary, String> col_name;

    @FXML
    private TableColumn<ModelSummary, Integer> col_new_elec;

    @FXML
    private TableColumn<ModelSummary, Integer> col_old_elec;

    @FXML
    private TableColumn<ModelSummary, Integer> col_room;

    @FXML
    private TableColumn<ModelSummary, Integer> col_total;

    @FXML
    private TableColumn<ModelSummary, Integer> col_veh;

    @FXML
    private TableColumn<ModelSummary, Integer> col_water;
    
    @FXML
    private TableView<ModelSummary> tableSummary;

    @FXML
    private Button edit;

    @FXML
    private Label electricity;

    @FXML
    private Label email;

    @FXML
    private Label envi_charge;

    @FXML
    private Label fullname;

    @FXML
    private VBox male_layout;

    @FXML
    private Label phone;

    @FXML
    private Label room;

    @FXML
    private Label vehicle;

    @FXML
    private Label water;
    
    public void setMessageToAdmin(String mesageToAdmin) {
		this.mesageToAdmin = mesageToAdmin;
	}
	
	public String getMessageToAdmin() {
		return this.mesageToAdmin;
	}
    
	public void setUsername(String username) {
		this.username.setText(username);
	}

    @FXML
    public void changePass(ActionEvent e) {
    	try {
    		Stage stage = new Stage();
	    	stage.initStyle(StageStyle.UNDECORATED);
			
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("/fxml/ChangePassword.fxml"));
			Parent root = loader.load();
			
			ChangePasswordController controller = loader.getController();
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("/css/newPassword.css").toExternalForm());
			
			// Drag scene
			scene.setOnMousePressed(event -> {
				offset_x = event.getSceneX();
				offset_y = event.getSceneY();
			});
			scene.setOnMouseDragged(event -> {
				stage.setX(event.getScreenX() - offset_x);
				stage.setY(event.getScreenY() - offset_y);
			});
				        
			stage.setScene(scene);
			stage.centerOnScreen();
			stage.setResizable(false);
			controller.setUsername(username.getText());
			stage.show();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
    }

    @FXML
    void editProfile(ActionEvent e) {
    	try {
			Scene currScene = (Scene)((Node) e.getSource()).getScene();
			Stage currStage = (Stage)currScene.getWindow();
			
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("/fxml/EditInfor.fxml"));
			Parent root = loader.load();
			
			EditUserInforController controller = loader.getController();
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
			controller.load(username.getText());
			controller.setScene(currScene);
			currStage.show();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
    }
    
    @FXML
    void holdButton(MouseEvent event) {
    	if (gender != null && gender.equals("Nu")) {
        	edit.setStyle("	-fx-background-color: #FFE4E1;");
		}
    }
    
    @FXML
    void exitButton(MouseEvent event) {
    	if (gender != null && gender.equals("Nu")) {
    		edit.setStyle("	-fx-background-color: #FFB6C1;");
    	}
    }

    @FXML
    void viewElec(MouseEvent e) {
    	try {
			Scene currScene = (Scene)((Node) e.getSource()).getScene();
			Stage currStage = (Stage)currScene.getWindow();
			
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("/fxml/Electricity.fxml"));
			Parent root = loader.load();
			
			ElectricityController controller = loader.getController();
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("/css/table.css").toExternalForm());
			
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
			controller.load(connection, statement, room.getText());
			currStage.show();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
    }

    @FXML
    void viewEnvi(MouseEvent e) {
    	try {
			Scene currScene = (Scene)((Node) e.getSource()).getScene();
			Stage currStage = (Stage)currScene.getWindow();
			
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("/fxml/EnviCharge.fxml"));
			Parent root = loader.load();
			
			EnviController controller = loader.getController();
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("/css/table.css").toExternalForm());
			
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
			controller.load(connection, statement, room.getText());
			currStage.show();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
    }

    @FXML
    void viewVeh(MouseEvent e) {
    	try {
			Scene currScene = (Scene)((Node) e.getSource()).getScene();
			Stage currStage = (Stage)currScene.getWindow();
			
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("/fxml/Vehicle.fxml"));
			Parent root = loader.load();
			
			VehicleController controller = loader.getController();
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("/css/table.css").toExternalForm());
			
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
			controller.setMainController(this);
			controller.load(connection, statement, room.getText());
			currStage.show();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
    }

    @FXML
    void viewWater(MouseEvent e) {
    	try {
			Scene currScene = (Scene)((Node) e.getSource()).getScene();
			Stage currStage = (Stage)currScene.getWindow();
			
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("/fxml/Water.fxml"));
			Parent root = loader.load();
			
			WaterController controller = loader.getController();
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("/css/table.css").toExternalForm());
			
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
			controller.load(connection, statement, room.getText());
			currStage.show();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
    }
    
    
	ArrayList<ModelDichVu> listDichVu;
	ArrayList<ModelXe> listXe;
//	ArrayList<ModelSummary> listSummaries;
	ObservableList<ModelSummary> listSummaries;
	
    public void load (String transUser) {  
    	
        try {
        	// Update display
			String query = "select apartment_manager.chu_so_huu.Id_chu_so_huu, apartment_manager.chu_so_huu.Ten, apartment_manager.chu_so_huu.Email, apartment_manager.chu_so_huu.So_dien_thoai, apartment_manager.chu_so_huu.Gioi_tinh ,apartment_manager.phong.Ma_phong "
					+ "from apartment_manager.chu_so_huu, apartment_manager.phong "
					+ "where apartment_manager.chu_so_huu.Id_chu_so_huu = apartment_manager.phong.Id_chu_so_huu and apartment_manager.chu_so_huu.Id_chu_so_huu = \'" + transUser + "\'";
			ResultSet rs = statement.executeQuery(query);
			if (rs.next()) {
				username.setText(rs.getString(1));
				fullname.setText(rs.getString(2));
				email.setText(rs.getString(3));
				phone.setText(rs.getString(4));
				room.setText(rs.getString(6));
				gender = rs.getString(5);
		    	Path path = FileSystems.getDefault().getPath("").toAbsolutePath();
		    	if (gender != null && gender.equals("Nu")) {
		        	Image im = new Image(String.valueOf(new File(path + "\\icon\\beauty" + ".png")));
		        	avt_male.setImage(im);
		        	male_layout.setStyle("	-fx-background-color: #FFE4E1;");
		        	edit.setStyle("	-fx-background-color: #FFB6C1;");
		        }
			}
			
			// Add data to listDichVu and listSummaries		
			// Just add date and room to listSummaries
			query = "select * from apartment_manager.dich_vu where apartment_manager.dich_vu.Ma_phong = " + room.getText() + " order by apartment_manager.dich_vu.Thang desc;";
			rs = statement.executeQuery(query);
			listDichVu = new ArrayList<>();
//			listSummaries = new ArrayList<>();
			listSummaries = FXCollections.observableArrayList();
			ArrayList<String> dateArrayList = new ArrayList<>();
			while (rs.next()) {
				listDichVu.add(new ModelDichVu(rs.getInt(1), rs.getInt(2), rs.getInt(3), rs.getInt(4), rs.getDate(5), rs.getBoolean(6)));
				if (!dateArrayList.contains(rs.getDate(5).toString())) {
					dateArrayList.add(rs.getDate(5).toString());
					listSummaries.add(new ModelSummary(rs.getDate(5), rs.getInt(1), 0, 0, 0, 0));
				}
			}
			
			// Add data to listXe
			query = "SELECT apartment_manager.xe.Ten_chu_xe, apartment_manager.xe.Ma_phong, apartment_manager.xe.Loai_xe, apartment_manager.xe.Bien_so_xe, apartment_manager.xe.Mau_sac, apartment_manager.xe.Thang, apartment_manager.xe.Da_dong, apartment_manager.don_gia_gui_xe.Don_gia"
					+ " FROM apartment_manager.xe, apartment_manager.don_gia_gui_xe" 
					+ " where apartment_manager.xe.Ma_phong = "+ room.getText() +" and apartment_manager.xe.Loai_xe = apartment_manager.don_gia_gui_xe.Loai_xe"
					+ " order by apartment_manager.xe.Thang desc;";
					
			rs = statement.executeQuery(query);
			listXe = new ArrayList<>();
			while (rs.next()) {
				listXe.add(new ModelXe(rs.getString(1), rs.getInt(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getDate(6), rs.getBoolean(7), rs.getInt(8)));
			}
			
			// Update data for listSummaries
			for (ModelSummary sum : listSummaries) {
				for (ModelDichVu dv : listDichVu) {
					if (sum.getThang().compareTo(dv.getThang()) == 0) {
						System.out.println("==========================================");
						System.out.println("\nThang " + sum.getThang());
						System.out.println(dv.getTien());
						if (dv.getMaDichVu() == 1) {
							System.out.println("Tien dien");
							sum.updateElec(dv.getTien());
						} else if (dv.getMaDichVu() == 2) {
							System.out.println("Tien nuoc");
							sum.updateWater(dv.getTien());
						} else if (dv.getMaDichVu() == 3) {
							System.out.println("Tien VS");
							sum.updateEnvi(dv.getTien());
						}
					}
				}
				for (ModelXe xe : listXe) {
					if (sum.getThang().compareTo(xe.getThang()) == 0) {
						sum.updateVehicle(xe.getTienXe());
						System.out.println("Tien xe " + xe.getTienXe());
					}
				}
				sum.updateTotal();
				System.out.println("----------------------------");
				System.out.println("thang " + sum.getThang());
				System.out.println("phong " + sum.getRoom());
				System.out.println("dien " +sum.getElectricityBill());
				System.out.println("vesinh " + sum.getEnviCharges());
				System.out.println("nuoc " + sum.getWaterBill());
				System.out.println("xe " + sum.getVehicle());
				System.out.println("tong " + sum.getTotal());
				System.out.println("----------------------------");
			}
	    	
	    	col_month.setCellValueFactory(new PropertyValueFactory<>("thang"));
	    	col_room.setCellValueFactory(new PropertyValueFactory<>("room"));
	    	col_elec_bill.setCellValueFactory(new PropertyValueFactory<>("electricityBill"));
	    	col_water.setCellValueFactory(new PropertyValueFactory<>("waterBill"));
	    	col_envi.setCellValueFactory(new PropertyValueFactory<>("enviCharges"));
	    	col_veh.setCellValueFactory(new PropertyValueFactory<>("Vehicle"));
	    	col_total.setCellValueFactory(new PropertyValueFactory<>("Total"));
	    	tableSummary.setItems(listSummaries);
			
		} catch (SQLException e) {
			e.printStackTrace();
			Logger.getLogger(UserMainPageController.class.getName()).log(Level.SEVERE, null, e);
		}
        
        try {
			UserClient userCLient = new UserClient(SocketLibrary.host, SocketLibrary.port, transUser);
			userCLient.setMainController(this);
			System.out.println("User: " + transUser);
			userCLient.execute();
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
    
    @FXML
    void initialize() {
    	try {
			connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/apartment_manager", "root", library.password);
			statement = connection.createStatement();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
    }
    
    public void handleNotify(String sms) {
    	//From Admin:admin_username:Message:To:username
		String parts[] = sms.split(":");
		String message = parts[2];
		String fromAdmin = parts[1];
		
		Alert alert = new Alert(Alert.AlertType.INFORMATION);
		alert.setTitle("NOTIFY");
		alert.setHeaderText("Admin " + fromAdmin + " has " + message + "ed your request!");
		
		alert.showAndWait();
    }

}
