package entities;

import core.Main;
import engine.*;
import math.Transformation;
import org.joml.Vector3f;
import org.lwjgl.glfw.GLFW;

public class Bird extends Entity
{

    public static final float width = TextureAtlas.birdWidth - 60, height = TextureAtlas.birdHeight - 40;
    public static boolean isAlive = true;

    private static final float maxUpwardsRot = 0.6f, maxDownwardsRot = -60f;
    private static final float rotationStep = 1f;
    private float acceleration = 0.01f;
    private static final float gravityConstant = 0.5f;
    private float rotCount;

    private Vector3f rotationVector;

    public Bird(Camera camera)
    {
        super();
        float[] vertices =
                {
                        0, height, 0.1f,
                        width, height, 0.1f,
                        width, 0, 0.1f,
                        0, 0, 0.1f
                };
        mesh = new Mesh(vertices, TextureAtlas.getBirdTexture());
        this.camera = camera;
        modelMatrix.translate((Main.WIDTH - width)/2, (Main.HEIGHT - height)/2, 0);
        positionVector = new Vector3f((Main.WIDTH - width)/2, (Main.HEIGHT - height)/2, 0);

        rotationVector = new Vector3f();
    }

    @Override
    public void render()
    {
        Shader.birdShader.bind();
        TextureAtlas.texture.bind(0);
        Shader.birdShader.setUniform("sampler", 0);
        Shader.birdShader.setUniform("projection", camera.getProjectionMatrix());
        Shader.birdShader.setUniform("model", Transformation.createTransformation(positionVector, rotationVector));
        Renderer.drawMesh(mesh);
        Shader.birdShader.unbind();
    }

    @Override
    public void update()
    {
        if(isAlive)
        {
            if (Handler.isKeyDown(GLFW.GLFW_KEY_SPACE))
                jump();
            else
            {
                fall();
            }

            if(positionVector.y <= height)
                isAlive = false;
        }
    }

    private void fall()
    {
        positionVector.y -= (gravityConstant + acceleration);
        acceleration += 0.05f;
        if(rotCount >= maxDownwardsRot)
        {
            rotationVector.z += -rotationStep;
            rotCount -= rotationStep;
            modelMatrix.rotateZ(-rotationStep);
        }
        modelMatrix.translate(positionVector);

    }

    private void jump()
    {
        positionVector.y += 0.1f;
        if(rotCount <= maxUpwardsRot)
        {
            modelMatrix.rotate(rotationStep, 0, 0, 1.0f);
            rotationVector.z += rotationStep;
            rotCount += rotationStep;
        }
//        else
//            positionVector.rotateZ(maxUpwardsRot);

//        modelMatrix.rotate(0.1f, 0f, 0f, 1.0f);
//        rotCount += 0.1f;
//        System.out.println(rotCount);
    }
}
