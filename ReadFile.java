/** This class will open and read a text file
 *  using try/catch
 */

/**
 * @author dds19 Dwight Sims
 * @version 1.0
 * Key to Keto
 * FileName: ReadFile.java
 */
import java.util.Scanner;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
public class ReadFile {
	private static String fileName;
	private static FileReader fileReader;
	private static BufferedReader bufferedReader ;
	private static String line;
	private static List <LongInteger> equation;
	
	public ReadFile() {
	}
	 
	public static void readFile(String myfile, int myStyle) throws IOException {
		System.out.println("Opening file.");
		Scanner inputStream = null;
		fileName = "ktkUsers.txt";
		System.out.println(fileName);
		try {
			inputStream = new Scanner(new FileInputStream(fileName));
		}
		catch (FileNotFoundException e){
			System.out.println("File " + fileName + " was not found");
			System.exit(0);
		}
		while (inputStream.hasNextLine()) {
			line = inputStream.nextLine();
		}
		inputStream.close( );
		
		try {
			fileReader = new FileReader(fileName);
		} 
		catch (FileNotFoundException e) {
			e.printStackTrace();
			System.out.println("Unable to read " + fileName + ".");
			System.exit(0);
		}
		System.out.println();

		bufferedReader = new BufferedReader(fileReader);
		equation = new ArrayList<>();

		if(myStyle == 1) {
			System.out.println("Using LinkedList");
		}
		if(myStyle == 2) {
			System.out.println("Using Stacks");
		}

		while ((line = bufferedReader.readLine()) != null) {
			String[] temp = line.split(" ");
			String fNumber = temp[0];
			String operand = temp[1];
			String sNumber = temp[2];
			
			equation.add(new LongInteger(fNumber,operand,sNumber,myStyle));	
		}
		
		inputStream.close( );
	}
}
