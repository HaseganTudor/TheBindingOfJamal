import Game.*;
import Game.Enemy.Fly;
import Physics.BoxCollider;
import org.joml.Vector3f;
import org.lwjgl.glfw.GLFWFramebufferSizeCallback;
import org.lwjgl.opengl.GL;

import java.awt.*;
import java.util.ArrayList;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.stb.STBImage.stbi_set_flip_vertically_on_load;
import static org.lwjgl.system.MemoryUtil.NULL;
import java.awt.Frame;

public class Main {
    private long window;
    private static int width = 1650;
    private static int height = 1050;
    private String title = "The Bindig Of Jamal";

    private ArrayList<GameObject> gameObjectList = new ArrayList<>();

    private static GLFWFramebufferSizeCallback resizeWindow = new GLFWFramebufferSizeCallback(){
        @Override
        public void invoke(long window, int width, int height){
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

        glEnable(GL_BLEND);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);

    }

    private void loop() {

        Renderer renderer = new Renderer(width, height);

        Player p = new Player();
        p.setColor(1, 1, 1);

        double lastFrame = glfwGetTime();
        Map map =  new Map(p,width, height, 10, renderer);
        Vector3f currentPos = p.position;
        Vector3f endPos = new Vector3f(10, 0, 0);

        ArrayList<Tear> tears = new ArrayList<>();


        glClearColor(0.05f, 0.05f, 0.05f, 1.0f);
        while (!glfwWindowShouldClose(window)) {

            glClear(GL_COLOR_BUFFER_BIT);

            double currentFrame = glfwGetTime();
            double delta = currentFrame - lastFrame;
            lastFrame = currentFrame;

            map.drawRoom(renderer);

            renderer.draw(p);
            p.update(window, delta);


            if(glfwGetKey(window, GLFW_KEY_ESCAPE) == GLFW_PRESS) {
                map.currentRoom.isCleared = true;
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
