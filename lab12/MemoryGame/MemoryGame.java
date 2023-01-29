package MemoryGame;

import byowTools.RandomUtils;
import edu.princeton.cs.introcs.StdDraw;

import java.awt.Color;
import java.awt.Font;
import java.util.Random;

public class MemoryGame {
    /** The width of the window of this game. */
    private int width;
    /** The height of the window of this game. */
    private int height;
    /** The current round the user is on. */
    private int round;
    /** The Random object used to randomly generate Strings. */
    private Random rand;
    /** Whether or not the game is over. */
    private boolean gameOver;
    /** Whether or not it is the player's turn. Used in the last section of the
     * spec, 'Helpful UI'. */
    private boolean playerTurn;
    /** The characters we generate random Strings from. */
    private static final char[] CHARACTERS = "abcdefghijklmnopqrstuvwxyz".toCharArray();
    /** Encouraging phrases. Used in the last section of the spec, 'Helpful UI'. */
    private static final String[] ENCOURAGEMENT = {"You can do this!", "I believe in you!",
                                                   "You got this!", "You're a star!", "Go Bears!",
                                                   "Too easy for you!", "Wow, so impressive!"};

    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("Please enter a seed");
            return;
        }

        long seed = Long.parseLong(args[0]);
        MemoryGame game = new MemoryGame(40, 40, seed);
        game.startGame();
    }

    public MemoryGame(int width, int height, long seed) {
        /* Sets up StdDraw so that it has a width by height grid of 16 by 16 squares as its canvas
         * Also sets up the scale so the top left is (0,0) and the bottom right is (width, height)
         */
        this.width = width;
        this.height = height;
        StdDraw.setCanvasSize(this.width * 16, this.height * 16);
        Font font = new Font("Monaco", Font.BOLD, 30);
        StdDraw.setFont(font);
        StdDraw.setXscale(0, this.width);
        StdDraw.setYscale(0, this.height);
        StdDraw.clear(Color.BLACK);
        StdDraw.enableDoubleBuffering();

        this.rand = new Random(seed);
    }

    public String generateRandomString(int n) {
        String current = "";
        for (int i = 0; i < n; i++) {
            current += CHARACTERS[rand.nextInt(25)];
        }
        return current;
    }

    public void drawFrame(String s) {
        /* Take the input string S and display it at the center of the screen,
         * with the pen settings given below. */

        //TODO: If the game is not over, display encouragement, and let the user know if they
        // should be typing their answer or watching for the next round.
        String current = (playerTurn) ? "Type!" : "Watch!";

        StdDraw.clear(Color.BLACK);
        StdDraw.setPenColor(Color.WHITE);
        Font fontBig = new Font("Monaco", Font.BOLD, 30);
        StdDraw.setFont(fontBig);
        StdDraw.text(this.width / 2, this.height / 2, s);

        StdDraw.setFont(new Font("Monaco", Font.BOLD, 15));
        StdDraw.text( width * 0.10 , 0.95 * height, "Round: " + Integer.toString(round));
        StdDraw.text( width / 2, 0.95 * height, current);
        StdDraw.text(width * 0.85, 0.95 * height, ENCOURAGEMENT[rand.nextInt(ENCOURAGEMENT.length)]);
        StdDraw.filledRectangle(width / 2, 0.92 * height, 25, 0.10);

        if (gameOver == true) {
            StdDraw.setPenColor(Color.BLACK);
            StdDraw.filledRectangle(width / 2, 0.95 * height, 25, 5);
        }
        StdDraw.show();
    }

    public void flashSequence(String letters) {
        String empty = "";
        for(int i = 0; i < letters.length(); i++) {
            String running = Character.toString(letters.charAt(i));
            drawFrame(running);
            StdDraw.pause(1000);
            StdDraw.clear();
            drawFrame(empty);
            StdDraw.pause(500);
        }
    }

    public String solicitNCharsInput(int n) {
        String current = "";
        int count = 0;
        drawFrame("");
        while (!(count == n)) {
            if (StdDraw.hasNextKeyTyped()) {
                String nextKey = Character.toString(StdDraw.nextKeyTyped());
                current += nextKey;
                drawFrame(current);
                count++;
            }
        }
        return current;
    }

    public void startGame() {
        //TODO: Set any relevant variables before the game starts
        this.gameOver = false;
        round = 1;
        int interval = 1000;

        //TODO: Establish Engine loop
        while (gameOver != true) {
            String target = generateRandomString(round);
            showRounds();
            flashSequence(target);

            playerTurn = true;
            String next = solicitNCharsInput(round);
            StdDraw.pause(interval / 4);

            if (next.equals(target)) {
                playerTurn = false;
                drawFrame(ENCOURAGEMENT[rand.nextInt(ENCOURAGEMENT.length)]);
                round++;
                StdDraw.pause(interval);

            } else {
                gameOver = true;
                drawFrame("Game Over! You made it to round: " + round);
                StdDraw.pause(interval * 2);
            }
        }
    }

    private void showRounds() {
        String currentRoundString = Integer.toString(round);
        drawFrame("Round " + currentRoundString + "!");
        StdDraw.pause(1000);
        drawFrame("");
        StdDraw.pause(1000);
    }
}
