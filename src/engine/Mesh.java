package engine;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL15;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

//Mesh object, contains all vertex data for each entity

public class Mesh
{
    private int vertexCount, vertexID, textureID, indexID;
    private final boolean usingTexture;

    private int[] indicies = new int[]{
            0, 1, 2,
            2, 3, 0
    };

    public Mesh(float[] verticies)
    {
        this.usingTexture = false;

        vertexCount = verticies.length / 3;
        vertexID = GL15.glGenBuffers();
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vertexID);
        GL15.glBufferData(GL15.GL_ARRAY_BUFFER, createFloatBuffer(verticies), GL15.GL_STATIC_DRAW);

        indexID = GL15.glGenBuffers();
        IntBuffer buffer = BufferUtils.createIntBuffer(indicies.length);
        buffer.put(indicies);
        buffer.flip();

        GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, indexID);
        GL15.glBufferData(GL15.GL_ELEMENT_ARRAY_BUFFER, buffer, GL15.GL_STATIC_DRAW);

        GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, 0);
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
    }


    public Mesh(float[] verticies, float[] textures)
    {
        this.usingTexture = true;
        vertexCount = indicies.length;

        vertexID = GL15.glGenBuffers();

        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vertexID);
        GL15.glBufferData(GL15.GL_ARRAY_BUFFER, createFloatBuffer(verticies), GL15.GL_STATIC_DRAW);

        //texture id

        textureID = GL15.glGenBuffers();
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, textureID);
        GL15.glBufferData(GL15.GL_ARRAY_BUFFER, createFloatBuffer(textures), GL15.GL_STATIC_DRAW);

        // generate indicies id

        indexID = GL15.glGenBuffers();

        IntBuffer buffer = BufferUtils.createIntBuffer(indicies.length);
        buffer.put(indicies);
        buffer.flip();

        GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, indexID);
        GL15.glBufferData(GL15.GL_ELEMENT_ARRAY_BUFFER, buffer, GL15.GL_STATIC_DRAW);

        GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, 0);
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
    }

    private FloatBuffer createFloatBuffer(float[] data)
    {
        FloatBuffer buffer = BufferUtils.createFloatBuffer(data.length);
        buffer.put(data);
        buffer.flip();
        return buffer;
    }

    public int getVertexID()
    {
        return vertexID;
    }

    public int getTextureID()
    {
        return textureID;
    }

    public int getIndexID()
    {
        return indexID;
    }

    public int getVertexCount()
    {
        return vertexCount;
    }

    public boolean isUsingTexture()
    {
        return usingTexture;
    }
}
