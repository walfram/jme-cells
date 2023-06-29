package jme3.cells.maze;

import jme.cells.core.Cell;

import java.util.HashSet;
import java.util.Set;

public class Cells {
    private final int domainSize;
    private final float cellExtent;

    public Cells(int domainSize, float cellExtent) {
        this.domainSize = domainSize;
        this.cellExtent = cellExtent;
    }

    public Set<Cell> domain() {
        Set<Cell> domain = new HashSet<>( (domainSize * 2 + 1) * (domainSize * 2 + 1) );

        for (int x = -domainSize; x <= domainSize; x++) {
            for (int z = -domainSize; z <= domainSize; z++) {
                domain.add(new Cell(x, z, cellExtent));
            }
        }

        return domain;
    }
}
