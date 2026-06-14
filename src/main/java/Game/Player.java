package Game;

import Render.SpriteSheet;
import Render.Texture;
import Utils.Direction;
import org.joml.Math;
import org.joml.Vector3f;

import java.util.ArrayList;

import static org.lwjgl.glfw.GLFW.*;

public class Player extends GameObject {
    private float shootSpeed = 0f;
    private float tearLifeTime = 1.5f;
    private float walkSpeed = 400f;
    private float sizeX = 115f;
    private float sizeY = 115f;
    private Texture Idle = new Texture();
    private Texture Walk_Right =  new Texture();
    private Texture Walk_Left =  new Texture();
    private Texture Walk_Up =  new Texture();
    private Texture Walk_Down =  new Texture();
    private Vector3f lastDirection = new Vector3f(0, -1, 0);
    ArrayList<Projectile> projectiles = new ArrayList<>();
    Renderer renderer;

    public Player(Renderer renderer) {
        setColliderSize(sizeX - 50.0f, sizeY - 25.0f);
        setColliderOffset(0.0f, -10.0f);

        this.renderer = renderer;
        Idle.loadTexture("res/textures/player/player_idle.png");
        Walk_Right.loadTexture("res/textures/player/player_walk_right.png");
        Walk_Left.loadTexture("res/textures/player/player_walk_left.png");
        Walk_Up.loadTexture("res/textures/player/player_walk_up.png");
        Walk_Down.loadTexture("res/textures/player/player_walk_down.png");
        spriteSheet = new SpriteSheet(Idle,1, 2);
        spriteSheet.setFrameDuration(0.2f);
        texture = Idle;
    }

    public void update(long window, double delta)
    {
        boolean isMoving = PlayerMovement(window, delta);
        updateSprite(delta, isMoving);
        shoot(window,delta);
        for(Projectile projectile : projectiles){
            moveProjectile(projectile, projectile.direction,delta);
        }

        modelMatrix.identity()
                .translate(position)
                .scale(sizeX, sizeY, 1.0f)
                .rotateX((float) Math.toRadians(180));

        for(int i=0; i<projectiles.size(); i++){
            if(projectiles.get(i).lifeTime <= 0){
                projectiles.remove(projectiles.get(i));
            }
            else{
                projectiles.get(i).lifeTime -= (float) delta;
            }
        }

    }


    private boolean PlayerMovement(long window, double delta){
        Vector3f movement = new Vector3f(0,0,0);

        if(glfwGetKey(window, GLFW_KEY_W) == GLFW_PRESS){
            movement.y = 1;
        }
        if(glfwGetKey(window, GLFW_KEY_S) == GLFW_PRESS){
            movement.y = -1;
        }
        if(glfwGetKey(window, GLFW_KEY_D) == GLFW_PRESS){
            movement.x = 1;
        }
        if(glfwGetKey(window, GLFW_KEY_A) == GLFW_PRESS){
            movement.x = -1;
        }

        if(movement.length() > 0){
            lastDirection.set(movement);
            movement.normalize().mul(((float) delta * walkSpeed));
            position.add(movement);
            return true;
        }

        return false;
    }

    public void shoot(long window,double delta){
        shootSpeed-= (float) delta;
        if(glfwGetKey(window, GLFW_KEY_LEFT) == GLFW_PRESS && shootSpeed <= 0){
            Tear tear = new Tear(this);
            tear.position = new Vector3f(position);
            tear.setSize(70);
            tear.setRotationZ(180);
            tear.direction = Direction.LEFT;
            projectiles.add(tear);
            shootSpeed = 0.4f;
        }
        if(glfwGetKey(window, GLFW_KEY_RIGHT) == GLFW_PRESS && shootSpeed <= 0){
            Tear tear = new Tear(this);
            tear.position = new Vector3f(position);
            tear.setSize(70);
            tear.setRotationZ(180);
            tear.direction = Direction.RIGHT;
            projectiles.add(tear);
            shootSpeed = 0.4f;
        }
        if(glfwGetKey(window, GLFW_KEY_UP) == GLFW_PRESS && shootSpeed <= 0){
            Tear tear = new Tear(this);
            tear.position = new Vector3f(position);
            tear.setSize(70);
            tear.setRotationZ(180);
            tear.direction = Direction.UP;
            projectiles.add(tear);
            shootSpeed = 0.4f;
        }
        if(glfwGetKey(window, GLFW_KEY_DOWN) == GLFW_PRESS && shootSpeed <= 0){
            Tear tear = new Tear(this);
            tear.position = new Vector3f(position);
            tear.setSize(70);
            tear.setRotationZ(180);
            tear.direction = Direction.DOWN;
            projectiles.add(tear);
            shootSpeed = 0.40f;
        }
    }

    private void moveProjectile(Projectile projectile, Direction dir, double delta) {
        switch (dir){
            case LEFT:
                projectile.position.x -= projectile.speed * (float) delta;
                break;
            case RIGHT:
                projectile.position.x += projectile.speed * (float) delta;
                break;
            case UP:
                projectile.position.y += projectile.speed * (float) delta;
                break;
            case DOWN:
                projectile.position.y -= projectile.speed * (float) delta;
                break;
            case NONE: break;
        }
    }

    private void updateSprite(double delta, boolean isMoving) {
        if (!isMoving) {
            spriteSheet.setTexture(Idle, 1, 2);
            spriteSheet.updateAnimation(delta);
            return;
        }

        if (Math.abs(lastDirection.x) > Math.abs(lastDirection.y)) {
            if (lastDirection.x > 0) {
                spriteSheet.setTexture(Walk_Right, 1, 3);
            } else {
                spriteSheet.setTexture(Walk_Left, 1, 3);
            }
        } else {
            if (lastDirection.y > 0) {
                spriteSheet.setTexture(Walk_Up, 1, 3);
            } else {
                spriteSheet.setTexture(Walk_Down, 1, 3);
            }
        }

        spriteSheet.updateAnimation(delta);
    }
}
