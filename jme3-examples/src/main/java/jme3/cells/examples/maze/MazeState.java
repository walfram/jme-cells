package jme3.cells.examples.maze;

import com.jme3.app.Application;
import com.jme3.app.state.BaseAppState;
import jme.cells.core.Cell;
import jme3utilities.math.noise.Generator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Comparator;
import java.util.List;
import java.util.stream.IntStream;

public class MazeState extends BaseAppState {

    private static final Logger logger = LoggerFactory.getLogger(MazeState.class);

    private static final float CELL_EXTENT = 10f;
    private final Generator random = new Generator();

    @Override
    protected void initialize(Application app) {
        List<Cell> domain = new Cell(0, 0, CELL_EXTENT).neighboursInExtent(4);

        int minx = domain.stream().min(Comparator.comparingInt(c -> c.x)).map(c -> c.x).orElseThrow();
        int maxx = domain.stream().max(Comparator.comparingInt(c -> c.x)).map(c -> c.x).orElseThrow();
        logger.debug("domain minx = {}, maxx = {}", minx, maxx);

        int minz = domain.stream().min(Comparator.comparingInt(c -> c.z)).map(c -> c.z).orElseThrow();
        int maxz = domain.stream().max(Comparator.comparingInt(c -> c.z)).map(c -> c.z).orElseThrow();
        logger.debug("domain minz = {}, maxz = {}", minz, maxz);

        int[] oddx = IntStream.rangeClosed(minx, maxx).filter(e -> e % 2 != 0).toArray();
        int startx = oddx[random.nextInt(oddx.length)];

        int[] oddz = IntStream.rangeClosed(minz, maxz).filter(e -> e % 2 != 0).toArray();
        int startz = oddz[random.nextInt(oddz.length)];

        logger.debug("picked start x = {}, z = {}", startx, startz);

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
}
