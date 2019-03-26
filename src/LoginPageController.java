import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class LoginPageController {
	
	@FXML private TextField username;
	@FXML private TextField password;
	@FXML private Button loginButton;
	private boolean proceed;
	
	@FXML protected void loginClick(ActionEvent event) throws FileNotFoundException
	{
		
		File file = new File("users");
		Scanner scan = new Scanner(file);
        while(scan.hasNext())
        {
        String usr = scan.next();
        String pwd = scan.next();
        if(username.getText().equals(usr) && password.getText().equals(pwd))
        {
        	proceed = true;
        }
        
		try {
			if(proceed == true)
			{
			Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
			Parent newScene = FXMLLoader.load(getClass().getResource("TabFrame.fxml"));
			stage.getScene().setRoot(newScene);
			}
			
		}
		
		catch(Exception e)
		{
			System.out.println("Error changing scenes");
		}
        }
		
	}
	@FXML protected void createClick(ActionEvent event)
	{
		System.out.println("Create clicked");
		username.setText("Create Clicked");
		try {
			Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
			Parent newScene = FXMLLoader.load(getClass().getResource("NewUserFrame.fxml"));
			stage.getScene().setRoot(newScene);
		}
		catch(Exception e)
		{
			System.out.println("Error changing to Create New User");
		}
		
	}
	@FXML protected void backClick(ActionEvent event)
	{
		//obviously not really what we want to do, just making sure the button is buttoning
		//System.out.println("Login clicked");
		//username.setText("Login Clicked");
		try {
			Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
			Parent newScene = FXMLLoader.load(getClass().getResource("LoginPage.fxml"));
			stage.getScene().setRoot(newScene);
		}
		catch(Exception e)
		{
			System.out.println("Error changing scenes withn back button");
		}
		
	}
}
