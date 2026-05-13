package Render;

import Utils.BufferUtils;
import Utils.FileUtils;
import org.joml.Matrix4f;

import static org.lwjgl.opengl.GL46C.*;

public class Shader {
    public static final int TEXTURE_SLOT = 1;
    public static final int VERTICES_SLOT = 0;

    private int program;
    private int vertexShader, fragmentShader;
    public Shader(String vertexPath, String fragmentPath){
        String vertexShaderSource = FileUtils.loadShader(vertexPath);
        String fragmentShaderSource = FileUtils.loadShader(fragmentPath);
        vertexShader = glCreateShader(GL_VERTEX_SHADER);
        glShaderSource(vertexShader, vertexShaderSource);
        glCompileShader(vertexShader);

        fragmentShader = glCreateShader(GL_FRAGMENT_SHADER);
        glShaderSource(fragmentShader, fragmentShaderSource);
        glCompileShader(fragmentShader);

        program = glCreateProgram();
        glAttachShader(program, vertexShader);
        glAttachShader(program, fragmentShader);
        glLinkProgram(program);
        glValidateProgram(program);
    }

    public void bind(){
        glUseProgram(program);
    }

    public void unbind(){
        glUseProgram(0);
    }

    public void setUniformMat4f(String name, Matrix4f matrix) {
        int location = glGetUniformLocation(program, name);
        float[] matrix_ = matrix.get(new float[16]);
        glUniformMatrix4fv(location, false, BufferUtils.createFloatBuffer(matrix_));
    }

    public void setSampler(int slot){
        glUniform1i(glGetUniformLocation(program, "uniTexture"), slot);
    }

    public void setInt(String name, int value){
        glUniform1i(glGetUniformLocation(program, name), value);
    }

    public void setColor(float r, float g, float b, float a){
        glUniform4f(glGetUniformLocation(program, "color"), r, g, b, a);
    }
}
