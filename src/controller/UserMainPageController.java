package controller;

import java.io.File;
import java.nio.file.FileSystems;
import java.nio.file.Path;

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

public class UserMainPageController {
	
	private String gender = "female";


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
    	if (gender.equals("female")) {
        	edit.setStyle("	-fx-background-color: #FFE4E1;");
		}
    }
    
    @FXML
    void exitButton(MouseEvent event) {
    	if (gender.equals("female")) {
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
    
    @FXML
    void initialize() {
    	Path path = FileSystems.getDefault().getPath("").toAbsolutePath();
    	
        if (gender.equals("male")) {
        	Image im = new Image(String.valueOf(new File(path + "\\icon\\man" + ".png")));
        	avt_male.setImage(im);
        	male_layout.setStyle("	-fx-background-color: #EBC95E;");
		}
        else {
        	Image im = new Image(String.valueOf(new File(path + "\\icon\\beauty" + ".png")));
        	avt_male.setImage(im);
        	male_layout.setStyle("	-fx-background-color: #FFE4E1;");
        	edit.setStyle("	-fx-background-color: #FFB6C1;");
        }
//        edit.setOnMouseMoved(event -> {
//        	System.out.println(hold);
//        	edit.setStyle("	-fx-background-color: #FFE4E1;");
//        });
        //edit.setOnMouse(event -> edit.setStyle("	-fx-background-color: #FFB6C1;"));
    }

}
