import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

@RunWith(Parameterized.class)
public class ParametricCheckPasswordTest{
	private String password;
	private Boolean result;
	
	//this class is being used to test the CheckPassword.CheckPassword() static method only. this is due to the parameterized nature of the tests required
	//Additional tests for the CheckPassword class are in the CheckPasswordTest.java class 
	public ParametricCheckPasswordTest(String password, Boolean result)
	{
		this.password = password;
		this.result = result;
	}
	
	@Parameterized.Parameters
	public static Collection input() {
		return Arrays.asList(new Object[][] {
			{"test", false}, 
			{"testa", false}, 
			{"test1", false},
			{"Test1", false}, 
			{"TEST1", false}, 
			{"Tst1!", true}, 
			{"tesT11!", true}
		});
	}
	
	@Test
	public void testCheckPassword()
	{
		assertEquals(CheckPassword.CheckPassword(password), result);
	}
}

