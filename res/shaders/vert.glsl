#version 330 core

layout(location = 0) in vec3 aPos;
layout(location = 1) in vec2 aTexCoord;

uniform mat4 projection;
uniform mat4 view;
uniform mat4 model;

uniform vec2 uvOffset;
uniform vec2 uvScale;

out vec2 TexCoord;
out vec2 WorldPos;

void main()
{
    vec4 worldPosition = model * vec4(aPos, 1.0);

    gl_Position = projection * view * worldPosition;
    TexCoord = aTexCoord * uvScale + uvOffset;
    WorldPos = worldPosition.xy;
}
