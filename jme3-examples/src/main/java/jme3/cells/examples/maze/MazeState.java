package jme3.cells.examples.maze;

import com.jme3.app.Application;
import com.jme3.app.state.BaseAppState;
import jme3.cells.maze.CellType;
import jme3.cells.maze.MazeCell;
import jme3.cells.maze.PrimMaze;
import jme3utilities.math.noise.Generator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

public class MazeState extends BaseAppState {

    private static final Logger logger = LoggerFactory.getLogger(MazeState.class);

    private final Map<MazeCell, CellType> cells = new HashMap<>();

    @Override
    protected void initialize(Application app) {
        int width = 33;
        int height = 33;

        Generator random = new Generator(42);

        PrimMaze maze = new PrimMaze(width, height, random);

        cells.putAll( maze.cells() );

        logger.debug("initialized");
    }

    @Override
    protected void cleanup(Application app) {
    }

    @Override
    protected void onEnable() {
    }

    @Override
    protected void onDisable() {
    }

    public Map<MazeCell, CellType> cells() {
        return cells;
    }
}
