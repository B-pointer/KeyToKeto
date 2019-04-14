package Controllers;

import java.util.function.UnaryOperator;

import DataAccess.DataAccessible;
import Models.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.TextFormatter.Change;
import javafx.stage.Stage;

public class CurrentStatsTabController {
	
	//private User user;
	
	@FXML TextField ageField;
	@FXML TextField heightField;
	@FXML TextField weightField;
	@FXML ComboBox<String> genderBox;

	
	private User user;
	private DataAccessible data;
	
	public CurrentStatsTabController(User user, DataAccessible data)
	{
		this.user = user;
		this.data = data;
	}
	
	
	@FXML private void initialize()
	{
		ObservableList<String> genders = FXCollections.observableArrayList("Male", "Female");
		genderBox.setItems(genders);
		
		ageField.setText(Integer.toString(user.getAge()));
		heightField.setText(Integer.toString(user.getHeight()));
		weightField.setText(Integer.toString(user.getWeight()));
		
		if(user.getGender().equals("Male")){
			genderBox.getSelectionModel().selectFirst();
		}
		else{
			genderBox.getSelectionModel().select(2);
		}
		setFormatter();
	}
		
	@FXML private void saveClick(ActionEvent e)
	{	 
		//System.out.println(checkFields());
		if(checkFields())
		{
			try {
				//this is temporary code, gonna get rid of it as soon as I get the flow of the program switched up and the input validation working as intended
				User u = new User();
				u.setAge(Integer.parseInt(ageField.getText()));
				u.setWeight(Integer.parseInt(weightField.getText()));
				u.setHeight(Integer.parseInt(heightField.getText()));
				u.setGender(genderBox.getValue().toString());
	
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
		}
		
		//Save current values to the database via API
		System.out.println("Clicked save button on current stats tab");
		
	}
	
	//returns true if field have values, false if any are empty
	private boolean checkFields()
	{
		//return !ageField.getText().isEmpty() && !weightField.getText().isEmpty() && !heightField.getText().isEmpty(); 
		if(!ageField.getText().isEmpty() && !weightField.getText().isEmpty() && !heightField.getText().isEmpty())
		{
			return true;
		}
		showErrors();
		return false;
	}
	
	private void showErrors()
	{
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Invalid Entry");
		alert.setContentText("No fields may be left empty");
		alert.setHeaderText(null);
		alert.showAndWait();
	}
		
	private void setFormatter()
	{
		UnaryOperator<Change> filter = change ->{
			String text = change.getText();
			if(text.matches("[0-9]*"))
			{
				return change;
			}
			return null;
		};
		
		//yes, each control actually needs a new textFormatter, you can't use the same one for all of them :/
		ageField.setTextFormatter(new TextFormatter<String>(filter));
		heightField.setTextFormatter(new TextFormatter<String>(filter));
		weightField.setTextFormatter(new TextFormatter<String>(filter));
	}
	
}
