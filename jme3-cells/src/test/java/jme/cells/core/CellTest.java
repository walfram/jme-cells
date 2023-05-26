package jme.cells.core;

import com.jme3.math.Vector3f;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CellTest {

    @Test
    public void test_cell_from_vector3f() {
        Cell a = new Cell(new Vector3f(0, 0, 0), 100f);
        assertEquals(new Cell(0, 0, 100f), a);

        Cell b = new Cell(new Vector3f(100, 0, 0), 100f);
        assertEquals(new Cell(1, 0, 100f), b);

        Cell c = new Cell(new Vector3f(200, 0, 0), 100f);
        assertEquals(new Cell(1, 0, 100f), c);

        Cell d = new Cell(new Vector3f(201, 0, 0), 100f);
        assertEquals(new Cell(1, 0, 100f), d);

        Cell e = new Cell(new Vector3f(300, 0, 0), 100f);
        assertEquals(new Cell(2, 0, 100f), e);

        Cell f = new Cell(new Vector3f(299, 0, 0), 100f);
        assertEquals(new Cell(1, 0, 100f), f);
    }

    @Test
    public void test_equals() {
        Cell a = new Cell(5, 5, 100f);
        Cell b = new Cell(5, 5, 100f);

        assertEquals(a, b);
        assertEquals(b, a);
    }

}