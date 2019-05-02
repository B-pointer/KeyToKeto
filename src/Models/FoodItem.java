package Models;

import java.time.LocalDate;

import org.json.JSONException;
import org.json.JSONObject;

public class FoodItem {
	
	private String name;
	private int ID;
	private int calories;
	private int carbs;
	private int protein;
	private int fat;
	private double servings;
	private int mealID;
	private LocalDate date;
	
	public FoodItem()
	{
		
	}
	//without date, without insertionID. Each only has a valid value when both of them do. ID, it either has both or neither
	public FoodItem(String name, int ID, int calories, int carbs, int protein, int fat, double servings)
	{
		this.name = name;
		this.ID = ID;
		this.calories = calories;
		this.carbs = carbs;
		this.protein = protein;
		this.fat = fat;
		this.servings = servings;
		mealID = -1;
		this.date = null; //null because this constructor should only be used for items that have not been added to diary
	}
	//with date, with insertionID
	public FoodItem(String name, int ID, int calories, int carbs, int protein, int fat, double servings, int insertionID, LocalDate date)
	{
		this.name = name;
		this.ID = ID;
		this.calories = calories;
		this.carbs = carbs;
		this.protein = protein;
		this.fat = fat;
		this.servings = servings;
		this.mealID = insertionID;
		this.date = date;
		
	}
	
	public FoodItem(JSONObject food) throws JSONException {
		this(food.getString("name") + " " + food.getString("servingsize"), food.getInt("fid"), food.getInt("calories"), food.getInt("carbohydrates"), food.getInt("proteins"), food.getInt("fats"), -1.0);
	}
	
	public void setName(String name)
	{
		this.name = name;
	}
	public void setID(int ID)
	{
		this.ID = ID;
	}
	public void setCalories(int calories)
	{
		this.calories = calories;
	}
	public void setCarbs(int carbs)
	{
		this.carbs = carbs;
	}
	public void setProtein(int protein)
	{
		this.protein = protein;
	}
	public void setFat(int fat)
	{
		this.fat = fat;
	}
	public void setServings(double servings)
	{
		this.servings = servings;
	}
	public void setMealID(int insertionID)
	{
		this.mealID = insertionID;
	}
	public void setDate(LocalDate date)
	{
		this.date = date;
	}
	public String getName()
	{
		return name;
	}
	public int getID()
	{
		return ID;
	}
	public int getCalories()
	{
		return calories;
	}
	public int getCarbs()
	{
		return carbs;
	}
	public int getProtein()
	{
		return protein;
	}
	public int getFat()
	{
		return fat;
	}
	public double getServings()
	{
		return servings;
	}
	public int getMealID()
	{
		return mealID;
	}
	public LocalDate getDate()
	{
		return date;
	}
	
}