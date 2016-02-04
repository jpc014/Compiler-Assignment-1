/*
 * TODO
 * convert list "stack" to string stack
 *
 * if variable gets pushed, push in format "memory index"
 * memory indicates that it is a variable, and index is the index where that variable is found
 */

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/*
Example Grammar
<Type> -> <simple> | ARRAY[<simple>] of <type>
<simple> -> int| char | num...num

Ex) ARRAY [0..7] of integer

public void type(){
	if(lookAhead == arraySYM){
		match(arraySym);
		match(lBracket);
		simple();
		match(rBracket)
		match(ofSym);
		type();
	}
	else{
		simple();
	}
}
*/

public class parser {
	// Symbols
	private static final String BEGINSym = "BEGIN";
	private static final String ENDSym = "END";
	private static final String EQUALSSym = ":=";
	private static final String EPSILONSym = "";
	private static final String SEMICOLONSym = ";";
	private static final String ADDSym = "+";
	private static final String SUBSym = "-";
	private static final String MULSym = "*";
	private static final String DIVSym = "DIV";
	private static final String MODSym = "MOD";
	private static final String POWERSym = "^";
	private static final String POSym = "(";
	private static final String PCSym = ")";

	//Holds next token
	private static String lookAhead;

	//initialize stack
	private static Stack<String> stack = new Stack<String>();

	private static List<Variable> memory = new ArrayList<Variable>();

	//create object for lexical class
	private static MLA lex = new MLA();;


	public static void main(String args[]) {
		System.out.println("Hello from main");
		//lexical analyzer
		lex.main(args);

		lookAhead = lex.currentToken();
		program();

	}

	/*
	 * Grammar
	 * <program> -> BEGIN <stmt_list> END
	 * <stmt_list> -> <stmt_list> ; <stmt> | <stmt>
	 * <stmt> -> <id> := <expr> | epsilon
	 * <expr> -> <expr> + <term> |<expr> - <term> | <term>
	 * <term> -> <term> * <factor> | <term> DIV <factor> | <term> MOD <factor> | <factor>
	 * <factor> -> <primary> ^ <factor> | <primary>
	 * <primary> -> <id> | <num> | ( <expr> )
	 */


	private static void Match(String t){
		//compares Symbol with token stored in lookAhead, if they match advance lookAhead to next token
		//else there is an error
		if(lookAhead == t){
			nextToken1();
		}
		else{
			System.out.println(lookAhead);
			System.out.println(t);
			System.out.println("The token does not match, exiting...");
			System.exit(0);
		}
	}

	private static void nextToken1(){
		//System.out.println("\nOld token: " + lookAhead);
		lex.nextToken();
		lookAhead = lex.currentToken();
		//System.out.println("New token: " + lookAhead + "\n");
	}

	//start
	private static void program(){
		// check BEGIN symbol
		Match(BEGINSym);

		// Proceed to parse stmt_List
		stmt_List();

		// check END Symbol
		Match(ENDSym);
		System.out.println("Done, exiting...");
	}

	private static void stmt(){
		// <id> := <expr>
		id();
		Match(EQUALSSym);
		
		expr();
		System.out.println(":=");

		// epsilon

	}

	private static void stmt_List(){
		stmt();
		stmt_List_r();
	}

	private static void stmt_List_r(){
		if(lookAhead == SEMICOLONSym){
			stmt();
			stmt_List_r();
		}

		else{

		}
	}

	private static void expr(){
		term();
		expr_r();
	}

	private static void expr_r(){
		// <expr> + <term>
		if(lookAhead == ADDSym){
			Match(ADDSym);
			term();
			expr_r();
			//ADD();
			System.out.println("ADD");
		}

		// <expr> - <term>
		else if(lookAhead == SUBSym){
			Match(SUBSym);
			term();
			expr_r();
			System.out.println("SUB");
		}

		else{

		}

		// error
	}

	private static void term(){
		factor();
		term_r();

	}

	private static void term_r(){
		// <term> * <factor>
		if(lookAhead == MULSym){
			Match(MULSym);
			factor();
			term_r();
			System.out.println("MUL");
		}

		// <term> DIV <factor>
		else if(lookAhead == DIVSym){
			Match(DIVSym);
			factor();
			term_r();
			System.out.println("MUL");
		}

		// <term> MOD <factor>
		else if(lookAhead == MODSym){
			Match(MODSym);
			factor();
			term_r();
			System.out.println("MOD");
		}

		// <factor>
		else{
		}

		// error
	}

	private static void factor(){
		primary();
		factor_r();
	}

	private static void factor_r(){
		// <primary> ^ <factor>
		if(lookAhead == POWERSym){
			Match(POWERSym);
			primary();
			factor_r();
			System.out.println("POW");
		}

		// <primary>
		else {
		}

		// error
	}

	private static void primary(){
		// reconize current token as an (<expr>)
		if(lookAhead.substring(0, 0) == POSym){
			Match(POSym);
			expr();
			Match(PCSym);
		}

		// reconize current token as an <id> (begins with a letter)
		if(Lexer.isAlpha_r(lookAhead.charAt(0))){
			id();
		}

		// reconize current token as a <num>
		else{
			PUSH(lookAhead);
		}

		// error
	}

	private static void id(){
		//check if variable already exists in artificial memory array, if it does overwrite the value stores for that variable
		//otherwise create new entry

		/*
		//check if variable already exists
		Variable v;
		for(int i =0; i<memory.size(); i++){
			v=memory.get(i);

			//if it does update value
			if( lookAhead == v.getVariable() ){

				return;
			}
		}
		*/

		//determine if valid id


		LVALUE(lookAhead);

		//advance to next token
		//nextToken1();

	}

	// Instructions

	private static void PUSH(String s) {
		// push s (an integer constant) on the stack
		System.out.println("PUSH " + lookAhead);

		stack.push(s + "");
		nextToken1();
	}

	private static void POP() {
		// throw away the top value on the stack
		try {
			System.out.println("Hello from POP");
			stack.pop();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("POP failed, Stack is empty");
			HALT();
		}
		nextToken1();
	}

	private static void RVALUE(String var) {
		// push the contents of variable l
		System.out.println("Hello from RVALUE");

		//find var
		Variable v;
		for(int i=0; i < memory.size(); i++){
			v = memory.get(i);

			if(v.getVariable() == var){
				PUSH(v.getValue() + "");
			}
		}
		nextToken1();
	}

	private static void LVALUE(String l) {
		// push the address of the variable l
		System.out.println("LVALUE " + lookAhead);

		Variable v;
		for(int i=0; i < memory.size(); i++){
			v = memory.get(i);

			if(v.getVariable() == l){
				stack.push(("memory " + i));
				nextToken1();
				return;
			}
		}

		//if it makes it to here then the variable doesn't have an entry in the m,emory array and so a new entry must be created
		// create new object and add it to the memory arraylist
		memory.add(new Variable(lookAhead,0));
		stack.push(("memory " + (memory.size()-1)));
		nextToken1();
	}

	private static void EQUALS() {
		// the rvalue on top of the stack is placed in the lvalue below it and both are popped
		System.out.println("Hello from EQUALS");

		int rvalue = Integer.parseInt(stack.pop());


		String variable = stack.pop();


		int index;
		if(variable.substring(0,6).equalsIgnoreCase("memory ")){
			System.out.println(variable.substring(6,7));

			index = Integer.parseInt(variable.toString().substring(7));

			System.out.println("index: " + index);

			Variable v = memory.get(index);
			v.setValue(rvalue);
		}
		else{
			System.out.println("An error has occured");
			HALT();
		}

		nextToken1();

	}

	private static void COPY() {
		// push a copy of the top value on the stack
		int s = Integer.parseInt(stack.pop());
		System.out.println("Copying " + s );

		stack.push(s + "");
		stack.push(s + "");

		nextToken1();
	}

	private static void ADD() {
		// pop the top two values off the stack, add them, and push the result
		int a = Integer.parseInt(stack.pop());
		int b = Integer.parseInt(stack.pop());
		int result = a+b;

		System.out.println(b + "+" + a + "=" + result);
		stack.push(result + "");

		nextToken1();
	}

	private static void SUB() {
		// pop the top two values off the stack, subtract them, and push the
		// result
		int a = Integer.parseInt(stack.pop());
		int b = Integer.parseInt(stack.pop());
		int result = a-b;

		System.out.println(a + "-" + b + "=" + result);
		stack.push(result + "");

		nextToken1();
	}

	private static void MPY() {
		// pop the top two values off the stack, multiply them, and push the
		// result
		int a = Integer.parseInt(stack.pop());
		int b = Integer.parseInt(stack.pop());
		int result = a*b;

		System.out.println(a + "*" + b + "=" + result);
		stack.push(result + "");

		nextToken1();
	}

	private static void DIV() {
		// pop the top two values off the stack, divide them, and push the
		// result
		int a = Integer.parseInt(stack.pop());
		int b = Integer.parseInt(stack.pop());
		int result = a/b;

		System.out.println(a + " DIV " + b + "=" + result);
		stack.push(result + "");

		nextToken1();
	}

	private static void MOD() {
		// pop the top two values off the stack, compute the modulus, and push
		// the result
		int a = Integer.parseInt(stack.pop());
		int b = Integer.parseInt(stack.pop());
		int result = a%b;

		System.out.println(a + " MOD " + b + "=" + result);
		stack.push(result + "");

		nextToken1();
	}

	private static void POW() {
		// pop the top two values off the stack, compute the exponentian
		// operation, and push the result

		int a = Integer.parseInt(stack.pop());
		int b = Integer.parseInt(stack.pop());
		int result = a^b;

		System.out.println(a + "^" + b + "=" + result);
		stack.push(result + "");

		nextToken1();
	}

	private static void HALT() {
		// stop execution
		System.out.println("Exiting");
		System.exit(0);
	}

}

class Variable{
	private String variable;
	private int value;

	public Variable(String var, int val){
		this.variable = var;
		this.value = val;
	}

	public void setVariable(String s){
		this.variable = s;
	}

	public String getVariable(){
		return this.variable;
	}

	public void setValue(int v){
		this.value = v;
	}

	public int getValue(){
		return this.value;
	}
}