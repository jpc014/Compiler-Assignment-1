import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Lexer
{
	private BufferedReader reader;
	private char curr;

	private static final char EOF = (char) (-1);

	public Lexer(String file)
	{
		try {
			reader = new BufferedReader(new FileReader(file));
		}

		catch(Exception e) {
			e.printStackTrace();
		}

		curr = read();
	}

	private char read()
	{
		try {
			return (char) (reader.read());
		}
		catch (IOException e) {
			e.printStackTrace();
			return EOF;
		}
	}

	//Checks if a character is a digit
	private boolean isNumeric(char c) {
		if (c >= '0' && c <= '9')
			return true;

		return false;
	}

	public boolean isAlpha(char c)
	{
		if(c >= 'a' && c <= 'z')
			return true;
		if(c >= 'A' && c <= 'Z')
			return true;

		return false;
	}

	public static boolean isAlpha_r(char c)
		{
			if(c >= 'a' && c <= 'z')
				return true;
			if(c >= 'A' && c <= 'Z')
				return true;

			return false;
	}

	public Token nextToken()
	{
		int state = 1;
		int numBuffer = 0;
		String alphaBuffer = "";
		int decBuffer = 0;
		boolean skipped = false;

		while(true)
		{
			if (curr == EOF && !skipped)
			{
				skipped = true;
			}

			else if (skipped)
			{
				try {
					reader.close();
				}
				catch (IOException e) {
					e.printStackTrace();
				}

				return null;
			}

			switch (state)
			{
				case 1:
					switch(curr)
					{
						case ' ': // Whitespaces
						case '\n':
						case '\b':
						case '\f':
						case '\r':
						case '\t':
							curr = read();
							continue;

						case '+':
							curr = read();
							return new Token("Plus Operator", "+");

						case ';':
													curr = read();
							return new Token("Plus Operator", ";");

						case '-':
							curr = read();
							return new Token("Minus Operator", "-");

						case '*':
							curr = read();
							return new Token("Times Operator", "*");

						case '/':
							curr = read();
							return new Token("Division Operator", "/");

						case '(':
							curr = read();
							return new Token("Left Parenthesis", "(");

						case ')':
							curr = read();
							return new Token("Right Parenthesis", ")");

						case '{':
							curr = read();
							return new Token("Left Brace", "{");

						case '}':
							curr = read();
							return new Token("Right Brace", "}");

						case '%':
							curr = read();
							return new Token("Modulus", "%");

						case '^':
							curr = read();
							return new Token("Caret", "^");

						case '=':
							curr = read();
							return new Token("Equals", ":=");

						case ':':
							curr = read();
							if(curr == '=')
							{
								curr = read();
								return new Token("Equals", ":=");
							}

							else{
								return new Token("Colon", ":");
							}

						default:
							state = 2; //Check the next possibility
							continue;
						}

					case 2:
						if(isNumeric(curr))
						{
							numBuffer = 0; //Reset the buffer
							numBuffer += (curr - '0');

							state = 3;

							curr = read();
						}

						else
						{
							state = 5; //doesn't start with number or symbol go to case 5
						}
						continue;

						//Integer Body
					case 3:
						if(isNumeric(curr))
						{
							numBuffer *= 10;
							numBuffer += (curr - '0');

							curr = read();
						}

						else if(curr == '.')
						{
							curr = read();
							state = 4; //has decimal point go to case 4
						}

						else
						{
							return new Token("NM", "" + numBuffer);
						}

						continue;
						//decimal-start
					case 4:
						if (isNumeric(curr))
						{
							decBuffer = 0;
							decBuffer += (curr - '0');
							state = 7;
							curr = read();
						}

						else
						{
							return new Token("Error", "Invalid input: "+numBuffer+"." );
						}
						continue;
						//decimal body

					case 7:
						if (isNumeric(curr))
						{
							decBuffer *= 10;
							decBuffer += (curr - '0');

							curr = read();
						}

						else
						{
							return new Token("NM", "" + numBuffer+"."+decBuffer);
						}
						continue;

						//identifier start
					case 5:
						if(isAlpha(curr) || curr == '_')
						{
							alphaBuffer = "";
							alphaBuffer += curr;
							state = 6;
							curr = read();
						}

						else
						{
							alphaBuffer = "";
							alphaBuffer += curr;
							curr = read();

							return new Token("Error", "Invalid input:"+alphaBuffer);
						}
						continue;

					case 6:
						if((isAlpha(curr) || isNumeric(curr) || curr == '_'))
						{
							alphaBuffer += curr;
							curr = read();
						}


						else
						{
							if(alphaBuffer.equals("BEGIN"))
								return new Token("Begin Statement", "BEGIN");
							if(alphaBuffer.equals("END"))
								return new Token("End Statement", "END");

							return new Token("ID", "" + alphaBuffer);
						}
						continue;
					}
			}
		}
	}