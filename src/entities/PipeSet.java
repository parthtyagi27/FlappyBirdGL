package entities;

import core.Main;
import engine.Camera;
import engine.Shader;
import engine.TextureAtlas;

import java.util.Random;

public class PipeSet
{
    private static Random random = new Random();
    private Pipe pipe1, pipe2;
    public static final float deltaPipeHeight = 150f;
    private boolean passedBird = false;

    public PipeSet(Camera camera)
    {
        pipe1 = new Pipe(camera, false, random.nextInt(Main.HEIGHT - 200) + 100);
        pipe2 = new Pipe(camera, true, Main.HEIGHT - pipe1.getHeight());

        pipe1.positionVector.x = Main.WIDTH;
        pipe2.positionVector.x = Main.WIDTH;

        pipe1.positionVector.y  =  0 - deltaPipeHeight;
        pipe2.positionVector.y =   Main.HEIGHT;
        System.out.println("Pipe 1 Height = " + pipe1.getHeight() + ", position = " + pipe1.positionVector.y());
        System.out.println("Pipe 2 Height = " + pipe2.getHeight() + ", position = " + pipe2.positionVector.y());
    }

    public void render()
    {
        pipe1.render();
        pipe2.render();
    }

    public void update()
    {
//        System.out.println("Pipe 1 = " + pipe1.positionVector.y + " Pipe 2 = " + pipe2.positionVector.y);
        pipe1.update();
        pipe2.update();
    }

    public void setPassedBird(boolean value)
    {
        passedBird = value;
    }

    public boolean isPassedBird()
    {
        return passedBird;
    }


    public float getX()
    {
        return pipe1.positionVector.x();
    }

    public void setX(float x)
    {
        pipe1.positionVector.x = x;
        pipe2.positionVector.x = x;
    }
}
