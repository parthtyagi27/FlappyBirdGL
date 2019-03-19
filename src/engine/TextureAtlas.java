package engine;

public class TextureAtlas
{
//  Allows for objects to extract texture coordinates from a single texture atlas (more efficient)
    public static Texture texture;

    public static final float atlasWidth = 512f, atlasHeight = 512f;
    public static float bgWidth = 284f, bgHeight = 512f, bgStartX = 0;
    public static float birdWidth = 125f, birdHeight = 88f, birdStartX = 336f, birdStartY = 42f;
    public static float pipeWidth = 52f, pipeHeight = 320f, pipeStartX = 372f, pipeStartY = 192f;


    public static void loadTextureAtlas(String file)
    {
        texture = new Texture(file);
    }

    public Texture getTexture()
    {
        return texture;
    }

    public static float[] getPipeTexture()
    {
        return new float[]
                {
                        ((pipeStartX)/atlasWidth), ((pipeStartY)/atlasHeight),
                        ((pipeStartX + pipeWidth)/atlasWidth), ((pipeStartY)/atlasHeight),
                        ((pipeStartX + pipeWidth)/atlasWidth), ((pipeStartY + pipeHeight)/atlasHeight),
                        ((pipeStartX)/atlasWidth), ((pipeStartY + pipeHeight)/atlasHeight)
                };
    }

    public static float[] getPipeTexture(float h)
    {
        if(h > pipeHeight)
            h = pipeHeight;
        return new float[]
                {
                        ((pipeStartX)/atlasWidth), ((pipeStartY)/atlasHeight),
                        ((pipeStartX + pipeWidth)/atlasWidth), ((pipeStartY)/atlasHeight),
                        ((pipeStartX + pipeWidth)/atlasWidth), ((pipeStartY + h)/atlasHeight),
                        ((pipeStartX)/atlasWidth), ((pipeStartY + h)/atlasHeight)
                };
    }

    public static float[] getBirdTexture()
    {
        return new float[]
                {
                    ((birdStartX)/atlasWidth), ((birdStartY)/atlasHeight),
                    ((birdStartX + birdWidth)/atlasWidth), ((birdStartY)/atlasHeight),
                    ((birdStartX + birdWidth)/atlasWidth), ((birdStartY + birdHeight)/atlasHeight),
                    ((birdStartX)/atlasWidth), ((birdStartY + birdHeight)/atlasHeight)

                };
    }

    public static float[] getBackgroundTexture()
    {
        return new float[]
                {
                        bgStartX, 0,
                        (bgWidth/atlasWidth), 0,
                        (bgWidth/atlasWidth), (bgHeight/ atlasHeight),
                        bgStartX, (bgHeight/ atlasHeight)
//                        0, 0,
//                        (float)(284f/512f), 0,
//                        (float)(284f/512f), 1,
//                        0, 1

//0,0,
//1,0,
//1,1,
//0,1,
                };
    }
}
