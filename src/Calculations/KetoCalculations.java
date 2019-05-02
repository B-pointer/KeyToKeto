package Calculations;
/*author: brandon pointer
 * this class calculates the requirements for ketosis based on height, weight, age and gender
 * height is in inches
 * weight is in pounds
 * Based on the classic keto diet, where 75 percent of calories come from fat, 20 from protein, and 5 from carbs
 * 
 */
public class KetoCalculations {
	private static double KilosPerPound = 0.453592;
	private static double CMPerInch = 2.54;
	private static int caloriesPerGramProtein = 4;
	private static int caloriesPerGramFat = 9;
	private static int caloriesPerGramCarbs = 4;
	private static double percentCaloriesFat = 0.75;
	private static double percentCaloriesCarbs = 0.05;
	private static double percentCaloriesProtein = 0.2;
	
	//calculates total calories from height, weight, age and gender
	public static int calculateCalories(int age, int height, int weight, String gender)
	{
		double BMR;	
		if(gender.equalsIgnoreCase("MALE"))
		{
			BMR = (13.397 * weight * KilosPerPound) + (4.799 * height * CMPerInch) - (5.677 * age) + 88.362;
		}
		else
		{
			BMR = (9.247 * weight * KilosPerPound) + (3.098 * height * CMPerInch) - (4.330 * age) + 447.593;
		}
		return (int)BMR;
	}
	//calculates grams of carbs based solely on total calories consumed (BMR)
	public static int calculateCarbs(int totalCalories)
	{
		return (int)(totalCalories * percentCaloriesCarbs / caloriesPerGramCarbs);
	}
	//calculates grams fat based solely on total calories consumed (BMR)
	public static int calculateFat(int totalCalories)
	{
		return (int)(totalCalories * percentCaloriesFat / caloriesPerGramFat);
	}
	//calculates grams of protein based solely on total calories consumed (BMR)
	public static int calculateProtein(int totalCalories)
	{
		return (int)(totalCalories * percentCaloriesProtein / caloriesPerGramProtein);
	}
	
}
