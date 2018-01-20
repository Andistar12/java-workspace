#version 330 core

in vec2 in_position;
in vec2 in_textureCoord;

uniform mat4 u_modelMatrix;
uniform float u_aspectRatio;

out vec2 pass_textureCoord;

void main() {
	gl_Position = u_modelMatrix * vec4(in_position, 0.0, 1.0);
	
	//This makes the x values to scale
	gl_Position.y = gl_Position.y * u_aspectRatio;
	
	//This makes the y values to scale
	//gl_Position.x = gl_Position.x / u_aspectRatio;
	
	pass_textureCoord = in_textureCoord;
}