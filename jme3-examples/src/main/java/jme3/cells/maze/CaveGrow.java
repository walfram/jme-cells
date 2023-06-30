package jme3.cells.maze;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class CaveGrow implements MazeMutation {
    private final MazeMutation source;

    private final int passes;

    public CaveGrow(MazeMutation source) {
        this(source, 3);
    }

    public CaveGrow(MazeMutation source, int passes) {
        this.source = source;
        this.passes = passes;
    }

    @Override
    public Map<MazeCell, CellType> cells() {
        Map<MazeCell, CellType> cells = source.cells();

        for (int i = 0; i < passes; i++) {
            Set<MazeCell> scheduled = new HashSet<>(cells.size());

            Set<MazeCell> walls = cells
                    .entrySet()
                    .stream()
                    .filter(e -> e.getValue() == CellType.WALL)
                    .map(e -> e.getKey())
                    .collect(Collectors.toSet());

            for (MazeCell cell : walls) {
                long count = cell
                        .neighbours(1)
                        .stream()
                        .filter(c -> cells.get(c) == CellType.EMPTY)
                        .count();

                if (count >= 4)
                    scheduled.add(cell);
            }

            scheduled.forEach(c -> cells.put(c, CellType.EMPTY));
        }

        return cells;
    }
}
