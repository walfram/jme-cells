package jme3.cells.examples.maze;

import com.jme3.app.SimpleApplication;
import com.jme3.system.AppSettings;
import jme3.cells.examples.InitCommonState;
import jme3.cells.examples.InitLemurState;

public class PrimMazeExample extends SimpleApplication {

    public static void main(String[] args) {
        AppSettings settings = new AppSettings(true);
        settings.setResolution(1600, 800);
        settings.setGammaCorrection(false);

        PrimMazeExample app = new PrimMazeExample();
        app.setSettings(settings);
        app.setShowSettings(false);

        app.start();
    }

    @Override
    public void simpleInitApp() {
        stateManager.attach(new InitCommonState());
        stateManager.attach(new InitLemurState());

        stateManager.attach(new MazeState());

        stateManager.attach(new MazeFloorState(rootNode));
        stateManager.attach(new SimpleWallsState(rootNode));
    }
}
