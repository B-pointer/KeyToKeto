/** Creates a connection to a database
 * 
 */

/**
 * @author ddath
 *
 */
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class CreateDB {
	private static final String driver = "org.apache.derby.jdbc.EmbeddedDriver";
	private static final String protocol = "jdbc:derby";
	private static String dbName= "keytoketo";
	private static Connection conn = null;
	
	public static void startDB (String [] args){
		
	}

	/**
	 * 
	 */
	public CreateDB() {
		try {
			Class.forName(driver).newInstance();
			
		}
		catch (Exception err) {
			System.err.println("Unable to load the embedded driver.");
			System.exit(0);
		}
		
		try {
			conn = DriverManager.getConnection(protocol + dbName + ";create=true");
			Statement s = conn.createStatement();
			s.execute("CREATE TABLE users" +
					"(email varchar(50), name varchar(25), pwd varchar(50), "
					+ "int age, char gender, double height, double weight"
					+ "bool admin)");
			conn.close();
		}
		catch (SQLException err){
			err.printStackTrace(System.err);
			System.exit(0);
		}
	}
	
	public void createNewUser(String myEmail, String myName, String myPwd, int myAge,
			char myGen, double myTall, double myWide) {
		try{
			Statement s = conn.createStatement();
			s.execute("INSERT INTO users " +
					"VALUES (myEmail, myName, myPwd, myAge, myGen, myTall, myWide)");
			conn.close();
		}
		catch (SQLException err){
			err.printStackTrace(System.err);
			System.exit(0);
		}
	}
}
