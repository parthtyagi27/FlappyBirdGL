#version 120

uniform sampler2D sampler;
varying vec2 textureCoordinates;

void main()
{
    gl_FragColor = texture2D(sampler, textureCoordinates);
//    gl_FragColor = vec4(1.0, 1.0, 1.0, 1.0);
}