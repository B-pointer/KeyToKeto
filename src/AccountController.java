/** This class is the GUI for New Client info
 * 
 * @author ddath
 */
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
	@FXML private TextField txtEmail;
	@FXML private TextField txtPass;
	@FXML private TextField txtRePass;
	@FXML private TextField txtAge;
	@FXML private TextField txtGender;
	@FXML private TextField txtHeight;
	@FXML private TextField txtWeight;
	@FXML private Button create;
	@FXML private Button back;
	
	@FXML protected void createClick(ActionEvent event)
	{
		showUserInfo();
/**		try {
			Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
			Parent newScene = FXMLLoader.load(getClass().getResource("TabFrame.fxml"));
			stage.getScene().setRoot(newScene);
		}
		catch(Exception e)
		{
			System.out.println("Error changing scenes");
		}
*/		
	}
	@FXML protected void backClick(ActionEvent event)
	{
		//obviously not really what we want to do, just making sure the button is buttoning
		System.out.println(txtName.getText());
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
	
	private void showUserInfo() {
		System.out.println(txtName.getText());
		System.out.println(txtEmail.getText());
		System.out.println(txtPass.getText());
		System.out.println(txtRePass.getText());
		System.out.println(txtAge.getText());
		System.out.println(txtGender.getText());
		System.out.println(txtHeight.getText());
		System.out.println(txtWeight.getText());
		boolean newPass = CheckPassword.CheckPassword(txtPass.getText());		
		if (newPass == false) {
			System.out.println(CheckPassword.getReason());
		}
		
		boolean pWordMatch = CheckPassword.matchPassword(txtPass.getText(), txtRePass.getText());
		if(pWordMatch == false) {
			System.out.println("Passwords do not match.");
		}
		if(newPass == true && pWordMatch == true) {
			System.out.println("Passwords are good!");
		}		
	}
}
