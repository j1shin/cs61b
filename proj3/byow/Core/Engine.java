package byow.Core;

import byow.TileEngine.TERenderer;
import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Out;
import edu.princeton.cs.algs4.StdDraw;

import java.awt.*;

public class Engine {
    TERenderer ter = new TERenderer();
    /* Feel free to change the width and height. */
    public static final int WIDTH = 100;
    public static final int HEIGHT = 53;
    public static char last_key;
    public static String all_inputs = "";
    public static InstantiateWorld world;
    public static TETile[][] finalWorldFrame;

    /**
     * Method used for exploring a fresh world. This method should handle all inputs,
     * including inputs from the main menu.
     */
    public void interactWithKeyboard() {
        StdDraw.setCanvasSize(WIDTH * 16, HEIGHT * 16);
        Font font = new Font("Monaco", Font.BOLD, 30);
        StdDraw.setFont(font);
        StdDraw.setXscale(0, WIDTH * 16);
        StdDraw.setYscale(0, HEIGHT * 16);
        StdDraw.clear(Color.BLACK);
        StdDraw.enableDoubleBuffering();

        introFrame();
        String writtenString = findNextLetter();
        if (writtenString.equals("n")) {
            String fullInputString = writtenString + currentSeed() + "s";
            startGame(fullInputString, false);
        } else if (writtenString.equals("l")) {
            In in = new In("savefile.txt");
            interactWithInputString(in.readString());
        } else if (writtenString.equals("q")) {
            System.exit(0);
        } else if (writtenString.equals("r")) {
            In in = new In("savefile.txt");
            startGame(in.readString(), true);

        }
    }

    /**
     * Method used for autograding and testing your code. The input string will be a series
     * of characters (for example, "n123sswwdasdassadwas", "n123sss:q", "lwww". The engine should
     * behave exactly as if the user typed these characters into the engine using
     * interactWithKeyboard.
     *
     * Recall that strings ending in ":q" should cause the game to quite save. For example,
     * if we do interactWithInputString("n123sss:q"), we expect the game to run the first
     * 7 commands (n123sss) and then quit and save. If we then do
     * interactWithInputString("l"), we should be back in the exact same state.
     *
     * In other words, running both of these:
     *   - interactWithInputString("n123sss:q")
     *   - interactWithInputString("lww")
     *
     * should yield the exact same world state as:
     *   - interactWithInputString("n123sssww")
     *
     * @param input the input string to feed to your program
     * @return the 2D TETile[][] representing the state of the world
     */
    public TETile[][] interactWithInputString(String input) {
        // TODO: Fill out this method so that it run the engine using the input
        // passed in as an argument, and return a 2D tile representation of the
        // world that would have been drawn if the same inputs had been given
        // to interactWithKeyboard().
        //
        // See proj3.byow.InputDemo for a demo of how you can make a nice clean interface
        // that works for many different input types.

        TETile[][] finalWorldFrame = startGame(input, false);

        return finalWorldFrame;
    }

    private void introFrame() {
        StdDraw.clear(Color.BLACK);
        StdDraw.setPenColor(StdDraw.WHITE);
        StdDraw.text(WIDTH * 8, HEIGHT * 12, "JOURNEY OF YASUO");
        StdDraw.text(WIDTH * 8, HEIGHT * 5, "New Game (N)");
        StdDraw.text(WIDTH * 8, HEIGHT * 4, "Load Game (L)");
        StdDraw.text(WIDTH * 8, HEIGHT * 3, "Replay Game (R)");
        StdDraw.text(WIDTH * 8, HEIGHT * 2, "Quit (Q)");
        StdDraw.show();
    }

    private String findNextLetter() {
        while (true) {
            if (StdDraw.hasNextKeyTyped()) {
                char current = StdDraw.nextKeyTyped();
                if (current == 'n' || current == 'l' || current == 'q' || current == 'r') {
                    all_inputs = all_inputs + current;
                    return Character.toString(current);
                }
            }
        }
    }
    private String currentSeed() {
        String seed = "";
        char total = 0;
        while (total != 's') {
            enterSeedFrame(seed);
            if (StdDraw.hasNextKeyTyped()) {
                total = StdDraw.nextKeyTyped();
                seed += total;
            }
        }
        all_inputs = all_inputs + seed;
        return seed.substring(0, seed.length() -1);
    }
    private void enterSeedFrame(String seed) {
        StdDraw.clear(Color.BLACK);
        StdDraw.setPenColor(Color.WHITE);
        StdDraw.text(WIDTH * 8, HEIGHT * 12, "Enter Seed");
        StdDraw.text(WIDTH * 8, HEIGHT * 9, "SEED:" + seed);
        StdDraw.show();
    }

    /** runs all the procedures to create the world and let the player move around */
    private TETile[][] startGame(String string, boolean replay) {

        String[] string_input = string.split("[A-Za-z]", 3);
        Long seed = Long.parseLong(string_input[1]);

        buildWorld(seed);
        for (char c : string_input[2].toCharArray()) {
            char key = c;
            WASD(world, finalWorldFrame, key);
            if (replay == true) {
                ter.renderFrame(finalWorldFrame);
            }
        }

        boolean maybeTrue = true;
        while (maybeTrue) {
            if (StdDraw.hasNextKeyTyped()) {
                char key = StdDraw.nextKeyTyped();
                maybeTrue = WASD(world, finalWorldFrame, key);
            }
            ter.renderFrame(finalWorldFrame);
            drawHUDFrame();
        }
        save();
        return finalWorldFrame;
    }

    private boolean WASD(InstantiateWorld world, TETile[][] finalWorldFrame, char key) {
        all_inputs = all_inputs + key;
        if (key == ':') {
            last_key = key;
        } else if (last_key == ':' && key == 'Q') {
            return false;
        } else if (key == 'w') {
            int x = world.avatar.xpos;
            int y = world.avatar.ypos;
            if (finalWorldFrame[x][y + 1] == Tileset.FLOOR) {
                finalWorldFrame[x][y] = Tileset.FLOOR;
                finalWorldFrame[x][y + 1] = Tileset.AVATAR;
                world.avatar = new TileCoords(x, y + 1);
            }
        } else if (key == 'a') {
            int x = world.avatar.xpos;
            int y = world.avatar.ypos;
            if (finalWorldFrame[x - 1][y] == Tileset.FLOOR) {
                finalWorldFrame[x][y] = Tileset.FLOOR;
                finalWorldFrame[x - 1][y] = Tileset.AVATAR;
                world.avatar = new TileCoords(x - 1, y);
            }
        } else if (key == 's') {
            int x = world.avatar.xpos;
            int y = world.avatar.ypos;
            if (finalWorldFrame[x][y - 1] == Tileset.FLOOR) {
                finalWorldFrame[x][y] = Tileset.FLOOR;
                finalWorldFrame[x][y - 1] = Tileset.AVATAR;
                world.avatar = new TileCoords(x, y - 1);
            }
        } else if (key == 'd') {
            int x = world.avatar.xpos;
            int y = world.avatar.ypos;
            if (finalWorldFrame[x + 1][y] == Tileset.FLOOR) {
                finalWorldFrame[x][y] = Tileset.FLOOR;
                finalWorldFrame[x + 1][y] = Tileset.AVATAR;
                world.avatar = new TileCoords(x + 1, y);
            }
        }
        return true;
    }

    private void save() {
        Out output = new Out("savefile.txt");
        output.println(all_inputs);
        System.exit(0);
    }

    private void buildWorld(Long seed) {
        ter.initialize(WIDTH, HEIGHT);
        world = new InstantiateWorld(seed);
        finalWorldFrame = world.frame;
        ter.renderFrame(finalWorldFrame);
    }

    private void drawHUDFrame() {
        StdDraw.setPenColor(StdDraw.WHITE);

        int xCoord = Math.min((int) StdDraw.mouseX(), WIDTH);
        int yCoord = Math.min((int) StdDraw.mouseY(), HEIGHT);
        if (xCoord >= 0 && xCoord < WIDTH && yCoord >= 0 && yCoord < HEIGHT) {

            TETile mouseTile = this.finalWorldFrame[xCoord][yCoord];
            StdDraw.text(5, HEIGHT - 1, "MOUSE OVER: ");
            StdDraw.text(10, HEIGHT - 1, mouseTile.description());
        }
        StdDraw.text(8, HEIGHT - 2, "W-Up A-Left S-Down D-Right");
        StdDraw.show();

    }
}
