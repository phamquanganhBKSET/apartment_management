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
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.HouseHolder;
import model.Room;

public class AdminMainPageController implements Initializable {
	private Connection connection;
	private Statement statement;
	Scene loginScene;
	boolean inRoomPage = true;
	
	@FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

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
    private ImageView roomImage;

    @FXML
    private Label typeName;
    
    @FXML
    private Label roomName;

    @FXML
    private HBox roomsManage;

    @FXML
    private ScrollPane scrollPane;
    
    @FXML
    private Button searchButton;
    
    @FXML
    private ImageView addUser;
    
    @FXML
    private Hyperlink myAccount;
    
    @FXML
    private Label notify;
    
    @FXML
    private TextField searchText;
    
    ObservableList<String> filterList = FXCollections.observableArrayList("All", "Empty Rooms", "Full Rooms");
    
    ObservableList<String> filterListHH = FXCollections.observableArrayList("All", "Male", "Female");
    
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
    	inRoomPage = false;
    	typeName.setText("User ID");
    	filter.setItems(filterListHH);
    	setChoosenUser(listHouseHolder.get(0));
    	gridPane.setVisible(false);
    	gridPane.setDisable(true);
    	gridPaneHH.setVisible(true);
    	gridPane.setDisable(false);
    }
    
    @FXML
    void handleRoomsManage(MouseEvent event) {
    	inRoomPage = true;
    	typeName.setText("Room ID");
    	filter.setItems(filterList);
    	setChoosenRoom(listRoom.get(0));
    	gridPaneHH.setVisible(false);
    	gridPaneHH.setDisable(true);
    	gridPane.setVisible(true);
    	gridPane.setDisable(false);
    }
    
    @FXML
    void handleSearch(ActionEvent event) {
    	String searchString = searchText.getText();
    	if (inRoomPage) {
    		
    	} else {
    		
    	}
    }
    
    @FXML
    void handleAddUser(MouseEvent event) {

    }
    
    @FXML
    void handleEditMyAccount(ActionEvent event) {

    }
    
    @FXML
    void handleNotify(MouseEvent event) {

    }
    
    @FXML
    void handleViewInfo(MouseEvent event) {

    }
    
    private List<Room> getData() throws SQLException {
    	List<Room> listRoom = new ArrayList<>();
    	Room room;
    	
    	for (int i = 1; i <= 5; i++) {
    		for (int j = 1; j <= 4; j++) {
    			String sqlString = "select * from apartment_manager.phong where ma_phong = \'" + Integer.toString(i * 100 + j) + "\'";
    			ResultSet rs = statement.executeQuery(sqlString);
    			room = new Room();
    			room.setRoomName(Integer.toString(i * 100 + j));
    			room.setStatus(rs.getBoolean(5));
    			room.setPeople(rs.getInt(3));
    			String type = rs.getString(4);
    			room.setType(Character.toString(type.charAt(0)));
    			if (rs.getBoolean(5)) {
    				room.setColor("00CED1");
    			} else {
    				room.setColor("FFFFFF");
    			}
    			Path path = FileSystems.getDefault().getPath("").toAbsolutePath();
    			room.setImgSrc(path + "\\icon\\room" + Integer.toString(j) + ".png");
    			listRoom.add(room);
    		}
    	}
    	
    	return listRoom;
    }
    
    private List<HouseHolder> getDataHH() {
    	List<HouseHolder> listHouseHolder = new ArrayList<>();
    	HouseHolder houseHolder;
    	
    	for (int i = 1; i <= 20; i++) {
    		houseHolder = new HouseHolder();
    		houseHolder.setUserName("anhpq" + Integer.toString(i));
    		Path path = FileSystems.getDefault().getPath("").toAbsolutePath();
    		houseHolder.setImgSrc(path + "\\icon\\user_male.png");
    		listHouseHolder.add(houseHolder);
    	}
    	
    	return listHouseHolder;
    }
    
    private void setChoosenRoom(Room room) {
    	roomName.setText(room.getRoomName());
    	Image image = new Image(String.valueOf(new File(room.getImgSrc())));
    	roomImage.setImage(image);
    }
    
    private void setChoosenUser(HouseHolder houseHolder) {
    	roomName.setText(houseHolder.getUserName());
    	Image image = new Image(String.valueOf(new File(houseHolder.getImgSrc())));
    	roomImage.setImage(image);
    }
    
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		gridPaneHH.setVisible(false);
    	gridPane.setVisible(true);
    	gridPane.setDisable(false);
		
		filter.setItems(filterList);
		try {
			listRoom.addAll(getData());
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		setChoosenRoom(listRoom.get(0));
		
		listHouseHolder.addAll(getDataHH());
		
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
	
	public void setLoginScene(Scene loginScene) {
		this.loginScene = loginScene;
	}
	
	public void setConnection(Connection connection) {
		this.connection = connection;
	}
	
	public void setStatement(Statement statement) {
		this.statement = statement;
	}
}
