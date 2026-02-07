import java.util.*;
/**
 * Hint class represents feedback for a password guess.
 * It stores the guessed digits and how many are correctly placed vs incorrectly placed.
 */
class Hint {
    String digits;           // The guessed digit sequence
    int wellPlaced;          // Number of digits in correct positions
    int wrongPlaced;         // Number of correct digits but wrong positions

    public Hint(String digits, int wellPlaced, int wrongPlaced) {
        this.digits = digits;
        this.wellPlaced = wellPlaced;
        this.wrongPlaced = wrongPlaced;
    }
}

/**
 * LockPuzzleEngine solves lock puzzles using backtracking.
 * Given multiple hints (guesses with feedback), it finds all possible passwords
 * that match the constraints from all hints.
 * 
 * This is commonly known as a "Master Mind" puzzle solver.
 */
class LockPuzzleEngine {

    private int length;
    private List<Hint> hints;
    private List<String> solutions = new ArrayList<>();

    /**
     * Constructor validates that all hints have the correct length.
     * 
     * @param length the password length to solve for
     * @param hints list of hint constraints
     * @throws IllegalArgumentException if any hint has wrong length
     */
    public LockPuzzleEngine(int length, List<Hint> hints) {
        this.length = length;
        this.hints = hints;

        // Validate all hints match the expected length
        for (Hint hint : hints) {
            if (hint.digits.length() != length) {
                throw new IllegalArgumentException(
                    "All hints must match password length: expected "
                    + length + " but got " + hint.digits.length()
                );
            }
        }
    }

    /**
     * Main entry point: Interactive CLI to gather puzzle constraints and solve.
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        // Get password length from user
        System.out.print("Enter password length: ");
        int length = scanner.nextInt();
        // Get number of hints/guesses from user
        System.out.print("Enter number of hints: ");
        int hintCount = scanner.nextInt();
        // Collect all hints with validation
        List<Hint> hints = new ArrayList<>();
        for (int i = 0; i < hintCount; i++) {
            System.out.println("\nHint " + (i + 1));
            String digits;
            // Validate digit input: must be correct length and numeric only
            while (true) {
                System.out.print("Digits: ");
                digits = scanner.next();
                if (digits.length() != length) {
                    System.out.println("❌ Digits must be exactly " + length + " characters long.");
                    continue;
                }
                if (!digits.matches("\\d+")) {
                    System.out.println("❌ Digits must contain only numbers.");
                    continue;
                }
                break;
            }
            System.out.print("Well placed count: ");
            int well = scanner.nextInt();
            System.out.print("Wrong placed count: ");
            int wrong = scanner.nextInt();
            hints.add(new Hint(digits, well, wrong));
        }
        scanner.close();
        LockPuzzleEngine engine = new LockPuzzleEngine(length, hints);
        List<String> solutions = engine.solve();
        System.out.println("\nPossible Solutions:");
        solutions.forEach(System.out::println);
        if (solutions.isEmpty()) {
            System.out.println("No solution found.");
        }
    }

    public List<String> solve() {
        backtrack(new StringBuilder());
        return solutions;
    }

    private void backtrack(StringBuilder current) {
        if (current.length() == length) {
            if (matchesAllHints(current.toString())) {
                solutions.add(current.toString());
            }
            return;
        }
        for (char digit = '0'; digit <= '9'; digit++) {
            current.append(digit);
            if (isStillPossible(current.toString())) {
                backtrack(current);
            }
            current.deleteCharAt(current.length() - 1);
        }
    }

    // FULL validation when complete
    private boolean matchesAllHints(String candidate) {
        for (Hint hint : hints) {
            if (!evaluate(candidate, hint)) {
                return false;
            }
        }
        return true;
    }

    // PARTIAL pruning logic
    private boolean isStillPossible(String partial) {
        for (Hint hint : hints) {
            int wellPlaced = 0;
            int possibleMatches = 0;
            for (int i = 0; i < partial.length(); i++) {
                if (partial.charAt(i) == hint.digits.charAt(i)) {
                    wellPlaced++;
                }
                if (hint.digits.indexOf(partial.charAt(i)) != -1) {
                    possibleMatches++;
                }
            }
            // Prune if we already exceed allowed well placed
            if (wellPlaced > hint.wellPlaced) {
                return false;
            }
            // Prune if possible matches exceed total allowed matches
            if (possibleMatches > (hint.wellPlaced + hint.wrongPlaced)) {
                return false;
            }
        }
        return true;
    }

    private boolean evaluate(String candidate, Hint hint) {
        int wellPlaced = 0;
        int wrongPlaced = 0;
        boolean[] usedCandidate = new boolean[length];
        boolean[] usedHint = new boolean[length];
        // Well placed
        for (int i = 0; i < length; i++) {
            if (candidate.charAt(i) == hint.digits.charAt(i)) {
                wellPlaced++;
                usedCandidate[i] = true;
                usedHint[i] = true;
            }
        }
        // Wrong placed
        for (int i = 0; i < length; i++) {
            if (usedCandidate[i]) continue;
            for (int j = 0; j < length; j++) {
                if (!usedHint[j] &&
                        candidate.charAt(i) == hint.digits.charAt(j)) {
                    wrongPlaced++;
                    usedCandidate[i] = true;
                    usedHint[j] = true;
                    break;
                }
            }
        }
        return wellPlaced == hint.wellPlaced &&wrongPlaced == hint.wrongPlaced;
    }
}
