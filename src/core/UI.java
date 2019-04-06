package core;

import engine.Camera;
import engine.Shader;
import font.FontMesh;
import font.Text;

import java.awt.*;
import java.util.HashMap;

public class UI
{
    private Shader shader;
    private Camera camera;

    private HashMap<String, Text> textLabel;

    public UI(Shader shader, Camera camera)
    {
        textLabel = new HashMap<String, Text>();
        this.shader = shader;
        this.camera = camera;
    }

    public void addTextLabel(String key, Text label)
    {
        textLabel.put(key, label);
    }

    public void addTextLabel(String text, String key, FontMesh mesh)
    {
        Text label = new Text(mesh);
        label.loadText(text);
        textLabel.put(key, label);
    }

    public void translateTextLabel(String key, float x, float y, float z)
    {

        Text label = textLabel.get(key);
        label.translate(x, y, z);
    }

    public void renderTextLabel(String key, float x, float y, float z)
    {

        textLabel.get(key).render(shader, camera);
    }

    public float getLabelWidth(String key)
    {
        return textLabel.get(key).getWidth();
    }

    public float getLabelHeight(String key)
    {
        return textLabel.get(key).getHeight();
    }

    public void renderLabels()
    {
        if(!textLabel.isEmpty())
        {
            for(Text label : textLabel.values())
            {
                label.render(shader, camera);
            }
        }else
            System.out.println("UI Label Map is empty ... nothing to render");
    }

    public void removeTextLabel(String key)
    {
        textLabel.remove(key);
    }
}
