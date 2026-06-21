package Programs;

public class ArmstrongNumbers {

    public static void main(String[] args) {

        int[] cube = new int[10];
        for (int i = 0; i <= 9; i++) {
            cube[i] = i * i * i;
        }

        for (int i = 1; i <= 1000; i++) {
            int num = i;
            int sum = 0;

            while (num > 0) {
                sum += cube[num % 10];
                num /= 10;
            }

            if (sum == i) {
                System.out.println(i);
            }
        }
    }
}