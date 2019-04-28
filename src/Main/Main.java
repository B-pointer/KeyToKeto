package Main;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import Controllers.LoginPageController;
import Controllers.StageLoader;
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
		
		/*
		DataConnection data = new DataConnection();
		
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/LoginPage.fxml"));
		loader.setController(new LoginPageController(data));
		Parent newScene = loader.load();
		primaryStage.setScene(new Scene(newScene, 900, 560));
		primaryStage.setResizable(false);
		primaryStage.show();
		*/
		
		DataConnection data = new DataConnection();
		StageLoader.loadLogin(primaryStage, data);
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
