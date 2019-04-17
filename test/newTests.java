import org.junit.Test;

import Calculations.KetoCalculations;

import static org.junit.Assert.assertEquals;

public class newTests {
	
   KetoCalculations keto = new KetoCalculations();

   @Test
   public void testCalories() {
     int Q = keto.calculateCalories(22, 67, 160, "Male");
     int A = 1752;
     assertEquals(Q,A);
   }
   @Test
   public void testCarbs() {
	   int Q = keto.calculateCarbs(1752);
	   int A = 21;
	   assertEquals(Q,A);
   }
   @Test
   public void testProteins(){
	   int Q = keto.calculateProtein(1752);
	   int A = 87;
	   assertEquals(Q,A);
   }
   @Test
   public void testFats(){
	   int Q = keto.calculateFat(1752);
	   int A = 146;
	   assertEquals(Q,A);
   }
}



