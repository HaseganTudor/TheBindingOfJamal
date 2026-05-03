package Render;

import Utils.FileUtils;

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
}
