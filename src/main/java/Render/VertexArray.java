package Render;

import Utils.BufferUtils;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import static org.lwjgl.opengl.GL46C.*;
import static org.lwjgl.system.MemoryUtil.memAllocFloat;

public class VertexArray {
    private int vao, vbo, ebo;

    public VertexArray(float[] vertices, int[] indices) {
        vao = glGenVertexArrays();
        glBindVertexArray(vao);

        vbo = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, vbo);

        FloatBuffer vert = BufferUtils.createFloatBuffer(vertices);
        glBufferData(GL_ARRAY_BUFFER, vert, GL_STATIC_DRAW);

        ebo = glGenBuffers();
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, ebo);

        IntBuffer ind = BufferUtils.createIntBuffer(indices);
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, ind, GL_STATIC_DRAW);

        int stride = 5 * Float.BYTES;

        glEnableVertexAttribArray(Shader.VERTICES_SLOT);
        glVertexAttribPointer(Shader.VERTICES_SLOT, 3, GL_FLOAT, false, stride, 0);

        glEnableVertexAttribArray(Shader.TEXTURE_SLOT);
        glVertexAttribPointer(Shader.TEXTURE_SLOT, 2, GL_FLOAT, false, stride, 3 * Float.BYTES);

        BufferUtils.free(vert);
        BufferUtils.free(ind);

        glBindVertexArray(0);
    }

    public void render(){

        bind();
        glDrawElements(GL_TRIANGLES, 6, GL_UNSIGNED_INT, 0);
    }

    public void renderLines(){
        bind();
        glDrawElements(GL_LINES, 6, GL_UNSIGNED_INT, 0);
    }

    public void bind() {
        glBindVertexArray(vao);
    }

    public void unbind() {
        glBindVertexArray(0);
    }

    public void destroy() {
        glDeleteBuffers(vbo);
    }
}
