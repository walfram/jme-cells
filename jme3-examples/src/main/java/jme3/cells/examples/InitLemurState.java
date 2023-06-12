package jme3.cells.examples;

import com.jme3.app.Application;
import com.jme3.app.state.BaseAppState;
import com.simsilica.lemur.GuiGlobals;
import com.simsilica.lemur.style.BaseStyles;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class InitLemurState extends BaseAppState {

    private static final Logger logger = LoggerFactory.getLogger(InitLemurState.class);

    @Override
    protected void initialize(Application app) {
        logger.debug("initializing");

        GuiGlobals.initialize(app);

        BaseStyles.loadGlassStyle();
        GuiGlobals.getInstance().getStyles().setDefaultStyle(BaseStyles.GLASS);

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
