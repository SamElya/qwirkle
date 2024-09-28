package g60731.qwirkle.model;

/**
 * Direction represent the direction of laying a tile
 * UP means deltaRow = -1 and deltaCol = 0
 * DOWN means deltaRow = 1 and deltaCol = 0
 * LEFT means deltaRow = 0 and deltaCol = -1
 * RIGHT means deltaRow = 0 and deltaCol = 1
 */
public enum Direction {
    UP (-1, 0), DOWN (+1, 0),
    LEFT (0, -1), RIGHT (0, +1);

    private final int deltaRow;
    private final int deltaCol;

    /**
     * Constructor of a Direction
     * @param   row A vertical movement
     * @param   col A horizontal movement
     */
    private Direction(int row, int col) {
        deltaRow = row;
        deltaCol = col;
    }

    /**
     * Give the value of deltaRow
     * @return  The value of deltaRow
     */
    public int getDeltaRow() {
        return deltaRow;
    }

    /**
     * Give the value of deltaCol
     * @return  The value of deltaCol
     */
    public int getDeltaCol() {
        return deltaCol;
    }

    /**
     * Give the opposite of the current direction
     * @return  The opposite of direction
     */
    public Direction opposite() {
        return switch (Direction.values()[this.ordinal()]) {
            case UP -> DOWN;
            case DOWN -> UP;
            case LEFT -> RIGHT;
            case RIGHT -> LEFT;
        };
    }
}
