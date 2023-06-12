package jme.cells.examples;

import com.jme3.app.Application;
import com.jme3.app.SimpleApplication;
import com.jme3.app.state.BaseAppState;
import com.jme3.math.Vector3f;
import com.simsilica.lemur.Container;
import com.simsilica.lemur.Label;
import jme.cells.core.Cell;
import jme3.common.vector.FormattedVector3f;
import jme3utilities.SimpleControl;

public class CellTrackGuiState extends BaseAppState {
    @Override
    protected void initialize(Application app) {
        Container container = new Container();

        container.addChild(new Label("camera"));
        Label camera = container.addChild(new Label("camera"));

        container.addChild(new Label("inside cell"));
        Label inside = container.addChild(new Label("inside cell"));

        container.addChild(new Label("ratio"));
        Label ratio = container.addChild(new Label("ratio"));

        container.addControl(new SimpleControl() {
            @Override
            protected void controlUpdate(float updateInterval) {
                camera.setText(new FormattedVector3f(app.getCamera().getLocation()).format());
                inside.setText(Boolean.toString(cell().contains(app.getCamera().getLocation())));

                Vector3f location = app.getCamera().getLocation().clone().setY(0);
                Vector3f center = cell().worldCenter().setY(0);

                Vector3f direction = location.subtract(center);

                ratio.setText( "%.04f".formatted( direction.length() / cell().extent() ) );
            }
        });

        ((SimpleApplication) app).getGuiNode().attachChild(container);
        container.setLocalTranslation(10, app.getCamera().getHeight() - 10, 0);
    }

    private Cell cell() {
        return getState(CellTrackCoreState.class).currentCell();
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
