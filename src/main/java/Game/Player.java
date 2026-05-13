package Game;

import Physics.BoxCollider;
import org.joml.Matrix4f;
import org.joml.Vector3f;

import static org.lwjgl.glfw.GLFW.*;

public class Player extends GameObject {
    private float walkSpeed = 600f;
    private float sizeX = 80f;
    private float sizeY = 100f;

    public Player() {
        BoxCollider.addCollider(this);
    }

    public void update(long window, double delta){
        PlayerMovement(window,delta);
        setSizeX(sizeX);
        setSizeY(sizeY);

        modelMatrix.identity()
                .translate(position)
                .scale(sizeX,sizeY,0.0f);
    }


    private void PlayerMovement(long window, double delta){
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
            movement.normalize().mul(((float) delta * walkSpeed));
            position.add(movement);
        }
    }
}
