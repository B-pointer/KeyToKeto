package DataAccess;

import java.util.ArrayList;

import Models.FoodItem;
import Models.User;

public class TemporaryDatabaseSimulator implements DataAccessible{
	
	//not at all the final implementation, just need some quick testing done
	
	
	@Override
	public boolean login(String username, String password) {
		if(username.equals("bkp5") && password.equals("password123"))
		{
			return true;
		}
		return false;
	}

	@Override
	public boolean createUser(String username, String email, String password, int age, String gender, int height,
			int weight) {
		
		return true;
	}

	@Override
	public User getUser(String username) {
		return new User("bkp5", "mailaccount@mail.com", "password123", 22, "Male", 67, 160, false);
	}

	@Override
	public ArrayList<FoodItem> getFoodByDate(String date, String username) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean deleteMeal(int uniqueID) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean addMeal(FoodItem meal) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean updateUser(User user) {
		// TODO Auto-generated method stub
		return false;
	}
	
}
