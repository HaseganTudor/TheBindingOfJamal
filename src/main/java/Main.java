import Game.GameObject;
import Game.Player;
import Game.Renderer;
import Game.Room;
import Physics.BoxCollider;
import org.joml.Vector3f;
import org.lwjgl.glfw.GLFWFramebufferSizeCallback;
import org.lwjgl.opengl.GL;

import java.awt.*;
import java.util.ArrayList;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryUtil.NULL;

public class Main {
    private long window;
    private static int width = 1500;
    private static int height = 900;
    private static float scale = 100f / height;
    private String title = "titlu";

    private ArrayList<GameObject> gameObjectList = new ArrayList<>();

    private static GLFWFramebufferSizeCallback resizeWindow = new GLFWFramebufferSizeCallback(){
        @Override
        public void invoke(long window, int width, int height){
            scale = 100f / (width * 9 / 16);
            glViewport(0,0,width,width * 9 / 16);
        }
    };

    private void init() {
        if (!glfwInit()) {
            System.out.println("nu s-a putut initializa GLFW");
            return;
        }

        glfwDefaultWindowHints();
        glfwWindowHint(GLFW_VISIBLE, GLFW_TRUE);
        glfwWindowHint(GLFW_RESIZABLE, GLFW_FALSE);

        window = glfwCreateWindow(width, height, title, NULL, NULL);

        glfwMakeContextCurrent(window);
        glfwSwapInterval(1);
        glfwSetFramebufferSizeCallback(window, resizeWindow);

        GL.createCapabilities();

    }

    private void loop() {

        Renderer renderer = new Renderer(width, height);

        Player p = new Player();

        double lastFrame = glfwGetTime();
        Room room = new Room(width, height);

        while (!glfwWindowShouldClose(window)) {

            glClear(GL_COLOR_BUFFER_BIT);
            glClearColor(0.5f, 0.5f, 0.5f, 1.0f);

            double currentFrame = glfwGetTime();
            double delta = currentFrame - lastFrame;
            lastFrame = currentFrame;

            renderer.draw(p);
            p.update(window, delta);
            room.drawRoom(renderer);

            for (GameObject obj : BoxCollider.colliders) {
                if (obj == p) continue;

                p.position.add(BoxCollider.getResolution(p, obj));
            }

            glfwPollEvents();
            glfwSwapBuffers(window);
        }

        glfwTerminate();
    }

    private void DrawGameObjects(Renderer renderer){
        for(GameObject obj : gameObjectList){
            renderer.draw(obj);
            obj.update();
        }
    }

    private void run() {
        init();
        loop();
    }

    public static void main(String[] args) {
        new Main().run();
    }
}