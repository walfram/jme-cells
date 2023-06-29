package jme3.cells.maze;

import jme3utilities.math.noise.Generator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

// https://kairumagames.com/blog/cavetutorial
// via
// https://habr.com/ru/post/537630/
public class PrimMaze {

    private static final Logger logger = LoggerFactory.getLogger(PrimMaze.class);

    private final int width;
    private final int height;
    private final Generator random;

    public PrimMaze(int width, int height, Generator random) {
        this.width = width;
        this.height = height;
        this.random = random;
    }

    public Map<MazeCell, CellType> cells() {
        Map<MazeCell, CellType> maze = new HashMap<>(width * height);

        for (int x = 0; x < width; x++) {
            for (int z = 0; z < height; z++) {
                maze.put(new MazeCell(x, z), CellType.WALL);
            }
        }

        int startx = random.nextInt(width / 2) * 2 + 1;
        int startz = random.nextInt(height / 2) * 2 + 1;

        logger.debug("oddx = {}, oddz = {}", startx, startz);

        MazeCell start = new MazeCell(startx, startz);
        maze.put(start, CellType.EMPTY);

        List<MazeCell> nbrsOrtho = new ArrayList<>(start.neighboursOrtho(2));
        nbrsOrtho.removeIf(c -> !maze.containsKey(c));

        Set<MazeCell> check = new HashSet<>(width * height);
        check.addAll(nbrsOrtho);

        logger.debug("check = {}", check);

        while (!check.isEmpty()) {
            MazeCell cell = check
                    .stream()
                    .skip(random.nextInt(check.size()))
                    .findAny()
                    .orElseThrow();

            check.remove(cell);
            maze.put(cell, CellType.EMPTY);

            List<Direction> directions = new ArrayList<>(List.of(Direction.values()));

            while (!directions.isEmpty()) {
                Direction direction = random.pick(directions);

                if (maze.get(cell.neighbourOrtho(direction, 2)) == CellType.EMPTY) {
                    maze.put(cell.neighbourOrtho(direction, 1), CellType.EMPTY);
                    directions.clear();
                }

                directions.remove(direction);
            }

            for (Direction direction : Direction.values()) {
                if (maze.get(cell.neighbourOrtho(direction, 2)) == CellType.WALL)
                    check.add(cell.neighbourOrtho(direction, 2));
            }
        }

        return maze;
    }

}
