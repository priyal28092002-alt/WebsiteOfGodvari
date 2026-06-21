package Programs;

import java.util.ArrayList;
import java.util.List;

public class ReverseListByKPosition {

	public static void main(String[] args) {

		int[] arr = { 1, 2, 3, 4, 5 };
		int k = 3;

		List<Integer> list = arrayReverseByK(arr, k);

		System.out.println(list.toString());

	}

	static List<Integer> arrayReverseByK(int[] arr, int l) {

		List<Integer> reversed = new ArrayList<>();

		for (int i = l; i < arr.length; i++) {
			reversed.add(arr[i]);
		}

		for (int i = 0; i < l; i++) {
			reversed.add(arr[i]);
		}

		return reversed;

	}

}
