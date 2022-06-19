package Entropy.engine.textures;

public class Texture {
    private final int textureId;

    private int width;
    private int height;

    private int channels;

    public Texture(int textureId, int width, int height, int channels) {
        this.textureId = textureId;
        this.width = width;
        this.height = height;
        this.channels = channels;
    }

    public int getTextureId() {
        return textureId;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getChannels() {
        return channels;
    }
}
