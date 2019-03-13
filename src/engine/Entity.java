package engine;

import org.joml.Matrix4f;
import org.joml.Vector3f;

public abstract class Entity
{
    public Vector3f positionVector;
    public Vector3f rotationVector;
    public Mesh mesh;
    public Camera camera;

    public Entity()
    {
        positionVector = new Vector3f();
        rotationVector = new Vector3f();
    }

    public abstract void render();
    public abstract void update();
}
