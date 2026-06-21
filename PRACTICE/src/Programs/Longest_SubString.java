package Programs;

import java.util.HashSet;

public class Longest_SubString {

    public static void main(String[] args) {

        String str = "abcaab";
        System.out.println(LongestSustring(str));
    }

    static String LongestSustring(String str) {

        HashSet<Character> ch = new HashSet<>();
        StringBuilder strs = new StringBuilder();

        int highestCount = 0;
        String finalstr = "";

        for (Character s : str.toCharArray()) {

            if (!ch.add(s)) {

                // check length (NOT capacity)
                if (strs.length() > highestCount) {
                    highestCount = strs.length();
                    finalstr = strs.toString();
                }

                // reset for new substring
                ch.clear();
                strs = new StringBuilder();

                // start again from current character
                ch.add(s);
                strs.append(s);

            } else {
                strs.append(s); // FIXED
            }
        }

        // final check (important)
        if (strs.length() > highestCount) {
            finalstr = strs.toString();
        }

        return finalstr;
    }
}

