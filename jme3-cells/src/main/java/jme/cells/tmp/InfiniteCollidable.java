package jme.cells.tmp;

import com.jme3.bounding.BoundingBox;
import com.jme3.collision.Collidable;
import com.jme3.collision.CollisionResults;
import com.jme3.collision.UnsupportedCollisionException;
import com.jme3.math.Vector3f;

public class InfiniteCollidable extends BoundingBox {

    public InfiniteCollidable() {
        super(new Vector3f(), Float.POSITIVE_INFINITY, 0, Float.POSITIVE_INFINITY);
    }


}
