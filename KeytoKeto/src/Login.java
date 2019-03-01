
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage; 
import javafx.scene.control.PasswordField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
public class Login extends Application {
	
	public static void main(String[] args) {
		launch(args);
		}
	@Override
	public void start(Stage primaryStage) {
		GridPane grid = new GridPane();
		grid.setAlignment(Pos.CENTER);
		grid.setHgap(20);
		grid.setVgap(20);
		grid.setPadding(new Insets(25, 25, 25, 25));
		//Scene title
		
		Text scenetitle = new Text("Key to Keto!");
		scenetitle.setFont(Font.font("Serif", FontWeight.BOLD, 20));
		grid.add(scenetitle, 0, 0, 2, 1);
		//Label for username
		
		Label userName = new Label("User Name:");
		grid.add(userName, 0, 1);
        //Text field for user name
		
		TextField userTextField = new TextField();
		grid.add(userTextField, 1, 1);
        //label for password
		
		Label pw = new Label("Password:");
		grid.add(pw, 0, 2);
        //Password box
		
		PasswordField pwBox = new PasswordField();
		grid.add(pwBox, 1, 2);
		//buttons
		
		Button btn = new Button("Sign in");
		Button btn2 = new Button("Create Account");
		HBox hbBtn = new HBox(10);
		hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
		hbBtn.getChildren().addAll(btn, btn2);
		grid.add(hbBtn, 1, 4);
		
		//Scene 2 for log in
		/////////////////////////////////////////////////////////
		String usernme = userTextField.getText();
		Text scenetitle2 = new Text("Welcome to Key to Keto! " + usernme);
		scenetitle2.setFont(Font.font("Serif", FontWeight.BOLD, 20));

		Button button2= new Button("Ketosis Calculation");
		Button button3 = new Button("View Current information");
		Button button4 = new Button("Update information");
		Button button5 = new Button("Back");

		VBox layout2= new VBox(50);
		layout2.setAlignment(Pos.CENTER);
		layout2.getChildren().addAll(scenetitle2, button2, button3, button4, button5);
		Scene scene2= new Scene(layout2,700,500);
		btn.setOnAction(e -> primaryStage.setScene(scene2));
		/////////////////////////////////////////////////////////	
		
		Scene scene = new Scene(grid, 400, 375);
	    primaryStage.setTitle("Login");
	    primaryStage.setScene(scene);
	    primaryStage.show();
	    button5.setOnAction(e -> primaryStage.setScene(scene));
	}
}



