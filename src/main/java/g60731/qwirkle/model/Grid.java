package g60731.qwirkle.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import static g60731.qwirkle.model.CstGame.*;

/**
 * Grid represent the game board
 */
public class Grid implements Serializable {

    private final Tile[][] tiles;
    private boolean isEmpty = true;


    /**
     * Constructor of Grid
     */
    public Grid() {

        tiles = new Tile[GRIDEND][GRIDEND];
    }

    /**
     * Give the tile at a given position
     * @param   row The row where the desired tile is
     * @param   col The col where the desired tile is
     * @return      Give the tile located at row and col
     */
    public Tile get(int row, int col) {

        if (row < GRIDBEGIN || row >= GRIDEND ||
                col < GRIDBEGIN || col >= GRIDEND) {

            return null;
        }

        return tiles[row][col];
    }

    /**
     * Generate directions to count the turn's point
     */
    private static Direction choiceDir(BoxOfPositions positions, Direction... d) {

        Direction direction;
        if (d.length == 0) {

            int row0 = positions.getRow(0);
            int row1 = positions.getRow(1);
            boolean sameRow = row0 == row1;
            if (sameRow) {

                direction = Direction.LEFT;
            }
            else {

                direction = Direction.UP;
            }
        }
        else {

            direction = switch (d[0]) {

                case UP, DOWN -> Direction.LEFT;
                case LEFT, RIGHT -> Direction.UP;
            };
        }

        return  direction;
    }

    /**
     * Count the point of a turn
     */
    private static int pointLine(Tile[][] tiles,
                                    BoxOfPositions positions,
                                    Direction principalD,
                                    Direction secondaryD,
                                    boolean principal) {

        int point = 0;
        int row0 = positions.getRow(0);
        int col0 = positions.getCol(0);
        int[] posChange = {row0, col0, 1};
        int secondaryRow = secondaryD.getDeltaRow();
        int secondaryCol = secondaryD.getDeltaCol();
        int nbTile = 0;
        while (posChange[0] < GRIDEND && posChange[0] >= GRIDBEGIN
                && posChange[1] < GRIDEND && posChange[1] >= GRIDBEGIN &&
                tiles[posChange[0]][posChange[1]] != null) {

            nbTile++;
            point++;
            if (principal && positions.isIn(posChange[0], posChange[1])) {
                //check if there is a secondary line

                if ((tiles[posChange[0] + secondaryRow]
                        [posChange[1] + secondaryCol] != null ||
                        tiles[posChange[0] - secondaryRow]
                                [posChange[1] - secondaryCol] != null)) {

                    point += pointLine(tiles, positions, secondaryD,
                            principalD, false);
                }
                positions.firstRemove();
            }

            posChange[0] += principalD.getDeltaRow();
            posChange[1] += principalD.getDeltaCol();

            if (tiles[posChange[0]][posChange[1]] == null && posChange[2] != 0) {

                principalD = tileIsOnTheEdge(row0, col0, principalD, posChange);
            }
        }
        if (nbTile == MAXTILELINE) {

            point += QWIRKLEPOINT;
        }

        return point;
    }

    /**
     * Check if the line has duplicate shape
     * @param   line    An array of tiles
     * @return          True if there are duplicate shapes
     */
    private static boolean diffShapeSameCol(Tile[] line) {

        int count = 1;

        if (!(line == null)) {
            for (int i = 0; (i < (line.length - 1)) &&
                    (count < 2); i++) {

                count = 1;

                for (int j = i+1; (j < line.length) &&
                        (count < 2); j++) {

                    if (line[i].shape() == line[j].shape()
                        || (line[i].color() != line[j].color())) {

                        count++;
                    }
                }
            }
        }

        return !(count > 1);
    }

    /**
     * Check if the line has duplicate color
     * @param   line    An array of tiles
     * @return          True if there are duplicate colors
     */
    private static boolean sameShapeDiffCol(Tile[] line) {

        int count = 1;

        if (!(line == null)) {

            for (int i = 0; (i < (line.length - 1)) &&
                    (count < 2); i++) {

                count = 1;
                for (int j = i+1; (j < line.length) &&
                        (count < 2); j++) {

                    if (line[i].shape() != line[j].shape()
                            || (line[i].color() == line[j].color())) {

                        count++;
                    }
                }
            }
        }

        return !(count > 1);
    }

    /**
     * Begin the game by placing a number of tile
     * @param d     Laying direction
     * @param line  The number of tile
     * @throws QwirkleException If add don't respect the rule
     * @return      The number of point won
     */
    public int firstAdd(Direction d, Tile... line)
            throws QwirkleException {

        if (!isEmpty || line.length > MAXTILELAID ||
                (!sameShapeDiffCol(line) && !diffShapeSameCol(line))) {

            throw new QwirkleException("action that does not" +
                    " follow the rules");
        }

        isEmpty = false;

        int row = DEFAULTPOS;
        int col = DEFAULTPOS;
        int point;
        BoxOfPositions positions = new BoxOfPositions();

        for (Tile tile : line) {

            tiles[row][col] = tile;
            positions.add(row, col);

            row += d.getDeltaRow();
            col += d.getDeltaCol();
        }

        Direction secondaryD = choiceDir(positions, d);
        point = pointLine(tiles, positions, d, secondaryD, true);

        return point;
    }

    /**
     *  Check if there is a tile next to our position
     */
    private static boolean checkNeighbor(Tile[][] tiles,
                                         int row, int col) {

        return tiles[row - 1][col] != null ||
                tiles[row + 1][col] != null ||
                tiles[row][col - 1] != null ||
                tiles[row][col + 1] != null;
    }

    /**
     *  Check if the line has the same Shape, the same Color
     *  or neither
     *  The returned value indicates which of the 3 the line matches
     */
    private static boolean checkLine(List<Tile> list) {

        if ((list.size() <= MAXTILELINE) && (list.size() > 1)) {

            return sameShapeDiffCol(list.toArray(new Tile[0])) ||
                    diffShapeSameCol(list.toArray(new Tile[0]));
        } // true because same and diff cannot be true in same time
            //because there aren't 6 identical tile with the same shape
            // and the same color

        return false;
    }

    /**
     * Return to initial row/col and go to the opposite direction
     */
    private static Direction tileIsOnTheEdge(int row, int col,
                                             Direction d,
                                             int[] posChange) {
        //posChange has 3 value 0: row, 1: col, 2: changeDir
        posChange[2]--;     // it has either 1 or 0
                            // if 1, when tile[row][col] is null
                            // row col will return on the initial position
                            // and go to the opposite direction
                             // and pass to 0
        posChange[0] = row;
        posChange[1] = col;
        d = d.opposite();

        posChange[0] += d.getDeltaRow();
        posChange[1] += d.getDeltaCol();

        return d;
    }

    /**
     * Return to the initial row col and go to the opposite
     * direction when there is no tile on the present row/col
     * and user is on the edge of grid
     */
    private static Direction conditionNullEdge(Tile[][] tiles, int row,
                                               int col, int[] posChange,
                                               Direction d) {

        if (!(posChange[0] < GRIDEND && posChange[0] >= GRIDBEGIN &&
                posChange[1] < GRIDEND && posChange[1] >= GRIDBEGIN)) {

            d = tileIsOnTheEdge(row, col, d, posChange);
        }
        if (tiles[posChange[0]][posChange[1]] == null){

            d = tileIsOnTheEdge(row, col, d, posChange);
        }

        return d;
    }

    /**
     * Verify is a tile can be placed on row and col
     */
    private static boolean checkVH(Tile[][] tiles,
                                         int row, int col,
                                         Tile tile, Direction d,
                                   boolean principal) {
        boolean tileOk = !principal;  //if principal is false,
                                    //it means we already validate
                                    // tileOk
        if (checkNeighbor(tiles, row, col)) {

            //posChange has 3 value 0: row, 1: col, 2: changeDir
            List<Tile> line = new ArrayList<>();
            line.add(tile);
            int[] posChange = {row + d.getDeltaRow(),
                    col + d.getDeltaCol(), 1};

            d = conditionNullEdge(tiles, row, col, posChange, d);
            while (posChange[0] < GRIDEND && posChange[0] >= GRIDBEGIN &&
                    posChange[1] < GRIDEND && posChange[1] >= GRIDBEGIN &&
                    tiles[posChange[0]][posChange[1]] != null) {

                line.add(tiles[posChange[0]][posChange[1]]);

                posChange[0] += d.getDeltaRow();
                posChange[1] += d.getDeltaCol();
                if (tiles[posChange[0]][posChange[1]] == null && posChange[2] != 0) {

                    d = tileIsOnTheEdge(row, col, d, posChange);
                }
            }

            if (line.size() > 1) {

                tileOk = checkLine(line);
            }
            if ((tileOk || line.size() == 1) && principal ) {

                tileOk = checkVH(tiles, row, col, tile,
                        Direction.LEFT, false);
            }
        }

        return tileOk;
    }

    /**
     *  Check if the tile respect the rules of adding a tile
     */
    private static boolean isAddOk(Tile[][] tiles, int row,
                                   int col, Tile tile, boolean isEmpty) {

        if ((row >= GRIDBEGIN && row < GRIDEND &&
                col >= GRIDBEGIN && col < GRIDEND) &&
                !isEmpty && tiles[row][col] == null) {

            return checkVH(tiles, row, col,
                        tile, Direction.UP, true);
        }

        return false;
    }

    /**
     * Add a tile on the grid
     * @param row   Row where we will set our tile
     * @param col   Column where we will set our tile
     * @param tile  A tile with a shape and a color
     * @throws QwirkleException If add don't respect the rule
     * @return      The number of point won
     */
    public int add(int row, int col, Tile tile)
            throws QwirkleException {

        if (!isAddOk(tiles, row, col, tile, isEmpty)) {

            throw new QwirkleException("action that does not" +
                    " follow the rules");
        }
        int point;
        BoxOfPositions boxOfPositions = new BoxOfPositions();
        boxOfPositions.add(row, col);

        tiles[row][col] = tile;

        Direction principalD;
        Direction secondaryD;
        if ((tiles[row + 1][col] != null ||
                tiles[row - 1][col] != null)) {

            principalD = Direction.UP;
        }
        else {

            principalD = Direction.LEFT;
        }
        secondaryD = choiceDir(boxOfPositions, principalD);
        point = pointLine(tiles, boxOfPositions, principalD,
                secondaryD, true);

        return point;
    }

    /**
     * Remove the tile from the board game
     * where the position are saved
     * @param tiles     The board game
     * @param positions Saved positions
     */
    private static void removeTileGrid(Tile[][] tiles,
                                       BoxOfPositions positions) {

        for (int i = 0; i < positions.size(); i++) {

            int row = positions.getRow(i);
            int col = positions.getCol(i);

            tiles[row][col] = null;
        }
    }

    /**
     * Check if there is a tile next to line
     * @param tiles         The board game
     * @param positions     All the positions
     *                      of the tiles
     * @param line          A number of tiles
     * @return              True if the line has a neighboring tiles
     */
    private static boolean checkLineNeighbor(Tile[][] tiles,
                                             BoxOfPositions positions, Tile[] line) {

        boolean neighbor = false;
        for (int i = 0; i < line.length; i++) {

            if (checkNeighbor(tiles,
                    positions.getRow(i),
                    positions.getCol(i))) {

                neighbor = true;
            }
        }

        return neighbor;
    }

    /**
     * Check if a line can be placed on the gmame board
     * @param tiles         The game board
     * @param positions     All the positions
     *                      of the tiles to play
     * @param line          A number of tiles
     * @return              True if the line can be placed
     */
    private boolean isLineOk(Tile[][] tiles, BoxOfPositions positions, Tile[] line) {

        boolean isLineOk = false;

        if (checkLineNeighbor(tiles, positions, line)) {

            BoxOfPositions boxOfPositionsT = new BoxOfPositions();

            for (int i = 0; i < line.length; i++) {

                if (checkNeighbor(tiles, positions.getRow(i), positions.getCol(i))) {

                    if (isAddOk(tiles, positions.getRow(i), positions.getCol(i), line[i],
                            isEmpty)) {

                        isLineOk = true;

                    } else {

                        removeTileGrid(tiles, boxOfPositionsT);

                        return false;
                    }
                }

                tiles[positions.getRow(i)][positions.getCol(i)] = line[i];
                boxOfPositionsT.add(positions.getRow(i), positions.getCol(i));
            }
        }

        return  isLineOk;
    }




    /**
     * Add with some tiles to lay on grid in a chosen direction
     * @param row   The first row where the first tile will be placed
     * @param col   The first column where the first tile will be placed
     * @param d     The laying direction
     * @param line  A number of tiles
     * @throws QwirkleException If add don't respect the rule
     * @return      The number of point won
     */
    public int add(int row, int col, Direction d, Tile... line)
            throws QwirkleException {

        if (line.length > MAXTILELAID ||
                (!sameShapeDiffCol(line) && !diffShapeSameCol(line))) {

            throw new QwirkleException("action that does not" +
                    " follow the rules");
        }
        BoxOfPositions positions = new BoxOfPositions();

        for (Tile tile : line) {

            positions.add(row, col);

            row += d.getDeltaRow();
            col += d.getDeltaCol();
        }
        if (!isLineOk(tiles, positions, line)) {

            throw new QwirkleException("action that does not" +
                    " follow the rules");
        }

        int point;

        Direction secondaryD = choiceDir(positions, d);
        point = pointLine(tiles, positions, d, secondaryD, true);

        return point;
    }


    /**
     * Checks if there is hole between 2 laid tiles
     */
    private static void isTileOnSameLine(Tile[][] tiles,
                                         BoxOfPositions positions)
            throws QwirkleException {

        if (positions.size() > 1) {

            Direction d = choiceDir(positions);

            int[] posChange = {positions.getRow(0),
                    positions.getCol(0), 1};
            // posChange[2] return to initial row
            // and col and go
            // to the opposite direction
            int count = positions.size();
            while (posChange[0] < GRIDEND && posChange[0] >= GRIDBEGIN &&
                    posChange[1] < GRIDEND && posChange[1] >= GRIDBEGIN &&
                    tiles[posChange[0]][posChange[1]] != null) {

                if (positions.isIn(posChange[0], posChange[1])) {

                    count--;
                }
                posChange[0] += d.getDeltaRow();
                posChange[1] += d.getDeltaCol();

                if (tiles[posChange[0]][posChange[1]] == null && posChange[2] != 0) {

                    d = tileIsOnTheEdge(positions.getRow(0),
                            positions.getCol(0), d, posChange);
                }
            }

            if (count > 0) {

                removeTileGrid(tiles, positions);
                throw new QwirkleException("Tile is on the " +
                        "row or col but a different line");
            }
        }
    }

    /**
     * Checks that all tiles will be placed only on the same row
     */
    private static void badChoicePosition(TileAtPosition[] line)
            throws QwirkleException {

        if (line.length > 1) {

            boolean sameRow = (line[0].row() ==
                    line[1].row());
            boolean sameCol = (line[0].col() ==
                    line[1].col());
            for (TileAtPosition tile : line) {

                if ((sameRow && (line[0].row() != tile.row())) ||
                        (sameCol && (line[0].col() != tile.col())) ||
                        (sameRow && sameCol) || (!sameRow && !sameCol)) {

                    throw new QwirkleException("action that does not" +
                            " follow the rules");
                }
            }
        }
    }

    /**
     * Place several independent tiles on the same line
     * @param line  A serie of tiles with their position
     * @throws QwirkleException If add don't respect the rule
     * @return      The number of point won
     */
    public int add(TileAtPosition... line)
            throws QwirkleException {

        if (line.length > MAXTILELAID) {

            throw new QwirkleException("action that does not" +
                    " follow the rules");
        }

        badChoicePosition(line);

        BoxOfPositions positions = new BoxOfPositions();
        Tile[] onlyTiles = new Tile[line.length];

        int i = 0;
        for (TileAtPosition tilePos : line) {

            onlyTiles[i] = tilePos.tile();
            positions.add(tilePos.row(), tilePos.col());

            i++;
        }

        if (!isLineOk(tiles, positions, onlyTiles)) {

            throw new QwirkleException("action that does not" +
                    " follow the rules");
        }
        isTileOnSameLine(tiles, positions);
        int point;

        Direction principalD = choiceDir(positions);
        Direction secondaryD = choiceDir(positions, principalD);
        point = pointLine(tiles, positions, principalD,
                secondaryD, true);

        return point;
    }

    /**
     * Tell if the grid has tile
     * @return  True if grid has a tile
     */
    public boolean isEmpty() {

        return isEmpty;
    }

    /**
     *  Increment the range to check the
     *  neighborhood of the furthest tiles
     */
    private static void offSetExtremum(BoxOfPositions positions,
                                  int min, int max,
                                  int offset) {

        if (positions.getRow(0) - offset >= min) {

            positions.setRow(0, -offset);
        }
        if (positions.getCol(0) - offset >= min) {

            positions.setCol(0, -offset);
        }
        if (positions.getRow(1) + offset < max) {

            positions.setRow(1, offset);
        }
        if (positions.getCol(1) + offset < max) {

            positions.setCol(1, offset);
        }
    }

    /**
     *  Search if a play is possible
     */
    private boolean canPlay(BoxOfPositions positions, Tile tile) {

        boolean isPlayPossible = false;

        offSetExtremum(positions, GRIDBEGIN, GRIDEND, 1);
        for (int row = positions.getRow(0);
             row <= positions.getRow(1); row++) {

            for (int col = positions.getCol(0);
                 col <= positions.getCol(1); col++) {
                if ((row >= GRIDBEGIN && row < GRIDEND) &&
                        (col >= GRIDBEGIN && col < GRIDEND)) {

                    if (tiles[row][col] == null) {

                        isPlayPossible = isAddOk(tiles, row,
                                col, tile, isEmpty);
                    }
                    if (isPlayPossible) {

                        return true;
                    }
                }

            }
        }

        return false;
    }

    /**
     * Search the furthest tiles
     */
    private void gridInterval(BoxOfPositions positions) {

        int minRow = GRIDBEGIN;
        int minCol = GRIDBEGIN;
        int maxRow = GRIDBEGIN;
        int maxCol = GRIDBEGIN;
        for (int row = GRIDBEGIN; row < GRIDEND; row++) {
            for (int col = GRIDBEGIN; col < GRIDEND; col++) {
                if (tiles[row][col] != null) {
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
     * Check if a play is possible when the bag is empty
     * @param hand  The tiles hand of a player
     * @return      True if play is possible else false
     */
    public boolean isPlayPossible(List<Tile> hand) {

        BoxOfPositions minMax = new BoxOfPositions();
        gridInterval(minMax);

        boolean isPossible;
        for (Tile tile : hand) {

            isPossible = canPlay(minMax, tile);
            if (isPossible) {

                return true;
            }
        }
        return false;
    }
}
