package bitmapFonts;

import engine.Camera;
import engine.Mesh;
import engine.Renderer;
import engine.Shader;
import org.joml.Matrix4f;

import java.util.ArrayList;
import java.util.Map;

public class Label
{
    private FontTexture fontTexture;
    private Map<Character, Glyph> map;
    private ArrayList<Mesh> meshList;
    private float meshScale;
    private float width = 0;
    private Matrix4f matrix;
    private float padding;
    private String text;


    public Label(FontTexture fontTexture, float scale)
    {
        this.fontTexture = fontTexture;
        map = fontTexture.getGlyphMap();
        meshList = new ArrayList<Mesh>();
        meshScale = scale;
        matrix = new Matrix4f();
    }

    public Label(String text, FontTexture fontTexture, float scale)
    {
        this.fontTexture = fontTexture;
        this.text = text;
        map = fontTexture.getGlyphMap();
        padding = fontTexture.getPadding();
        meshList = new ArrayList<Mesh>();
        meshScale = scale;
        matrix = new Matrix4f();
        //load the mesh of each character in text into meshList
        loadText(text);
    }

    public void loadText(String text)
    {
        if(!meshList.isEmpty())
            meshList.clear();
        float startX = 0;
        float o = 0;
        for(int i = 0; i < text.length(); i++)
        {
            Glyph glyph = map.get(text.charAt(i));
            float xoffset = glyph.getXoffset();
//            float yoffset = glyph.getYoffset();

            float[] verticies =
                    {
                            (0 + startX + xoffset) * meshScale, (glyph.getHeight())  * meshScale, 0.3f,
                            (glyph.getWidth()  + startX + xoffset) * meshScale, (glyph.getHeight()) * meshScale, 0.3f,
                            (glyph.getWidth()  + startX + xoffset) * meshScale, (0) * meshScale, 0.3f,
                            (0 + startX + xoffset) * meshScale, (0) * meshScale, 0.3f
                    };

            float[] textures =
                    {
                            ((glyph.getX() ) / fontTexture.getTextureWidth()), (glyph.getY()) / fontTexture.getTextureHeight(),
                            (glyph.getX() + glyph.getWidth() ) / fontTexture.getTextureWidth(), (glyph.getY()) / fontTexture.getTextureHeight(),
                            (glyph.getX() + glyph.getWidth() ) / fontTexture.getTextureWidth(), (glyph.getY() + glyph.getHeight()) / fontTexture.getTextureHeight(),
                            ((glyph.getX() ) / fontTexture.getTextureWidth()), (glyph.getY() +  glyph.getHeight()) / fontTexture.getTextureHeight()
                    };
//            System.out.println("Tex Coordinates");
//            System.out.println((glyph.getX() / fontTexture.getTextureWidth()) + " " + ((glyph.getY()) / fontTexture.getTextureHeight()));

            meshList.add(new Mesh(verticies, textures));
            startX += glyph.getXadvance();
            width += glyph.getWidth() * meshScale;
        }
    }

    public void translate(float x, float y, float z)
    {
        matrix.translate(x, y, z);
    }

    public void render(Shader shader, Camera camera)
    {
        shader.bind();
        fontTexture.getFontTexture().bind();

        shader.setUniform("sampler", 0);
        shader.setUniform("projection", camera.getProjectionMatrix());
        shader.setUniform("model", matrix);

        for(Mesh mesh : meshList)
            Renderer.drawMesh(mesh);


        shader.unbind();
    }

    public float getWidth()
    {
        return width;
    }
}
