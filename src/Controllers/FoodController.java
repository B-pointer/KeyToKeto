package Controllers;


import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class FoodController {
	@FXML private TextField prots;
	@FXML private TextField carbs;
	@FXML private TextField cals;
	@FXML private TextField fats;
	@FXML private TextField food;
	@FXML private TextField searcher;
	
	@FXML void searchFood(ActionEvent e) throws FileNotFoundException
	{
		System.out.println("Clicked");
		String foodSearch = searcher.getText();
		Scanner in = new Scanner(new FileReader("foodtext"));
		StringBuilder sb = new StringBuilder();
		while(in.hasNext()) {
			if(foodSearch.equals(in.next()))
			{
				food.setText(foodSearch);
				cals.setText(in.next());
				carbs.setText(in.next());
				prots.setText(in.next());
				fats.setText(in.next());	
			}
		    
		}
		in.close();
		
	}
	
}
