package jme3.cells.examples;

import com.jme3.app.Application;
import com.jme3.app.SimpleApplication;
import com.jme3.app.state.BaseAppState;
import com.jme3.asset.AssetManager;
import com.jme3.input.FlyByCamera;
import com.jme3.light.AmbientLight;
import com.jme3.light.DirectionalLight;
import com.jme3.material.Material;
import com.jme3.material.Materials;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import com.jme3.renderer.Camera;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.debug.Grid;
import jme3utilities.MyCamera;
import jme3utilities.debug.AxesVisualizer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class InitCommonState extends BaseAppState {

    private static final Logger logger = LoggerFactory.getLogger(InitCommonState.class);

    @Override
    protected void initialize(Application app) {
        logger.debug("initializing");

        SimpleApplication sa = (SimpleApplication) app;

        FlyByCamera flyCam = sa.getFlyByCamera();
        Node rootNode = sa.getRootNode();
        Camera cam = sa.getCamera();
        AssetManager assetManager = sa.getAssetManager();

        flyCam.setDragToRotate(true);
        flyCam.setMoveSpeed(100);
        flyCam.setZoomSpeed(0);

        MyCamera.setNearFar(cam, cam.getFrustumNear(), 32768f);

        rootNode.addControl(new AxesVisualizer(assetManager, 100f, 2f));
        rootNode.getControl(AxesVisualizer.class).setEnabled(true);

        cam.setLocation(new Vector3f(97.52752f, 64.86752f, 165.35893f));
        cam.setRotation(new Quaternion(-0.02934412f, 0.96092063f, -0.112174965f, -0.25137076f));

        rootNode.addLight(new AmbientLight(ColorRGBA.White));
        rootNode.addLight(new DirectionalLight(cam.getDirection(), ColorRGBA.White));

        Geometry debugGrid = new Geometry("debug-grid", new Grid(8, 8, 25));

        Material debugGridMat = new Material(assetManager, Materials.UNSHADED);
        debugGridMat.setColor("Color", ColorRGBA.Blue);

        debugGrid.setMaterial(debugGridMat);
        debugGrid.center();
        rootNode.attachChild(debugGrid);

        logger.debug("initialized");
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
