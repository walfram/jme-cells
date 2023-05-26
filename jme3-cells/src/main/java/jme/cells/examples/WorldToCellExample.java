package jme.cells.examples;

import com.jme3.app.SimpleApplication;
import com.jme3.collision.CollisionResult;
import com.jme3.input.MouseInput;
import com.jme3.input.event.MouseButtonEvent;
import com.jme3.material.Material;
import com.jme3.material.Materials;
import com.jme3.math.ColorRGBA;
import com.jme3.scene.Geometry;
import com.jme3.scene.Mesh;
import com.jme3.scene.Spatial;
import com.jme3.scene.shape.Box;
import com.jme3.system.AppSettings;
import com.simsilica.lemur.GuiGlobals;
import com.simsilica.lemur.event.DefaultMouseListener;
import com.simsilica.lemur.event.MouseEventControl;
import com.simsilica.lemur.input.Button;
import com.simsilica.lemur.input.FunctionId;
import com.simsilica.lemur.input.InputMapper;
import com.simsilica.lemur.input.InputState;
import jme.cells.core.Cell;
import jme3.common.collision.CollisionWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jme3.common.collision.InfiniteCollidable;

import java.util.List;
import java.util.Optional;

public class WorldToCellExample extends SimpleApplication {

    private static final Logger logger = LoggerFactory.getLogger(WorldToCellExample.class);

    private static final FunctionId FUNC_RIGHT_CLICK = new FunctionId("right-click");


    public static void main(String[] args) {
        AppSettings settings = new AppSettings(true);
        settings.setResolution(1600, 800);
        settings.setGammaCorrection(false);

        WorldToCellExample app = new WorldToCellExample();
        app.setSettings(settings);
        app.setShowSettings(false);
        app.start();
    }

    @Override
    public void simpleInitApp() {
        stateManager.attach(new CommonInitState());

        GuiGlobals.initialize(this);

        final float cellExtent = 100f;

        Cell origin = new Cell(0, 0, 100f);
        List<Cell> cells = origin.neighbours();

        final float k = 0.99f;

//        Mesh mesh = new WireBox(cellExtent * k, 1, cellExtent * k);
        Mesh mesh = new Box(cellExtent * k, 1, cellExtent * k);

        Material material = new Material(assetManager, Materials.UNSHADED);
        material.setColor("Color", ColorRGBA.Orange);
        material.getAdditionalRenderState().setWireframe(true);

        for (Cell cell : cells) {
            Geometry geometry = new Geometry(cell.name(), mesh);
            geometry.setMaterial(material);
            geometry.setLocalTranslation(cell.worldCenter());
            rootNode.attachChild(geometry);
        }

        InputMapper inputMapper = GuiGlobals.getInstance().getInputMapper();

        inputMapper.map(FUNC_RIGHT_CLICK, Button.MOUSE_BUTTON2);
        inputMapper.addStateListener((func, value, tpf) -> {
            if (value != InputState.Off)
                return;

            Optional<CollisionResult> result = new CollisionWith(
                    new InfiniteCollidable(),
                    getCamera(),
                    inputManager.getCursorPosition()
            ).collision();

            result.ifPresentOrElse(cr -> {
                Cell cell = new Cell(cr.getContactPoint(), cellExtent);
                logger.debug("click @ {}, cell = {}", cr.getContactPoint(), cell);
            }, () -> {
                logger.debug("no collision...");
            });
        }, FUNC_RIGHT_CLICK);

    }

}
