package Render;

import org.joml.Matrix4f;

public class Camera {
    private int width, height;
    private Matrix4f viewMatrix;
    private Matrix4f projectionMatrix;
    private Matrix4f modelMatrix;
    public static float left;
    public static float right;
    public static float top;
    public static float bottom;
    Shader shader;

    public Camera(int width, int height, Shader shader){
        this.width = width;
        this.height = height;
        this.shader = shader;

        left = -width / 2.0f;
        right = width / 2.0f;
        bottom = -height / 2.0f;
        top = height / 2.0f;

        viewMatrix = new Matrix4f().identity();
        projectionMatrix = new Matrix4f().ortho(left, right, bottom, top, -10f, 10);

        this.shader.bind();
        this.shader.setUniformMat4f("projection", projectionMatrix);
        this.shader.setUniformMat4f("view", viewMatrix);
        this.shader.unbind();
    }
}
