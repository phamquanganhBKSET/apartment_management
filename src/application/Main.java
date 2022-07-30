package application;
	
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.scene.Parent;
import javafx.scene.Scene;


public class Main extends Application {
	private double offset_x;
    private double offset_y;
	
	@Override
	public void start(Stage primaryStage) {
		try {
			Parent root = FXMLLoader.load(getClass().getResource("/fxml/Login.fxml"));
			Scene scene = new Scene(root);
			primaryStage.initStyle(StageStyle.UNDECORATED);
			scene.getStylesheets().add(getClass().getResource("/css/login.css").toExternalForm());
			
			// Drag scene
			scene.setOnMousePressed(event -> {
	            offset_x = event.getSceneX();
	            offset_y = event.getSceneY();
	        });
	        scene.setOnMouseDragged(event -> {
	            primaryStage.setX(event.getScreenX() - offset_x);
	            primaryStage.setY(event.getScreenY() - offset_y);
	        });
	        
			primaryStage.setScene(scene);
			primaryStage.setResizable(false);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
