package engine;

public class TextureAtlas
{
    public static Texture texture;

    public static final float atlasWidth = 512f, atlasHeight = 512f;
    public static float bgWidth = 284f, bgHeight = 512f, bgStartX = 0;
    public static float birdWidth = 125f, birdHeight = 88f, birdStartX = 336f, birdStartY = 42f;

//    public TextureAtlas(String file)
//    {
//        texture = new Texture(file);
//    }

    public static void loadTextureAtlas(String file)
    {
        texture = new Texture(file);
    }

    public Texture getTexture()
    {
        return texture;
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
