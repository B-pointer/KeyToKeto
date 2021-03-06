package Controllers;

import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.function.UnaryOperator;

import Calculations.KetoCalculations;
import DataAccess.DataAccessible;
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
 
	public ResultsController(User aUser, DataAccessible aData)
	{
		user = aUser;
		data = aData;
	}
 
	
	@FXML private void initialize()
	{
		setFormatter();
		//fillFields(true);
		fillFields(user.getCalories());
		calories.textProperty().addListener((obs, oldText, newText)-> 
		{
			if(newText.length() > 2){
				fillFields(Integer.parseInt(newText));
			}
		});
		//System.out.println(System.);
		
		
	}
 
	@FXML public void newResults(ActionEvent e)
	{
		int totalCalories = KetoCalculations.calculateCalories(user.getAge(), user.getHeight(), user.getWeight(), user.getGender());
		fillFields(totalCalories);
		/*
		int totalCalories = KetoCalculations.calculateCalories(user.getAge(), user.getHeight(), user.getWeight(), user.getGender());
		calories.setText(Integer.toString(totalCalories));
		carbs.setText(Integer.toString(KetoCalculations.calculateCarbs(totalCalories)));
		fats.setText(Integer.toString(KetoCalculations.calculateFat(totalCalories)));
		proteins.setText(Integer.toString(KetoCalculations.calculateProtein(totalCalories)));
		// home.setCals(calories.toString());
		System.out.println(calories.toString());
		String homeCarbs = String.valueOf((KetoCalculations.calculateCarbs(Integer.parseInt(calories.getText()))));
		home.setCals(homeCarbs);	
		*/
	}
	
	@FXML private void logoutClick(ActionEvent e)
	{
		try {
			Stage primaryStage = (Stage)((Node)e.getSource()).getScene().getWindow();
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/LoginPage.fxml"));
			loader.setController(new LoginPageController(new TemporaryDatabaseSimulator()));
			Parent newScene = loader.load();
			primaryStage.setScene(new Scene(newScene, 900, 560));
			primaryStage.setResizable(false);
			primaryStage.show();
		}
		catch(Exception ex)
		{
			
		}
	}
	
	@FXML private void saveClick(ActionEvent e)
	{
		
		if(checkFields())
		{
			System.out.println("need to save to db here and also need to check to be sure field is not empty");
			user.setCalories(Integer.parseInt(calories.getText()));
		}
	}
	
	@FXML private void resetClick(ActionEvent e)
	{
		//fillFields(true);
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
		System.out.println(calories.toString());
		String homeCarbs = String.valueOf((KetoCalculations.calculateCarbs(Integer.parseInt(calories.getText()))));
		home.setCals(homeCarbs);
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
		calories.setTextFormatter(new TextFormatter<String>(filter));
	}
	
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
