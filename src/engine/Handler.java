package engine;

import org.lwjgl.glfw.GLFW;

public class Handler
{
    //Allows for key input in the program
    private static Window window;

    public static void loadHandler(Window win)
    {
        window = win;
    }

    public static boolean isKeyDown(int key)
    {
        return (GLFW.glfwGetKey(window.getWindowID(), key) == GLFW.GLFW_PRESS);
    }
}
