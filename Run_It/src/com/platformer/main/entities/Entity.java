package com.platformer.main.entities;

import com.platformer.main.game.level.Level;
import com.platformer.main.graphics.Texture;

public abstract class Entity {
	protected float x, y;
	protected boolean removed = false;
	protected Texture texture;
	protected Level level;
	protected float mass = 0;
	protected float grid = 0;
	protected int jumpOver = 0;
	public boolean jumpCapacity = true;
	
	public Entity(int x, int y) {
		this.setX(x * 8);
		this.setY(y * 8);
	}

	public abstract void init(Level level);
	
	public abstract void update();
	
	public abstract void render();
	
	public boolean isSolid(float xa, float ya){
		
		int x0 = (int) (x + xa + 4) / 16;
		int x1 = (int) (x + xa + 12) / 16;
		int y0 = (int) (y + ya + 4) / 16;
		int y1 = (int) (y + ya + 12) / 16;
		
		if (level.getSolidTile(x0, y0) != null) {
			return true;
		}
		if (level.getSolidTile(x0, y1) != null) {
			return true;
		}
		if (level.getSolidTile(x1, y0) != null) {
			return true;
		}
		if (level.getSolidTile(x1, y1) != null) {
			return true;
		}
		return false;
	}
	
	
	public boolean isGrounded(){
		if ((level.getSolidTile((int)(x + 4) / 16,(int)(y + 14) / 16) != null)) {
			jumpOver = 0;
			return true;
		}
		return false;
	}
	
	public boolean doubleJump(){
		if (!isGrounded()) {
			if(canMakeDouble()){
				return true;
			}else {
				return false;
			}
		}
		return false;
	}
	
	private boolean canMakeDouble() {
		if((jumpOver == 0) && (jumpCapacity)){
			jumpOver = 1;
			return true;
		}
		return false;
	}

	public float getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public float getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public boolean isRemoved() {
		return removed;
	}

	public void setRemoved(boolean removed) {
		this.removed = removed;
	}

	public Texture getTexture() {
		return texture;
	}

	public void setTexture(Texture texture) {
		this.texture = texture;
	}
}
