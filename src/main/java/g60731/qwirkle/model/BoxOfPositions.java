package g60731.qwirkle.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Represent a box of all row/col
 */
public class BoxOfPositions implements Serializable {

    private final List<List<Integer>> positions;

    /**
     * Constructor of BoxOfPositions
     */
    public BoxOfPositions(){

        positions = new ArrayList<>();
    }

    /**
     * Give the row position of an object
     * @param i The index of an object
     * @return  The row position of the object in index i
     */
    public Integer getRow(int i) {

        return positions.get(i).get(0);
    }

    /**
     * Give the col position of an object
     * @param i The index of an object
     * @return  The col position of the object in index i
     */
    public Integer getCol(int i) {

        return positions.get(i).get(1);
    }

    /**
     * Add the position of an object
     * @param row   The row position of an object
     * @param col   The col position of an object
     */
    public void add(int row, int col) {

        positions.add(Arrays.asList(row, col));
    }

    /**
     * Give the number of position saved
     * @return  The number of position saved
     */
    public int size() {

        return positions.size();
    }

    /**
     * Check if a row and col is in positions
     * @param row   A row
     * @param col   A col
     * @return  True if row and col is in position else false
     */
    public boolean isIn(int row, int col) {

        int index = 0;
        if (positions.size() > 0) {

            while (index < positions.size() &&
                    (row != positions.get(index).get(0) ||
                            col != positions.get(index).get(1))) {

                index++;
            }
        }

        return index < positions.size();
    }

    /**
     * Remove the first position
     */
    public void firstRemove() {
        positions.remove(0);
    }

    /**
     * Set the row of an index to another value
     * @param i         The index of the position
     * @param offset    The offset from the old value
     */
    public void setRow(int i, int offset) {

        positions.get(i).set(0,
                positions.get(i).get(0) + offset);
    }

    /**
     * Set the col of an index to another value
     * @param i         The index of the position
     * @param offset    The offset from the old value
     */
    public void setCol(int i, int offset) {

        positions.get(i).set(1,
                positions.get(i).get(1) + offset);
    }
}
