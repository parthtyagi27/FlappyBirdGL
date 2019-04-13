package bitmapFonts;

public class Glyph
{
    private char character;
    private float x, y, width, height, xoffset, yoffset, xadvance;

    public Glyph(char c, float x, float y, float width, float height, float xoffset, float yoffset, float xadvance)
    {
        character = c;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.xoffset = xoffset;
        this.yoffset = yoffset;
        this.xadvance = xadvance;
    }

    public float getX()
    {
        return x;
    }

    public float getY()
    {
        return y;
    }

    public float getWidth()
    {
        return width;
    }

    public float getHeight()
    {
        return height;
    }

    public float getXadvance()
    {
        return xadvance;
    }

    public float getXoffset()
    {
        return xoffset;
    }
    public float getYoffset()
    {
        return yoffset;
    }
}
