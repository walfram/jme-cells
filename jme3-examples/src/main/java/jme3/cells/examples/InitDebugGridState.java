package jme3.cells.examples;

import com.jme3.app.Application;
import com.jme3.app.SimpleApplication;
import com.jme3.app.state.BaseAppState;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.scene.Geometry;
import com.jme3.scene.debug.Grid;
import jme3.common.material.MtlUnshaded;

public class InitDebugGridState extends BaseAppState {

    private final int xlines;
    private final int ylines;
    private final float lineDist;

    public InitDebugGridState() {
        this(8, 8, 25);
    }

    public InitDebugGridState(int xlines, int ylines, float lineDist) {
        this.xlines = xlines;
        this.ylines = ylines;
        this.lineDist = lineDist;
    }


    @Override
    protected void initialize(Application app) {
        Geometry debugGrid = new Geometry("debug-grid", new Grid(xlines, ylines, lineDist));

        Material debugGridMat = new MtlUnshaded(app.getAssetManager(), ColorRGBA.Blue, 2);

        debugGrid.setMaterial(debugGridMat);
        debugGrid.center();

        ((SimpleApplication) app).getRootNode().attachChild(debugGrid);
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
