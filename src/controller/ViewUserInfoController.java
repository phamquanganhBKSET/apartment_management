package controller;

import java.io.File;
import java.net.URL;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class ViewUserInfoController implements Initializable {
	private String username;
	
	private Connection connection;
	private Statement statement;
	
	private double offset_x;
    private double offset_y;
    
    private AdminMainPageController mainController;
	
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField ID;

    @FXML
    private Button apply;

    @FXML
    private Hyperlink changePassword;

    @FXML
    private VBox chosenUserCard;

    @FXML
    private TextField citizenID;

    @FXML
    private Label citizenIDLabel;

    @FXML
    private Label citizenIDText;

    @FXML
    private Label close;

    @FXML
    private Button delete;

    @FXML
    private Button editID;

    @FXML
    private TextField email;

    @FXML
    private TextField fullName;

    @FXML
    private TextField gender;

    @FXML
    private ImageView image;

    @FXML
    private Label minimize;

    @FXML
    private TextField phone;

    @FXML
    private Label phoneLabel;

    @FXML
    private Label phoneText;

    @FXML
    private TextField rooms;

    @FXML
    private Separator sep1;

    @FXML
    private Separator sep2;

    @FXML
    private Label typeName;

    @FXML
    private Label userIDText;

    @FXML
    public void handleClose(MouseEvent event) {
    	Stage stage = (Stage) minimize.getScene().getWindow();
        stage.close();
    }

    @FXML
    public void handleMinimize(MouseEvent event) {
    	Stage stage = (Stage) minimize.getScene().getWindow();
        stage.setIconified(true);
    }

    @FXML
    public void handleApply(ActionEvent event) {
    	String newName = ID.getText();
    	
    	if (newName.equals(username)) {
    		return;
    	} else if (newName.equals("")) {
    		Alert alert = new Alert(Alert.AlertType.INFORMATION);
			alert.setTitle("Error");
			alert.setHeaderText("Edit failed!");
			alert.setContentText("ID field is empty!");
			alert.showAndWait();
			return;
    	}
    	
    	try {
    		String sqlString = "select * from chu_so_huu";
			ResultSet rs = statement.executeQuery(sqlString);
			
			while (rs.next()) {
				if (rs.getString(1).equals(newName) & (!newName.equals(username))) {
					Alert alert = new Alert(Alert.AlertType.INFORMATION);
					alert.setTitle("Error");
					alert.setHeaderText("Edit failed!");
					alert.setContentText("ID " + newName + " has already existed!");
					alert.showAndWait();
					return;
				}
			}
			
			System.out.println(username);
			
			sqlString = "SET FOREIGN_KEY_CHECKS=0;";
			statement.executeUpdate(sqlString);
			
			sqlString = "update apartment_manager.chu_so_huu "
					 + "set ID_chu_so_huu = \'" + newName + "\' "
					 + "where ID_chu_so_huu = \'" + username + "\'";
			
			statement.executeUpdate(sqlString);
			
			sqlString = "update apartment_manager.phong "
					 + "set ID_chu_so_huu = \'" + newName + "\' "
					 + "where ID_chu_so_huu = \'" + username + "\'";
			
			statement.executeUpdate(sqlString);
			
			username = newName;
			ID.setText(newName);
			ID.setDisable(true);
			
			editID.setText("Edit");
			
			Alert alert = new Alert(Alert.AlertType.INFORMATION);
			alert.setTitle("Update information");
			alert.setHeaderText("Successfully update!");
			alert.showAndWait();
			
			mainController.updateDataUser();
		} catch (SQLException e) {
			e.printStackTrace();
		}
    }
    
    @FXML
    public void handleDelete(ActionEvent e) {
    	try {
	    	String sqlString = "SET FOREIGN_KEY_CHECKS=0;";
			statement.executeUpdate(sqlString);
			
			sqlString = "delete from chu_so_huu where ID_chu_so_huu = \'" + username + "\'";
			statement.executeUpdate(sqlString);
			
			sqlString = "update apartment_manager.phong "
					 + "set ID_chu_so_huu = null, "
					 + "So_nguoi = null, "
					 + "Trang_thai_phong = 1 "
					 + "where ID_chu_so_huu = \'" + username + "\'";
			
			statement.executeUpdate(sqlString);
			
			mainController.updateDataUser();
			mainController.updateDataRoom();
			
			Stage stage = (Stage) minimize.getScene().getWindow();
			stage.close();
    	} catch(Exception ex) {
    		ex.printStackTrace();
    	}
    }

    @FXML
    public void handleChangePassword(ActionEvent e) {
    	try {
	    	Stage stage = new Stage();
	    	stage.initStyle(StageStyle.UNDECORATED);
	    	
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("/fxml/NewPassword.fxml"));
			Parent root = loader.load();
			
			NewPasswordController controller = loader.getController();
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
			controller.setUsername(username);
			controller.setChangePassUser(true);
			stage.show();
    	} catch (Exception ex) {
    		ex.printStackTrace();
    	}
    }

    @FXML
    public void handleEditID(ActionEvent event) {
    	apply.setDisable(false);
    	if (editID.getText().equals("Edit")) {
	    	ID.setDisable(false);
	    	ID.setText("");
	    	editID.setText("Exit");
    	} else {
    		ID.setDisable(true);
	    	ID.setText(username);
	    	editID.setText("Edit");
	    	apply.setDisable(true);
    	}
    }
    
	public void setUsername(String username) {
    	this.username = username;
    	ID.setText(username);
    	
    	try {
    		String imgSrc = "";
        	String color = "";
        	
			connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/apartment_manager", "root", library.password);
			statement = connection.createStatement();
			
			String sqlString;
			
			sqlString = "select * from chu_so_huu where ID_chu_so_huu = \'" + username + "\'";
			ResultSet rs = statement.executeQuery(sqlString);
			if (rs.next()) {
				citizenID.setText(rs.getString(2));
				fullName.setText(rs.getString(3));
				phone.setText(rs.getString(4));
				email.setText(rs.getString(5));
				gender.setText(rs.getString(6));
				
				Path path = FileSystems.getDefault().getPath("").toAbsolutePath();
				
				if (rs.getString(6).equals("Nam")) {
					imgSrc = path + "\\icon\\user_male.png";
					color = "00CED1";
				} else {
					imgSrc = path + "\\icon\\user_female.png";
					color = "FFC0CB";
				}
				
				userIDText.setText(username);
		    	Image img = new Image(String.valueOf(new File(imgSrc)));
		    	image.setImage(img);
		    	chosenUserCard.setStyle("-fx-background-color: #" + color);
		    	phoneText.setText(rs.getString(4));
			}
			ID.setDisable(true);
			citizenID.setDisable(true);
			fullName.setDisable(true);
			phone.setDisable(true);
			email.setDisable(true);
			gender.setDisable(true);
			
			List<String> listRoom = new ArrayList<>();
			sqlString = "select * from phong where ID_chu_so_huu = \'" + username + "\'";
			rs = statement.executeQuery(sqlString);
			
			while(rs.next()) {
				listRoom.add(rs.getString(1));
			}
			
			String roomText = "";
			
			for (int i = 0; i < listRoom.size(); i++) {
				roomText = roomText + listRoom.get(i) + "  ";
			}
			
			rooms.setText(roomText);
			rooms.setDisable(true);
		} catch (SQLException e) {
			e.printStackTrace();
		}
    }
	
	public void setMainController(AdminMainPageController mainController) {
		this.mainController = mainController;
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		apply.setDisable(true);
	}
}
