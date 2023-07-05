package jme3.cells.assets;

import com.jme3.app.SimpleApplication;
import com.jme3.system.AppSettings;
import jme3.cells.examples.InitCommonState;
import jme3.cells.examples.InitDebugGridState;
import jme3.cells.examples.InitLemurState;

public class AssetViewExample extends SimpleApplication {

    public static void main(String[] args) {
        AppSettings settings = new AppSettings(true);
        settings.setResolution(1600, 800);
        settings.setGammaCorrection(false);

        AssetViewExample app = new AssetViewExample();
        app.setSettings(settings);
        app.setShowSettings(false);

        app.start();
    }

    @Override
    public void simpleInitApp() {
        stateManager.attach(new InitCommonState());

        stateManager.attach(new InitDebugGridState(10, 10, 20));

        stateManager.attach(new InitLemurState());

        stateManager.attach(new AssetViewState());
//        stateManager.attach(new AssetSizeState());
    }
}
