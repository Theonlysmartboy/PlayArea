import java.util.*;

/**
 * Hint class represents feedback for a password guess.
 * It stores the guessed digits and how many are correctly placed vs incorrectly
 * placed.
 */
class Hint {
    String digits; // The guessed digit sequence
    int wellPlaced; // Number of digits in correct positions
    int wrongPlaced; // Number of correct digits but wrong positions

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
     * @param hints  list of hint constraints
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
                                + length + " but got " + hint.digits.length());
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
            // Get well placed and wrong placed counts
            System.out.print("Well placed count: ");
            int well = scanner.nextInt();
            System.out.print("Wrong placed count: ");
            int wrong = scanner.nextInt();
            hints.add(new Hint(digits, well, wrong));
        }
        // Close scanner to prevent resource leak
        scanner.close();
        // Create engine and solve the puzzle using the provided hints and display the
        // results
        LockPuzzleEngine engine = new LockPuzzleEngine(length, hints);
        List<String> solutions = engine.solve();
        System.out.println("\nPossible Solutions:");
        solutions.forEach(System.out::println);
        if (solutions.isEmpty()) {
            System.out.println("No solution found.");
        }
    }

    /**
     * Main method to solve the puzzle using backtracking.
     * 
     * @return list of all valid solutions
     */
    public List<String> solve() {
        backtrack(new StringBuilder());
        return solutions;
    }

    /**
     * Recursive backtracking algorithm to build candidate passwords digit by digit
     * and validate against hints.
     * Uses pruning to skip invalid candidates early, improving efficiency.
     * 
     * @param current the partially built password candidate
     */
    private void backtrack(StringBuilder current) {
        // Base case: if we have built a full candidate, validate it against all hints
        if (current.length() == length) {
            // If candidate matches all hints, add to solutions
            if (matchesAllHints(current.toString())) {
                solutions.add(current.toString());
            }
            return;
        }
        // Recursive case: try all digits from 0 to 9 for the current position
        for (char digit = '0'; digit <= '9'; digit++) {
            current.append(digit);
            // Pruning: only continue if the current partial candidate can still potentially
            // match all hints
            if (isStillPossible(current.toString())) {
                backtrack(current);
            }
            // Backtrack: remove the digit we just added to try the next one
            current.deleteCharAt(current.length() - 1);
        }
    }

    /**
     * Full Validation: checks if a complete candidate password matches all hints.
     * Used when we have a full candidate to verify against the constraints provided
     * by the hints.
     * 
     * @param candidate the complete password candidate to validate
     * @return true if candidate matches all hints, false otherwise
     */
    private boolean matchesAllHints(String candidate) {
        for (Hint hint : hints) {
            if (!evaluate(candidate, hint)) {
                return false;
            }
        }
        return true;
    }

    /**
     * PARTIAL pruning logic: early rejection of partial candidates that cannot
     * possibly satisfy the hints.
     * This is a critical optimization to reduce the search space and improve
     * performance.
     * 
     * @param partial the partially build password candidate to evaluate against the
     *                hints for pruning
     * @return true if the partial candidate is still potentially valid, false
     *         otherwise
     */
    private boolean isStillPossible(String partial) {
        for (Hint hint : hints) {
            int wellPlaced = 0;
            int possibleMatches = 0;
            // Count exact matches (well placed) and potential matches (digits that exist in
            // the hint but may not be well placed)
            for (int i = 0; i < partial.length(); i++) {
                if (partial.charAt(i) == hint.digits.charAt(i)) {
                    wellPlaced++;
                }
                if (hint.digits.indexOf(partial.charAt(i)) != -1) {
                    possibleMatches++;
                }
            }
            // Prune: we already exceed allowed well placed digits for this hint
            if (wellPlaced > hint.wellPlaced) {
                return false;
            }
            // Prune: if possible matches exceed total allowed matches
            if (possibleMatches > (hint.wellPlaced + hint.wrongPlaced)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Evaluates a complete candidate password against a single hint to check if it
     * satisfies the constraints.
     * This is the core logic that checks if a candidate password matches the
     * feedback provided by a
     * Counts well placed and wrong placed digits according to the hint and compares
     * with the expected counts.
     * 
     * @param candidate the complete password candidate to evaluate
     * @param hint      the hint to evaluate against
     * @return true if candidate matches the hint's constraints, false otherwise
     */
    private boolean evaluate(String candidate, Hint hint) {
        int wellPlaced = 0;
        int wrongPlaced = 0;
        // To avoid counting the same digit multiple times, we track which positions in
        // the candidate and hint have been used for well placed and wrong placed
        // matches.
        boolean[] usedCandidate = new boolean[length];
        boolean[] usedHint = new boolean[length];
        // First pass: count well placed digits and mark them as used
        for (int i = 0; i < length; i++) {
            if (candidate.charAt(i) == hint.digits.charAt(i)) {
                wellPlaced++;
                usedCandidate[i] = true; // Mark candidate digit as used
                usedHint[i] = true; // Mark hint digit as used
            }
        }
        // Second pass: count wrong placed digits (correct digit but in wrong position)
        for (int i = 0; i < length; i++) {
            if (usedCandidate[i])
                continue; // Skip already matched digits
            // Try to find this candidate digit elsewhere in hint
            for (int j = 0; j < length; j++) {
                if (!usedHint[j] &&
                        candidate.charAt(i) == hint.digits.charAt(j)) {
                    wrongPlaced++;
                    usedCandidate[i] = true;
                    usedHint[j] = true;
                    break; // Found one match, move to next candidate digit
                }
            }
        }
        // Verify the counts match the hint exactly
        return wellPlaced == hint.wellPlaced && wrongPlaced == hint.wrongPlaced;
    }
}
