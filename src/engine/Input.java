package engine;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWKeyCallback;

public class Input extends GLFWKeyCallback
{
    //Deprecated method of getting input for the program, I should probably delete this...

//    @Override
    public String getSignature()
    {
        return null;
    }

//    @Override
    public void callback(long args)
    {

    }

//    @Override
    public void close()
    {

    }

    public static boolean[] keys = new boolean[65536];

//    @Override
    public void invoke(long window, int key, int scancode, int action, int mods)
    {
        keys[key] = action != GLFW.GLFW_PRESS;
    }

    public static boolean isKeyDown(int keycode)
    {
        return keys[keycode];
    }


}
