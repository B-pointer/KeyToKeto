package Main;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import Controllers.LoginPageController;
import DataAccess.APIEndpoint;
import DataAccess.DataConnection;
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
		//loader.setController(new LoginPageController(new APIEndpoint()));
		loader.setController(new LoginPageController(new DataConnection()));
		Parent newScene = loader.load();
		primaryStage.setScene(new Scene(newScene, 900, 560));
		primaryStage.setResizable(false);
		primaryStage.show();
		
		
		
		
		
		//DATABASE TEST CODE HERE *******************************************************************************************************************************************************************************
		/*
		DataConnection a = new DataConnection();
		System.out.print(a.isUsernameAvailable("Bobert"));
		DateTimeFormatter dtf =  DateTimeFormatter.ofPattern("yyyy-MM-dd");
		LocalDate date = LocalDate.parse("1996-09-02", dtf);
		User u = new User("bkp5", "mail1@mail.com", "myPassword",date, "Male", 70, 160, 1752);
		if(a.isUsernameAvailable(u.getName()))
		{
			a.createUser(u);
		}
		System.out.print(a.isUsernameAvailable("bkp5"));
		if(!a.isUsernameAvailable("bkp5"))
		{
			User b = a.getUser("bkp5");
			System.out.println(b.getName() + " email: " + b.getEmail() + " password: " + b.getPass() 
				+ " date: " + b.getBirth().toString() + " gender: " + b.getGender() 
				+ " height: " + b.getHeight() + " weight: "  + b.getWeight() 
				+ " calories: " + b.getCalories());
		}
		 
		System.out.println("result of logging in as bkp5: " + a.login("bkp5", "myPassword"));
		System.out.println("result of logging in as bkp5: " + a.login("bkp6", "myPassword"));
		*/
	}
	
	public static void main(String[] args)
	{
		launch(args);
	}
}
