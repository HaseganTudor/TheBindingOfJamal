package Game.Enemy;

import Game.GameObject;
import org.joml.Vector3f;

public class Enemy extends GameObject {
    float health;
    float speed;
    float damage;
    GameObject target;
    public Vector3f rgb = new Vector3f(1,1,1);

    public Enemy(){
        health = 100;
        speed = 100;
        damage = 1;
    }

}
