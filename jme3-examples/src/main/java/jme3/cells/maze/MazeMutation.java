package jme3.cells.maze;

import java.util.Map;

public interface MazeMutation {
    Map<MazeCell, CellType> cells();
}
