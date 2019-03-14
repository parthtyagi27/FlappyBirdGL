package entities;


import core.Main;
import engine.*;
import math.Transformation;
import org.joml.Vector3f;

public class Background extends Entity
{
    public Background(Camera camera)
    {
        super();
        this.camera = camera;
        float[] vertices =
        {
                0, Main.HEIGHT, 0,
                Main.WIDTH, Main.HEIGHT, 0,
                Main.WIDTH, 0, 0,
                0, 0, 0
        };


        mesh = new Mesh(vertices, TextureAtlas.getBackgroundTexture());
        positionVector = new Vector3f();
    }

    @Override
    public void render()
    {
        Shader.backgroundShader.bind();
        TextureAtlas.texture.bind();
        Shader.backgroundShader.setUniform("sampler", 0);
        Shader.backgroundShader.setUniform("projection", camera.getProjectionMatrix());
        Shader.backgroundShader.setUniform("model", Transformation.createTransformation(positionVector, rotationVector));
        Renderer.drawMesh(mesh);
        Shader.backgroundShader.unbind();
    }

    @Override
    public void update()
    {
        positionVector.x -= 2;
    }
}
