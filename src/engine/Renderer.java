package engine;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;

import java.util.List;

public class Renderer
{
    public static void drawMesh(Mesh mesh)
    {
        GL20.glEnableVertexAttribArray(0);

        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, mesh.getVertexID());
        GL20.glVertexAttribPointer(0, 3, GL11.GL_FLOAT, false, 0, 0);

        if(mesh.isUsingTexture())
        {
            GL20.glEnableVertexAttribArray(1);
            GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, mesh.getTextureID());
            GL20.glVertexAttribPointer(1, 2, GL11.GL_FLOAT, false, 0, 0);
        }

        GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, mesh.getIndexID());

        GL15.glDrawElements(GL15.GL_TRIANGLES, mesh.getVertexCount(), GL15.GL_UNSIGNED_INT, 0);
        GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, 0);
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);

        GL20.glDisableVertexAttribArray(0);
        if(mesh.isUsingTexture())
            GL20.glDisableVertexAttribArray(1);
    }

    public static void drawBatch(List<Mesh> batch)
    {

    }

    public static void drawRectangle(float x1, float x2, float y1, float y2)
    {
        GL11.glRectf(x1, y1, x2, y2);
    }
}
