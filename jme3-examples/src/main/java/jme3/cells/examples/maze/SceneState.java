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
import jme3.cells.maze.Direction;
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

            geometry.setLocalTranslation(mazeCellTranslation.apply(e.getKey()));

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

        float scale = cellExtent / Math.max(cliffBound.getXExtent(), cliffBound.getZExtent());
        cliff.scale(scale);
        corner.scale(scale);

        List<MazeCell> walls = mazeCells
                .entrySet()
                .stream()
                .filter(e -> e.getValue() == CellType.WALL)
                .map(e -> e.getKey())
                .toList();

        Mesh wallMesh = new MBox(cellExtent, cellExtent, cellExtent, 2, 2, 2);
        Material wallMaterial = new MtlLighting(app.getAssetManager(), ColorRGBA.Brown);
        wallMaterial.getAdditionalRenderState().setWireframe(true);

        Map<Direction, Spatial> wallSpatials = wallSpatials();

        for (MazeCell wall: walls) {
            List<MazeCell> empties = wall
                    .neighbours(1)
                    .stream()
                    .filter(c -> mazeCells.get(c) == CellType.EMPTY)
                    .toList();

            if (empties.isEmpty())
                continue;

            for (Direction d : Direction.values()) {
                if (!empties.contains( wall.neighbourOrtho(d, 1) ))
                    continue;

                Spatial s = wallSpatials.get(d).clone();
                Vector3f translation = mazeCellTranslation.apply(wall);
                s.setLocalTranslation(translation);
                scene.attachChild(s);
            }

        }
    }

    private Map<Direction, Spatial> wallSpatials() {

        Material material = new MtlLighting(getApplication().getAssetManager(), ColorRGBA.Brown);

        Mesh zMesh = new MBox(cellExtent, cellExtent, cellExtent * 0.2f, 2, 2, 0);
        Mesh xMesh = new MBox(cellExtent * 0.2f, cellExtent, cellExtent, 0, 2, 2);

        Map<Direction, Spatial> map = new HashMap<>();

        Geometry zNegWall = new Geometry("z-neg-wall", zMesh);
        zNegWall.setMaterial(material);
        Node zNegNode = new Node("z-neg-node");
        zNegNode.attachChild(zNegWall);
        zNegWall.move(0, cellExtent, -cellExtent * 0.8f);
        map.put(Direction.Z_NEG, zNegNode);

        Geometry zPosWall = new Geometry("z-pos-wall", zMesh);
        zPosWall.setMaterial(material);
        Node zPosNode = new Node("z-pos-node");
        zPosNode.attachChild(zPosWall);
        zPosWall.move(0, cellExtent, cellExtent * 0.8f);
        map.put(Direction.Z_POS, zPosNode);

        Geometry xNegWall = new Geometry("x-neg-wall", xMesh);
        xNegWall.setMaterial(material);
        Node xNegNode = new Node("x-neg-node");
        xNegNode.attachChild(xNegWall);
        xNegWall.move(-cellExtent * 0.8f, cellExtent, 0);
        map.put(Direction.X_NEG, xNegNode);

        Geometry xPosWall = new Geometry("x-pos-wall", xMesh);
        xPosWall.setMaterial(material);
        Node xPosNode = new Node("x-pos-node");
        xPosNode.attachChild(xPosWall);
        xPosWall.move(cellExtent * 0.8f, cellExtent, 0);
        map.put(Direction.X_POS, xPosNode);

        return map;
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
