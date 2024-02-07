import java.util.List;

public class Boggle {

    // File path of dictionary file
    static String dictPath = "words.txt";

    /**
     * Solves a Boggle puzzle.
     *
     * @param k The maximum number of words to return.
     * @param boardFilePath The file path to Boggle board file.
     * @return a list of words found in given Boggle board.
     *         The Strings are sorted in descending order of length.
     *         If multiple words have the same length,
     *         have them in ascending alphabetical order.
     */
    public static List<String> solve(int k, String boardFilePath) {
        validBoard(boardFilePath);
        validDict();
        validK(k);



        return null;
    }

    private static void validBoard(String boardFilePath) {
        In boardFile = new In(boardFilePath);
        String[] board = boardFile.readAllLines();
        int row = board.length;
        int col = board[0].length();
        for (String s : board) {
            if (s.length() != col) {
                throw new IllegalArgumentException();
            }
        }
    }

    private static void validDict() {
        In dict = new In(dictPath);
        if (!dict.exists()) {
            throw new IllegalArgumentException();
        }
    }

    private static void validK(int k) {
        if (k <= 0) {
            throw new IllegalArgumentException();
        }
    }
}