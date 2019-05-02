package DataAccess;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

import Models.FoodItem;
import Models.User;

public class APIEndpoint implements DataAccessible{
	public static String APIURL = "https://capstone.andrewmerts.com";
	//not at all the final implementation, just need some quick testing done
	private List<FoodItem> foodList; 
	
	public APIEndpoint()
	{
		//generateFoodList();
		foodList = new ArrayList<FoodItem>();
	}
	
	
	@Override
	public boolean login(String username, String password) {
		try {
			
			HttpResponse<JsonNode> response = Unirest.post(APIURL + "/login")
					.header("Content-Type", "application/x-www-form-urlencoded")
					.header("cache-control", "no-cache")
					.header("Postman-Token", "171b0435-9d3f-40c9-a573-ffcc66f343cd")
					.body("username=" + username + "&password=" + password)//TODO properly escape these
					.asJson();
			JSONObject body = response.getBody().getObject();
			if (response.getStatus() == 200 && body.getBoolean("success")) {
				Unirest.setDefaultHeader("Authorization", "Bearer " + body.getString("token"));
				return true;
			} else {
				System.out.println(body.getString("message"));//TODO show this in a popup message
			}
		} catch (UnirestException | JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return false;
	}
	
	public void logout() {
		Unirest.clearDefaultHeaders();
	}

	
	public boolean isUsernameAvailable(String username)
	{
		try {
			HttpResponse<JsonNode> response = Unirest.get(APIURL + "/checkUsername/{search}")
			        .routeParam("search", username)
					.header("accept", "application/json")
			        .header("Content-Type", "application/json")
			        .asJson();
			JSONObject body = response.getBody().getObject();
			if (response.getStatus() == 200 && body.getBoolean("exists") == false) {
				return true;
			} else {
				return false;
			//}
			} 
		}
		catch (UnirestException | JSONException e) {
			e.printStackTrace();
		}
		return false;	
	}
	
	
	@Override
	public boolean createUser(String username, String email, String password, String gender, int height,
			int weight, LocalDate birthDate, int calories) {
		try {
			DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
			String dateString = birthDate.format(dtf);
			HttpResponse<JsonNode> response = Unirest.post(APIURL + "/register")
					  .header("Content-Type", "application/x-www-form-urlencoded")
					  .header("cache-control", "no-cache")
					  .header("Postman-Token", "535f3255-6849-45e2-b465-1d0abb1dbf24")
					  .body("username=" + username + "&password=" + password + "&birthdate=" +dateString + "&weight=" + weight + "&height=" + height + "&email=" + email + "&gender=" + gender +"&calorie_goal=" + calories)
					  .asJson();
			JSONObject body = response.getBody().getObject();
			if (response.getStatus() == 200 && body.getBoolean("success")) {
				return true;
			} else {
				System.out.println(body.getString("message"));//TODO show this in a popup message
			}
		} catch (UnirestException | JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	
	public boolean createUser(User user)
	{
		return createUser(user.getName(), user.getEmail(), user.getPass(), user.getGender(), user.getHeight(), user.getWeight(), user.getBirth(), user.getCalories());
	}
	
	
	public User getUser(String username)
	{
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		try {
			
			HttpResponse<JsonNode> response = Unirest.get(APIURL + "/profile")
					.header("Content-Type", "application/x-www-form-urlencoded")
					.header("cache-control", "no-cache")
					.header("Postman-Token", "171b0435-9d3f-40c9-a573-ffcc66f343cd")
					.asJson();
			JSONObject body = response.getBody().getObject();
			if (response.getStatus() == 200 && body.getBoolean("success")) 
			{
				body = body.getJSONObject("profile");
				String name = body.getString("username");
				String email = body.getString("email");
				String dateString = body.getString("birthdate");
				dateString = dateString.substring(0, 10);
				LocalDate date = LocalDate.parse(dateString, dtf);
				String gender = body.getString("gender");
				int height = body.getInt("height");
				int weight = body.getInt("weight");
				int calories = body.optInt("calorie_goal", 0);
				User u = new User(name, email, "nopass", date, gender, height, weight, calories);
				return u;
			} else {
				System.out.println(body.getString("message"));//TODO show this in a popup message
			}
		} 
		catch (UnirestException  | JSONException e) {
			e.printStackTrace();
		}
		return new User();
	}

	

	@Override
	public ArrayList<FoodItem> getFoodByDate(LocalDate date, String username) {
		updateFoods();
		ArrayList<FoodItem> datedFoodList = new ArrayList<FoodItem>();
		
		for(FoodItem f : foodList)
		{
			if(f.getDate().isEqual(date))
			{
				datedFoodList.add(f);
			}
		}
		return datedFoodList;	
	}
	
	private void updateFoods() {
		try {
			HttpResponse<JsonNode> response = Unirest.get(APIURL + "/meal")
				.header("accept", "application/json")
		        .header("Content-Type", "application/json")
		        .asJson();
			JSONObject body = response.getBody().getObject();
			
			
			if (response.getStatus() == 200 && body.has("results")) {
				JSONArray foods = body.getJSONArray("results");
				int size = foods.length();
				foodList.clear();
				for (int i=0; i<size; i++)
					foodList.add(new FoodItem(foods.getJSONObject(i), true));
			}
		}
		catch (UnirestException | JSONException e) {
			e.printStackTrace();
		}
		
	}

	@Override
	public boolean deleteMeal(int uniqueID) {
		try {
			Unirest.post(APIURL + "/meal/{mid}")
				.routeParam("mid", Integer.toString(uniqueID))
				.header("Content-Type", "application/x-www-form-urlencoded")
				.header("cache-control", "no-cache")
				.header("Postman-Token", "171b0435-9d3f-40c9-a573-ffcc66f343cd")
				.body("remove=1")
				.asJson();
			updateFoods();
		} catch (UnirestException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		
		return true;
	}

	@Override
	public int addMeal(FoodItem meal, String username) {
		// TODO Auto-generated method stub
		//needs to insert into db and return the mealID
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd 00:00:00");
		HttpResponse<JsonNode> response;
		try {
			response = Unirest.post(APIURL + "/meal")
				.header("Content-Type", "application/x-www-form-urlencoded")
				.header("cache-control", "no-cache")
				.header("Postman-Token", "171b0435-9d3f-40c9-a573-ffcc66f343cd")
				.body(
					"name=" + meal.getName() +
					"&servings=" + meal.getServings() +
					"&calories=" + meal.getCalories() +
					"&fats=" + meal.getFat() +
					"&carbohydrates=" + meal.getCarbs() +
					"&proteins=" + meal.getProtein() +
					"&consumed_at=" + meal.getDate().format(dtf)
				)
				.asJson();
			JSONObject body = response.getBody().getObject();
			if (response.getStatus() == 200 && body.getBoolean("success")) 
			{
				meal.setMealID(body.getInt("mid"));
				foodList.add(meal);
				return body.getInt("mid");
			} else {
				System.out.println(body.getString("message"));//TODO show this in a popup message
			}
		} catch (UnirestException | JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return -1;
	}

	public boolean updateUser(User user)
	{	
		try {
				DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
				String dateString = user.getBirth().format(dtf);
				HttpResponse<JsonNode> response = Unirest.post(APIURL + "/profile")
						  .header("Content-Type", "application/x-www-form-urlencoded")
						  .header("cache-control", "no-cache")
						  .header("Postman-Token", "535f3255-6849-45e2-b465-1d0abb1dbf24")
						  .body(  "weight=" + user.getWeight() 
								  + "&height=" + user.getHeight()  
								  + "&gender=" + user.getGender()
								  +"&calorie_goal=" + user.getCalories())
						  .asJson();
				JSONObject body = response.getBody().getObject();
				if (response.getStatus() == 200 && body.getBoolean("success")) {
					return true;
				} else {
					System.out.println(body.getString("message"));//TODO show this in a popup message
				}
			} catch (UnirestException | JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
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
		
	}


	


	@Override
	public void closeConnection() {
		// TODO Auto-generated method stub
		
	}


	@Override
	public ArrayList<FoodItem> searchForFood(String keyword) {
		ArrayList<FoodItem> results = new ArrayList<FoodItem>();
		try {
			HttpResponse<JsonNode> response = Unirest.get(APIURL + "/foodSearch/{search}")
		        .routeParam("search", keyword)
				.header("accept", "application/json")
		        .header("Content-Type", "application/json")
		        .asJson();
			JSONObject body = response.getBody().getObject();
			
			
			if (response.getStatus() == 200 && body.has("results")) {
				JSONArray foods = body.getJSONArray("results");
				int size = foods.length();
				
				for (int i=0; i<size; i++)
					results.add(new FoodItem(foods.getJSONObject(i)));
			}
		}
		catch (UnirestException | JSONException e) {
			e.printStackTrace();
		}
		return results;
	}
	
	
}
