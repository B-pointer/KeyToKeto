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
import DataAccess.DataConnection;
import DataAccess.TemporaryDatabaseSimulator;
import Models.FoodItem;
import Models.User;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.DoubleBinding;
import javafx.beans.binding.NumberBinding;
import javafx.beans.binding.StringBinding;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Control;
import javafx.scene.control.TextFormatter.Change;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.converter.NumberStringConverter;

public class FoodController {
	
	//these are all for the search box on the right and the food entry fields underneath it
	@FXML private TextField prots;
	@FXML private TextField carbs;
	@FXML private TextField cals;
	@FXML private TextField fats;
	@FXML private TextField food;
	@FXML private TextField searcher;
	@FXML private Spinner<Double> servingSpinner;
	
	//for the search table on the right
	@FXML private TableView<FoodItem> SearchTable;
	@FXML private TableColumn searchTitle;
	@FXML private TableColumn<FoodItem, String> searchCalorie;
	@FXML private TableColumn<FoodItem, String> searchProtein;
	@FXML private TableColumn<FoodItem, String> searchFat;
	@FXML private TableColumn<FoodItem, String> searchCarbs;

	//these are for the Diary box and related fields on the left
	@FXML private DatePicker datePicker;
	@FXML private TableView<FoodItem> MealTable;
	@FXML private TableColumn titleColumn;
	@FXML private TableColumn<FoodItem, String> calorieColumn;
	@FXML private TableColumn<FoodItem, String> proteinColumn;
	@FXML private TableColumn<FoodItem, String> fatColumn;
	@FXML private TableColumn<FoodItem, String> carbsColumn;
	@FXML private TableColumn servingColumn;
	
	//labels for the goal row of the summation grid
	@FXML private Label calorieGoalLabel;
	@FXML private Label proteinGoalLabel;
	@FXML private Label carbGoalLabel;
	@FXML private Label fatGoalLabel;
	//labels for the actual total row of the summation grid
	@FXML private Label calorieActualLabel;
	@FXML private Label proteinActualLabel;
	@FXML private Label carbActualLabel;
	@FXML private Label fatActualLabel;
	//labels for the remaining row of the summation grid
	@FXML private Label calorieRemainingLabel;
	@FXML private Label proteinRemainingLabel;
	@FXML private Label carbRemainingLabel;
	@FXML private Label fatRemainingLabel;
	
	//bindings for the labels listed above
	private IntegerProperty calorieSum = new SimpleIntegerProperty();
	private IntegerProperty proteinSum = new SimpleIntegerProperty();
	private IntegerProperty carbSum = new SimpleIntegerProperty();
	private IntegerProperty fatSum = new SimpleIntegerProperty();
	
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
		
		setupTable();
		setFormatter();
		getFoodList();
	}
	
	
	private void getFoodList()
	{
		
		ObservableList<FoodItem> food = FXCollections.observableArrayList(data.getFoodByDate(date, user.getName()));
		MealTable.setItems(food);
		for(FoodItem a :  food)
		{
			System.out.println(a.getName() + " " + a.getCalories() + " " + a.getFat() );
		}
		sumFields();
		
	}
	
	private void setupTable()
	{

		
		MealTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		
		titleColumn.setCellValueFactory(new PropertyValueFactory<FoodItem, String>("name"));
	
		calorieColumn.setCellValueFactory(c-> new SimpleStringProperty(Integer.toString((int)((c.getValue().getCalories()) * c.getValue().getServings()))));
		proteinColumn.setCellValueFactory(c-> new SimpleStringProperty(Integer.toString((int)((c.getValue().getProtein()) * c.getValue().getServings()))));
		fatColumn.setCellValueFactory(c-> new SimpleStringProperty(Integer.toString((int)((c.getValue().getFat()) * c.getValue().getServings()))));
		carbsColumn.setCellValueFactory(c-> new SimpleStringProperty(Integer.toString((int)((c.getValue().getCarbs()) * c.getValue().getServings()))));
		servingColumn.setCellValueFactory(new PropertyValueFactory<FoodItem, Double>("servings"));
		
		
		
		SearchTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		
		searchTitle.setCellValueFactory(new PropertyValueFactory<FoodItem, String>("name"));
		searchCalorie.setCellValueFactory(new PropertyValueFactory<FoodItem, String>("calories"));
		searchProtein.setCellValueFactory(new PropertyValueFactory<FoodItem, String>("protein"));
		searchFat.setCellValueFactory(new PropertyValueFactory<FoodItem, String>("fat"));
		searchCarbs.setCellValueFactory(new PropertyValueFactory<FoodItem, String>("calories"));	
		
		
		SearchTable.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<FoodItem>(){

					@Override
					public void changed(ObservableValue<? extends FoodItem> observable, FoodItem oldVal, FoodItem newVal) {
						food.setText(newVal.getName());
						cals.setText(Integer.toString(newVal.getCalories()));
						prots.setText(Integer.toString(newVal.getProtein()));
						carbs.setText(Integer.toString(newVal.getCarbs()));
						fats.setText(Integer.toString(newVal.getFat()));
					}
		});
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
			FoodItem f = new FoodItem(name, ID, foodcalories, foodcarbs, foodprotein, foodfat, servings );
			f.setDate(foodDate);
			
			//actually calling data access layer, prolly need some error checking here
			int mealID = data.addMeal(f, user.getName());
			f.setMealID(mealID);
					
			MealTable.getItems().add(f);
			
			clearFields();
			sumFields();
		}
	}
	
	
	
	@FXML private void deleteClick(ActionEvent e)
	{
		if(!MealTable.getSelectionModel().isEmpty())
		{
			FoodItem food = MealTable.getSelectionModel().getSelectedItem();
			data.deleteMeal(food.getMealID());
			MealTable.getItems().remove(food);
			MealTable.getSelectionModel().clearSelection();
			sumFields();
		}
	}
	
	
	
	
	@FXML private void dateChange(ActionEvent e)
	{
		date = datePicker.getValue();
		System.out.println(datePicker.getValue());
		getFoodList();
	}

	
	
	@FXML void searchFood(ActionEvent e)
	{

		if(!searcher.getText().isEmpty())
		{
			System.out.println("search clicked");
			String foodSearch = searcher.getText();
			ObservableList<FoodItem> searchList = FXCollections.observableArrayList(data.searchForFood(foodSearch));
			SearchTable.setItems(searchList);
		}
	}
	
	
	@FXML private void logoutClick(ActionEvent event)
	{
		try {	
			Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
			StageLoader.loadLogin(stage, data);	
		}
		catch(Exception e){
			System.out.println("Error changing scenes withn back button");
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
		
		
		calorieRemainingLabel.textProperty().bind(user.CalorieProperty().subtract(calorieSum).asString());
		proteinRemainingLabel.textProperty().bind(user.ProteinProperty().subtract(proteinSum).asString());
		carbRemainingLabel.textProperty().bind(user.CarbsProperty().subtract(carbSum).asString());
		fatRemainingLabel.textProperty().bind(user.FatProperty().subtract(fatSum).asString());

		
	}
	
	
	private void sumFields()
	{
		
		int calSum = 0;
		int protSum = 0;
		int carSum = 0;
		int fSum = 0;
		for(FoodItem a :MealTable.getItems())
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
}
