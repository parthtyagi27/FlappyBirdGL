package engine;

import sun.audio.AudioPlayer;
import sun.audio.AudioStream;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import java.io.InputStream;

public class Audio
{

    private AudioStream audioStream;
    private AudioInputStream audioInputStream;
    private Clip clip;

    public Audio(String path)
    {
        InputStream inputStream = getClass().getResourceAsStream(path);
        try
        {
            audioInputStream = AudioSystem.getAudioInputStream(inputStream);
//            audioStream = new AudioStream(inputStream);
            clip = AudioSystem.getClip();
//            inputStream.close();
        }catch (Exception ex)
        {
            ex.printStackTrace();
        }

    }

    public void play()
    {
//        new Thread(new Runnable()
//        {
//            @Override
//            public void run()
//            {
//                AudioPlayer.player.start(audioStream);
//            }
//        }).start();
        try
        {
            clip.open(audioInputStream);
            clip.setFramePosition(0);
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        clip.start();
//        clip.close();
    }

}

