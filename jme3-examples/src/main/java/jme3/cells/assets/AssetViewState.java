package jme3.cells.assets;

import com.jme3.app.Application;
import com.jme3.app.SimpleApplication;
import com.jme3.app.state.BaseAppState;
import com.jme3.input.event.MouseButtonEvent;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.simsilica.lemur.event.DefaultMouseListener;
import com.simsilica.lemur.event.MouseEventControl;
import jme3.cells.maze.MazeCell;
import jme3.cells.maze.MazeCellTranslation;
import org.reflections.Reflections;
import org.reflections.scanners.Scanners;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.function.Function;

public class AssetViewState extends BaseAppState {

    private static final Logger logger = LoggerFactory.getLogger(AssetViewState.class);

    private final Node scene = new Node("scene");

    private final Function<MazeCell, Vector3f> mazeCellTranslation = new MazeCellTranslation(10f);

    @Override
    protected void initialize(Application app) {
        Reflections reflections = new Reflections("mini-dungeon.obj", Scanners.Resources);
        Set<String> resources = reflections.getResources(".*\\.obj");

        logger.debug("found obj resources = {}", resources.size());

        List<String> cliffResources = resources
                .stream()
//                .filter(s -> s.contains("cliff_"))
                .sorted()
                .toList();

        logger.debug("cliff resources = {}", cliffResources.size());

        int extent = (int) Math.ceil( Math.sqrt( cliffResources.size() ) * 0.5f) - 1;
        logger.debug("extent = {}", extent);

        List<MazeCell> cells = new ArrayList<>( (extent * 2 + 1) * (extent * 2 + 1) );
        for (int x = -extent; x <= extent; x++) {
            for (int z = -extent; z <= extent; z++) {
                cells.add(new MazeCell(x, z));
            }
        }

        int idx = 0;
        for (String path : cliffResources) {
            Spatial spatial = app.getAssetManager().loadModel(path);
            spatial.scale(15f);

            Vector3f translation = mazeCellTranslation.apply(cells.get(idx));
            spatial.setLocalTranslation(translation);

            scene.attachChild(spatial);

            MouseEventControl.addListenersToSpatial(spatial, new DefaultMouseListener() {
                @Override
                protected void click(MouseButtonEvent event, Spatial target, Spatial capture) {
                    logger.debug("click on {}", target.getName());
                }
            });

            idx++;
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
