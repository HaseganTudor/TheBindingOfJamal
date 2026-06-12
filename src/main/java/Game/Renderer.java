package Game;

import Game.Enemy.Enemy;
import Render.Camera;
import Render.Shader;

import java.util.List;

import static org.lwjgl.opengl.GL13C.GL_TEXTURE0;
import static org.lwjgl.opengl.GL13C.glActiveTexture;

public class Renderer {
    protected static Shader shader;
    protected static Camera camera;
    private static final int MAX_LIGHTS = 12;

    public Renderer(int width, int height){
        shader = new Shader("res/shaders/vert.glsl", "res/shaders/frag.glsl");
        camera = new Camera(width, height, shader);

        shader.bind();
        shader.setInt("lightCount", 0);

    }

    public Renderer() {

    }

    public void draw(GameObject object)
    {
        shader.bind();


        shader.setColor(
                object.color.x,
                object.color.y,
                object.color.z,
                1.0f
        );

        shader.setUniform2f("uvOffset", 0.0f, 0.0f);
        shader.setUniform2f("uvScale", 1.0f, 1.0f);

        if (object.spriteSheet != null) {
            object.texture = object.spriteSheet.getTexture();
            object.spriteSheet.apply(shader);
        }

        if(object.texture != null)
        {
            glActiveTexture(GL_TEXTURE0);

            shader.setInt("hasTexture", 1);
            shader.setSampler(0);

            object.texture.bind();
        }
        else
        {
            shader.setInt("hasTexture", 0);
        }

        shader.setUniformMat4f(
                "model",
                object.getModelMatrix()
        );

        object.draw();
    }

    public void setSceneLights(
            boolean doorsEnabled,
            GameObject doorUp,
            GameObject doorDown,
            GameObject doorLeft,
            GameObject doorRight,
            Player player,
            List<Enemy> enemies
    ) {
        shader.bind();

        int lightIndex = 0;

        if (doorsEnabled) {
            GameObject[] doors = { doorUp, doorDown, doorLeft, doorRight };

            for (GameObject door : doors) {
                if (door == null || lightIndex >= MAX_LIGHTS) {
                    continue;
                }

                lightIndex = setLight(
                        lightIndex,
                        door.position.x,
                        door.position.y,
                        1.0f,
                        0.78f,
                        0.35f,
                        250.0f,
                        0.40f
                );
            }
        }

        if (player != null && lightIndex < MAX_LIGHTS) {
            lightIndex = setLight(
                    lightIndex,
                    player.position.x,
                    player.position.y,
                    0.70f,
                    0.88f,
                    1.0f,
                    50.0f,
                    0.3f
            );
        }

        for (Enemy enemy : enemies) {
            if (lightIndex >= MAX_LIGHTS) {
                break;
            }

            lightIndex = setLight(
                    lightIndex,
                    enemy.position.x,
                    enemy.position.y,
                    1.0f,
                    0.25f,
                    0.18f,
                    100.0f,
                    0.65f
            );
        }

        shader.setInt("lightCount", lightIndex);
    }

    private int setLight(int index, float x, float y, float r, float g, float b, float radius, float intensity) {
        shader.setVec2f("lights[" + index + "].position", x, y);
        shader.setVec3f("lights[" + index + "].color", r, g, b);
        shader.setFloat("lights[" + index + "].radius", radius);
        shader.setFloat("lights[" + index + "].intensity", intensity);

        return index + 1;
    }

}
