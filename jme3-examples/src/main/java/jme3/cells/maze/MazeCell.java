package jme3.cells.maze;

import java.util.List;

public class MazeCell {

    public final int x;
    public final int z;

    public MazeCell(int x, int z) {
        this.x = x;
        this.z = z;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MazeCell mazeCell = (MazeCell) o;

        if (x != mazeCell.x) return false;
        return z == mazeCell.z;
    }

    @Override
    public int hashCode() {
        int result = x;
        result = 31 * result + z;
        return result;
    }

    @Override
    public String toString() {
        return "MazeCell{%s, %s}".formatted(x, z);
    }

    public List<MazeCell> neighboursOrtho(int extent) {
        return List.of(
                new MazeCell(x + extent, z),
                new MazeCell(x - extent, z),
                new MazeCell(x, z + extent),
                new MazeCell(x, z - extent)
        );
    }

    public MazeCell neighbourOrtho(Direction direction, int extent) {
        return switch (direction) {
            case NORTH -> new MazeCell(x, z - extent);
            case SOUTH -> new MazeCell(x, z + extent);
            case EAST -> new MazeCell(x - extent, z);
            case WEST -> new MazeCell(x + extent, z);
        };
    }

}