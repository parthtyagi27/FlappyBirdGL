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
    private static Map<String, Integer> bufferPointers;
    private static long context, device;
    private static int source;

    public static void init()
    {
        String name = ALC10.alcGetString(0, ALC10.ALC_DEFAULT_DEVICE_SPECIFIER);
        device = ALC10.alcOpenDevice(name);
        int[] attributes = {0};
        context = ALC10.alcCreateContext(device, attributes);
        ALC10.alcMakeContextCurrent(context);
        ALCCapabilities alcCapabilities = ALC.createCapabilities(device);
        ALCapabilities  alCapabilities  = AL.createCapabilities(alcCapabilities);
        source = AL10.alGenSources();

        bufferPointers = new HashMap<String, Integer>();
    }

    public static void loadAudio(String path, String key)
    {
        if(bufferPointers.containsKey(key))
        {
            System.err.println("Audio with key: " + key + " already exists...");
            return;
        }
        WaveData waveFile = WaveData.create(path);
        int buffer = AL10.alGenBuffers();
        //Code from the OpenAL wiki
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
        bufferPointers.put(key, buffer);
    }

    public static void play(String key)
    {
        if(bufferPointers.containsKey(key))
        {
            AL10.alSourcei(source, AL10.AL_BUFFER, bufferPointers.get(key));
            AL10.alSourcePlay(source);
        }else
        {
            System.err.println("Audio with key: " + key + " has not been initialized...");
        }
    }

    public static void flush()
    {
        for(int buffer : bufferPointers.values())
            AL10.alDeleteBuffers(buffer);
        AL10.alDeleteSources(source);
        ALC10.alcDestroyContext(context);
        ALC10.alcCloseDevice(device);
    }

}
