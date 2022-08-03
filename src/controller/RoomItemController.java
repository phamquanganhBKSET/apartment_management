package controller;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import model.Room;

public class RoomItemController implements Initializable {

	@FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ImageView roomImage;

    @FXML
    private Label roomName;
	
    private Room room;
    
    private RoomItemListener roomItemListener;
    
    @FXML
    private void handleMouseClicked(MouseEvent event) {
    	roomItemListener.onClickListener(room);
    }
    
    public void setData(Room room, RoomItemListener roomItemListener) {
    	this.room = room;
    	this.roomItemListener = roomItemListener;
    	roomName.setText(room.getRoomName());
    	Image image = new Image(String.valueOf(new File(room.getImgSrc())));
    	roomImage.setImage(image);
    }
    
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
	}

}
