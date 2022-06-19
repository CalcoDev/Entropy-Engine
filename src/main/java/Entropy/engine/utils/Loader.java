package Entropy.engine.utils;

import Entropy.engine.models.RawModel;
import Entropy.engine.textures.Texture;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.*;
import org.lwjgl.stb.STBImage;
import org.lwjgl.system.MemoryStack;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;

public class Loader {
    private final List<Integer> VAOs;
    private final List<Integer> VBOs;
    private final List<Integer> textures;

    public Loader() {
        VAOs = new ArrayList<>();
        VBOs = new ArrayList<>();
        textures = new ArrayList<>();
    }

    public RawModel loadModel(float[] positions, float[] texCoords, int[] indices) {
        // Create a VAO.
        int vaoId = createVAO();

        GL30.glBindVertexArray(vaoId);

        storeDataInVAO(positions, 0, 3);
        storeDataInVAO(texCoords, 1, 2);

        // Index buffer
        int indexVBOId = createVBO();

        GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, indexVBOId);

        IntBuffer indicesBuffer = createFlippedBuffer(indices);
        GL15.glBufferData(GL15.GL_ELEMENT_ARRAY_BUFFER, indicesBuffer, GL15.GL_STATIC_DRAW);

        GL30.glBindVertexArray(0);

        GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, 0);

        // Return the VAO.
        return new RawModel(vaoId, indices.length);
    }

    private void storeDataInVAO(float[] data, int index, int size) {
        int vboId = createVBO();

        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vboId);

        FloatBuffer buffer = createFlippedBuffer(data);
        GL15.glBufferData(GL15.GL_ARRAY_BUFFER, buffer, GL15.GL_STATIC_DRAW);

        // Add the VBO to the VAO.
        GL20.glVertexAttribPointer(index, size, GL11.GL_FLOAT, false, 0, 0);

        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
    }

    public Texture loadTexture(String filePath) {
        int textureId = GL11.glGenTextures();
        int width = 0;
        int height = 0;
        int channels = 0;

        GL11.glBindTexture(GL11.GL_TEXTURE_2D, textureId);

        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, GL12.GL_CLAMP_TO_EDGE);
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, GL12.GL_CLAMP_TO_EDGE);

        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_NEAREST);
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST);

        try (MemoryStack stack = MemoryStack.stackPush()) {
            IntBuffer widthPtr = stack.mallocInt(1);
            IntBuffer heightPtr = stack.mallocInt(1);
            IntBuffer channelsPtr = stack.mallocInt(1);


            STBImage.nstbi_set_flip_vertically_on_load(1);
            ByteBuffer imageData = STBImage.stbi_load(filePath, widthPtr, heightPtr, channelsPtr, 0);

            width = widthPtr.get(0);
            height = heightPtr.get(0);
            channels = channelsPtr.get(0);

            GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGB, width, height, 0, GL11.GL_RGB, GL11.GL_UNSIGNED_BYTE, imageData);
            GL30.glGenerateMipmap(GL11.GL_TEXTURE_2D);
        }

        return new Texture(textureId, width, height, channels);
    }

    private int createVAO() {
        int vaoId = GL30.glGenVertexArrays();
        VAOs.add(vaoId);
        return vaoId;
    }

    private int createVBO() {
        int vboId = GL15.glGenBuffers();
        VBOs.add(vboId);
        return vboId;
    }

    private FloatBuffer createFlippedBuffer(float[] data) {
        FloatBuffer buffer = BufferUtils.createFloatBuffer(data.length);
        buffer.put(data);
        buffer.flip();
        return buffer;
    }

    private IntBuffer createFlippedBuffer(int[] data) {
        IntBuffer buffer = BufferUtils.createIntBuffer(data.length);
        buffer.put(data);
        buffer.flip();
        return buffer;
    }

    public void dispose() {
        for (int vaoId : VAOs) {
            GL30.glDeleteVertexArrays(vaoId);
        }

        for (int vboId : VBOs) {
            GL15.glDeleteBuffers(vboId);
        }

        for (int textureId : textures) {
            GL11.glDeleteTextures(textureId);
        }
    }
}
