package Game;

import Physics.BoxCollider;
import org.joml.Matrix4f;
import org.joml.Vector3f;

import static org.lwjgl.glfw.GLFW.*;

public class Player extends GameObject {
    private float walkSpeed = 600f;

    public Player() {
        super();
        position = new Vector3f(0,-200f,-1f);
        BoxCollider.addCollider(this);
    }

    public void update(long window, double delta){
        PlayerMovement(window,delta);

        modelMatrix.identity()
                .translate(position)
                .scale(100);
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
