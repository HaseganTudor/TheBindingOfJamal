import Game.GameObject;
import Game.Player;
import Game.Renderer;
import Physics.BoxCollider;
import org.joml.Vector3f;
import org.lwjgl.opengl.GL;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryUtil.NULL;

public class Main {
    private long window;
    private int width = 1400;
    private int height = 1000;
    private String title = "titlu";

    private void init() {
        if (!glfwInit()) {
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

    private void loop() {

        Renderer renderer = new Renderer(width, height);

        Player p = new Player();

        GameObject cube = new GameObject();
        BoxCollider.addCollider(cube);

        cube.setColor(1, 0, 0);
        cube.position = new Vector3f(0, 200, 0);
        cube.update();

        double lastFrame = glfwGetTime();

        while (!glfwWindowShouldClose(window)) {

            glClear(GL_COLOR_BUFFER_BIT);
            glClearColor(0.5f, 0.5f, 0.5f, 1.0f);

            double currentFrame = glfwGetTime();
            double delta = currentFrame - lastFrame;
            lastFrame = currentFrame;

            for (GameObject obj : BoxCollider.colliders) {
                if (obj == p) continue;

                p.position.add(BoxCollider.getResolution(p, obj));
            }


            p.update(window, delta);

            renderer.draw(p);
            renderer.draw(cube);

            glfwPollEvents();
            glfwSwapBuffers(window);
        }

        glfwTerminate();
    }

    private void run() {
        init();
        loop();
    }

    public static void main(String[] args) {
        new Main().run();
    }
}