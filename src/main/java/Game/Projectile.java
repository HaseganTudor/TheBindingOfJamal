package Game;

import Render.Texture;

public class Projectile extends GameObject {
    float damage;
    float speed;
    float maxTravel;
    public Projectile(){
        speed = 100;
        damage = 1;
        maxTravel = 500.0f;
    }
}
