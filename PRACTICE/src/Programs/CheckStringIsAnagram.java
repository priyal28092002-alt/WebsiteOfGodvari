package Programs;

import java.util.HashMap;

public class CheckStringIsAnagram {

	public static void main(String[] args) {

		String s1 = "triangle";
		String s2 = "integral";

       System.out.println(checkStringIsAnagram(s1,s2));
	}

	static boolean checkStringIsAnagram(String str1, String str2) {

	    if (str1.length() != str2.length())
	        return false;

	    HashMap<Character, Integer> map = new HashMap<>();

	    // Count characters from first string
	    for (char ch : str1.toCharArray()) {
	        map.put(ch, map.getOrDefault(ch, 0) + 1);
	    }

	    // Reduce count using second string
	    for (char ch : str2.toCharArray()) {
	        if (!map.containsKey(ch))
	            return false;

	        map.put(ch, map.get(ch) - 1);

	        if (map.get(ch) == 0)
	            map.remove(ch);
	    }

	    // If map is empty → anagram
	    return map.isEmpty();
	}

}
