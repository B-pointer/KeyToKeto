package DataAccess;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import Models.User;
import Models.FoodItem;

public class DataConnection implements DataAccessible{
	
	
	private static final String DRIVER = "org.apache.derby.jdbc.EmbeddedDriver";
	private static final String JDBC_URL = "jdbc:derby:DerbyTest;create=true";
	private static Connection conn = null;
	private static Statement stmt = null;
	int newMealID;

	//constructor 
	public DataConnection()
	{
		createConnection();
		setupAccountTable();
		setupMealTable();
	}
	
	
	
	
	
	
	
	
	
	
	public boolean login(String username, String password)
	{	
		try {
			String query = "SELECT COUNT(*) FROM ACCOUNT WHERE username = ? AND password = ?";
			PreparedStatement ps = conn.prepareStatement(query);
			ps.setString(1, username);
			ps.setString(2, password);
			ResultSet results =  ps.executeQuery();
			if(results.next()){
				int numberOfRows = results.getInt(1);
				if(numberOfRows > 0){
					return true;
				}
				else return false;
			}	
		}
		catch(SQLException e){
			System.out.println(e.getMessage());
		}		
		return false;
		
	}
	
	
	
	
	
	
	
	
	public boolean isUsernameAvailable(String username)
	{
		try {
			String query = "SELECT COUNT(*) FROM ACCOUNT WHERE username = ?";
			PreparedStatement ps = conn.prepareStatement(query);
			ps.setString(1, username);
			ResultSet results =  ps.executeQuery();
			if(results.next()){
				int numberOfRows = results.getInt(1);
				if(numberOfRows > 0){
					return false;
				}
				else return true;
			}	
		}
		catch(SQLException e){
			System.out.println(e.getMessage());
		}		
		return false;
	}
	
	
	
	
	//never call this without first checking if username exists with isUsernameAvailable function
	public boolean createUser(String username, String email, String password,String gender, int height, int weight, LocalDate birthdate, int calories)
	{
		try {
			DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
			String dateString = birthdate.format(dtf);
			
			String query = "INSERT INTO ACCOUNT (username, email, password, birthdate, gender, height, weight, calories) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
			PreparedStatement ps = conn.prepareStatement(query);
			ps.setString(1,  username);
			ps.setString(2,  email);
			ps.setString(3, password);
			ps.setString(4, dateString);
			ps.setString(5,  gender);
			ps.setInt(6,  height);
			ps.setInt(7, weight);
			ps.setInt(8,  calories);
			ps.execute();
			return true;
		}
		catch(SQLException e){
			System.out.println(e.getMessage());
		}
		return false;
	}
	
	
	
	
	
	
	public boolean createUser(User user)
	{
		return createUser(user.getName(), user.getEmail(), user.getPass(), user.getGender(), user.getHeight(), user.getWeight(), user.getBirth(), user.getCalories());
	}
	
	
	
	
	
	//Do not call this if login has not confirmed that there is a user account with the username, otherwise unexpected behaviour will occur. 
	//currently returns null user if error occurs, meaning if there is no user for a given username among other things 
	public User getUser(String username)
	{
		String user, password, email, gender, dateIn;
		int height, weight, calories;
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		try {
			String query = "SELECT * FROM ACCOUNT WHERE USERNAME = ?";
			PreparedStatement ps = conn.prepareStatement(query);
			ps.setString(1, username);
			ResultSet rs = ps.executeQuery();
			while(rs.next())
			{
				user = rs.getString("username");
				password = rs.getString("password");
				email = rs.getString("email");
				gender = rs.getString("gender");
				dateIn = rs.getString("birthdate");
				height = rs.getInt("height");
				weight = rs.getInt("weight");
				calories = rs.getInt("calories");
				LocalDate date = LocalDate.parse(dateIn, dtf);
				return new User(user, email, password, date, gender, height, weight, calories);
						//String myName, String myEmail, String myPass, LocalDate birthdate, String myGender, int myTall, int myWide, int myCalories
			}
		}
		catch(SQLException e){
			System.out.println(e.getMessage());
		}
		return new User("no user", "nomail", "nopass", LocalDate.now(), "nogender", -1, -1, -1);
	}
	
	
	
	//FIXME add actual calls to database.returns an empty list if no food is available for the given date and user
	public ArrayList<FoodItem> getFoodByDate(LocalDate date, String username)
	{
		
		return new ArrayList<FoodItem>();
	}
	
	//FIXME add actual calls to database. should just delete from meal table by mealID
	public boolean deleteMeal(int mealID)
	{
		
		PreparedStatement ps;
		try {
			String query = "DELETE FROM MEAL WHERE mealID = " + mealID;
			ps = conn.prepareStatement(query);
			ps.execute();
			System.out.println("Deleted Meal");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return true;
	}
	
	//FIXME add actual calls to database. returns the mealID of the meal added to the database
	public int addMeal(FoodItem meal, String username)
	{

		try {
			String query = "INSERT INTO MEAL (name, username, calories, carbs, protein, fat, servings, date) VALUES ("+ "'" + meal.getName() + "'" + ", "+"'" + username + "'" + ", " + meal.getCalories() + ", " + meal.getCarbs() + ", " + meal.getProtein() +", " + meal.getFat() +", "+ meal.getServings() + ", " +"'"+ meal.getDate() + "'"+")";
			System.out.println(query);
			PreparedStatement ps = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
			ps.execute();
			ResultSet rs = ps.getGeneratedKeys();
			if(rs.next())
			{
			newMealID = rs.getInt(1);
			}
			 
			System.out.println("Added Meal!");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		
		return newMealID;
	}
	
	//FIXME add actual calls to database. needs to basically update all fields of a given user
	public boolean updateUser(User u)
	{
		try {
			String query = "UPDATE ACCOUNT SET weight = " + u.getWeight() + ", calories = " + u.getCalories() +", height = " + u.getHeight() + ", " +" WHERE username = " + "'"+ u.getName()+"'" ;
			PreparedStatement ps = conn.prepareStatement(query);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	
	

	//setting initial connection to the database, called by constructor
	void createConnection()
	{
		try {
			Class.forName(DRIVER).newInstance();
			conn = DriverManager.getConnection(JDBC_URL);
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	
	
	
	
	
	
	
	
	
	//creating the table for user accounts it does not exist already in the database, called by constructor
	void setupAccountTable()
	{
		String TABLE_NAME = "ACCOUNT";
		try {
			stmt = conn.createStatement();
			DatabaseMetaData dbm = conn.getMetaData();
			ResultSet tables = dbm.getTables(null, null, TABLE_NAME.toUpperCase(), null);
			if(tables.next())
			{
				System.out.println("Table " + TABLE_NAME + " Already exists");
			}
			else
			{
				stmt.execute("CREATE TABLE " + TABLE_NAME + " ("
						+ " username varchar(50) primary key, "
						+ "email varchar(75), "
						+ "password varchar(255), "
						+ "birthdate varchar(16), "
						+ "gender varchar(16), " 
						+ " height integer, "
						+ "weight integer, "
					    + " calories integer )"
						);
			}
		}
		catch(SQLException e)
		{
			System.out.println(e.getMessage());
		}
	}
	
	
	
	
	
	
	//creating the table for meals it does not exist already in the database, called by constructor
	void setupMealTable()
	{
		String TABLE_NAME = "MEAL";
		try {
			stmt = conn.createStatement();
			DatabaseMetaData dbm = conn.getMetaData();
			ResultSet tables = dbm.getTables(null, null, TABLE_NAME.toUpperCase(), null);
			if(tables.next())
			{
				System.out.println("Table " + TABLE_NAME + " Already exists");
			}
			else
			{
				stmt.execute("CREATE TABLE " + TABLE_NAME + " ("
					+ "name varchar(255), "
					+ " username varchar(50),"
					+ " calories integer, "
					+ "carbs integer, "
					+ "protein integer, "
					+ "fat integer, "
					+ "servings float, "
					+ "mealID integer not null generated always as identity (start with 1, increment by 1), "
					+ "date varchar(16))"
						);
			}
		}
		catch(SQLException e)
		{
			System.out.println(e.getMessage());
		}
	}
}
