package controller;

import java.io.File;
import java.io.IOException;
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
import java.util.Optional;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Separator;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import model.HouseHolder;
import model.Room;
import model.socket.AdminClient;
import model.socket.SocketLibrary;

public class AdminMainPageController implements Initializable {
	private String username;
	private Connection connection;
	private Statement statement;
	private Scene loginScene;
	private boolean inRoomPage = true;
	private String messageToUser = "";
	private String toUser = "";
	
	private double offset_x;
    private double offset_y;
	
    @FXML
    private Button edit;
    
	@FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ImageView addUser;

    @FXML
    private VBox chosenRoomCard;

    @FXML
    private Label close;

    @FXML
    private ComboBox<String> filter;

    @FXML
    private GridPane gridPane;

    @FXML
    private GridPane gridPaneHH;

    @FXML
    private HBox houseHoldersManage;

    @FXML
    private Label minimize;

    @FXML
    private Hyperlink myAccount;

    @FXML
    private Label notify;

    @FXML
    private Label numPeople;

    @FXML
    private Label ownerID;

    @FXML
    private Label ownerIDLabel;

    @FXML
    private Label peopleLabel;

    @FXML
    private ImageView roomImage;

    @FXML
    private Label roomName;

    @FXML
    private Label roomType;

    @FXML
    private HBox roomsManage;

    @FXML
    private ScrollPane scrollPane;

    @FXML
    private Button searchButton;

    @FXML
    private TextField searchText;

    @FXML
    private Label typeLabel;

    @FXML
    private Label typeName;
    
    @FXML
    private Separator sep1;

    @FXML
    private Separator sep2;

    @FXML
    private Separator sep3;
    
    @FXML
    private Label numRooms;
    
    @FXML
    private Label numHouseHolders;
    
    @FXML
    private Label resultFilter;
    
    @FXML
    private GridPane gridFilterRoom;

    @FXML
    private GridPane gridFilterUser;
    
    private ObservableList<String> filterList = FXCollections.observableArrayList("All", "Empty Rooms", "Full Rooms");
    
    private ObservableList<String> filterListHH = FXCollections.observableArrayList("All", "Male", "Female");
    
    private Room currRoom;
    private String currUserID;
    
    private RoomItemListener roomItemListener = new RoomItemListener() {
		@Override
		public void onClickListener(Room room) {
			setChoosenRoom(room);
		}
	};
    
    private HouseHolderItemListener houseHolderItemListener = new HouseHolderItemListener() {
		@Override
		public void onClickListener(HouseHolder houseHolder) {
			setChoosenUser(houseHolder);
		}
	};
	
    private List<Room> listRoom = new ArrayList<>();
    
    private List<HouseHolder> listHouseHolder = new ArrayList<>();
    
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
    void handleHouseHoldersManage(MouseEvent event) {
    	filter.setPromptText("Filter");
    	typeLabel.setText("Citizen ID");
    	ownerIDLabel.setText("Phone");
    	peopleLabel.setText("Email");
    	inRoomPage = false;
    	typeName.setText("User ID");
    	filter.setItems(filterListHH);
    	setChoosenUser(listHouseHolder.get(0));
    	gridPane.setVisible(false);
    	gridPane.setDisable(true);
    	gridPaneHH.setVisible(true);
    	gridPaneHH.setDisable(false);
    	gridFilterUser.setVisible(false);
		gridFilterUser.setDisable(true);
    	gridFilterRoom.setVisible(false);
		gridFilterRoom.setDisable(true);
    }
    
    @FXML
    void handleRoomsManage(MouseEvent event) {
    	filter.setPromptText("Filter");
    	typeLabel.setText("Type");
    	ownerIDLabel.setText("Owner ID");
    	peopleLabel.setText("People");
    	inRoomPage = true;
    	typeName.setText("Room ID");
    	filter.setItems(filterList);
    	setChoosenRoom(listRoom.get(0));
    	gridPaneHH.setVisible(false);
    	gridPaneHH.setDisable(true);
    	gridPane.setVisible(true);
    	gridPane.setDisable(false);
    	gridFilterUser.setVisible(false);
		gridFilterUser.setDisable(true);
		gridFilterRoom.setVisible(false);
		gridFilterRoom.setDisable(true);
    }
    
    @FXML
    void handleSearch(ActionEvent event) {
    	String searchString = searchText.getText();
    	if (inRoomPage) {
    		for (Room room : listRoom) {
    			if (room.getRoomName().equals(searchString)) {
    				this.setChoosenRoom(room);
    				return;
    			}
    		}
    		
    		Alert alert = new Alert(Alert.AlertType.INFORMATION);
			alert.setTitle("Searching Information");
			alert.setHeaderText("Searching failed!");
			alert.setContentText("There aren't any room with ID: " + searchString + "!");
			alert.showAndWait();
    	} else {
    		for (HouseHolder houseHolder : listHouseHolder) {
    			if (houseHolder.getUserName().equals(searchString)) {
    				this.setChoosenUser(houseHolder);
    				return;
    			}
    		}
    		
    		Alert alert = new Alert(Alert.AlertType.INFORMATION);
			alert.setTitle("Searching Information");
			alert.setHeaderText("Searching failed!");
			alert.setContentText("There aren't any user has ID: " + searchString + "!");
			alert.showAndWait();
    	}
    }
    
    @FXML
    public void handleEnterSearch(KeyEvent event) {
    	if (event.getCode() == KeyCode.ENTER) {
    		String searchString = searchText.getText();
        	if (inRoomPage) {
        		for (Room room : listRoom) {
        			if (room.getRoomName().equals(searchString)) {
        				this.setChoosenRoom(room);
        				return;
        			}
        		}
        		
        		Alert alert = new Alert(Alert.AlertType.INFORMATION);
    			alert.setTitle("Searching Information");
    			alert.setHeaderText("Searching failed!");
    			alert.setContentText("There aren't any room has ID: " + searchString + "!");
    			alert.showAndWait();
        	} else {
        		for (HouseHolder houseHolder : listHouseHolder) {
        			if (houseHolder.getUserName().equals(searchString)) {
        				this.setChoosenUser(houseHolder);
        				return;
        			}
        		}
        		
        		Alert alert = new Alert(Alert.AlertType.INFORMATION);
    			alert.setTitle("Searching Information");
    			alert.setHeaderText("Searching failed!");
    			alert.setContentText("There aren't any user has ID: " + searchString + "!");
    			alert.showAndWait();
        	}
    	}
    }
    
    public void showFilterRoom(boolean status) {
    	gridPane.setVisible(false);
    	gridPane.setDisable(true);
		gridFilterRoom.setVisible(true);
		gridFilterRoom.setDisable(false);
		
		gridFilterRoom.getChildren().clear();
    	
    	int column = 0;
		int row = 1;
		
		try {
			for (int i = 0; i < listRoom.size(); i++) {
				if (listRoom.get(i).getStatus() == status) {
					FXMLLoader loader = new FXMLLoader();
					loader.setLocation(getClass().getResource("/fxml/RoomItem.fxml"));
					
					AnchorPane anchorPane = loader.load();
					
					RoomItemController controller = loader.getController();
					controller.setData(listRoom.get(i), roomItemListener);
					
					if (column == 3) {
						column = 0;
						row++;
					}
					
					anchorPane.setStyle("-fx-background-color: #" + listRoom.get(i).getColor());
					gridFilterRoom.add(anchorPane, column++, row);
					
					// Set grid width
					gridFilterRoom.setMinWidth(Region.USE_COMPUTED_SIZE);
					gridFilterRoom.setPrefWidth(Region.USE_COMPUTED_SIZE);
					gridFilterRoom.setMaxWidth(Region.USE_PREF_SIZE);
					
					// Set grid height
					gridFilterRoom.setMinHeight(Region.USE_COMPUTED_SIZE);
					gridFilterRoom.setPrefHeight(Region.USE_COMPUTED_SIZE);
					gridFilterRoom.setMaxHeight(Region.USE_PREF_SIZE);
					
					GridPane.setMargin(anchorPane, new Insets(10));
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
    
    public void showFilterUser(String gender) {
    	gridPaneHH.setVisible(false);
    	gridPaneHH.setDisable(true);
		gridFilterUser.setVisible(true);
		gridFilterUser.setDisable(false);
		
		gridFilterUser.getChildren().clear();
    	
    	int column = 0;
		int row = 1;
		
		try {
			for (int i = 0; i < listHouseHolder.size(); i++) {
				if (listHouseHolder.get(i).getGender() != null && listHouseHolder.get(i).getGender().equals(gender)) {
					FXMLLoader loader = new FXMLLoader();
					loader.setLocation(getClass().getResource("/fxml/HouseHolderItem.fxml"));
					
					AnchorPane anchorPane = loader.load();
					
					HouseHolderItemController controller = loader.getController();
					controller.setData(listHouseHolder.get(i), houseHolderItemListener);
					
					if (column == 3) {
						column = 0;
						row++;
					}
					
					anchorPane.setStyle("-fx-background-color: #" + listHouseHolder.get(i).getColor());
					gridFilterUser.add(anchorPane, column++, row);
					
					// Set grid width
					gridFilterUser.setMinWidth(Region.USE_COMPUTED_SIZE);
					gridFilterUser.setPrefWidth(Region.USE_COMPUTED_SIZE);
					gridFilterUser.setMaxWidth(Region.USE_PREF_SIZE);
					
					// Set grid height
					gridFilterUser.setMinHeight(Region.USE_COMPUTED_SIZE);
					gridFilterUser.setPrefHeight(Region.USE_COMPUTED_SIZE);
					gridFilterUser.setMaxHeight(Region.USE_PREF_SIZE);
					
					GridPane.setMargin(anchorPane, new Insets(10));
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
    
    @FXML
    public void handleFilter(ActionEvent event) {
    	int result = 0;
    	String filterValue = filter.getValue();
    	if (filterValue != null) {
	    	if (inRoomPage) {
	    		if (filterValue.equals("All")) {
	    			resultFilter.setVisible(false);
	    	    	gridPane.setVisible(true);
	    	    	gridPane.setDisable(false);
	    	    	gridFilterRoom.setVisible(false);
	    	    	gridFilterRoom.setDisable(true);
	    	    	gridFilterUser.setVisible(false);
	    	    	gridFilterUser.setDisable(true);
	    		}
	    		else if (filterValue.equals("Empty Rooms")) {
		    		for (Room room : listRoom) {
		    			if (room.getStatus() == true) {
		    				result++;
		    			}
		    		}
		    		resultFilter.setVisible(true);
		    		resultFilter.setText("Result: " + result);
		    		this.showFilterRoom(true);
	    		} else {
	    			for (Room room : listRoom) {
		    			if (room.getStatus() == false) {
		    				result++;
		    			}
		    		}
	    			resultFilter.setVisible(true);
		    		resultFilter.setText("Result: " + result);
		    		this.showFilterRoom(false);
	    		}
	    	} else {
				if (filterValue.equals("All")) {
					resultFilter.setVisible(false);
					gridPaneHH.setVisible(true);
	    	    	gridPaneHH.setDisable(false);
	    	    	gridFilterRoom.setVisible(false);
	    	    	gridFilterRoom.setDisable(true);
	    	    	gridFilterUser.setVisible(false);
	    	    	gridFilterUser.setDisable(true);
				}
	    		else if (filterValue.equals("Male")) {
	    			for (HouseHolder houseHolder : listHouseHolder) {
		    			if (houseHolder.getGender() != null && houseHolder.getGender().equals("Nam")) {
		    				result++;
		    			}
		    		}
	    			resultFilter.setVisible(true);
		    		resultFilter.setText("Result: " + result);
		    		this.showFilterUser("Nam");
	    		} else {
	    			for (HouseHolder houseHolder : listHouseHolder) {
		    			if (houseHolder.getGender() != null && houseHolder.getGender().equals("Nu")) {
		    				result++;
		    			}
		    		}
	    			resultFilter.setVisible(true);
		    		resultFilter.setText("Result: " + result);
		    		this.showFilterUser("Nu");
	    		}
	    	}
    	} else {
    		gridFilterRoom.setVisible(false);
        	gridFilterRoom.setDisable(false);
        	gridFilterUser.setVisible(false);
        	gridFilterUser.setDisable(false);
    		resultFilter.setVisible(false);
    		filter.setPromptText("Filter");
    	}
    }
    
    @FXML
    void handleAddUser(MouseEvent event) {
    	try {
			Stage stage = new Stage();
			stage.initStyle(StageStyle.UNDECORATED);
			
			String file;

			Alert alert = new Alert(AlertType.CONFIRMATION);
			alert.setTitle("Select");
			alert.setHeaderText("What type of user do you want to add?");

			ButtonType adUser = new ButtonType("Admin");
			ButtonType baseUser = new ButtonType("Base User");
			ButtonType cancel = new ButtonType("Cancel", ButtonData.CANCEL_CLOSE);

			alert.getButtonTypes().setAll(adUser, baseUser, cancel);

			Optional<ButtonType> option = alert.showAndWait();

			if (option.get() == adUser) {
				file = "/fxml/AddAdmin.fxml";
			} else if (option.get() == baseUser) {
				file = "/fxml/AddUser.fxml";
			} else {
				file = null;
				return;
			}
			
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource(file));
			Parent root = loader.load();
			
			if (file.equals("/fxml/AddAdmin.fxml")) {
				AddAdminController controller = loader.getController();
				controller.setMainController(this);
			} else {
				AddUserController controller = loader.getController();
				controller.setMainController(this);
			}
			
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("/css/AddAdmin.css").toExternalForm());
			
			// Drag scene
			scene.setOnMousePressed(e -> {
	            offset_x = e.getSceneX();
	            offset_y = e.getSceneY();
	        });
	        scene.setOnMouseDragged(e -> {
	        	stage.setX(e.getScreenX() - offset_x);
	        	stage.setY(e.getScreenY() - offset_y);
	        });
	        
			stage.setScene(scene);
			stage.setResizable(false);
			stage.centerOnScreen();
			stage.show();
    	} catch (Exception e) {
    		e.printStackTrace();
    	}
    }
    
    public void updateDataUser() {
    	listHouseHolder.clear();
		boolean hasUser = false;
    	
		try {
			listHouseHolder.addAll(getDataHH());
			for (int i = 0; i < listHouseHolder.size(); i++) {
				if (listHouseHolder.get(i).getUserName().equals(currUserID)) {
					hasUser = true;
					this.setChoosenUser(listHouseHolder.get(i));
				}
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		
		if (hasUser == false) {
			this.setChoosenUser(listHouseHolder.get(0));
		}
		
		numHouseHolders.setText("about " + listHouseHolder.size() + " householders");
		
		int column = 0;
		int row = 1;
		
		try {
			for (int i = 0; i < listHouseHolder.size(); i++) {
				FXMLLoader loader = new FXMLLoader();
				loader.setLocation(getClass().getResource("/fxml/HouseHolderItem.fxml"));
					
				AnchorPane anchorPane = loader.load();
					
				HouseHolderItemController controller = loader.getController();
				controller.setData(listHouseHolder.get(i), houseHolderItemListener);
					
				if (column == 3) {
					column = 0;
					row++;
				}
				
				anchorPane.setStyle("-fx-background-color: #" + listHouseHolder.get(i).getColor());
				gridPaneHH.add(anchorPane, column++, row);
					
				// Set grid width
				gridPaneHH.setMinWidth(Region.USE_COMPUTED_SIZE);
				gridPaneHH.setPrefWidth(Region.USE_COMPUTED_SIZE);
				gridPaneHH.setMaxWidth(Region.USE_PREF_SIZE);
					
				// Set grid height
				gridPaneHH.setMinHeight(Region.USE_COMPUTED_SIZE);
				gridPaneHH.setPrefHeight(Region.USE_COMPUTED_SIZE);
				gridPaneHH.setMaxHeight(Region.USE_PREF_SIZE);
					
				GridPane.setMargin(anchorPane, new Insets(10));
			} 
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
    
    public void updateDataRoom() {
    	listRoom.clear();
    	
    	try {
			listRoom.addAll(getData());
			for (int i = 0; i < listRoom.size(); i++) {
				if (listRoom.get(i).getRoomName().equals(currRoom.getRoomName())) {
					this.setChoosenRoom(listRoom.get(i));
				}
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		
    	numRooms.setText("about " + listRoom.size() + " rooms");
		
		int column = 0;
		int row = 1;
		
		try {
			for (int i = 0; i < listRoom.size(); i++) {
				FXMLLoader loader = new FXMLLoader();
				loader.setLocation(getClass().getResource("/fxml/RoomItem.fxml"));
				
				AnchorPane anchorPane = loader.load();
				
				RoomItemController controller = loader.getController();
				controller.setData(listRoom.get(i), roomItemListener);
				
				if (column == 3) {
					column = 0;
					row++;
				}
				
				anchorPane.setStyle("-fx-background-color: #" + listRoom.get(i).getColor());
				gridPane.add(anchorPane, column++, row);
				
				// Set grid width
				gridPane.setMinWidth(Region.USE_COMPUTED_SIZE);
				gridPane.setPrefWidth(Region.USE_COMPUTED_SIZE);
				gridPane.setMaxWidth(Region.USE_PREF_SIZE);
				
				// Set grid height
				gridPane.setMinHeight(Region.USE_COMPUTED_SIZE);
				gridPane.setPrefHeight(Region.USE_COMPUTED_SIZE);
				gridPane.setMaxHeight(Region.USE_PREF_SIZE);
				
				GridPane.setMargin(anchorPane, new Insets(10));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
    }
    
    @FXML
    void handleEditMyAccount(ActionEvent e) {
    	try {
    		Stage stage = new Stage();
    		stage.initStyle(StageStyle.UNDECORATED);
    		
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("/fxml/ViewAdminInfo.fxml"));
			Parent root = loader.load();
			
			ViewAdminInfoController controller = loader.getController();
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("/css/viewAdminInfo.css").toExternalForm());
			
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
			stage.show();
    	} catch (Exception ex) {
    		ex.printStackTrace();
    	}
    }
    
    @FXML
    void handleNotify(MouseEvent event) {
    	
    }
    
    @FXML
    void handleViewInfo(MouseEvent e) {
    	if (inRoomPage) {
    		try {
	    		Stage stage = new Stage();
		    	stage.initStyle(StageStyle.UNDECORATED);
		    	
				FXMLLoader loader = new FXMLLoader();
				loader.setLocation(getClass().getResource("/fxml/ViewRoomInfo.fxml"));
				Parent root = loader.load();
				
				ViewRoomInfoController controller = loader.getController();
				Scene scene = new Scene(root);
				scene.getStylesheets().add(getClass().getResource("/css/viewUserInfo.css").toExternalForm());
				
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
				controller.setImgSrc(currRoom.getImgSrc());
				controller.setColor(currRoom.getColor());
				controller.setRoomName(currRoom.getRoomName());
				controller.setMainController(this);
				stage.show();
    		} catch(Exception ex) {
    			ex.printStackTrace();
    		}
    	} else {
    		try {
	    		Stage stage = new Stage();
		    	stage.initStyle(StageStyle.UNDECORATED);
		    	
				FXMLLoader loader = new FXMLLoader();
				loader.setLocation(getClass().getResource("/fxml/ViewUserInfo.fxml"));
				Parent root = loader.load();
				
				ViewUserInfoController controller = loader.getController();
				Scene scene = new Scene(root);
				scene.getStylesheets().add(getClass().getResource("/css/viewUserInfo.css").toExternalForm());
				
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
				controller.setUsername(currUserID);
				controller.setMainController(this);
				stage.show();
    		} catch(Exception ex) {
    			ex.printStackTrace();
    		}
    	}
    }
    
    private List<Room> getData() throws SQLException {
    	List<Room> listRoom = new ArrayList<>();
    	Room room;
    	int i = 1;
    	
    	String sqlString = "select * from apartment_manager.phong";
		ResultSet rs = statement.executeQuery(sqlString);
    	while(rs.next()) {
    			room = new Room();
    			room.setRoomName(rs.getString(1));
    			room.setStatus(rs.getBoolean(5));
    			room.setPeople(rs.getInt(3));
    			String type = rs.getString(4);
    			room.setType(Character.toString(type.charAt(0)));
    			if (rs.getInt(5) == 0) {
    				room.setColor("00CED1");
    				room.setStatus(false);
    			} else {
    				room.setColor("F08080");
    				room.setStatus(true);
    			}
    			room.setOwnerID(rs.getString(2));
    			Path path = FileSystems.getDefault().getPath("").toAbsolutePath();
    			room.setImgSrc(path + "\\icon\\room" + Integer.toString(i) + ".png");
    			listRoom.add(room);
    			i = (i + 1) % 4;
    	}
    	
    	return listRoom;
    }
    
    private List<HouseHolder> getDataHH() throws SQLException {
    	List<HouseHolder> listHouseHolder = new ArrayList<>();
    	HouseHolder houseHolder;
    	
    	String sqlString = "select * from apartment_manager.chu_so_huu";
		ResultSet rs = statement.executeQuery(sqlString);
    	while(rs.next()) {
    		houseHolder = new HouseHolder();
    		houseHolder.setUserName(rs.getString(1));
    		houseHolder.setEmail(rs.getString(5));
    		houseHolder.setPhoneNumber(rs.getString(4));
    		houseHolder.setGender(rs.getString(6));
    		houseHolder.setCitizenID(rs.getString(2));
    		Path path = FileSystems.getDefault().getPath("").toAbsolutePath();
    		if (rs.getString(6) != null && rs.getString(6).equals("Nam")) {
    			houseHolder.setImgSrc(path + "\\icon\\user_male.png");
    			houseHolder.setColor("00CED1");
    		} else {
    			houseHolder.setImgSrc(path + "\\icon\\user_female.png");
    			houseHolder.setColor("FFC0CB");
    		}
    		listHouseHolder.add(houseHolder);
    	}
    	
    	return listHouseHolder;
    }
    
    private void setChoosenRoom(Room room) {
    	currRoom = room;
    	roomName.setText(room.getRoomName());
    	Image image = new Image(String.valueOf(new File(room.getImgSrc())));
    	roomImage.setImage(image);
    	chosenRoomCard.setStyle("-fx-background-color: #" + room.getColor());
    	roomType.setText(room.getType() + " beds");
    	ownerID.setText(room.getOwnerID());
    	numPeople.setText(Integer.toString(room.getPeople()));
    }
    
    private void setChoosenUser(HouseHolder houseHolder) {
    	currUserID = houseHolder.getUserName();
    	roomName.setText(houseHolder.getUserName());
    	Image image = new Image(String.valueOf(new File(houseHolder.getImgSrc())));
    	roomImage.setImage(image);
    	roomType.setText(houseHolder.getCitizenID());
    	ownerID.setText(houseHolder.getPhoneNumber());
    	numPeople.setText(houseHolder.getEmail());
    	chosenRoomCard.setStyle("-fx-background-color: #" + houseHolder.getColor());
    }
    
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		resultFilter.setVisible(false);
		gridPaneHH.setVisible(false);
    	gridPane.setVisible(true);
    	gridPane.setDisable(false);
    	gridFilterRoom.setVisible(false);
    	gridFilterRoom.setDisable(true);
    	gridFilterUser.setVisible(false);
    	gridFilterUser.setDisable(true);
    	myAccount.setText("My Account");
		
    	try {
			connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/apartment_manager", "root", library.password);
			statement = connection.createStatement();
		} catch (SQLException e) {
			e.printStackTrace();
		}
    	
		filter.setItems(filterList);
		try {
			listRoom.addAll(getData());
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		setChoosenRoom(listRoom.get(0));
		
		try {
			listHouseHolder.addAll(getDataHH());
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		
		numRooms.setText("about " + listRoom.size() + " rooms");
		numHouseHolders.setText("about " + listHouseHolder.size() + " householders");
		
		int column = 0;
		int row = 1;
		
		try {
			for (int i = 0; i < listRoom.size(); i++) {
				FXMLLoader loader = new FXMLLoader();
				loader.setLocation(getClass().getResource("/fxml/RoomItem.fxml"));
				
				AnchorPane anchorPane = loader.load();
				
				RoomItemController controller = loader.getController();
				controller.setData(listRoom.get(i), roomItemListener);
				
				if (column == 3) {
					column = 0;
					row++;
				}
				
				anchorPane.setStyle("-fx-background-color: #" + listRoom.get(i).getColor());
				gridPane.add(anchorPane, column++, row);
				
				// Set grid width
				gridPane.setMinWidth(Region.USE_COMPUTED_SIZE);
				gridPane.setPrefWidth(Region.USE_COMPUTED_SIZE);
				gridPane.setMaxWidth(Region.USE_PREF_SIZE);
				
				// Set grid height
				gridPane.setMinHeight(Region.USE_COMPUTED_SIZE);
				gridPane.setPrefHeight(Region.USE_COMPUTED_SIZE);
				gridPane.setMaxHeight(Region.USE_PREF_SIZE);
				
				GridPane.setMargin(anchorPane, new Insets(10));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
			
		column = 0;
		row = 1;
			
		try {
			for (int i = 0; i < listHouseHolder.size(); i++) {
				FXMLLoader loader = new FXMLLoader();
				loader.setLocation(getClass().getResource("/fxml/HouseHolderItem.fxml"));
					
				AnchorPane anchorPane = loader.load();
					
				HouseHolderItemController controller = loader.getController();
				controller.setData(listHouseHolder.get(i), houseHolderItemListener);
					
				if (column == 3) {
					column = 0;
					row++;
				}
				
				anchorPane.setStyle("-fx-background-color: #" + listHouseHolder.get(i).getColor());
				gridPaneHH.add(anchorPane, column++, row);
					
				// Set grid width
				gridPaneHH.setMinWidth(Region.USE_COMPUTED_SIZE);
				gridPaneHH.setPrefWidth(Region.USE_COMPUTED_SIZE);
				gridPaneHH.setMaxWidth(Region.USE_PREF_SIZE);
					
				// Set grid height
				gridPaneHH.setMinHeight(Region.USE_COMPUTED_SIZE);
				gridPaneHH.setPrefHeight(Region.USE_COMPUTED_SIZE);
				gridPaneHH.setMaxHeight(Region.USE_PREF_SIZE);
					
				GridPane.setMargin(anchorPane, new Insets(10));
			} 
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void handleNotify(String sms) {
		//From User:username:Ten_chu_xe:Ma_phong:Loai_xe:Bien_so_xe:Mau_sac:Thang:To:all
		String parts[] = sms.split(":");
		
		String toUser = parts[1];
		String Ten_chu_xe = parts[2];
		String Ma_phong = parts[3];
		String Loai_xe = parts[4];
		String Bien_so_xe = parts[5];
		String Mau_sac = parts[6];
		String Thang = parts[7];
		
		String message = "Add vehicle with infomation\n"
				   + "+) Ten chu xe - " + Ten_chu_xe + "\n"
				   + "+) Ma phong - " + Ma_phong + "\n"
				   + "+) Loai xe - " + Loai_xe + "\n"
				   + "+) Bien so xe - " + Bien_so_xe + "\n"
				   + "+) Mau sac - " + Mau_sac + "\n"
				   + "+) Thang - " + Thang;
		
		Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
		alert.setTitle("NOTIFY");
		alert.setHeaderText("Request from user " + toUser + ": " + message);
		alert.setContentText("Choose your option");
		
		ButtonType buttonTypeAccept = new ButtonType("Accept", ButtonBar.ButtonData.YES);
		ButtonType buttonTypeRefuse = new ButtonType("Refuse", ButtonBar.ButtonData.NO);
		
		alert.getButtonTypes().setAll(buttonTypeAccept, buttonTypeRefuse);
		
		Optional<ButtonType> result = alert.showAndWait();
		
		if (result.get() == buttonTypeAccept) {
			System.out.println("Accept");
			messageToUser = "Accept";
			this.toUser = toUser;
			
			int ticketMax = 0;
			
			try {
				String s = "select MAX(Ve_xe) from apartment_manager.xe";
				ResultSet rs = statement.executeQuery(s);
				if (rs.next()) {
					ticketMax = rs.getInt(1) + 1;
				}
				else {
					ticketMax = 1;
				}
			} catch (Exception ex) {
				ex.printStackTrace();
			}
			
			try {
				String s = "insert into apartment_manager.xe (Ten_chu_xe, Ma_phong, Loai_xe, Bien_so_xe, Mau_sac, Thang, Da_dong, Ve_xe) "
						+ "values (\'" + Ten_chu_xe + "\', " + Ma_phong + ",\'" + Loai_xe + "\', \'"
						+ Bien_so_xe + "\', \'" + Mau_sac + "\', \'" + Thang 
						+"-01\', 1," + Integer.toString(ticketMax) + ");";
				statement.executeUpdate (s);
				
				Alert alertSuccess = new Alert(AlertType.INFORMATION);
				alertSuccess.setHeaderText("Successfully update vehicle for user " + toUser + "!");
				alertSuccess.showAndWait();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} else {
			System.out.println("Refuse");
			messageToUser = "Refuse";
			this.toUser = toUser;
		}
	}
	
	public void setMessageToUser(String messageToUser) {
		this.messageToUser = messageToUser;
	}
	
	public String getMessageToUser() {
		return this.messageToUser;
	}
	
	public void setToUser(String toUser) {
		this.toUser = toUser;
	}
	
	public String getToUser() {
		return this.toUser;
	}
	
	public void setCurrUserID(String currUserID) {
		this.currUserID = currUserID;
	}
	
	public void setLoginScene(Scene loginScene) {
		this.loginScene = loginScene;
	}
	
	public void setUsername(String username) {
		this.username = new String();
		this.username = username;
		
		try {
			AdminClient adminCLient = new AdminClient(SocketLibrary.host, SocketLibrary.port, username);
			adminCLient.setMainController(this);
			System.out.println("Admin: " + username);
			adminCLient.execute();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
