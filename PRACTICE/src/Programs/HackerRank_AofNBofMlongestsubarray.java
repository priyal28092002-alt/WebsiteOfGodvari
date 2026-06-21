package Programs;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

/*8 3
5 1 8 1 1 4 3 3
1 2 3*/
public class HackerRank_AofNBofMlongestsubarray {

	public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int N = sc.nextInt(); // size of A
        int M = sc.nextInt(); // size of B

        int[] A = new int[N];
        for (int i = 0; i < N; i++) {
            A[i] = sc.nextInt();
        }

        Set<Integer> Bset = new HashSet<>();
        for (int i = 0; i < M; i++) {
            Bset.add(sc.nextInt());
        }

        System.out.println(longestValidSubarray(A, Bset));
    }

    static int longestValidSubarray(int[] A, Set<Integer> Bset) {
        int maxLen = 0;

        for (int i = 0; i < A.length; i++) {
            Map<Integer, Integer> freq = new HashMap<>();

            for (int j = i; j < A.length; j++) {
                freq.put(A[j], freq.getOrDefault(A[j], 0) + 1);

                if (isValid(freq, Bset)) {
                    maxLen = Math.max(maxLen, j - i + 1);
                }
            }
        }
        return maxLen;
    }

    static boolean isValid(Map<Integer, Integer> freq, Set<Integer> Bset) {
        for (int x : Bset) {
            if (freq.containsKey(x) && freq.get(x) % 2 != 0) {
                return false;
            }
        }
        return true;
    }
}
