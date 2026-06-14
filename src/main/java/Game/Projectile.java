package Game;

import Render.Texture;
import Utils.Direction;

public class Projectile extends GameObject {
    float damage;
    float speed;
    public float lifeTime;
    Direction direction;
    public Projectile(){
        speed = 600;
        damage = 1;
        lifeTime = 1.0f;
        direction = Direction.NONE;
    }
}
