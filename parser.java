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
	public static final String BEGINSym = "BEGIN";
	public static final String ENDSym = "END";
	public static final String EQUALSSym = ":=";
	public static final String EPSILONSym = "";
	public static final String SEMICOLONSym = ";";
	public static final String ADDSym = "+";
	public static final String SUBSym = "-";
	public static final String MULSym = "*";
	public static final String DIVSym = "DIV";
	public static final String MODSym = "MOD";
	public static final String POWERSym = "^";
	// public final String = ;
	
	//Holds next token
	public static String lookAhead= "";
	
	//initialize stack
	public static Stack<Integer> stack = new Stack<Integer>();

	public static void main(String args[]) {
		System.out.println("Hello from main");
		//lexical analyzer
		//compiler_assignment arrList = new compiler_assignment();
	}

	/*
	 * Grammar 
	 * <program> ->BEGIN <stmt_list> END <stmt> -> <id> := <expr> | ε
	 * <stmt_list> -> <stmt_list> ; <stmt> | <stmt> <expr> -> <expr> + <term> |
	 * <expr> - <term> | <term> <term> -> <term> * <factor> | <term> DIV
	 * <factor> | <term> MOD <factor> | <factor> <factor> -> <primary> ^
	 * <factor> | <primary> <primary> -> <id> | <num> | ( <expr> )
	 */

	
	public static void Match(String t){
		//compares Symbol with token stored in lookAhead, if they match advance look ahead to next token
		//else there is an error
		if(lookAhead == t){
			//LookAhead == nexttoken();
		}	
		else{
			System.out.println("The token does not match, exiting...");
			System.exit(0);
		}
	}
	
	
	

	// Instructions

	public static void PUSH(int s) {
		// push v (an integer constant) on the stack
		System.out.println("Hello from PUSH");
		
		stack.push(s);
	}
	
	public static void POP() {
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

	public static void RVALUE() {
		// push the contents of variable l
		System.out.println("Hello from RVALUE");
	}

	public static void LVALUE() {
		// push the address of the variable l
		System.out.println("Hello from LVALUE");
	}

	public static void EQUALS() {
		// the rvalue on top of the stack is place in the lvalue below it and
		// both are popped
		System.out.println("Hello from EQUALS");
	}

	public static void COPY() {
		// push a copy of the top value on the stack
		int s = stack.pop();
		System.out.println("Copying " + s );

		stack.push(s);
		stack.push(s);
	}

	public static void ADD() {
		// pop the top two values off the stack, add them, and push the result
		int a = stack.pop();
		int b = stack.pop();
		int result = a+b;
		
		System.out.println(a + "+" + b + "=" + result);
		stack.push(result);
		
	}

	public static void SUB() {
		// pop the top two values off the stack, subtract them, and push the
		// result
		int a = stack.pop();
		int b = stack.pop();
		int result = a-b;
		
		System.out.println(a + "-" + b + "=" + result);
		stack.push(result);
	}

	public static void MPY() {
		// pop the top two values off the stack, multiply them, and push the
		// result
		int a = stack.pop();
		int b = stack.pop();
		int result = a*b;
		
		System.out.println(a + "*" + b + "=" + result);
		stack.push(result);
	}

	public static void DIV() {
		// pop the top two values off the stack, divide them, and push the
		// result
		int a = stack.pop();
		int b = stack.pop();
		int result = a/b;
		
		System.out.println(a + " DIV " + b + "=" + result);
		stack.push(result);
	}

	public static void MOD() {
		// pop the top two values off the stack, compute the modulus, and push
		// the result
		int a = stack.pop();
		int b = stack.pop();
		int result = a%b;
		
		System.out.println(a + " MOD " + b + "=" + result);
		stack.push(result);
	}

	public static void POW() {
		// pop the top two values off the stack, compute the exponentian
		// operation, and push the result
		
		int a = stack.pop();
		int b = stack.pop();
		int result = a^b;
		
		System.out.println(a + "^" + b + "=" + result);
		stack.push(result);
	}

	public static void HALT() {
		// stop execution
		System.out.println("Exiting");
		System.exit(0);
	}

}
