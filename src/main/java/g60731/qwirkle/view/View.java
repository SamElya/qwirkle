package g60731.qwirkle.view;

import g60731.qwirkle.model.*;

import java.util.ArrayList;
import java.util.List;
import static g60731.qwirkle.model.CstGame.*;

/**
 * Display of different element of the game
 */
public class View {

    /**
     * Display a tile with the corresponding color and shape
     */
    private static void displayColor(Tile tile) {

        String colorReset = "\u001B[0m";
        String color = switch (tile.color()) {

            case BLUE -> "\u001B[34m";
            case RED -> "\u001B[31m";
            case GREEN -> "\u001B[32m";
            case ORANGE -> "\u001B[38;5;208m";
            case YELLOW -> "\u001B[33m";
            //case PURPLE -> "\u001B[35m";
        };

        String shape = switch (tile.shape()) {

            case CROSS -> "  X ";
            case SQUARE -> " [] ";
            case ROUND -> "  O ";
            case STAR -> "  * ";
            case PLUS -> "  + ";
           // case DIAMOND -> " <> ";
        };

        System.out.print(color + shape + colorReset);
}

    /**
     * Search the furthest tiles
     */
    private static void gridInterval(GridView gridView,
                                     BoxOfPositions positions) {

        int minRow = GRIDBEGIN;
        int minCol = GRIDBEGIN;
        int maxRow = GRIDBEGIN;
        int maxCol = GRIDBEGIN;
        for (int row = GRIDBEGIN; row < GRIDEND; row++) {
            for (int col = GRIDBEGIN; col < GRIDEND; col++) {
                if (gridView.get(row, col) != null) {
                    maxRow = row;
                    if (col > maxCol) {

                        maxCol = col;
                    }
                    if (minRow == GRIDBEGIN || minRow > row) {

                        minRow = row;
                    }
                    if (minCol == GRIDBEGIN || minCol > col) {

                        minCol = col;
                    }
                }
            }
        }
        positions.add(minRow, minCol);
        positions.add(maxRow, maxCol);
    }

    /**
     *  Display the format of grid
     */
    private static void displayGrid(GridView gridView, BoxOfPositions positions) {

        for (int row = positions.getRow(0) - 2;
             row <= positions.getRow(1) + 2; row++) {
            System.out.printf("%02d |", row);

            for (int col = positions.getCol(0) - 2;
                 col <= positions.getCol(1) + 2; col++) {

                if (gridView.get(row, col) == null){
                    System.out.print("    ");
                }
                else {
                    displayColor(gridView.get(row, col));
                }
            }
            System.out.println();
        }
        System.out.println();
        System.out.print("    ");
        for (int nCol = positions.getCol(0) - 2;
             nCol <= positions.getCol(1) + 2; nCol++) {
            System.out.printf(" %02d ", nCol);
        }
        System.out.println();
    }

    /**
     * Give interval if the grid has tile on it,
     * else it gives the initial position of the game
     */
    private static void createInterval(GridView gridView, BoxOfPositions positions) {

        if (gridView.isEmpty()) {

            positions.add(DEFAULTPOS, DEFAULTPOS);
            positions.add(DEFAULTPOS, DEFAULTPOS);
        }
        else {

            gridInterval(gridView, positions);
        }
    }

    /**
     * Display the grid
     * @param gridView  The non-modifiable grid
     */
    public static void display(GridView gridView) {

        BoxOfPositions interval = new BoxOfPositions();

        createInterval(gridView, interval);
        displayGrid(gridView, interval);
    }

    /**
     * Display the player names and scores
     * and who has the highest score
     * @param playersView   An unmodifiable list of Players
     */
    public static void displayPlayers(PlayersView playersView) {

        System.out.println();
        System.out.println("SCORE: ");
        for (int i = 0; i <
                playersView.getNamesPlayers().size();
                i++) {

            System.out.println("Name: " +
                    playersView.getNamesPlayers().get(i) +
                    "     Score: " +
                    playersView.getScoresPlayer().get(i));
        }

        System.out.println("Winner: " + playersView.
                getNamesPlayers().get(0));
        System.out.println("Score: " + playersView.
                getScoresPlayer().get(0));

    }

    /**
     * Display the player's hand
     */
    private static void displayColorHand(Tile tile) {

        String colorReset = "\u001B[0m";
        String color = switch (tile.color()) {

            case BLUE -> "\u001B[34m";
            case RED -> "\u001B[31m";
            case GREEN -> "\u001B[32m";
            case ORANGE ->   "\u001B[38;5;208m";
            case YELLOW -> "\u001B[33m";
            //case PURPLE -> "\u001B[35m";
        };

        String shape = switch (tile.shape()) {

            case CROSS -> "X ";
            case SQUARE -> "[] ";
            case ROUND -> "O ";
            case STAR -> "* ";
            case PLUS -> "+ ";
           // case DIAMOND -> "<> ";
        };

        System.out.print(color + shape + colorReset);
    }

    /**
     * Display the player name and its hand
     * @param name  The player's name
     * @param hand  The player's hand
     * @param score  The player's score
     */
    public static void display(String name, List<Tile> hand, int score) {

        System.out.println();
        System.out.print("Turn of: ");
        System.out.println(name);
        System.out.print("Score: ");
        System.out.println(score);
        System.out.println("Your hand: ");
        System.out.print(" ");
        for (Tile tile : hand) {
            displayColorHand(tile);
        }
        System.out.println();
        System.out.println("tile index starts from 0 to 5");

    }

    /**
     * Display what a player can do
     */
    public static void displayHelp() {
        String GREEN = "\u001B[32m";
        String RED = "\u001B[31m";
        String colorReset = "\u001B[0m";

        System.out.println();
        System.out.println(GREEN + "Q W I R K L E" + colorReset);
        System.out.println("Qwirkle command:");
        System.out.println("- play 1 tile : " + RED + "o" + colorReset +
                " <row> <col> <i>");
        System.out.println("- play line: "+ RED + "l" + colorReset +
                " <row> <col> " +
                "<direction> <i1> [<i2>]");
        System.out.println("- play plic-ploc : "+ RED + "m" + colorReset +
                " <row1> <col1> <i1> [<row2> <col2> <i2>]");
        System.out.println("- play first : " + RED + "f" + colorReset +
                " [<direction>] <f1> [<f2> …]");
        System.out.println("- pass : " + RED + "p" + colorReset);
        System.out.println("- quit : " + RED + "q" + colorReset);
        System.out.println("    " + "i : index in list of tiles");
        System.out.println("    " + "d : direction in l (left), " +
                "r (right), u (up), d(down)");
        System.out.println();
    }

    /**
     * Display all filenames to select one
     * @param filenames An Arraylist of String of all filenames
     */
    public static void displayBackUp(ArrayList<String> filenames) {

        System.out.println("Select one save: ");
        for (int i = 1; i <= filenames.size(); i++) {

            System.out.println(i + ") " + filenames.get(i - 1));
        }
    }

    /**
     * Display a Save Message
     */
    public static void displaySave() {

        System.out.println("Do you want to " +
                "save your game? Y/N");
    }

    /**
     * Display an error message
     * @param message   The error message
     */
    public static void displayError(String message) {

        System.out.println(message);
    }

}
