package engine;

import org.lwjgl.BufferUtils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.ByteBuffer;


public class Image
{
    private int width, height;
    private ByteBuffer pixels;

    public Image(String path)
    {
        BufferedImage bufferedImage;
        try
        {
            bufferedImage = ImageIO.read(getClass().getResourceAsStream(path));
            width = bufferedImage.getWidth();
            height = bufferedImage.getHeight();

            int[] rawPixels = new int[width * height * 4];
            bufferedImage.getRGB(0, 0, width, height, rawPixels, 0, width);

            pixels = BufferUtils.createByteBuffer(width * height * 4);

            for (int i = 0; i < width; i++)
            {
                for (int j = 0; j < height; j++)
                {
                    int pixel = rawPixels[i * width + j];
                    pixels.put((byte) ((pixel >> 16) & 0xFF)); //RED
                    pixels.put((byte) ((pixel >> 8) & 0xFF));  //GREEN
                    pixels.put((byte) (pixel & 0xFF));          //BLUE
                    pixels.put((byte) ((pixel >> 24) & 0xFF)); //ALPHA
                }
            }

            pixels.flip();
        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public int getWidth()
    {
        return width;
    }

    public int getHeight()
    {
        return height;
    }

    public ByteBuffer getPixels()
    {
        return pixels;
    }
}