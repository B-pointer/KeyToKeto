package Controllers;

import DataAccess.DataAccessible;
import Models.User;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.util.Callback;

public class StageLoader {
	public static void loadTabs(Stage stage, User user, DataAccessible data) throws Exception
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
		
		FXMLLoader loader = new FXMLLoader(StageLoader.class.getResource("/fxml/TabFrame.fxml"));
		loader.setControllerFactory(controllerFactory);	
		Parent root = loader.load();	
		stage.setScene(new Scene(root, stage.getWidth(), stage.getHeight()));
		stage.show();	
	}
	
	public static void loadLogin(Stage stage, DataAccessible data) throws Exception
	{
		FXMLLoader loader = new FXMLLoader(StageLoader.class.getResource("/fxml/LoginPage.fxml"));
		loader.setController(new LoginPageController(data));
		Parent newScene = loader.load();
		
		stage.setScene(new Scene(newScene, stage.getWidth(), stage.getHeight()));
	}
}
