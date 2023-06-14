package jme3.cells.examples.tracking;

import com.jme3.app.Application;
import com.jme3.app.SimpleApplication;
import com.jme3.app.state.BaseAppState;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.scene.Geometry;
import com.jme3.scene.Mesh;
import com.jme3.scene.Node;
import com.simsilica.lemur.geom.MBox;
import jme.cells.core.Cell;
import jme3.common.material.MtlLighting;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

final class CellTrackCoreState extends BaseAppState {

    private static final Logger logger = LoggerFactory.getLogger(CellTrackCoreState.class);

    private static final float CELL_EXTENT = 256f;

    private final Node scene = new Node("cells-scene");

    private Mesh mesh;

    private Material material;

    private Cell current;

    @Override
    protected void initialize(Application app) {
//        mesh = new WireBox(CELL_EXTENT, 1, CELL_EXTENT);
//        mesh = new Box(CELL_EXTENT, 1, CELL_EXTENT);
        mesh = new MBox(CELL_EXTENT, 1, CELL_EXTENT, 4, 1, 4);
        material = new MtlLighting(app.getAssetManager(), ColorRGBA.Orange);
        material.getAdditionalRenderState().setWireframe(true);

        current = new Cell(app.getCamera().getLocation(), CELL_EXTENT);

        ((SimpleApplication) app).getRootNode().attachChild(scene);

        updateCells();
    }

    @Override
    public void update(float tpf) {
        Cell c = new Cell(getApplication().getCamera().getLocation(), CELL_EXTENT);

        if (!c.equals(current)) {
            logger.debug("cell changed from {} to {}", current, c);
            current = c;
            updateCells();
        }
    }

    private void updateCells() {
        scene.detachAllChildren();

        Geometry geometry = new Geometry(current.name(), mesh);
        geometry.setMaterial(material);

        geometry.setLocalTranslation(current.worldCenter());

        scene.attachChild(geometry);
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

    public Cell currentCell() {
        return current;
    }
}
