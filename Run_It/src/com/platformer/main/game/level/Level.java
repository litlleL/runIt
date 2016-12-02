package com.platformer.main.game.level;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import org.lwjgl.opengl.Display;

import com.platformer.main.Component;
import com.platformer.main.entities.Entity;
import com.platformer.main.entities.Player;
import com.platformer.main.game.level.tiles.Tile;
import com.platformer.main.game.level.tiles.Tile.Tiles;

public class Level {

	public int width;
	public int height;
	
	public float gravity = 1.8f;
	
	List<Tile> tiles = new ArrayList<Tile>();
	Tile[][] solidTiles;
	Tile[][] bgTiles;
	
	private int[] bounds = new int[4];
	
	List<Entity> entities = new ArrayList<Entity>();
	
	private static Player player = new Player(5, 5);
	
	public Level(int width, int height) {
		
		loadLevel("level_metal");
		spawner();
	}
	
	public static Player getPlayer() {
		return player;
	}

	public static void setPlayer(Player player) {
		Level.player = player;
	}

	public void loadLevel(String name){
		int []pixels;
		
		BufferedImage image = null;
		try {
			image = ImageIO.read(Level.class.getResource("/level/"+name+".png"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		width = image.getWidth();
		height = image.getHeight();
		
		bounds[0]= - 16;
		bounds[1]= - 16;
		bounds[2]= -width * 16 + 16 + Display.getWidth() / Component.scale;
		bounds[3]= -height * 16 + 16 + Display.getHeight() / Component.scale;
		System.out.println(bounds[3]);
		pixels = new int[width * height];
		image.getRGB(0, 0, width, height, pixels, 0, width);
		
		solidTiles = new Tile[width][height];
		bgTiles = new Tile[width][height];
		
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				if (pixels[x + y * width] == 0xFFffffff) {
					solidTiles[x][y] = new Tile(x, y, Tiles.SOLID_METAL);
				}
				if (pixels[x + y * width] == 0xFF000000) {
					bgTiles[x][y] = new Tile(x, y, Tiles.BG_METAL);
				}
				if (pixels[x + y * width] == 0xFFffff00 || 
					pixels[x + y * width] == 0xFFffff01 || 
					pixels[x + y * width] == 0xFFffff02 ||
					pixels[x + y * width] == 0xFFffff03 ||
					pixels[x + y * width] == 0xFFffff04 ||
					pixels[x + y * width] == 0xFFffff05){
					bgTiles[x][y] = new Tile(x, y, Tiles.BG_METAL);
				}
			}
		}
		
		setTiles();
	}
	
	public void setTiles(){

		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
			//solidTiles[x][y] = new Tile(x, y, Tiles.SOLID_METAL);

				
				boolean vu = false, vd = false, vl = false, vr = false;
				boolean vur = false, vdr = false, vul = false, vdl = false;
				
				//-- OutOfBounds
				
				if(x - 1 < 0 || y - 1 < 0 || x + 1 >= width || y + 1 >=  height){
					continue;
				}
				
				//-- droite, gauche, haut et bas tile
				
				if(solidTiles[x + 1][y] == null){
					vr = true;
				}
				if(solidTiles[x - 1][y] == null){
					vl = true;
				}
				if(solidTiles[x][y + 1] == null){
					vd = true;
				}
				if(solidTiles[x][y - 1] == null){
					vu = true;
				}
				
				//--Angle des tiles
				
				if(solidTiles[x + 1][y + 1] == null){
					vdr = true;
				}
				if(solidTiles[x - 1][y - 1] == null){
					vul = true;
				}
				if(solidTiles[x - 1][y + 1] == null){
					vdl = true;
				}
				if(solidTiles[x + 1][y - 1] == null){
					vur = true;
				}
				
				//--
				
				if(solidTiles[x ][y] != null){
					solidTiles[x][y].SetTiles(vu, vd, vl, vr, vur, vdr, vul, vdl);
				}
				
				addTiles(x,  y);
			}
		}
	}

	public Tile getSolidTile(int x, int y){
		if(x < 0 || y < 0 || x >= width || y >= height){
			return null;
		}
		return solidTiles[x][y];
	}
	
	public void addTiles(int x, int y){
		if(solidTiles[x ][y] != null){
			tiles.add(solidTiles[x][y]);
		}else if(bgTiles[x ][y] != null){
			tiles.add(bgTiles[x][y]);
		}
	}
	
	public void spawner(){
		player.init(this);
		addEntity(player);
	}
	
	public void init(){
		
	}
	
	public void addEntity(Entity e){
		entities.add(e);
	}
	
	public void removeEntity(Entity e){
		entities.remove(e);
	}
	
	public void update(){
		for (int i = 0; i < entities.size(); i++) {
			Entity e = entities.get(i);
			if (e.isRemoved()) {
				removeEntity(e);
			}
			e.update();
		}
	}
	
	public void render(){
		for (Tile tile : tiles) {
			tile.render();
		}
		for (int i = 0; i < entities.size(); i++) {
			Entity e = entities.get(i);
			e.render();
		}
	}

	public int getBounds(int index){
		return bounds[index];
	}
}
