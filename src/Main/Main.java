package Main;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import Controllers.LoginPageController;
import Controllers.StageLoader;
import DataAccess.APIEndpoint;
import DataAccess.DataConnection;
import Models.FoodItem;
import Models.User;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application{
	
	DataConnection data;
	
	
	@Override
	public void start(Stage primaryStage) throws Exception
	{
		
		primaryStage.setOnCloseRequest(e -> Platform.exit());
		
		DataConnection data = new DataConnection();
		StageLoader.loadLogin(primaryStage, data);
		primaryStage.show();
		
		//data.utility();
	}
	
	@Override
	public void stop()
	{
		if(data != null)
		{
			//System.out.println("Stop method called");
			data.closeConnection();
		}
	}
	
	public static void main(String[] args)
	{
		launch(args);
	}
}
