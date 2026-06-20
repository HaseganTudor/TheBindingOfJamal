package Game.Enemy;

import Render.Texture;
import org.joml.Vector3f;

public class Fly extends Enemy {

    public Fly() {
        speed = 25;
        damage = 1 / 2;
        health = 5;
        texture = new Texture();
        texture.loadTexture("res/textures/enemies/fly.png");
        rgb = new Vector3f(1, 0.25f, 0.18f);
        type = "Fly";
    }
}
