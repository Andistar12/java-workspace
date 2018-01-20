#version 330 core

in vec2 pass_textureCoord;

uniform sampler2D u_texture;
uniform vec3 u_guiColor;

out vec4 final_color;

void main(void){
	vec4 myColor = texture(u_texture, pass_textureCoord) * vec4(u_guiColor, 1.0);
	
	//Transparency discard test, pretty interesting
	if (myColor.a < 0.1) { discard; }
	
	final_color = myColor;
}