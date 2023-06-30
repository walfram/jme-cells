package jme3.cells.maze;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class DeadEndRemove implements MazeMutation {

    private final PrimMaze maze;
    private final int passes;

    public DeadEndRemove(PrimMaze maze) {
        this(maze, 4);
    }

    public DeadEndRemove(PrimMaze maze, int passes) {
        this.maze = maze;
        this.passes = passes;
    }

    @Override
    public Map<MazeCell, CellType> cells() {
        Map<MazeCell, CellType> cells = maze.cells();

        for (int i = 0; i < passes; i++) {
            Set<MazeCell> empties = cells
                    .entrySet()
                    .stream()
                    .filter(e -> e.getValue() == CellType.EMPTY)
                    .map(e -> e.getKey())
                    .collect(Collectors.toSet());

            Set<MazeCell> scheduled = new HashSet<>(empties.size());

            // for each empty count count neighbouring empty cells
            for (MazeCell cell : empties) {
                long count = cell
                        .neighboursOrtho(1)
                        .stream()
                        .filter(c -> cells.get(c) == CellType.EMPTY)
                        .count();

                // if count <= 1 schedule to remove
                if (count <= 1) {
                    scheduled.add(cell);
                }
            }

            // make all scheduled walls
            scheduled.forEach(c -> cells.put(c, CellType.WALL));
        }

        return cells;
    }
}
