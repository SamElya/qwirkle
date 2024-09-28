package g60731.qwirkle.model;

import java.io.Serializable;

/**
 * Represent the view of grid
 */
public class GridView implements Serializable {

    Grid grid;

    /**
     * Constructor of GridView
     * @param grid  The game's grid
     */
    public GridView(Grid grid) {

        this.grid = grid;
    }

    /**
     * Give the tile placed on the in the grid
     * @param row   A row in grid
     * @param col   A col in grid
     * @return      The tile at row and col position
     */
    public Tile get(int row, int col) {

        return grid.get(row, col);
    }

    /**
     * Tell if grid is empty
     * @return  True if empty
     */
    public boolean isEmpty() {

        return grid.isEmpty();
    }


}
