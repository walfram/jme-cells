package jme.cells.tmp;

import com.jme3.collision.Collidable;
import com.jme3.collision.CollisionResult;
import com.jme3.collision.CollisionResults;
import com.jme3.math.Ray;
import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import com.jme3.renderer.Camera;

import java.util.Optional;

public class CollisionWith {

    private final Camera camera;
    private final Vector2f click2d;
    private final Collidable collidable;

    public CollisionWith(Camera camera, Vector2f click2d, Collidable collidable) {
        this.camera = camera;
        this.click2d = click2d;
        this.collidable = collidable;
    }

    public Optional<CollisionResult> collision() {
        Vector3f click3d = camera.getWorldCoordinates(click2d, 0f).clone();
        Vector3f dir = camera.getWorldCoordinates(click2d, 1f).subtractLocal(click3d).normalizeLocal();
        Ray ray = new Ray(click3d, dir);

        CollisionResults results = new CollisionResults();
        ray.collideWith(collidable, results);

        return Optional.ofNullable(results.getClosestCollision());
    }
}
