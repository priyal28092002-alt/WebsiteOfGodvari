package Programs;


// if character reverse append char from last 
public class ReversedString_WithoutBuiltIn {

	public static void main(String[] args) {

		String p = "Hello Priyal";

		StringBuilder ss = Reverse(p);
		String Reversed = ss.toString();
		System.out.println(Reversed);
	}

	static StringBuilder Reverse(String str) {
		StringBuilder ReversedStr = new StringBuilder();
		String[] arr = str.split(" ");
		for (int i = arr.length-1; i >= 0; i--) {
			ReversedStr.append(arr[i]);
			  if (i != 0) {   // ✅ fix
				 ReversedStr.append(" ");
		        }
		}

		return ReversedStr;
	}

}
