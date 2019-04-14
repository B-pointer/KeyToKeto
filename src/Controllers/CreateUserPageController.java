package Controllers;

import java.util.function.UnaryOperator;

import DataAccess.DataAccessible;
import Models.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.TextFormatter.Change;
import javafx.stage.Stage;
import javafx.util.Callback;

public class CreateUserPageController {
	
	
	private User user;
	private DataAccessible data;

	
	@FXML private TextField usernameField;
	@FXML private TextField passwordField;
	@FXML private TextField passwordConfirmField;
	@FXML private ComboBox genderBox;
	@FXML private TextField ageField;
	@FXML private TextField heightField;
	@FXML private TextField weightField;
	
	
	public CreateUserPageController(DataAccessible data)
	{
		this.data = data;
	}
	
	@FXML private void initialize()
	{
		ObservableList<String> genders = FXCollections.observableArrayList("Male", "Female");
		genderBox.setItems(genders);
		genderBox.getSelectionModel().selectFirst();
		setFormatter();
	}
	
	@FXML protected void createClick(ActionEvent event)
	{
		System.out.println("Create clicked, create new userObject from data here then save to database. if successful , move on to next page");
		
	}
	
	
	
	private void loadTabs(Stage stage) throws Exception
	{
		//Parent newScene = FXMLLoader.load(getClass().getResource("/fxml/TabFrame.fxml"));
	//	stage.getScene().setRoot(newScene);
		
		
		Callback<Class<?>, Object> controllerFactory = new Callback<Class<?>, Object>() {
		    @Override
		    public Object call(Class<?> type) {
		        if(type == CurrentStatsTabController.class){
		        	return new CurrentStatsTabController(user, data);
		        }
		       // if(type == tab2Controller.class){
		        //	return  new tab2Controller(model);
		       // }
		        return null;
		    }
		};
		
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/TabFrame.fxml"));
		loader.setControllerFactory(controllerFactory);	
		Parent root = loader.load();	
		stage.setScene(new Scene(root, 640, 400));
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
		ageField.setTextFormatter(new TextFormatter<String>(filter));
		heightField.setTextFormatter(new TextFormatter<String>(filter));
		weightField.setTextFormatter(new TextFormatter<String>(filter));
	}
}
