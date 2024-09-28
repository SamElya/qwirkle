package g60731.qwirkle.model;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import static g60731.qwirkle.model.CstGame.*;

/**
 * Represent what can be done on the game
 */
public class Game implements Serializable {

    private final Grid grid;
    private final Player[] players;
    private int turn;
    private final GridView gridView;
    private Bag bag = Bag.getInstance();
    private final PlayersView playersView;
    private List<String> fileNames;

    /**
     * Constructor of Game
     * @param names The player name's
     * @throws QwirkleException If there are no String
     */
    public Game(String[] names) throws QwirkleException {
        if (names.length == 0) {
            throw new QwirkleException("action that does not " +
                    "follow the rules");
        }

        grid = new Grid();
        this.gridView = new GridView(grid);
        turn = 0;
        players = new Player[names.length];
        playersView = new PlayersView();

        for (int i = 0; i < names.length; i++) {
            players[i] = new Player(names[i]);
            players[i].refill();

            playersView.add(players[i]);
        }

        fileNames = new ArrayList<>();

    }

    /**
     * Constructor of Game when a save must be loaded
     */
    public Game() {
        grid = new Grid();
        this.gridView = new GridView(grid);
        players = new Player[0];
        playersView = new PlayersView();

    }

    /**
     * Remove, refill tiles from hand and advance one turn
     */
    private void reArrange(Tile... tiles) {

        players[turn].remove(tiles);
        if (bag.size() > 0) {

            players[turn].refill();
        }
        playersView.updatePoint(turn, players);
        changeTurn();
    }

    /**
     * Put the tiles to play in an array by using index
     */
    private Tile[] tilesHand(int... is)
            throws QwirkleException {

        Tile[] hand = new Tile[is.length];
        for (int i = 0; i < is.length; i++) {

            if (is[i] < 0 || is[i] > MAXINDEX) {

                throw new QwirkleException("action that does not " +
                        "follow the rules");
            }
            hand[i] = players[turn].getTiles().get(is[i]);
        }

        return hand;
    }

    /**
     * The first laying of tiles
     * @param d     Direction of laying pose
     * @param is    Indexes of tiles present in the hand
     */
    public void first(Direction d, int... is)
            throws QwirkleException {

        Tile[] hand = tilesHand(is);
        int point = grid.firstAdd(d, hand);
        players[turn].addScore(point);
        reArrange(hand);
    }

    /**
     * Play a tile present in the hand
     * @param row       The laying row
     * @param col       The laying col
     * @param index     Index of tile present in the hand
     */
    public void play(int row, int col, int index)
            throws QwirkleException {

        Tile tile = players[turn].getTiles().get(index);
        int point = grid.add(row, col, tile);
        players[turn].addScore(point);
        reArrange(tile);
    }

    /**
     * Play some tiles present in the hand
     * @param row       The laying row
     * @param col       The laying col
     * @param d         Chosen direction
     * @param indexes   Indexes of tiles present in the hand
     */
    public void play(int row, int col, Direction d, int... indexes)
            throws QwirkleException {

        Tile[] hand = tilesHand(indexes);
        int point = grid.add(row, col, d, hand);
        players[turn].addScore(point);
        reArrange(hand);
    }

    /**
     * Separate all tiles and positions on different arrays
     */
    private void diviseTileAndPositions(TileAtPosition[] tilesPosition,
                                        Tile[] tiles, int[] is) {

        for (int i = 0; i < (is.length / 3); i++) {

            Tile tile = players[turn].getTiles()
                    .get(is[(i * 3) + 2]);

            tilesPosition[i] = new TileAtPosition(is[i * 3],
                    is[(i * 3) + 1], tile);
        }
    }

    /**
     * Take only the tiles index in is
     */
    private int[] indexTile(int[] is) {

        int[] indexes = new int[is.length / 3];
        for (int i = 0; i < indexes.length; i++) {

            indexes[i] = is[(i * 3) + 2];
        }

        return indexes;
    }

    /**
     * Play any number of tiles
     * @param is    Parameter of TileAtPosion
     *              th first is row, the second col
     *              and the third is the index of a tile present
     *              in the hand, etc.
     */
    public void play(int... is)
            throws QwirkleException {

        int[] indexes = indexTile(is);

        TileAtPosition[] tilesPosition =
                new TileAtPosition[is.length / 3];
        Tile[] tiles = new Tile[tilesPosition.length];
        diviseTileAndPositions(tilesPosition, tiles, is);

        int point = grid.add(tilesPosition);
        players[turn].addScore(point);
        reArrange(tiles);
    }

    /**
     * Return the player name
     * @return  The player name
     */
    public String getCurrentPlayerName() {

        return players[turn].getName();
    }

    /**
     * Return the player hand
     * @return  The player hand
     */
    public List<Tile> getCurrentPlayerHand() {

        return players[turn].getTiles();
    }

    /**
     * Display a non-modifiable grid
     * @return  GridView the non-modifiable grid
     */
    public GridView getGrid() {

        return gridView;
    }

    /**
     * Display a non-modifiable players
     * @return  PlayersView the non-modifiable
     *          players
     */
    public PlayersView getPlayers() {

        return playersView;
    }

    /**
     * Return the player score
     * @return  The player score
     */
    public int getCurrentPlayerScore() {

        return players[turn].getScore();
    }

    /**
     * Change the player's turn
     */
    private void changeTurn() {

        if (turn == players.length - 1) {
            turn = 0;
        }
        else {
            this.turn++;
        }
    }

    /**
     * Put back Tiles before draw
     * @param index The index of tile we
     *              want to put back
     */
    public void trade(int[] index) {

        Tile[] hand = tilesHand(index);
        bag.putBack(hand);
        reArrange(hand);
    }

    /**
     * Pass a turn
     */
    public void pass() {

        changeTurn();
    }

    /**
     * Check if all player can play when the bag is empty
     */
    private boolean endOfGame() {

        boolean isPossible = false;
        int testTurn = 0;
        while (testTurn < players.length && !isPossible) {

            isPossible = canAdd1P(testTurn);
        }
        return !isPossible;
    }

    /**
     * Check if a player can play when the bag is empty
     */
    private boolean canAdd1P(int currentTurn) {

        return grid.isPlayPossible(players[currentTurn]
                .getTiles());
    }

    /**
     * Check if the game is over
     * @return  True if the game is over
     */
    public boolean isOver() {

        if (grid.isEmpty()) {

            return false;
        }

        boolean isEndGame = false;
        if (turn == 0 && bag.size() == 0) {

            isEndGame = endOfGame();
        }
        if (turn >= 0 && bag.size() == 0) {

            boolean isEnd1P = !canAdd1P(turn);
            if (isEnd1P) {

                System.out.println("Player " +
                        players[turn].getName() + " can no longer play");
                System.out.println("He will pass his turn");
                turn++;
            }

            return false;
        }
        if (players[turn].getTiles().size() == 0 &&
                bag.size() == 0) {

            System.out.println("Player: " + players[turn].getName() +
                    " managed to empty his hand, " +
                    "he gain 6 additional point!");
            players[turn].addScore(6);
            isEndGame = true;
        }

        return isEndGame;
    }

    /**
     * Save all filename in a file
     * @param filename  The file name where all filenames are
     * @return          An arrayList of all filenames
     */
    public ArrayList<String> getBackUp(String filename) {

        ArrayList<String> filenames;
        try (ObjectInputStream in =
                     new ObjectInputStream(
                             Files.newInputStream(
                                     Paths.get(filename)))) {

            filenames = (ArrayList) in.readObject();
        } catch (IOException | ClassNotFoundException e) {

            return null;
        }

        return filenames;
    }

    /**
     * Restore the List of filename when
     * loaded the game
     * @param files The filename we want to restore
     */
    public void setFileNames(ArrayList<String> files) {

        fileNames.clear();
        fileNames.addAll(files);
    }

    /**
     * Create a Backup of all filenames
     */
    private void createBackUp() {

        try (ObjectOutputStream outputFlux =
                     new ObjectOutputStream(
                             Files.newOutputStream(Paths.get("backup.ser"),
                                     StandardOpenOption.CREATE))) {

            outputFlux.writeObject(fileNames);

            System.out.println("Backup saves with success.");
        } catch (IOException e) {

            System.out.println("Backup Failed,");
        }
    }

    /**
     * Check if a name already exist in filenames
     */
    private  boolean isFileNotInList(String filename) {

        if (fileNames.size() != 0) {

            for (String file : fileNames) {

                if (filename.equals(file)) {

                    return false;
                }
            }
        }

        return true;
    }

    /**
     * Add the filename in a list of filename
     * when the game has been saved
     */
    private void save(String filename) {

        fileNames.add(filename);
    }

    /**
     * Give the number of file saved
     * @return  The number of file saved
     */
    public int fileNameSize() {

        return fileNames.size();
    }


    /**
     * Save the game in a file
     * @param filename  The name of the file
     */
    public void write(String filename) {

        filename +=  ".ser";

        try (ObjectOutputStream outputFlux =
                     new ObjectOutputStream(
                             Files.newOutputStream(Paths.get(filename),
                                     StandardOpenOption.CREATE))) {

            outputFlux.writeObject(this);

            System.out.println("Game saves with success.");
            if (isFileNotInList(filename)) {

                save(filename);
                createBackUp();
            }
        } catch (IOException e) {

            e.printStackTrace();
            System.out.println("Save Failed,");
        }
    }

    /**
     * Recover data from previous part
     * @param filename  The file where the game
     *                  has been saved
     * @return          The Game if there is no error
     */
    public Game getFromFile(String filename) {

        Game game;

        try (ObjectInputStream in =
                     new ObjectInputStream(
                             Files.newInputStream(
                                     Paths.get(filename)))) {

            game = (Game) in.readObject();
            Bag.setBag(game.bag);
        } catch (IOException | ClassNotFoundException e) {

            return null;
        }

        return game;
    }
}
