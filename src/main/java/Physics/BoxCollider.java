package Physics;

import Game.GameObject;
import org.joml.Vector3f;

import java.util.ArrayList;
import java.util.List;

public class BoxCollider {
    public static List<GameObject> colliders = new ArrayList<>();

    public static boolean isColliding(GameObject a, GameObject b) {
        Vector3f pa = a.position;
        Vector3f pb = b.position;

        float aHalf = a.getHalfSize();
        float bHalf = b.getHalfSize();

        return Math.abs(pa.x - pb.x) < (aHalf + bHalf) &&
                Math.abs(pa.y - pb.y) < (aHalf + bHalf);
    }

    public static Vector3f getResolution(GameObject a, GameObject b) {

        Vector3f pa = a.position;
        Vector3f pb = b.position;

        float dx = pb.x - pa.x;
        float dy = pb.y - pa.y;

        float aHalf = a.getHalfSize();
        float bHalf = b.getHalfSize();

        float px = (aHalf + bHalf) - Math.abs(dx);
        float py = (aHalf + bHalf) - Math.abs(dy);

        if (px <= 0 || py <= 0)
            return new Vector3f(0, 0, 0);

        float epsilon = 0.002f;

        if (px < py) {
            return new Vector3f(dx > 0 ? -px - epsilon : px + epsilon, 0, 0);
        } else {
            return new Vector3f(0, dy > 0 ? -py - epsilon : py + epsilon, 0);
        }
    }

    public static void removeCollider(GameObject gameObject){
        colliders.remove(gameObject);
    }

    public static void addCollider(GameObject gameObject){
        colliders.add(gameObject);
    }
}
