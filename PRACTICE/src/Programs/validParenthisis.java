package Programs;
import java.util.*;
public class validParenthisis {
	
	/* {}() ({()}) {}( [] */
	
	public static void main(String[] args) {
		
		Scanner sc=new Scanner(System.in);
		
		while(sc.hasNext()) {
			String input=sc.next();
			System.out.println(isBalanced(input));	
		}
		
		
	}

	public static boolean isBalanced(String input) {

		Stack<Character> stack = new Stack<>();

		for (Character ch : input.toCharArray()) {
			if (ch.equals('(') || ch.equals('{') || ch.equals('[')) {
				stack.push(ch);
			} else {
				if (stack.isEmpty()) {
					return false;
				}

				char top = stack.pop();

				if ((ch == ')' && top != '(') || (ch == '}' && top != '{') || (ch == ']' && top != '[')) {
					return false;
				}

			}
		}

		return stack.isEmpty();

	}
}
