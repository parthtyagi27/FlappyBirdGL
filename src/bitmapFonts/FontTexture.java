package bitmapFonts;

import engine.Texture;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

public class FontTexture
{
    private Texture fontTexture;
    private float maxHeight = 0;

    private Map<Character, Glyph> glyphMap;
    private static String[] limiters = {"id=", "x=", "y=", "width=", "height=", "xoffset=", "yoffset=", "xadvance="};
    private float padding = 0;

    public FontTexture(String path)
    {
        fontTexture = new Texture(path + ".png");
        glyphMap = new HashMap<Character, Glyph>();
        parseFontData(path + ".fnt");
    }

    private void parseFontData(String path)
    {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream(path)));
        try
        {
            String firstLine = bufferedReader.readLine();
            String[] firstSplit = firstLine.split("padding=");
            padding = Float.parseFloat(firstSplit[1].charAt(0) + "");
            System.out.println("Padding = " + padding);
            bufferedReader.readLine();
            bufferedReader.readLine();
            String line = bufferedReader.readLine();

            while(line != null)
            {
                line = bufferedReader.readLine();

                if(line.startsWith("kerning"))
                    break;
                int[] values = new int[limiters.length];
                String[] split = line.split(" ");
                for(String s : split)
                {
                    for(int i = 0; i < limiters.length; i++)
                    {
                        String l = limiters[i];
                        if(s.contains(l))
                        {
                            String[] pair = s.split(l);
                            values[i] = Integer.parseInt(pair[1]);
//                            System.out.println(l + values[i]);
                        }
                    }
                }
                maxHeight = Math.max(maxHeight, (float) values[4]);
                Glyph glyph = new Glyph((char)values[0], (float) values[1], (float) values[2], (float) values[3], (float) values[4], (float) values[5], (float) values[6], (float) values[7]);
                glyphMap.put((char) values[0], glyph);
            }
            bufferedReader.close();
        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public Map<Character, Glyph> getGlyphMap()
    {
        return glyphMap;
    }

    public float getTextureWidth()
    {
        return fontTexture.getWidth();
    }

    public float getTextureHeight()
    {
        return fontTexture.getHeight();
    }

    public Texture getFontTexture()
    {
        return fontTexture;
    }

    public float getMaxHeight()
    {
        return maxHeight;
    }

    public float getPadding()
    {
        return padding;
    }
}
