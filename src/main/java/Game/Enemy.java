package Game;

import org.joml.Random;
import org.joml.Vector3f;

import java.util.ArrayList;

public class Enemy extends GameObject {
    float health;
    float speed;
    float damage;
    GameObject target;

    public Enemy(){
        health = 100;
        speed = 100;
        damage = 1;
    }

}
