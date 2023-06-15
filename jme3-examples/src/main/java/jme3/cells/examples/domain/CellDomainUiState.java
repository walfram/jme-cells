package jme3.cells.examples.domain;

import com.jme3.app.Application;
import com.jme3.app.SimpleApplication;
import com.jme3.app.state.BaseAppState;
import com.simsilica.lemur.Button;
import com.simsilica.lemur.Container;
import com.simsilica.lemur.Label;
import com.simsilica.lemur.style.ElementId;

import java.util.List;

public class CellDomainUiState extends BaseAppState {

    private final List<Class<? extends BaseAppState>> states;

    public CellDomainUiState(List<Class<? extends BaseAppState>> states) {
        this.states = states;
    }

    @Override
    protected void initialize(Application app) {
        Container container = new Container();
        container.addChild(new Label("cell domain examples", new ElementId("title")));

        container.addChild(new Button("simple domain example"))
                .addClickCommands(btn -> enableState(CellDomainSimpleState.class));

        container.addChild(new Button("primm maze example"))
                .addClickCommands(btn -> enableState(CellDomainPrimmState.class));

        ((SimpleApplication) app).getGuiNode().attachChild(container);
        container.setLocalTranslation(10, app.getCamera().getHeight() - 10, 0);
    }

    private void enableState(Class<? extends BaseAppState> stateClazz) {
        states
                .stream()
                .filter(clz -> !clz.equals(stateClazz))
                .forEach(clz -> {
                    getStateManager().getState(clz).setEnabled(false);
                });

        getState(stateClazz).setEnabled(true);
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
