package jme3.cells.examples;

import com.jme3.app.SimpleApplication;
import com.jme3.material.Material;
import com.jme3.material.Materials;
import com.jme3.math.ColorRGBA;
import com.jme3.scene.Geometry;
import com.jme3.scene.shape.Box;
import com.jme3.system.AppSettings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DummyExample extends SimpleApplication {

    public static void main(String[] args) {
        AppSettings settings = new AppSettings(true);
        settings.setResolution(1600, 800);
        settings.setGammaCorrection(false);

        DummyExample app = new DummyExample();
        app.setSettings(settings);
        app.setShowSettings(false);
        app.start();
    }

    private static final Logger logger = LoggerFactory.getLogger(DummyExample.class);

    @Override
    public void simpleInitApp() {
        logger.debug("initializing");

        stateManager.attach(new InitCommonState());

        Material material = new Material(assetManager, Materials.LIGHTING);
        material.setColor("Diffuse", ColorRGBA.Gray);
        material.setColor("Ambient", ColorRGBA.Gray);
        material.setBoolean("UseMaterialColors", true);

        Geometry geometry = new Geometry("box", new Box(1, 1, 1));
        geometry.setMaterial(material);
        geometry.scale(10f);

        rootNode.attachChild(geometry);

        logger.debug("initialized");
    }
}