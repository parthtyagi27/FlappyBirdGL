package entities;

import engine.*;
import math.Transformation;
import org.joml.Vector3f;

public class Pipe extends Entity
{

    public static final float width = TextureAtlas.pipeWidth + 25;
    private float height;
    private boolean isFlipped;

    public Pipe(Camera camera, boolean isFlipped, float height)
    {
        super();
        this.camera = camera;
        this.height = height;

        positionVector = new Vector3f();
        rotationVector = new Vector3f();

        float[] verticies =
                {
                        0, height, 0f,
                        width, height, 0f,
                        width, 0, 0f,
                        0, 0, 0f
                };

        mesh = new Mesh(verticies, TextureAtlas.getPipeTexture(height));
        this.isFlipped = isFlipped;

    }

    public Pipe(Camera camera, boolean isFlipped)
    {
        super();
        this.camera = camera;

        positionVector = new Vector3f();
        rotationVector = new Vector3f();

        float[] verticies =
                {
                        0, height, 0f,
                        width, height, 0f,
                        width, 0, 0f,
                        0, 0, 0f
                };

        mesh = new Mesh(verticies, TextureAtlas.getPipeTexture());
        height = TextureAtlas.pipeHeight;
        this.isFlipped = isFlipped;
    }

    public float getHeight()
    {
        return height;
    }

    @Override
    public void render()
    {
        Shader.pipeShader.bind();
        TextureAtlas.texture.bind();
        Shader.pipeShader.setUniform("sampler", 0);
        Shader.pipeShader.setUniform("projection", camera.getProjectionMatrix());
        if(isFlipped)
            Shader.pipeShader.setUniform("model", Transformation.createTransformation(positionVector, rotationVector).reflect(0, 1, 0, 0));
        else
            Shader.pipeShader.setUniform("model", Transformation.createTransformation(positionVector, rotationVector));
        Renderer.drawMesh(mesh);
        Shader.pipeShader.unbind();
    }

    @Override
    public void update()
    {
        positionVector.x -= (Background.backgroundMovement + 0.5f);
    }
}
