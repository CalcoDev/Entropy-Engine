package Entropy.engine.shaders;

import java.nio.FloatBuffer;

public class DefaultShader extends Shader {
    private final int transformationLoc;

    public DefaultShader(String vertexPath, String fragmentPath) {
        super(vertexPath, fragmentPath);

        this.transformationLoc = super.getUniformLocation("transformationMatrix");
    }

    public void setTransformation(FloatBuffer transformationBuffer) {
        super.setMatrix4f(this.transformationLoc, transformationBuffer);
    }
}
