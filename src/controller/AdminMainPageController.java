package controller;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.FileSystems;
import java.nio.file.Path;
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
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Room;

public class AdminMainPageController implements Initializable {

	Scene loginScene;
	
	@FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Label close;
    
    @FXML
    private Label minimize;
    
    @FXML
    private VBox chosenRoomCard;

    @FXML
    private GridPane gridPane;

    @FXML
    private ImageView roomImage;

    @FXML
    private Label roomName;

    @FXML
    private ScrollPane scrollPane;
    
    @FXML
    private ComboBox<String> filter;
    
    ObservableList<String> filterList = FXCollections.observableArrayList("All", "Empty Rooms", "Full Rooms");
    
    private RoomItemListener roomItemListener;
	
    private List<Room> listRoom = new ArrayList<>();
    
    @FXML
    private void handleClose(MouseEvent event) {
    	System.exit(0);
    }
    
    @FXML
    private void handleMinimize(MouseEvent event) {
    	Stage stage = (Stage) minimize.getScene().getWindow();
        stage.setIconified(true);
    }
    
    private List<Room> getData() {
    	List<Room> listRoom = new ArrayList<>();
    	Room room;
    	
    	for (int i = 1; i <= 5; i++) {
    		for (int j = 1; j <= 4; j++) {
    			room = new Room();
    			room.setRoomName(Integer.toString(i * 100 + j));
    			Path path = FileSystems.getDefault().getPath("").toAbsolutePath();
    			room.setImgSrc(path + "\\icon\\room" + Integer.toString(j) + ".png");
    			listRoom.add(room);
    		}
    	}
    	
    	return listRoom;
    }
    
    private void setChoosenRoom(Room room) {
    	roomName.setText(room.getRoomName());
    	Image image = new Image(String.valueOf(new File(room.getImgSrc())));
    	roomImage.setImage(image);
    }
    
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		filter.setItems(filterList);
		listRoom.addAll(getData());
		setChoosenRoom(listRoom.get(0));
		roomItemListener = new RoomItemListener() {
			@Override
			public void onClickListener(Room room) {
				setChoosenRoom(room);
			}
		};
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
	}

	public void comboBoxChanged (ActionEvent event) {
		
	}
	
	public void setLoginScene(Scene loginScene) {
		this.loginScene = loginScene;
	}
}
