package g60731.qwirkle.model;

import static java.util.Collections.unmodifiableList;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import static g60731.qwirkle.model.CstGame.*;

/**
 * Player will define the user in the game.
 * He will have at his disposal a name
 * and a hand composed of 6 tiles
 */
public class Player implements Serializable {

    private final String name;
    private final List<Tile> tiles = new ArrayList<>();
    private int score;
    static Bag bag = Bag.getInstance();

    /**
     * Constructor of Player's Class
     *
     * @param name The name of a Player
     */
    public Player(String name) {

        this.name = name;
        score = 0;
    }

    /**
     * Display a player's hand made up of tiles
     *
     * @return An unmodifiable list of the player's hand
     */
    public List<Tile> getTiles() {

        return unmodifiableList(tiles);
    }

    /**
     * Give the name of a Player
     *
     * @return The Player's name
     */
    public String getName() {

        return name;
    }

    /**
     * Give the score of a Player
     *
     * @return The Player's score
     */
    public int getScore() {

        return score;
    }

    /**
     * Refill the hand of a Player until he has 6 tiles
     */
    public void refill() {

        if (bag.size() > 0) {

            int nbRefill = HANDLENGHT - tiles.size();
            Tile[] tiles = bag.getRandomTiles(nbRefill);
            this.tiles.addAll(Arrays.asList(tiles));
        }

    }

    /**
     * Remove the tiles that the player has played
     *
     * @param ts The tiles that the player has played
     */
    public void remove(Tile... ts) {

        for (Tile tile : ts) {

            tiles.remove(tile);
        }
    }

    /**
     * Update the score of a Player
     * @param value The value that will
     *              increase the score
     */
    public void addScore(int value) {

        score += value;
    }
}
