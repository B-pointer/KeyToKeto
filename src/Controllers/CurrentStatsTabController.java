package Controllers;
//Author: brandon pointer
import java.util.function.UnaryOperator;

import DataAccess.DataAccessible;
import DataAccess.DataConnection;
import DataAccess.TemporaryDatabaseSimulator;
import Models.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Tab;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.TextFormatter.Change;
import javafx.stage.Stage;

public class CurrentStatsTabController {

	@FXML TextField ageField;
	@FXML TextField heightField;
	@FXML TextField weightField;
	@FXML ComboBox<String> genderBox;

	
	private User user;
	private DataAccessible data;
	//constructor, with fields for the data access later and a user object that is created during login or account creation
	public CurrentStatsTabController(User user, DataAccessible data)
	{
		this.user = user;
		this.data = data;
	}
	
	//called during the JavaFX lifecycle, after the @FXML annotated fields are injected
	//basically a constructor, but relies on fields that are created after the constructor is called
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
	//executes when the save button is clicked, sets the user object's fields to match those in the fields displayed on the screen, then saves that to the 
	//data access layer
	//only executes fully if the fields are properly filled and valid
	@FXML private void saveClick(ActionEvent e)
	{	 
		if(checkFields())
		{
			user.setAge(Integer.parseInt(ageField.getText()));
			user.setWeight(Integer.parseInt(weightField.getText()));
			user.setHeight(Integer.parseInt(heightField.getText()));
			user.setGender(genderBox.getValue().toString());
			data.updateUser(user);
		}		
	}
	
	//returns true if field have values, false if any are empty
	private boolean checkFields()
	{
		if(!ageField.getText().isEmpty() && !weightField.getText().isEmpty() && !heightField.getText().isEmpty())
		{
			return true;
		}
		showErrors();
		return false;
	}
	//shows a dialog box if a field is empty
	private void showErrors()
	{
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Invalid Entry");
		alert.setContentText("No fields may be left empty");
		alert.setHeaderText(null);
		alert.showAndWait();
	}
	//this sets the textFields up with a textFormatter that disallows non integers	
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
	
	//executes when logout is clicked, reloads the login page
	@FXML private void logoutClick(ActionEvent event)
	{
		try {	
			Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
			StageLoader.loadLogin(stage, data);	
		}
		catch(Exception e){
			System.out.println("Error changing scenes withn back button");
		}
	}
	
}
