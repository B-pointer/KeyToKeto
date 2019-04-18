package Models;


public class FoodItem {
	
	private String name;
	private int ID;
	private int calories;
	private int carbs;
	private int protein;
	private int fat;
	private double servings;
	private int insertionID;
	private String date;
	
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
		insertionID = -1;
		this.date = "NA";
	}
	//with date, with insertionID
	public FoodItem(String name, int ID, int calories, int carbs, int protein, int fat, double servings, int insertionID, String date)
	{
		this.name = name;
		this.ID = ID;
		this.calories = calories;
		this.carbs = carbs;
		this.protein = protein;
		this.fat = fat;
		this.servings = servings;
		this.insertionID = insertionID;
		this.date = date;
		
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
	public void setInsertionID(int insertionID)
	{
		this.insertionID = insertionID;
	}
	public void setDate(String date)
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
	public int getInsertionID()
	{
		return insertionID;
	}
	public String getDate()
	{
		return date;
	}
	
}