import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import Calculations.CheckPassword;

class CheckPasswordTest {

	@Test
	void testCheckPassword() {
		CheckPassword checkPW = new CheckPassword();
	}

	@Test
	void testCheckPasswordStringPass() {
		boolean checkPW = CheckPassword.CheckPassword("MyP!@#$123asdf");
		assertEquals(true, checkPW);

	}

	@Test
	void testCheckPasswordStringFailLength() {
		boolean checkPW = CheckPassword.CheckPassword("MyP");
		assertEquals(false, checkPW);
	}

	@Test
	void testCheckPasswordStringFailNumber() {
		boolean checkPW = CheckPassword.CheckPassword("MyP!@#$!@#asdf");
		assertEquals(false, checkPW);
	}

	@Test
	void testCheckPasswordStringFailLowerCase() {
		boolean checkPW = CheckPassword.CheckPassword("MYP!@#$123ASDF");
		assertEquals(false, checkPW);
	}

	@Test
	void testCheckPasswordStringFailUpperCase() {
		boolean checkPW = CheckPassword.CheckPassword("myp!@#$123asdf");
		assertEquals(false, checkPW);
	}

	@Test
	void testCheckPasswordStringFailSpecialChar() {
		boolean checkPW = CheckPassword.CheckPassword("MyP1234123asdf");
		assertEquals(false, checkPW);
	}

	@Test
	void testCheckPasswordStringFailComplexity() {
		boolean checkPW = CheckPassword.CheckPassword("mypasswordasdf");
		assertEquals(false, checkPW);
	}

	@Test
	void testMatchPasswordTrue() {
		String testPw1 = "MyP!@#$123asdf";
		String testPw2 = "MyP!@#$123asdf";
		assertEquals(true, CheckPassword.matchPassword(testPw1, testPw2));
	}

	@Test
	void testMatchPasswordFalse() {
		String testPw1 = "MyP!@#$123asdf";
		String testPw2 = "MyP!@#$123sdf";
		assertEquals(false, CheckPassword.matchPassword(testPw1, testPw2));
	}

	@Test
	void testGetReason() {
		assertEquals("None", CheckPassword.getReason());
	}

}
