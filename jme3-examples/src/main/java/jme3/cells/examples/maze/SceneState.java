package jme3.cells.examples.maze;

import com.jme3.app.Application;
import com.jme3.app.SimpleApplication;
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
import jme3.common.material.MtlLighting;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

public class SceneState extends BaseAppState {

    private static final Logger logger = LoggerFactory.getLogger(SceneState.class);

    private final Node scene = new Node("maze-node");

    private final Map<CellType, Mesh> meshes = new HashMap<>();

    private final Map<CellType, Material> materials = new HashMap<>();

    private final float extent = 10f;

    private final Function<MazeCell, Vector3f> mazeCellTranslation = (c) -> new Vector3f(c.x, 0, c.z).multLocal(2 * extent);

    @Override
    protected void initialize(Application app) {
        meshes.put(CellType.EMPTY, new MBox(extent, 0, extent, 2, 0, 2));
        meshes.put(CellType.WALL, new MBox(extent, 0, extent, 2, 0, 2));

        materials.put(CellType.EMPTY, new MtlLighting(app.getAssetManager(), ColorRGBA.Gray));
        materials.put(CellType.WALL, new MtlLighting(app.getAssetManager(), ColorRGBA.DarkGray));

//        materials.values().forEach(m -> m.getAdditionalRenderState().setWireframe(true));

        Map<MazeCell, CellType> mazeCells = getState(MazeState.class).cells();

        for (Map.Entry<MazeCell, CellType> e : mazeCells.entrySet()) {

            Geometry geometry = new Geometry(e.getKey().toString(), meshes.get(e.getValue()));
            geometry.setMaterial(materials.get(e.getValue()));

            geometry.setLocalTranslation( mazeCellTranslation.apply(e.getKey()) );

            scene.attachChild(geometry);

        }
    }

    @Override
    protected void cleanup(Application app) {

    }

    @Override
    protected void onEnable() {
        ((SimpleApplication) getApplication()).getRootNode().attachChild(scene);
    }

    @Override
    protected void onDisable() {
        ((SimpleApplication) getApplication()).getRootNode().detachChild(scene);
    }
}
