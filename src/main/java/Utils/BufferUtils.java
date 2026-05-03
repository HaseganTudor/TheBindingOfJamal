package Utils;

import org.lwjgl.system.MemoryUtil;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

public class BufferUtils {
    public static FloatBuffer createFloatBuffer(float[] data){
        return MemoryUtil.memAllocFloat(data.length).put(data).flip();
    }

    public static IntBuffer createIntBuffer(int[] data){
        return MemoryUtil.memAllocInt(data.length).put(data).flip();
    }

    public static ByteBuffer createByteBuffer(byte[] data){
        return MemoryUtil.memAlloc(data.length).put(data).flip();
    }

    public static void free(java.nio.Buffer buffer){
        MemoryUtil.memFree(buffer);
    }
}
