package byog.Core;

import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;
import edu.princeton.cs.algs4.StdDraw;

import java.awt.*;
import java.util.Random;

public class Game {
    TERenderer ter = new TERenderer();
    /* Feel free to change the width and height. */
    public static final int WIDTH = 80;
    public static final int HEIGHT = 30;

    /**
     * Method used for playing a fresh game. The game should start from the main menu.
     */
    public void playWithKeyboard() {

        while (true) {
            if (StdDraw.hasNextKeyTyped()) {
                break;
            }
        }
        char c = StdDraw.nextKeyTyped();
        if (c == 'N' || c == 'n') {
            System.exit(0);
        } else if (c == 'L' || c == 'l') {
            TETile[][] object1 = null;
            Point player1 = null;
            Point door1 = null;
            String filename = "file.ser";
            String filename1 = "file1.ser";
            String filename2 = "file2.ser";
            String filename3 = "file3.ser";
            // Deserialization
        } else if (c == 'Q' || c == 'q') {
            System.exit(0);
        }

    }

    /**
     * Method used for autograding and testing the game code. The input string will be a series
     * of characters (for example, "n123sswwdasdassadwas", "n123sss:q", "lwww". The game should
     * behave exactly as if the user typed these characters into the game after playing
     * playWithKeyboard. If the string ends in ":q", the same world should be returned as if the
     * string did not end with q. For example "n123sss" and "n123sss:q" should return the same
     * world. However, the behavior is slightly different. After playing with "n123sss:q", the game
     * should save, and thus if we then called playWithInputString with the string "l", we'd expect
     * to get the exact same world back again, since this corresponds to loading the saved game.
     *
     * @param input the input string to feed to your program
     * @return the 2D TETile[][] representing the state of the world
     */
    public TETile[][] playWithInputString(String input) {
        //
        // and return a 2D tile representation of the world that would have been
        // drawn if the same inputs had been given to playWithKeyboard().

        final long SEED = 1234567;
        final Random RANDOM = new Random(SEED);

        TETile[][] finalWorldFrame = new TETile[WIDTH][HEIGHT];
        int curX = RANDOM.nextInt(WIDTH - 2) + 1;
        int curY = RANDOM.nextInt(HEIGHT - 2) + 1;
        finalWorldFrame[curX][curY] = Tileset.FLOOR;

        for (int i = 0; i < WIDTH; i++) {
            for (int j = 0; j < HEIGHT; j++) {
                finalWorldFrame[i][j] = Tileset.NOTHING;
            }
        }

        // generate floors
        for (int i = 0; i < WIDTH * HEIGHT; i++) {
            int dirc = RANDOM.nextInt(4);
            if (curX <= 1) {
                curX++;
            } else if (curX >= WIDTH - 2) {
                curX--;
            }
            if (curY <= 1) {
                curY++;
            } else if (curY >= HEIGHT - 2) {
                curY--;
            }
            switch (dirc) {
                case 0:
                    curX--;
                case 1:
                    curX++;
                case 2:
                    curY--;
                case 3:
                    curY++;
            }
            finalWorldFrame[curX][curY] = Tileset.FLOOR;
        }

        genWalls(finalWorldFrame);

        ter.initialize(WIDTH, HEIGHT);
        ter.renderFrame(finalWorldFrame);

        return finalWorldFrame;
    }

    // generate walls against floors
    private void genWalls(TETile[][] teTiles) {
        for (int i = 1; i < WIDTH - 1; i++) {
            for (int j = 1; j < HEIGHT - 1; j++) {
                if (teTiles[i][j].equals(Tileset.FLOOR)) {
                    if (teTiles[i - 1][j].equals(Tileset.NOTHING)) {
                        teTiles[i - 1][j] = Tileset.WALL;
                    }
                    if (teTiles[i + 1][j].equals(Tileset.NOTHING)) {
                        teTiles[i + 1][j] = Tileset.WALL;
                    }
                    if (teTiles[i][j - 1].equals(Tileset.NOTHING)) {
                        teTiles[i][j - 1] = Tileset.WALL;
                    }
                    if (teTiles[i][j + 1].equals(Tileset.NOTHING)) {
                        teTiles[i][j + 1] = Tileset.WALL;
                    }
                }
            }
        }
    }
}
