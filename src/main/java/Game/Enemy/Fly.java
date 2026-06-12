package Game.Enemy;

import Render.Texture;

public class Fly extends Enemy {
    public Fly(){
        speed = 25;
        damage = 1/2;
        texture = new Texture();
        texture.loadTexture("res/textures/enemies/fly.png");
    }
}
