import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class ResultsController {
 @FXML public TextField calories;
 
 @FXML public void calcClick(ActionEvent e)
	{
		calories.setText("1200");
	}
}
