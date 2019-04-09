import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class CurrentStatsTabController {
	
	@FXML TextField ageField;
	@FXML TextField heightField;
	@FXML TextField weightField;
	@FXML TextField genderField;
	static int age;
	static int weight;
	static int height;
	static String gender;
	
	@FXML private void saveClick(ActionEvent e)
	{
		User u = new User();
		 age = Integer.parseInt(ageField.getText());
		 weight = Integer.parseInt(weightField.getText());
		 height = Integer.parseInt(heightField.getText());
		 gender = genderField.getText();
		
		
		
		try {
			//Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
			//Parent newScene = FXMLLoader.load(getClass().getResource("LoginPage.fxml"));
		//	stage.getScene().setRoot(newScene);
			
			//rc.calories.setText("1200");
			FXMLLoader loader = new FXMLLoader(getClass().getResource("results.fxml"));
			//loader.setController(this);
			Stage stage = (Stage)((Node)e.getSource()).getScene().getWindow();
			Parent newScene = loader.load();
			stage.getScene().setRoot(newScene);	
			stage.show();
			
		   
		}
		catch(Exception e1)
		{
			System.out.println("Error changing scenes with save button");
		}
		
		//Save current values to the database via API
		System.out.println("Clicked save button on current stats tab");
	}
	
}
