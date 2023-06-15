package jme3.cells.examples.domain;

import com.jme3.app.Application;
import com.jme3.app.SimpleApplication;
import com.jme3.app.state.BaseAppState;
import com.jme3.scene.Node;
import jme.cells.core.Cell;

import java.util.List;

public class CellDomainPrimmState extends BaseAppState {

    private final Node scene = new Node("primm-maze-scene");

    public CellDomainPrimmState() {
        setEnabled(false);
    }

    @Override
    protected void initialize(Application app) {
        List<Cell> domain = new Cell(0, 0, 10f).neighboursInExtent(4);
    }

    @Override
    protected void cleanup(Application app) {
    }

    @Override
    protected void onEnable() {
        ((SimpleApplication) getApplication()).getRootNode().attachChild(scene);
    }

    @Override
    protected void onDisable() {
        ((SimpleApplication) getApplication()).getRootNode().detachChild(scene);
    }
}
