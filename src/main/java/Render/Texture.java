package Render;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.util.HashMap;
import org.lwjgl.stb.STBImage;
import org.lwjgl.system.MemoryStack;

import static org.lwjgl.opengl.GL11C.*;
import static org.lwjgl.opengl.GL30C.glGenerateMipmap;

public class Texture {
    private int ID;
    private static HashMap<String, Integer> cache = new HashMap<>();
    public int loadTexture(String path){
        if(cache.containsKey(path)){
            ID = cache.get(path);
            return ID;
        }
        int width, height;
        ByteBuffer img;
        try(MemoryStack stack = MemoryStack.stackPush()){
            IntBuffer widthBuffer = stack.mallocInt(1);
            IntBuffer heightBuffer = stack.mallocInt(1);
            IntBuffer channelsBuffer = stack.mallocInt(1);

            img = STBImage.stbi_load(path, widthBuffer, heightBuffer, channelsBuffer, 4);

            width = widthBuffer.get();
            height = heightBuffer.get();
        }

        ID = glGenTextures();
        glBindTexture(GL_TEXTURE_2D, ID);

        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_REPEAT);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_REPEAT);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR_MIPMAP_LINEAR);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);

        glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, width, height, 0, GL_RGBA, GL_UNSIGNED_BYTE, img);
        glGenerateMipmap(GL_TEXTURE_2D);

        STBImage.stbi_image_free(img);
        return ID;
    }

    public void bind(){
        glBindTexture(GL_TEXTURE_2D, ID);
    }

    public int getID(){
        return ID;
    }

    public void unbind(int ID){
        glBindTexture(GL_TEXTURE_2D, 0);
    }
}
