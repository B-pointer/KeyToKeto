
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class ResultsController {
	@FXML public TextField calories;
	private User user;
 
 
	public ResultsController()
	{
	 
	}
 
	public ResultsController(User aUser)
	{
		user = aUser;
	}
 
 
	@FXML public void newResults(ActionEvent e)
	{
		double BMR;
		
		if(user.getGender().equalsIgnoreCase("MALE"))
		{
			BMR = (13.397 * user.getWeight() * 0.453) + (4.799 * user.getHeight() * 2.54) - (5.677 * user.getAge()) + 88.362;
		}
		else
		{
			BMR = (9.247 * user.getWeight() * 0.453) + (3.098 * user.getHeight() * 2.54) - (4.330 * user.getAge()) + 447.593;
		}
		calories.setText(Integer.toString((int)BMR));
	}
}
