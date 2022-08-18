package controller;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class AddWaterController {
    
    private Scene addWaterScene;
    Connection connection;
    Statement statement;
    
    // Set scene
    public void setScene(Scene addWaterScene) {
        this.addWaterScene = addWaterScene;
    }

    @FXML
    private Button add;

    @FXML
    private ImageView back;

    @FXML
    private Label close;

    @FXML
    private Label minimize;

    @FXML
    private TextField month;

    @FXML
    private TextField newNum;

    @FXML
    private TextField oldNum;

    @FXML
    private TextField roomNumber;
    
    
    // Function load data from sql to display in textfileds    
    void load(Connection connection, Statement statement, String room) throws SQLException {
        this.connection = connection;
        this.statement = statement;
        roomNumber.setText(room);
        LocalDate currentime = LocalDate.now();
        int curMonth = currentime.getMonthValue();
		int curYear = currentime.getYear();
		String date = Integer.toString(curYear) + "-" +  Integer.toString(curMonth) + "-1";
		month.setText(date);
        String s = "select MAX(apartment_manager.dich_vu.Thang) from apartment_manager.dich_vu where apartment_manager.dich_vu.Ma_phong = " + room;
        ResultSet rs = statement.executeQuery(s);
        rs.next();
        System.out.println(rs.getString(1));
        if (rs.getString(1) == null) {
            oldNum.setText("0");
        }
        else {
            s = "select apartment_manager.dich_vu.So_moi from apartment_manager.dich_vu where Ma_phong = " + room 
                    + " and Ma_dich_vu = 2 and Thang = \'" + rs.getString(1) + "\'";
            System.out.println(s);
            ResultSet rs2 = statement.executeQuery(s);
            rs2.next();
            oldNum.setText(rs2.getString(1));
        }
    }

    // Add new Water when add button is clicked
    @FXML
    void actionAddWater(MouseEvent event) {
        if (newNum.getText().isEmpty()) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setHeaderText("New Electric Number is empty!");
        alert.showAndWait();
        }
        else {
            // Add data into dich_vu table
            if ((Integer.valueOf(newNum.getText())) < Integer.valueOf(oldNum.getText())) {
                // Alert
                Alert alert = new Alert(AlertType.ERROR);
                alert.setHeaderText("Check Water Number!");
                alert.showAndWait();
            }
            else {
                try {
                    String s = "insert into apartment_manager.dich_vu(Ma_phong, Ma_dich_vu, So_cu, So_moi, Thang, Da_dong) "
                            + "values (" + roomNumber.getText() + ", 1, " + oldNum.getText() + ", " + newNum.getText() + ", \'" 
                            + month.getText() + "\', 0)";
                    statement.executeUpdate(s);
                    // Alert
                    Alert alert = new Alert(AlertType.INFORMATION);
                    alert.setHeaderText("Success!");
                    alert.showAndWait();

                    // Go back
                    Scene currScene = (Scene) ((Node) event.getSource()).getScene();
                    Stage currStage = (Stage) currScene.getWindow();
                    currStage.setScene(addWaterScene);
                    currStage.centerOnScreen();
                    currStage.show();

                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }
    }

    // Go to previous stage when button goback is clicked
    @FXML
    void goBack(MouseEvent event) {
        try {
            Scene currScene = (Scene) ((Node) event.getSource()).getScene();
            Stage currStage = (Stage) currScene.getWindow();
            currStage.setScene(addWaterScene);
            currStage.centerOnScreen();
            currStage.show();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    // Close stage when X is clicked
    @FXML
    void handleClose(MouseEvent event) {
        Stage stage = (Stage) minimize.getScene().getWindow();
        stage.close();
    }

    // Minimize stage when - is clicked
    @FXML
    void handleMinimize(MouseEvent event) {
        Stage stage = (Stage) minimize.getScene().getWindow();
        stage.setIconified(true);
    }

    @FXML
    void initialize() {
        roomNumber.setDisable(true);
        month.setDisable(true);
        oldNum.setDisable(true);
    }
}
