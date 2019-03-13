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

    private static final float maxUpwardsRot = 30f, maxDownwardsRot = -60f;
    private static final float rotationStep = 1f;
    private float downwardsAcceleration = 0.01f, upwardsAcceleration = 0.5f;
    private static final float gravityConstant = 0.5f;
    private float targetY;
    private float angle;


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
        positionVector = new Vector3f((Main.WIDTH - width)/2, (Main.HEIGHT - height)/2, 0);
        rotationVector = new Vector3f();

        angle = (float) Math.toDegrees(Math.asin((float) (height/(Math.sqrt(Math.pow(width, 2) + Math.pow(height, 2))))));
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
            {
                targetY = positionVector.y + 75;
                jump();
            }
            else
                fall();

            if(positionVector.y < Math.cos(Math.toRadians(rotationVector.z())))
                isAlive = false;

//            System.out.println("Bird Position = " + positionVector.y + " angle = " + rotationVector.z());
//            System.out.println(Transformation.createTransformation(positionVector, rotationVector).m31());
        }
    }

    private void fall()
    {
        upwardsAcceleration = 0.5f;
        positionVector.y -= (gravityConstant + downwardsAcceleration);
        downwardsAcceleration += 0.05f;
        if(rotationVector.z() >= maxDownwardsRot)
        {
            rotationVector.z += -(rotationStep * 1.25);
        }
        angle += rotationStep;

    }

    private void jump()
    {
        if(positionVector.y <= targetY)
        {
            positionVector.y += upwardsAcceleration;
            if(upwardsAcceleration <= 4f)
                upwardsAcceleration += 0.5f;
            if (rotationVector.z() <= maxUpwardsRot)
            {
                rotationVector.z += (rotationStep * 4);
            }
        }
    }
}
