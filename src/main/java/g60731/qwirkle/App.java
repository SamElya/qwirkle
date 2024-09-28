package g60731.qwirkle;

import g60731.qwirkle.model.*;
import  g60731.qwirkle.view.View;

import java.util.ArrayList;
import java.util.Scanner;

public class App {

    static String RED = "\u001B[31m";
    static String colorReset = "\u001B[0m";

    /**
     * RobustReading of Y or N
     */
    private static String yesOrNo() {

        Scanner keyboard = new Scanner(System.in);

        String choice = keyboard.nextLine();
        while (!choice.equals("Y") && !choice.equals("N")) {

            View.displayError("Incorrect entry, please retry: ");
            choice = keyboard.next();
        }

        return choice;
    }

    /**
     * Save the Game
     */
    private static void saveGame(Game game) {

        Scanner keyboard = new Scanner(System.in);

        int nbSavedFile = game.fileNameSize();
        View.displaySave();
        String choice = yesOrNo();

        if (choice.equals("Y")) {

            System.out.print("Filename: ");
            String filename = keyboard.next();
            game.write(filename);
            if (game.fileNameSize() == nbSavedFile) {

                System.out.println("Saved Failed, do you want to retry ? Y/N");
                choice = yesOrNo();
                if (choice.equals("Y")) {

                    saveGame(game);
                }
            }
        }


    }

    /**
     * Read an integer robustly
     */
    private static int robustReading(String message) {

        Scanner keyboard = new Scanner(System.in);
        System.out.println(message);

        while (!keyboard.hasNextInt()) {

            keyboard.next();
            System.out.println(RED + "Please enter an int: " + colorReset);
        }

        return keyboard.nextInt();
    }

    /**
     * Ask the number of player who will play
     * and return a non-zero positive integer
     */
    private static int robustNumber(int max) {

        int nb = robustReading("Enter a number: ");
        while (nb <= 0 || nb > max) {

            System.out.println(RED + "number incorrect, please enter " +
                    "a number greater than at least 1 or " + max + "!" + colorReset);

            nb = robustReading("How many player");
        }

        return nb;
    }

    /**
     * Check if a Player's name has already been given
     */
    private static boolean isIn(String name, String[] names) {

        boolean notIn = true;
        if (names.length > 1) {

            int index = 0;
            while (notIn && index < names.length) {

                if (name.equals(names[index])) {

                    notIn = false;
                }
                index++;
            }
        }

        return !notIn;
    }

    /**
     * Ask the name of each player
     */
    private static void namesPlayers(int nb, String[] names) {

        Scanner keyboard = new Scanner(System.in);
        String name;
        for (int i = 1; i <= nb; i++) {

            System.out.println("Name for Player " + i);
            name = keyboard.next();
            while (isIn(name, names)) {

                System.out.println("Name already taken " +
                        "please select another name: ");
                name = keyboard.next();
            }
            names[i-1] = name;
        }
    }

    /**
     *  Ask User to enter a string and check
     *  if the string match with the chosen pattern
     */
    private static String robustChoice(String message) {

        Scanner keyboard = new Scanner(System.in);

        System.out.println(message);

        String choice = null;
        boolean hasChoice = false;
        String[] regex = {"^f [drul]( [0-5]){1,6}$",
                "^l ([0-9]|[1-8][0-9]|90) ([0-9]|[1-8][0-9]|90) [drul]( [0-5]){1,6}$",
                "^o ([0-9]|[1-8][0-9]|90) ([0-9]|[1-8][0-9]|90) [0-5]$",
                "^m( ([0-9]|[1-8][0-9]|90) ([0-9]|[1-8][0-9]|90) [0-5]){1,6}$",
                "^p( [0-5]){0,6}$"};

        while (!hasChoice) {

            choice = keyboard.nextLine();
            if (choice.equals("q")) {

                hasChoice = true;
            }
            else {
                int i = 0;
                while (i < regex.length && !hasChoice) {

                    if (choice.matches(regex[i])) {

                        hasChoice = true;
                    }
                    i++;
                }
                if (!hasChoice){
                    System.out.println(RED + "Incorrect entry." +
                            " Please select one of these lines : " +
                            colorReset);
                    View.displayHelp();
                    System.out.println(message);
                }
            }

        }

        return choice;
    }

    /**
     * Action to play one tile
     */
    private static void play1Tile(Game game, String[] choice)
            throws QwirkleException {

        BoxOfPositions boxOfPositions = new BoxOfPositions();
        boxOfPositions.add(Integer.parseInt(choice[1]),
                Integer.parseInt(choice[2]));
        int index = Integer.parseInt(choice[3]);

        try {

            game.play(boxOfPositions.getRow(0),
                    boxOfPositions.getCol(0), index);
        } catch (QwirkleException q) {

            System.out.println(RED + "ERROR! Please " +
                    "give other values" + colorReset);
            gameChoice(game);
           }
    }

    /**
     * Action to play multiple tile on
     * different location but on the same line
     */
    private static void playPlicPloc(Game game, String[] choice)
            throws QwirkleException{

        try {

            int[] rCT = new int[choice.length - 1];
            for (int i = 1; i < choice.length; i++) {

                rCT[i - 1] = Integer.parseInt(choice[i]);
            }

            game.play(rCT);
        } catch (QwirkleException q) {

            System.out.println(RED + "ERROR! Please " +
                    "give other values" + colorReset);
            gameChoice(game);
        }
    }


    /**
     * Convert all the character strings present
     * from an index chosen in an array into integers
     */
    private static int[] indexArrayTile(String[] indexTiles, int begin) {

        int[] indexes = new int[indexTiles.length - begin];
        for (int i = 0; i < indexes.length; i++) {

            indexes[i] = Integer.parseInt(indexTiles[i + begin]);
        }

        return indexes;
    }

    /**
     * Convert a String to a Direction
     */
    private static Direction strToDirection(String str_dir) {

        return switch (str_dir) {
            case "u" -> Direction.UP;
            case "d" -> Direction.DOWN;
            case "r" -> Direction.RIGHT;
            case "l" -> Direction.LEFT;
            default -> throw new
                    QwirkleException("Incorrect ENTRY");
        };
    }

    /**
     * Action to play one line of tile
     */
    private static void playLine(Game game, String[] choice)
            throws QwirkleException {

        BoxOfPositions positions = new BoxOfPositions();
        positions.add(Integer.parseInt(choice[1]),
                Integer.parseInt(choice[2]));
        Direction d = strToDirection(choice[3]);

        int[] tiles = indexArrayTile(choice, 4);
        try {

            game.play(positions.getRow(0),
                    positions.getCol(0), d, tiles);

        } catch (QwirkleException q) {

            System.out.println(RED + "ERROR! Please " +
                    "give other values" + colorReset);
            gameChoice(game);
        }
    }

    /**
     * Action to make the first move
     */
    private static void playFirst(Game game, String[] choice)
            throws QwirkleException {

        Direction d = strToDirection(choice[1]);

        int[] tiles = indexArrayTile(choice, 2);

        try {

            game.first(d, tiles);

        } catch (QwirkleException q) {

            System.out.println(RED + "ERROR! Please " +
                    "give other values" + colorReset);
            gameChoice(game);
        }
    }

    /**
     * Pass a turn or trade Tiles before passing a turn
     */
    private static void pass(Game game,String[] choiceArray) {

        if (choiceArray.length == 1) {

            game.pass();
        }
        else {

            int [] index = indexArrayTile(choiceArray, 1);
            game.trade(index);
        }
    }

    /**
     * Action for choosing a letter
     */
    private static boolean gameChoice(Game game)
            throws QwirkleException {

        String choice = robustChoice("Select one of" +
                " these line :");
        String[] choiceArray = choice.split(" ");
        boolean surrender = false;

        switch (choice.charAt(0)) {
            case 'o' -> play1Tile(game, choiceArray);
            case 'l' -> playLine(game, choiceArray);
            case 'm' -> playPlicPloc(game, choiceArray);
            case 'f' -> playFirst(game, choiceArray);
            case 'p' -> pass(game, choiceArray);
            default -> {surrender = true;
                        saveGame(game);}
        }

        return surrender;
    }

    /**
     * Course of the game
     */
    private static void app(Game game)
            throws QwirkleException {

        boolean surrender = false;
        while (!game.isOver() && !surrender) {

            View.display(game.getGrid());
            View.display(game.getCurrentPlayerName(),
                    game.getCurrentPlayerHand(),
                    game.getCurrentPlayerScore());
            View.displayHelp();

            surrender = gameChoice(game);
        }
        game.getPlayers().update();
        View.displayPlayers(game.getPlayers());
    }

    /**
     * Launch a New Game
     */
    private static void newGame() {

        Game game;
        int nbPlayer = robustNumber(18);
        String[] names = new String[nbPlayer];
        namesPlayers(nbPlayer, names);

        game = new Game(names);

        app(game);
    }

    /**
     * Load a Save if either a filename is detected
     * or User wants to load his game
     */
    private static void loadGame(Game savedGame, ArrayList<String> filenames) {

        System.out.println("Files Saved detected");
        System.out.println("Do you want to load a save? Y/N");
        String choice = yesOrNo();
        if (choice.equals("Y")) {

            View.displayBackUp(filenames);
            int index = robustNumber(filenames.size()) - 1;
            savedGame = savedGame.getFromFile(filenames.get(index));
            if (savedGame != null) {

                savedGame.setFileNames(filenames);
                app(savedGame);
            } else {

                System.out.println("BackUp cannot be loaded, " +
                        "New Game will be launch!");
                newGame();
            }
        }
        else {

            System.out.println("You selected No, " +
                    "New Game will be launch");
            newGame();
        }
    }

    public static void main(String[] args)
            throws QwirkleException {

        Game savedGame;


        savedGame = new Game();
        ArrayList<String> filenames = savedGame.getBackUp("backup.ser");

        if (filenames != null) {

            loadGame(savedGame, filenames);
        }
        else {

            newGame();
        }

    }
}



