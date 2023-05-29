package jme.cells.examples;

import com.jme3.app.*;
import com.jme3.app.state.BaseAppState;
import com.jme3.app.state.ConstantVerifierState;
import com.jme3.audio.AudioListenerState;
import com.jme3.system.AppSettings;
import com.simsilica.lemur.Container;
import com.simsilica.lemur.Label;
import jme.cells.core.Cell;
import jme3.common.vector.FormattedVector3f;
import jme3utilities.SimpleControl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CellTrackExample extends SimpleApplication {

    private static final Logger logger = LoggerFactory.getLogger(CellTrackExample.class);

    public static void main(String[] args) {
        AppSettings settings = new AppSettings(true);
        settings.setResolution(1600, 800);
        settings.setGammaCorrection(false);

        CellTrackExample app = new CellTrackExample();
        app.setSettings(settings);
        app.setShowSettings(false);
        app.start();
    }

    private final Cell cell = new Cell(0, 0, 256f);

    @Override
    public void simpleInitApp() {
        logger.debug("initializing");

        logger.debug("cell = {}, center = {}", cell, cell.worldCenter());

        stateManager.attach(new InitCommonState());
        stateManager.attach(new InitLemurState());

        stateManager.attach(new BaseAppState() {
            @Override
            protected void initialize(Application app) {
                Container container = new Container();

                container.addChild(new Label("camera"));
                Label camera = container.addChild(new Label("camera"));

                container.addChild(new Label("inside cell"));
                Label inside = container.addChild(new Label("inside cell"));

                container.addControl(new SimpleControl() {
                    @Override
                    protected void controlUpdate(float updateInterval) {
                        camera.setText(new FormattedVector3f(cam.getLocation()).format());
                        inside.setText(Boolean.toString(cell.contains(cam.getLocation())));
                    }
                });

                guiNode.attachChild(container);
                container.setLocalTranslation(10, cam.getHeight() - 10, 0);
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
        });

        logger.debug("initialized");
    }
}
