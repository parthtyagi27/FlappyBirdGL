package entities;

import core.Main;
import engine.Camera;

public class Level
{
    private Background[] background;

    public Level(Camera camera)
    {
        background = new Background[3];
        for(int i = 0; i < background.length; i++)
        {
            background[i] = new Background(camera);
            background[i].positionVector.x = -Main.WIDTH + (i * Main.WIDTH);
        }
//        background[0]
    }

    public void render()
    {
        for(int i = 0; i < background.length; i++)
        {
            background[i].render();
        }
    }

    public void update()
    {
        for(int i = 0; i < background.length; i++)
        {
            background[i].update();
        }

    }
}
