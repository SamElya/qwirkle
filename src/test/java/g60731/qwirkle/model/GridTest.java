package g60731.qwirkle.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static g60731.qwirkle.model.Color.*;
import static g60731.qwirkle.model.Direction.*;
import static g60731.qwirkle.model.Shape.*;
import static g60731.qwirkle.model.QwirkleTestUtils.*;

class GridTest {

    private Grid grid;

    @BeforeEach
    void setUp() {
        grid = new Grid();
    }

    //FirstAdd
/*
    @Test
    void firstAdd_one_tile() {
        var tile = new Tile(Color.RED, CROSS);

        grid.firstAdd(RIGHT, tile);

        assertSame(get(grid, 0, 0), tile);
    }

    @Test
    void rules_sonia_a() {
        var t1 = new Tile(RED, ROUND);
        var t2 = new Tile(RED, DIAMOND);
        var t3 = new Tile(RED, PLUS);

        grid.firstAdd(UP, t1, t2, t3);

        assertEquals(t1, get(grid, 0, 0));
        assertEquals(t2, get(grid, -1, 0));
        assertEquals(t3, get(grid, -2, 0));
    }

    @Test
    void SameShapeAndColor() {
        var t1 = new Tile(RED, DIAMOND);
        var t2 = new Tile(RED, DIAMOND);
        var t3 = new Tile(RED, DIAMOND);

        assertThrows(QwirkleException.class,()
                -> {grid.firstAdd(UP, t1, t2, t3);});
    }

    @Test
    void DiffShapeAndColor() {
        var t1 = new Tile(YELLOW, DIAMOND);
        var t2 = new Tile(RED, SQUARE);
        var t3 = new Tile(Color.RED, CROSS);

        assertThrows(QwirkleException.class,()
                -> {grid.firstAdd(UP, t1, t2, t3);});
    }

    @Test
    void MoreThan6Tiles() {
        var t1 = new Tile(YELLOW, DIAMOND);
        var t2 = new Tile(YELLOW, SQUARE);
        var t3 = new Tile(YELLOW, CROSS);
        var t4 = new Tile(YELLOW, STAR);
        var t5 = new Tile(YELLOW, ROUND);
        var t6 = new Tile(YELLOW, PLUS);
        var t7 = new Tile(YELLOW, PLUS);
        assertThrows(QwirkleException.class,()
                -> {grid.firstAdd(UP, t1, t2, t3, t4,
                t5, t6, t7);});
    }

    @Test
    void rules_sonia_a_adapted_to_fail() {
        var t1 = new Tile(RED, ROUND);
        var t2 = new Tile(RED, DIAMOND);
        var t3 = new Tile(RED, DIAMOND);

        assertThrows(QwirkleException.class, ()->{
            grid.firstAdd(UP, t1, t2, t3);
        });
        assertNull(get(grid,0,0));
        assertNull(get(grid,-1,0));
        assertNull(get(grid,-2,0));
    }

    @Test
    void firstAdd_cannot_be_called_twice() {
        Tile redcross = new Tile(RED, CROSS);
        Tile reddiamond = new Tile(RED, DIAMOND);
        grid.firstAdd(UP, redcross, reddiamond);

        assertThrows(QwirkleException.class, () -> grid.firstAdd(DOWN, redcross, reddiamond));
    }

    @Test
    void firstAdd_must_be_called_first_simple() {
        Tile redcross = new Tile(RED, CROSS);
        assertThrows(QwirkleException.class, () -> add(grid, 0, 0, redcross));
    }

    //Get

    @Test
    @DisplayName("get outside the grid should return null, not throw")
    void can_get_tile_outside_virtual_grid() {
        var g = new Grid();
        assertDoesNotThrow(() -> get(g, -250, 500));
        assertNull(get(g, -250, 500));
    }

    //Add without direction

    @Test
    void Add_after_firstAdd_one_tile_on_the_same_position() {
        var tile = new Tile(RED, CROSS);
        grid.firstAdd(RIGHT, tile);

        assertThrows(QwirkleException.class, () -> add(grid, 0, 0, tile));
    }

    @Test
    void Add_when_no_one_is_around() {
        var tile = new Tile(RED, CROSS);
        grid.firstAdd(RIGHT, tile);

        assertThrows(QwirkleException.class, () -> add(grid, 3, 3, tile));
    }

    @Test
    void one_tile_after_rules_sonia_a_Is_OK() {
        var t1 = new Tile(RED, ROUND);
        var t2 = new Tile(RED, DIAMOND);
        var t3 = new Tile(RED, PLUS);
        var t4 = new Tile(RED, SQUARE);
        grid.firstAdd(UP, t1, t2, t3);

        add(grid, -3, 0, t4);

        assertEquals(t4, get(grid, -3, 0));
    }

    @Test
    void one_tile_after_rules_sonia_a_Is_not_OK_Color() {
        var t1 = new Tile(RED, ROUND);
        var t2 = new Tile(RED, DIAMOND);
        var t3 = new Tile(RED, PLUS);
        var t4 = new Tile(Color.BLUE, SQUARE);
        grid.firstAdd(UP, t1, t2, t3);

        assertThrows(QwirkleException.class, () -> add(grid, -3, 0, t4));
    }

    @Test
    void one_tile_after_rules_sonia_a_Is_not_OK_Shape() {
        var t1 = new Tile(RED, ROUND);
        var t2 = new Tile(RED, DIAMOND);
        var t3 = new Tile(RED, PLUS);
        var t4 = new Tile(RED, ROUND);
        grid.firstAdd(UP, t1, t2, t3);

        assertThrows(QwirkleException.class, () -> add(grid, -3, 0, t4));
    }

    @Test
    void one_tile_Same_color_after_a_complete_line() {
        var t1 = new Tile(RED, ROUND);
        var t2 = new Tile(RED, DIAMOND);
        var t3 = new Tile(RED, PLUS);
        var t4 = new Tile(RED, SQUARE);
        var t5 = new Tile(RED, STAR);
        var t6 = new Tile(RED, CROSS);
        var t7 = new Tile(RED, SQUARE);
        grid.firstAdd(UP, t1, t2, t3);
        add(grid, -3, 0, t4);
        add(grid, -4, 0, t5);
        add(grid, -5, 0, t6);


        assertThrows(QwirkleException.class, () -> add(grid, -6, 0, t7));
    }

    @Test
    void one_tile_Same_Shape_after_a_complete_line() {
        var t1 = new Tile(RED, ROUND);
        var t2 = new Tile(YELLOW, ROUND);
        var t3 = new Tile(BLUE, ROUND);
        var t4 = new Tile(GREEN, ROUND);
        var t5 = new Tile(PURPLE, ROUND);
        var t6 = new Tile(ORANGE, ROUND);
        var t7 = new Tile(RED, ROUND);
        grid.firstAdd(UP, t1, t2, t3);
        add(grid, -3, 0, t4);
        add(grid, -4, 0, t5);
        add(grid, -5, 0, t6);


        assertThrows(QwirkleException.class, () -> add(grid, -6, 0, t7));
    }

    @Test
    void one_tile_between_2_tile_same_color() {
        var t1 = new Tile(RED, ROUND);
        var t2 = new Tile(RED, DIAMOND);
        var t3 = new Tile(RED, PLUS);
        var t4 = new Tile(RED, CROSS);
        var t5 = new Tile(RED, SQUARE);
        grid.firstAdd(DOWN, t1);
        add(grid, 0, 1, t2);
        add(grid, 0, 2, t3);
        add(grid, 1, 2, t1);
        add(grid, 2, 2, t2);
        add(grid, 2, 1, t3);
        add(grid, 2, 0, t4);
        add(grid, 1, 0, t5);


        assertEquals(t5, get(grid, 1, 0));
    }

    @Test
    void one_tile_between_2_tile_Different_color() {
        var t1 = new Tile(RED, ROUND);
        var t2 = new Tile(RED, DIAMOND);
        var t3 = new Tile(RED, PLUS);
        var t4 = new Tile(BLUE, PLUS);
        var t5 = new Tile(YELLOW, PLUS);
        var t6 = new Tile(YELLOW, CROSS);
        var t7 = new Tile(YELLOW, DIAMOND);
        var t8 = new Tile(RED, SQUARE);
        grid.firstAdd(DOWN, t1);
        add(grid, 0, 1, t2);
        add(grid, 0, 2, t3);
        add(grid, 1, 2, t4);
        add(grid, 2, 2, t5);
        add(grid, 2, 1, t6);
        add(grid, 2, 0, t7);

        assertThrows(QwirkleException.class, () ->
                add(grid, 1, 0, t8));
    }

    @Test
    void one_tile_between_2_tile_Same_Shape() {
        var t1 = new Tile(RED, SQUARE);
        var t2 = new Tile(RED, DIAMOND);
        var t3 = new Tile(RED, PLUS);
        var t4 = new Tile(BLUE, PLUS);
        var t5 = new Tile(YELLOW, PLUS);
        var t6 = new Tile(YELLOW, CROSS);
        var t7 = new Tile(YELLOW, SQUARE);
        var t8 = new Tile(BLUE, SQUARE);
        grid.firstAdd(DOWN, t1);
        add(grid, 0, 1, t2);
        add(grid, 0, 2, t3);
        add(grid, 1, 2, t4);
        add(grid, 2, 2, t5);
        add(grid, 2, 1, t6);
        add(grid, 2, 0, t7);
        add(grid, 1, 0, t8);

        assertEquals(t8, get(grid, 1, 0));

    }

    @Test
    void one_tile_between_2_tile_Different_Shape() {
        var t1 = new Tile(RED, SQUARE);
        var t2 = new Tile(RED, DIAMOND);
        var t3 = new Tile(RED, PLUS);
        var t4 = new Tile(BLUE, PLUS);
        var t5 = new Tile(YELLOW, PLUS);
        var t6 = new Tile(YELLOW, CROSS);
        var t7 = new Tile(YELLOW, SQUARE);
        var t8 = new Tile(BLUE, STAR);
        grid.firstAdd(DOWN, t1);
        add(grid, 0, 1, t2);
        add(grid, 0, 2, t3);
        add(grid, 1, 2, t4);
        add(grid, 2, 2, t5);
        add(grid, 2, 1, t6);
        add(grid, 2, 0, t7);

        assertThrows(QwirkleException.class, () ->
                add(grid, 1, 0, t8));

    }

    @Test
    void one_tile_between_2_tile_But_DuplicateShape_on_the_line_if_linked() {
        var t1 = new Tile(RED, ROUND);
        var t2 = new Tile(RED, DIAMOND);
        var t3 = new Tile(RED, PLUS);
        var t4 = new Tile(RED, SQUARE);

        grid.firstAdd(DOWN, t1);
        add(grid, 0, 1, t2);
        add(grid, 0, 2, t3);
        add(grid, 1, 2, t1);
        add(grid, 2, 2, t2);
        add(grid, 2, 1, t3);
        add(grid, 2, 0, t1);

        assertThrows(QwirkleException.class, () ->
                add(grid, 1, 0, t4));
    }

    //Add with direction
    @Test
    void rules_cedric_b() {
        var t1 = new Tile(RED, ROUND);
        var t2 = new Tile(RED, DIAMOND);
        var t3 = new Tile(RED, PLUS);
        grid.firstAdd(UP, t1, t2, t3);
        var t4 = new Tile(RED, SQUARE);
        var t5 = new Tile(BLUE, SQUARE);
        var t6 = new Tile(PURPLE, SQUARE);

        add(grid, 1, 0, RIGHT, t4, t5, t6);

        assertEquals(t4, get(grid, 1, 0));
        assertEquals(t5, get(grid, 1, 1));
        assertEquals(t6, get(grid, 1, 2));
    }

    @Test
    void rules_cedric_b_Same_Color_Different_Shape() {
        var t1 = new Tile(RED, ROUND);
        var t2 = new Tile(RED, DIAMOND);
        var t3 = new Tile(RED, PLUS);
        grid.firstAdd(UP, t1, t2, t3);
        var t4 = new Tile(RED, SQUARE);
        var t5 = new Tile(RED, PLUS);
        var t6 = new Tile(RED, DIAMOND);

        add(grid, 1, 0, RIGHT, t4, t5, t6);

        assertEquals(t4, get(grid, 1, 0));
        assertEquals(t5, get(grid, 1, 1));
        assertEquals(t6, get(grid, 1, 2));
    }

    @Test
    void rules_cedric_b_FirstFail_Shape() {
        var t1 = new Tile(RED, ROUND);
        var t2 = new Tile(RED, DIAMOND);
        var t3 = new Tile(RED, PLUS);
        grid.firstAdd(UP, t1, t2, t3);
        var t4 = new Tile(RED, STAR);
        var t5 = new Tile(BLUE, SQUARE);
        var t6 = new Tile(PURPLE, SQUARE);

        assertThrows(QwirkleException.class, () ->
                add(grid, 1, 0, RIGHT, t4, t5, t6));
    }

    @Test
    void rules_cedric_b_FirstFail_Color() {
        var t1 = new Tile(RED, ROUND);
        var t2 = new Tile(RED, DIAMOND);
        var t3 = new Tile(RED, PLUS);
        grid.firstAdd(UP, t1, t2, t3);
        var t4 = new Tile(YELLOW, SQUARE);
        var t5 = new Tile(BLUE, SQUARE);
        var t6 = new Tile(PURPLE, SQUARE);

        assertThrows(QwirkleException.class, () ->
                add(grid, 1, 0, RIGHT, t4, t5, t6));
    }

    @Test
    void rules_cedric_b_LastFail_Shape() {
        var t1 = new Tile(RED, ROUND);
        var t2 = new Tile(RED, DIAMOND);
        var t3 = new Tile(RED, PLUS);
        grid.firstAdd(UP, t1, t2, t3);
        var t4 = new Tile(RED, SQUARE);
        var t5 = new Tile(BLUE, SQUARE);
        var t6 = new Tile(PURPLE, STAR);

        assertThrows(QwirkleException.class, () ->
                add(grid, 1, 0, RIGHT, t4, t5, t6));
    }

    @Test
    void rules_cedric_b_LastFail_Color() {
        var t1 = new Tile(RED, ROUND);
        var t2 = new Tile(RED, DIAMOND);
        var t3 = new Tile(RED, PLUS);
        grid.firstAdd(UP, t1, t2, t3);
        var t4 = new Tile(RED, SQUARE);
        var t5 = new Tile(BLUE, SQUARE);
        var t6 = new Tile(RED, SQUARE);

        assertThrows(QwirkleException.class, () ->
                add(grid, 1, 0, RIGHT, t4, t5, t6));

    }

    @Test
    void rules_cedric_b_Fail_SAMEShapeColor() {
        var t1 = new Tile(RED, ROUND);
        var t2 = new Tile(RED, DIAMOND);
        var t3 = new Tile(RED, PLUS);
        grid.firstAdd(UP, t1, t2, t3);
        var t4 = new Tile(RED, SQUARE);
        var t5 = new Tile(RED, SQUARE);
        var t6 = new Tile(RED, SQUARE);

        assertThrows(QwirkleException.class, () ->
                add(grid, 1, 0, RIGHT, t4, t5, t6));

    }

    @Test
    void rules_cedric_b_Fail_SAMEDirection_Than_sonia_a() {
        var t1 = new Tile(RED, ROUND);
        var t2 = new Tile(RED, DIAMOND);
        var t3 = new Tile(RED, PLUS);
        grid.firstAdd(UP, t1, t2, t3);
        var t4 = new Tile(RED, SQUARE);
        var t5 = new Tile(BLUE, SQUARE);
        var t6 = new Tile(PURPLE, SQUARE);

        assertThrows(QwirkleException.class, () ->
                add(grid, 1, 0, DOWN, t4, t5, t6));

    }

    @Test
    void rules_elvire_c() {
        var t1 = new Tile(RED, ROUND);
        var t2 = new Tile(RED, DIAMOND);
        var t3 = new Tile(RED, PLUS);
        grid.firstAdd(UP, t1, t2, t3);
        var t4 = new Tile(RED, SQUARE);
        var t5 = new Tile(BLUE, SQUARE);
        var t6 = new Tile(PURPLE, SQUARE);
        add(grid, 1, 0, RIGHT, t4, t5, t6);
        var t7 = new Tile(BLUE, ROUND);

        add(grid, 0, 1, t7);


        assertEquals(t7, get(grid, 0, 1));
    }

    @Test
    void rules_elvire_c_RED_SQUARE_OK() {
        var t1 = new Tile(RED, ROUND);
        var t2 = new Tile(RED, DIAMOND);
        var t3 = new Tile(RED, PLUS);
        grid.firstAdd(UP, t1, t2, t3);
        var t4 = new Tile(RED, SQUARE);
        var t5 = new Tile(BLUE, SQUARE);
        var t6 = new Tile(PURPLE, SQUARE);
        add(grid, 1, 0, RIGHT, t4, t5, t6);
        var t7 = new Tile(RED, SQUARE);

        add(grid, 0, 1, UP, t7);


        assertEquals(t7, get(grid, 0, 1));
    }

    @Test
    void rules_elvire_c_Another_Direction_OK() {
        var t1 = new Tile(RED, ROUND);
        var t2 = new Tile(RED, DIAMOND);
        var t3 = new Tile(RED, PLUS);
        grid.firstAdd(UP, t1, t2, t3);
        var t4 = new Tile(RED, SQUARE);
        var t5 = new Tile(BLUE, SQUARE);
        var t6 = new Tile(PURPLE, SQUARE);
        add(grid, 1, 0, RIGHT, t4, t5, t6);
        var t7 = new Tile(BLUE, ROUND);

        add(grid, 2, 1, UP, t7);


        assertEquals(t7, get(grid, 2, 1));
    }

    @Test
    void rules_elvire_c_Fail_Shape() {
        var t1 = new Tile(RED, ROUND);
        var t2 = new Tile(RED, DIAMOND);
        var t3 = new Tile(RED, PLUS);
        grid.firstAdd(UP, t1, t2, t3);
        var t4 = new Tile(RED, SQUARE);
        var t5 = new Tile(BLUE, SQUARE);
        var t6 = new Tile(PURPLE, SQUARE);
        add(grid, 1, 0, RIGHT, t4, t5, t6);
        var t7 = new Tile(BLUE, SQUARE);

        assertThrows(QwirkleException.class, () ->
                add(grid, 0, 1, UP, t7));
    }

    @Test
    void rules_elvire_c_Fail_Color_Horizontal() {
        var t1 = new Tile(RED, ROUND);
        var t2 = new Tile(RED, DIAMOND);
        var t3 = new Tile(RED, PLUS);
        grid.firstAdd(UP, t1, t2, t3);
        var t4 = new Tile(RED, SQUARE);
        var t5 = new Tile(BLUE, SQUARE);
        var t6 = new Tile(PURPLE, SQUARE);
        add(grid, 1, 0, RIGHT, t4, t5, t6);
        var t7 = new Tile(RED, ROUND);

        assertThrows(QwirkleException.class, () ->
                add(grid, 0, 1, UP, t7));
    }

    @Test
    void rules_vincent_d() {
        var t1 = new Tile(RED, ROUND);
        var t2 = new Tile(RED, DIAMOND);
        var t3 = new Tile(RED, PLUS);
        grid.firstAdd(UP, t1, t2, t3);
        var t4 = new Tile(RED, SQUARE);
        var t5 = new Tile(BLUE, SQUARE);
        var t6 = new Tile(PURPLE, SQUARE);
        add(grid, 1, 0, RIGHT, t4, t5, t6);
        var t7 = new Tile(BLUE, ROUND);
        add(grid, 0, 1, UP, t7);
        var t8 = new Tile(GREEN, PLUS);
        var t9 = new Tile(GREEN, DIAMOND);

        add(grid, -2, -1, DOWN, t8, t9);

        assertEquals(t8, get(grid, -2, -1));
        assertEquals(t9, get(grid, -1, -1));
    }

    @Test
    void rules_vincent_d_T8_T9_Swap_RED_color_OK() {
        var t1 = new Tile(RED, ROUND);
        var t2 = new Tile(RED, DIAMOND);
        var t3 = new Tile(RED, PLUS);
        grid.firstAdd(UP, t1, t2, t3);
        var t4 = new Tile(RED, SQUARE);
        var t5 = new Tile(BLUE, SQUARE);
        var t6 = new Tile(PURPLE, SQUARE);
        add(grid, 1, 0, RIGHT, t4, t5, t6);
        var t7 = new Tile(BLUE, ROUND);
        add(grid, 0, 1, UP, t7);
        var t8 = new Tile(RED, DIAMOND);
        var t9 = new Tile(RED, PLUS);

        add(grid, -2, -1, DOWN, t8, t9);

        assertEquals(t8, get(grid, -2, -1));
        assertEquals(t9, get(grid, -1, -1));
    }

    @Test
    void rules_vincent_d_FirstFail_Shape() {
        var t1 = new Tile(RED, ROUND);
        var t2 = new Tile(RED, DIAMOND);
        var t3 = new Tile(RED, PLUS);
        grid.firstAdd(UP, t1, t2, t3);
        var t4 = new Tile(RED, SQUARE);
        var t5 = new Tile(BLUE, SQUARE);
        var t6 = new Tile(PURPLE, SQUARE);
        add(grid, 1, 0, RIGHT, t4, t5, t6);
        var t7 = new Tile(BLUE, ROUND);
        add(grid, 0, 1, UP, t7);
        var t8 = new Tile(GREEN, DIAMOND);
        var t9 = new Tile(GREEN, DIAMOND);

        assertThrows(QwirkleException.class, () ->
                add(grid, -2, -1, DOWN, t8, t9));

    }

    @Test
    void rules_vincent_d_FirstFail_Color() {
        var t1 = new Tile(RED, ROUND);
        var t2 = new Tile(RED, DIAMOND);
        var t3 = new Tile(RED, PLUS);
        grid.firstAdd(UP, t1, t2, t3);
        var t4 = new Tile(RED, SQUARE);
        var t5 = new Tile(BLUE, SQUARE);
        var t6 = new Tile(PURPLE, SQUARE);
        add(grid, 1, 0, RIGHT, t4, t5, t6);
        var t7 = new Tile(BLUE, ROUND);
        add(grid, 0, 1, UP, t7);
        var t8 = new Tile(RED, PLUS);
        var t9 = new Tile(GREEN, DIAMOND);

        assertThrows(QwirkleException.class, () ->
                add(grid, -2, -1, DOWN, t8, t9));

    }

    @Test
    void rules_vincent_d_LastFail_Shape() {
        var t1 = new Tile(RED, ROUND);
        var t2 = new Tile(RED, DIAMOND);
        var t3 = new Tile(RED, PLUS);
        grid.firstAdd(UP, t1, t2, t3);
        var t4 = new Tile(RED, SQUARE);
        var t5 = new Tile(BLUE, SQUARE);
        var t6 = new Tile(PURPLE, SQUARE);
        add(grid, 1, 0, RIGHT, t4, t5, t6);
        var t7 = new Tile(BLUE, ROUND);
        add(grid, 0, 1, UP, t7);
        var t8 = new Tile(GREEN, PLUS);
        var t9 = new Tile(GREEN, SQUARE);

        assertThrows(QwirkleException.class, () ->
                add(grid, -2, -1, DOWN, t8, t9));

    }

    @Test
    void rules_vincent_d_LastFail_Color() {
        var t1 = new Tile(RED, ROUND);
        var t2 = new Tile(RED, DIAMOND);
        var t3 = new Tile(RED, PLUS);
        grid.firstAdd(UP, t1, t2, t3);
        var t4 = new Tile(RED, SQUARE);
        var t5 = new Tile(BLUE, SQUARE);
        var t6 = new Tile(PURPLE, SQUARE);
        add(grid, 1, 0, RIGHT, t4, t5, t6);
        var t7 = new Tile(BLUE, ROUND);
        add(grid, 0, 1, UP, t7);
        var t8 = new Tile(GREEN, PLUS);
        var t9 = new Tile(BLUE, DIAMOND);

        assertThrows(QwirkleException.class, () ->
                add(grid, -2, -1, DOWN, t8, t9));

    }

    @Test
    void two_tile_between_tile_same_color() {
        var t1 = new Tile(RED, ROUND);
        var t2 = new Tile(YELLOW, ROUND);
        var t3 = new Tile(BLUE, ROUND);
        var t4 = new Tile(BLUE, SQUARE);
        var t5 = new Tile(BLUE, STAR);
        var t6 = new Tile(BLUE, DIAMOND);
        var t7 = new Tile(YELLOW, DIAMOND);
        var t8 = new Tile(RED, DIAMOND);
        var t9 = new Tile(RED, PLUS);
        var t10 = new Tile(RED, CROSS);
        var t11 = new Tile(RED, SQUARE);
        grid.firstAdd(DOWN, t1);
        add(grid, 0, 1, t2);
        add(grid, 0, 2, t3);
        add(grid, 1, 2, t4);
        add(grid, 2, 2, t5);
        add(grid, 3, 2, t6);
        add(grid, 3, 1, t7);
        add(grid, 3, 0, t8);
        add(grid, 4, 0, t9);

        add(grid, 1, 0,DOWN, t10, t11);

        assertEquals(t10, get(grid, 1, 0));
        assertEquals(t11, get(grid, 2, 0));
    }

    @Test
    void two_tile_between_tile_Same_color_More_than_6() {
        var t1 = new Tile(RED, ROUND);
        var t2 = new Tile(YELLOW, ROUND);
        var t3 = new Tile(BLUE, ROUND);
        var t4 = new Tile(BLUE, SQUARE);
        var t5 = new Tile(BLUE, STAR);
        var t6 = new Tile(BLUE, DIAMOND);
        var t7 = new Tile(YELLOW, DIAMOND);
        var t8 = new Tile(RED, DIAMOND);
        var t9 = new Tile(RED, PLUS);
        var t10 = new Tile(RED, ROUND);
        var t11 = new Tile(RED, STAR);
        var t12 = new Tile(RED, CROSS);
        var t13 = new Tile(RED, SQUARE);
        grid.firstAdd(DOWN, t1);
        add(grid, 0, 1, t2);
        add(grid, 0, 2, t3);
        add(grid, 1, 2, t4);
        add(grid, 2, 2, t5);
        add(grid, 3, 2, t6);
        add(grid, 3, 1, t7);
        add(grid, 3, 0, t8);
        add(grid, 4, 0, t9);
        add(grid, 5, 0, t10);
        add(grid, 6, 0, t11);

        assertThrows(QwirkleException.class, () ->
                add(grid, 1, 0,DOWN, t12, t13));
    }

    @Test
    void two_tile_between_tile_Diff_color() {
        var t1 = new Tile(RED, ROUND);
        var t2 = new Tile(YELLOW, ROUND);
        var t3 = new Tile(BLUE, ROUND);
        var t4 = new Tile(BLUE, SQUARE);
        var t5 = new Tile(BLUE, STAR);
        var t6 = new Tile(BLUE, DIAMOND);
        var t7 = new Tile(YELLOW, DIAMOND);
        var t8 = new Tile(RED, DIAMOND);
        var t9 = new Tile(RED, PLUS);
        var t10 = new Tile(BLUE, CROSS);
        var t11 = new Tile(GREEN, CROSS);
        grid.firstAdd(DOWN, t1);
        add(grid, 0, 1, t2);
        add(grid, 0, 2, t3);
        add(grid, 1, 2, t4);
        add(grid, 2, 2, t5);
        add(grid, 3, 2, t6);
        add(grid, 3, 1, t7);
        add(grid, 3, 0, t8);
        add(grid, 4, 0, t9);

        assertThrows(QwirkleException.class, () ->
                add(grid, 1, 0,DOWN, t10, t11));
    }

    @Test
    void two_tile_between_tile_Same_Shape() {
        var t1 = new Tile(RED, ROUND);
        var t2 = new Tile(YELLOW, ROUND);
        var t3 = new Tile(BLUE, ROUND);
        var t4 = new Tile(BLUE, SQUARE);
        var t5 = new Tile(BLUE, STAR);
        var t6 = new Tile(BLUE, DIAMOND);
        var t7 = new Tile(BLUE, STAR);
        var t8 = new Tile(BLUE, ROUND);
        var t9 = new Tile(YELLOW, ROUND);
        var t10 = new Tile(GREEN, ROUND);
        var t11 = new Tile(PURPLE, ROUND);
        grid.firstAdd(DOWN, t1);
        add(grid, 0, 1, t2);
        add(grid, 0, 2, t3);
        add(grid, 1, 2, t4);
        add(grid, 2, 2, t5);
        add(grid, 3, 2, t6);
        add(grid, 3, 1, t7);
        add(grid, 3, 0, t8);
        add(grid, 4, 0, t9);

        add(grid, 1, 0,DOWN, t10, t11);

        assertEquals(t10, get(grid, 1, 0));
        assertEquals(t11, get(grid, 2, 0));
    }

    @Test
    void two_tile_between_tile_Diff_Shape() {
        var t1 = new Tile(RED, ROUND);
        var t2 = new Tile(YELLOW, ROUND);
        var t3 = new Tile(BLUE, ROUND);
        var t4 = new Tile(BLUE, SQUARE);
        var t5 = new Tile(BLUE, STAR);
        var t6 = new Tile(BLUE, DIAMOND);
        var t7 = new Tile(BLUE, STAR);
        var t8 = new Tile(BLUE, ROUND);
        var t9 = new Tile(YELLOW, ROUND);
        var t10 = new Tile(GREEN, STAR);
        var t11 = new Tile(PURPLE, PLUS);
        grid.firstAdd(DOWN, t1);
        add(grid, 0, 1, t2);
        add(grid, 0, 2, t3);
        add(grid, 1, 2, t4);
        add(grid, 2, 2, t5);
        add(grid, 3, 2, t6);
        add(grid, 3, 1, t7);
        add(grid, 3, 0, t8);
        add(grid, 4, 0, t9);

        assertThrows(QwirkleException.class, () ->
                add(grid, 1, 0,DOWN, t10, t11));
    }

    //ADD AtPosition

    @Test
    void rules_sonia_e() {
        var t1 = new Tile(RED, ROUND);
        var t2 = new Tile(RED, DIAMOND);
        var t3 = new Tile(RED, PLUS);
        grid.firstAdd(UP, t1, t2, t3);
        var t4 = new Tile(RED, SQUARE);
        var t5 = new Tile(BLUE, SQUARE);
        var t6 = new Tile(PURPLE, SQUARE);
        add(grid, 1, 0, RIGHT, t4, t5, t6);
        var t7 = new Tile(BLUE, ROUND);
        add(grid, 0, 1, UP, t7);
        var t8 = new Tile(GREEN, PLUS);
        var t9 = new Tile(GREEN, DIAMOND);
        add(grid, -2, -1, DOWN, t8, t9);
        var t10 = new Tile(GREEN, STAR);
        var t11 = new Tile(GREEN, ROUND);
        TileAtPosition t10Pos1 = createTileAtpos(-3, -1, t10);
        TileAtPosition t11Pos2 = createTileAtpos(0, -1, t11);

        grid.add(t10Pos1, t11Pos2);

        assertEquals(t10, get(grid, -3, -1));
        assertEquals(t11, get(grid, 0, -1));
    }

    @Test
    void rules_sonia_e_FirstFail_Color() {
        var t1 = new Tile(RED, ROUND);
        var t2 = new Tile(RED, DIAMOND);
        var t3 = new Tile(RED, PLUS);
        grid.firstAdd(UP, t1, t2, t3);
        var t4 = new Tile(RED, SQUARE);
        var t5 = new Tile(BLUE, SQUARE);
        var t6 = new Tile(PURPLE, SQUARE);
        add(grid, 1, 0, RIGHT, t4, t5, t6);
        var t7 = new Tile(BLUE, ROUND);
        add(grid, 0, 1, UP, t7);
        var t8 = new Tile(GREEN, PLUS);
        var t9 = new Tile(GREEN, DIAMOND);
        add(grid, -2, -1, DOWN, t8, t9);
        var t10 = new Tile(RED, STAR);
        var t11 = new Tile(GREEN, ROUND);
        TileAtPosition t10Pos1 = createTileAtpos(-3, -1, t10);
        TileAtPosition t11Pos2 = createTileAtpos(0, -1, t11);

        assertThrows(QwirkleException.class, () ->
                grid.add(t10Pos1, t11Pos2));
    }

    @Test
    void rules_sonia_e_LastFail_Color() {
        var t1 = new Tile(RED, ROUND);
        var t2 = new Tile(RED, DIAMOND);
        var t3 = new Tile(RED, PLUS);
        grid.firstAdd(UP, t1, t2, t3);
        var t4 = new Tile(RED, SQUARE);
        var t5 = new Tile(BLUE, SQUARE);
        var t6 = new Tile(PURPLE, SQUARE);
        add(grid, 1, 0, RIGHT, t4, t5, t6);
        var t7 = new Tile(BLUE, ROUND);
        add(grid, 0, 1, UP, t7);
        var t8 = new Tile(GREEN, PLUS);
        var t9 = new Tile(GREEN, DIAMOND);
        add(grid, -2, -1, DOWN, t8, t9);
        var t10 = new Tile(GREEN, STAR);
        var t11 = new Tile(PURPLE, ROUND);
        TileAtPosition t10Pos1 = createTileAtpos(-3, -1, t10);
        TileAtPosition t11Pos2 = createTileAtpos(0, -1, t11);

        assertThrows(QwirkleException.class, () ->
                grid.add(t10Pos1, t11Pos2));
    }

    @Test
    void rules_sonia_e_LastFail_Shape() {
        var t1 = new Tile(RED, ROUND);
        var t2 = new Tile(RED, DIAMOND);
        var t3 = new Tile(RED, PLUS);
        grid.firstAdd(UP, t1, t2, t3);
        var t4 = new Tile(RED, SQUARE);
        var t5 = new Tile(BLUE, SQUARE);
        var t6 = new Tile(PURPLE, SQUARE);
        add(grid, 1, 0, RIGHT, t4, t5, t6);
        var t7 = new Tile(BLUE, ROUND);
        add(grid, 0, 1, UP, t7);
        var t8 = new Tile(GREEN, PLUS);
        var t9 = new Tile(GREEN, DIAMOND);
        add(grid, -2, -1, DOWN, t8, t9);
        var t10 = new Tile(GREEN, STAR);
        var t11 = new Tile(GREEN, CROSS);
        TileAtPosition t10Pos1 = createTileAtpos(-3, -1, t10);
        TileAtPosition t11Pos2 = createTileAtpos(0, -1, t11);

        assertThrows(QwirkleException.class, () ->
                grid.add(t10Pos1, t11Pos2));
    }

    @Test
    void rules_sonia_e_FailPosition() {
        var t1 = new Tile(RED, ROUND);
        var t2 = new Tile(RED, DIAMOND);
        var t3 = new Tile(RED, PLUS);
        grid.firstAdd(UP, t1, t2, t3);
        var t4 = new Tile(RED, SQUARE);
        var t5 = new Tile(BLUE, SQUARE);
        var t6 = new Tile(PURPLE, SQUARE);
        add(grid, 1, 0, RIGHT, t4, t5, t6);
        var t7 = new Tile(BLUE, ROUND);
        add(grid, 0, 1, UP, t7);
        var t8 = new Tile(GREEN, PLUS);
        var t9 = new Tile(GREEN, DIAMOND);
        add(grid, -2, -1, DOWN, t8, t9);
        var t10 = new Tile(GREEN, STAR);
        var t11 = new Tile(GREEN, ROUND);
        TileAtPosition t10Pos1 = createTileAtpos(-2, -2, t10);
        TileAtPosition t11Pos2 = createTileAtpos(0, -1, t11);

        assertThrows(QwirkleException.class, () ->
                grid.add(t10Pos1, t11Pos2));
    }

    //The last testes for Grid
    @Test
    void rules_cedric_f() {
        var t1 = new Tile(RED, ROUND);
        var t2 = new Tile(RED, DIAMOND);
        var t3 = new Tile(RED, PLUS);
        grid.firstAdd(UP, t1, t2, t3);
        var t4 = new Tile(RED, SQUARE);
        var t5 = new Tile(BLUE, SQUARE);
        var t6 = new Tile(PURPLE, SQUARE);
        add(grid, 1, 0, RIGHT, t4, t5, t6);
        var t7 = new Tile(BLUE, ROUND);
        add(grid, 0, 1, UP, t7);
        var t8 = new Tile(GREEN, PLUS);
        var t9 = new Tile(GREEN, DIAMOND);
        add(grid, -2, -1, DOWN, t8, t9);
        var t10 = new Tile(GREEN, STAR);
        var t11 = new Tile(GREEN, ROUND);
        var t10Pos1 = createTileAtpos(-3, -1, t10);
        var t11Pos2 = createTileAtpos(0, -1, t11);
        grid.add(t10Pos1, t11Pos2);
        var t12 = new Tile(ORANGE, SQUARE);
        var t13 = new Tile(RED, SQUARE);

        add(grid, 1, 3, DOWN, t12, t13);

        assertEquals(t12, get(grid, 1, 3));
        assertEquals(t13, get(grid, 2, 3));
    }

    @Test
    void rules_cedric_f_Same_Color() {
        var t1 = new Tile(RED, ROUND);
        var t2 = new Tile(RED, DIAMOND);
        var t3 = new Tile(RED, PLUS);
        grid.firstAdd(UP, t1, t2, t3);
        var t4 = new Tile(RED, SQUARE);
        var t5 = new Tile(BLUE, SQUARE);
        var t6 = new Tile(PURPLE, SQUARE);
        add(grid, 1, 0, RIGHT, t4, t5, t6);
        var t7 = new Tile(BLUE, ROUND);
        add(grid, 0, 1, UP, t7);
        var t8 = new Tile(GREEN, PLUS);
        var t9 = new Tile(GREEN, DIAMOND);
        add(grid, -2, -1, DOWN, t8, t9);
        var t10 = new Tile(GREEN, STAR);
        var t11 = new Tile(GREEN, ROUND);
        var t10Pos1 = createTileAtpos(-3, -1, t10);
        var t11Pos2 = createTileAtpos(0, -1, t11);
        grid.add(t10Pos1, t11Pos2);
        var t12 = new Tile(ORANGE, SQUARE);
        var t13 = new Tile(ORANGE, ROUND);

        add(grid, 1, 3, DOWN, t12, t13);

        assertEquals(t12, get(grid, 1, 3));
        assertEquals(t13, get(grid, 2, 3));
    }

    @Test
    void rules_cedric_f_FirstFail_Shape() {
        var t1 = new Tile(RED, ROUND);
        var t2 = new Tile(RED, DIAMOND);
        var t3 = new Tile(RED, PLUS);
        grid.firstAdd(UP, t1, t2, t3);
        var t4 = new Tile(RED, SQUARE);
        var t5 = new Tile(BLUE, SQUARE);
        var t6 = new Tile(PURPLE, SQUARE);
        add(grid, 1, 0, RIGHT, t4, t5, t6);
        var t7 = new Tile(BLUE, ROUND);
        add(grid, 0, 1, UP, t7);
        var t8 = new Tile(GREEN, PLUS);
        var t9 = new Tile(GREEN, DIAMOND);
        add(grid, -2, -1, DOWN, t8, t9);
        var t10 = new Tile(GREEN, STAR);
        var t11 = new Tile(GREEN, ROUND);
        var t10Pos1 = createTileAtpos(-3, -1, t10);
        var t11Pos2 = createTileAtpos(0, -1, t11);
        grid.add(t10Pos1, t11Pos2);
        var t12 = new Tile(ORANGE, ROUND);
        var t13 = new Tile(RED, SQUARE);

        assertThrows(QwirkleException.class, () ->
                add(grid, 1, 3, DOWN, t12, t13));
    }

    @Test
    void rules_cedric_f_FirstFail_Color() {
        var t1 = new Tile(RED, ROUND);
        var t2 = new Tile(RED, DIAMOND);
        var t3 = new Tile(RED, PLUS);
        grid.firstAdd(UP, t1, t2, t3);
        var t4 = new Tile(RED, SQUARE);
        var t5 = new Tile(BLUE, SQUARE);
        var t6 = new Tile(PURPLE, SQUARE);
        add(grid, 1, 0, RIGHT, t4, t5, t6);
        var t7 = new Tile(BLUE, ROUND);
        add(grid, 0, 1, UP, t7);
        var t8 = new Tile(GREEN, PLUS);
        var t9 = new Tile(GREEN, DIAMOND);
        add(grid, -2, -1, DOWN, t8, t9);
        var t10 = new Tile(GREEN, STAR);
        var t11 = new Tile(GREEN, ROUND);
        var t10Pos1 = createTileAtpos(-3, -1, t10);
        var t11Pos2 = createTileAtpos(0, -1, t11);
        grid.add(t10Pos1, t11Pos2);
        var t12 = new Tile(BLUE, SQUARE);
        var t13 = new Tile(RED, SQUARE);

        assertThrows(QwirkleException.class, () ->
                add(grid, 1, 3, DOWN, t12, t13));
    }

    @Test
    void rules_cedric_f_LastFail_Shape() {
        var t1 = new Tile(RED, ROUND);
        var t2 = new Tile(RED, DIAMOND);
        var t3 = new Tile(RED, PLUS);
        grid.firstAdd(UP, t1, t2, t3);
        var t4 = new Tile(RED, SQUARE);
        var t5 = new Tile(BLUE, SQUARE);
        var t6 = new Tile(PURPLE, SQUARE);
        add(grid, 1, 0, RIGHT, t4, t5, t6);
        var t7 = new Tile(BLUE, ROUND);
        add(grid, 0, 1, UP, t7);
        var t8 = new Tile(GREEN, PLUS);
        var t9 = new Tile(GREEN, DIAMOND);
        add(grid, -2, -1, DOWN, t8, t9);
        var t10 = new Tile(GREEN, STAR);
        var t11 = new Tile(GREEN, ROUND);
        var t10Pos1 = createTileAtpos(-3, -1, t10);
        var t11Pos2 = createTileAtpos(0, -1, t11);
        grid.add(t10Pos1, t11Pos2);
        var t12 = new Tile(ORANGE, SQUARE);
        var t13 = new Tile(RED, STAR);

        assertThrows(QwirkleException.class, () ->
                add(grid, 1, 3, DOWN, t12, t13));
    }

    @Test
    void rules_cedric_f_LastFail_Color() {
        var t1 = new Tile(RED, ROUND);
        var t2 = new Tile(RED, DIAMOND);
        var t3 = new Tile(RED, PLUS);
        grid.firstAdd(UP, t1, t2, t3);
        var t4 = new Tile(RED, SQUARE);
        var t5 = new Tile(BLUE, SQUARE);
        var t6 = new Tile(PURPLE, SQUARE);
        add(grid, 1, 0, RIGHT, t4, t5, t6);
        var t7 = new Tile(BLUE, ROUND);
        add(grid, 0, 1, UP, t7);
        var t8 = new Tile(GREEN, PLUS);
        var t9 = new Tile(GREEN, DIAMOND);
        add(grid, -2, -1, DOWN, t8, t9);
        var t10 = new Tile(GREEN, STAR);
        var t11 = new Tile(GREEN, ROUND);
        var t10Pos1 = createTileAtpos(-3, -1, t10);
        var t11Pos2 = createTileAtpos(0, -1, t11);
        grid.add(t10Pos1, t11Pos2);
        var t12 = new Tile(ORANGE, SQUARE);
        var t13 = new Tile(ORANGE, SQUARE);

        assertThrows(QwirkleException.class, () ->
                add(grid, 1, 3, DOWN, t12, t13));
    }

    @Test
    void rules_cedric_f_Fail_Direction_Right() {
        var t1 = new Tile(RED, ROUND);
        var t2 = new Tile(RED, DIAMOND);
        var t3 = new Tile(RED, PLUS);
        grid.firstAdd(UP, t1, t2, t3);
        var t4 = new Tile(RED, SQUARE);
        var t5 = new Tile(BLUE, SQUARE);
        var t6 = new Tile(PURPLE, SQUARE);
        add(grid, 1, 0, RIGHT, t4, t5, t6);
        var t7 = new Tile(BLUE, ROUND);
        add(grid, 0, 1, UP, t7);
        var t8 = new Tile(GREEN, PLUS);
        var t9 = new Tile(GREEN, DIAMOND);
        add(grid, -2, -1, DOWN, t8, t9);
        var t10 = new Tile(GREEN, STAR);
        var t11 = new Tile(GREEN, ROUND);
        var t10Pos1 = createTileAtpos(-3, -1, t10);
        var t11Pos2 = createTileAtpos(0, -1, t11);
        grid.add(t10Pos1, t11Pos2);
        var t12 = new Tile(ORANGE, SQUARE);
        var t13 = new Tile(RED, SQUARE);

        assertThrows(QwirkleException.class, () ->
                add(grid, 1, 3, RIGHT, t12, t13));
    }

    @Test
    void rules_elvire_g() {
        var t1 = new Tile(RED, ROUND);
        var t2 = new Tile(RED, DIAMOND);
        var t3 = new Tile(RED, PLUS);
        grid.firstAdd(UP, t1, t2, t3);
        var t4 = new Tile(RED, SQUARE);
        var t5 = new Tile(BLUE, SQUARE);
        var t6 = new Tile(PURPLE, SQUARE);
        add(grid, 1, 0, RIGHT, t4, t5, t6);
        var t7 = new Tile(BLUE, ROUND);
        add(grid, 0, 1, UP, t7);
        var t8 = new Tile(GREEN, PLUS);
        var t9 = new Tile(GREEN, DIAMOND);
        add(grid, -2, -1, DOWN, t8, t9);
        var t10 = new Tile(GREEN, STAR);
        var t11 = new Tile(GREEN, ROUND);
        var t10Pos1 = createTileAtpos(-3, -1, t10);
        var t11Pos2 = createTileAtpos(0, -1, t11);
        grid.add(t10Pos1, t11Pos2);
        var t12 = new Tile(ORANGE, SQUARE);
        var t13 = new Tile(RED, SQUARE);
        add(grid, 1, 3, DOWN, t12, t13);
        var t14 = new Tile(YELLOW, STAR);
        var t15 = new Tile(ORANGE, STAR);

        add(grid, -3, -2, LEFT, t14, t15);

        assertEquals(t14, get(grid, -3, -2));
        assertEquals(t15, get(grid, -3, -3));
    }

    @Test
    void rules_elvire_g_Direction_UP() {
        var t1 = new Tile(RED, ROUND);
        var t2 = new Tile(RED, DIAMOND);
        var t3 = new Tile(RED, PLUS);
        grid.firstAdd(UP, t1, t2, t3);
        var t4 = new Tile(RED, SQUARE);
        var t5 = new Tile(BLUE, SQUARE);
        var t6 = new Tile(PURPLE, SQUARE);
        add(grid, 1, 0, RIGHT, t4, t5, t6);
        var t7 = new Tile(BLUE, ROUND);
        add(grid, 0, 1, UP, t7);
        var t8 = new Tile(GREEN, PLUS);
        var t9 = new Tile(GREEN, DIAMOND);
        add(grid, -2, -1, DOWN, t8, t9);
        var t10 = new Tile(GREEN, STAR);
        var t11 = new Tile(GREEN, ROUND);
        var t10Pos1 = createTileAtpos(-3, -1, t10);
        var t11Pos2 = createTileAtpos(0, -1, t11);
        grid.add(t10Pos1, t11Pos2);
        var t12 = new Tile(ORANGE, SQUARE);
        var t13 = new Tile(RED, SQUARE);
        add(grid, 1, 3, DOWN, t12, t13);
        var t14 = new Tile(YELLOW, STAR);
        var t15 = new Tile(ORANGE, STAR);

        add(grid, -3, -2, UP, t14, t15);

        assertEquals(t14, get(grid, -3, -2));
        assertEquals(t15, get(grid, -4, -2));
    }

    @Test
    void rules_elvire_g_Green() {
        var t1 = new Tile(RED, ROUND);
        var t2 = new Tile(RED, DIAMOND);
        var t3 = new Tile(RED, PLUS);
        grid.firstAdd(UP, t1, t2, t3);
        var t4 = new Tile(RED, SQUARE);
        var t5 = new Tile(BLUE, SQUARE);
        var t6 = new Tile(PURPLE, SQUARE);
        add(grid, 1, 0, RIGHT, t4, t5, t6);
        var t7 = new Tile(BLUE, ROUND);
        add(grid, 0, 1, UP, t7);
        var t8 = new Tile(GREEN, PLUS);
        var t9 = new Tile(GREEN, DIAMOND);
        add(grid, -2, -1, DOWN, t8, t9);
        var t10 = new Tile(GREEN, STAR);
        var t11 = new Tile(GREEN, ROUND);
        var t10Pos1 = createTileAtpos(-3, -1, t10);
        var t11Pos2 = createTileAtpos(0, -1, t11);
        grid.add(t10Pos1, t11Pos2);
        var t12 = new Tile(ORANGE, SQUARE);
        var t13 = new Tile(RED, SQUARE);
        add(grid, 1, 3, DOWN, t12, t13);
        var t14 = new Tile(GREEN, SQUARE);
        var t15 = new Tile(GREEN, PLUS);

        add(grid, -3, -2, LEFT, t14, t15);

        assertEquals(t14, get(grid, -3, -2));
        assertEquals(t15, get(grid, -3, -3));
    }

    @Test
    void rules_elvire_g_FirstFail_Shape() {
        var t1 = new Tile(RED, ROUND);
        var t2 = new Tile(RED, DIAMOND);
        var t3 = new Tile(RED, PLUS);
        grid.firstAdd(UP, t1, t2, t3);
        var t4 = new Tile(RED, SQUARE);
        var t5 = new Tile(BLUE, SQUARE);
        var t6 = new Tile(PURPLE, SQUARE);
        add(grid, 1, 0, RIGHT, t4, t5, t6);
        var t7 = new Tile(BLUE, ROUND);
        add(grid, 0, 1, UP, t7);
        var t8 = new Tile(GREEN, PLUS);
        var t9 = new Tile(GREEN, DIAMOND);
        add(grid, -2, -1, DOWN, t8, t9);
        var t10 = new Tile(GREEN, STAR);
        var t11 = new Tile(GREEN, ROUND);
        var t10Pos1 = createTileAtpos(-3, -1, t10);
        var t11Pos2 = createTileAtpos(0, -1, t11);
        grid.add(t10Pos1, t11Pos2);
        var t12 = new Tile(ORANGE, SQUARE);
        var t13 = new Tile(RED, SQUARE);
        add(grid, 1, 3, DOWN, t12, t13);
        var t14 = new Tile(YELLOW, SQUARE);
        var t15 = new Tile(ORANGE, STAR);

        assertThrows(QwirkleException.class, () ->
                add(grid, -3, -2, LEFT, t14, t15));
    }

    @Test
    void rules_elvire_g_FirstFail_Color() {
        var t1 = new Tile(RED, ROUND);
        var t2 = new Tile(RED, DIAMOND);
        var t3 = new Tile(RED, PLUS);
        grid.firstAdd(UP, t1, t2, t3);
        var t4 = new Tile(RED, SQUARE);
        var t5 = new Tile(BLUE, SQUARE);
        var t6 = new Tile(PURPLE, SQUARE);
        add(grid, 1, 0, RIGHT, t4, t5, t6);
        var t7 = new Tile(BLUE, ROUND);
        add(grid, 0, 1, UP, t7);
        var t8 = new Tile(GREEN, PLUS);
        var t9 = new Tile(GREEN, DIAMOND);
        add(grid, -2, -1, DOWN, t8, t9);
        var t10 = new Tile(GREEN, STAR);
        var t11 = new Tile(GREEN, ROUND);
        var t10Pos1 = createTileAtpos(-3, -1, t10);
        var t11Pos2 = createTileAtpos(0, -1, t11);
        grid.add(t10Pos1, t11Pos2);
        var t12 = new Tile(ORANGE, SQUARE);
        var t13 = new Tile(RED, SQUARE);
        add(grid, 1, 3, DOWN, t12, t13);
        var t14 = new Tile(GREEN, STAR);
        var t15 = new Tile(ORANGE, STAR);

        assertThrows(QwirkleException.class, () ->
                add(grid, -3, -2, LEFT, t14, t15));
    }

    @Test
    void rules_elvire_g_LastFail_Shape() {
        var t1 = new Tile(RED, ROUND);
        var t2 = new Tile(RED, DIAMOND);
        var t3 = new Tile(RED, PLUS);
        grid.firstAdd(UP, t1, t2, t3);
        var t4 = new Tile(RED, SQUARE);
        var t5 = new Tile(BLUE, SQUARE);
        var t6 = new Tile(PURPLE, SQUARE);
        add(grid, 1, 0, RIGHT, t4, t5, t6);
        var t7 = new Tile(BLUE, ROUND);
        add(grid, 0, 1, UP, t7);
        var t8 = new Tile(GREEN, PLUS);
        var t9 = new Tile(GREEN, DIAMOND);
        add(grid, -2, -1, DOWN, t8, t9);
        var t10 = new Tile(GREEN, STAR);
        var t11 = new Tile(GREEN, ROUND);
        var t10Pos1 = createTileAtpos(-3, -1, t10);
        var t11Pos2 = createTileAtpos(0, -1, t11);
        grid.add(t10Pos1, t11Pos2);
        var t12 = new Tile(ORANGE, SQUARE);
        var t13 = new Tile(RED, SQUARE);
        add(grid, 1, 3, DOWN, t12, t13);
        var t14 = new Tile(YELLOW, STAR);
        var t15 = new Tile(ORANGE, SQUARE);

        assertThrows(QwirkleException.class, () ->
                add(grid, -3, -2, LEFT, t14, t15));
    }

    @Test
    void rules_elvire_g_LastFail_ShapeColor() {
        var t1 = new Tile(RED, ROUND);
        var t2 = new Tile(RED, DIAMOND);
        var t3 = new Tile(RED, PLUS);
        grid.firstAdd(UP, t1, t2, t3);
        var t4 = new Tile(RED, SQUARE);
        var t5 = new Tile(BLUE, SQUARE);
        var t6 = new Tile(PURPLE, SQUARE);
        add(grid, 1, 0, RIGHT, t4, t5, t6);
        var t7 = new Tile(BLUE, ROUND);
        add(grid, 0, 1, UP, t7);
        var t8 = new Tile(GREEN, PLUS);
        var t9 = new Tile(GREEN, DIAMOND);
        add(grid, -2, -1, DOWN, t8, t9);
        var t10 = new Tile(GREEN, STAR);
        var t11 = new Tile(GREEN, ROUND);
        var t10Pos1 = createTileAtpos(-3, -1, t10);
        var t11Pos2 = createTileAtpos(0, -1, t11);
        grid.add(t10Pos1, t11Pos2);
        var t12 = new Tile(ORANGE, SQUARE);
        var t13 = new Tile(RED, SQUARE);
        add(grid, 1, 3, DOWN, t12, t13);
        var t14 = new Tile(YELLOW, STAR);
        var t15 = new Tile(YELLOW, SQUARE);

        assertThrows(QwirkleException.class, () ->
                add(grid, -3, -2, LEFT, t14, t15));
    }

    @Test
    void rules_elvire_g_LastFail_Color() {
        var t1 = new Tile(RED, ROUND);
        var t2 = new Tile(RED, DIAMOND);
        var t3 = new Tile(RED, PLUS);
        grid.firstAdd(UP, t1, t2, t3);
        var t4 = new Tile(RED, SQUARE);
        var t5 = new Tile(BLUE, SQUARE);
        var t6 = new Tile(PURPLE, SQUARE);
        add(grid, 1, 0, RIGHT, t4, t5, t6);
        var t7 = new Tile(BLUE, ROUND);
        add(grid, 0, 1, UP, t7);
        var t8 = new Tile(GREEN, PLUS);
        var t9 = new Tile(GREEN, DIAMOND);
        add(grid, -2, -1, DOWN, t8, t9);
        var t10 = new Tile(GREEN, STAR);
        var t11 = new Tile(GREEN, ROUND);
        var t10Pos1 = createTileAtpos(-3, -1, t10);
        var t11Pos2 = createTileAtpos(0, -1, t11);
        grid.add(t10Pos1, t11Pos2);
        var t12 = new Tile(ORANGE, SQUARE);
        var t13 = new Tile(RED, SQUARE);
        add(grid, 1, 3, DOWN, t12, t13);
        var t14 = new Tile(YELLOW, STAR);
        var t15 = new Tile(GREEN, STAR);

        assertThrows(QwirkleException.class, () ->
                add(grid, -3, -2, LEFT, t14, t15));
    }

    @Test
    void rules_elvire_g_Fail_Direction_Down() {
        var t1 = new Tile(RED, ROUND);
        var t2 = new Tile(RED, DIAMOND);
        var t3 = new Tile(RED, PLUS);
        grid.firstAdd(UP, t1, t2, t3);
        var t4 = new Tile(RED, SQUARE);
        var t5 = new Tile(BLUE, SQUARE);
        var t6 = new Tile(PURPLE, SQUARE);
        add(grid, 1, 0, RIGHT, t4, t5, t6);
        var t7 = new Tile(BLUE, ROUND);
        add(grid, 0, 1, UP, t7);
        var t8 = new Tile(GREEN, PLUS);
        var t9 = new Tile(GREEN, DIAMOND);
        add(grid, -2, -1, DOWN, t8, t9);
        var t10 = new Tile(GREEN, STAR);
        var t11 = new Tile(GREEN, ROUND);
        var t10Pos1 = createTileAtpos(-3, -1, t10);
        var t11Pos2 = createTileAtpos(0, -1, t11);
        grid.add(t10Pos1, t11Pos2);
        var t12 = new Tile(ORANGE, SQUARE);
        var t13 = new Tile(RED, SQUARE);
        add(grid, 1, 3, DOWN, t12, t13);
        var t14 = new Tile(YELLOW, STAR);
        var t15 = new Tile(ORANGE, STAR);

        assertThrows(QwirkleException.class, () ->
                add(grid, -3, -2, DOWN, t14, t15));
    }

    @Test
    void rules_vincent_h() {
        var t1 = new Tile(RED, ROUND);
        var t2 = new Tile(RED, DIAMOND);
        var t3 = new Tile(RED, PLUS);
        grid.firstAdd(UP, t1, t2, t3);
        var t4 = new Tile(RED, SQUARE);
        var t5 = new Tile(BLUE, SQUARE);
        var t6 = new Tile(PURPLE, SQUARE);
        add(grid, 1, 0, RIGHT, t4, t5, t6);
        var t7 = new Tile(BLUE, ROUND);
        add(grid, 0, 1, UP, t7);
        var t8 = new Tile(GREEN, PLUS);
        var t9 = new Tile(GREEN, DIAMOND);
        add(grid, -2, -1, DOWN, t8, t9);
        var t10 = new Tile(GREEN, STAR);
        var t11 = new Tile(GREEN, ROUND);
        var t10Pos1 = createTileAtpos(-3, -1, t10);
        var t11Pos2 = createTileAtpos(0, -1, t11);
        grid.add(t10Pos1, t11Pos2);
        var t12 = new Tile(ORANGE, SQUARE);
        var t13 = new Tile(RED, SQUARE);
        add(grid, 1, 3, DOWN, t12, t13);
        var t14 = new Tile(YELLOW, STAR);
        var t15 = new Tile(ORANGE, STAR);
        add(grid, -3, -2, LEFT, t14, t15);
        var t16 = new Tile(ORANGE, CROSS);
        var t17 = new Tile(ORANGE, DIAMOND);

        add(grid, -2, -3, DOWN, t16, t17);

        assertEquals(t16, get(grid, -2, -3));
        assertEquals(t17, get(grid, -1, -3));
    }

    @Test
    void rules_vincent_h_FirstFail_Shape() {
        var t1 = new Tile(RED, ROUND);
        var t2 = new Tile(RED, DIAMOND);
        var t3 = new Tile(RED, PLUS);
        grid.firstAdd(UP, t1, t2, t3);
        var t4 = new Tile(RED, SQUARE);
        var t5 = new Tile(BLUE, SQUARE);
        var t6 = new Tile(PURPLE, SQUARE);
        add(grid, 1, 0, RIGHT, t4, t5, t6);
        var t7 = new Tile(BLUE, ROUND);
        add(grid, 0, 1, UP, t7);
        var t8 = new Tile(GREEN, PLUS);
        var t9 = new Tile(GREEN, DIAMOND);
        add(grid, -2, -1, DOWN, t8, t9);
        var t10 = new Tile(GREEN, STAR);
        var t11 = new Tile(GREEN, ROUND);
        var t10Pos1 = createTileAtpos(-3, -1, t10);
        var t11Pos2 = createTileAtpos(0, -1, t11);
        grid.add(t10Pos1, t11Pos2);
        var t12 = new Tile(ORANGE, SQUARE);
        var t13 = new Tile(RED, SQUARE);
        add(grid, 1, 3, DOWN, t12, t13);
        var t14 = new Tile(YELLOW, STAR);
        var t15 = new Tile(ORANGE, STAR);
        add(grid, -3, -2, LEFT, t14, t15);
        var t16 = new Tile(ORANGE, STAR);
        var t17 = new Tile(ORANGE, DIAMOND);

        assertThrows(QwirkleException.class, () ->
                add(grid, -2, -3, DOWN, t16, t17));
    }

    @Test
    void rules_vincent_h_FirstFail_Color() {
        var t1 = new Tile(RED, ROUND);
        var t2 = new Tile(RED, DIAMOND);
        var t3 = new Tile(RED, PLUS);
        grid.firstAdd(UP, t1, t2, t3);
        var t4 = new Tile(RED, SQUARE);
        var t5 = new Tile(BLUE, SQUARE);
        var t6 = new Tile(PURPLE, SQUARE);
        add(grid, 1, 0, RIGHT, t4, t5, t6);
        var t7 = new Tile(BLUE, ROUND);
        add(grid, 0, 1, UP, t7);
        var t8 = new Tile(GREEN, PLUS);
        var t9 = new Tile(GREEN, DIAMOND);
        add(grid, -2, -1, DOWN, t8, t9);
        var t10 = new Tile(GREEN, STAR);
        var t11 = new Tile(GREEN, ROUND);
        var t10Pos1 = createTileAtpos(-3, -1, t10);
        var t11Pos2 = createTileAtpos(0, -1, t11);
        grid.add(t10Pos1, t11Pos2);
        var t12 = new Tile(ORANGE, SQUARE);
        var t13 = new Tile(RED, SQUARE);
        add(grid, 1, 3, DOWN, t12, t13);
        var t14 = new Tile(YELLOW, STAR);
        var t15 = new Tile(ORANGE, STAR);
        add(grid, -3, -2, LEFT, t14, t15);
        var t16 = new Tile(GREEN, STAR);
        var t17 = new Tile(ORANGE, DIAMOND);

        assertThrows(QwirkleException.class, () ->
                add(grid, -2, -3, DOWN, t16, t17));
    }

    @Test
    void rules_vincent_h_LastFail_Shape() {
        var t1 = new Tile(RED, ROUND);
        var t2 = new Tile(RED, DIAMOND);
        var t3 = new Tile(RED, PLUS);
        grid.firstAdd(UP, t1, t2, t3);
        var t4 = new Tile(RED, SQUARE);
        var t5 = new Tile(BLUE, SQUARE);
        var t6 = new Tile(PURPLE, SQUARE);
        add(grid, 1, 0, RIGHT, t4, t5, t6);
        var t7 = new Tile(BLUE, ROUND);
        add(grid, 0, 1, UP, t7);
        var t8 = new Tile(GREEN, PLUS);
        var t9 = new Tile(GREEN, DIAMOND);
        add(grid, -2, -1, DOWN, t8, t9);
        var t10 = new Tile(GREEN, STAR);
        var t11 = new Tile(GREEN, ROUND);
        var t10Pos1 = createTileAtpos(-3, -1, t10);
        var t11Pos2 = createTileAtpos(0, -1, t11);
        grid.add(t10Pos1, t11Pos2);
        var t12 = new Tile(ORANGE, SQUARE);
        var t13 = new Tile(RED, SQUARE);
        add(grid, 1, 3, DOWN, t12, t13);
        var t14 = new Tile(YELLOW, STAR);
        var t15 = new Tile(ORANGE, STAR);
        add(grid, -3, -2, LEFT, t14, t15);
        var t16 = new Tile(ORANGE, CROSS);
        var t17 = new Tile(ORANGE, CROSS);

        assertThrows(QwirkleException.class, () ->
                add(grid, -2, -3, DOWN, t16, t17));
    }

    @Test
    void rules_vincent_h_LastFail_Color() {
        var t1 = new Tile(RED, ROUND);
        var t2 = new Tile(RED, DIAMOND);
        var t3 = new Tile(RED, PLUS);
        grid.firstAdd(UP, t1, t2, t3);
        var t4 = new Tile(RED, SQUARE);
        var t5 = new Tile(BLUE, SQUARE);
        var t6 = new Tile(PURPLE, SQUARE);
        add(grid, 1, 0, RIGHT, t4, t5, t6);
        var t7 = new Tile(BLUE, ROUND);
        add(grid, 0, 1, UP, t7);
        var t8 = new Tile(GREEN, PLUS);
        var t9 = new Tile(GREEN, DIAMOND);
        add(grid, -2, -1, DOWN, t8, t9);
        var t10 = new Tile(GREEN, STAR);
        var t11 = new Tile(GREEN, ROUND);
        var t10Pos1 = createTileAtpos(-3, -1, t10);
        var t11Pos2 = createTileAtpos(0, -1, t11);
        grid.add(t10Pos1, t11Pos2);
        var t12 = new Tile(ORANGE, SQUARE);
        var t13 = new Tile(RED, SQUARE);
        add(grid, 1, 3, DOWN, t12, t13);
        var t14 = new Tile(YELLOW, STAR);
        var t15 = new Tile(ORANGE, STAR);
        add(grid, -3, -2, LEFT, t14, t15);
        var t16 = new Tile(ORANGE, CROSS);
        var t17 = new Tile(GREEN, DIAMOND);

        assertThrows(QwirkleException.class, () ->
                add(grid, -2, -3, DOWN, t16, t17));
    }

    @Test
    void rules_vincent_h_Fail_ColorShape() {
        var t1 = new Tile(RED, ROUND);
        var t2 = new Tile(RED, DIAMOND);
        var t3 = new Tile(RED, PLUS);
        grid.firstAdd(UP, t1, t2, t3);
        var t4 = new Tile(RED, SQUARE);
        var t5 = new Tile(BLUE, SQUARE);
        var t6 = new Tile(PURPLE, SQUARE);
        add(grid, 1, 0, RIGHT, t4, t5, t6);
        var t7 = new Tile(BLUE, ROUND);
        add(grid, 0, 1, UP, t7);
        var t8 = new Tile(GREEN, PLUS);
        var t9 = new Tile(GREEN, DIAMOND);
        add(grid, -2, -1, DOWN, t8, t9);
        var t10 = new Tile(GREEN, STAR);
        var t11 = new Tile(GREEN, ROUND);
        var t10Pos1 = createTileAtpos(-3, -1, t10);
        var t11Pos2 = createTileAtpos(0, -1, t11);
        grid.add(t10Pos1, t11Pos2);
        var t12 = new Tile(ORANGE, SQUARE);
        var t13 = new Tile(RED, SQUARE);
        add(grid, 1, 3, DOWN, t12, t13);
        var t14 = new Tile(YELLOW, STAR);
        var t15 = new Tile(ORANGE, STAR);
        add(grid, -3, -2, LEFT, t14, t15);
        var t16 = new Tile(GREEN, CROSS);
        var t17 = new Tile(GREEN, DIAMOND);

        assertThrows(QwirkleException.class, () ->
                add(grid, -2, -3, DOWN, t16, t17));
    }

    @Test
    void rules_vincent_h_Fail_Direction_LEFT() {
        var t1 = new Tile(RED, ROUND);
        var t2 = new Tile(RED, DIAMOND);
        var t3 = new Tile(RED, PLUS);
        grid.firstAdd(UP, t1, t2, t3);
        var t4 = new Tile(RED, SQUARE);
        var t5 = new Tile(BLUE, SQUARE);
        var t6 = new Tile(PURPLE, SQUARE);
        add(grid, 1, 0, RIGHT, t4, t5, t6);
        var t7 = new Tile(BLUE, ROUND);
        add(grid, 0, 1, UP, t7);
        var t8 = new Tile(GREEN, PLUS);
        var t9 = new Tile(GREEN, DIAMOND);
        add(grid, -2, -1, DOWN, t8, t9);
        var t10 = new Tile(GREEN, STAR);
        var t11 = new Tile(GREEN, ROUND);
        var t10Pos1 = createTileAtpos(-3, -1, t10);
        var t11Pos2 = createTileAtpos(0, -1, t11);
        grid.add(t10Pos1, t11Pos2);
        var t12 = new Tile(ORANGE, SQUARE);
        var t13 = new Tile(RED, SQUARE);
        add(grid, 1, 3, DOWN, t12, t13);
        var t14 = new Tile(YELLOW, STAR);
        var t15 = new Tile(ORANGE, STAR);
        add(grid, -3, -2, LEFT, t14, t15);
        var t16 = new Tile(ORANGE, CROSS);
        var t17 = new Tile(ORANGE, DIAMOND);

        assertThrows(QwirkleException.class, () ->
                add(grid, -3, -4, DOWN, t16, t17));
    }

    @Test
    void rules_sonia_i() {
        var t1 = new Tile(RED, ROUND);
        var t2 = new Tile(RED, DIAMOND);
        var t3 = new Tile(RED, PLUS);
        grid.firstAdd(UP, t1, t2, t3);
        var t4 = new Tile(RED, SQUARE);
        var t5 = new Tile(BLUE, SQUARE);
        var t6 = new Tile(PURPLE, SQUARE);
        add(grid, 1, 0, RIGHT, t4, t5, t6);
        var t7 = new Tile(BLUE, ROUND);
        add(grid, 0, 1, UP, t7);
        var t8 = new Tile(GREEN, PLUS);
        var t9 = new Tile(GREEN, DIAMOND);
        add(grid, -2, -1, DOWN, t8, t9);
        var t10 = new Tile(GREEN, STAR);
        var t11 = new Tile(GREEN, ROUND);
        var t10Pos1 = createTileAtpos(-3, -1, t10);
        var t11Pos2 = createTileAtpos(0, -1, t11);
        grid.add(t10Pos1, t11Pos2);
        var t12 = new Tile(ORANGE, SQUARE);
        var t13 = new Tile(RED, SQUARE);
        add(grid, 1, 3, DOWN, t12, t13);
        var t14 = new Tile(YELLOW, STAR);
        var t15 = new Tile(ORANGE, STAR);
        add(grid, -3, -2, LEFT, t14, t15);
        var t16 = new Tile(ORANGE, CROSS);
        var t17 = new Tile(ORANGE, DIAMOND);
        add(grid, -2, -3, DOWN, t16, t17);
        var t18 = new Tile(YELLOW, DIAMOND);
        var t19 = new Tile(YELLOW, ROUND);

        add(grid, -1, -2, DOWN, t18, t19);

        assertEquals(t18, get(grid, -1, -2));
        assertEquals(t19, get(grid, 0, -2));
    }

    @Test
    void rules_sonia_i_Fail_Swap() {
        var t1 = new Tile(RED, ROUND);
        var t2 = new Tile(RED, DIAMOND);
        var t3 = new Tile(RED, PLUS);
        grid.firstAdd(UP, t1, t2, t3);
        var t4 = new Tile(RED, SQUARE);
        var t5 = new Tile(BLUE, SQUARE);
        var t6 = new Tile(PURPLE, SQUARE);
        add(grid, 1, 0, RIGHT, t4, t5, t6);
        var t7 = new Tile(BLUE, ROUND);
        add(grid, 0, 1, UP, t7);
        var t8 = new Tile(GREEN, PLUS);
        var t9 = new Tile(GREEN, DIAMOND);
        add(grid, -2, -1, DOWN, t8, t9);
        var t10 = new Tile(GREEN, STAR);
        var t11 = new Tile(GREEN, ROUND);
        var t10Pos1 = createTileAtpos(-3, -1, t10);
        var t11Pos2 = createTileAtpos(0, -1, t11);
        grid.add(t10Pos1, t11Pos2);
        var t12 = new Tile(ORANGE, SQUARE);
        var t13 = new Tile(RED, SQUARE);
        add(grid, 1, 3, DOWN, t12, t13);
        var t14 = new Tile(YELLOW, STAR);
        var t15 = new Tile(ORANGE, STAR);
        add(grid, -3, -2, LEFT, t14, t15);
        var t16 = new Tile(ORANGE, CROSS);
        var t17 = new Tile(ORANGE, DIAMOND);
        add(grid, -2, -3, DOWN, t16, t17);
        var t18 = new Tile(YELLOW, DIAMOND);
        var t19 = new Tile(YELLOW, ROUND);

        assertThrows(QwirkleException.class, () ->
                add(grid, -1, -2, DOWN, t19, t18));
    }

    @Test
    void rules_sonia_i_Fail_Direction_UP() {
        var t1 = new Tile(RED, ROUND);
        var t2 = new Tile(RED, DIAMOND);
        var t3 = new Tile(RED, PLUS);
        grid.firstAdd(UP, t1, t2, t3);
        var t4 = new Tile(RED, SQUARE);
        var t5 = new Tile(BLUE, SQUARE);
        var t6 = new Tile(PURPLE, SQUARE);
        add(grid, 1, 0, RIGHT, t4, t5, t6);
        var t7 = new Tile(BLUE, ROUND);
        add(grid, 0, 1, UP, t7);
        var t8 = new Tile(GREEN, PLUS);
        var t9 = new Tile(GREEN, DIAMOND);
        add(grid, -2, -1, DOWN, t8, t9);
        var t10 = new Tile(GREEN, STAR);
        var t11 = new Tile(GREEN, ROUND);
        var t10Pos1 = createTileAtpos(-3, -1, t10);
        var t11Pos2 = createTileAtpos(0, -1, t11);
        grid.add(t10Pos1, t11Pos2);
        var t12 = new Tile(ORANGE, SQUARE);
        var t13 = new Tile(RED, SQUARE);
        add(grid, 1, 3, DOWN, t12, t13);
        var t14 = new Tile(YELLOW, STAR);
        var t15 = new Tile(ORANGE, STAR);
        add(grid, -3, -2, LEFT, t14, t15);
        var t16 = new Tile(ORANGE, CROSS);
        var t17 = new Tile(ORANGE, DIAMOND);
        add(grid, -2, -3, DOWN, t16, t17);
        var t18 = new Tile(YELLOW, DIAMOND);
        var t19 = new Tile(YELLOW, ROUND);

        assertThrows(QwirkleException.class, () ->
                add(grid, -1, -2, UP, t18, t19));
    }

    @Test
    void rules_sonia_i_FirstFail_Shape() {
        var t1 = new Tile(RED, ROUND);
        var t2 = new Tile(RED, DIAMOND);
        var t3 = new Tile(RED, PLUS);
        grid.firstAdd(UP, t1, t2, t3);
        var t4 = new Tile(RED, SQUARE);
        var t5 = new Tile(BLUE, SQUARE);
        var t6 = new Tile(PURPLE, SQUARE);
        add(grid, 1, 0, RIGHT, t4, t5, t6);
        var t7 = new Tile(BLUE, ROUND);
        add(grid, 0, 1, UP, t7);
        var t8 = new Tile(GREEN, PLUS);
        var t9 = new Tile(GREEN, DIAMOND);
        add(grid, -2, -1, DOWN, t8, t9);
        var t10 = new Tile(GREEN, STAR);
        var t11 = new Tile(GREEN, ROUND);
        var t10Pos1 = createTileAtpos(-3, -1, t10);
        var t11Pos2 = createTileAtpos(0, -1, t11);
        grid.add(t10Pos1, t11Pos2);
        var t12 = new Tile(ORANGE, SQUARE);
        var t13 = new Tile(RED, SQUARE);
        add(grid, 1, 3, DOWN, t12, t13);
        var t14 = new Tile(YELLOW, STAR);
        var t15 = new Tile(ORANGE, STAR);
        add(grid, -3, -2, LEFT, t14, t15);
        var t16 = new Tile(ORANGE, CROSS);
        var t17 = new Tile(ORANGE, DIAMOND);
        add(grid, -2, -3, DOWN, t16, t17);
        var t18 = new Tile(YELLOW, SQUARE);
        var t19 = new Tile(YELLOW, ROUND);

        assertThrows(QwirkleException.class, () ->
                add(grid, -1, -2, DOWN, t18, t19));
    }

    @Test
    void rules_sonia_i_FirstFail_Color() {
        var t1 = new Tile(RED, ROUND);
        var t2 = new Tile(RED, DIAMOND);
        var t3 = new Tile(RED, PLUS);
        grid.firstAdd(UP, t1, t2, t3);
        var t4 = new Tile(RED, SQUARE);
        var t5 = new Tile(BLUE, SQUARE);
        var t6 = new Tile(PURPLE, SQUARE);
        add(grid, 1, 0, RIGHT, t4, t5, t6);
        var t7 = new Tile(BLUE, ROUND);
        add(grid, 0, 1, UP, t7);
        var t8 = new Tile(GREEN, PLUS);
        var t9 = new Tile(GREEN, DIAMOND);
        add(grid, -2, -1, DOWN, t8, t9);
        var t10 = new Tile(GREEN, STAR);
        var t11 = new Tile(GREEN, ROUND);
        var t10Pos1 = createTileAtpos(-3, -1, t10);
        var t11Pos2 = createTileAtpos(0, -1, t11);
        grid.add(t10Pos1, t11Pos2);
        var t12 = new Tile(ORANGE, SQUARE);
        var t13 = new Tile(RED, SQUARE);
        add(grid, 1, 3, DOWN, t12, t13);
        var t14 = new Tile(YELLOW, STAR);
        var t15 = new Tile(ORANGE, STAR);
        add(grid, -3, -2, LEFT, t14, t15);
        var t16 = new Tile(ORANGE, CROSS);
        var t17 = new Tile(ORANGE, DIAMOND);
        add(grid, -2, -3, DOWN, t16, t17);
        var t18 = new Tile(RED, DIAMOND);
        var t19 = new Tile(YELLOW, ROUND);

        assertThrows(QwirkleException.class, () ->
                add(grid, -1, -2, DOWN, t18, t19));
    }

    @Test
    void rules_sonia_i_LastFail_Shape() {
        var t1 = new Tile(RED, ROUND);
        var t2 = new Tile(RED, DIAMOND);
        var t3 = new Tile(RED, PLUS);
        grid.firstAdd(UP, t1, t2, t3);
        var t4 = new Tile(RED, SQUARE);
        var t5 = new Tile(BLUE, SQUARE);
        var t6 = new Tile(PURPLE, SQUARE);
        add(grid, 1, 0, RIGHT, t4, t5, t6);
        var t7 = new Tile(BLUE, ROUND);
        add(grid, 0, 1, UP, t7);
        var t8 = new Tile(GREEN, PLUS);
        var t9 = new Tile(GREEN, DIAMOND);
        add(grid, -2, -1, DOWN, t8, t9);
        var t10 = new Tile(GREEN, STAR);
        var t11 = new Tile(GREEN, ROUND);
        var t10Pos1 = createTileAtpos(-3, -1, t10);
        var t11Pos2 = createTileAtpos(0, -1, t11);
        grid.add(t10Pos1, t11Pos2);
        var t12 = new Tile(ORANGE, SQUARE);
        var t13 = new Tile(RED, SQUARE);
        add(grid, 1, 3, DOWN, t12, t13);
        var t14 = new Tile(YELLOW, STAR);
        var t15 = new Tile(ORANGE, STAR);
        add(grid, -3, -2, LEFT, t14, t15);
        var t16 = new Tile(ORANGE, CROSS);
        var t17 = new Tile(ORANGE, DIAMOND);
        add(grid, -2, -3, DOWN, t16, t17);
        var t18 = new Tile(YELLOW, DIAMOND);
        var t19 = new Tile(YELLOW, SQUARE);

        assertThrows(QwirkleException.class, () ->
                add(grid, -1, -2, DOWN, t18, t19));
    }

    @Test
    void rules_sonia_i_LastFail_Color() {
        var t1 = new Tile(RED, ROUND);
        var t2 = new Tile(RED, DIAMOND);
        var t3 = new Tile(RED, PLUS);
        grid.firstAdd(UP, t1, t2, t3);
        var t4 = new Tile(RED, SQUARE);
        var t5 = new Tile(BLUE, SQUARE);
        var t6 = new Tile(PURPLE, SQUARE);
        add(grid, 1, 0, RIGHT, t4, t5, t6);
        var t7 = new Tile(BLUE, ROUND);
        add(grid, 0, 1, UP, t7);
        var t8 = new Tile(GREEN, PLUS);
        var t9 = new Tile(GREEN, DIAMOND);
        add(grid, -2, -1, DOWN, t8, t9);
        var t10 = new Tile(GREEN, STAR);
        var t11 = new Tile(GREEN, ROUND);
        var t10Pos1 = createTileAtpos(-3, -1, t10);
        var t11Pos2 = createTileAtpos(0, -1, t11);
        grid.add(t10Pos1, t11Pos2);
        var t12 = new Tile(ORANGE, SQUARE);
        var t13 = new Tile(RED, SQUARE);
        add(grid, 1, 3, DOWN, t12, t13);
        var t14 = new Tile(YELLOW, STAR);
        var t15 = new Tile(ORANGE, STAR);
        add(grid, -3, -2, LEFT, t14, t15);
        var t16 = new Tile(ORANGE, CROSS);
        var t17 = new Tile(ORANGE, DIAMOND);
        add(grid, -2, -3, DOWN, t16, t17);
        var t18 = new Tile(YELLOW, DIAMOND);
        var t19 = new Tile(RED, ROUND);

        assertThrows(QwirkleException.class, () ->
                add(grid, -1, -2, DOWN, t18, t19));
    }

    @Test
    void rules_sonia_i_Fail_Shape() {
        var t1 = new Tile(RED, ROUND);
        var t2 = new Tile(RED, DIAMOND);
        var t3 = new Tile(RED, PLUS);
        grid.firstAdd(UP, t1, t2, t3);
        var t4 = new Tile(RED, SQUARE);
        var t5 = new Tile(BLUE, SQUARE);
        var t6 = new Tile(PURPLE, SQUARE);
        add(grid, 1, 0, RIGHT, t4, t5, t6);
        var t7 = new Tile(BLUE, ROUND);
        add(grid, 0, 1, UP, t7);
        var t8 = new Tile(GREEN, PLUS);
        var t9 = new Tile(GREEN, DIAMOND);
        add(grid, -2, -1, DOWN, t8, t9);
        var t10 = new Tile(GREEN, STAR);
        var t11 = new Tile(GREEN, ROUND);
        var t10Pos1 = createTileAtpos(-3, -1, t10);
        var t11Pos2 = createTileAtpos(0, -1, t11);
        grid.add(t10Pos1, t11Pos2);
        var t12 = new Tile(ORANGE, SQUARE);
        var t13 = new Tile(RED, SQUARE);
        add(grid, 1, 3, DOWN, t12, t13);
        var t14 = new Tile(YELLOW, STAR);
        var t15 = new Tile(ORANGE, STAR);
        add(grid, -3, -2, LEFT, t14, t15);
        var t16 = new Tile(ORANGE, CROSS);
        var t17 = new Tile(ORANGE, DIAMOND);
        add(grid, -2, -3, DOWN, t16, t17);
        var t18 = new Tile(YELLOW, DIAMOND);
        var t19 = new Tile(YELLOW, DIAMOND);

        assertThrows(QwirkleException.class, () ->
                add(grid, -1, -2, DOWN, t18, t19));
    }

    @Test
    void rules_sonia_i_Fail_Color() {
        var t1 = new Tile(RED, ROUND);
        var t2 = new Tile(RED, DIAMOND);
        var t3 = new Tile(RED, PLUS);
        grid.firstAdd(UP, t1, t2, t3);
        var t4 = new Tile(RED, SQUARE);
        var t5 = new Tile(BLUE, SQUARE);
        var t6 = new Tile(PURPLE, SQUARE);
        add(grid, 1, 0, RIGHT, t4, t5, t6);
        var t7 = new Tile(BLUE, ROUND);
        add(grid, 0, 1, UP, t7);
        var t8 = new Tile(GREEN, PLUS);
        var t9 = new Tile(GREEN, DIAMOND);
        add(grid, -2, -1, DOWN, t8, t9);
        var t10 = new Tile(GREEN, STAR);
        var t11 = new Tile(GREEN, ROUND);
        var t10Pos1 = createTileAtpos(-3, -1, t10);
        var t11Pos2 = createTileAtpos(0, -1, t11);
        grid.add(t10Pos1, t11Pos2);
        var t12 = new Tile(ORANGE, SQUARE);
        var t13 = new Tile(RED, SQUARE);
        add(grid, 1, 3, DOWN, t12, t13);
        var t14 = new Tile(YELLOW, STAR);
        var t15 = new Tile(ORANGE, STAR);
        add(grid, -3, -2, LEFT, t14, t15);
        var t16 = new Tile(ORANGE, CROSS);
        var t17 = new Tile(ORANGE, DIAMOND);
        add(grid, -2, -3, DOWN, t16, t17);
        var t18 = new Tile(GREEN, DIAMOND);
        var t19 = new Tile(GREEN, ROUND);

        assertThrows(QwirkleException.class, () ->
                add(grid, -1, -2, DOWN, t18, t19));
    }

    @Test
    void rules_cedric_j() {
        var t1 = new Tile(RED, ROUND);
        var t2 = new Tile(RED, DIAMOND);
        var t3 = new Tile(RED, PLUS);
        grid.firstAdd(UP, t1, t2, t3);
        var t4 = new Tile(RED, SQUARE);
        var t5 = new Tile(BLUE, SQUARE);
        var t6 = new Tile(PURPLE, SQUARE);
        add(grid, 1, 0, RIGHT, t4, t5, t6);
        var t7 = new Tile(BLUE, ROUND);
        add(grid, 0, 1, UP, t7);
        var t8 = new Tile(GREEN, PLUS);
        var t9 = new Tile(GREEN, DIAMOND);
        add(grid, -2, -1, DOWN, t8, t9);
        var t10 = new Tile(GREEN, STAR);
        var t11 = new Tile(GREEN, ROUND);
        var t10Pos1 = createTileAtpos(-3, -1, t10);
        var t11Pos2 = createTileAtpos(0, -1, t11);
        grid.add(t10Pos1, t11Pos2);
        var t12 = new Tile(ORANGE, SQUARE);
        var t13 = new Tile(RED, SQUARE);
        add(grid, 1, 3, DOWN, t12, t13);
        var t14 = new Tile(YELLOW, STAR);
        var t15 = new Tile(ORANGE, STAR);
        add(grid, -3, -2, LEFT, t14, t15);
        var t16 = new Tile(ORANGE, CROSS);
        var t17 = new Tile(ORANGE, DIAMOND);
        add(grid, -2, -3, DOWN, t16, t17);
        var t18 = new Tile(YELLOW, DIAMOND);
        var t19 = new Tile(YELLOW, ROUND);
        add(grid, -1, -2, DOWN, t18, t19);
        var t20 = new Tile(RED, STAR);

        add(grid, -3, 0, t20);

        assertEquals(t20, get(grid, -3, 0));
    }

    @Test
    void rules_cedric_j_Fail_Shape() {
        var t1 = new Tile(RED, ROUND);
        var t2 = new Tile(RED, DIAMOND);
        var t3 = new Tile(RED, PLUS);
        grid.firstAdd(UP, t1, t2, t3);
        var t4 = new Tile(RED, SQUARE);
        var t5 = new Tile(BLUE, SQUARE);
        var t6 = new Tile(PURPLE, SQUARE);
        add(grid, 1, 0, RIGHT, t4, t5, t6);
        var t7 = new Tile(BLUE, ROUND);
        add(grid, 0, 1, UP, t7);
        var t8 = new Tile(GREEN, PLUS);
        var t9 = new Tile(GREEN, DIAMOND);
        add(grid, -2, -1, DOWN, t8, t9);
        var t10 = new Tile(GREEN, STAR);
        var t11 = new Tile(GREEN, ROUND);
        var t10Pos1 = createTileAtpos(-3, -1, t10);
        var t11Pos2 = createTileAtpos(0, -1, t11);
        grid.add(t10Pos1, t11Pos2);
        var t12 = new Tile(ORANGE, SQUARE);
        var t13 = new Tile(RED, SQUARE);
        add(grid, 1, 3, DOWN, t12, t13);
        var t14 = new Tile(YELLOW, STAR);
        var t15 = new Tile(ORANGE, STAR);
        add(grid, -3, -2, LEFT, t14, t15);
        var t16 = new Tile(ORANGE, CROSS);
        var t17 = new Tile(ORANGE, DIAMOND);
        add(grid, -2, -3, DOWN, t16, t17);
        var t18 = new Tile(YELLOW, DIAMOND);
        var t19 = new Tile(YELLOW, ROUND);
        add(grid, -1, -2, DOWN, t18, t19);
        var t20 = new Tile(RED, CROSS);

        assertThrows(QwirkleException.class, () ->
                add(grid, -3, 0, t20));
    }

    @Test
    void rules_cedric_j_Fail_Color() {
        var t1 = new Tile(RED, ROUND);
        var t2 = new Tile(RED, DIAMOND);
        var t3 = new Tile(RED, PLUS);
        grid.firstAdd(UP, t1, t2, t3);
        var t4 = new Tile(RED, SQUARE);
        var t5 = new Tile(BLUE, SQUARE);
        var t6 = new Tile(PURPLE, SQUARE);
        add(grid, 1, 0, RIGHT, t4, t5, t6);
        var t7 = new Tile(BLUE, ROUND);
        add(grid, 0, 1, UP, t7);
        var t8 = new Tile(GREEN, PLUS);
        var t9 = new Tile(GREEN, DIAMOND);
        add(grid, -2, -1, DOWN, t8, t9);
        var t10 = new Tile(GREEN, STAR);
        var t11 = new Tile(GREEN, ROUND);
        var t10Pos1 = createTileAtpos(-3, -1, t10);
        var t11Pos2 = createTileAtpos(0, -1, t11);
        grid.add(t10Pos1, t11Pos2);
        var t12 = new Tile(ORANGE, SQUARE);
        var t13 = new Tile(RED, SQUARE);
        add(grid, 1, 3, DOWN, t12, t13);
        var t14 = new Tile(YELLOW, STAR);
        var t15 = new Tile(ORANGE, STAR);
        add(grid, -3, -2, LEFT, t14, t15);
        var t16 = new Tile(ORANGE, CROSS);
        var t17 = new Tile(ORANGE, DIAMOND);
        add(grid, -2, -3, DOWN, t16, t17);
        var t18 = new Tile(YELLOW, DIAMOND);
        var t19 = new Tile(YELLOW, ROUND);
        add(grid, -1, -2, DOWN, t18, t19);
        var t20 = new Tile(BLUE, STAR);

        assertThrows(QwirkleException.class, () ->
                add(grid, -3, 0, t20));
    }

    @Test
    void rules_cedric_j_Fail_ShapeColor() {
        var t1 = new Tile(RED, ROUND);
        var t2 = new Tile(RED, DIAMOND);
        var t3 = new Tile(RED, PLUS);
        grid.firstAdd(UP, t1, t2, t3);
        var t4 = new Tile(RED, SQUARE);
        var t5 = new Tile(BLUE, SQUARE);
        var t6 = new Tile(PURPLE, SQUARE);
        add(grid, 1, 0, RIGHT, t4, t5, t6);
        var t7 = new Tile(BLUE, ROUND);
        add(grid, 0, 1, UP, t7);
        var t8 = new Tile(GREEN, PLUS);
        var t9 = new Tile(GREEN, DIAMOND);
        add(grid, -2, -1, DOWN, t8, t9);
        var t10 = new Tile(GREEN, STAR);
        var t11 = new Tile(GREEN, ROUND);
        var t10Pos1 = createTileAtpos(-3, -1, t10);
        var t11Pos2 = createTileAtpos(0, -1, t11);
        grid.add(t10Pos1, t11Pos2);
        var t12 = new Tile(ORANGE, SQUARE);
        var t13 = new Tile(RED, SQUARE);
        add(grid, 1, 3, DOWN, t12, t13);
        var t14 = new Tile(YELLOW, STAR);
        var t15 = new Tile(ORANGE, STAR);
        add(grid, -3, -2, LEFT, t14, t15);
        var t16 = new Tile(ORANGE, CROSS);
        var t17 = new Tile(ORANGE, DIAMOND);
        add(grid, -2, -3, DOWN, t16, t17);
        var t18 = new Tile(YELLOW, DIAMOND);
        var t19 = new Tile(YELLOW, ROUND);
        add(grid, -1, -2, DOWN, t18, t19);
        var t20 = new Tile(BLUE, CROSS);

        assertThrows(QwirkleException.class, () ->
                add(grid, -3, 0, t20));
    }

    @Test
    void rules_elvire_k() {
        var t1 = new Tile(RED, ROUND);
        var t2 = new Tile(RED, DIAMOND);
        var t3 = new Tile(RED, PLUS);
        grid.firstAdd(UP, t1, t2, t3);
        var t4 = new Tile(RED, SQUARE);
        var t5 = new Tile(BLUE, SQUARE);
        var t6 = new Tile(PURPLE, SQUARE);
        add(grid, 1, 0, RIGHT, t4, t5, t6);
        var t7 = new Tile(BLUE, ROUND);
        add(grid, 0, 1, UP, t7);
        var t8 = new Tile(GREEN, PLUS);
        var t9 = new Tile(GREEN, DIAMOND);
        add(grid, -2, -1, DOWN, t8, t9);
        var t10 = new Tile(GREEN, STAR);
        var t11 = new Tile(GREEN, ROUND);
        var t10Pos1 = createTileAtpos(-3, -1, t10);
        var t11Pos2 = createTileAtpos(0, -1, t11);
        grid.add(t10Pos1, t11Pos2);
        var t12 = new Tile(ORANGE, SQUARE);
        var t13 = new Tile(RED, SQUARE);
        add(grid, 1, 3, DOWN, t12, t13);
        var t14 = new Tile(YELLOW, STAR);
        var t15 = new Tile(ORANGE, STAR);
        add(grid, -3, -2, LEFT, t14, t15);
        var t16 = new Tile(ORANGE, CROSS);
        var t17 = new Tile(ORANGE, DIAMOND);
        add(grid, -2, -3, DOWN, t16, t17);
        var t18 = new Tile(YELLOW, DIAMOND);
        var t19 = new Tile(YELLOW, ROUND);
        add(grid, -1, -2, DOWN, t18, t19);
        var t20 = new Tile(RED, STAR);
        add(grid, -3, 0, t20);
        var t21 = new Tile(BLUE, CROSS);
        var t22 = new Tile(RED, CROSS);
        var t23 = new Tile(ORANGE, CROSS);

        add(grid, 2, 1, LEFT, t21, t22, t23);

        assertEquals(t21, get(grid, 2, 1));
        assertEquals(t22, get(grid, 2, 0));
        assertEquals(t23, get(grid, 2, -1));
    }

    @Test
    void rules_elvire_k_Last_Color() {
        var t1 = new Tile(RED, ROUND);
        var t2 = new Tile(RED, DIAMOND);
        var t3 = new Tile(RED, PLUS);
        grid.firstAdd(UP, t1, t2, t3);
        var t4 = new Tile(RED, SQUARE);
        var t5 = new Tile(BLUE, SQUARE);
        var t6 = new Tile(PURPLE, SQUARE);
        add(grid, 1, 0, RIGHT, t4, t5, t6);
        var t7 = new Tile(BLUE, ROUND);
        add(grid, 0, 1, UP, t7);
        var t8 = new Tile(GREEN, PLUS);
        var t9 = new Tile(GREEN, DIAMOND);
        add(grid, -2, -1, DOWN, t8, t9);
        var t10 = new Tile(GREEN, STAR);
        var t11 = new Tile(GREEN, ROUND);
        var t10Pos1 = createTileAtpos(-3, -1, t10);
        var t11Pos2 = createTileAtpos(0, -1, t11);
        grid.add(t10Pos1, t11Pos2);
        var t12 = new Tile(ORANGE, SQUARE);
        var t13 = new Tile(RED, SQUARE);
        add(grid, 1, 3, DOWN, t12, t13);
        var t14 = new Tile(YELLOW, STAR);
        var t15 = new Tile(ORANGE, STAR);
        add(grid, -3, -2, LEFT, t14, t15);
        var t16 = new Tile(ORANGE, CROSS);
        var t17 = new Tile(ORANGE, DIAMOND);
        add(grid, -2, -3, DOWN, t16, t17);
        var t18 = new Tile(YELLOW, DIAMOND);
        var t19 = new Tile(YELLOW, ROUND);
        add(grid, -1, -2, DOWN, t18, t19);
        var t20 = new Tile(RED, STAR);
        add(grid, -3, 0, t20);
        var t21 = new Tile(BLUE, CROSS);
        var t22 = new Tile(RED, CROSS);
        var t23 = new Tile(GREEN, CROSS);

        add(grid, 2, 1, LEFT, t21, t22, t23);

        assertEquals(t21, get(grid, 2, 1));
        assertEquals(t22, get(grid, 2, 0));
        assertEquals(t23, get(grid, 2, -1));
    }
    @Test
    void rules_elvire_k_FirstFail_Shape() {
        var t1 = new Tile(RED, ROUND);
        var t2 = new Tile(RED, DIAMOND);
        var t3 = new Tile(RED, PLUS);
        grid.firstAdd(UP, t1, t2, t3);
        var t4 = new Tile(RED, SQUARE);
        var t5 = new Tile(BLUE, SQUARE);
        var t6 = new Tile(PURPLE, SQUARE);
        add(grid, 1, 0, RIGHT, t4, t5, t6);
        var t7 = new Tile(BLUE, ROUND);
        add(grid, 0, 1, UP, t7);
        var t8 = new Tile(GREEN, PLUS);
        var t9 = new Tile(GREEN, DIAMOND);
        add(grid, -2, -1, DOWN, t8, t9);
        var t10 = new Tile(GREEN, STAR);
        var t11 = new Tile(GREEN, ROUND);
        var t10Pos1 = createTileAtpos(-3, -1, t10);
        var t11Pos2 = createTileAtpos(0, -1, t11);
        grid.add(t10Pos1, t11Pos2);
        var t12 = new Tile(ORANGE, SQUARE);
        var t13 = new Tile(RED, SQUARE);
        add(grid, 1, 3, DOWN, t12, t13);
        var t14 = new Tile(YELLOW, STAR);
        var t15 = new Tile(ORANGE, STAR);
        add(grid, -3, -2, LEFT, t14, t15);
        var t16 = new Tile(ORANGE, CROSS);
        var t17 = new Tile(ORANGE, DIAMOND);
        add(grid, -2, -3, DOWN, t16, t17);
        var t18 = new Tile(YELLOW, DIAMOND);
        var t19 = new Tile(YELLOW, ROUND);
        add(grid, -1, -2, DOWN, t18, t19);
        var t20 = new Tile(RED, STAR);
        add(grid, -3, 0, t20);
        var t21 = new Tile(BLUE, DIAMOND);
        var t22 = new Tile(RED, CROSS);
        var t23 = new Tile(ORANGE, CROSS);

        assertThrows(QwirkleException.class, () ->
                add(grid, 2, 1, LEFT, t21, t22, t23));
    }

    @Test
    void rules_elvire_k_FirstFail_Color() {
        var t1 = new Tile(RED, ROUND);
        var t2 = new Tile(RED, DIAMOND);
        var t3 = new Tile(RED, PLUS);
        grid.firstAdd(UP, t1, t2, t3);
        var t4 = new Tile(RED, SQUARE);
        var t5 = new Tile(BLUE, SQUARE);
        var t6 = new Tile(PURPLE, SQUARE);
        add(grid, 1, 0, RIGHT, t4, t5, t6);
        var t7 = new Tile(BLUE, ROUND);
        add(grid, 0, 1, UP, t7);
        var t8 = new Tile(GREEN, PLUS);
        var t9 = new Tile(GREEN, DIAMOND);
        add(grid, -2, -1, DOWN, t8, t9);
        var t10 = new Tile(GREEN, STAR);
        var t11 = new Tile(GREEN, ROUND);
        var t10Pos1 = createTileAtpos(-3, -1, t10);
        var t11Pos2 = createTileAtpos(0, -1, t11);
        grid.add(t10Pos1, t11Pos2);
        var t12 = new Tile(ORANGE, SQUARE);
        var t13 = new Tile(RED, SQUARE);
        add(grid, 1, 3, DOWN, t12, t13);
        var t14 = new Tile(YELLOW, STAR);
        var t15 = new Tile(ORANGE, STAR);
        add(grid, -3, -2, LEFT, t14, t15);
        var t16 = new Tile(ORANGE, CROSS);
        var t17 = new Tile(ORANGE, DIAMOND);
        add(grid, -2, -3, DOWN, t16, t17);
        var t18 = new Tile(YELLOW, DIAMOND);
        var t19 = new Tile(YELLOW, ROUND);
        add(grid, -1, -2, DOWN, t18, t19);
        var t20 = new Tile(RED, STAR);
        add(grid, -3, 0, t20);
        var t21 = new Tile(YELLOW, CROSS);
        var t22 = new Tile(RED, CROSS);
        var t23 = new Tile(ORANGE, CROSS);

        assertThrows(QwirkleException.class, () ->
                add(grid, 2, 1, LEFT, t21, t22, t23));
    }

    @Test
    void rules_elvire_k_MiddleFail_Shape() {
        var t1 = new Tile(RED, ROUND);
        var t2 = new Tile(RED, DIAMOND);
        var t3 = new Tile(RED, PLUS);
        grid.firstAdd(UP, t1, t2, t3);
        var t4 = new Tile(RED, SQUARE);
        var t5 = new Tile(BLUE, SQUARE);
        var t6 = new Tile(PURPLE, SQUARE);
        add(grid, 1, 0, RIGHT, t4, t5, t6);
        var t7 = new Tile(BLUE, ROUND);
        add(grid, 0, 1, UP, t7);
        var t8 = new Tile(GREEN, PLUS);
        var t9 = new Tile(GREEN, DIAMOND);
        add(grid, -2, -1, DOWN, t8, t9);
        var t10 = new Tile(GREEN, STAR);
        var t11 = new Tile(GREEN, ROUND);
        var t10Pos1 = createTileAtpos(-3, -1, t10);
        var t11Pos2 = createTileAtpos(0, -1, t11);
        grid.add(t10Pos1, t11Pos2);
        var t12 = new Tile(ORANGE, SQUARE);
        var t13 = new Tile(RED, SQUARE);
        add(grid, 1, 3, DOWN, t12, t13);
        var t14 = new Tile(YELLOW, STAR);
        var t15 = new Tile(ORANGE, STAR);
        add(grid, -3, -2, LEFT, t14, t15);
        var t16 = new Tile(ORANGE, CROSS);
        var t17 = new Tile(ORANGE, DIAMOND);
        add(grid, -2, -3, DOWN, t16, t17);
        var t18 = new Tile(YELLOW, DIAMOND);
        var t19 = new Tile(YELLOW, ROUND);
        add(grid, -1, -2, DOWN, t18, t19);
        var t20 = new Tile(RED, STAR);
        add(grid, -3, 0, t20);
        var t21 = new Tile(BLUE, CROSS);
        var t22 = new Tile(RED, STAR);
        var t23 = new Tile(ORANGE, CROSS);

        assertThrows(QwirkleException.class, () ->
                add(grid, 2, 1, LEFT, t21, t22, t23));
    }

    @Test
    void rules_elvire_k_MiddleFail_Color() {
        var t1 = new Tile(RED, ROUND);
        var t2 = new Tile(RED, DIAMOND);
        var t3 = new Tile(RED, PLUS);
        grid.firstAdd(UP, t1, t2, t3);
        var t4 = new Tile(RED, SQUARE);
        var t5 = new Tile(BLUE, SQUARE);
        var t6 = new Tile(PURPLE, SQUARE);
        add(grid, 1, 0, RIGHT, t4, t5, t6);
        var t7 = new Tile(BLUE, ROUND);
        add(grid, 0, 1, UP, t7);
        var t8 = new Tile(GREEN, PLUS);
        var t9 = new Tile(GREEN, DIAMOND);
        add(grid, -2, -1, DOWN, t8, t9);
        var t10 = new Tile(GREEN, STAR);
        var t11 = new Tile(GREEN, ROUND);
        var t10Pos1 = createTileAtpos(-3, -1, t10);
        var t11Pos2 = createTileAtpos(0, -1, t11);
        grid.add(t10Pos1, t11Pos2);
        var t12 = new Tile(ORANGE, SQUARE);
        var t13 = new Tile(RED, SQUARE);
        add(grid, 1, 3, DOWN, t12, t13);
        var t14 = new Tile(YELLOW, STAR);
        var t15 = new Tile(ORANGE, STAR);
        add(grid, -3, -2, LEFT, t14, t15);
        var t16 = new Tile(ORANGE, CROSS);
        var t17 = new Tile(ORANGE, DIAMOND);
        add(grid, -2, -3, DOWN, t16, t17);
        var t18 = new Tile(YELLOW, DIAMOND);
        var t19 = new Tile(YELLOW, ROUND);
        add(grid, -1, -2, DOWN, t18, t19);
        var t20 = new Tile(RED, STAR);
        add(grid, -3, 0, t20);
        var t21 = new Tile(BLUE, CROSS);
        var t22 = new Tile(GREEN, CROSS);
        var t23 = new Tile(ORANGE, CROSS);

        assertThrows(QwirkleException.class, () ->
                add(grid, 2, 1, LEFT, t21, t22, t23));
    }

    @Test
    void rules_elvire_k_LastFail_Shape() {
        var t1 = new Tile(RED, ROUND);
        var t2 = new Tile(RED, DIAMOND);
        var t3 = new Tile(RED, PLUS);
        grid.firstAdd(UP, t1, t2, t3);
        var t4 = new Tile(RED, SQUARE);
        var t5 = new Tile(BLUE, SQUARE);
        var t6 = new Tile(PURPLE, SQUARE);
        add(grid, 1, 0, RIGHT, t4, t5, t6);
        var t7 = new Tile(BLUE, ROUND);
        add(grid, 0, 1, UP, t7);
        var t8 = new Tile(GREEN, PLUS);
        var t9 = new Tile(GREEN, DIAMOND);
        add(grid, -2, -1, DOWN, t8, t9);
        var t10 = new Tile(GREEN, STAR);
        var t11 = new Tile(GREEN, ROUND);
        var t10Pos1 = createTileAtpos(-3, -1, t10);
        var t11Pos2 = createTileAtpos(0, -1, t11);
        grid.add(t10Pos1, t11Pos2);
        var t12 = new Tile(ORANGE, SQUARE);
        var t13 = new Tile(RED, SQUARE);
        add(grid, 1, 3, DOWN, t12, t13);
        var t14 = new Tile(YELLOW, STAR);
        var t15 = new Tile(ORANGE, STAR);
        add(grid, -3, -2, LEFT, t14, t15);
        var t16 = new Tile(ORANGE, CROSS);
        var t17 = new Tile(ORANGE, DIAMOND);
        add(grid, -2, -3, DOWN, t16, t17);
        var t18 = new Tile(YELLOW, DIAMOND);
        var t19 = new Tile(YELLOW, ROUND);
        add(grid, -1, -2, DOWN, t18, t19);
        var t20 = new Tile(RED, STAR);
        add(grid, -3, 0, t20);
        var t21 = new Tile(BLUE, CROSS);
        var t22 = new Tile(RED, CROSS);
        var t23 = new Tile(ORANGE, SQUARE);

        assertThrows(QwirkleException.class, () ->
                add(grid, 2, 1, LEFT, t21, t22, t23));
    }

    @Test
    void rules_elvire_k_Fail_Direction_DOWN_BLUE() {
        var t1 = new Tile(RED, ROUND);
        var t2 = new Tile(RED, DIAMOND);
        var t3 = new Tile(RED, PLUS);
        grid.firstAdd(UP, t1, t2, t3);
        var t4 = new Tile(RED, SQUARE);
        var t5 = new Tile(BLUE, SQUARE);
        var t6 = new Tile(PURPLE, SQUARE);
        add(grid, 1, 0, RIGHT, t4, t5, t6);
        var t7 = new Tile(BLUE, ROUND);
        add(grid, 0, 1, UP, t7);
        var t8 = new Tile(GREEN, PLUS);
        var t9 = new Tile(GREEN, DIAMOND);
        add(grid, -2, -1, DOWN, t8, t9);
        var t10 = new Tile(GREEN, STAR);
        var t11 = new Tile(GREEN, ROUND);
        var t10Pos1 = createTileAtpos(-3, -1, t10);
        var t11Pos2 = createTileAtpos(0, -1, t11);
        grid.add(t10Pos1, t11Pos2);
        var t12 = new Tile(ORANGE, SQUARE);
        var t13 = new Tile(RED, SQUARE);
        add(grid, 1, 3, DOWN, t12, t13);
        var t14 = new Tile(YELLOW, STAR);
        var t15 = new Tile(ORANGE, STAR);
        add(grid, -3, -2, LEFT, t14, t15);
        var t16 = new Tile(ORANGE, CROSS);
        var t17 = new Tile(ORANGE, DIAMOND);
        add(grid, -2, -3, DOWN, t16, t17);
        var t18 = new Tile(YELLOW, DIAMOND);
        var t19 = new Tile(YELLOW, ROUND);
        add(grid, -1, -2, DOWN, t18, t19);
        var t20 = new Tile(RED, STAR);
        add(grid, -3, 0, t20);
        var t21 = new Tile(BLUE, CROSS);
        var t22 = new Tile(RED, CROSS);
        var t23 = new Tile(ORANGE, CROSS);

        assertThrows(QwirkleException.class, () ->
                add(grid, 2, 1, DOWN, t21, t22, t23));
    }

    @Test
    void rules_elvire_k_Fail_Direction_DOWN_ORANGE() {
        var t1 = new Tile(RED, ROUND);
        var t2 = new Tile(RED, DIAMOND);
        var t3 = new Tile(RED, PLUS);
        grid.firstAdd(UP, t1, t2, t3);
        var t4 = new Tile(RED, SQUARE);
        var t5 = new Tile(BLUE, SQUARE);
        var t6 = new Tile(PURPLE, SQUARE);
        add(grid, 1, 0, RIGHT, t4, t5, t6);
        var t7 = new Tile(BLUE, ROUND);
        add(grid, 0, 1, UP, t7);
        var t8 = new Tile(GREEN, PLUS);
        var t9 = new Tile(GREEN, DIAMOND);
        add(grid, -2, -1, DOWN, t8, t9);
        var t10 = new Tile(GREEN, STAR);
        var t11 = new Tile(GREEN, ROUND);
        var t10Pos1 = createTileAtpos(-3, -1, t10);
        var t11Pos2 = createTileAtpos(0, -1, t11);
        grid.add(t10Pos1, t11Pos2);
        var t12 = new Tile(ORANGE, SQUARE);
        var t13 = new Tile(RED, SQUARE);
        add(grid, 1, 3, DOWN, t12, t13);
        var t14 = new Tile(YELLOW, STAR);
        var t15 = new Tile(ORANGE, STAR);
        add(grid, -3, -2, LEFT, t14, t15);
        var t16 = new Tile(ORANGE, CROSS);
        var t17 = new Tile(ORANGE, DIAMOND);
        add(grid, -2, -3, DOWN, t16, t17);
        var t18 = new Tile(YELLOW, DIAMOND);
        var t19 = new Tile(YELLOW, ROUND);
        add(grid, -1, -2, DOWN, t18, t19);
        var t20 = new Tile(RED, STAR);
        add(grid, -3, 0, t20);
        var t21 = new Tile(BLUE, CROSS);
        var t22 = new Tile(RED, CROSS);
        var t23 = new Tile(ORANGE, CROSS);

        assertThrows(QwirkleException.class, () ->
                add(grid, 2, -1, DOWN, t23, t22, t21));
    }

    @Test
    void rules_vincent_l() {
        var t1 = new Tile(RED, ROUND);
        var t2 = new Tile(RED, DIAMOND);
        var t3 = new Tile(RED, PLUS);
        grid.firstAdd(UP, t1, t2, t3);
        var t4 = new Tile(RED, SQUARE);
        var t5 = new Tile(BLUE, SQUARE);
        var t6 = new Tile(PURPLE, SQUARE);
        add(grid, 1, 0, RIGHT, t4, t5, t6);
        var t7 = new Tile(BLUE, ROUND);
        add(grid, 0, 1, UP, t7);
        var t8 = new Tile(GREEN, PLUS);
        var t9 = new Tile(GREEN, DIAMOND);
        add(grid, -2, -1, DOWN, t8, t9);
        var t10 = new Tile(GREEN, STAR);
        var t11 = new Tile(GREEN, ROUND);
        var t10Pos1 = createTileAtpos(-3, -1, t10);
        var t11Pos2 = createTileAtpos(0, -1, t11);
        grid.add(t10Pos1, t11Pos2);
        var t12 = new Tile(ORANGE, SQUARE);
        var t13 = new Tile(RED, SQUARE);
        add(grid, 1, 3, DOWN, t12, t13);
        var t14 = new Tile(YELLOW, STAR);
        var t15 = new Tile(ORANGE, STAR);
        add(grid, -3, -2, LEFT, t14, t15);
        var t16 = new Tile(ORANGE, CROSS);
        var t17 = new Tile(ORANGE, DIAMOND);
        add(grid, -2, -3, DOWN, t16, t17);
        var t18 = new Tile(YELLOW, DIAMOND);
        var t19 = new Tile(YELLOW, ROUND);
        add(grid, -1, -2, DOWN, t18, t19);
        var t20 = new Tile(RED, STAR);
        add(grid, -3, 0, t20);
        var t21 = new Tile(BLUE, CROSS);
        var t22 = new Tile(RED, CROSS);
        var t23 = new Tile(ORANGE, CROSS);
        add(grid, 2, 1, LEFT, t21, t22, t23);
        var t24 = new Tile(YELLOW, SQUARE);
        var t25 = new Tile(BLUE, SQUARE);

        add(grid, 1, 4, DOWN, t24, t25);

        assertEquals(t24, get(grid, 1, 4));
        assertEquals(t25, get(grid, 2, 4));
    }

    @Test
    void rules_vincent_l_other_Color() {
        var t1 = new Tile(RED, ROUND);
        var t2 = new Tile(RED, DIAMOND);
        var t3 = new Tile(RED, PLUS);
        grid.firstAdd(UP, t1, t2, t3);
        var t4 = new Tile(RED, SQUARE);
        var t5 = new Tile(BLUE, SQUARE);
        var t6 = new Tile(PURPLE, SQUARE);
        add(grid, 1, 0, RIGHT, t4, t5, t6);
        var t7 = new Tile(BLUE, ROUND);
        add(grid, 0, 1, UP, t7);
        var t8 = new Tile(GREEN, PLUS);
        var t9 = new Tile(GREEN, DIAMOND);
        add(grid, -2, -1, DOWN, t8, t9);
        var t10 = new Tile(GREEN, STAR);
        var t11 = new Tile(GREEN, ROUND);
        var t10Pos1 = createTileAtpos(-3, -1, t10);
        var t11Pos2 = createTileAtpos(0, -1, t11);
        grid.add(t10Pos1, t11Pos2);
        var t12 = new Tile(ORANGE, SQUARE);
        var t13 = new Tile(RED, SQUARE);
        add(grid, 1, 3, DOWN, t12, t13);
        var t14 = new Tile(YELLOW, STAR);
        var t15 = new Tile(ORANGE, STAR);
        add(grid, -3, -2, LEFT, t14, t15);
        var t16 = new Tile(ORANGE, CROSS);
        var t17 = new Tile(ORANGE, DIAMOND);
        add(grid, -2, -3, DOWN, t16, t17);
        var t18 = new Tile(YELLOW, DIAMOND);
        var t19 = new Tile(YELLOW, ROUND);
        add(grid, -1, -2, DOWN, t18, t19);
        var t20 = new Tile(RED, STAR);
        add(grid, -3, 0, t20);
        var t21 = new Tile(BLUE, CROSS);
        var t22 = new Tile(RED, CROSS);
        var t23 = new Tile(ORANGE, CROSS);
        add(grid, 2, 1, LEFT, t21, t22, t23);
        var t24 = new Tile(GREEN, SQUARE);
        var t25 = new Tile(YELLOW, SQUARE);

        add(grid, 1, 4, DOWN, t24, t25);

        assertEquals(t24, get(grid, 1, 4));
        assertEquals(t25, get(grid, 2, 4));
    }

    @Test
    void rules_vincent_l_FirstFail_Shape() {
        var t1 = new Tile(RED, ROUND);
        var t2 = new Tile(RED, DIAMOND);
        var t3 = new Tile(RED, PLUS);
        grid.firstAdd(UP, t1, t2, t3);
        var t4 = new Tile(RED, SQUARE);
        var t5 = new Tile(BLUE, SQUARE);
        var t6 = new Tile(PURPLE, SQUARE);
        add(grid, 1, 0, RIGHT, t4, t5, t6);
        var t7 = new Tile(BLUE, ROUND);
        add(grid, 0, 1, UP, t7);
        var t8 = new Tile(GREEN, PLUS);
        var t9 = new Tile(GREEN, DIAMOND);
        add(grid, -2, -1, DOWN, t8, t9);
        var t10 = new Tile(GREEN, STAR);
        var t11 = new Tile(GREEN, ROUND);
        var t10Pos1 = createTileAtpos(-3, -1, t10);
        var t11Pos2 = createTileAtpos(0, -1, t11);
        grid.add(t10Pos1, t11Pos2);
        var t12 = new Tile(ORANGE, SQUARE);
        var t13 = new Tile(RED, SQUARE);
        add(grid, 1, 3, DOWN, t12, t13);
        var t14 = new Tile(YELLOW, STAR);
        var t15 = new Tile(ORANGE, STAR);
        add(grid, -3, -2, LEFT, t14, t15);
        var t16 = new Tile(ORANGE, CROSS);
        var t17 = new Tile(ORANGE, DIAMOND);
        add(grid, -2, -3, DOWN, t16, t17);
        var t18 = new Tile(YELLOW, DIAMOND);
        var t19 = new Tile(YELLOW, ROUND);
        add(grid, -1, -2, DOWN, t18, t19);
        var t20 = new Tile(RED, STAR);
        add(grid, -3, 0, t20);
        var t21 = new Tile(BLUE, CROSS);
        var t22 = new Tile(RED, CROSS);
        var t23 = new Tile(ORANGE, CROSS);
        add(grid, 2, 1, LEFT, t21, t22, t23);
        var t24 = new Tile(YELLOW, CROSS);
        var t25 = new Tile(BLUE, SQUARE);

        assertThrows(QwirkleException.class, () ->
                add(grid, 1, 4, DOWN, t24, t25));
    }

    @Test
    void rules_vincent_l_FirstFail_Color() {
        var t1 = new Tile(RED, ROUND);
        var t2 = new Tile(RED, DIAMOND);
        var t3 = new Tile(RED, PLUS);
        grid.firstAdd(UP, t1, t2, t3);
        var t4 = new Tile(RED, SQUARE);
        var t5 = new Tile(BLUE, SQUARE);
        var t6 = new Tile(PURPLE, SQUARE);
        add(grid, 1, 0, RIGHT, t4, t5, t6);
        var t7 = new Tile(BLUE, ROUND);
        add(grid, 0, 1, UP, t7);
        var t8 = new Tile(GREEN, PLUS);
        var t9 = new Tile(GREEN, DIAMOND);
        add(grid, -2, -1, DOWN, t8, t9);
        var t10 = new Tile(GREEN, STAR);
        var t11 = new Tile(GREEN, ROUND);
        var t10Pos1 = createTileAtpos(-3, -1, t10);
        var t11Pos2 = createTileAtpos(0, -1, t11);
        grid.add(t10Pos1, t11Pos2);
        var t12 = new Tile(ORANGE, SQUARE);
        var t13 = new Tile(RED, SQUARE);
        add(grid, 1, 3, DOWN, t12, t13);
        var t14 = new Tile(YELLOW, STAR);
        var t15 = new Tile(ORANGE, STAR);
        add(grid, -3, -2, LEFT, t14, t15);
        var t16 = new Tile(ORANGE, CROSS);
        var t17 = new Tile(ORANGE, DIAMOND);
        add(grid, -2, -3, DOWN, t16, t17);
        var t18 = new Tile(YELLOW, DIAMOND);
        var t19 = new Tile(YELLOW, ROUND);
        add(grid, -1, -2, DOWN, t18, t19);
        var t20 = new Tile(RED, STAR);
        add(grid, -3, 0, t20);
        var t21 = new Tile(BLUE, CROSS);
        var t22 = new Tile(RED, CROSS);
        var t23 = new Tile(ORANGE, CROSS);
        add(grid, 2, 1, LEFT, t21, t22, t23);
        var t24 = new Tile(RED, SQUARE);
        var t25 = new Tile(BLUE, SQUARE);

        assertThrows(QwirkleException.class, () ->
                add(grid, 1, 4, DOWN, t24, t25));
    }

    @Test
    void rules_vincent_l_LastFail_Shape() {
        var t1 = new Tile(RED, ROUND);
        var t2 = new Tile(RED, DIAMOND);
        var t3 = new Tile(RED, PLUS);
        grid.firstAdd(UP, t1, t2, t3);
        var t4 = new Tile(RED, SQUARE);
        var t5 = new Tile(BLUE, SQUARE);
        var t6 = new Tile(PURPLE, SQUARE);
        add(grid, 1, 0, RIGHT, t4, t5, t6);
        var t7 = new Tile(BLUE, ROUND);
        add(grid, 0, 1, UP, t7);
        var t8 = new Tile(GREEN, PLUS);
        var t9 = new Tile(GREEN, DIAMOND);
        add(grid, -2, -1, DOWN, t8, t9);
        var t10 = new Tile(GREEN, STAR);
        var t11 = new Tile(GREEN, ROUND);
        var t10Pos1 = createTileAtpos(-3, -1, t10);
        var t11Pos2 = createTileAtpos(0, -1, t11);
        grid.add(t10Pos1, t11Pos2);
        var t12 = new Tile(ORANGE, SQUARE);
        var t13 = new Tile(RED, SQUARE);
        add(grid, 1, 3, DOWN, t12, t13);
        var t14 = new Tile(YELLOW, STAR);
        var t15 = new Tile(ORANGE, STAR);
        add(grid, -3, -2, LEFT, t14, t15);
        var t16 = new Tile(ORANGE, CROSS);
        var t17 = new Tile(ORANGE, DIAMOND);
        add(grid, -2, -3, DOWN, t16, t17);
        var t18 = new Tile(YELLOW, DIAMOND);
        var t19 = new Tile(YELLOW, ROUND);
        add(grid, -1, -2, DOWN, t18, t19);
        var t20 = new Tile(RED, STAR);
        add(grid, -3, 0, t20);
        var t21 = new Tile(BLUE, CROSS);
        var t22 = new Tile(RED, CROSS);
        var t23 = new Tile(ORANGE, CROSS);
        add(grid, 2, 1, LEFT, t21, t22, t23);
        var t24 = new Tile(YELLOW, SQUARE);
        var t25 = new Tile(BLUE, CROSS);

        assertThrows(QwirkleException.class, () ->
                add(grid, 1, 4, DOWN, t24, t25));
    }

    @Test
    void rules_vincent_l_LastFail_Color() {
        var t1 = new Tile(RED, ROUND);
        var t2 = new Tile(RED, DIAMOND);
        var t3 = new Tile(RED, PLUS);
        grid.firstAdd(UP, t1, t2, t3);
        var t4 = new Tile(RED, SQUARE);
        var t5 = new Tile(BLUE, SQUARE);
        var t6 = new Tile(PURPLE, SQUARE);
        add(grid, 1, 0, RIGHT, t4, t5, t6);
        var t7 = new Tile(BLUE, ROUND);
        add(grid, 0, 1, UP, t7);
        var t8 = new Tile(GREEN, PLUS);
        var t9 = new Tile(GREEN, DIAMOND);
        add(grid, -2, -1, DOWN, t8, t9);
        var t10 = new Tile(GREEN, STAR);
        var t11 = new Tile(GREEN, ROUND);
        var t10Pos1 = createTileAtpos(-3, -1, t10);
        var t11Pos2 = createTileAtpos(0, -1, t11);
        grid.add(t10Pos1, t11Pos2);
        var t12 = new Tile(ORANGE, SQUARE);
        var t13 = new Tile(RED, SQUARE);
        add(grid, 1, 3, DOWN, t12, t13);
        var t14 = new Tile(YELLOW, STAR);
        var t15 = new Tile(ORANGE, STAR);
        add(grid, -3, -2, LEFT, t14, t15);
        var t16 = new Tile(ORANGE, CROSS);
        var t17 = new Tile(ORANGE, DIAMOND);
        add(grid, -2, -3, DOWN, t16, t17);
        var t18 = new Tile(YELLOW, DIAMOND);
        var t19 = new Tile(YELLOW, ROUND);
        add(grid, -1, -2, DOWN, t18, t19);
        var t20 = new Tile(RED, STAR);
        add(grid, -3, 0, t20);
        var t21 = new Tile(BLUE, CROSS);
        var t22 = new Tile(RED, CROSS);
        var t23 = new Tile(ORANGE, CROSS);
        add(grid, 2, 1, LEFT, t21, t22, t23);
        var t24 = new Tile(YELLOW, SQUARE);
        var t25 = new Tile(RED, SQUARE);

        assertThrows(QwirkleException.class, () ->
                add(grid, 1, 4, DOWN, t24, t25));
    }

    @Test
    void rules_vincent_l_Fail_Shape() {
        var t1 = new Tile(RED, ROUND);
        var t2 = new Tile(RED, DIAMOND);
        var t3 = new Tile(RED, PLUS);
        grid.firstAdd(UP, t1, t2, t3);
        var t4 = new Tile(RED, SQUARE);
        var t5 = new Tile(BLUE, SQUARE);
        var t6 = new Tile(PURPLE, SQUARE);
        add(grid, 1, 0, RIGHT, t4, t5, t6);
        var t7 = new Tile(BLUE, ROUND);
        add(grid, 0, 1, UP, t7);
        var t8 = new Tile(GREEN, PLUS);
        var t9 = new Tile(GREEN, DIAMOND);
        add(grid, -2, -1, DOWN, t8, t9);
        var t10 = new Tile(GREEN, STAR);
        var t11 = new Tile(GREEN, ROUND);
        var t10Pos1 = createTileAtpos(-3, -1, t10);
        var t11Pos2 = createTileAtpos(0, -1, t11);
        grid.add(t10Pos1, t11Pos2);
        var t12 = new Tile(ORANGE, SQUARE);
        var t13 = new Tile(RED, SQUARE);
        add(grid, 1, 3, DOWN, t12, t13);
        var t14 = new Tile(YELLOW, STAR);
        var t15 = new Tile(ORANGE, STAR);
        add(grid, -3, -2, LEFT, t14, t15);
        var t16 = new Tile(ORANGE, CROSS);
        var t17 = new Tile(ORANGE, DIAMOND);
        add(grid, -2, -3, DOWN, t16, t17);
        var t18 = new Tile(YELLOW, DIAMOND);
        var t19 = new Tile(YELLOW, ROUND);
        add(grid, -1, -2, DOWN, t18, t19);
        var t20 = new Tile(RED, STAR);
        add(grid, -3, 0, t20);
        var t21 = new Tile(BLUE, CROSS);
        var t22 = new Tile(RED, CROSS);
        var t23 = new Tile(ORANGE, CROSS);
        add(grid, 2, 1, LEFT, t21, t22, t23);
        var t24 = new Tile(YELLOW, CROSS);
        var t25 = new Tile(BLUE, CROSS);

        assertThrows(QwirkleException.class, () ->
                add(grid, 1, 4, DOWN, t24, t25));
    }

    @Test
    void rules_vincent_l_Fail_Color() {
        var t1 = new Tile(RED, ROUND);
        var t2 = new Tile(RED, DIAMOND);
        var t3 = new Tile(RED, PLUS);
        grid.firstAdd(UP, t1, t2, t3);
        var t4 = new Tile(RED, SQUARE);
        var t5 = new Tile(BLUE, SQUARE);
        var t6 = new Tile(PURPLE, SQUARE);
        add(grid, 1, 0, RIGHT, t4, t5, t6);
        var t7 = new Tile(BLUE, ROUND);
        add(grid, 0, 1, UP, t7);
        var t8 = new Tile(GREEN, PLUS);
        var t9 = new Tile(GREEN, DIAMOND);
        add(grid, -2, -1, DOWN, t8, t9);
        var t10 = new Tile(GREEN, STAR);
        var t11 = new Tile(GREEN, ROUND);
        var t10Pos1 = createTileAtpos(-3, -1, t10);
        var t11Pos2 = createTileAtpos(0, -1, t11);
        grid.add(t10Pos1, t11Pos2);
        var t12 = new Tile(ORANGE, SQUARE);
        var t13 = new Tile(RED, SQUARE);
        add(grid, 1, 3, DOWN, t12, t13);
        var t14 = new Tile(YELLOW, STAR);
        var t15 = new Tile(ORANGE, STAR);
        add(grid, -3, -2, LEFT, t14, t15);
        var t16 = new Tile(ORANGE, CROSS);
        var t17 = new Tile(ORANGE, DIAMOND);
        add(grid, -2, -3, DOWN, t16, t17);
        var t18 = new Tile(YELLOW, DIAMOND);
        var t19 = new Tile(YELLOW, ROUND);
        add(grid, -1, -2, DOWN, t18, t19);
        var t20 = new Tile(RED, STAR);
        add(grid, -3, 0, t20);
        var t21 = new Tile(BLUE, CROSS);
        var t22 = new Tile(RED, CROSS);
        var t23 = new Tile(ORANGE, CROSS);
        add(grid, 2, 1, LEFT, t21, t22, t23);
        var t24 = new Tile(YELLOW, SQUARE);
        var t25 = new Tile(YELLOW, STAR);

        assertThrows(QwirkleException.class, () ->
                add(grid, 1, 4, DOWN, t24, t25));
    }

    @Test
    void rules_vincent_l_Fail_Direction_RIGHT() {
        var t1 = new Tile(RED, ROUND);
        var t2 = new Tile(RED, DIAMOND);
        var t3 = new Tile(RED, PLUS);
        grid.firstAdd(UP, t1, t2, t3);
        var t4 = new Tile(RED, SQUARE);
        var t5 = new Tile(BLUE, SQUARE);
        var t6 = new Tile(PURPLE, SQUARE);
        add(grid, 1, 0, RIGHT, t4, t5, t6);
        var t7 = new Tile(BLUE, ROUND);
        add(grid, 0, 1, UP, t7);
        var t8 = new Tile(GREEN, PLUS);
        var t9 = new Tile(GREEN, DIAMOND);
        add(grid, -2, -1, DOWN, t8, t9);
        var t10 = new Tile(GREEN, STAR);
        var t11 = new Tile(GREEN, ROUND);
        var t10Pos1 = createTileAtpos(-3, -1, t10);
        var t11Pos2 = createTileAtpos(0, -1, t11);
        grid.add(t10Pos1, t11Pos2);
        var t12 = new Tile(ORANGE, SQUARE);
        var t13 = new Tile(RED, SQUARE);
        add(grid, 1, 3, DOWN, t12, t13);
        var t14 = new Tile(YELLOW, STAR);
        var t15 = new Tile(ORANGE, STAR);
        add(grid, -3, -2, LEFT, t14, t15);
        var t16 = new Tile(ORANGE, CROSS);
        var t17 = new Tile(ORANGE, DIAMOND);
        add(grid, -2, -3, DOWN, t16, t17);
        var t18 = new Tile(YELLOW, DIAMOND);
        var t19 = new Tile(YELLOW, ROUND);
        add(grid, -1, -2, DOWN, t18, t19);
        var t20 = new Tile(RED, STAR);
        add(grid, -3, 0, t20);
        var t21 = new Tile(BLUE, CROSS);
        var t22 = new Tile(RED, CROSS);
        var t23 = new Tile(ORANGE, CROSS);
        add(grid, 2, 1, LEFT, t21, t22, t23);
        var t24 = new Tile(YELLOW, SQUARE);
        var t25 = new Tile(BLUE, SQUARE);

        assertThrows(QwirkleException.class, () ->
                add(grid, 1, 4, RIGHT, t24, t25));
    }

    //More Test with direction

    @Test
    void only_the_last_connect_ok_line() {
        var t1 = new Tile(RED, ROUND);
        var t2 = new Tile(RED, DIAMOND);
        var t3 = new Tile(RED, PLUS);
        grid.firstAdd(UP, t1, t2, t3);
        var t4 = new Tile(BLUE, STAR);
        var t5 = new Tile(GREEN, STAR);
        var t6 = new Tile(RED, STAR);

        add(grid, -3, 2, LEFT, t4, t5, t6);

        assertEquals(t4, get(grid, -3, 2));
        assertEquals(t5, get(grid, -3, 1));
        assertEquals(t6, get(grid, -3, 0));
    }

    @Test
    void only_the_last_and_middle_connect_ok_line() {
        var t1 = new Tile(RED, ROUND);
        var t2 = new Tile(RED, DIAMOND);
        var t3 = new Tile(RED, PLUS);
        grid.firstAdd(UP, t1, t2, t3);
        var t7 = new Tile(GREEN, ROUND);
        var t8 = new Tile(GREEN, DIAMOND);
        var t9 = new Tile(GREEN, PLUS);
        add(grid, 0, 1, UP, t7, t8, t9);
        var t4 = new Tile(BLUE, STAR);
        var t5 = new Tile(GREEN, STAR);
        var t6 = new Tile(RED, STAR);

        add(grid, -3, 2, LEFT, t4, t5, t6);

        assertEquals(t4, get(grid, -3, 2));
        assertEquals(t5, get(grid, -3, 1));
        assertEquals(t6, get(grid, -3, 0));
    }

    @Test
    void only_the_middle_connect_ok_line() {
        var t1 = new Tile(RED, ROUND);
        var t2 = new Tile(RED, DIAMOND);
        var t3 = new Tile(RED, PLUS);
        grid.firstAdd(UP, t1, t2, t3);
        var t4 = new Tile(BLUE, STAR);
        var t5 = new Tile(RED, STAR);
        var t6 = new Tile(GREEN, STAR);

        add(grid, -3, 1, LEFT, t4, t5, t6);

        assertEquals(t4, get(grid, -3, 1));
        assertEquals(t5, get(grid, -3, 0));
        assertEquals(t6, get(grid, -3, -1));
    }

    @Test
    void no_connect_line() {
        var t1 = new Tile(RED, ROUND);
        var t2 = new Tile(RED, DIAMOND);
        var t3 = new Tile(RED, PLUS);
        grid.firstAdd(UP, t1, t2, t3);
        var t4 = new Tile(BLUE, STAR);
        var t5 = new Tile(RED, STAR);
        var t6 = new Tile(GREEN, STAR);

        assertThrows(QwirkleException.class, () ->
                add(grid, -4, 1, LEFT, t4, t5, t6));
    }

    //More test with TileAtPosition

    @Test
    void rules_sonia_e_TileAtPosition_Diff_row_Same_col_than_the_other() {
        var t1 = new Tile(RED, ROUND);
        var t2 = new Tile(RED, DIAMOND);
        var t3 = new Tile(RED, PLUS);
        grid.firstAdd(UP, t1, t2, t3);
        var t4 = new Tile(RED, ROUND);
        add(grid, -1, -1, t4);
        var t5 = new Tile(RED, DIAMOND);
        var t6 = new Tile(RED, STAR);
        var t7 = new Tile(RED, SQUARE);
        TileAtPosition t5Pos1 = createTileAtpos(0, -1, t5);
        TileAtPosition t6Pos2 = createTileAtpos(-2, -1, t6);
        TileAtPosition t7Pos3 = createTileAtpos(0, 1, t7);

        assertThrows(QwirkleException.class, () ->
                grid.add(t5Pos1, t6Pos2, t7Pos3));
    }

    @Test
    void rules_sonia_e_TileAtPosition_line_connect_one_tile() {
        var t1 = new Tile(RED, ROUND);
        var t2 = new Tile(RED, DIAMOND);
        var t3 = new Tile(RED, PLUS);
        grid.firstAdd(UP, t1, t2, t3);
        var t5 = new Tile(BLUE, DIAMOND);
        var t6 = new Tile(YELLOW, DIAMOND);
        var t7 = new Tile(GREEN, DIAMOND);
        TileAtPosition t5Pos1 = createTileAtpos(-1, -1, t5);
        TileAtPosition t6Pos2 = createTileAtpos(-1, -2, t6);
        TileAtPosition t7Pos3 = createTileAtpos(-1, 1, t7);

        grid.add(t5Pos1, t6Pos2, t7Pos3);

        assertEquals(t5, get(grid, -1, -1));
        assertEquals(t6, get(grid, -1, -2));
        assertEquals(t7, get(grid, -1, 1));
    }

    @Test
    void rules_sonia_e_TileAtPosition_line_connect_one_tile_extremity() {
        var t1 = new Tile(RED, ROUND);
        var t2 = new Tile(RED, DIAMOND);
        var t3 = new Tile(RED, PLUS);
        grid.firstAdd(UP, t1, t2, t3);
        var t5 = new Tile(BLUE, DIAMOND);
        var t6 = new Tile(YELLOW, DIAMOND);
        var t7 = new Tile(GREEN, DIAMOND);
        TileAtPosition t5Pos1 = createTileAtpos(-1, -2, t5);
        TileAtPosition t6Pos2 = createTileAtpos(-1, -1, t6);
        TileAtPosition t7Pos3 = createTileAtpos(-1, 1, t7);

        grid.add(t5Pos1, t6Pos2, t7Pos3);

        assertEquals(t5, get(grid, -1, -2));
        assertEquals(t6, get(grid, -1, -1));
        assertEquals(t7, get(grid, -1, 1));
    }

    @Test
    void test_game_1() {
        var t1 = new Tile(GREEN, STAR);
        var t2 = new Tile(GREEN, ROUND);
        grid.firstAdd(UP, t1, t2);
        var t4 = new Tile(PURPLE, STAR);
        var t5 = new Tile(PURPLE, SQUARE);
        add(grid, 1, 1, UP, t5, t4);
        var t7 = new Tile(GREEN, SQUARE);
        var t8 = new Tile(GREEN, CROSS);

        add(grid, 1, 0, DOWN, t7, t8);

        assertEquals(t7, get(grid, 1, 0));
        assertEquals(t8, get(grid, 2, 0));
    }

    @Test
    void rules_sonia_a_point() {
        var t1 = new Tile(RED, ROUND);
        var t2 = new Tile(RED, DIAMOND);
        var t3 = new Tile(RED, PLUS);
        int point;

        point = grid.firstAdd(UP, t1, t2, t3);

        int expectedPoint = 3;
        assertEquals(expectedPoint, point);
    }

    @Test
    void rules_cedric_b_point() {
        var t1 = new Tile(RED, ROUND);
        var t2 = new Tile(RED, DIAMOND);
        var t3 = new Tile(RED, PLUS);
        grid.firstAdd(UP, t1, t2, t3);
        var t4 = new Tile(RED, SQUARE);
        var t5 = new Tile(BLUE, SQUARE);
        var t6 = new Tile(PURPLE, SQUARE);
        int point;

        point = add2(grid, 1, 0, RIGHT, t4, t5, t6);

        int expected = 7;
        assertEquals(expected, point);
    }

    @Test
    void rules_elvire_c_point() {
        var t1 = new Tile(RED, ROUND);
        var t2 = new Tile(RED, DIAMOND);
        var t3 = new Tile(RED, PLUS);
        grid.firstAdd(UP, t1, t2, t3);
        var t4 = new Tile(RED, SQUARE);
        var t5 = new Tile(BLUE, SQUARE);
        var t6 = new Tile(PURPLE, SQUARE);
        add(grid, 1, 0, RIGHT, t4, t5, t6);
        var t7 = new Tile(BLUE, ROUND);
        int point;

        point = add2(grid, 0, 1, t7);


        int expected = 4;
        assertEquals(expected, point);
    }

    @Test
    void rules_vincent_d_point() {
        var t1 = new Tile(RED, ROUND);
        var t2 = new Tile(RED, DIAMOND);
        var t3 = new Tile(RED, PLUS);
        grid.firstAdd(UP, t1, t2, t3);
        var t4 = new Tile(RED, SQUARE);
        var t5 = new Tile(BLUE, SQUARE);
        var t6 = new Tile(PURPLE, SQUARE);
        add(grid, 1, 0, RIGHT, t4, t5, t6);
        var t7 = new Tile(BLUE, ROUND);
        add(grid, 0, 1, UP, t7);
        var t8 = new Tile(GREEN, PLUS);
        var t9 = new Tile(GREEN, DIAMOND);
        int point;

        point = add2(grid, -2, -1, DOWN, t8, t9);

        int expected = 6;
        assertEquals(expected, point);
    }

    @Test
    void rules_sonia_e_point() {
        var t1 = new Tile(RED, ROUND);
        var t2 = new Tile(RED, DIAMOND);
        var t3 = new Tile(RED, PLUS);
        grid.firstAdd(UP, t1, t2, t3);
        var t4 = new Tile(RED, SQUARE);
        var t5 = new Tile(BLUE, SQUARE);
        var t6 = new Tile(PURPLE, SQUARE);
        add(grid, 1, 0, RIGHT, t4, t5, t6);
        var t7 = new Tile(BLUE, ROUND);
        add(grid, 0, 1, UP, t7);
        var t8 = new Tile(GREEN, PLUS);
        var t9 = new Tile(GREEN, DIAMOND);
        add(grid, -2, -1, DOWN, t8, t9);
        var t10 = new Tile(GREEN, STAR);
        var t11 = new Tile(GREEN, ROUND);
        TileAtPosition t10Pos1 = createTileAtpos(-3, -1, t10);
        TileAtPosition t11Pos2 = createTileAtpos(0, -1, t11);

        int point = grid.add(t10Pos1, t11Pos2);

        int expected = 7;
        assertEquals(expected, point);
    }

    @Test
    void rules_cedric_f_point() {
        var t1 = new Tile(RED, ROUND);
        var t2 = new Tile(RED, DIAMOND);
        var t3 = new Tile(RED, PLUS);
        grid.firstAdd(UP, t1, t2, t3);
        var t4 = new Tile(RED, SQUARE);
        var t5 = new Tile(BLUE, SQUARE);
        var t6 = new Tile(PURPLE, SQUARE);
        add(grid, 1, 0, RIGHT, t4, t5, t6);
        var t7 = new Tile(BLUE, ROUND);
        add(grid, 0, 1, UP, t7);
        var t8 = new Tile(GREEN, PLUS);
        var t9 = new Tile(GREEN, DIAMOND);
        add(grid, -2, -1, DOWN, t8, t9);
        var t10 = new Tile(GREEN, STAR);
        var t11 = new Tile(GREEN, ROUND);
        var t10Pos1 = createTileAtpos(-3, -1, t10);
        var t11Pos2 = createTileAtpos(0, -1, t11);
        grid.add(t10Pos1, t11Pos2);
        var t12 = new Tile(ORANGE, SQUARE);
        var t13 = new Tile(RED, SQUARE);

        int point = add2(grid, 1, 3, DOWN, t12, t13);

        int expected = 6;
        assertEquals(expected, point);
    }

    @Test
    void rules_elvire_g_point() {
        var t1 = new Tile(RED, ROUND);
        var t2 = new Tile(RED, DIAMOND);
        var t3 = new Tile(RED, PLUS);
        grid.firstAdd(UP, t1, t2, t3);
        var t4 = new Tile(RED, SQUARE);
        var t5 = new Tile(BLUE, SQUARE);
        var t6 = new Tile(PURPLE, SQUARE);
        add(grid, 1, 0, RIGHT, t4, t5, t6);
        var t7 = new Tile(BLUE, ROUND);
        add(grid, 0, 1, UP, t7);
        var t8 = new Tile(GREEN, PLUS);
        var t9 = new Tile(GREEN, DIAMOND);
        add(grid, -2, -1, DOWN, t8, t9);
        var t10 = new Tile(GREEN, STAR);
        var t11 = new Tile(GREEN, ROUND);
        var t10Pos1 = createTileAtpos(-3, -1, t10);
        var t11Pos2 = createTileAtpos(0, -1, t11);
        grid.add(t10Pos1, t11Pos2);
        var t12 = new Tile(ORANGE, SQUARE);
        var t13 = new Tile(RED, SQUARE);
        add(grid, 1, 3, DOWN, t12, t13);
        var t14 = new Tile(YELLOW, STAR);
        var t15 = new Tile(ORANGE, STAR);

        int point = add2(grid, -3, -2, LEFT, t14, t15);

        int expected = 3;
        assertEquals(expected, point);
    }

    @Test
    void rules_vincent_h_point() {
        var t1 = new Tile(RED, ROUND);
        var t2 = new Tile(RED, DIAMOND);
        var t3 = new Tile(RED, PLUS);
        grid.firstAdd(UP, t1, t2, t3);
        var t4 = new Tile(RED, SQUARE);
        var t5 = new Tile(BLUE, SQUARE);
        var t6 = new Tile(PURPLE, SQUARE);
        add(grid, 1, 0, RIGHT, t4, t5, t6);
        var t7 = new Tile(BLUE, ROUND);
        add(grid, 0, 1, UP, t7);
        var t8 = new Tile(GREEN, PLUS);
        var t9 = new Tile(GREEN, DIAMOND);
        add(grid, -2, -1, DOWN, t8, t9);
        var t10 = new Tile(GREEN, STAR);
        var t11 = new Tile(GREEN, ROUND);
        var t10Pos1 = createTileAtpos(-3, -1, t10);
        var t11Pos2 = createTileAtpos(0, -1, t11);
        grid.add(t10Pos1, t11Pos2);
        var t12 = new Tile(ORANGE, SQUARE);
        var t13 = new Tile(RED, SQUARE);
        add(grid, 1, 3, DOWN, t12, t13);
        var t14 = new Tile(YELLOW, STAR);
        var t15 = new Tile(ORANGE, STAR);
        add(grid, -3, -2, LEFT, t14, t15);
        var t16 = new Tile(ORANGE, CROSS);
        var t17 = new Tile(ORANGE, DIAMOND);

        int point = add2(grid, -2, -3, DOWN, t16, t17);

        int expected = 3;
        assertEquals(expected, point);
    }

    @Test
    void rules_sonia_i_point() {
        var t1 = new Tile(RED, ROUND);
        var t2 = new Tile(RED, DIAMOND);
        var t3 = new Tile(RED, PLUS);
        grid.firstAdd(UP, t1, t2, t3);
        var t4 = new Tile(RED, SQUARE);
        var t5 = new Tile(BLUE, SQUARE);
        var t6 = new Tile(PURPLE, SQUARE);
        add(grid, 1, 0, RIGHT, t4, t5, t6);
        var t7 = new Tile(BLUE, ROUND);
        add(grid, 0, 1, UP, t7);
        var t8 = new Tile(GREEN, PLUS);
        var t9 = new Tile(GREEN, DIAMOND);
        add(grid, -2, -1, DOWN, t8, t9);
        var t10 = new Tile(GREEN, STAR);
        var t11 = new Tile(GREEN, ROUND);
        var t10Pos1 = createTileAtpos(-3, -1, t10);
        var t11Pos2 = createTileAtpos(0, -1, t11);
        grid.add(t10Pos1, t11Pos2);
        var t12 = new Tile(ORANGE, SQUARE);
        var t13 = new Tile(RED, SQUARE);
        add(grid, 1, 3, DOWN, t12, t13);
        var t14 = new Tile(YELLOW, STAR);
        var t15 = new Tile(ORANGE, STAR);
        add(grid, -3, -2, LEFT, t14, t15);
        var t16 = new Tile(ORANGE, CROSS);
        var t17 = new Tile(ORANGE, DIAMOND);
        add(grid, -2, -3, DOWN, t16, t17);
        var t18 = new Tile(YELLOW, DIAMOND);
        var t19 = new Tile(YELLOW, ROUND);

        int point = add2(grid, -1, -2, DOWN, t18, t19);

        int expected = 10;
        assertEquals(expected, point);
    }

    @Test
    void rules_cedric_j_point() {
        var t1 = new Tile(RED, ROUND);
        var t2 = new Tile(RED, DIAMOND);
        var t3 = new Tile(RED, PLUS);
        grid.firstAdd(UP, t1, t2, t3);
        var t4 = new Tile(RED, SQUARE);
        var t5 = new Tile(BLUE, SQUARE);
        var t6 = new Tile(PURPLE, SQUARE);
        add(grid, 1, 0, RIGHT, t4, t5, t6);
        var t7 = new Tile(BLUE, ROUND);
        add(grid, 0, 1, UP, t7);
        var t8 = new Tile(GREEN, PLUS);
        var t9 = new Tile(GREEN, DIAMOND);
        add(grid, -2, -1, DOWN, t8, t9);
        var t10 = new Tile(GREEN, STAR);
        var t11 = new Tile(GREEN, ROUND);
        var t10Pos1 = createTileAtpos(-3, -1, t10);
        var t11Pos2 = createTileAtpos(0, -1, t11);
        grid.add(t10Pos1, t11Pos2);
        var t12 = new Tile(ORANGE, SQUARE);
        var t13 = new Tile(RED, SQUARE);
        add(grid, 1, 3, DOWN, t12, t13);
        var t14 = new Tile(YELLOW, STAR);
        var t15 = new Tile(ORANGE, STAR);
        add(grid, -3, -2, LEFT, t14, t15);
        var t16 = new Tile(ORANGE, CROSS);
        var t17 = new Tile(ORANGE, DIAMOND);
        add(grid, -2, -3, DOWN, t16, t17);
        var t18 = new Tile(YELLOW, DIAMOND);
        var t19 = new Tile(YELLOW, ROUND);
        add(grid, -1, -2, DOWN, t18, t19);
        var t20 = new Tile(RED, STAR);

        int point = add2(grid, -3, 0, t20);

        int expected = 9;
        assertEquals(expected, point);
    }
*/
    @Test
    void rules_cedric_j_point_with_qwirkle() {
        var t1 = new Tile(RED, ROUND);
        var t2 = new Tile(RED, CROSS);
        var t3 = new Tile(RED, PLUS);
        grid.firstAdd(UP, t1, t2, t3);
        var t4 = new Tile(RED, SQUARE);
        var t5 = new Tile(BLUE, SQUARE);
        var t6 = new Tile(GREEN, SQUARE);
        add(grid, 1, 0, RIGHT, t4, t5, t6);
        var t7 = new Tile(BLUE, ROUND);
        add(grid, 0, 1, UP, t7);
        var t8 = new Tile(GREEN, PLUS);
        var t9 = new Tile(GREEN, CROSS);
        add(grid, -2, -1, DOWN, t8, t9);
        var t10 = new Tile(GREEN, STAR);
        var t11 = new Tile(GREEN, ROUND);
        var t10Pos1 = createTileAtpos(-3, -1, t10);
        var t11Pos2 = createTileAtpos(0, -1, t11);
        grid.add(t10Pos1, t11Pos2);
        var t12 = new Tile(ORANGE, SQUARE);
        var t13 = new Tile(RED, SQUARE);
        add(grid, 1, 3, DOWN, t12, t13);
        var t14 = new Tile(YELLOW, STAR);
        var t15 = new Tile(ORANGE, STAR);
        add(grid, -3, -2, LEFT, t14, t15);
        var t16 = new Tile(ORANGE, PLUS);
        var t17 = new Tile(ORANGE, CROSS);
        add(grid, -2, -3, DOWN, t16, t17);
        var t18 = new Tile(YELLOW, CROSS);
        var t19 = new Tile(YELLOW, ROUND);
        add(grid, -1, -2, DOWN, t18, t19);
        var t20 = new Tile(RED, STAR);

        int point = add2(grid, -3, 0, t20);

        int expected = 14;
        assertEquals(expected, point);
    }
/*
    @Test
    void rules_elvire_k_point_without_quirkle() {
        var t1 = new Tile(RED, ROUND);
        var t2 = new Tile(RED, DIAMOND);
        var t3 = new Tile(RED, PLUS);
        grid.firstAdd(UP, t1, t2, t3);
        var t4 = new Tile(RED, SQUARE);
        var t5 = new Tile(BLUE, SQUARE);
        var t6 = new Tile(PURPLE, SQUARE);
        add(grid, 1, 0, RIGHT, t4, t5, t6);
        var t7 = new Tile(BLUE, ROUND);
        add(grid, 0, 1, UP, t7);
        var t8 = new Tile(GREEN, PLUS);
        var t9 = new Tile(GREEN, DIAMOND);
        add(grid, -2, -1, DOWN, t8, t9);
        var t10 = new Tile(GREEN, STAR);
        var t11 = new Tile(GREEN, ROUND);
        var t10Pos1 = createTileAtpos(-3, -1, t10);
        var t11Pos2 = createTileAtpos(0, -1, t11);
        grid.add(t10Pos1, t11Pos2);
        var t12 = new Tile(ORANGE, SQUARE);
        var t13 = new Tile(RED, SQUARE);
        add(grid, 1, 3, DOWN, t12, t13);
        var t14 = new Tile(YELLOW, STAR);
        var t15 = new Tile(ORANGE, STAR);
        add(grid, -3, -2, LEFT, t14, t15);
        var t16 = new Tile(ORANGE, CROSS);
        var t17 = new Tile(ORANGE, DIAMOND);
        add(grid, -2, -3, DOWN, t16, t17);
        var t18 = new Tile(YELLOW, DIAMOND);
        var t19 = new Tile(YELLOW, ROUND);
        add(grid, -1, -2, DOWN, t18, t19);
        var t20 = new Tile(RED, STAR);
        add(grid, -3, 0, t20);
        var t21 = new Tile(BLUE, CROSS);
        var t22 = new Tile(RED, CROSS);
        var t23 = new Tile(ORANGE, CROSS);

        int point = add2(grid, 2, 1, LEFT, t21, t22, t23);
        point -= 6;

        int expected = 12;
        assertEquals(expected, point);
    }

    @Test
    void rules_elvire_k_point_with_quirkle() {
        var t1 = new Tile(RED, ROUND);
        var t2 = new Tile(RED, DIAMOND);
        var t3 = new Tile(RED, PLUS);
        grid.firstAdd(UP, t1, t2, t3);
        var t4 = new Tile(RED, SQUARE);
        var t5 = new Tile(BLUE, SQUARE);
        var t6 = new Tile(PURPLE, SQUARE);
        add(grid, 1, 0, RIGHT, t4, t5, t6);
        var t7 = new Tile(BLUE, ROUND);
        add(grid, 0, 1, UP, t7);
        var t8 = new Tile(GREEN, PLUS);
        var t9 = new Tile(GREEN, DIAMOND);
        add(grid, -2, -1, DOWN, t8, t9);
        var t10 = new Tile(GREEN, STAR);
        var t11 = new Tile(GREEN, ROUND);
        var t10Pos1 = createTileAtpos(-3, -1, t10);
        var t11Pos2 = createTileAtpos(0, -1, t11);
        grid.add(t10Pos1, t11Pos2);
        var t12 = new Tile(ORANGE, SQUARE);
        var t13 = new Tile(RED, SQUARE);
        add(grid, 1, 3, DOWN, t12, t13);
        var t14 = new Tile(YELLOW, STAR);
        var t15 = new Tile(ORANGE, STAR);
        add(grid, -3, -2, LEFT, t14, t15);
        var t16 = new Tile(ORANGE, CROSS);
        var t17 = new Tile(ORANGE, DIAMOND);
        add(grid, -2, -3, DOWN, t16, t17);
        var t18 = new Tile(YELLOW, DIAMOND);
        var t19 = new Tile(YELLOW, ROUND);
        add(grid, -1, -2, DOWN, t18, t19);
        var t20 = new Tile(RED, STAR);
        add(grid, -3, 0, t20);
        var t21 = new Tile(BLUE, CROSS);
        var t22 = new Tile(RED, CROSS);
        var t23 = new Tile(ORANGE, CROSS);

        int point = add2(grid, 2, 1, LEFT, t21, t22, t23);

        int expected = 18;
        assertEquals(expected, point);
    }


    @Test
    void rules_vincent_l_point() {
        var t1 = new Tile(RED, ROUND);
        var t2 = new Tile(RED, DIAMOND);
        var t3 = new Tile(RED, PLUS);
        grid.firstAdd(UP, t1, t2, t3);
        var t4 = new Tile(RED, SQUARE);
        var t5 = new Tile(BLUE, SQUARE);
        var t6 = new Tile(PURPLE, SQUARE);
        add(grid, 1, 0, RIGHT, t4, t5, t6);
        var t7 = new Tile(BLUE, ROUND);
        add(grid, 0, 1, UP, t7);
        var t8 = new Tile(GREEN, PLUS);
        var t9 = new Tile(GREEN, DIAMOND);
        add(grid, -2, -1, DOWN, t8, t9);
        var t10 = new Tile(GREEN, STAR);
        var t11 = new Tile(GREEN, ROUND);
        var t10Pos1 = createTileAtpos(-3, -1, t10);
        var t11Pos2 = createTileAtpos(0, -1, t11);
        grid.add(t10Pos1, t11Pos2);
        var t12 = new Tile(ORANGE, SQUARE);
        var t13 = new Tile(RED, SQUARE);
        add(grid, 1, 3, DOWN, t12, t13);
        var t14 = new Tile(YELLOW, STAR);
        var t15 = new Tile(ORANGE, STAR);
        add(grid, -3, -2, LEFT, t14, t15);
        var t16 = new Tile(ORANGE, CROSS);
        var t17 = new Tile(ORANGE, DIAMOND);
        add(grid, -2, -3, DOWN, t16, t17);
        var t18 = new Tile(YELLOW, DIAMOND);
        var t19 = new Tile(YELLOW, ROUND);
        add(grid, -1, -2, DOWN, t18, t19);
        var t20 = new Tile(RED, STAR);
        add(grid, -3, 0, t20);
        var t21 = new Tile(BLUE, CROSS);
        var t22 = new Tile(RED, CROSS);
        var t23 = new Tile(ORANGE, CROSS);
        add(grid, 2, 1, LEFT, t21, t22, t23);
        var t24 = new Tile(YELLOW, SQUARE);
        var t25 = new Tile(BLUE, SQUARE);

        int point = add2(grid, 1, 4, DOWN, t24, t25);

        int expected = 9;
        assertEquals(expected, point);
    }

    @Test
    void rules_2_point() {
        var t1 = new Tile(GREEN, SQUARE);
        var t2 = new Tile(BLUE, SQUARE);
        grid.firstAdd(DOWN, t1, t2);
        var t4 = new Tile(BLUE, CROSS);
        int point;

        point = add2(grid, 1, 1, t4);

        int expected = 2;
        assertEquals(expected, point);
    }

 */
}