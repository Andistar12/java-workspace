#version 330 core

in vec3 in_position;
in vec2 in_textureCoord;
in vec3 in_normal;

uniform mat4 u_modelMatrix;
uniform mat4 u_viewProjMatrix;

out vec2 pass_textureCoords;
out vec4 pass_worldPos;
out vec3 pass_normal;

void main() {
	pass_worldPos = u_modelMatrix * vec4(in_position, 1.0);
	pass_textureCoords = in_textureCoord;
	pass_normal = vec3(u_modelMatrix * vec4(in_normal, 0.0) );
	
	gl_Position = u_viewProjMatrix * pass_worldPos;
}