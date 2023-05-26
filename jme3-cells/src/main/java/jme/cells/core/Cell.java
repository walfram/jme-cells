package jme.cells.core;

import com.jme3.math.Vector3f;

import java.util.ArrayList;
import java.util.List;

public class Cell {

    private final int x;
    private final int z;
    private final float extent;

    public Cell(int x, int z, float extent) {
        this.x = x;
        this.z = z;
        this.extent = extent;
    }

    public Cell(Vector3f location, float cellExtent) {
        this(
                Math.round(location.x / (2 * cellExtent)),
                Math.round(location.z / (2 * cellExtent)),
                cellExtent
        );
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Cell cell = (Cell) o;

        if (x != cell.x) return false;
        if (z != cell.z) return false;
        return Float.compare(cell.extent, extent) == 0;
    }

    @Override
    public int hashCode() {
        int result = x;
        result = 31 * result + z;
        result = 31 * result + (extent != +0.0f ? Float.floatToIntBits(extent) : 0);
        return result;
    }

    @Override
    public String toString() {
        return name();
    }

    public String name() {
        return "Cell{%s,%s,%.2f}".formatted(x, z, extent);
    }

    public Vector3f worldCenter() {
        return new Vector3f(x, 0, z).multLocal(2f * extent);
    }

    public List<Cell> neighbours() {
        List<Cell> nbrs = new ArrayList<>();

        for (int xx = x - 1; xx <= x + 1; xx++) {
            for (int zz = z - 1; zz <= z + 1; zz++) {
                nbrs.add(new Cell(xx, zz, extent));
            }
        }

//        nbrs.remove(this);

        return nbrs;
    }
}
