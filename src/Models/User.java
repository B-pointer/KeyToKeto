package Models;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import Calculations.KetoCalculations;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

/** This class will collect the user info
 * 
 */

/**
 * @author ddath
 *
 */
public class User {

	private LocalDate birthdate;
	private String name;
	private String email;
	private String pass;
	private int age;
	private String gender;
	private int height;
	private int weight;
	private boolean admin;
	private int calories;
	
	//these properties are used for bindings so that any time the calories/macros are modified, the GUI is updated
	private final IntegerProperty calorieProperty = new SimpleIntegerProperty();
	private final IntegerProperty carbsProperty = new SimpleIntegerProperty();
	private final IntegerProperty proteinProperty = new SimpleIntegerProperty();
	private final IntegerProperty fatProperty = new SimpleIntegerProperty();
	
	/**
	 * 
	 */
	public User() {
		// TODO Auto-generated constructor stub
	}
	
	
	public User(String myName, String myEmail, String myPass, LocalDate birthdate, String myGender, int myTall, int myWide, int myCalories)
	{
		setName(myName);
		setEmail(myEmail);
		setPass(myPass);
		setAge((int)ChronoUnit.YEARS.between(birthdate, LocalDate.now()));
		setGender(myGender);
		setHeight(myTall);
		setWeight(myWide);
		setAdmin(false);
		setCalories(myCalories);
		
		this.birthdate = birthdate;
		
		setProperties();
	}

	//getters for observable properties that are used for bindings. not stored in database, but the int representation of calories is stored. 
	
	public IntegerProperty CalorieProperty()
	{
		return calorieProperty;
	}
	
	public IntegerProperty CarbsProperty()
	{
		return carbsProperty;
	}
	
	public IntegerProperty FatProperty()
	{
		return fatProperty;
	}
	
	public IntegerProperty ProteinProperty()
	{
		return proteinProperty;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @param pass the pass to set
	 */
	public void setPass(String pass) {
		this.pass = pass;
	}

	/**
	 * @param age the age to set
	 */
	public void setAge(int age) {
		this.age = age;
	}

	/**
	 * @param gender the gender to set
	 */
	public void setGender(String gender) {
		this.gender = gender;
	}

	/**
	 * @param height the height to set
	 */
	public void setHeight(int height) {
		this.height = height;
	}

	/**
	 * @param weight the weight to set
	 */
	public void setWeight(int weight) {
		this.weight = weight;
	}

	/**
	 * @param admin the admin to set
	 */
	public void setAdmin(boolean admin) {
		this.admin = admin;
	}
	
	public void setCalories(int calories)
	{
		
		this.calories = calories;
		//calorieProperty.set(calories);
		setProperties();
	}
	
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @return the pass
	 */
	public String getPass() {
		return pass;
	}

	/**
	 * @return the age
	 */
	public int getAge() {
		return age;
	}

	/**
	 * @return the gender
	 */
	public String getGender() {
		return gender;
	}

	/**
	 * @return the height
	 */
	public int getHeight() {
		return height;
	}

	/**
	 * @return the weight
	 */
	public int getWeight() {
		return weight;
	}

	/**
	 * @return the admin
	 */
	public boolean isAdmin() {
		return admin;
	}
	
	public int getCalories()
	{
		return calories;
	}
	
	
	public void setProperties()
	{
		calorieProperty.set(calories);
		carbsProperty.set(KetoCalculations.calculateCarbs(calories));
		fatProperty.set(KetoCalculations.calculateFat(calories));
		proteinProperty.set(KetoCalculations.calculateProtein(calories));
	}

}
