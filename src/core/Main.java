package core;

import engine.*;
import entities.Background;
import entities.Bird;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;

public class Main
{
    private static Window window;

    public static final int WIDTH = 500, HEIGHT = 700;
    private static final double fpsCap = 1.0/60.0;
    private static double time, unprocessedTime = 0;

    private static Camera camera;
    private static Background bg;
    private static Bird bird;

    public static void main(String[] args)
    {
        window.setCallBack();
        window = new Window(WIDTH, HEIGHT, "FlappyGL", false);
        window.render();

        GLFW.glfwMakeContextCurrent(window.getWindowID());
        GL.createCapabilities();
        System.out.println("OpenGL Version: " + GL11.glGetString(GL11.GL_VERSION));

        init();

        //Game loop

        time = getTime();
        double frameTime = 0;
        unprocessedTime = 0;
        int frames = 0;

        while (!window.isClosed())
        {
            boolean canRender = false;
            double currentTime = getTime();
            double delta = currentTime - time;
            unprocessedTime += delta;
            frameTime += delta;
            time = currentTime;

            while (unprocessedTime >= fpsCap)
            {
                unprocessedTime -= fpsCap;
                canRender = true;
                window.update();
                update();

                if(frameTime >= 1.0)
                {
                    frameTime = 0;
                    System.out.println("FPS = " + frames);
                    frames = 0;
                }
            }

            if(canRender)
            {
                GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);
                render();
                window.swapBuffers();
                frames++;
            }
        }

        flush();
    }

    private static void render()
    {
        bg.render();
        bird.render();
    }

    private static void update()
    {
        bird.update();
    }

    private static void init()
    {
        GLFW.glfwSetKeyCallback(window.getWindowID(), new Input());
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        Shader.loadShaders();
        Handler.loadHandler(window);
        TextureAtlas.loadTextureAtlas("/res/test.png");

        camera = new Camera(WIDTH, HEIGHT);
        bg = new Background(camera);
        bird = new Bird(camera);
    }

    private  static void flush()
    {
        Shader.deleteAll();
        window.flush();
        GL.destroy();
        GLFW.glfwTerminate();
        System.out.println("Disposed Resources");
        System.exit(0);
    }

    private static double getTime()
    {
        return (double) System.nanoTime() / (double) 1000000000L;
    }


}
