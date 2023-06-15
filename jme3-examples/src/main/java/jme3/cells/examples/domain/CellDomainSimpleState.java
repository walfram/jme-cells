package jme3.cells.examples.domain;

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
import jme3.cells.examples.ToggledBoolean;
import jme3.common.material.MtlLighting;

import java.util.List;

public class CellDomainSimpleState extends BaseAppState {

    private final Node scene = new Node("cell-domain-scene");

    public CellDomainSimpleState() {
        setEnabled(false);
    }

    @Override
    protected void initialize(Application app) {
        Cell origin = new Cell(0, 0, 100f);

        List<Cell> cells = origin.neighboursInExtent(4);

        float extent = 0.99f * origin.extent();
        Mesh mesh = new MBox(extent, 1, extent, 4, 1, 4);

        Material materialOrange = new MtlLighting(app.getAssetManager(), ColorRGBA.Orange);
        Material materialYellow = new MtlLighting(app.getAssetManager(), ColorRGBA.Yellow);

        ToggledBoolean tb = new ToggledBoolean(false);

        for (Cell cell : cells) {
            Geometry geometry = new Geometry(cell.name(), mesh);
            geometry.setMaterial(tb.toggle() ? materialOrange : materialYellow);
            geometry.setLocalTranslation(cell.worldCenter());
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
