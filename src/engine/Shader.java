package engine;

import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL20;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.FloatBuffer;

public class Shader
{
//  Creates and manages Shader programs for OpenGL
    private  int program, vertexShaderID, fragmentShaderID;
//  All Shaders are static so they can easily be used, also Shaders don't need to be instanced as it leads to redundancies
    public static Shader backgroundShader, birdShader, pipeShader, textShader;

    public Shader(String filename)
    {
        program = GL20.glCreateProgram();
        vertexShaderID = GL20.glCreateShader(GL20.GL_VERTEX_SHADER);
        GL20.glShaderSource(vertexShaderID, readFile(filename + ".vert"));
        GL20.glCompileShader(vertexShaderID);
        //Compile status of anything other than 1 is an error
        if(GL20.glGetShaderi(vertexShaderID, GL20.GL_COMPILE_STATUS) != 1)
        {
            System.err.println(GL20.glGetShaderInfoLog(vertexShaderID));
            System.exit(1);
        }

        fragmentShaderID = GL20.glCreateShader(GL20.GL_FRAGMENT_SHADER);
        GL20.glShaderSource(fragmentShaderID, readFile(filename + ".frag"));
        GL20.glCompileShader(fragmentShaderID);
        //Compile status of anything other than 1 is an error
        if(GL20.glGetShaderi(fragmentShaderID, GL20.GL_COMPILE_STATUS) != 1)
        {
            System.err.println(GL20.glGetShaderInfoLog(fragmentShaderID));
            System.exit(1);
        }

        GL20.glAttachShader(program, vertexShaderID);
        GL20.glAttachShader(program, fragmentShaderID);

        GL20.glBindAttribLocation(program, 0, "vertices");
        GL20.glBindAttribLocation(program, 1, "textures");

        GL20.glLinkProgram(program);
        if(GL20.glGetProgrami(program, GL20.GL_LINK_STATUS) != 1)
        {
            System.err.println(GL20.glGetProgramInfoLog(program));
            System.exit(1);
        }
        GL20.glValidateProgram(program);
        if(GL20.glGetProgrami(program, GL20.GL_VALIDATE_STATUS) != 1)
        {
            System.err.println(GL20.glGetProgramInfoLog(program));
            System.exit(1);
        }
    }

    public static void loadShaders()
    {
        backgroundShader = new Shader("/shaders/entity");
        birdShader = new Shader("/shaders/bird");
        pipeShader = new Shader("/shaders/pipe");
        textShader = new Shader("/shaders/text");
    }

    public void setUniform(String name, int value)
    {
        int location = GL20.glGetUniformLocation(program, name);
        if(location != -1)
            GL20.glUniform1i(location, value);
    }

    public void setUniform(String name, Matrix4f value)
    {
        int location = GL20.glGetUniformLocation(program, name);
        FloatBuffer buffer = BufferUtils.createFloatBuffer(4 * 4);
        value.get(buffer);
        if(location != -1)
            GL20.glUniformMatrix4fv(location, false, buffer);
    }

    public void setUniform(String name, Vector3f value)
    {
        int location = GL20.glGetUniformLocation(program, name);
        FloatBuffer  buffer = BufferUtils.createFloatBuffer(3);
        value.get(buffer);
        if(location != -1)
            GL20.glUniformMatrix3fv(location, false, buffer);
    }

    public void bind()
    {
        GL20.glUseProgram(program);
    }

    public void delete()
    {
        GL20.glDetachShader(program, vertexShaderID);
        GL20.glDetachShader(program, fragmentShaderID);
        GL20.glDeleteShader(vertexShaderID);
        GL20.glDeleteShader(fragmentShaderID);
        GL20.glDeleteProgram(program);
    }

    public static void deleteAll()
    {
        backgroundShader.delete();
        birdShader.delete();
        pipeShader.delete();
        textShader.delete();
    }

    public void unbind()
    {
        GL20.glUseProgram(0);
    }

    private String readFile(String filename)
    {
        StringBuilder string = new StringBuilder();
        BufferedReader bufferedReader;
        try
        {
            bufferedReader = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream(filename)));
            String line;
            while((line = bufferedReader.readLine()) != null)
            {
                string.append(line);
                string.append("\n");
            }
            bufferedReader.close();
        }catch(Exception ex)
        {
            ex.printStackTrace();
        }
        return string.toString();
    }

}
