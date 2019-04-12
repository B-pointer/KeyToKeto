package Controllers;

import Models.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class CurrentStatsTabController {
	
	//private User user;
	
	@FXML TextField ageField;
	@FXML TextField heightField;
	@FXML TextField weightField;
	@FXML TextField genderField;
	@FXML ComboBox<String> genderBox;

	//TODO need to finish adding Constructors to all the classes so the same user object is shared
	//public CurrentStatsTabController(User aUser)
	//{
	//	user = aUser;
	//}

	@FXML private void initialize()
	{
		ObservableList<String> genders = FXCollections.observableArrayList("Male", "Female");
		genderBox.setItems(genders);
		genderBox.getSelectionModel().selectFirst();	
	}
		
	@FXML private void saveClick(ActionEvent e)
	{
		 User u = new User();
		 u.setAge(Integer.parseInt(ageField.getText()));
		 u.setWeight(Integer.parseInt(weightField.getText()));
		 u.setHeight(Integer.parseInt(heightField.getText()));
		 u.setGender(genderBox.getValue().toString());
		
		
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/results.fxml"));
			loader.setController(new ResultsController(u));
			Stage stage = (Stage)((Node)e.getSource()).getScene().getWindow();
			Parent newScene = loader.load();
			stage.getScene().setRoot(newScene);	
			stage.show();
		}
		catch(Exception e1)
		{
			System.out.println("Error changing scenes with save button");
			e1.printStackTrace();
		}
		
		//Save current values to the database via API
		System.out.println("Clicked save button on current stats tab");
	}
	
}
