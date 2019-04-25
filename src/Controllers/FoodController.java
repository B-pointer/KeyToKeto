package Controllers;


import java.io.FileNotFoundException;
import java.io.FileReader;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;
import java.util.function.UnaryOperator;

import Calculations.KetoCalculations;
import DataAccess.DataAccessible;
import DataAccess.TemporaryDatabaseSimulator;
import Models.FoodItem;
import Models.User;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.DoubleBinding;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Control;
import javafx.scene.control.TextFormatter.Change;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.converter.NumberStringConverter;

public class FoodController {
	@FXML private TextField prots;
	@FXML private TextField carbs;
	@FXML private TextField cals;
	@FXML private TextField fats;
	@FXML private TextField food;
	@FXML private TextField searcher;
	@FXML private ListView<FoodItem> FoodListView;
	@FXML private DatePicker datePicker;
	@FXML private Spinner<Double> servingSpinner;
	
	
	@FXML private Label calorieGoalLabel;
	@FXML private Label proteinGoalLabel;
	@FXML private Label carbGoalLabel;
	@FXML private Label fatGoalLabel;
	
	@FXML private Label calorieActualLabel;
	@FXML private Label proteinActualLabel;
	@FXML private Label carbActualLabel;
	@FXML private Label fatActualLabel;
	
	
	private IntegerProperty calorieSum = new SimpleIntegerProperty();
	private IntegerProperty proteinSum = new SimpleIntegerProperty();;
	private IntegerProperty carbSum = new SimpleIntegerProperty();;
	private IntegerProperty fatSum = new SimpleIntegerProperty();;
	
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
		SpinnerValueFactory<Double> valueFactory = new SpinnerValueFactory.DoubleSpinnerValueFactory(0.0, 10.0, 1.0, 0.1);
		servingSpinner.setValueFactory(valueFactory);
		
		getBindings();
		date = LocalDate.now();
		datePicker.setValue(LocalDate.now());
		
		getFoodList();
	}
	
	
	private void getFoodList()
	{
		FoodListView.setItems(FXCollections.observableArrayList(data.getFoodByDate(date, user.getName())));
		FoodListView.setCellFactory(new Callback<ListView<FoodItem>, ListCell<FoodItem>>() {
            @Override
            public ListCell<FoodItem> call(ListView<FoodItem> FoodListView) {
                return new CustomFoodCell();
            }
        });
		
		sumFields();
	}
	
	
	@FXML private void saveClick(ActionEvent e)
	{
		if(checkFields())
		{
			//creating a food item from the fields
			String name = food.getText();
			int ID = -10000; //testing obviously
			int foodcalories = Integer.parseInt(cals.getText());
			int foodcarbs = Integer.parseInt(carbs.getText());
			int foodfat = Integer.parseInt(fats.getText());
			int foodprotein = Integer.parseInt(prots.getText());
			LocalDate foodDate = datePicker.getValue();
			double servings = servingSpinner.getValue();
			//String name, int ID, int calories, int carbs, int protein, int fat, double servings
			FoodItem f = new FoodItem(name, ID, foodcalories, foodcarbs, foodprotein, foodfat, servings );
			f.setDate(foodDate);
			
			//actually calling data access layer
			int mealID = data.addMeal(f);
			f.setMealID(mealID);
			
			//adding to diary listview
			FoodListView.getItems().add(f);
			//getFoodList();
			clearFields();
			sumFields();
		}
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
			primaryStage.setScene(new Scene(newScene, 900, 560));
			primaryStage.setResizable(false);
			primaryStage.show();
		}
		catch(Exception ex)
		{
			
		}
	}
	
	//makes it so that only numbers can be added to the macr nutrient and calorie fields. may not be used depending on API
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
		prots.setTextFormatter(new TextFormatter<String>(filter));
		carbs.setTextFormatter(new TextFormatter<String>(filter));
		fats.setTextFormatter(new TextFormatter<String>(filter));
		cals.setTextFormatter(new TextFormatter<String>(filter));
	}
	
	private boolean checkFields()
	{
		if(cals.getText().isEmpty() || carbs.getText().isEmpty()  || prots.getText().isEmpty() || fats.getText().isEmpty() || food.getText().isEmpty())
		{
			showErrors();
			return false;
		}
		return true;
	}
	

	private void showErrors()
	{
		String message = "No fields may be left empty\n";
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Invalid Entry");
		alert.setContentText(message);
		alert.setHeaderText(null);
		alert.showAndWait();
	}
	
	
	
	private void clearFields()
	{
		food.clear();
		cals.clear();
		carbs.clear();
		fats.clear();
		prots.clear();
		servingSpinner.getValueFactory().setValue(1.0);
	}
	
	
	private void getBindings()
	{
		IntegerProperty in = user.CalorieProperty();
		calorieGoalLabel.textProperty().bind(user.CalorieProperty().asString());
		proteinGoalLabel.textProperty().bind(user.ProteinProperty().asString());
		fatGoalLabel.textProperty().bind(user.FatProperty().asString());
		carbGoalLabel.textProperty().bind(user.CarbsProperty().asString());
		
		calorieActualLabel.textProperty().bind(calorieSum.asString());
		proteinActualLabel.textProperty().bind(proteinSum.asString());
		carbActualLabel.textProperty().bind(carbSum.asString());
		fatActualLabel.textProperty().bind(fatSum.asString());
	}
	
	
	private void sumFields()
	{
		int calSum = 0;
		int protSum = 0;
		int carSum = 0;
		int fSum = 0;
		for(FoodItem a :FoodListView.getItems())
		{
			calSum += (int)(a.getServings()* a.getCalories());
			protSum += (int)(a.getServings()* a.getProtein());
			carSum += (int)(a.getServings()* a.getCarbs());
			fSum += (int)(a.getServings()* a.getFat());
		}
		
		calorieSum.set(calSum);
		proteinSum.set(protSum);
		carbSum.set(carSum);
		fatSum.set(fSum);
	}
	
	
	private class CustomFoodCell extends ListCell<FoodItem>
	{
		private VBox content;
		//private HBox macros; 
		
		
		private Text macroText;
		
		
		private Text name;
		//private Text protein;
		//private Text carbs;
		//private Text calories;
		//private Text fat;
		//private Text servings;
		
		public CustomFoodCell()
		{
			
			super();
			
			//prefWidthProperty().bind(FoodListView.widthProperty().subtract(2));
            //setMaxWidth(Control.USE_PREF_SIZE);
			setPrefWidth(0);
            name = new Text();
            macroText = new Text();
            content = new VBox(name, macroText);
		}
		
		@Override
		protected void updateItem(FoodItem item, boolean empty)
		{
			super.updateItem(item,  empty);
			if(item != null && !empty)
			{
				double totalServings = item.getServings();
				name.setText(item.getName());
				String cal = String.format("%-20s", "Cal: " + (int)(totalServings * item.getCalories()));
				String pro = String.format("%-20s","Prot: " + (int)(totalServings * item.getProtein()));
				String carbs = String.format("%-20s","Carbs: " + (int)(totalServings * item.getCarbs()));
				String fat = String.format("%-20s","Fat: " + (int)(totalServings * item.getFat()));
				
				String out = cal + pro + carbs + fat;
				
				System.out.println(out);
				macroText.setText(out);
				setGraphic(content);
			}
			else
			{
				setGraphic(null);
			}
		}
	}
	
}
