package Game;

import Render.Shader;
import Render.VertexArray;
import org.joml.Matrix4f;

public class GameObject{
    public Matrix4f modelMatrix;
    private VertexArray vertexArray;

    float[] vertices = {
            -0.5f, -0.5f, 0.0f,
            0.5f, -0.5f, 0.0f,
            0.5f, 0.5f, 0.0f,
            -0.5f, 0.5f, 0.0f,
    };

    int[] indices = {
            0,1,2,
            0,2,3
    };


    public GameObject() {
        super();
        modelMatrix = new Matrix4f()
                .translate(0.0f, 0.0f, -1.0f)
                .scale(100);
        vertexArray = new VertexArray(vertices, indices);
    }

    public void draw() {
        vertexArray.bind();
        vertexArray.render();
        vertexArray.unbind();
    }

    public Matrix4f getModelMatrix() {
        return modelMatrix;
    }
}
