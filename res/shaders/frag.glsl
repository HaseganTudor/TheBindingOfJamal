#version 330 core

out vec4 FragColor;

in vec2 TexCoord;
in vec2 WorldPos;

uniform vec4 color;
uniform sampler2D uniTexture;
uniform int hasTexture;

const int MAX_LIGHTS = 12;

struct PointLight {
    vec2 position;
    vec3 color;
    float radius;
    float intensity;
};

uniform int lightCount;
uniform PointLight lights[MAX_LIGHTS];

void main()
{
    vec4 baseColor;

    if (hasTexture == 1)
    {
        baseColor = texture(uniTexture, TexCoord);
    }
    else
    {
        baseColor = color;
    }

    if (baseColor.a <= 0.0) {
        discard;
    }

    vec3 light = vec3(0.7);

    for (int i = 0; i < lightCount; i++) {
        float distanceToLight = distance(WorldPos, lights[i].position);
        float falloff = 1.0 - smoothstep(0.0, lights[i].radius, distanceToLight);
        light += lights[i].color * falloff * lights[i].intensity;
    }

    FragColor = vec4(baseColor.rgb * light, baseColor.a);
}
