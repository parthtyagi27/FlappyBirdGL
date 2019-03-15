package entities;

import engine.*;
import math.Transformation;
import org.joml.Vector3f;

public class Pipe extends Entity
{

    public static final float width = TextureAtlas.pipeWidth, height = 300f;

    public Pipe(Camera camera)
    {
        super();
        this.camera = camera;

        positionVector = new Vector3f(100, 100, 0.2f);
        rotationVector = new Vector3f();

        float[] verticies =
                {
                        0, height, 0f,
                        width, height, 0f,
                        width, 0, 0f,
                        0, 0, 0f
                };

        mesh = new Mesh(verticies, TextureAtlas.getPipeTexture());
    }

    @Override
    public void render()
    {
        Shader.pipeShader.bind();
        TextureAtlas.texture.bind();
        Shader.pipeShader.setUniform("sampler", 0);
        Shader.pipeShader.setUniform("projection", camera.getProjectionMatrix());
        Shader.pipeShader.setUniform("model", Transformation.createTransformation(positionVector, rotationVector));
        Renderer.drawMesh(mesh);
        Shader.pipeShader.unbind();
    }

    @Override
    public void update()
    {

    }
}
