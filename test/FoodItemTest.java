

import static org.junit.jupiter.api.Assertions.*;
import java.time.LocalDate;
import org.junit.jupiter.api.Test;

import Models.FoodItem;

class FoodItemTest {

	@Test
	void testFoodItem() {
		FoodItem newFood = new FoodItem();
	}

	@Test
	void testFoodItemStringIntIntIntIntIntDouble() {
		FoodItem newFood = new FoodItem("Creamy Chicken Noodle", 10, 220, 18, 7, 13, 1);
	}

	@Test
	void testFoodItemStringIntIntIntIntIntDoubleIntLocalDate() {
		LocalDate date = LocalDate.now();
		FoodItem newFood = new FoodItem("Creamy Chicken Noodle", 10, 220, 18, 7, 13, 1, 101, date);
	}

	@Test
	void testSetGetName() {
		String newName = "Creamy Chicken Noodle";
		FoodItem newFood = new FoodItem();
		newFood.setName(newName);
		String testName = newFood.getName(); 
		assertEquals(newName, testName);

	}

	@Test
	void testSetGetID() {
		int newInt = 111112;
		FoodItem newFood = new FoodItem();
		newFood.setID(newInt);
		int testInt = newFood.getID(); 
		assertEquals(newInt, testInt);
	}

	@Test
	void testSetGetCalories() {
		int newInt = 220;
		FoodItem newFood = new FoodItem();
		newFood.setID(newInt);
		int testInt = newFood.getCalories(); 
		assertEquals(newInt, testInt);
	}

	@Test
	void testSetGetCarbs() {
		int newInt = 18;
		FoodItem newFood = new FoodItem();
		newFood.setID(newInt);
		int testInt = newFood.getCarbs(); 
		assertEquals(newInt, testInt);
	}

	@Test
	void testSetGetProtein() {
		int newInt = 7;
		FoodItem newFood = new FoodItem();
		newFood.setID(newInt);
		int testInt = newFood.getProtein(); 
		assertEquals(newInt, testInt);
	}

	@Test
	void testSetGetFat() {
		int newInt = 13;
		FoodItem newFood = new FoodItem();
		newFood.setID(newInt);
		int testInt = newFood.getFat(); 
		assertEquals(newInt, testInt);
	}

	@Test
	void testSetGetServings() {
		int newInt = 3;
		FoodItem newFood = new FoodItem();
		newFood.setID(newInt);
		double testInt = newFood.getServings(); 
		assertEquals(newInt, Double.valueOf(testInt));
	}

	@Test
	void testSetGetMealID() {
		int newInt = 1313;
		FoodItem newFood = new FoodItem();
		newFood.setID(newInt);
		int testInt = newFood.getMealID(); 
		assertEquals(newInt, testInt);
	}

	@Test
	void testSetGetDate() {
		FoodItem newFood = new FoodItem();
		LocalDate newDay = LocalDate.of(2001, 01, 01);
		newFood.setDate(newDay);
		LocalDate testDay = newFood.getDate(); 
		assertEquals(newDay, testDay);
	}

}
