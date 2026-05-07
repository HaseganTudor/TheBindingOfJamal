package Game;

import Render.Camera;
import Render.Shader;
import org.joml.Vector3f;

public class Renderer {
    private static Shader shader;
    private static Camera camera;

    public Renderer(int width, int height){
        shader = new Shader("res/shaders/vert.glsl", "res/shaders/frag.glsl");
        camera = new Camera(width, height, shader);
    }

    public void draw(GameObject object){
        shader.bind();

        shader.setUniformMat4f("model",object.getModelMatrix());
        shader.setColor(object.color.x, object.color.y, object.color.z, 1.0f);
        object.draw();
    }

}
