package core;

import engine.*;
import entities.Bird;
import entities.Level;
import experimental.FontTexture;
import experimental.Label;
import font.FontMesh;
import font.Text;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;


public class Main
{

    public static String state = "menu";
    private static UI ui;
    private static Label label;

    //Create window object
    private static Window window;
    //Window and game loop related variables
    public static final int WIDTH = 500, HEIGHT = 700;
    private static final double fpsCap = 1.0/60.0; //Sets the amount of update per seconds (1 sec / updates per second)
    private static double time, unprocessedTime = 0; //Variables to establish the game loop

    //Game objects, declared static as there's only one instance of the main class and because it works well in the main method
    private static Camera camera;
    private static Bird bird;
    private static Level level;
    //Players score = the number of pipes the bird has maneuvered through
    public static int score = 0;
    //GUI related variables
    private static FontMesh scoreFontMesh;
    private static Text scoreText;

    public static void main(String[] args)
    {
        //Init and render the window object
        window.setCallBack();
        window = new Window(WIDTH, HEIGHT, "FlappyGL", false);
        window.setIcon("/res/icon.png");
        window.render();
        //Init OpenGL
        GLFW.glfwMakeContextCurrent(window.getWindowID());
        GL.createCapabilities();
        System.out.println("OpenGL Version: " + GL11.glGetString(GL11.GL_VERSION));
        //Init GL code
        initGL();
        //Init game objects
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
                    //Reset FPS counter (This part gets executed every second)
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
        //Dispose of resources
        flush();
    }

    private static void render()
    {
        level.render();
        bird.render();
        if(state == "game")
        {
            scoreText.render(Shader.textShader, camera);
        }else if(state == "menu")
        {
            ui.renderLabel("instruction");
            if(Handler.isKeyDown(GLFW.GLFW_KEY_SPACE))
            {
                state = "game";
//                ui.removeTextLabel("instruction");
                ui.addTextLabel("score", scoreText);
            }
        }else if(state == "over")
        {

            ui.renderLabel("gameOver");
        }
//        label.render(Shader.textShader, camera);
    }

    private static void update()
    {
        if(Bird.isAlive == false)
            state = "over";
        if(state == "game")
        {
            level.update(bird);
            bird.update();
            scoreText.loadText(score + "");
        }else if(state == "over")
        {
            if(Handler.isKeyDown(GLFW.GLFW_KEY_ENTER))
            {
                init();
                state = "menu";
            }
        }
    }

    private static void initGL()
    {
        GLFW.glfwSetKeyCallback(window.getWindowID(), new Input());
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        Shader.loadShaders();
        Handler.loadHandler(window);
        TextureAtlas.loadTextureAtlas("/res/test.png");
    }

    private static void init()
    {
        camera = new Camera(WIDTH, HEIGHT);
        level = new Level(camera);
        bird = new Bird(camera);

        Bird.isAlive = true;
        score = 0;

        initGUI();

        //Experimental code
//        FontTexture ft = new FontTexture("/res/font.png");
//        ft.parseFontData("/res/test.fnt");
//
//        label = new Label(ft, 0.25f);
//        label.loadText("PRESS SPACE BAR TO JUMP");
//        label.translate(100, 100, 0);
    }

    private static void initGUI()
    {
        scoreFontMesh = new FontMesh("/res/apex.otf", 48);
        scoreText = new Text(scoreFontMesh);

        scoreText.loadText(score + "");
        scoreText.translate((Main.WIDTH - scoreText.getWidth())/2, Main.HEIGHT - scoreText.getHeight() - 10, 0);
        ui = new UI(Shader.textShader, camera);

        Text instructionLabel = new Text(scoreFontMesh, 0.5f);
        instructionLabel.loadText("Press space bar to jump");
        instructionLabel.translate((Main.WIDTH - instructionLabel.getWidth())/2, (Main.HEIGHT - instructionLabel.getHeight())/2 +50, 0);
        ui.addTextLabel("instruction", instructionLabel);

        Text gameOverLabel = new Text(scoreFontMesh, 0.5f);
        gameOverLabel.loadText("Press enter to play again");
        gameOverLabel.translate((Main.WIDTH - gameOverLabel.getWidth())/2, (Main.HEIGHT - gameOverLabel.getHeight())/2 +50, 0);
        ui.addTextLabel("gameOver", gameOverLabel);
    }

    private static void flush()
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
