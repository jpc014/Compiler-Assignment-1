public class Token
{
	private String token;
	private String lexeme;

	public Token(String token, String lexeme)
	{
		this.token = token;
		this. lexeme = lexeme;
	}

	public String getTokenType()
	{
		return token;
	}

	public String getLexeme()
	{
		return lexeme;
	}

	public String toString()
	{
		return token + "\t" + lexeme;
	}
}