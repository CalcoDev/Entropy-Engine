package Entropy.engine.rendering;

import Entropy.engine.entities.Entity;
import Entropy.engine.shaders.DefaultShader;
import org.joml.Matrix4f;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

import java.nio.FloatBuffer;

public class Renderer {
    private final FloatBuffer floatBuffer4x4 = BufferUtils.createFloatBuffer(16);
    private final Matrix4f matrix4f = new Matrix4f();

    public void clear(float r, float g, float b, float a) {
        GL11.glClearColor(r, g, b, a);
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
    }

    public void render(Entity entity, DefaultShader shader) {
        var texturedModel = entity.getModel();
        var rawModel = texturedModel.getRawModel();

        GL30.glBindVertexArray(rawModel.getVaoId());

        GL20.glEnableVertexAttribArray(0);
        GL20.glEnableVertexAttribArray(1);

        // Send transformation matrix to shader
        entity.getTransform().toTransformationMatrix(matrix4f);
        matrix4f.get(floatBuffer4x4);
        shader.setTransformation(floatBuffer4x4);

        GL20.glActiveTexture(GL20.GL_TEXTURE0);
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, texturedModel.getTexture().getTextureId());

        GL15.glDrawElements(GL11.GL_TRIANGLES, rawModel.getVertexCount(), GL11.GL_UNSIGNED_INT, 0);

        GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0);

        GL20.glDisableVertexAttribArray(0);
        GL20.glDisableVertexAttribArray(1);

        GL30.glBindVertexArray(0);
    }
}
