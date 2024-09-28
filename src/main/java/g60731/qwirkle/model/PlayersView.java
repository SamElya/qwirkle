package g60731.qwirkle.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Class that represent the view of Players
 */
public class PlayersView implements Serializable {

    private final List<String> namesPlayers;
    private final List<Integer> scoresPlayer;

    /**
     * Constructor of PlayersView
     */
    public PlayersView() {

        namesPlayers = new ArrayList<>();
        scoresPlayer = new ArrayList<>();
    }

    /**
     * Add the name of a Player and his score
     * @param player    A player
     */
    public void add(Player player) {

            namesPlayers.add(player.getName());
            scoresPlayer.add(player.getScore());

    }

    /**
     * Update the score of a player
     * @param index     The player's turn
     * @param players   All players on game
     */
    public void updatePoint(int index, Player[] players){

        scoresPlayer.set(index, players[index].getScore());
    }

    /**
     * Sort all players by his score in descending order
     */
    public void update() {

        UtilSort.bubbleSort(scoresPlayer, namesPlayers);
    }

    /**
     * Give all player names
     * @return  An unmodifiable List of the player names
     */
    public List<String> getNamesPlayers() {

        return Collections.unmodifiableList(namesPlayers);
    }

    /**
     * Give all player scores
     * @return  An unmodifiable List of the player scores
     */
    public List<Integer> getScoresPlayer() {

        return Collections.unmodifiableList(scoresPlayer);
    }
}
