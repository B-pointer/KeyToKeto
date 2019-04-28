package Controllers;

import javafx.application.Platform;
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
	private HomePageController home;
	
	
	public LoginPageController(DataAccessible data)
	{
		this.data = data;
	}
	
	@FXML private void initialize()
	{
		Platform.runLater(()-> {
			loginButton.requestFocus();
		});
	}
	
	@FXML protected void loginClick(ActionEvent event) throws FileNotFoundException
	{			
		if(data.login(username.getText(), password.getText())){
			proceed = true;
			user = data.getUser(username.getText());
			
		}
		try {
			if(proceed == true){
				Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
				loadTabs(stage);
			}	
		}	
		catch(Exception e){
			System.out.println("Error changing scenes");
			e.printStackTrace();
		}
	}
	
	
	@FXML protected void createUserPageClick(ActionEvent event)
	{
		//System.out.println("Create clicked");
		//username.setText("Create Clicked");
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/NewUser.fxml"));
			loader.setController(new CreateUserPageController(data));
			Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
			Parent newScene = loader.load();
			stage.getScene().setRoot(newScene);
		}
		catch(Exception e)
		{
			System.out.println("Error changing to Create New User");
			e.printStackTrace();
		}	
	}
	
	private void loadTabs(Stage stage) throws Exception
	{
		Callback<Class<?>, Object> controllerFactory = new Callback<Class<?>, Object>() {
		    @Override
		    public Object call(Class<?> type) {
		        if(type == CurrentStatsTabController.class){
		        	return new CurrentStatsTabController(user, data);
		        }
		       if(type == ResultsController.class){
		        	return  new ResultsController(user, data);
		        }
		       if(type == FoodController.class)
		       {
		    	   return new FoodController(user, data);
		       }
		        return null;
		    }
		};
		
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/TabFrame.fxml"));
		loader.setControllerFactory(controllerFactory);	
		Parent root = loader.load();	
		stage.setScene(new Scene(root, 900, 560));
		stage.show();	
		
	}
}
