package g60731.qwirkle.model;

/**
 * Object to lay several independent tiles on the same line
 * @param row   the row of each tile
 * @param col   the col of each tile
 * @param tile  a tile with a shape and a color
 */
public record TileAtPosition(int row, int col, Tile tile) {
}
