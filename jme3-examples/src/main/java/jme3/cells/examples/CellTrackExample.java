package jme3.cells.examples;

import com.jme3.app.SimpleApplication;
import com.jme3.system.AppSettings;
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

    @Override
    public void simpleInitApp() {
        logger.debug("initializing");

        stateManager.attach(new InitCommonState());
        stateManager.attach(new InitLemurState());

        stateManager.attach(new CellTrackCoreState());
        stateManager.attach(new CellTrackGuiState());

        logger.debug("initialized");
    }
}
