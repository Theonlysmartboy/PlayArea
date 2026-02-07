import java.util.*;

public class LockSolver {

    static class Hint {
        String digits;
        int wellPlaced;
        int wrongPlaced;

        public Hint(String digits, int wellPlaced, int wrongPlaced) {
            this.digits = digits;
            this.wellPlaced = wellPlaced;
            this.wrongPlaced = wrongPlaced;
        }
    }

    public static void main(String[] args) {

        List<Hint> hints = new ArrayList<>();

        // From the image:
        hints.add(new Hint("682", 1, 0)); // One correct and well placed
        hints.add(new Hint("614", 0, 1)); // One correct but wrongly placed
        hints.add(new Hint("206", 0, 2)); // Two correct but wrongly placed
        hints.add(new Hint("738", 0, 0)); // Nothing correct
        hints.add(new Hint("780", 0, 1)); // One correct but wrongly placed

        for (int i = 0; i <= 999; i++) {
            String candidate = String.format("%03d", i);

            if (matchesAllHints(candidate, hints)) {
                System.out.println("Possible Password: " + candidate);
            }
        }
    }

    static boolean matchesAllHints(String candidate, List<Hint> hints) {
        for (Hint hint : hints) {
            int wellPlaced = 0;
            int wrongPlaced = 0;

            boolean[] usedCandidate = new boolean[3];
            boolean[] usedHint = new boolean[3];

            // Check well placed
            for (int i = 0; i < 3; i++) {
                if (candidate.charAt(i) == hint.digits.charAt(i)) {
                    wellPlaced++;
                    usedCandidate[i] = true;
                    usedHint[i] = true;
                }
            }

            // Check wrongly placed
            for (int i = 0; i < 3; i++) {
                if (usedCandidate[i]) continue;

                for (int j = 0; j < 3; j++) {
                    if (!usedHint[j] &&
                        candidate.charAt(i) == hint.digits.charAt(j)) {
                        wrongPlaced++;
                        usedCandidate[i] = true;
                        usedHint[j] = true;
                        break;
                    }
                }
            }

            if (wellPlaced != hint.wellPlaced ||
                wrongPlaced != hint.wrongPlaced) {
                return false;
            }
        }
        return true;
    }
}
