import java.util.*;
import java.util.Scanner;
import java.io.*;
import java.util.StringTokenizer;
public class lexical_analizer{
	static List <String> list = new ArrayList<String>();
	public static void main(String [] args) {
		// The name of the file to open.
		String fileName = "input.txt";
		String input;
		String token;

		// This will reference one line at a time
		String line = null;

		try {
			// FileReader reads text files in the default encoding.
			String content = new Scanner(new File("input.txt")).useDelimiter("\\Z").next();
			System.out.println("Input file converted to a string:");
			System.out.println(content);

		    FileReader fileReader =
		    new FileReader(fileName);

		    // Always wrap FileReader in BufferedReader.
		    BufferedReader bufferedReader = new BufferedReader(fileReader);
		    String str;


		    while((str = bufferedReader.readLine()) != null) {
				list.add(str);
			}
			String[] stringArr = list.toArray(new String[0]);

			while((line = bufferedReader.readLine()) != null) {
				System.out.println(line);
				}

		    // Always close files.
		    bufferedReader.close();
		}
		catch(FileNotFoundException ex) {
			System.out.println("Unable to open file '" + fileName + "'");
		}


		catch(IOException ex) {
			System.out.println("Error reading file '" + fileName + "'");
		    // Or we could just do this:
		    // ex.printStackTrace();
		}

		token = String_Locator(1);
		System.out.println("Item in Array List is: " + token);
	}

	public static String String_Locator(Integer a) {
		String item;
		item = list.get(a);
		return item;
	}

}