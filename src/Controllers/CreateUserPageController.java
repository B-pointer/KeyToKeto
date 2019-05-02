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
	
	//called at during the JavaFX lifecycle, after all fields with @FXML annotation have been injected. Effectively a constructor after the constructor
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
	
	
	//executes when create account button is clicked
	//checks if fields are valid and none are empty. if valid and filled, then constructs a user object from the values in the fields
	//then checks if username is available
	//finally creates the user and loads the main page of the application, passing the user object into the next set of pages
	@FXML protected void createClick(ActionEvent event)
	{
		//System.out.println("Create clicked, create new userObject from data here then save to database. if successful , move on to next page");
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
						StageLoader.loadTabs(stage, user, data);
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
	
	
	//executes when back button is clicked, reloads the login page
	@FXML private void backClick(ActionEvent event)
	{
		try {	
			Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
			StageLoader.loadLogin(stage, data);	
		}
		catch(Exception e){
			System.out.println("Error changing scenes withn back button");
		}
	}
	
	
	//sets the TextFormatter for the height and weight field. As written only allows integers (character 0-9) to be entered into the
	//aforementioned fields
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
	
	
	//checks if all the user input fields are filled and valid. if filled and valid returns true, else false
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
	
	
	//shows a alert box that informs the user of input errors such as empty fields and mismatched password and confirmation of password
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
	
	//shows an alert that informs the user that the username they have selected is already in use
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
