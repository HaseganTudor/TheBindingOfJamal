package Game;

import Render.Camera;
import Render.Shader;

import java.util.ArrayList;

import static org.lwjgl.opengl.GL13C.GL_TEXTURE0;
import static org.lwjgl.opengl.GL13C.glActiveTexture;

public class Renderer {
    protected static Shader shader;
    protected static Camera camera;

    public Renderer(int width, int height){
        shader = new Shader("res/shaders/vert.glsl", "res/shaders/frag.glsl");
        camera = new Camera(width, height, shader);

        shader.setVec2f("lightPos", 0.0f, 0.0f);
        shader.setVec3f("lightColor", 1.0f, 1.0f, 1.0f);
        shader.setFloat("lightRadius", 300.0f);

    }

    public Renderer() {

    }

    public void draw(GameObject object)
    {
        shader.bind();


        shader.setColor(
                object.color.x,
                object.color.y,
                object.color.z,
                1.0f
        );

        shader.setUniform2f("uvOffset", 0.0f, 0.0f);
        shader.setUniform2f("uvScale", 1.0f, 1.0f);

        if (object.spriteSheet != null) {
            object.texture = object.spriteSheet.getTexture();
            object.spriteSheet.apply(shader);
        }

        if(object.texture != null)
        {
            glActiveTexture(GL_TEXTURE0);

            shader.setInt("hasTexture", 1);
            shader.setSampler(0);

            object.texture.bind();
        }
        else
        {
            shader.setInt("hasTexture", 0);
        }

        shader.setUniformMat4f(
                "model",
                object.getModelMatrix()
        );

        object.draw();
    }

}
