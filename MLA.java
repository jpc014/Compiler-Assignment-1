import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

/*
 * Class MLA
 *
 * Reads a specified input file, tokenize it, and writes
 * the output to a specified output file;
 *
 * Input and output files can be given as command line
 * arguments. If no arguments are given, hard coded file
 * names will be used.
 *
 * Output file will be automatically overwritten if exists.
 *
 */
public class MLA {
	static List <String> list = new ArrayList<String>();
	static Integer counter = 0;
	public static void main(String[] args) {
		String inFile = "input.txt";
		String outFile = "Sample.out";
		String tokenToString = null;

		if (args.length > 1) {
			inFile = args[0];
			outFile = args[1];
		}

		Lexer lexer = new Lexer(inFile);

		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter(outFile));

			Token t;

			while ((t = lexer.nextToken()) != null) {
				tokenToString = t.getLexeme();
				list.add(tokenToString);
				writer.write(t.toString());
				writer.newLine();
			}

			writer.close();

			System.out.println("Done tokenizing file: " + inFile);
			System.out.println("Output written in file: " + outFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println(currentToken());

	}

	public static String currentToken()
	{
		return list.get(counter);
	}

	public static void nextToken()
	{
		if(counter < list.size() - 1){
			counter++;
		}
	}
}
