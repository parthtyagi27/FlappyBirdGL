package engine;

import org.lwjgl.glfw.*;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;

public class Window
{
    private long windowID;
    private int width, height;
    private String title;
    private final boolean resizable;

    public Window(int width, int height, String title, boolean resizable)
    {
        this.height = height;
        this.width = width;
        this.title = title;
        this.resizable = resizable;
    }

    public static void setCallBack()
    {
        GLFW.glfwSetErrorCallback(new GLFWErrorCallbackI()
        {

            @Override
            public String getSignature()
            {
                return null;
            }

            @Override
            public long address()
            {
                return 0;
            }

            @Override
            public void callback(long args)
            {

            }

            @Override
            public void invoke(int error, long description)
            {
                throw new IllegalStateException(GLFWErrorCallback.getDescription(description));
            }
        });
    }

    public void createCapabilities()
    {
        GL.createCapabilities();
    }

    public void render()
    {
        if(!GLFW.glfwInit())
        {
            System.err.println("Error: Could not init GLFW");
            System.exit(-1);
        }
        setResizable(false);
        windowID = GLFW.glfwCreateWindow(width, height, title, 0, 0);

        if(windowID == 0)
        {
            System.err.println("Error: Could not init window");
            System.exit(-1);
        }

        GLFWVidMode videoMode = GLFW.glfwGetVideoMode(GLFW.glfwGetPrimaryMonitor());
        GLFW.glfwSetWindowPos(windowID, (videoMode.width() - width)/2, (videoMode.height() - height)/2);
    }

    public void setInput(Input input)
    {
//        GLFW.glfwSetKeyCallback(windowID, )
    }

    public boolean isClosed()
    {
        return GLFW.glfwWindowShouldClose(windowID);
    }

    public void update()
    {
        GLFW.glfwPollEvents();
    }

    protected void setResizable(boolean resizable)
    {
        if(!resizable)
            GLFW.glfwWindowHint(GLFW.GLFW_RESIZABLE, GL11.GL_FALSE);
    }

    public void swapBuffers()
    {
        GLFW.glfwSwapBuffers(windowID);
    }

    public void flush()
    {
        GLFW.glfwDestroyWindow(windowID);
    }

    public long getWindowID()
    {
        return windowID;
    }

}
