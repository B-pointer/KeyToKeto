

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import org.junit.jupiter.api.Test;

import Models.User;

class UserTest {

	@Test
	void testUser() {
		User user = new User();
	}

	@Test
	void testUserStringStringStringLocalDateStringIntIntInt() {
		LocalDate bDay = LocalDate.of(2001, 01, 01);
		User user = new User("test1", "test1@email.com", "P@55W0rd123", bDay, "Male", 72 , 200, 2000);
	}

	@Test
	void testSetGetName() {
		String newName = "test2";
		User user = new User();
		user.setName(newName);
		String testName = user.getName(); 
		assertEquals(newName, testName);
	}

	@Test
	void testSetGetEmail() {
		String newEmail = "test2@email.com";
		User user = new User();
		user.setEmail(newEmail);
		String testEmail = user.getName(); 
		assertEquals(newEmail, testEmail);
	}

	@Test
	void testSetGetPass() {
		String newPass = "P@55w0rd";
		User user = new User();
		user.setPass(newPass);
		String testPass = user.getPass(); 
		assertEquals(newPass, testPass);
	}

	@Test
	void testSetGetAge() {
		User user = new User();
		user.setAge(20);
		int testAge = user.getAge(); 
		assertEquals(20, testAge);
	}

	@Test
	void testSetGetGender() {
		User user = new User();
		user.setGender("Male");
		String testGender = user.getPass(); 
		assertEquals("Male", testGender);
	}

	@Test
	void testSetGetHeight() {
		User user = new User();
		user.setAge(72);
		int testHeight = user.getHeight(); 
		assertEquals(20, testHeight);
	}

	@Test
	void testSetGetWeight() {
		User user = new User();
		user.setWeight(200);
		int testWeight = user.getWeight(); 
		assertEquals(200, testWeight);
	}

	@Test
	void testSetGetGetAdmin() {
		User user = new User();
		user.setAdmin(true);
		String testAdmin = user.getPass(); 
		assertEquals(true, testAdmin);
	}

	@Test
	void testSetGetCalories() {
		User user = new User();
		user.setCalories(2000);
		int testHeight = user.getCalories(); 
		assertEquals(20, testHeight);
	}

}
