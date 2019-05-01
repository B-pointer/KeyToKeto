

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import Calculations.KetoCalculations;

class KetoCalculationsTest {

	@Test
	void testCalculateCaloriesMale() {
		int age, height, weight;
		age = 20;
		height = 72;
		weight = 200;
		String gender = "Male";
		double myBMR = 2067.8175248;
		assertEquals(myBMR, KetoCalculations.calculateCalories(age,height,weight,gender));
	}

	@Test
	void testCalculateCarbsFemale() {
		int age, height, weight;
		age = 20;
		height = 66;
		weight = 130;
		String gender = "Female";
		double myBMR = 1425.60919912;
		assertEquals(myBMR, KetoCalculations.calculateCalories(age,height,weight,gender));
	}

	@Test
	void testCalculateCarbs() {
		int testCals = 2000;
		int testCarbs = 25;
		assertEquals(testCarbs, KetoCalculations.calculateCarbs(testCals));
	}

	@Test
	void testCalculateFat() {
		int testCals = 2000;
		int testFat = 169;
		assertEquals(testFat, KetoCalculations.calculateCarbs(testCals));
	}

	@Test
	void testCalculateProtein() {
		int testCals = 2000;
		int testProtein = 100;
		assertEquals(testProtein, KetoCalculations.calculateProtein(testCals));
	}

}
