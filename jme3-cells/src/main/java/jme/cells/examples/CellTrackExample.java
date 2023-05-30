package jme.cells.examples;

import com.jme3.app.Application;
import com.jme3.app.SimpleApplication;
import com.jme3.app.state.BaseAppState;
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

        stateManager.attach(new CellTrackState(cell));

        logger.debug("initialized");
    }
}
