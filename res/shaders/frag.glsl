#version 330 core
out vec4 FragColor;

in vec2 TexCoord;

uniform vec4 color;
uniform sampler2D uniTexture;
uniform int hasTexture;

void main()
{
    if(hasTexture == 1){
        FragColor = texture(uniTexture, TexCoord);
    }
    else{
        FragColor = color;
    }
}