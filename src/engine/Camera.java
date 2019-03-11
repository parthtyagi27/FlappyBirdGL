package engine;

import org.joml.Matrix4f;

public class Camera
{
    private Matrix4f projectionMatrix;

    public Camera(int width, int height)
    {
//        projectionMatrix = new Matrix4f().ortho2D(0, width, height, 0);
        projectionMatrix = new Matrix4f().ortho(0, width, 0, height, -1, 1);
    }

    public Matrix4f getProjectionMatrix()
    {
        return projectionMatrix;
    }
}
