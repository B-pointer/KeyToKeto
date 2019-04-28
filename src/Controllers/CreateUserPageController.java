package Controllers;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.function.UnaryOperator;

import Calculations.KetoCalculations;
import DataAccess.DataAccessible;
import Models.User;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TextFormatter.Change;
import javafx.stage.Stage;
import javafx.util.Callback;

public class CreateUserPageController {
	
	
	private User user;
	private DataAccessible data;
	private HomePageController home;
	@FXML private TextField usernameField;
	@FXML private TextField passwordField;
	@FXML private TextField emailField;
	@FXML private TextField passwordConfirmField;
	@FXML private ComboBox<String> genderBox;
	@FXML private DatePicker birthDatePicker;
	@FXML private TextField heightField;
	@FXML private TextField weightField;
	@FXML private Button backButton;
	
	public CreateUserPageController(DataAccessible data)
	{
		this.data = data;
	}
	
	@FXML private void initialize()
	{
		birthDatePicker.setValue(LocalDate.now());
		birthDatePicker.setEditable(false);
		ObservableList<String> genders = FXCollections.observableArrayList("Male", "Female");
		genderBox.setItems(genders);
		genderBox.getSelectionModel().selectFirst();
		setFormatter();
		
		Platform.runLater(()-> {
			backButton.requestFocus();
		});
	}
	
	
	
	@FXML protected void createClick(ActionEvent event)
	{
		System.out.println("Create clicked, create new userObject from data here then save to database. if successful , move on to next page");
		if(checkFields())
		{
			int height = Integer.parseInt(heightField.getText());
			int weight = Integer.parseInt(weightField.getText());
			int age = (int)ChronoUnit.YEARS.between(birthDatePicker.getValue(), LocalDate.now()); 
			String gender = genderBox.getValue().toString();
			int calories = KetoCalculations.calculateCalories(age, height, weight, gender);
			
			user = new User(usernameField.getText(), emailField.getText(), passwordField.getText(), birthDatePicker.getValue(), gender, height, weight, calories);

			if(data.isUsernameAvailable(user.getName()))
			{
				if( data.createUser(user))
				{
					try {
						Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
						loadTabs(stage);
					}
					catch(Exception e)
					{
						System.out.println("Error switching scenes");
						e.printStackTrace();
					}
				}
				else
				{
					System.out.println("Error creating account. handle this better elsewhere");
				}
			}
			else
			{
				showExistingUserName();
			}
		}
	}
	
	
	//Ideally this would be in a static class that is called here and in the login page controller, but time is short and I know this works
	private void loadTabs(Stage stage) throws Exception
	{
		Callback<Class<?>, Object> controllerFactory = new Callback<Class<?>, Object>() {
		    @Override
		    public Object call(Class<?> type) {
		        if(type == CurrentStatsTabController.class){
		        	return new CurrentStatsTabController(user, data);
		        }
		       if(type == ResultsController.class){
		        	return  new ResultsController(user, data);
		        }
		       if(type == FoodController.class)
		       {
		    	   return new FoodController(user, data);
		       }
		        return null;
		    }
		};
		
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/TabFrame.fxml"));
		loader.setControllerFactory(controllerFactory);	
		Parent root = loader.load();	
		stage.setScene(new Scene(root, 900, 560));
		stage.show();		
	}
	

	
	
	@FXML private void backClick(ActionEvent event)
	{
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/LoginPage.fxml"));
			loader.setController(new LoginPageController(data));
			Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
			Parent newScene = loader.load();
			stage.getScene().setRoot(newScene);		
		}
		catch(Exception e)
		{
			System.out.println("Error changing scenes withn back button");
		}
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
		heightField.setTextFormatter(new TextFormatter<String>(filter));
		weightField.setTextFormatter(new TextFormatter<String>(filter));
	}
	
	
	
	private boolean checkFields()
	{
		boolean emptyFields = usernameField.getText().isEmpty() || emailField.getText().isEmpty() || passwordField.getText().isEmpty() || passwordConfirmField.getText().isEmpty() 
				|| heightField.getText().isEmpty() || weightField.getText().isEmpty();
		boolean mismatchedPasswords = !(passwordField.getText().equals(passwordConfirmField.getText()));
		
		if(emptyFields || mismatchedPasswords)
		{
			showErrors(emptyFields, mismatchedPasswords);
			return false;
		}
		
		return true;
	}
	
	
	
	private void showErrors(boolean emptyFields, boolean mismatchedPasswords)
	{
		String message = "";
		if(emptyFields)
			message += "No fields may be left empty\n";
		if(mismatchedPasswords)
			message += "The entered passwords do not match";
		
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Invalid Entry");
		alert.setContentText(message);
		alert.setHeaderText(null);
		alert.showAndWait();
	}
	
	private void showExistingUserName()
	{
		String message = "A user with that name already exists";
		
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Invalid Entry");
		alert.setContentText(message);
		alert.setHeaderText(null);
		alert.showAndWait();
	}
}
