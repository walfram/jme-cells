package jme3.cells.examples.maze;

import com.jme3.app.Application;
import com.jme3.app.state.AppState;
import com.jme3.app.state.BaseAppState;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import jme3.cells.maze.CellType;
import jme3.cells.maze.MazeCell;
import jme3utilities.math.noise.Generator;

import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class MiniDungeonFloorState extends BaseAppState {

    private final Node scene = new Node("mini-dungeon-floor-scene");

    public MiniDungeonFloorState(Node rootNode) {
        rootNode.attachChild(scene);
    }

    @Override
    protected void initialize(Application app) {
        Map<MazeCell, CellType> maze = getState(MazeState.class).cells();

        Set<MazeCell> empties = maze
                .entrySet()
                .stream()
                .filter(e -> e.getValue() == CellType.EMPTY)
                .map(e -> e.getKey())
                .collect(Collectors.toSet());

        Generator random = new Generator(42);

        float scale = Const.CELL_EXTENT / 0.5f;

        for (MazeCell empty : empties) {
            Spatial spatial = app.getAssetManager().loadModel("mini-dungeon/obj/floor.obj");

            if (random.nextFloat() >= 0.7f)
                spatial = app.getAssetManager().loadModel("mini-dungeon/obj/floor-detail.obj");

            spatial.scale(scale);

            spatial.setLocalTranslation( Const.translationFunc.apply(empty) );

            scene.attachChild(spatial);
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
