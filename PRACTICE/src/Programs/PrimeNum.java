package Programs;

public class PrimeNum {

    public static void main(String[] args) {

        int num = 11;

        if (isPrime(num)) {
            System.out.println("Number is prime");
        } else {
            System.out.println("Number is not prime");
        }
    }

    static boolean isPrime(int n) {

        if (n <= 1) return false;   // 0,1 not prime
        if (n == 2) return true;    // 2 is prime
        if (n % 2 == 0) return false; // even numbers not prime

        for (int i = 3; i * i <= n; i += 2) {
            if (n % i == 0) {
                return false;
            }
        }
        return true;
    }
}
