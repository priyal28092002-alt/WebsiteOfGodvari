package com.example.JavaTricksShowcase;

/**
 * Labeled break and continue for nested loops.
 */
public class LabeledBreakContinueDemo {
    public static void main(String[] args) {
        example1_labeledBreak();
        example2_labeledContinue();
        example3_breakFromNestedLoop();
        example4_labeledBlock();
        challenge();
    }

    static void example1_labeledBreak() {
        System.out.println("\n[LabeledBreakContinueDemo - Example 1] Labeled break");
        outer:
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (i == 1 && j == 1) break outer;
                System.out.print("(" + i + "," + j + ") ");
            }
        }
        System.out.println("\nPrediction: (0,0) (0,1) (0,2) (1,0) ✅  Actual: as printed ✅");
        System.out.println("Key Takeaway: Labeled break exits the labeled loop, not just inner loop.");
    }

    static void example2_labeledContinue() {
        System.out.println("\n[LabeledBreakContinueDemo - Example 2] Labeled continue");
        outer:
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (j == 1) continue outer;
                System.out.print("(" + i + "," + j + ") ");
            }
        }
        System.out.println("\nPrediction: (0,0) (1,0) (2,0) ✅  Actual: as printed ✅");
        System.out.println("Key Takeaway: Labeled continue skips to next iteration of labeled loop.");
    }

    static void example3_breakFromNestedLoop() {
        System.out.println("\n[LabeledBreakContinueDemo - Example 3] Finding element in 2D array");
        int[][] matrix = {{1,2,3}, {4,5,6}, {7,8,9}};
        int target = 5;
        boolean found = false;
        search:
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                if (matrix[i][j] == target) {
                    System.out.println("Found " + target + " at [" + i + "][" + j + "]");
                    found = true;
                    break search;
                }
            }
        }
        System.out.println("Prediction: Found 5 at [1][1] ✅  Actual: as printed ✅");
        System.out.println("Key Takeaway: Labeled break is cleaner than flag variables for nested loops.");
    }

    static void example4_labeledBlock() {
        System.out.println("\n[LabeledBreakContinueDemo - Example 4] Labeled block (not loop)");
        block: {
            System.out.println("Before break");
            if (true) break block;
            System.out.println("After break"); // not executed
        }
        System.out.println("After block");
        System.out.println("Prediction: Before break, After block ✅  Actual: as printed ✅");
        System.out.println("Key Takeaway: Labels work with any block, not just loops.");
    }

    static void challenge() {
        System.out.println("\n[LabeledBreakContinueDemo - Challenge] What prints?");
        System.out.println("Hidden");
        // outer: for(int i=0;i<2;i++) { for(int j=0;j<2;j++) { if(i==j) continue outer; System.out.print(i+""+j+" "); } }
        System.out.println("Interview Hint: Labeled statements are rare but appear in interviews.");
    }
}
