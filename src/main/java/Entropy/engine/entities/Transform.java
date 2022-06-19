package Entropy.engine.entities;

import Entropy.engine.utils.MathUtils;
import org.joml.Matrix4f;
import org.joml.Vector3f;

public class Transform {
    private Vector3f position = new Vector3f(0f, 0f, 0f);
    private Vector3f rotation = new Vector3f(0f, 0f, 0f);
    private Vector3f scale = new Vector3f(1f, 1f, 1f);

    public Transform(Vector3f position, Vector3f rotation, Vector3f scale) {
        this.position = position;
        this.rotation = rotation;
        this.scale = scale;
    }

    public Transform() {
    }

    public void toTransformationMatrix(Matrix4f matrix) {
        MathUtils.createTransformationMatrix(matrix, position, rotation, scale);
    }

    public Vector3f getPosition() {
        return position;
    }

    public void setPosition(Vector3f position) {
        this.position = position;
    }

    public Vector3f getRotation() {
        return rotation;
    }

    public void setRotation(Vector3f rotation) {
        this.rotation = rotation;
    }

    public Vector3f getScale() {
        return scale;
    }

    public void setScale(Vector3f scale) {
        this.scale = scale;
    }
}
