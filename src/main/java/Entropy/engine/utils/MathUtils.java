package Entropy.engine.utils;

import org.joml.Matrix4f;
import org.joml.Vector3f;

public abstract class MathUtils {
    public static void createTransformationMatrix(Matrix4f matrix, Vector3f translation, Vector3f rotation, Vector3f scale) {
        matrix.identity();
        matrix.translate(translation);
        matrix.rotateAffineXYZ(rotation.x, rotation.y, rotation.z);
        matrix.scale(scale);
    }
}
