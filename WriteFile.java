/** This class creates and fills a user file  */

/**
 * @author dds19 Dwight Sims
 * @version 1.0
 * KeytoKeto
 * FileName: WriteFile.java
 */
import java.io.PrintWriter;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;

public class WriteFile {
	private static String filePath;
	private static String fileName;
	private static String tmpFilePath;
	private static String theEvent;
	

	/** Initialize the variables */
	public WriteFile() {
		filePath = ".\\\\src\\\\";
		fileName = "ktkUsers.txt";
		tmpFilePath = filePath + fileName;
	}
	
	/**Setup the file path 	
	 * @return tmpFilePath
	 */
	public static String setPath() {
		filePath = ".\\\\src\\\\";
		fileName = "ktkUsers.txt";
		tmpFilePath = filePath + fileName;
		return tmpFilePath;
	}
	
	/**
	 * Send log data to the printer
	 * @param myEvent
	 */
	public LogFile(String myUser) {
		setUser(myEvent);
	}
	
	/**
	 * Adds the time stamp to the log data
	 * @param myEvent
	 */
	public static void setUser(String myEvent) {
		Date stamp = new Date();
		String setEvent = stamp + "\t" + myEvent;
		theEvent = setEvent;
	}
	
	/**
	 * Attempt to do it all at once...works
	 * @param myEvent
	 * @throws IOException
	 */
	public static void createLog(String myEvent) throws IOException {
		filePath = ".\\\\src\\\\";
		fileName = "dbOperations.log";
		String tmpFilePath = filePath + fileName;

		PrintWriter printWriter;
		setTheEvent(myEvent);
		try {
			printWriter = new PrintWriter(new FileWriter (tmpFilePath, true));
			printWriter.println (theEvent);
			printWriter.close ();       
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
}
