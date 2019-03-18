package entities;

import core.Main;
import engine.Camera;
import javafx.scene.shape.TriangleMesh;

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

    public void update(Bird bird)
    {
        if (Bird.isAlive)
        {
//            System.out.println("Bird pos = " + (bird.positionVector.x() + Bird.width/2)+ " Pipe 1 pos = " + array[0].getX());
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
            {
                pipeSet.update();
                if(checkCollision(bird, pipeSet))
                {
                    System.out.println("Collision!");
                    Bird.isAlive = false;
                }
                if(pipeSet.getX() + Pipe.width/2 <= bird.positionVector.x() && pipeSet.isPassedBird() == false)
                {
                    pipeSet.setPassedBird(true);
                    Main.score++;
                }
            }

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

    private boolean checkCollision(Bird bird, PipeSet pipeSet)
    {
        // bird.x = 250
//        if(pipeSet.getX() >= bird.positionVector.x() + Bird.width/2 && pipeSet.getX() <= bird.positionVector.x() + Pipe.width - Bird.width/2)
//        if(pipeSet.getX() <= (250 + Bird.width/2) && (pipeSet.getX() + Pipe.width) >= (250 + Bird.width/2))
        if(pipeSet.getX() + Pipe.width < 250 - Bird.width/2)
            return false;
        if(pipeSet.getX() <= (250 + Bird.width/2) && (pipeSet.getX() + Pipe.width) >= (250 - Bird.width/2))
        {
            //ToDo: Check if bird collides with the walls of the pipes
            if(bird.positionVector.y() + Bird.height/2 >= Main.HEIGHT - pipeSet.getTopPipe().getHeight())
            {
                System.out.println("Hit Top pipe, bird = " + bird.positionVector.y + " pipe height = " + pipeSet.getTopPipe().getHeight());
                return true;
            }else if (bird.positionVector.y() + Bird.height/2 >= 0 && bird.positionVector.y() - Bird.height/2 <= pipeSet.getBottomPipe().getHeight() + pipeSet.getBottomPipe().positionVector.y())
            {
                System.out.println("Hit Bot pipe, bird = " + bird.positionVector.y + " pipe height = " + (pipeSet.getBottomPipe().getHeight() + pipeSet.getBottomPipe().positionVector.y()));
                return true;
            }

//            System.out.println("Bottom Pipe Pos = " + pipeSet.getBottomPipe().getHeight() + "  bird pos = " + bird.positionVector.y());
        }
        return false;
    }
}
