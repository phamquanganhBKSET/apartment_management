package controller;

import java.io.File;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Optional;
import java.util.ResourceBundle;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Separator;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class ViewRoomInfoController implements Initializable {
	private String roomName;
	private String newStatusText;
	private String ownerIDRoom;
	private String typeRoom;
	private String peopleRoom;
	private String statusRoom;
	private String imgSrc;
	private String color;
	
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
    private Button apply;

    @FXML
    private VBox chosenUserCard;

    @FXML
    private Label close;

    @FXML
    private Button editOwnerID;

    @FXML
    private Button editPeople;

    @FXML
    private Button editStatus;

    @FXML
    private Button editType;

    @FXML
    private Button electricity;

    @FXML
    private RadioButton empty;

    @FXML
    private Button envi;

    @FXML
    private RadioButton full;

    @FXML
    private ImageView image;

    @FXML
    private Label minimize;

    @FXML
    private TextField ownerID;

    @FXML
    private Label ownerIDLabel;

    @FXML
    private Label ownerIDText;

    @FXML
    private TextField people;

    @FXML
    private Label peopleLabel;

    @FXML
    private Label peopleText;

    @FXML
    private TextField room;

    @FXML
    private Separator sep1;

    @FXML
    private Separator sep2;

    @FXML
    private Separator sep21;

    @FXML
    private TextField type;

    @FXML
    private Label typeLabel;

    @FXML
    private Label typeName;

    @FXML
    private Label typeText;

    @FXML
    private Label userIDText;

    @FXML
    private Button vehicle;

    @FXML
    private Button water;
    
    @FXML
    private Button update;
    
    @FXML
    void handleUpdate(ActionEvent e) {
    	try {
			Scene currScene = (Scene)((Node) e.getSource()).getScene();
			Stage currStage = (Stage)currScene.getWindow();
			
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("/fxml/UpdateDebt.fxml"));
			Parent root = loader.load();
			
			UpdateDebtController controller = loader.getController();
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
			controller.load(room.getText());
			currStage.show();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
    }

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
    void handleEmpty(MouseEvent event) {
    	if (empty.isSelected()) {
    		full.setSelected(false);
    		newStatusText = "Empty";
    	}
    }

    @FXML
    void handleFull(MouseEvent event) {
    	if (full.isSelected()) {
    		empty.setSelected(false);
    		newStatusText = "Full";
    	}
    }
    
    @FXML
    public void handleApply(ActionEvent event) {
    	String newType = type.getText();
    	String newOwnerID = ownerID.getText();
    	
    	if (newType.equals("") | newOwnerID.equals("") | people.getText().equals("")) {
    		Alert alert = new Alert(Alert.AlertType.INFORMATION);
			alert.setTitle("Error");
			alert.setHeaderText("Edit failed!");
			alert.setContentText("One or more fields are empty!");
			alert.showAndWait();
			return;
    	}
    	
    	int newPeople = Integer.parseInt(people.getText());
    	int newStatusInt = (newStatusText.equals("Empty")) ? 1 : 0;
    	
    	try {
			String sqlString = "SET FOREIGN_KEY_CHECKS=0;";
			statement.executeUpdate(sqlString);
			
			sqlString = "select ID_chu_so_huu from chu_so_huu where ID_chu_so_huu = \'" + newOwnerID + "\'";
			ResultSet rs = statement.executeQuery(sqlString);
			if (rs.next() == false) {
				Alert alert = new Alert(Alert.AlertType.INFORMATION);
				alert.setTitle("Update information");
				alert.setHeaderText("Update failed!");
				alert.setContentText("Username " + newOwnerID + " doesn't exist!");
				alert.showAndWait();
			}
			
			else {
				if (newStatusInt == 1) {
					sqlString = "update apartment_manager.phong "
							 + "set ID_chu_so_huu = null, "
							 + "So_nguoi = null, "
							 + "Loai_phong = \'" + newType.charAt(0) + " phong ngu" + "\', "
							 + "Trang_thai_phong = 1 "
							 + "where Ma_phong = " + roomName;
					
					statement.executeUpdate(sqlString);
					
					ownerIDRoom = "";
					ownerID.setText(ownerIDRoom);
					ownerID.setDisable(true);
					typeRoom = newType;
					type.setText(typeRoom.charAt(0) + " beds");
					type.setDisable(true);
					peopleRoom = Integer.toString(0);
					people.setText(peopleRoom);
					people.setDisable(true);
				} else {
					sqlString = "update apartment_manager.phong "
							 + "set ID_chu_so_huu = \'" + newOwnerID + "\', "
							 + "So_nguoi = " + newPeople + ", "
							 + "Loai_phong = \'" + newType.charAt(0) + " phong ngu" + "\', "
							 + "Trang_thai_phong = 0 "
							 + "where Ma_phong = " + roomName;
					
					statement.executeUpdate(sqlString);
					
					ownerIDRoom = newOwnerID;
					ownerID.setText(ownerIDRoom);
					ownerID.setDisable(true);
					typeRoom = newType;
					type.setText(typeRoom.charAt(0) + " beds");
					type.setDisable(true);
					peopleRoom = Integer.toString(newPeople);
					people.setText(peopleRoom);
					people.setDisable(true);
				}
				
				statusRoom = newStatusText;
				if (statusRoom.equals("Empty")) {
					empty.setSelected(true);
					full.setSelected(false);
					empty.setDisable(true);
					full.setDisable(true);
				} else {
					empty.setSelected(false);
					full.setSelected(true);
					empty.setDisable(true);
					full.setDisable(true);
				}
				typeText.setText(typeRoom.charAt(0) + " beds");
				ownerIDText.setText(ownerIDRoom);
				peopleText.setText(peopleRoom);
				
				editOwnerID.setText("Edit");
				editPeople.setText("Edit");
				editStatus.setText("Edit");
				editType.setText("Edit");
				
				Alert alert = new Alert(Alert.AlertType.INFORMATION);
				alert.setTitle("Update information");
				alert.setHeaderText("Successfully update!");
				alert.showAndWait();
				
				this.updateChosenCard();
				mainController.updateDataRoom();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
    }

    @FXML
    public void handleEditOwnerID(ActionEvent event) {
    	apply.setDisable(false);
    	if (editOwnerID.getText().equals("Edit")) {
    		ownerID.setDisable(false);
    		ownerID.setText("");
    		editOwnerID.setText("Exit");
    	} else {
    		ownerID.setDisable(true);
    		ownerID.setText(ownerIDRoom);
    		editOwnerID.setText("Edit");
    		apply.setDisable(true);
    	}
    }

    @FXML
    public void handleEditPeople(ActionEvent event) {
    	apply.setDisable(false);
    	if (editPeople.getText().equals("Edit")) {
    		people.setDisable(false);
    		people.setText("");
    		editPeople.setText("Exit");
    	} else {
    		people.setDisable(true);
    		people.setText(peopleRoom);
    		editPeople.setText("Edit");
    		apply.setDisable(true);
    	}
    }

    @FXML
    public void handleEditStatus(ActionEvent event) {
    	apply.setDisable(false);
    	if (editStatus.getText().equals("Edit")) {
    		full.setDisable(false);
    		empty.setDisable(false);
    		editStatus.setText("Exit");
    	} else {
    		if (statusRoom.equals("Empty")) {
				empty.setSelected(true);
				full.setSelected(false);
				empty.setDisable(true);
				full.setDisable(true);
			} else {
				empty.setSelected(false);
				full.setSelected(true);
				empty.setDisable(true);
				full.setDisable(true);
			}
    		editStatus.setText("Edit");
    		apply.setDisable(true);
    	}
    }

    @FXML
    public void handleEditType(ActionEvent event) {
    	apply.setDisable(false);
    	if (editType.getText().equals("Edit")) {
    		type.setDisable(false);
    		type.setText("");
    		editType.setText("Exit");
    	} else {
    		type.setDisable(true);
    		type.setText(typeRoom.charAt(0) + " beds");
    		editType.setText("Edit");
    		apply.setDisable(true);
    	}
    }

    @FXML
    public void handleElectricity(MouseEvent event) {
    	try {
			Stage stage = new Stage();
			stage.initStyle(StageStyle.UNDECORATED);
			
			String file;

			Alert alert = new Alert(AlertType.CONFIRMATION);
			alert.setTitle("Select");
			alert.setHeaderText("Do you want to see or add electricity?");

			ButtonType see = new ButtonType("See");
			ButtonType add = new ButtonType("Add");
			ButtonType cancel = new ButtonType("Cancel", ButtonData.CANCEL_CLOSE);

			alert.getButtonTypes().setAll(see, add, cancel);

			Optional<ButtonType> option = alert.showAndWait();

			if (option.get() == see) {
				file = "/fxml/Electricity.fxml";
			} else if (option.get() == add) {
				file = "/fxml/AddElectricity.fxml";
			} else {
				file = null;
				return;
			}
			
			Scene currScene = (Scene)((Node) event.getSource()).getScene();
			Stage currStage = (Stage)currScene.getWindow();
			
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource(file));
			Parent root = loader.load();
			
			Scene scene = new Scene(root);
			
			if (file.equals("/fxml/Electricity.fxml")) {
				ElectricityController controller = loader.getController();
				scene.getStylesheets().add(getClass().getResource("/css/table.css").toExternalForm());
				// Drag scene
				scene.setOnMousePressed(ev -> {
		            offset_x = ev.getSceneX();
		            offset_y = ev.getSceneY();
		        });
		        scene.setOnMouseDragged(ev -> {
		        	currStage.setX(ev.getScreenX() - offset_x);
		        	currStage.setY(ev.getScreenY() - offset_y);
		        });
		        
				currStage.setScene(scene);
				currStage.centerOnScreen();
				currStage.setResizable(false);
				controller.setScene(currScene);
				controller.load(connection, statement, room.getText());
				currStage.show();
			} else {
				AddElectricController controller = loader.getController();
				scene.getStylesheets().add(getClass().getResource("/css/AddAdmin.css").toExternalForm());

				// Drag scene
				scene.setOnMousePressed(ev -> {
		            offset_x = ev.getSceneX();
		            offset_y = ev.getSceneY();
		        });
		        scene.setOnMouseDragged(ev -> {
		        	currStage.setX(ev.getScreenX() - offset_x);
		        	currStage.setY(ev.getScreenY() - offset_y);
		        });
		        
				currStage.setScene(scene);
				currStage.centerOnScreen();
				currStage.setResizable(false);
				controller.setScene(currScene);
				controller.load(connection, statement, room.getText());
				currStage.show();
			}

    	} catch (Exception e) {
    		e.printStackTrace();
    	}
    }

    @FXML
    public void handleEnvi(ActionEvent event) {
    	try {
			Stage stage = new Stage();
			stage.initStyle(StageStyle.UNDECORATED);
			
			String file;

			Alert alert = new Alert(AlertType.CONFIRMATION);
			alert.setTitle("Select");
			alert.setHeaderText("Do you want to see or add Water?");

			ButtonType see = new ButtonType("See");
			ButtonType add = new ButtonType("Add");
			ButtonType cancel = new ButtonType("Cancel", ButtonData.CANCEL_CLOSE);

			alert.getButtonTypes().setAll(see, add, cancel);

			Optional<ButtonType> option = alert.showAndWait();

			if (option.get() == see) {
				file = "/fxml/EnviCharge.fxml";
			} else if (option.get() == add) {
				file = "/fxml/AddEnvi.fxml";
			} else {
				file = null;
				return;
			}
			
			Scene currScene = (Scene)((Node) event.getSource()).getScene();
			Stage currStage = (Stage)currScene.getWindow();
			
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource(file));
			Parent root = loader.load();
			
			Scene scene = new Scene(root);
			
			if (file.equals("/fxml/EnviCharge.fxml")) {
				EnviController controller = loader.getController();
				scene.getStylesheets().add(getClass().getResource("/css/table.css").toExternalForm());
				// Drag scene
				scene.setOnMousePressed(ev -> {
		            offset_x = ev.getSceneX();
		            offset_y = ev.getSceneY();
		        });
		        scene.setOnMouseDragged(ev -> {
		        	currStage.setX(ev.getScreenX() - offset_x);
		        	currStage.setY(ev.getScreenY() - offset_y);
		        });
		        
				currStage.setScene(scene);
				currStage.centerOnScreen();
				currStage.setResizable(false);
				controller.setScene(currScene);
				controller.load(connection, statement, room.getText());
				currStage.show();
			} else {
				AddEnviChargeController controller = loader.getController();
				scene.getStylesheets().add(getClass().getResource("/css/AddAdmin.css").toExternalForm());

				// Drag scene
				scene.setOnMousePressed(ev -> {
		            offset_x = ev.getSceneX();
		            offset_y = ev.getSceneY();
		        });
		        scene.setOnMouseDragged(ev -> {
		        	currStage.setX(ev.getScreenX() - offset_x);
		        	currStage.setY(ev.getScreenY() - offset_y);
		        });
		        
				currStage.setScene(scene);
				currStage.centerOnScreen();
				currStage.setResizable(false);
				controller.setScene(currScene);
				controller.load(connection, statement, room.getText());
				currStage.show();
			}

    	} catch (Exception e) {
    		e.printStackTrace();
    	}
    }
    
    @FXML
    public void handleVehicle(ActionEvent e) {
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
			controller.disableAdd(true);
			controller.disableDelete(true);
			controller.load(connection, statement, room.getText());
			currStage.show();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
    }

    @FXML
    public void handleWater(ActionEvent event) {
    	try {
			Stage stage = new Stage();
			stage.initStyle(StageStyle.UNDECORATED);
			
			String file;

			Alert alert = new Alert(AlertType.CONFIRMATION);
			alert.setTitle("Select");
			alert.setHeaderText("Do you want to see or add Water?");

			ButtonType see = new ButtonType("See");
			ButtonType add = new ButtonType("Add");
			ButtonType cancel = new ButtonType("Cancel", ButtonData.CANCEL_CLOSE);

			alert.getButtonTypes().setAll(see, add, cancel);

			Optional<ButtonType> option = alert.showAndWait();

			if (option.get() == see) {
				file = "/fxml/Water.fxml";
			} else if (option.get() == add) {
				file = "/fxml/AddWater.fxml";
			} else {
				file = null;
				return;
			}
			
			Scene currScene = (Scene)((Node) event.getSource()).getScene();
			Stage currStage = (Stage)currScene.getWindow();
			
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource(file));
			Parent root = loader.load();
			
			Scene scene = new Scene(root);
			
			if (file.equals("/fxml/Water.fxml")) {
				WaterController controller = loader.getController();
				scene.getStylesheets().add(getClass().getResource("/css/table.css").toExternalForm());
				// Drag scene
				scene.setOnMousePressed(ev -> {
		            offset_x = ev.getSceneX();
		            offset_y = ev.getSceneY();
		        });
		        scene.setOnMouseDragged(ev -> {
		        	currStage.setX(ev.getScreenX() - offset_x);
		        	currStage.setY(ev.getScreenY() - offset_y);
		        });
		        
				currStage.setScene(scene);
				currStage.centerOnScreen();
				currStage.setResizable(false);
				controller.setScene(currScene);
				controller.load(connection, statement, room.getText());
				currStage.show();
			} else {
				AddWaterController controller = loader.getController();
				scene.getStylesheets().add(getClass().getResource("/css/AddAdmin.css").toExternalForm());

				// Drag scene
				scene.setOnMousePressed(ev -> {
		            offset_x = ev.getSceneX();
		            offset_y = ev.getSceneY();
		        });
		        scene.setOnMouseDragged(ev -> {
		        	currStage.setX(ev.getScreenX() - offset_x);
		        	currStage.setY(ev.getScreenY() - offset_y);
		        });
		        
				currStage.setScene(scene);
				currStage.centerOnScreen();
				currStage.setResizable(false);
				controller.setScene(currScene);
				controller.load(connection, statement, room.getText());
				currStage.show();
			}

    	} catch (Exception e) {
    		e.printStackTrace();
    	}
    }
    
	public void setRoomName(String roomName) {
    	this.roomName = roomName;
    	room.setText(roomName);
    	room.setDisable(true);
    	
    	try {
			connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/apartment_manager", "root", library.password);
			statement = connection.createStatement();
			
			String sqlString;
			
			sqlString = "select * from phong where Ma_phong = \'" + roomName + "\'";
			ResultSet rs = statement.executeQuery(sqlString);
			if (rs.next()) {
				ownerIDRoom = rs.getString(2);
				ownerID.setText(ownerIDRoom);
				ownerID.setDisable(true);
				typeRoom = rs.getString(4);
				type.setText(typeRoom.charAt(0) + " beds");
				type.setDisable(true);
				peopleRoom = Integer.toString(rs.getInt(3));
				people.setText(peopleRoom);
				people.setDisable(true);
				statusRoom = (rs.getInt(5) == 0) ? "Full" : "Empty";
				newStatusText = statusRoom;
				if (statusRoom.equals("Empty")) {
					empty.setSelected(true);
					full.setSelected(false);
					empty.setDisable(true);
					full.setDisable(true);
				} else {
					empty.setSelected(false);
					full.setSelected(true);
					empty.setDisable(true);
					full.setDisable(true);
				}
				
				userIDText.setText(roomName);
				Image img = new Image(String.valueOf(new File(imgSrc)));
		    	image.setImage(img);
		    	chosenUserCard.setStyle("-fx-background-color: #" + color);
		    	typeText.setText(typeRoom.charAt(0) + " beds");
				ownerIDText.setText(ownerIDRoom);
				peopleText.setText(peopleRoom);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
    }
	
	public void updateChosenCard() {
		if (statusRoom.equals("Empty")) {
			this.color = "F08080";
		} else {
			this.color = "00CED1";
		}
		
		chosenUserCard.setStyle("-fx-background-color: #" + this.color);
	}
	
	public void setImgSrc(String imgSrc) {
		this.imgSrc = imgSrc;
	}
	
	public void setColor(String color) {
		this.color = color;
	}
	
	public void setMainController(AdminMainPageController mainController) {
		this.mainController = mainController;
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		apply.setDisable(true);
	}
}
