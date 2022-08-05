package controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collections;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import model.ModelDichVu;

public class EditUserInforController {
	private Scene EditUserIn4;
	
	private Connection connection;
	private Statement statement;
	private String oldUsername;
	private String gender = "";
	
	private double offset_x;
    private double offset_y;
	
    @FXML
    private ImageView back;

    @FXML
    private Button change;

    @FXML
    private TextField citizenID;

    @FXML
    private Label close;

    @FXML
    private TextField email;

    @FXML
    private RadioButton female;

    @FXML
    private TextField fullName;

    @FXML
    private RadioButton male;

    @FXML
    private Label minimize;

    @FXML
    private TextField phoneNumber;

    @FXML
    private TextField roomNumber;

    @FXML
    private TextField username;

    @FXML
    void ActionChangeInfo(MouseEvent e) throws IOException {
    	if (username.getText().isEmpty()) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setHeaderText("Username is empty!");
			alert.showAndWait();
		}
    	else if (citizenID.getText().isEmpty()) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setHeaderText("Citizen ID is empty!");
			alert.showAndWait();
		}
    	else if (fullName.getText().isEmpty()) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setHeaderText("Full name is empty!");
			alert.showAndWait();
		}
    	else {
    		// Edit data
    		try {
    			String query = "select Id_chu_so_huu from Chu_so_huu where Id_chu_so_huu = \'" + username.getText() + "\'";
    			ResultSet rs = statement.executeQuery(query);
    			if (rs.next()) {	// Da ton tai Id_chu_so_huu trong database
    				String newUsername = rs.getString(1);
    				if (!oldUsername.equals(newUsername)) { // Neu Id_chu_so_huu khong trung Id_chu_so_huu cu -> Trung tai khoan khac
        				Alert alert = new Alert(AlertType.WARNING);
        				alert.setHeaderText("Username already exists!");
        				alert.showAndWait();
					}
    				else {
    					statement.executeUpdate("SET FOREIGN_KEY_CHECKS=0");
    					statement.executeUpdate("Update Chu_so_huu set Id_chu_so_huu = \'" + username.getText() + "\', CCCD = \'"
    							+ citizenID.getText() + "\', Ten = \'" + fullName.getText() + "\', So_dien_thoai = \'" + phoneNumber.getText()
    							+ "\', Email = \'" + email.getText() + "\', Gioi_tinh = \'" + gender + "\' where Id_chu_so_huu = \'" + oldUsername + "\'");
    					statement.executeUpdate("Update Phong set Id_chu_so_huu = \'" + username.getText() 
						+ "\' where Id_chu_so_huu = \'" + oldUsername + "\'");
    					Alert alert = new Alert(AlertType.INFORMATION);
        				alert.setHeaderText("Success!");
        				alert.showAndWait();
        				
        				
        				// Go back
        				Scene currScene = (Scene)((Node) e.getSource()).getScene();
    					Stage currStage = (Stage)currScene.getWindow();
    					
    					FXMLLoader loader = new FXMLLoader();
    					loader.setLocation(getClass().getResource("/fxml/UserMainPage.fxml"));
    					Parent root = loader.load();
    					
    					UserMainPageController controller = loader.getController();
    					Scene scene = new Scene(root);
    					scene.getStylesheets().add(getClass().getResource("/css/admin.css").toExternalForm());
    					
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
    					controller.load(username.getText());
    					currStage.show();
    				}
				}
    			else {
    				statement.executeUpdate("SET FOREIGN_KEY_CHECKS=0");
					statement.executeUpdate("Update Chu_so_huu set Id_chu_so_huu = \'" + username.getText() + "\', CCCD = \'"
							+ citizenID.getText() + "\', Ten = \'" + fullName.getText() + "\', So_dien_thoai = \'" + phoneNumber.getText()
							+ "\', Email = \'" + email.getText() + "\', Gioi_tinh = \'" + gender + "\' where Id_chu_so_huu = \'" + oldUsername + "\'");
					statement.executeUpdate("Update Phong set Id_chu_so_huu = \'" + username.getText() 
					+ "\' where Id_chu_so_huu = \'" + oldUsername + "\'");
					Alert alert = new Alert(AlertType.INFORMATION);
    				alert.setHeaderText("Success!");
    				alert.showAndWait();
    				
    				// Go back
    				Scene currScene = (Scene)((Node) e.getSource()).getScene();
					Stage currStage = (Stage)currScene.getWindow();
					
					FXMLLoader loader = new FXMLLoader();
					loader.setLocation(getClass().getResource("/fxml/UserMainPage.fxml"));
					Parent root = loader.load();
					
					UserMainPageController controller = loader.getController();
					Scene scene = new Scene(root);
					scene.getStylesheets().add(getClass().getResource("/css/admin.css").toExternalForm());
					
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
					controller.load(username.getText());
					currStage.show();
    			}
			} catch (SQLException ex) {
				// TODO Auto-generated catch block
				ex.printStackTrace();
			}
		}
    }
    
    void load(String passUser) {
    	try {
			// Add data to listDichVu and listSummaries		
			// Just add date and room to listSummaries
			String query = "SELECT apartment_manager.chu_so_huu.Id_chu_so_huu, apartment_manager.chu_so_huu.CCCD, apartment_manager.chu_so_huu.Ten, apartment_manager.chu_so_huu.So_dien_thoai, apartment_manager.chu_so_huu.Email, apartment_manager.chu_so_huu.Gioi_tinh, apartment_manager.phong.Ma_phong FROM apartment_manager.chu_so_huu, apartment_manager.phong where apartment_manager.chu_so_huu.Id_chu_so_huu = apartment_manager.phong.Id_chu_so_huu and apartment_manager.chu_so_huu.Id_chu_so_huu = \'" + passUser + "\'";
			ResultSet rs = statement.executeQuery(query);
			rs.next();
			username.setText(rs.getString(1));
			citizenID.setText(rs.getString(2));
			fullName.setText(rs.getString(3));
			roomNumber.setText(String.valueOf(rs.getInt(7)));
			email.setText(rs.getString(5));
			phoneNumber.setText(rs.getString(4));
			System.out.println(rs.getString(6));
			if (rs.getString(6).equals("Nam")) {
				male.setSelected(true);
			}
			else if (rs.getString(6).equals("Nu")) {
				female.setSelected(true);
			}
			this.oldUsername = passUser;

    	} catch (SQLException e) {
			e.printStackTrace();
			Logger.getLogger(EditUserInforController.class.getName()).log(Level.SEVERE, null, e);
		}
    }

    @FXML
    void goBack(MouseEvent event) {
    	try {
			Scene currScene = (Scene)((Node) event.getSource()).getScene();
			Stage currStage = (Stage)currScene.getWindow();
			currStage.setScene(EditUserIn4);
			currStage.centerOnScreen();
			currStage.show();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
    }

    @FXML
    private void handleClose(MouseEvent event) {
    	System.exit(0);
    }
    
    @FXML
    private void handleMinimize(MouseEvent event) {
    	Stage stage = (Stage) minimize.getScene().getWindow();
        stage.setIconified(true);
    }

    @FXML
    void maleClicked(MouseEvent event) {
    	if (male.isSelected()) {
			female.setSelected(false);
			gender = "Nam";
		}
    }
    
    @FXML
    void femaleClicked(MouseEvent event) {
    	if (female.isSelected()) {
			male.setSelected(false);
		}
    	gender = "Nu";
    }
    
	public void setScene(Scene EditUserIn4) {
		this.EditUserIn4 = EditUserIn4;
	}
	

    @FXML
    void initialize() {
        roomNumber.setEditable(false);
    	try {
			connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/apartment_manager", "root", library.password);
			statement = connection.createStatement();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
    }

}
