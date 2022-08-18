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
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
//import java.util.Date;
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
import model.ModelSummary;
import model.ModelXe;
import model.Room;
import model.socket.AdminClient;
import model.socket.SocketLibrary;

public class AdminMainPageController implements Initializable {
	private String username;
	private Connection connection;
	private Statement statement;
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
    
    @FXML
    private Button updateVehMoney;
    
    // Update vehicle fee for new month
    @FXML
    void actionUPdate(ActionEvent event) {
		Calendar cal = Calendar.getInstance();
		int res = cal.getActualMaximum(Calendar.DATE);
		LocalDate currentime = LocalDate.now();
        int curDay = currentime.getDayOfMonth();
        int curMonth = currentime.getMonthValue();
        int curYear = currentime.getYear();
        System.out.println(curYear);
        System.out.println(curMonth);
        System.out.println(curDay);
        int nextMonth = 0;
        int nextYear = curYear;
        if (curDay == 16) {
        	if (curMonth == 12) {
				nextMonth = 1;
				nextYear = curYear + 1;
			}
        	else {
        		nextMonth = curMonth + 1;
        		nextYear = curYear;
        	}
        	
        	// Get list vehicle
        	ArrayList<ModelXe> listXe = new ArrayList<>();
        	String query = "select * from apartment_manager.xe where Thang = \'" + curYear + "-" + curMonth + "-1\'";
        	System.out.println(query);
			try {
				// Get date time
				Date date2 = Date.valueOf("" + curYear + "-" + curMonth + "-1");
				ResultSet rs = statement.executeQuery(query);
				while (rs.next()) {
					listXe.add(new ModelXe(rs.getInt(1), rs.getString(2), rs.getInt(3), rs.getString(4), rs.getString(5), rs.getString(6), date2, false, 0));
				}
				for (ModelXe i : listXe) {
					// Insert new record to table Xe in database
					String s = "insert into apartment_manager.xe (Ve_xe, Ten_chu_xe, Ma_phong, Loai_xe, Bien_so_xe, Mau_sac, Thang, Da_dong) " +
								" values (" + i.getVeXe() + ", \'" + i.getTenChuXe() + "\', " + i.getMaPhong() + ", \'" + i.getLoaiXe() + "\', \'" 
								+ i.getBienSoXe() + "\', \'" + i.getMauSac() + "\', \'" + nextYear+"-"+nextMonth+"-"+"1" + "\', " + i.getDaDong() + ")";
					System.out.println(s);
					statement.executeUpdate("SET FOREIGN_KEY_CHECKS=0;");
					statement.executeUpdate(s);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
	    	// Alert
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setHeaderText("Success!");
			alert.showAndWait();
		}
    }
    
    // List filter for rooms
    private ObservableList<String> filterList = FXCollections.observableArrayList("All", "Empty Rooms", "Full Rooms");
    
    // List filter for householders
    private ObservableList<String> filterListHH = FXCollections.observableArrayList("All", "Male", "Female");
    
    private Room currRoom;
    private String currUserID;
    
    // Listener for room card
    private RoomItemListener roomItemListener = new RoomItemListener() {
		@Override
		public void onClickListener(Room room) {
			setChoosenRoom(room);
		}
	};
    
	// Listener for householder card
    private HouseHolderItemListener houseHolderItemListener = new HouseHolderItemListener() {
		@Override
		public void onClickListener(HouseHolder houseHolder) {
			setChoosenUser(houseHolder);
		}
	};
	
	// List room
    private List<Room> listRoom = new ArrayList<>();
    
    // List householder
    private List<HouseHolder> listHouseHolder = new ArrayList<>();
    
    // Close window
    @FXML
    private void handleClose(MouseEvent event) {
    	System.exit(0);
    }
    
    // Minimize window
    @FXML
    private void handleMinimize(MouseEvent event) {
    	Stage stage = (Stage) minimize.getScene().getWindow();
        stage.setIconified(true);
    }
    
    // Switch from room page to householder page
    @FXML
    void handleHouseHoldersManage(MouseEvent event) {
    	// Set text for textfields in choosen card
    	filter.setPromptText("Filter");
    	typeLabel.setText("Citizen ID");
    	ownerIDLabel.setText("Phone");
    	peopleLabel.setText("Email");
    	inRoomPage = false;
    	typeName.setText("User ID");
    	filter.setItems(filterListHH);
    	setChoosenUser(listHouseHolder.get(0));
    	
    	// Set visible for 4 lists
    	gridPane.setVisible(false);
    	gridPane.setDisable(true);
    	gridPaneHH.setVisible(true);
    	gridPaneHH.setDisable(false);
    	gridFilterUser.setVisible(false);
		gridFilterUser.setDisable(true);
    	gridFilterRoom.setVisible(false);
		gridFilterRoom.setDisable(true);
    }
    
    // Switch from householder page to room page
    @FXML
    void handleRoomsManage(MouseEvent event) {
    	// Set text for textfields in choosen card
    	filter.setPromptText("Filter");
    	typeLabel.setText("Type");
    	ownerIDLabel.setText("Owner ID");
    	peopleLabel.setText("People");
    	inRoomPage = true;
    	typeName.setText("Room ID");
    	filter.setItems(filterList);
    	setChoosenRoom(listRoom.get(0));
    	
    	// Set visible for 4 lists
    	gridPaneHH.setVisible(false);
    	gridPaneHH.setDisable(true);
    	gridPane.setVisible(true);
    	gridPane.setDisable(false);
    	gridFilterUser.setVisible(false);
		gridFilterUser.setDisable(true);
		gridFilterRoom.setVisible(false);
		gridFilterRoom.setDisable(true);
    }
    
    // Search rooms and householders
    @FXML
    void handleSearch(ActionEvent event) {
    	String searchString = searchText.getText();
    	// If in room page
    	if (inRoomPage) {
    		for (Room room : listRoom) { // Search in list rooms
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
    	} else { // If in householder page
    		for (HouseHolder houseHolder : listHouseHolder) { // Search in list householders
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
    
    // "Enter" key pressed when in search box
    @FXML
    public void handleEnterSearch(KeyEvent event) {
    	// Search similar to the previous function
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
    
    // SHow filter room
    public void showFilterRoom(boolean status) {
    	// Set visible for 4 lists
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
					
					// Load room item
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
    
    // Show filter householder
    public void showFilterUser(String gender) {
    	// Set visible for 4 lists
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
					
					// Load householder item
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
    
    // Handle filter: rooms or householders
    @FXML
    public void handleFilter(ActionEvent event) {
    	int result = 0;
    	String filterValue = filter.getValue();
    	if (filterValue != null) {
    		// If in room page
	    	if (inRoomPage) {
	    		if (filterValue.equals("All")) {
	    			// Get all rooms
	    			resultFilter.setVisible(false);
	    	    	gridPane.setVisible(true);
	    	    	gridPane.setDisable(false);
	    	    	gridFilterRoom.setVisible(false);
	    	    	gridFilterRoom.setDisable(true);
	    	    	gridFilterUser.setVisible(false);
	    	    	gridFilterUser.setDisable(true);
	    		}
	    		else if (filterValue.equals("Empty Rooms")) {
	    			// Get empty rooms
		    		for (Room room : listRoom) {
		    			if (room.getStatus() == true) {
		    				result++;
		    			}
		    		}
		    		resultFilter.setVisible(true);
		    		resultFilter.setText("Result: " + result);
		    		this.showFilterRoom(true);
	    		} else {
	    			// Get full rooms
	    			for (Room room : listRoom) {
		    			if (room.getStatus() == false) {
		    				result++;
		    			}
		    		}
	    			resultFilter.setVisible(true);
		    		resultFilter.setText("Result: " + result);
		    		this.showFilterRoom(false);
	    		}
	    	} else { // If in householder page
				if (filterValue.equals("All")) {
					// Get all householders
					resultFilter.setVisible(false);
					gridPaneHH.setVisible(true);
	    	    	gridPaneHH.setDisable(false);
	    	    	gridFilterRoom.setVisible(false);
	    	    	gridFilterRoom.setDisable(true);
	    	    	gridFilterUser.setVisible(false);
	    	    	gridFilterUser.setDisable(true);
				}
	    		else if (filterValue.equals("Male")) {
	    			// Get householders who are male
	    			for (HouseHolder houseHolder : listHouseHolder) {
		    			if (houseHolder.getGender() != null && houseHolder.getGender().equals("Nam")) {
		    				result++;
		    			}
		    		}
	    			resultFilter.setVisible(true);
		    		resultFilter.setText("Result: " + result);
		    		this.showFilterUser("Nam");
	    		} else {
	    			// Get householders who are female
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
    		// Set visible for 4 lists
    		gridFilterRoom.setVisible(false);
        	gridFilterRoom.setDisable(false);
        	gridFilterUser.setVisible(false);
        	gridFilterUser.setDisable(false);
    		resultFilter.setVisible(false);
    		filter.setPromptText("Filter");
    	}
    }
    
    // Add user
    @FXML
    void handleAddUser(MouseEvent event) {
    	try {
    		// Go to add user page
			Stage stage = new Stage();
			stage.initStyle(StageStyle.UNDECORATED);
			
			String file;

			// Choose type of user you want to add
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
			
			// Load add admin page
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
    
    // Update data of users when add, edit or delete users
    public void updateDataUser() {
    	listHouseHolder.clear();
		boolean hasUser = false;
    	
		// Get list householders
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
		
		// Count number of householders
		numHouseHolders.setText("about " + listHouseHolder.size() + " householders");
		
		int column = 0;
		int row = 1;
		
		// View list householders
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
    
    // Update data of rooms when add, edit or delete rooms
    public void updateDataRoom() {
    	listRoom.clear();
    	
    	// Get list rooms
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
		
    	// Count number of rooms
    	numRooms.setText("about " + listRoom.size() + " rooms");
		
		int column = 0;
		int row = 1;
		
		// View list rooms
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
    
    // Edit current admin account's information 
    @FXML
    void handleEditMyAccount(ActionEvent e) {
    	try {
    		Stage stage = new Stage();
    		stage.initStyle(StageStyle.UNDECORATED);
    		
    		// Go to view admin information page
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
    
    // View user or room information
    @FXML
    void handleViewInfo(MouseEvent e) {
    	// If in room page
    	if (inRoomPage) {
    		try {
	    		Stage stage = new Stage();
		    	stage.initStyle(StageStyle.UNDECORATED);
		    	
		    	// Go to view room information page
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
    	} else { // If in householder page
    		try {
	    		Stage stage = new Stage();
		    	stage.initStyle(StageStyle.UNDECORATED);
		    	
		    	// Go to view room information page
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
    
    // Get list rooms' data
    private List<Room> getData() throws SQLException {
    	List<Room> listRoom = new ArrayList<>();
    	Room room;
    	int i = 1;
    	
    	// Select data from table Phong in database
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
    
    // Get list householders' data
    private List<HouseHolder> getDataHH() throws SQLException {
    	List<HouseHolder> listHouseHolder = new ArrayList<>();
    	HouseHolder houseHolder;
    	
    	// Select data from table Chu_so_huu in database
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
    
    // Set information for choosen room card
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
    
    // Set information for choosen householder card
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
		// Set visible for 4 lists
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
    		// COnnnect database
			connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/apartment_manager", "root", library.password);
			statement = connection.createStatement();
		} catch (SQLException e) {
			e.printStackTrace();
		}
    	
    	// Go to room page by default
		filter.setItems(filterList);
		try {
			listRoom.addAll(getData());
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		// Set choosen room card is room 101 by default
		setChoosenRoom(listRoom.get(0));
		
		// Get information for list householders
		try {
			listHouseHolder.addAll(getDataHH());
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		
		// Number of rooms and number of householders
		numRooms.setText("about " + listRoom.size() + " rooms");
		numHouseHolders.setText("about " + listHouseHolder.size() + " householders");
		
		int column = 0;
		int row = 1;
		
		// Get data for list rooms and view list rooms' data 
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
		
		// Get data for list householders but not view 
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
	
	// Handle if there are at least one notify (request)
	public void handleNotify(String sms) {
		String parts[] = sms.split(":");
		String toUser = parts[1];
		// If request is delete vehicle
		if (parts[2].equals("delete")) {
			//From User:username:delete:ticket:To:all
			
			// Separate fields from message received from server
			String ticket = parts[3];
			String sqlString = "select * from apartment_manager.xe where Ve_xe = " + ticket;
			
			boolean hasDebt = false;
			ResultSet rs;
			
			// Check if debt is not 0
			try {
				rs = statement.executeQuery(sqlString);
				
				while(rs.next()) {
					if (rs.getBoolean(8) == false) {
						hasDebt = true;
					}
				}
				
				String message = "Delete vehicle has ticket " + ticket;
				if (hasDebt) {
					message += " (still owe money)";
				}
				
				// View request
				Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
				alert.setTitle("NOTIFY");
				alert.setHeaderText("Request from user " + toUser + ": " + message);
				alert.setContentText("Choose your option");
				
				ButtonType buttonTypeAccept = new ButtonType("Accept", ButtonBar.ButtonData.YES);
				ButtonType buttonTypeRefuse = new ButtonType("Refuse", ButtonBar.ButtonData.NO);
				
				alert.getButtonTypes().setAll(buttonTypeAccept, buttonTypeRefuse);
				
				// Get button type
				Optional<ButtonType> result = alert.showAndWait();
				
				if (result.get() == buttonTypeAccept) {
					System.out.println("Accept");
					messageToUser = "Accept";
					this.toUser = toUser;
					
					String s = "delete from apartment_manager.xe where Ve_xe = " + ticket;
					statement.executeUpdate (s);
				} else {
					System.out.println("Refuse");
					messageToUser = "Refuse";
					this.toUser = toUser;
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} else { // If request is add vehicle
			//From User:username:Ten_chu_xe:Ma_phong:Loai_xe:Bien_so_xe:Mau_sac:Thang:To:all
			
			// Separate fields from message received from server
			String Ten_chu_xe = parts[2];
			String Ma_phong = parts[3];
			String Loai_xe = parts[4];
			String Bien_so_xe = parts[5];
			String Mau_sac = parts[6];
			String Thang = parts[7];
			
			// View request
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
			
			// Get button type
			Optional<ButtonType> result = alert.showAndWait();
			
			if (result.get() == buttonTypeAccept) {
				System.out.println("Accept");
				messageToUser = "Accept";
				this.toUser = toUser;
				
				int ticketMax = 0;
				
				try {
					// Get max of Ve_xe from table Xe in database
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
					// Insert new record to table Xe in database
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
	
	public void setUsername(String username) {
		this.username = new String();
		this.username = username;
		
		try {
			// Create new admin client (new socket) and connect to server
			AdminClient adminCLient = new AdminClient(SocketLibrary.host, SocketLibrary.port, username);
			adminCLient.setMainController(this);
			System.out.println("Admin: " + username);
			adminCLient.execute();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
