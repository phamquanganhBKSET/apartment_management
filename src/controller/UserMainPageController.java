package controller;

import java.io.File;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.ModelDichVu;
import model.ModelSummary;
import model.ModelXe;

public class UserMainPageController {
	private Connection connection;
	private Statement statement;
	
		
	private String gender = "male";

	private double offset_x;
    private double offset_y;
    
    @FXML
    private ImageView avt_male;

    @FXML
    private Hyperlink change_password;

    @FXML
    private TableColumn<?, ?> col_elec_bill;

    @FXML
    private TableColumn<?, ?> col_envi;

    @FXML
    private TableColumn<?, ?> col_month;

    @FXML
    private TableColumn<?, ?> col_name;

    @FXML
    private TableColumn<?, ?> col_new_elec;

    @FXML
    private TableColumn<?, ?> col_old_elec;

    @FXML
    private TableColumn<?, ?> col_room;

    @FXML
    private TableColumn<?, ?> col_total;

    @FXML
    private TableColumn<?, ?> col_veh;

    @FXML
    private TableColumn<?, ?> col_water;

    @FXML
    private Button edit;
    
    @FXML
    private Label username;

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
    
	public void setUsername(String username) {
		this.username.setText(username);
	}

    @FXML
    void changePass(MouseEvent e) {
    	try {
			Scene currScene = (Scene)((Node) e.getSource()).getScene();
			Stage currStage = (Stage)currScene.getWindow();
			
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
	        	currStage.setX(event.getScreenX() - offset_x);
	        	currStage.setY(event.getScreenY() - offset_y);
	        });
	        
			currStage.setScene(scene);
			currStage.centerOnScreen();
			currStage.setResizable(false);
			controller.setScene(currScene);
			currStage.show();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
    }

    @FXML
    void editProfile(MouseEvent e) {
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
			//scene.getStylesheets().add(getClass().getResource("/css/AddAdmin.css").toExternalForm());
			
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
			//scene.getStylesheets().add(getClass().getResource("/css/AddAdmin.css").toExternalForm());
			
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
			//scene.getStylesheets().add(getClass().getResource("/css/AddAdmin.css").toExternalForm());
			
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
			//scene.getStylesheets().add(getClass().getResource("/css/AddAdmin.css").toExternalForm());
			
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
			currStage.show();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
    }
    
    
	ArrayList<ModelDichVu> listDichVu;
	ArrayList<ModelXe> listXe;
	ArrayList<ModelSummary> listSummaries;
	
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
			listSummaries = new ArrayList<>();
			ArrayList<String> dateArrayList = new ArrayList<>();
			while (rs.next()) {
				listDichVu.add(new ModelDichVu(rs.getInt(1), rs.getInt(2), rs.getInt(3), rs.getInt(4), rs.getDate(5), rs.getBoolean(6)));
				if (!dateArrayList.contains(rs.getDate(5).toString())) {
					dateArrayList.add(rs.getDate(5).toString());
					listSummaries.add(new ModelSummary(rs.getDate(5), rs.getInt(1), 0, 0, 0, 0));
				}
			}
			
			// Add data to listXe
			query = "SELECT * FROM apartment_manager.xe where apartment_manager.xe.Ma_phong = " + room.getText();
			rs = statement.executeQuery(query);
			listXe = new ArrayList<>();
			while (rs.next()) {
				listXe.add(new ModelXe(rs.getString(1), rs.getInt(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getDate(6), rs.getBoolean(7)));
			}
			
			// Update data for listSummaries
			for (ModelSummary sum : listSummaries) {
				for (ModelDichVu dv : listDichVu) {
					if (sum.getThang().compareTo(dv.getThang()) == 0) {
						System.out.println("\nthangttt " + sum.getThang());
						System.out.println(dv.getTien());
						if (dv.getMaDichVu() == 1) {
							System.out.println("TIen dien");
							sum.updateElec(dv.getTien());
						} else if (dv.getMaDichVu() == 2) {
							System.out.println("TIen nuoc");
							sum.updateWater(dv.getTien());
						} else if (dv.getMaDichVu() == 3) {
							System.out.println("TIen VS");
							sum.updateEnvi(dv.getTien());
						}
					}
				}
				for (ModelXe xe : listXe) {
					if (sum.getThang().compareTo(xe.getThang()) == 0) {
						sum.updateVehicle(xe.getTienXe());
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
			}
			
			
		} catch (SQLException e) {
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

}
