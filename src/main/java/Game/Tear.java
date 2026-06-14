package Game;

import org.joml.Vector3f;

public class Tear extends Projectile{
    public Tear(Player player){
        super();
        position = new Vector3f(player.position);
        setTexture("res/textures/player/tear.png");
    }
}
