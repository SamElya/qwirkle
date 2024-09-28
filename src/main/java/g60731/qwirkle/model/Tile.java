package g60731.qwirkle.model;

import java.io.Serializable;

/**
 * Create a tile with a color and a shape
 * @param color the color of the tile
 * @param shape the shape indicated on the tile
 */
public record Tile(Color color, Shape shape) implements Serializable {
}
