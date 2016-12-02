package com.platformer.main.entities;

import org.lwjgl.input.Keyboard;

import com.platformer.main.game.level.Level;
import com.platformer.main.graphics.Color;
import com.platformer.main.graphics.Renderer;
import com.platformer.main.graphics.Texture;
import com.platformer.main.utils.Animation;

public class Player extends Entity{
	
	int dir = 0;
	Animation animer ;
	
	public Player(int x, int y) {
		super(x, y);
		texture = Texture.player;
		animer = new Animation(3, 5, true);
		
		mass = 0.32f;
		grid = 0.78f;
	}

	float xa, ya;
	
	public void update() {
		ya += level.gravity * mass;
		animer.update();
		animer.pause();
		float speed = 0.5f;
		float jump;
		if(jumpCapacity){
			jump = 6.3f;
		}
		else{
			jump = 7.8f;
		}
		
		if ((Keyboard.isKeyDown(Keyboard.KEY_Z)) || Keyboard.isKeyDown(Keyboard.KEY_W) || Keyboard.isKeyDown(Keyboard.KEY_UP)) {
			
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_S) || Keyboard.isKeyDown(Keyboard.KEY_DOWN)) {
			ya += speed * 2;
		}
		
		if ((Keyboard.isKeyDown(Keyboard.KEY_Q)) || Keyboard.isKeyDown(Keyboard.KEY_A) || Keyboard.isKeyDown(Keyboard.KEY_LEFT)) {
			xa -= speed;
			dir = 1;
			animer.play();
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_D) || Keyboard.isKeyDown(Keyboard.KEY_RIGHT)) {
			xa += speed;
			dir = 0;
			animer.play();
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_SPACE)) {
			if (isGrounded()) {
				ya -= jump;
			}
			if(doubleJump()){
				ya -= jump;
			}
		}
		
		int xSteps = (int) Math.abs(xa * 1000);
		for (int i = 0; i < xSteps; i++) {
			if (!isSolid(xa / xSteps, 0)) {
				x += xa / xSteps;
			}else {
				xa = 0;
			}
		}
		
		int ySteps = (int) Math.abs(ya * 1000);
		for (int i = 0; i < ySteps; i++) {
			if (!isSolid(0, ya / ySteps)) {
				y += ya / ySteps;
			}else{
				ya = 0;
			}
		}
		
		xa *= grid;
		ya *= grid;
	}

	public void render() {
		texture.bind();
			Renderer.renderEntity(x, y, 16, 16, Color.WHITE, 8.0f, 1 + dir, animer.getCurrentFrame());
		texture.unbind();
	}

	public void init(Level level) {
		this.level = level;
	}

}
