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
	HomePageController home;

	public ResultsController()
	{
	 
	}
 
	public ResultsController(User aUser, DataAccessible aData)
	{
		user = aUser;
		data = aData;
	}
 
 
	@FXML public void newResults(ActionEvent e)
	{
		calories.setText(Integer.toString(KetoCalculations.calculateCalories(user.getAge(), user.getHeight(), user.getWeight(), user.getGender())));
		carbs.setText(Integer.toString(KetoCalculations.calculateCarbs(Integer.parseInt(calories.getText()))));
		fats.setText(Integer.toString(KetoCalculations.calculateFat(Integer.parseInt(calories.getText()))));
		proteins.setText(Integer.toString(KetoCalculations.calculateProtein(Integer.parseInt(calories.getText()))));
	}
}
