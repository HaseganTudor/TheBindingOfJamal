package Game;

import Render.Shader;
import Render.Texture;
import Render.VertexArray;
import org.joml.Math;
import org.joml.Matrix4f;
import org.joml.Vector3f;


public class GameObject{
    public Matrix4f modelMatrix;
    private VertexArray vertexArray;
    public Texture texture;
    public Vector3f position = new Vector3f(0.0f, 0.0f, -1.0f);
    public  float sizeX = 100f;
    public  float sizeY = 100f;
    public float size = 100f;
    public boolean isSolid = true;
    Vector3f color = new Vector3f(0.0f,0.0f,0.0f);
    private float rotationZ = 0.0f;

    float[] vertices = {
            // x,    y,    z,     u,    v
            -0.5f, -0.5f, 0.0f,  0.0f, 0.0f,
            0.5f, -0.5f, 0.0f,  1.0f, 0.0f,
            0.5f,  0.5f, 0.0f,  1.0f, 1.0f,
            -0.5f,  0.5f, 0.0f,  0.0f, 1.0f
    };
    int[] indices = {
            0,1,2,
            0,2,3
    };

    public GameObject() {
        modelMatrix = new Matrix4f()
                .translate(position)
                .scale(sizeX, sizeY, 0.0f)
                .rotateZ(Math.toRadians(rotationZ));
        vertexArray = new VertexArray(vertices, indices);
    }

    public void setTexture(String path){
        texture = new Texture();
        texture.loadTexture(path);
    }

    public void update(){
        modelMatrix = new Matrix4f()
                .translate(position)
                .scale(sizeX, sizeY, 0.0f)
                .rotateZ(Math.toRadians(rotationZ));
    }

    public void draw() {
        if(texture != null)
            texture.bind();
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

    public void setSizeX(float size){
        this.sizeX = size;
    }
    public void setSizeY(float size){
        this.sizeY = size;
    }

    public float getSize(){
        return size;
    }

    public float getSizeX(){
        return sizeX;
    }

    public float getSizeY(){
        return sizeY;
    }

    public void setSolid(boolean solidState){ isSolid = solidState; }

    public float getHalfSizeX() {
        return sizeX / 2;
    }

    public float getHalfSizeY() {
        return sizeY / 2;
    }

    public void setRotationZ(float Degrees){
        rotationZ = Degrees;
    }
}
