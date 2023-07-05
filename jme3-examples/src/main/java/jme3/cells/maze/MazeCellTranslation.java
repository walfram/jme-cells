package jme3.cells.maze;

import com.jme3.math.Vector3f;

import java.util.function.Function;

public class MazeCellTranslation implements Function<MazeCell, Vector3f> {
    private final float cellExtent;

    public MazeCellTranslation(float cellExtent) {
        this.cellExtent = cellExtent;
    }

    @Override
    public Vector3f apply(MazeCell cell) {
        return new Vector3f(cell.x, 0, cell.z).multLocal(2 * cellExtent);
    }
}
