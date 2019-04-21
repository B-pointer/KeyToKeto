package Models;

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

	private String name;
	private String email;
	private String pass;
	private int age;
	private String gender;
	private int height;
	private int weight;
	private boolean admin;
	private int calories;
	private final IntegerProperty calorieProperty = new SimpleIntegerProperty();
	
	/**
	 * 
	 */
	public User() {
		// TODO Auto-generated constructor stub
	}
	//this constructor does not have calories in it,not currently used 
	public User(String myName, String myEmail, String myPass, int myAge, String myGender, int myTall, int myWide, boolean myAdmin ) {
		setName(myName);
		setEmail(myEmail);
		setPass(myPass);
		setAge(myAge);
		setGender(myGender);
		setHeight(myTall);
		setWeight(myWide);
		setAdmin(myAdmin);
		//setCalories(calories);
	}
	//this contructor has calories in it, used in account retrieval
	public User(String myName, String myEmail, String myPass, int myAge, String myGender, int myTall, int myWide, int myCalories,  boolean myAdmin ) {
		setName(myName);
		setEmail(myEmail);
		setPass(myPass);
		setAge(myAge);
		setGender(myGender);
		setHeight(myTall);
		setWeight(myWide);
		setAdmin(myAdmin);
		setCalories(myCalories);
		
		
		//test code for now
		calorieProperty.set(myCalories);
		
	}

	
	
	public IntegerProperty CalorieProperty()
	{
		return calorieProperty;
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
		calorieProperty.set(calories);
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

}
