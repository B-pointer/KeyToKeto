package Main;
import Controllers.LoginPageController;
import DataAccess.TemporaryDatabaseSimulator;
import Models.User;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application{
	@Override
	public void start(Stage primaryStage) throws Exception
	{
		//User user = new User();
		
		//Code below is new. Note that you dont have a fx:controller in the LoginPage.fxml file anymore
		//because we are setting the controller manually for that page. this allows us to inject our dependencies 
		//such as the user data object
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/LoginPage.fxml"));
		//loader.setController(new LoginPageController(user));
		loader.setController(new LoginPageController(new TemporaryDatabaseSimulator()));
		Parent newScene = loader.load();
		primaryStage.setScene(new Scene(newScene, 640, 400));
		primaryStage.setResizable(false);
		primaryStage.show();
	}
	
	public static void main(String[] args)
	{
		launch(args);
	}
}
