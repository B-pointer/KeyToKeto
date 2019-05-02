package Controllers;

import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.function.UnaryOperator;

import Calculations.KetoCalculations;
import DataAccess.DataAccessible;
import DataAccess.DataConnection;
import DataAccess.TemporaryDatabaseSimulator;
import Models.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextFormatter.Change;
import javafx.stage.Stage;

public class ResultsController {
	@FXML public TextField calories;
	@FXML public TextField carbs;
	@FXML public TextField fats;
	@FXML public TextField proteins;
	private User user;
	private DataAccessible data;
	HomePageController home = new HomePageController(null);
	
	public ResultsController()
	{
	 
	}
	//constructor with fields for user and data access layer
	public ResultsController(User aUser, DataAccessible aData)
	{
		user = aUser;
		data = aData;
	}
 
	//called after the @FXML fields are injected
	@FXML private void initialize()
	{
		setFormatter();
		fillFields(user.getCalories());
		calories.textProperty().addListener((obs, oldText, newText)-> 
		{
			if(newText.length() > 2){
				fillFields(Integer.parseInt(newText));
			}
		});	
	}
	//fires when the Recalculate button is clicked. recalculates the user's calorie goal based on current weight height and age and gender, 
	//but does not save it to the user object
	@FXML public void newResults(ActionEvent e)
	{
		int totalCalories = KetoCalculations.calculateCalories(user.getAge(), user.getHeight(), user.getWeight(), user.getGender());
		fillFields(totalCalories);
	}
	//reloads the login page
	@FXML private void logoutClick(ActionEvent event)
	{
		try {	
			Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
			StageLoader.loadLogin(stage, data);	
		}
		catch(Exception e){
			System.out.println("Error changing scenes with back button");
		}
	}
	//saves the current calculations (the current values of the fields) to the user object and the database 
	@FXML private void saveClick(ActionEvent e)
	{
		
		if(checkFields())
		{
			user.setCalories(Integer.parseInt(calories.getText()));
			data.updateUser(user);
		}
	}
	//resets to the current values stored in the user object
	@FXML private void resetClick(ActionEvent e)
	{
		fillFields(user.getCalories());
	}
	//if from save is true, then it uses the calories stored in the User object. if it is false, it will recalculate the calories and the other
	//values based on the User objects current age, height, weight, and gender
	private void fillFields(int totalCalories)
	{
		calories.setText(Integer.toString(totalCalories));
		carbs.setText(Integer.toString(KetoCalculations.calculateCarbs(totalCalories)));
		fats.setText(Integer.toString(KetoCalculations.calculateFat(totalCalories)));
		proteins.setText(Integer.toString(KetoCalculations.calculateProtein(totalCalories)));
		String homeCarbs = String.valueOf((KetoCalculations.calculateCarbs(Integer.parseInt(calories.getText()))));
		home.setCals(homeCarbs);
	}
	//prevents non integers from being entered in the calorie field
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
		calories.setTextFormatter(new TextFormatter<String>(filter));
	}
	//checks if any fields are empty and displays an alert box if so
	private boolean checkFields()
	{
		if(calories.getText().isEmpty())
		{
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Invalid Entry");
			alert.setContentText("Calories must not be left empty");
			alert.setHeaderText(null);
			alert.showAndWait();
			return false;
		}
		return true;
	}
}
