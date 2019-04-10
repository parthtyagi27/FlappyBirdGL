package experimental;

import engine.Camera;
import engine.Mesh;
import engine.Renderer;
import engine.Shader;
import math.Transformation;
import org.joml.Matrix4f;
import org.joml.Vector3f;

import javax.swing.event.TableColumnModelListener;
import java.util.ArrayList;
import java.util.Map;

public class Label
{
    private FontTexture fontTexture;
    private Map<Character, Glyph> map;
    private ArrayList<Mesh> meshList;
    private float meshScale = 1;
    private float width = 0;
    private Matrix4f matrix;
    private String text;

    public float x, y, z;

    public Label(FontTexture fontTexture, float scale)
    {
        this.fontTexture = fontTexture;
        map = fontTexture.getGlyphMap();
        meshList = new ArrayList<Mesh>();
        meshScale = scale;
        matrix = new Matrix4f();
    }

    public void loadText(String text)
    {
        this.text = text;
        if(!meshList.isEmpty())
            meshList.clear();
        float startX = 0;
        for(int i = 0; i < text.length(); i++)
        {
            Glyph glyph = map.get(text.charAt(i));
            startX += glyph.getXoffset() - 5;
            float[] verticies =
                    {
                            (0 + startX) * meshScale, (glyph.getHeight())  * meshScale, 0.3f,
                            (glyph.getWidth() + startX) * meshScale, (glyph.getHeight()) * meshScale, 0.3f,
                            (glyph.getWidth() + startX) * meshScale, 0, 0.3f,
                            (0 + startX) * meshScale, 0, 0.3f
                    };

            float[] textures =
                    {
                            (glyph.getX() / fontTexture.getTextureWidth()), (glyph.getY()) / fontTexture.getTextureHeight(),
                            (glyph.getX() + glyph.getWidth()) / fontTexture.getTextureWidth(), (glyph.getY()) / fontTexture.getTextureHeight(),
                            (glyph.getX() + glyph.getWidth()) / fontTexture.getTextureWidth(), (glyph.getY() + glyph.getHeight()) / fontTexture.getTextureHeight(),
                            (glyph.getX() / fontTexture.getTextureWidth()), (glyph.getY() +  glyph.getHeight()) / fontTexture.getTextureHeight()
                    };

            meshList.add(new Mesh(verticies, textures));
            startX += glyph.getXadvance();
            width += glyph.getWidth() * meshScale;
        }
    }

    public void translate(float x, float y, float z)
    {
        matrix.translate(x, y, z);
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public void render(Shader shader, Camera camera)
    {
        shader.bind();
        fontTexture.getFontTexture().bind();

        shader.setUniform("sampler", 0);
        shader.setUniform("projection", camera.getProjectionMatrix());
        shader.setUniform("model", matrix);

        for(int i = 0; i < meshList.size(); i++)
        {
            Mesh m = meshList.get(i);
            Renderer.drawMesh(m);
        }
        shader.unbind();
    }
}
