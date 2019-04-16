package Controllers;

import java.net.URL;
import java.util.ResourceBundle;

import Calculations.KetoCalculations;
import DataAccess.DataAccessible;
import Models.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

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
		fillFields(true);
	}
 
	@FXML public void newResults(ActionEvent e)
	{
		fillFields(false);
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
	@FXML private void saveClick(ActionEvent e)
	{
		System.out.println("need to save to db here and also need to check to be sure field is not empty");
		user.setCalories(Integer.parseInt(calories.getText()));
	}
	
	@FXML private void resetClick(ActionEvent e)
	{
		fillFields(true);
	}
	//if from save is true, then it uses the calories stored in the User object. if it is false, it will recalculate the calories and the other
	//values based on the User objects current age, height, weight, and gender
	private void fillFields(boolean fromSave)
	{
		int totalCalories;
		if(fromSave)
		{
			totalCalories = user.getCalories();
		}
		else
		{
			totalCalories = KetoCalculations.calculateCalories(user.getAge(), user.getHeight(), user.getWeight(), user.getGender());
		}
		
		calories.setText(Integer.toString(totalCalories));
		carbs.setText(Integer.toString(KetoCalculations.calculateCarbs(totalCalories)));
		fats.setText(Integer.toString(KetoCalculations.calculateFat(totalCalories)));
		proteins.setText(Integer.toString(KetoCalculations.calculateProtein(totalCalories)));
		// home.setCals(calories.toString());
		System.out.println(calories.toString());
		String homeCarbs = String.valueOf((KetoCalculations.calculateCarbs(Integer.parseInt(calories.getText()))));
		home.setCals(homeCarbs);
	}
}
