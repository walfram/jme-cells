package jme3.cells.examples.maze;

import com.jme3.app.Application;
import com.jme3.app.state.BaseAppState;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Mesh;
import com.jme3.scene.Node;
import com.simsilica.lemur.geom.MBox;
import jme3.cells.maze.CellType;
import jme3.cells.maze.MazeCell;
import jme3.cells.maze.MazeCellTranslation;
import jme3.common.material.MtlLighting;

import java.util.Map;
import java.util.function.Function;

public class MazeFloorState extends BaseAppState {

    private final Node scene = new Node("maze-floor-scene");

    private final Function<MazeCell, Vector3f> mazeCellTranslation = new MazeCellTranslation(Const.CELL_EXTENT);

    public MazeFloorState(Node rootNode) {
        rootNode.attachChild(scene);
    }

    @Override
    protected void initialize(Application app) {
        Mesh empty = new MBox(Const.CELL_EXTENT, 0, Const.CELL_EXTENT, 2, 0, 2);
        Mesh wall = new MBox(Const.CELL_EXTENT, 0, Const.CELL_EXTENT, 2, 0, 2);

        Material emptyMtl = new MtlLighting(app.getAssetManager(), ColorRGBA.Gray);
        Material wallMtl = new MtlLighting(app.getAssetManager(), ColorRGBA.DarkGray);

        Map<MazeCell, CellType> mazeCells = getState(MazeState.class).cells();

        for (Map.Entry<MazeCell, CellType> e : mazeCells.entrySet()) {
            Geometry geometry = new Geometry(e.getKey().toString(), e.getValue() == CellType.EMPTY ? empty : wall);
            geometry.setMaterial(e.getValue() == CellType.EMPTY ? emptyMtl : wallMtl);

            geometry.setLocalTranslation(mazeCellTranslation.apply(e.getKey()));

            scene.attachChild(geometry);
        }
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
