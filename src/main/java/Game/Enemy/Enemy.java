package Game.Enemy;

import Game.GameObject;

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
