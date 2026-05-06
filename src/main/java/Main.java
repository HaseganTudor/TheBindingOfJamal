import Game.GameObject;
import Game.Player;
import Game.Renderer;
import Render.Camera;
import Render.Shader;
import org.lwjgl.opengl.*;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryUtil.*;

public class Main {
    private long window;
    private int width = 1400;
    private int height = 1000;
    private String title = "titlu";

    private void init(){
        if(!glfwInit()) {
            System.out.println("nu s-a putut initializa GLFW");
            return;
        }
        glfwDefaultWindowHints();
        glfwWindowHint(GLFW_VISIBLE, GLFW_TRUE);
        glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE);

        window = glfwCreateWindow(width, height, title, NULL, NULL);

        glfwMakeContextCurrent(window);
        glfwSwapInterval(1);

        GL.createCapabilities();
    }

    private void loop(){

        Renderer renderer = new Renderer(width, height);
        GameObject object = new GameObject();
        Player p = new Player();

        double lastFrame = glfwGetTime();

        while(!glfwWindowShouldClose(window)) {
            glClear(GL_COLOR_BUFFER_BIT);
            glClearColor(0.5f, 0.5f, 0.5f, 1.0f);

            double currentFrame = glfwGetTime();
            double delta = currentFrame - lastFrame;
            lastFrame = currentFrame;

            renderer.draw(p);
            p.update(window,delta);

            glfwPollEvents();
            glfwSwapBuffers(window);
        }

        glfwTerminate();
    }

    private void run(){
        init();
        loop();
    }

    public static void main(String[] args) {
        new Main().run();
    }
}