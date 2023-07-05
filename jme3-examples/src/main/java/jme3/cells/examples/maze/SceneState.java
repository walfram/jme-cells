package jme3.cells.examples.maze;

import com.jme3.app.Application;
import com.jme3.app.SimpleApplication;
import com.jme3.app.state.BaseAppState;
import com.jme3.bounding.BoundingBox;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Mesh;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.simsilica.lemur.geom.MBox;
import jme3.cells.maze.CellType;
import jme3.cells.maze.MazeCell;
import jme3.cells.maze.MazeCellTranslation;
import jme3.common.material.MtlLighting;
import jme3utilities.math.MyVector3f;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

public class SceneState extends BaseAppState {

    private static final Logger logger = LoggerFactory.getLogger(SceneState.class);

    private final Node scene = new Node("maze-node");

    private final Map<CellType, Mesh> meshes = new HashMap<>();

    private final Map<CellType, Material> materials = new HashMap<>();

    private final float cellExtent = 4f;

    private final Function<MazeCell, Vector3f> mazeCellTranslation = new MazeCellTranslation(cellExtent);

    @Override
    protected void initialize(Application app) {
        meshes.put(CellType.EMPTY, new MBox(cellExtent, 0, cellExtent, 2, 0, 2));
        meshes.put(CellType.WALL, new MBox(cellExtent, 0, cellExtent, 2, 0, 2));

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

//        scene.center();

        app.getCamera().setLocation(new Vector3f(-81.89475f, 144.8642f, 125.05195f));
        app.getCamera().setRotation(new Quaternion(0.24751574f, 0.6671981f, -0.2517807f, 0.655888f));

        // cliff_cornerInnerLarge_stone
        Spatial corner = app.getAssetManager().loadModel("nature-kit/obj/cliff_cornerInnerLarge_stone.obj");

        // cliff_large_rock
        Spatial cliff = app.getAssetManager().loadModel("nature-kit/obj/cliff_large_rock.obj");
        BoundingBox cliffBound = (BoundingBox) cliff.getWorldBound().clone();
        logger.debug("cliff bound = {}", cliffBound);

        cliff.scale( cellExtent / Math.max(cliffBound.getXExtent(), cliffBound.getZExtent()) );

        List<MazeCell> walls = mazeCells
                .entrySet()
                .stream()
                .filter(e -> e.getValue() == CellType.WALL)
                .map(e -> e.getKey())
                .toList();

        for (MazeCell wall: walls) {
            Set<MazeCell> emptyNeighbours = wall
                    .neighboursOrtho(1)
                    .stream()
                    .filter(c -> mazeCells.get(c) == CellType.EMPTY)
                    .collect(Collectors.toSet());

            // surrounded by all walls
            if (emptyNeighbours.isEmpty())
                continue;

            if (emptyNeighbours.size() == 1) {
                Spatial spatial = cliff.clone();
                Vector3f translation = mazeCellTranslation.apply(wall);
                spatial.setLocalTranslation(translation);

                MazeCell nbr = emptyNeighbours.stream().findFirst().orElseThrow();

                Vector3f lookAt = mazeCellTranslation.apply(nbr);
                spatial.lookAt(lookAt, Vector3f.UNIT_Y);

                scene.attachChild(spatial);
            }

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
