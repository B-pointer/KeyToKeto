import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.MouseEvent;
import javafx.stage.Window;


public class LoginPageController {

	@FXML private TextField username;
	@FXML private TextField password;
	@FXML private Button loginButton;
	
	
	@FXML protected void loginClick(ActionEvent event) {
        Window owner = loginButton.getScene().getWindow();
        if(username.getText().isEmpty() && password.getText().isEmpty()) {
        	Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("No username or password entered");
			alert.showAndWait();
            return;
        }
    }
}






