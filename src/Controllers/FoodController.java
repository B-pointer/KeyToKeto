package Controllers;


import java.io.FileNotFoundException;
import java.io.FileReader;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;

import DataAccess.DataAccessible;
import DataAccess.TemporaryDatabaseSimulator;
import Models.FoodItem;
import Models.User;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Callback;

public class FoodController {
	@FXML private TextField prots;
	@FXML private TextField carbs;
	@FXML private TextField cals;
	@FXML private TextField fats;
	@FXML private TextField food;
	@FXML private TextField searcher;
	@FXML private ListView<FoodItem> FoodListView;
	@FXML private DatePicker datePicker;
	
	private LocalDate date;
	
	User user;
	DataAccessible data;
	
	
	public FoodController(User user, DataAccessible data)
	{
		this.user = user;
		this.data = data;
	}
	
	
	@FXML private void initialize()
	{

		date = LocalDate.now();
		datePicker.setValue(LocalDate.now());
		getFoodList();
	}
	
	
	private void getFoodList()
	{
		FoodListView.setItems(FXCollections.observableArrayList(data.getFoodByDate(date.toString(), user.getName())));
		//FoodListView.setCellFactory(Callback<ListView<FoodItem, ListCell<FoodItem>>() {
		//			@Override public ListCell
		//		});
		FoodListView.setCellFactory(new Callback<ListView<FoodItem>, ListCell<FoodItem>>() {
            @Override
            public ListCell<FoodItem> call(ListView<FoodItem> FoodListView) {
                return new CustomFoodCell();
            }
        });
	}
	
	
	@FXML private void saveClick(ActionEvent e)
	{
		
	}
	
	@FXML private void dateChange(ActionEvent e)
	{
		date = datePicker.getValue();
		System.out.println(datePicker.getValue());
		getFoodList();
	}

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
	
	@FXML private void logoutClick(ActionEvent e)
	{
		try {
			Stage primaryStage = (Stage)((Node)e.getSource()).getScene().getWindow();
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/LoginPage.fxml"));
			loader.setController(new LoginPageController(new TemporaryDatabaseSimulator()));
			Parent newScene = loader.load();
			primaryStage.setScene(new Scene(newScene, 640, 400));
			primaryStage.setResizable(false);
			primaryStage.show();
		}
		catch(Exception ex)
		{
			
		}
	}
	
	
	private class CustomFoodCell extends ListCell<FoodItem>
	{
		private VBox content;
		private HBox macros; 
		
		private Text name;
		private Text protein;
		private Text carbs;
		private Text calories;
		private Text fat;
		
		public CustomFoodCell()
		{
			super();
			name = new Text();
			protein = new Text();
			carbs = new Text();
			calories = new Text();
			fat = new Text();
			macros = new HBox(calories, protein, carbs, fat);
			macros.setSpacing(10);
			content = new VBox(name, macros);
		}
		
		@Override
		protected void updateItem(FoodItem item, boolean empty)
		{
			super.updateItem(item,  empty);
			if(item != null && !empty)
			{
				name.setText(item.getName());
				calories.setText("Cal: " + item.getCalories());
				protein.setText("Protein: " + item.getProtein());
				carbs.setText("Carbs: " + item.getCarbs());
				fat.setText("Fat: " + item.getFat());
				setGraphic(content);
			}
			else
			{
				setGraphic(null);
			}
		}
	}
	
}
