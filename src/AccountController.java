

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;
import javafx.stage.Stage;

public class AccountController {
	
	@FXML private TextField txtName;
	@FXML private TextField txtPass;
	@FXML private Button create;
	@FXML private Button back;
	
	@FXML protected void loginClick(ActionEvent event)
	{
		//obviously not really what we want to do, just making sure the button is buttoning
		//System.out.println("Login clicked");
		//username.setText("Login Clicked");
		try {
			Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
			Parent newScene = FXMLLoader.load(getClass().getResource("fxml/TabFrame.fxml"));
			stage.getScene().setRoot(newScene);
		}
		catch(Exception e)
		{
			System.out.println("Error changing scenes");
		}
		
	}
	
	@FXML protected void createClick(ActionEvent event)
	{
		try {
			Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
			Parent newScene = FXMLLoader.load(getClass().getResource("fxml/TabFrame.fxml"));
			stage.getScene().setRoot(newScene);
		}
		catch(Exception e)
		{
			System.out.println("Error changing scenes");
		}
		
	}
	@FXML protected void backClick(ActionEvent event)
	{
		//obviously not really what we want to do, just making sure the button is buttoning
		//System.out.println("Login clicked");
		//username.setText("Login Clicked");
		try {
			Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
			Parent newScene = FXMLLoader.load(getClass().getResource("fxml/TabFrame.fxml"));
			stage.getScene().setRoot(newScene);
		}
		catch(Exception e)
		{
			System.out.println("Error changing scenes withn back button");
		}
		
	}	
}
