package Game;

import Render.Shader;
import Render.VertexArray;
import org.joml.Matrix4f;
import org.joml.Vector3f;

public class GameObject{
    public Matrix4f modelMatrix;
    private VertexArray vertexArray;
    public Vector3f position = new Vector3f(0.0f, 0.0f, -1.0f);
    public float size = 100f;
    public boolean isSolid = true;

    Vector3f color = new Vector3f(0.0f,0.0f,0.0f);

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
        modelMatrix = new Matrix4f()
                .translate(position)
                .scale(size);
        vertexArray = new VertexArray(vertices, indices);
    }

    public void update(){
        modelMatrix.identity()
                .translate(position)
                .scale(size);
    }

    public void draw() {
        vertexArray.bind();
        vertexArray.render();
        vertexArray.unbind();
    }

    public Matrix4f getModelMatrix() {
        return modelMatrix;
    }

    public float getHalfSize(){
        return size / 2;
    }

    public void setColor(float r, float g, float b){
        color.set(r,g,b);
    }

    public void setSize(float size){
        this.size = size;
    }

    public void setSolid(boolean solidState){ isSolid = solidState; }
}
