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
import model.HouseHolder;

public class HouseHolderItemController implements Initializable {

	@FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ImageView userImage;

    @FXML
    private Label userName;
    
    private HouseHolder houseHolder;
    
    private HouseHolderItemListener houseHolderItemListener;

    // Householder listener
    @FXML
    void handleMouseClicked(MouseEvent event) {
    	houseHolderItemListener.onClickListener(houseHolder);
    }
	
    // Set data of householder item
    public void setData(HouseHolder houseHolder, HouseHolderItemListener houseHolderItemListener) {
    	this.houseHolder = houseHolder;
    	this.houseHolderItemListener = houseHolderItemListener;
    	userName.setText(houseHolder.getUserName());
    	Image image = new Image(String.valueOf(new File(houseHolder.getImgSrc())));
    	userImage.setImage(image);
    }
    
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
	}

}
