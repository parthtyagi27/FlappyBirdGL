package entities;

import core.Main;
import engine.Audio;
import engine.AudioManager;
import engine.Camera;

public class Level
{
//  Stores the background and PipeSet array which is constantly moving
    private Camera camera;
    private Background[] background;
    public static PipeSet[] pipes;
    private Audio audio;

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

        pipes = new PipeSet[4];
//      Create pipes and set them a specific x distance away from each other (deltaPipeDistance)
        for(int i = 0; i < pipes.length; i++)
        {
            PipeSet pipeSet = new PipeSet(camera);
            if(i != 0)
                pipeSet.setX(pipes[i - 1].getX() + Pipe.width + deltaPipeDistance);

            pipes[i] = pipeSet;
        }

        audio = new Audio("/res/audio/score.wav");
    }

    public void render()
    {
//  Update all background objects and PipeSet objects
        for (Background bg : background)
        {
            bg.render();
        }

        for(PipeSet pipeSet : pipes)
            pipeSet.render();
    }

    public void update(Bird bird)
    {
        if (Bird.isAlive)
        {
            //Recalibrate the background array if the background is "running out"
//          background[2] is the last background object in the array
            if (background[2].positionVector.x() <= 0)
            {
                //translate the other backgrounds so the background doesn't bleed
                background[0].positionVector.x = background[2].positionVector.x() + Main.WIDTH;
                background[1].positionVector.x = background[0].positionVector.x() + Main.WIDTH;
                //restructure array so background objects are in order (0, 1, 2)
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
                if(checkCollision(bird, pipeSet))
                {
                    System.out.println("Collision!");
                    Bird.isAlive = false;
                }
                if(pipeSet.getX() + Pipe.width/2 <= bird.positionVector.x() && !pipeSet.isPassedBird())
                {
//                  If bird passes through the pipe w/o collisions then award a point and make sure the pipeSet is flagged as behind the bird
                    pipeSet.setPassedBird(true);
//                    audio.play();
                    AudioManager.play(AudioManager.loadAudio("/res/audio/score.wav"));
                    Main.score++;
                }
            }
//          Check if the left most pipe (pipe[0]) is past the screen i.e. need to generate more pipes
            if(pipes[0].getX() <= -Pipe.width)
            {
                //iterate through the pipe array and shift the index of each pipeSet one to the right(+1) except the last pipeSet object (pipeset[length - 1])
                for(int i = 0; i < pipes.length; i++)
                {
                    if(i < pipes.length - 1)
                    {
                        pipes[i] = pipes[i + 1];
                    }else
                    {
                        //create a new pipeset object for the last pipe which is currently invisible
                        PipeSet pipeSet = new PipeSet(camera);
                        pipeSet.setX(pipes[i - 1].getX() + Pipe.width + deltaPipeDistance);
                        pipes[i] = pipeSet;
                    }
                }
            }

        }
    }

    private boolean checkCollision(Bird bird, PipeSet pipeSet)
    {
        // bird.x = 250, the bird's x coordinate never changes
        if(pipeSet.getX() + Pipe.width < 250 - Bird.width/2)
            return false;
        if(pipeSet.getX() <= (250 + Bird.width/2) && (pipeSet.getX() + Pipe.width) >= (250 - Bird.width/2))
        {
            if(bird.positionVector.y() + Bird.height/4 >= Main.HEIGHT - pipeSet.getTopPipe().getHeight())
            {
                System.out.println("Hit Top pipe, bird = " + bird.positionVector.y + " pipe height = " + pipeSet.getTopPipe().getHeight());
                return true;
            }else if (bird.positionVector.y() + Bird.height/4 >= 0 && bird.positionVector.y() - Bird.height/4 <= pipeSet.getBottomPipe().getHeight() + pipeSet.getBottomPipe().positionVector.y())
            {
                System.out.println("Hit Bottom pipe, bird = " + bird.positionVector.y + " pipe height = " + (pipeSet.getBottomPipe().getHeight() + pipeSet.getBottomPipe().positionVector.y()));
                return true;
            }
        }
        return false;
    }

}
