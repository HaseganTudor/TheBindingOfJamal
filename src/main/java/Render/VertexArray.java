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

        glEnableVertexAttribArray(Shader.VERTICES_SLOT);
        glVertexAttribPointer(Shader.VERTICES_SLOT, 3, GL_FLOAT, false, 3 * Float.BYTES, 0);

        BufferUtils.free(vert);
        BufferUtils.free(ind);

        glBindVertexArray(0);
    }

    public void render(){
        glBindVertexArray(vao);
        glDrawElements(GL_TRIANGLES, 6, GL_UNSIGNED_INT, 0);
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
