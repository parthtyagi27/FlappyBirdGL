package engine;

import org.joml.Matrix4f;

public class Camera
{
    private Matrix4f projectionMatrix;

    public Camera(int width, int height)
    {
//      Create the projection matrix for the game, since it's 2D we use an orthographic matrix
        projectionMatrix = new Matrix4f().ortho(0, width, 0, height, -1, 1);
    }

    public Matrix4f getProjectionMatrix()
    {
        return projectionMatrix;
    }
}
