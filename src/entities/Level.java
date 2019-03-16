package entities;

import core.Main;
import engine.Camera;

import java.util.ArrayList;

public class Level
{
    private Background[] background;
    private Pipe pipe;
    private PipeSet ps;
    private ArrayList<PipeSet> pipes;

    public Level(Camera camera)
    {
        background = new Background[3];
        for(int i = 0; i < background.length; i++)
        {
            background[i] = new Background(camera);
            background[i].positionVector.x = i * Main.WIDTH;
        }

        pipes = new ArrayList<PipeSet>();
        pipes.add(new PipeSet(camera));
    }

    public void render()
    {
        for (Background bg : background)
        {
            bg.render();
        }

        for(PipeSet pipeSet : pipes)
        {
            pipeSet.render();
        }
    }

    public void update()
    {
        if (Bird.isAlive)
        {
            if (background[2].positionVector.x() <= 0)
            {
                background[0].positionVector.x = background[2].positionVector.x() + Main.WIDTH;
                background[1].positionVector.x = background[0].positionVector.x() + Main.WIDTH;

                Background b = background[0];
                background[0] = background[1];
                background[1] = background[2];
                background[2] = b;
            }

            for (Background bg : background)
            {
                bg.update();
            }

            for(PipeSet pipeSet : pipes)
            {
                pipeSet.update();
            }
        }
    }
}
