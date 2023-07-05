package jme3.cells.maze;

import org.junit.jupiter.api.Test;

import java.util.List;

public class OrientationTest {

    @Test
    public void test_detect_orientation() {
        // given wall that has empties on the east
        MazeCell wall = new MazeCell(0, 0);

        List<MazeCell> nbrs = List.of(
            new MazeCell(-1, -1),
            new MazeCell(-1, 0),
            new MazeCell(-1, 1)
        );

        // should return smth that points to east
    }

}
