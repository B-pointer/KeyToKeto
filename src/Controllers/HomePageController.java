package Controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class HomePageController {
	@FXML public Label cals;
    public HomePageController(String newCals)
    {
    	String val1 = newCals;
    }
	void setCals(String calss)
    {
    	//System.out.println("Worked");
    }
    
	public void myValString (String val) {
		System.out.println("String val: " + val);
	}
		
	
	//@FXML public TextField calories;
	

}
