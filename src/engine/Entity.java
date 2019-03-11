package engine;

import org.joml.Matrix4f;
import org.joml.Vector3f;

public abstract class Entity
{
    public Matrix4f modelMatrix;
    public Vector3f positionVector;
    public Mesh mesh;
    private float rotX, rotY, rotZ;
    public Camera camera;

    public Entity()
    {
        modelMatrix = new Matrix4f();
        positionVector = new Vector3f();
    }

    public abstract void render();
    public abstract void update();
}
