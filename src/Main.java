import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application{
	@Override
	public void start(Stage primaryStage) throws Exception
	{
		User user = new User();
		
		
		//Parent root = FXMLLoader.load(getClass().getResource("LoginPage.fxml"));
		//primaryStage.setTitle("Key To Keto");
		//primaryStage.setScene(new Scene(root, 640, 400));
		//primaryStage.setResizable(false);
		//primaryStage.show();
		
		
		
		//Code below is new. Note that you dont have a fx:controller in the LoginPage.fxml file anymore
		//because we are setting the controller manually for that page. this allows us to inject our dependencies 
		//such as the user data object
		FXMLLoader loader = new FXMLLoader(getClass().getResource("LoginPage.fxml"));
		loader.setController(new LoginPageController(user));
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
