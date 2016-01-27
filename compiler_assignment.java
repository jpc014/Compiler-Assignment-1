import java.util.*;
import java.io.*;
public class compiler_assignment{
	public static void main(String [] args) {
		// The name of the file to open.
		String fileName = "input.txt";

		// This will reference one line at a time
		String line = null;

		try {
			// FileReader reads text files in the default encoding.
		    FileReader fileReader =
		    new FileReader(fileName);

		    // Always wrap FileReader in BufferedReader.
		    BufferedReader bufferedReader = new BufferedReader(fileReader);
		    String str;

		    List <String> list = new ArrayList<String>();
		    while((str = bufferedReader.readLine()) != null) {
				list.add(str);
			}
			System.out.println(list);
			String[] stringArr = list.toArray(new String[0]);

			while((line = bufferedReader.readLine()) != null) {
				System.out.println(line);
				}

		            // Always close files.
		            bufferedReader.close();
		        }
		        catch(FileNotFoundException ex) {
		            System.out.println(
		                "Unable to open file '" +
		                fileName + "'");
		        }
		        catch(IOException ex) {
		            System.out.println(
		                "Error reading file '"
		                + fileName + "'");
		            // Or we could just do this:
		            // ex.printStackTrace();
		        }
		    }
}