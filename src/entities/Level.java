package entities;

import core.Main;
import engine.Camera;

import java.util.ArrayList;

public class Level
{
    private Camera camera;
    private Background[] background;
//    public static ArrayList<PipeSet> pipes;
    public static PipeSet[] array;

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

//        pipes = new ArrayList<PipeSet>();
//        pipes.add(new PipeSet(camera));
        array = new PipeSet[4];
        for(int i = 0; i < array.length; i++)
        {
            PipeSet pipeSet = new PipeSet(camera);
            if(i != 0)
                pipeSet.setX(array[i - 1].getX() + Pipe.width + deltaPipeDistance);

            array[i] = pipeSet;
        }
    }

    public void render()
    {
        for (Background bg : background)
        {
            bg.render();
        }

//        for(PipeSet pipeSet : pipes)
//        {
//            pipeSet.render();
//        }

        for(PipeSet pipeSet : array)
            pipeSet.render();
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
//            if(pipes.size() <= 3)
//            {
//                PipeSet pipeSet = new PipeSet(camera);
//                pipeSet.setX(Math.abs(pipes.get(pipes.size() - 1).getX()) + Pipe.width + deltaPipeDistance);
//                pipes.add(pipeSet);
//            }
//
//            for(int i = 0; i < pipes.size(); i++)
//            {
//                if(pipes.get(i).getX() <= -(Pipe.width + 10f))
//                    pipes.remove(i);
//                else
//                    pipes.get(i).update();
//            }
            for(PipeSet pipeSet : array)
                pipeSet.update();

            if(array[0].getX() <= -Pipe.width)
            {
                for(int i = 0; i < array.length; i++)
                {
                    if(i < array.length - 1)
                    {
                        array[i] = array[i + 1];
                    }else
                    {
                        PipeSet pipeSet = new PipeSet(camera);
                        pipeSet.setX(array[i - 1].getX() + Pipe.width + deltaPipeDistance);
                        array[i] = pipeSet;
                    }
                }
            }



        }
    }
}
