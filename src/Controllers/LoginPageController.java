package Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

import DataAccess.DataAccessible;
import DataAccess.TemporaryDatabaseSimulator;
import Models.User;

public class LoginPageController {
	
	@FXML private TextField username;
	@FXML private TextField password;
	@FXML private Button loginButton;
	private boolean proceed;
	private User user;
	private DataAccessible data;
	
	//gives us a reference to our user object, so we can create it when we first run the program
	//public LoginPageController(User aUser)
	//{
	//	user = aUser;
	//}
	
	
	public LoginPageController()
	{
		data = new TemporaryDatabaseSimulator();
	}
	
	@FXML protected void loginClick(ActionEvent event) throws FileNotFoundException
	{			
		if(data.login(username.getText(), password.getText()))
		{
			proceed = true;
			user = data.getUser(username.getText());
		}
		try {
			if(proceed == true)
			{
				Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
				loadTabs(stage);
			}	
		}	
		catch(Exception e)
		{
			System.out.println("Error changing scenes");
			e.printStackTrace();
		}
	}
	
	
	@FXML protected void createClick(ActionEvent event)
	{
		System.out.println("Create clicked");
		username.setText("Create Clicked");
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/NewUser.fxml"));
			loader.setController(this);
			Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
			Parent newScene = loader.load();
			stage.getScene().setRoot(newScene);
		}
		catch(Exception e)
		{
			System.out.println("Error changing to Create New User");
		}	
	}
	
	
	@FXML protected void backClick(ActionEvent event)
	{
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/LoginPage.fxml"));
			loader.setController(this);
			Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
			Parent newScene = loader.load();
			stage.getScene().setRoot(newScene);		
		}
		catch(Exception e)
		{
			System.out.println("Error changing scenes withn back button");
		}
		
	}
	
	
	private void loadTabs(Stage stage) throws Exception
	{
		//Parent newScene = FXMLLoader.load(getClass().getResource("/fxml/TabFrame.fxml"));
	//	stage.getScene().setRoot(newScene);
		
		
		Callback<Class<?>, Object> controllerFactory = new Callback<Class<?>, Object>() {
		    @Override
		    public Object call(Class<?> type) {
		        if(type == CurrentStatsTabController.class){
		        	return new CurrentStatsTabController(user, data);
		        }
		       // if(type == tab2Controller.class){
		        //	return  new tab2Controller(model);
		       // }
		        return null;
		    }
		};
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/TabFrame.fxml"));
		loader.setControllerFactory(controllerFactory);	
		Parent root = loader.load();	
		stage.setScene(new Scene(root, 640, 400));
		stage.show();	
		
	}
}
