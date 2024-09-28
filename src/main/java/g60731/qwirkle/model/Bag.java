package g60731.qwirkle.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Class that represent the bag where the tiles will be stored
 */
public class Bag implements Serializable {

    private List<Tile> tiles;

    private static Bag instance;

    /**
     * Constructor of Bag
     */
    private Bag(){

        tiles = new ArrayList<>();

        for (int i = 0; i < 3; i++) {
            for (Shape shape : Shape.values()) {
                for (Color color : Color.values()) {
                    tiles.add(new Tile(color, shape));
                }
            }
        }
    }

    /**
     * Create a unique objet of Bag's type
     * @return A bag of tiles
     */
    public static Bag getInstance() {
        if (instance == null) {
            synchronized (Bag.class) {
                if (instance == null) {
                    instance = new Bag();
                }
            }
        }
        return instance;
    }

    /**
     * Put Back the tiles on the bag
     * @param hand  The tiles that will be put back
     */
    public void putBack(Tile[] hand) {

        tiles.addAll(Arrays.asList(hand));
    }

    /**
     * Draw n tiles from the bag
     * @param   n   Number of tiles desired
     * @return      Null if the bag is empty,
     *              a tile's array of size:
     *              n if n smaller than the number of tiles left on the bag,
     *              m tiles left if n bigger than m
     */
    public Tile[] getRandomTiles(int n) {
        if (size() == 0) {

            return null;
        }

        Tile[] drawnTiles;

        if (n >= size()) {
            drawnTiles = new Tile[size()];

            int i = 0;
            while (i < size()) {
                drawnTiles[size() - 1] = tiles.get(i);
                tiles.remove(i);
            }
        }
        else {
            drawnTiles = new Tile[n];

            for (int i = 0, randomNum; i < n; i++) {
                randomNum = (int) (Math.random() * size());
                drawnTiles[i] = tiles.get(randomNum);
                tiles.remove(randomNum);
            }
        }
        return drawnTiles;
    }

    /**
     * Give the number of tiles left in bag
     * @return  The number of tiles left in bag
     */
    public int size() {
        return tiles.size();
    }

    public static void setBag(Bag bag) {

        instance = bag;
    }
}
