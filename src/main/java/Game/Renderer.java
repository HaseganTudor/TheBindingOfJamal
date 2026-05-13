package Game;

import Render.Camera;
import Render.Shader;
import org.joml.Vector3f;

import static org.lwjgl.opengl.GL13C.GL_TEXTURE0;
import static org.lwjgl.opengl.GL13C.glActiveTexture;

public class Renderer {
    private static Shader shader;
    private static Camera camera;

    public Renderer(int width, int height){
        shader = new Shader("res/shaders/vert.glsl", "res/shaders/frag.glsl");
        camera = new Camera(width, height, shader);
    }

    public void draw(GameObject object){
        shader.bind();

        shader.setColor(object.color.x, object.color.y, object.color.z, 1.0f);

        if(object.texture != null){
            glActiveTexture(GL_TEXTURE0);
            shader.setInt("hasTexture", 1);
            shader.setSampler(0);
            object.texture.bind();
        }
        else{
            shader.setInt("hasTexture", 0);
        }

        shader.setUniformMat4f("model", object.getModelMatrix());
        object.draw();
    }

}
