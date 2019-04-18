package core;

import engine.*;
import entities.Bird;
import entities.Level;
import font.FontMesh;
import font.Text;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;


public class Main
{

    public static String state = "menu";
    private static UI ui;
//    private static Label instructionLabel;
//    private static Label scoreLabel;

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
        //Init OpenAL
        loadAudio();
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
//        instructionLabel.render(Shader.textShader, camera);
    }

    private static void update()
    {
        if(!Bird.isAlive)
        {
            state = "over";
        }
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
        }else if(state == "menu")
            bird.hover();

    }

    private static void initGL()
    {
        GLFW.glfwSetKeyCallback(window.getWindowID(), new Input());
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        Shader.loadShaders();
        Handler.loadHandler(window);
        TextureAtlas.loadTextureAtlas("/res/textureAtlas.png");
    }

    private static void init()
    {
        camera = new Camera(WIDTH, HEIGHT);
        level = new Level(camera);
        bird = new Bird(camera);

        Bird.isAlive = true;
        score = 0;

        initGUI();

    }

    private static void initGUI()
    {
        FontMesh fontMesh = new FontMesh("/res/fonts/gameFont.ttf", 64);
        scoreText = new Text(fontMesh);

        scoreText.loadText(score + "");
        scoreText.translate((Main.WIDTH - scoreText.getWidth())/2, Main.HEIGHT - scoreText.getHeight() - 10, 0);
        ui = new UI(Shader.textShader, camera);

        Text instructionLabel = new Text(fontMesh, .5f);
        instructionLabel.loadText("PRESS SPACE BAR TO JUMP");
        instructionLabel.translate((Main.WIDTH - instructionLabel.getWidth())/2, (Main.HEIGHT - instructionLabel.getHeight())/2 + 150, 0);
        ui.addTextLabel("instruction", instructionLabel);

        Text gameOverLabel = new Text(fontMesh, .5f);
        gameOverLabel.loadText("PRESS ENTER TO PLAY AGAIN");
        gameOverLabel.translate((Main.WIDTH - gameOverLabel.getWidth())/2, (Main.HEIGHT - gameOverLabel.getHeight())/2 +50, 0);
        ui.addTextLabel("gameOver", gameOverLabel);

        //Experimental code
//        FontTexture apex24 = new FontTexture("/res/fonts/apex24");
//        FontTexture apex64 = new FontTexture("/res/fonts/apex64");
//
//        instructionLabel = new Label("Press Space to Jump...", apex24, 1f);
//        instructionLabel.translate(25 , 100, 0);
//        //(WIDTH - instructionLabel.getWidth()) / 2
//
//        scoreLabel = new Label(score + "", apex64 , 1.0f);
    }

    private static void loadAudio()
    {
        AudioManager.init();
        AudioManager.loadAudio("/res/audio/score.wav", "score");
        AudioManager.loadAudio("/res/audio/dead.wav", "dead");
        AudioManager.loadAudio("/res/audio/wing.wav", "wing");
    }

    private static void flush()
    {
        Shader.deleteAll();
        window.flush();
        AudioManager.flush();
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
