package jme3.cells.examples.domain;

import com.jme3.app.SimpleApplication;
import com.jme3.system.AppSettings;
import jme3.cells.examples.InitCommonState;
import jme3.cells.examples.InitLemurState;

import java.util.List;

public class CellDomainExample extends SimpleApplication {

    public static void main(String[] args) {
        AppSettings settings = new AppSettings(true);
        settings.setResolution(1600, 800);
        settings.setGammaCorrection(false);

        CellDomainExample app = new CellDomainExample();
        app.setSettings(settings);
        app.setShowSettings(false);
        app.start();
    }

    @Override
    public void simpleInitApp() {
        stateManager.attach(new InitCommonState());
        stateManager.attach(new InitLemurState());

        stateManager.attach(new CellDomainSimpleState());
    }
}
