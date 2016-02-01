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
import java.util.StringTokenizer;

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
	private static lexical_analizer lex = new lexical_analizer();
	
	public static void main(String args[]) {
		System.out.println("Hello from main");
		//lexical analyzer
		
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
			lex.nextToken();
			lookAhead = lex.currentToken();
		}	
		else{
			System.out.println("The token does not match, exiting...");
			System.exit(0);
		}
	}
	
	//start
	private static void program(){
		// check BEGIN symbol
		Match(BEGINSym);
		
		// Proceed to parse stmt_List
		stmt_List();
		
		// check END Symbol
		Match(ENDSym);
	}
	
	private static void stmt(){
		// <id> := <expr>
		id();
		Match(EQUALSSym);
		expr();
		
		// epsilon
		
	}
	
	private static void stmt_List(){//********************************************************************************
		// if <stmt_list> ; <stmt> ************ need to remove left recursion 
		if(){
			
		}
		
		// else if <stmt>
		else{
			stmt();
		}
		
		// else error
		
	}
	
	private static void expr(){
		// <expr> + <term>
		if(lookAhead.equals(ADDSym)){
			expr();
			Match(ADDSym);
			term();
		}
		
		// <expr> - <term>
		else if(lookAhead == SUBSym){
			expr();
			Match(SUBSym);
			term();
		}
		
		// <term>
		else{
			term();
		}
		
		// error
		
	}
	
	private static void term(){
		// <term> * <factor>
		if(lookAhead == MULSym){
			term();
			Match(MULSym);
			factor();
		}
		
		// <term> DIV <factor>
		else if(lookAhead == DIVSym){
			term();
			Match(DIVSym);
			factor();
		}
		
		// <term> MOD <factor>
		else if(lookAhead == MODSym){
			term();
			Match(MODSym);
			factor();
		}
		
		// <factor>
		else{
			factor();
		}
		
		// error
		
	}
	
	private static void factor(){
		// <primary> ^ <factor>
		if(lookAhead == POWERSym){
			primary();
			Match(POWERSym);
			factor();
		}
		
		// <primary>
		else {
			primary();
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
		if(lookAhead.substring(0, 0) == "temp"){
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
		
		//check if variable already exists
			
		//if it does update vlaue
		if(lookAhead == "derp"){
			
		}
		//else create new object and add it to the memory arraylist
		else{
			memory.add(new Variable(lookAhead,0));
		}				
	}
	
	// Instructions

	private static void PUSH(String s) {
		// push s (an integer constant) on the stack
		System.out.println("Hello from PUSH");
		
		stack.push(s + "");
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
	}

	private static void RVALUE(String var) {//***********************************************************************
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
	}

	private static void LVALUE(String l) {//********************************************************************************
		// push the address of the variable l
		System.out.println("Hello from LVALUE");		
		
		Variable v;
		for(int i=0; i < memory.size(); i++){
			v = memory.get(i);
			
			if(v.getVariable() == l){
				PUSH("memory " + i);
			}
			
		}
	}

	private static void EQUALS() {//***********************************************************************************
		// the rvalue on top of the stack is placed in the lvalue below it and both are popped
		System.out.println("Hello from EQUALS");
		
		int rvalue = Integer.parseInt(stack.pop());
		String variable = stack.pop();
		int index;
		if(variable.substring(0,6).equalsIgnoreCase("memory ")){
			index = Integer.parseInt(variable.substring(7));
		}
		else{
			System.out.println("An error has occured");
			HALT();
		}
		
		
		
		Variable v = memory.get(index);
		v.setValue(rvalue);

	}

	private static void COPY() {
		// push a copy of the top value on the stack
		int s = Integer.parseInt(stack.pop());
		System.out.println("Copying " + s );

		stack.push(s + "");
		stack.push(s + "");
	}

	private static void ADD() {
		// pop the top two values off the stack, add them, and push the result
		int a = Integer.parseInt(stack.pop());
		int b = Integer.parseInt(stack.pop());
		int result = a+b;
		
		System.out.println(a + "+" + b + "=" + result);
		stack.push(result + "");
		
	}

	private static void SUB() {
		// pop the top two values off the stack, subtract them, and push the
		// result
		int a = Integer.parseInt(stack.pop());
		int b = Integer.parseInt(stack.pop());
		int result = a-b;
		
		System.out.println(a + "-" + b + "=" + result);
		stack.push(result + "");
	}

	private static void MPY() {
		// pop the top two values off the stack, multiply them, and push the
		// result
		int a = Integer.parseInt(stack.pop());
		int b = Integer.parseInt(stack.pop());
		int result = a*b;
		
		System.out.println(a + "*" + b + "=" + result);
		stack.push(result + "");
	}

	private static void DIV() {
		// pop the top two values off the stack, divide them, and push the
		// result
		int a = Integer.parseInt(stack.pop());
		int b = Integer.parseInt(stack.pop());
		int result = a/b;
		
		System.out.println(a + " DIV " + b + "=" + result);
		stack.push(result + "");
	}

	private static void MOD() {
		// pop the top two values off the stack, compute the modulus, and push
		// the result
		int a = Integer.parseInt(stack.pop());
		int b = Integer.parseInt(stack.pop());
		int result = a%b;
		
		System.out.println(a + " MOD " + b + "=" + result);
		stack.push(result + "");
	}

	private static void POW() {
		// pop the top two values off the stack, compute the exponentian
		// operation, and push the result
		
		int a = Integer.parseInt(stack.pop());
		int b = Integer.parseInt(stack.pop());
		int result = a^b;
		
		System.out.println(a + "^" + b + "=" + result);
		stack.push(result + "");
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