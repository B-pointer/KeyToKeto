import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class CurrentStatsTabController {
	
	@FXML TextField ageField;
	@FXML TextField heightField;
	@FXML TextField weightField;
	@FXML TextField genderField;
	
	@FXML private void saveClick(ActionEvent e)
	{
		//Save current values to the database via API
		System.out.println("Clicked save button on current stats tab");
	}
}
