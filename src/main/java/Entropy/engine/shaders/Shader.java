package Entropy.engine.shaders;

import Entropy.engine.utils.FileUtils;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.lwjgl.opengl.GL20;

import java.nio.FloatBuffer;

public class Shader {
    private final int programId;

    private final int vertexShaderId;
    private final int fragmentShaderId;

    public Shader(String vertexPath, String fragmentPath) {
        String vertexSource = FileUtils.getFileContents(vertexPath);
        String fragmentSource = FileUtils.getFileContents(fragmentPath);

        this.vertexShaderId = createShader(vertexSource, GL20.GL_VERTEX_SHADER);
        this.fragmentShaderId = createShader(fragmentSource, GL20.GL_FRAGMENT_SHADER);

        this.programId = createProgram();

        linkAndValidateProgram(this.programId, this.vertexShaderId, this.fragmentShaderId);

//        bindAttributes();
    }

    public int getUniformLocation(String name) {
        return GL20.glGetUniformLocation(this.programId, name);
    }

    public void setInt(int location, int value) {
        GL20.glUniform1i(location, value);
    }

    public void setFloat(int location, float value) {
        GL20.glUniform1f(location, value);
    }

    public void setBool(int location, boolean value) {
        GL20.glUniform1i(location, value ? 1 : 0);
    }

    public void setVector2f(int location, Vector2f vector2f) {
        GL20.glUniform2f(location, vector2f.x, vector2f.y);
    }

    public void setVector3f(int location, Vector3f vector3f) {
        GL20.glUniform3f(location, vector3f.x, vector3f.y, vector3f.z);
    }

    public void setMatrix4f(int location, FloatBuffer matrixBuffer) {
        GL20.glUniformMatrix4fv(location, false, matrixBuffer);
    }

    private void bindAttributes() {

    }

    private void bindAttribute(int index, String name) {
        GL20.glBindAttribLocation(this.programId, index, name);
    }

    public static int createProgram() {
        int programId = GL20.glCreateProgram();
        if (programId == 0) {
            throw new RuntimeException("Failed to create shader program.");
        }

        return programId;
    }

    public static void linkAndValidateProgram(int programId, int vertexShaderId, int fragmentShaderId) {
        GL20.glAttachShader(programId, vertexShaderId);
        GL20.glAttachShader(programId, fragmentShaderId);

        GL20.glLinkProgram(programId);
        if (GL20.glGetProgrami(programId, GL20.GL_LINK_STATUS) == GL20.GL_FALSE) {
            throw new RuntimeException("Failed to link shader program: " + GL20.glGetProgramInfoLog(programId));
        }

        GL20.glValidateProgram(programId);
        if (GL20.glGetProgrami(programId, GL20.GL_VALIDATE_STATUS) == GL20.GL_FALSE) {
            throw new RuntimeException("Failed to validate shader program: " + GL20.glGetProgramInfoLog(programId));
        }
    }

    public void dispose() {
        deleteProgram(this.programId);
    }

    public static void deleteProgram(int programId) {
        GL20.glDeleteProgram(programId);
    }

    public static int createShader(String source, int type) {
        int shaderId = GL20.glCreateShader(type);

        GL20.glShaderSource(shaderId, source);
        GL20.glCompileShader(shaderId);

        if (GL20.glGetShaderi(shaderId, GL20.GL_COMPILE_STATUS) == GL20.GL_FALSE) {
            throw new RuntimeException("Error compiling shader: " + GL20.glGetShaderInfoLog(shaderId));
        }

        return shaderId;
    }

    public void bind() {
        GL20.glUseProgram(this.programId);
    }

    public void unbind() {
        GL20.glUseProgram(0);
    }

    public int getProgramId() {
        return programId;
    }

    public int getVertexShaderId() {
        return vertexShaderId;
    }

    public int getFragmentShaderId() {
        return fragmentShaderId;
    }
}
