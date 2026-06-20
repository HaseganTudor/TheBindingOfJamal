package Game.Enemy;

import Game.GameObject;
import org.joml.Vector3f;

public class Enemy extends GameObject {

    public float health;
    float speed;
    float damage;
    GameObject target;
    String type = "null";
    public Vector3f rgb = new Vector3f(1, 1, 1);

    public Enemy() {
        health = 10;
        speed = 100;
        damage = 1;
    }
}
