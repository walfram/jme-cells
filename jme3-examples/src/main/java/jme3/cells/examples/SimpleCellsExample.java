package jme3.cells.examples;

import com.jme3.app.SimpleApplication;
import com.jme3.material.Material;
import com.jme3.material.Materials;
import com.jme3.math.ColorRGBA;
import com.jme3.scene.Geometry;
import com.jme3.scene.Mesh;
import com.jme3.scene.debug.WireBox;
import com.jme3.system.AppSettings;
import jme.cells.core.Cell;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashSet;
import java.util.Set;

public class SimpleCellsExample extends SimpleApplication {

    public static void main(String[] args) {
        AppSettings settings = new AppSettings(true);
        settings.setResolution(1600, 800);
        settings.setGammaCorrection(false);

        SimpleCellsExample app = new SimpleCellsExample();
        app.setSettings(settings);
        app.setShowSettings(false);
        app.start();
    }

    private static final Logger logger = LoggerFactory.getLogger(SimpleCellsExample.class);

    @Override
    public void simpleInitApp() {
        stateManager.attach(new InitCommonState());

        // TODO should use domain width extent or smth like that
        final int domainWidth = 8;
        final int domainHeight = 8;
        final float cellExtent = 25f;

        logger.debug(
                "domain width = {}, domain height = {}, width x height = {}",
                domainWidth, domainHeight, domainWidth * domainHeight);

        Set<Cell> domain = new HashSet<>((domainWidth + 1) * (domainHeight + 1));
        for (int x = -domainWidth / 2; x <= domainWidth / 2; x++) {
            for (int z = -domainHeight / 2; z <= domainHeight / 2; z++) {
                domain.add(new Cell(x, z, cellExtent));
            }
        }

        logger.debug("domain size = {}", domain.size());
        final float k = 0.99f;

        Mesh mesh = new WireBox(cellExtent * k, 1, cellExtent * k);

        Material material = new Material(assetManager, Materials.UNSHADED);
        material.setColor("Color", ColorRGBA.Orange);

        for (Cell cell : domain) {
            Geometry geometry = new Geometry(cell.name(), mesh);
            geometry.setMaterial(material);
            geometry.setLocalTranslation(cell.worldCenter());
            rootNode.attachChild(geometry);
        }
    }
}
