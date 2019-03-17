package entities;

import core.Main;
import engine.Camera;

import java.util.ArrayList;

public class Level
{
    private Camera camera;
    private Background[] background;
    private ArrayList<PipeSet> pipes;

    private static float deltaPipeDistance = 200f;

    public Level(Camera camera)
    {
        this.camera = camera;
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
            if(pipes.size() <= 3)
            {
                PipeSet pipeSet = new PipeSet(camera);
                pipeSet.setX(pipes.get(pipes.size() - 1).getX() + Pipe.width + deltaPipeDistance);
                pipes.add(pipeSet);
            }

            for(int i = 0; i < pipes.size(); i++)
            {
                if(pipes.get(i).getX() <= -Pipe.width)
                    pipes.remove(i);
                else
                    pipes.get(i).update();
            }


        }
    }
}
