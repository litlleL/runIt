package com.platformer.main.graphics;

import static org.lwjgl.opengl.GL11.*;

public class Renderer {

	public Renderer() {
		
	}

	//Rendu des tiles en 4 points
	public static void quadData(float x, float y, int width, int height, float []color, int xo, int yo){
		glColor4f(color[0], color[1], color[2], color[3]);
		glTexCoord2f((0 + xo) / 32.0f, (0 + yo) / 32.0f);glVertex2f(x, y);
		glTexCoord2f((1 + xo) / 32.0f, (0 + yo) / 32.0f);glVertex2f(x + width, y);
		glTexCoord2f((1 + xo) / 32.0f, (1 + yo) / 32.0f);glVertex2f(x + width, y + height);
		glTexCoord2f((0 + xo) / 32.0f, (1 + yo) / 32.0f);glVertex2f(x, y + height);
	}
	
	public static void renderQuad(float x, float y, int width, int height, float []color, int xo, int yo){
		glBegin(GL_QUADS);
			Renderer.quadData(x, y, width, height, color, xo, yo);
		glEnd();
	}
	
	//rendu des entité (joueurs, ennemies, bonus .........)
	public static void renderEntity(float x, float y, int width, int height, float []color, float size, int xo, int yo){
		glBegin(GL_QUADS);
			glColor4f(color[0], color[1], color[2], color[3]);
			glTexCoord2f((0 + xo) / size, (0 + yo) / size);glVertex2f(x, y);
			glTexCoord2f((1 + xo) / size, (0 + yo) / size);glVertex2f(x + width, y);
			glTexCoord2f((1 + xo) / size, (1 + yo) / size);glVertex2f(x + width, y + height);
			glTexCoord2f((0 + xo) / size, (1 + yo) / size);glVertex2f(x, y + height);
		glEnd();
	}
}
