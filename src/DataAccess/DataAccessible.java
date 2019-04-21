package DataAccess;

import java.util.ArrayList;

import Models.FoodItem;
import Models.User;

public interface DataAccessible {
	
	public boolean login(String username, String password);
	
	public boolean createUser(String username, String email, String password, int age, String gender, int height, int weight);
	
	public boolean createUser(User user);
	
	public User getUser(String username);
	
	public ArrayList<FoodItem> getFoodByDate(String date, String username);
	
	public boolean deleteMeal(int uniqueID); //unique ID here refers to the uniquely generated ID for the food in the meal table, not the food ID
											 //this is to be used to delete food that a user has added to their meals
	public int addMeal(FoodItem meal);
	
	public boolean updateUser(User user);
}
