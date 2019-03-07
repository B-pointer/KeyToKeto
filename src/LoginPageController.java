import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class LoginPageController {
	
	@FXML private TextField username;
	@FXML private TextField password;
	@FXML private Button loginButton;
	
	@FXML protected void loginClick(ActionEvent event)
	{
		//obviously not really what we want to do, just making sure the button is buttoning
		System.out.println("Login clicked");
		username.setText("Login Clicked");
	}
}
