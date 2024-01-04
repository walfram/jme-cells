package jme3.cells.examples.maze;

import com.jme3.app.Application;
import com.jme3.app.state.AppState;
import com.jme3.app.state.BaseAppState;
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
import jme3.common.material.MtlLighting;

import java.util.List;
import java.util.Map;

public class MiniDungeonWallsState extends BaseAppState {

    private final Node scene = new Node("mini-dungeon-walls-scene");

    public MiniDungeonWallsState(Node rootNode) {
        rootNode.attachChild(scene);
    }

    @Override
    protected void initialize(Application app) {
        Map<MazeCell, CellType> maze = getState(MazeState.class).cells();

        List<MazeCell> walls = maze
                .entrySet()
                .stream()
                .filter(e -> e.getValue() == CellType.WALL)
                .map(e -> e.getKey())
                .toList();

        float scale = Const.CELL_EXTENT / 0.5f;

        for (MazeCell wall : walls) {
            List<MazeCell> emptyNbrs = wall
                    .neighboursOrtho(1)
                    .stream()
                    .filter(c -> maze.get(c) == CellType.EMPTY)
                    .toList();

            if (emptyNbrs.isEmpty())
                continue;

            Spatial spatial;
            if (emptyNbrs.size() == 1) {
                spatial = app.getAssetManager().loadModel("mini-dungeon/obj/wall-half.obj");
                spatial.setLocalTranslation(Const.translationFunc.apply(wall));
                spatial.lookAt( Const.translationFunc.apply(emptyNbrs.get(0)), Vector3f.UNIT_Y );
            } else {
                spatial = app.getAssetManager().loadModel("mini-dungeon/obj/wall.obj");
                spatial.setLocalTranslation(Const.translationFunc.apply(wall));
            }
            spatial.scale(scale);
            scene.attachChild(spatial);
        }

        app.getCamera().setLocation(new Vector3f(-33.84205f, 192.39107f, 124.108604f));
        app.getCamera().setRotation(new Quaternion(0.34281301f, 0.6170813f, -0.34064195f, 0.6210096f));
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
