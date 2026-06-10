package Render;

public class SpriteSheet {
    private int rows;
    private int columns;
    private Texture texture;

    private float animationTimer = 0.0f;
    private float frameDuration = 0.1f;

    private int currentFrame = 0;

    public SpriteSheet(Texture texture, int rows, int columns) {
        this.texture = texture;
        this.rows = rows;
        this.columns = columns;
    }

    public void updateAnimation(double delta) {
        animationTimer += (float) delta;

        if (animationTimer >= frameDuration) {
            animationTimer = 0.0f;

            currentFrame++;

            if (currentFrame >= rows * columns) {
                currentFrame = 0;
            }
        }
    }

    public void apply(Shader shader) {
        float frameWidth = 1.0f / columns;
        float frameHeight = 1.0f / rows;

        int row = currentFrame / columns;
        int col = currentFrame % columns;

        float u = col * frameWidth;
        float v = row * frameHeight;

        shader.setUniform2f("uvOffset", u, v);
        shader.setUniform2f("uvScale", frameWidth, frameHeight);
    }

    public void setTexture(Texture texture, int rows, int columns) {
        if (this.texture == texture && this.rows == rows && this.columns == columns) {
            return;
        }

        this.texture = texture;
        this.rows = rows;
        this.columns = columns;
        resetAnimation();
    }

    public Texture getTexture() {
        return texture;
    }

    public void resetAnimation() {
        currentFrame = 0;
        animationTimer = 0.0f;
    }

    public void setFrameDuration(float duration) {
        this.frameDuration = duration;
    }

    public int getCurrentFrame() {
        return currentFrame;
    }
}
