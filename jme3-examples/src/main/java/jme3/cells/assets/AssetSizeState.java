package jme3.cells.assets;

import com.jme3.app.Application;
import com.jme3.app.SimpleApplication;
import com.jme3.app.state.BaseAppState;
import com.jme3.bounding.BoundingBox;
import com.jme3.bounding.BoundingVolume;
import com.jme3.math.ColorRGBA;
import com.jme3.math.FastMath;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.simsilica.lemur.geom.MBox;
import jme3.common.material.MtlLighting;
import jme3.common.material.MtlUnshaded;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AssetSizeState extends BaseAppState {

    private static final Logger logger = LoggerFactory.getLogger(AssetSizeState.class);

    private static final float CELL_EXTENT = 10f;

    private final Node scene = new Node("scene");

    @Override
    protected void initialize(Application app) {
        app.getCamera().setLocation(new Vector3f(18.702965f, 44.192417f, 35.825813f));
        app.getCamera().setRotation(new Quaternion(-0.080958694f, 0.9009645f, -0.38081762f, -0.19153765f));

        Geometry geometry = new Geometry("cell", new MBox(CELL_EXTENT, 0, CELL_EXTENT, 4, 0, 4));
        geometry.setMaterial(new MtlUnshaded(app.getAssetManager(), ColorRGBA.Orange));
        geometry.getMaterial().getAdditionalRenderState().setWireframe(true);
        scene.attachChild(geometry);

        Spatial spatial = app.getAssetManager().loadModel("nature-kit/obj/cliff_large_rock.obj");

        BoundingBox bound = (BoundingBox) spatial.getWorldBound().clone();
        logger.debug("cliff rock bound = {}", bound);

        float scale = CELL_EXTENT / bound.getXExtent();
        logger.debug("scale = {}", scale);

        spatial.scale(scale, scale, 1);
        scene.attachChild(spatial);

        BoundingBox scaledBound = (BoundingBox) spatial.getWorldBound().clone();
        logger.debug("scaled bound = {}", scaledBound);

        Vector3f offset = scaledBound.getCenter().negate().setY(0);
        logger.debug("offset = {}", offset);
//        spatial.move(offset);

        for (int i = 1; i < 4; i++) {
            Spatial clone = spatial.clone();
            clone.depthFirstTraversal(s -> {
                if (s instanceof Geometry g)
                    g.getMaterial().getAdditionalRenderState().setWireframe(true);
            });

            clone.rotate(0, FastMath.HALF_PI * i, 0);
            scene.attachChild(clone);
        }
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
