package jme3.cells.examples.maze;

import com.jme3.math.Vector3f;
import jme3.cells.maze.MazeCell;
import jme3.cells.maze.MazeCellTranslation;

public interface Const {

    static final float CELL_EXTENT = 4;

    static final MazeCellTranslation translationFunc = new MazeCellTranslation(CELL_EXTENT);

}
