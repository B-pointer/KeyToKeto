import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class ResultsController {
 @FXML public TextField calories;
 
 @FXML public void newResults(ActionEvent e)
	{
	// BMR = 13.397W + 4.799H - 5.677A + 88.362
			// For women:
			 //BMR = 9.247W + 3.098H - 4.330A + 447.593
		
	   User u = new User();
	   CurrentStatsTabController cn = new CurrentStatsTabController();
	   double newWeight = cn.weight /2.2;
	   u.setAge(cn.age);
	   u.setHeight(cn.height);
	   u.setWeight((int) newWeight);
	   u.setGender(cn.gender);
	   int age = u.getAge();
	   String gndr = u.getGender();
	   double height = u.getHeight();
	   double weight = u.getWeight();
	   double bmr;
	   double w;
	   double h;
	   double a;
	   System.out.println(gndr);
	   if(gndr.equals("Male") || gndr.equals("male"))
	   {
		  w = (13.7 * weight);
		  h = (5 * height);
		  a = (6.8 * age);
		  bmr = 66 + w + h - a;
		  String newBMR = String.valueOf(bmr*1.2);
		  calories.setText(newBMR);
		 
	   }
	   else if(gndr.equals("Female") || gndr.equals("female"))
	   {
		   w = (9.6 * weight);
		   h = (1.8 * height);
		   a = (4.7 * age);
		   bmr = 655 + w + h - a;
		   String newBMR = String.valueOf(bmr);
		   calories.setText(newBMR);
		   String carbs = calories.getText();
		   
	   }
	
	}
}
