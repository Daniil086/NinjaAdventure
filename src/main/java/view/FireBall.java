package view;


import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

public class FireBall extends MapObject {
	
	private boolean hit;
	private boolean remove;
	private BufferedImage[] sprites;
	private int numFrames;
	
	public FireBall(TileMap tm, boolean right, String s, int size, int num) {
		
		super(tm);
		
		facingRight = right;
		
		moveSpeed = 5.8;
		if(right) dx = moveSpeed;
		else dx = -moveSpeed;
		
		width = height = size;
		cwidth = 30;
		cheight = 30;
		numFrames = num;
		
		// load sprites
		try {
			sprites = new BufferedImage[numFrames];
			for(int i = 0; i < numFrames; i++) {
				sprites[i] = ImageIO.read(new File(s + i + ".png"));
			}
			
			animation = new Animation();
			animation.setFrames(sprites);
			animation.setDelay(70);
			
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public void setHit() {
		if(hit) return;
		hit = true;
		dx = 0;
	}
	
	public boolean shouldRemove() { return remove; }
	
	public void update() {
		
		checkTileMapCollision();
		setPosition(xtemp, ytemp);
		
		if(dx == 0 && !hit) {
			setHit();
		}
		
		animation.update();
		if(hit && animation.hasPlayedOnce()) {
			remove = true;
		}
		
	}
	
	public void draw(Graphics g) {
		
		setMapPosition();
		
		super.draw(g);
		
	}
	
}


















