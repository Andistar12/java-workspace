#version 330 core

in vec2 pass_textureCoord;
in vec4 pass_worldPos;
in vec3 pass_normal;

uniform vec3 u_cameraLoc;
uniform vec3 u_lightLoc;
uniform sampler2D u_texture;
uniform vec3 u_skyColor;

uniform vec4 u_sunLoc;

out vec4 final_color;

//Diffuse a light to a world pos given its normal
void diffuseLight(in vec3 light, in vec3 world, in vec3 normal, out float diffuse) {
	vec3 toLightVec = normalize(light - vec3(world));
    diffuse = dot(normal, toLightVec);
    diffuse = clamp(diffuse, 0.0, 1.0);
}

//Attenuate a diffused light given a world pos, light pos, and attenuation values
void attenuateLight(in vec3 world, in vec3 lightLoc, in vec3 atten, inout float diffuse) {
	    float distanceToLight = length(world - lightLoc);
		diffuse = diffuse / (atten.x * distanceToLight * distanceToLight
							+ atten.y * distanceToLight + atten.z);
		diffuse = clamp(diffuse, 0.0, 1.0);
}

void main(void){
	
	float distance = length(vec3(pass_worldPos) - u_cameraLoc);
	//e ^ ( -( density * distance ^ gradient))
    //density = how close the fog can come (smaller is farther)
    //gradient = how fast the fog goes from clear to fog (higher is thicker)
    float visibility = exp( -pow(0.01 * distance, 0.75) );
    //visibility = 1.0; //Disable fog
    visibility = clamp(visibility, 0.0, 1.0);
    
    //Light diffuse & attenuation
    float diffuse = 0.0;
    diffuseLight(u_lightLoc, vec3(pass_worldPos), pass_normal, diffuse);
    diffuse = pow(diffuse, 0.5);
    //Quadratic attenuated lighting
    attenuateLight(vec3(pass_worldPos), u_lightLoc, vec3(u_sunLoc.w, 0.0, 1.0), diffuse);
    
    //Sun diffuse & attenuation
    float sunDiffuse = 0.0;
    diffuseLight(vec3(u_sunLoc), vec3(pass_worldPos), pass_normal, sunDiffuse);
    attenuateLight(vec3(pass_worldPos), vec3(u_sunLoc), vec3(u_sunLoc.w, 0.0, 1.0), sunDiffuse);
    
    //Combine the diffuse, add in daylight
    diffuse = diffuse + sunDiffuse + u_skyColor.x;
    diffuse = clamp(diffuse, 0.2, 1.0); //Ambient lighting can go here

	//Add daylight into visibility
	visibility = visibility + diffuse;
    visibility = clamp(visibility, 0.0, 1.0);

	//Fog visibility still takes precedence
	vec4 skyColor = vec4(u_skyColor, 1.0);
	vec4 textureColor = texture(u_texture, pass_textureCoord) * diffuse;
	final_color = mix(skyColor, textureColor, visibility);
}