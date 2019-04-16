package entities;

import core.Main;
import engine.*;
import math.Transformation;
import org.joml.Vector3f;
import org.lwjgl.glfw.GLFW;

import java.util.Random;


public class Bird extends Entity
{
//  Encapsulates the Bird object
    public static final float width = TextureAtlas.birdWidth - 60, height = TextureAtlas.birdHeight - 40;
    public static boolean isAlive = true;

    private static final float maxUpwardsRot = 30f, maxDownwardsRot = -60f;
    private static final float rotationStep = 1.5f;
    private float downwardsAcceleration = 0.05f, upwardsAcceleration = 0.5f;
    private static final float gravityConstant = 0.25f;
    private float targetY;
    private float hoverMax = (float) Main.HEIGHT /2f + 10f, hoverMin = (float) Main.HEIGHT/2f - 10f, hoverSpeed = .75f;
    private boolean hoveringUp;

    public Bird(Camera camera)
    {
        super();
        float[] vertices =
                {
//                        Old Vertices
//                        0, height, 0.1f,
//                        width, height, 0.1f,
//                        width, 0, 0.1f,
//                        0, 0, 0.1f
//              New vertices which allow the bird to be rotated about its center rather than the lower left corner (realistic rotation)
                        -width/2, height/2, 0.1f,
                        width/2, height/2, 0.1f,
                        width/2, -height/2, 0.1f,
                        -width/2, -height/2, 0.1f
                };
        mesh = new Mesh(vertices, TextureAtlas.getBirdTexture());
        this.camera = camera;
        positionVector = new Vector3f((Main.WIDTH - width)/2 + width/2, (Main.HEIGHT - height)/2, 0);
        rotationVector = new Vector3f();
        hoveringUp = new Random().nextBoolean();
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
//  Only update if the bird is alive to save resources
        if(isAlive)
        {
            if (Handler.isKeyDown(GLFW.GLFW_KEY_SPACE))
            {
                targetY = positionVector.y + 75;
                jump();
            }
            else
                fall();

            if(positionVector.y() + height <= height*2 + 0.5 * Math.sin(Math.toRadians(rotationVector.z())) * width)
            {
                isAlive = false;
                AudioManager.play("dead");
            }
        }
    }

    private void fall()
    {
        upwardsAcceleration = 0.5f;
        positionVector.y -= (gravityConstant + downwardsAcceleration);
            downwardsAcceleration += 0.2f;
        if(rotationVector.z() >= maxDownwardsRot)
        {
            rotationVector.z += -(rotationStep * downwardsAcceleration);
        }

    }

    private void jump()
    {
        downwardsAcceleration = 0.05f;
        if(positionVector.y <= targetY)
        {
            positionVector.y += upwardsAcceleration;
            if(upwardsAcceleration <= 8f)
                upwardsAcceleration += 0.5f;
            if (rotationVector.z() <= maxUpwardsRot)
            {
                rotationVector.z += (rotationStep * upwardsAcceleration);
            }
        }
    }

    public void hover()
    {
        if(hoveringUp)
        {
            //Bird hovers above
            if(positionVector.y() < hoverMax)
                positionVector.y += hoverSpeed;
            else
                hoveringUp = false;
        }

        if(!hoveringUp)
        {
            //Bird hovers below
            if(positionVector.y() > hoverMin)
                positionVector.y -= hoverSpeed;
            else
                hoveringUp = true;
        }


    }

}
