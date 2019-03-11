package math;

import org.joml.Matrix4f;
import org.joml.Vector3f;

public class Transformation
{
    public static Matrix4f createTransformation(Vector3f position, Vector3f rotation)
    {
        Matrix4f matrix = new Matrix4f();
        matrix.translate(position);
        matrix.rotate((float) Math.toRadians(rotation.z), 0, 0, 1);
        matrix.rotate((float) Math.toRadians(rotation.y), 0, 1, 0);
        matrix.rotate((float) Math.toRadians(rotation.x), 1, 0, 0);
        return matrix;
    }
}
