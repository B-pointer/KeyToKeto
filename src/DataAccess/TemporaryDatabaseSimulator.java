package DataAccess;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import Models.FoodItem;
import Models.User;

public class TemporaryDatabaseSimulator implements DataAccessible{
	
	//not at all the final implementation, just need some quick testing done
	private List<FoodItem> foodList; 
	
	public TemporaryDatabaseSimulator()
	{
		generateFoodList();
	}
	
	
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

	public boolean createUser(User user)
	{
		return createUser(user.getName(), user.getEmail(), user.getPass(), user.getAge(), user.getGender(), user.getHeight(), user.getWeight());
	}
	
	
	
	@Override
	public User getUser(String username) {
		//return new User("bkp5", "mailaccount@mail.com", "password123", 22, "Male", 67, 160, 1752,  false);
		return new User("bkp5", "mailaccount@gmail.com", "password123", LocalDate.parse("1996-09-02", DateTimeFormatter.ofPattern("yyyy-MM-dd")), "Male", 67, 160, 1752);
	}

	@Override
	public ArrayList<FoodItem> getFoodByDate(LocalDate date, String username) {

		ArrayList<FoodItem> datedFoodList = new ArrayList<FoodItem>();
		
		SimpleDateFormat fmt = new SimpleDateFormat("yyyyMMdd");
		
		for(FoodItem f : foodList)
		{
			if(f.getDate().isEqual(date))
			{
				datedFoodList.add(f);
			}
		}
		return datedFoodList;	
	}

	@Override
	public boolean deleteMeal(int uniqueID) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int addMeal(FoodItem meal) {
		// TODO Auto-generated method stub
		//needs to insert into db and return the mealID 
		
		foodList.add(meal);
		
		Random r = new Random();
		return r.nextInt(10000);
		//return -1;
	}

	@Override
	public boolean updateUser(User user) {
		// TODO Auto-generated method stub
		return false;
	}
	
	//this is test code for just demonstrating
	private void generateFoodList()
	{
		Random r = new Random();
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		
		
		foodList = new ArrayList<FoodItem>();
		for(int i = 15; i <=29; i++)
		{
			for(int j = 1; j <=10; j++)
			{
				int ID = r.nextInt(1200);
				String name = "Food" + ID;
				int calories = r.nextInt(500);
				int protein = calories / 16; //protein is 1/4 of the calories 
				int carbs = (int)(calories / 3.0 / 4.0); 
				int fat = calories - (protein * 4 + carbs * 4) / 9;
				int servings = r.nextInt(4) + 1;
				int uniqueID = r.nextInt(3000);
				String day = Integer.toString(i);			//String.format("%02d", i);
				LocalDate date = LocalDate.parse("2019-04-" + day, dtf);
				foodList.add(new FoodItem(name, ID, calories, carbs, protein, fat, (double)servings, uniqueID, date));
			}
		}
		
		for(FoodItem food : foodList)
		{
			System.out.println(food.getName() + " " + food.getCalories() + " " +  food.getProtein()+ " " + food.getCarbs() + " " + food.getFat() + " " + food.getMealID() + " " + food.getDate());
		}
	}
	
	
}
