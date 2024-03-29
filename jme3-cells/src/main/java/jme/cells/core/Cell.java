package jme.cells.core;

import com.jme3.bounding.BoundingBox;
import com.jme3.math.Vector3f;

import java.util.ArrayList;
import java.util.List;

public class Cell {

    public final int x;
    public final int z;
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

    public boolean contains(Vector3f location) {
//        return new BoundingBox(
//                new Vector3f(x, 0, z).multLocal(2f * extent).subtractLocal(2f * extent, 0, 2f * extent),
//                new Vector3f(x, 0, z).multLocal(2f * extent).addLocal(2f * extent, 0, 2f * extent)
//        ).contains(location);

        Vector3f c = worldCenter();

        float xmin = c.x - extent;
        float xmax = c.x + extent;

        float zmin = c.z - extent;
        float zmax = c.z + extent;

        return (location.x >= xmin && location.x <= xmax) && (location.z >= zmin && location.z <= zmax);
    }

    public float extent() {
        return extent;
    }

    public List<Cell> neighboursWithoutSelf() {
        List<Cell> nbrs = neighbours();
        nbrs.remove(this);
        return nbrs;
    }

    public List<Cell> neighboursInExtent(int ext) {
        List<Cell> cells = new ArrayList<>( (2 * ext + 1) * (2 * ext + 1) );

        for (int cx = x - ext; cx <= x + ext; cx++) {
            for (int cz = z - ext; cz <= z + ext; cz++) {
                cells.add(new Cell(cx, cz, extent));
            }
        }

        return cells;
    }

    public List<Cell> orthogonalNeighbours(int ext) {
        return List.of(
                new Cell(x + ext, z, extent),
                new Cell(x - ext, z, extent),
                new Cell(x, z + ext, extent),
                new Cell(x, z - ext, extent)
        );
    }
}
