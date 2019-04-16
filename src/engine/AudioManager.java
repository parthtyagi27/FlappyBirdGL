package engine;

import org.lwjgl.BufferUtils;
import org.lwjgl.openal.*;
import org.lwjgl.stb.STBVorbis;
import org.lwjgl.system.MemoryStack;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.nio.ShortBuffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AudioManager
{
    private Map<String, Audio> audioFiles;
    private Thread playBackThread;
    private static long context, device;
    private static ArrayList<Integer> buffers = new ArrayList<Integer>();

    public AudioManager()
    {
        audioFiles = new HashMap<String, Audio>();
        playBackThread = new Thread(new Runnable()
        {
            @Override
            public void run()
            {

            }
        });
    }

    public void addAudio(String key, Audio audio)
    {
        audioFiles.put(key, audio);
    }

    public void playAudio(String key)
    {
    }

    public static void init()
    {
        String name = ALC10.alcGetString(0, ALC10.ALC_DEFAULT_DEVICE_SPECIFIER);
        device = ALC10.alcOpenDevice(name);
        int[] attributes = {0};
        context = ALC10.alcCreateContext(device, attributes);
        ALC10.alcMakeContextCurrent(context);
        ALCCapabilities alcCapabilities = ALC.createCapabilities(device);
        ALCapabilities  alCapabilities  = AL.createCapabilities(alcCapabilities);
    }

    public static int loadAudio(String path)
    {
//        path = AudioManager.class.getResource(path).toString();
        System.out.println(path);
        WaveData waveFile = WaveData.create(path);
        int buffer = AL10.alGenBuffers();
//        MemoryStack.stackPush();
//        IntBuffer channelsBuffer = MemoryStack.stackCallocInt(1);
//        MemoryStack.stackPush();
//        IntBuffer sampleRateBuffer = MemoryStack.stackCallocInt(1);
//        ShortBuffer rawAudioBuffer = STBVorbis.stb_vorbis_decode_filename(path, channelsBuffer, sampleRateBuffer);
//
//        int channels = channelsBuffer.get();
//        int sampleRate = sampleRateBuffer.get();
//
//        MemoryStack.stackPop();
//        MemoryStack.stackPop();
//
//        int format = -1;
//
//        if(channels == 1) {
//            format = AL10.AL_FORMAT_MONO16;
//        } else if(channels == 2) {
//            format = AL10.AL_FORMAT_STEREO16;
//        }

//        AL10.alBufferData(buffer, format, rawAudioBuffer, sampleRate);
        AL10.alBufferData(buffer, waveFile.format, waveFile.data, waveFile.samplerate);
        buffers.add(buffer);
        return buffer;
    }

    public static void play(int buffer)
    {
        int source = AL10.alGenSources();
        AL10.alSourcei(source, AL10.AL_BUFFER, buffer);
        AL10.alSourcePlay(source);
    }

    public static void flush()
    {
        for(int b : buffers)
            AL10.alDeleteBuffers(b);
        ALC10.alcDestroyContext(context);
        ALC10.alcCloseDevice(device);
    }

}
