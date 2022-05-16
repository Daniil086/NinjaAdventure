package view;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

public class Wolf extends Enemy {
	private BufferedImage[] sprites;
	private final int numFrames = 6;
	
	public Wolf(TileMap tm) {
		super(tm);
		
		moveSpeed = 1.0;
		maxSpeed = 2.0;
		fallSpeed = 0.2;
		maxFallSpeed = 10.0;
		
		width = 80;
		height = 45;
		cwidth = 80;
		cheight = 45;
		
		health = maxHealth = 25;
		damage = 25;
		// load sprites
		try {
			sprites = new BufferedImage[numFrames];
			for(int i = 0; i < numFrames; i++) {
				String name = "src/main/resources/Animation/A8/" + i + ".png";
				sprites[i] = ImageIO.read(new File(name));
				}
			} catch(Exception e) {
			e.printStackTrace();
		}
		
		animation = new Animation();
		animation.setFrames(sprites);
		animation.setDelay(40);
		
		right = true;
		facingRight = true;
		
	}
	
	private void getNextPosition() {
		
		// movement
		if(left) {
			dx -= moveSpeed;
			if(dx < -maxSpeed) {
				dx = -maxSpeed;
			}
		}
		else if(right) {
			dx += moveSpeed;
			if(dx > maxSpeed) {
				dx = maxSpeed;
			}
		}
		
		// falling
		if(falling) {
			dy += fallSpeed;
		}
	}
	
	public void update() {
		
		// update position
		getNextPosition();
		checkTileMapCollision();
		setPosition(xtemp, ytemp);
		
		// check flinching
		if(flinching) {
			long elapsed =
				(System.nanoTime() - flinchTimer) / 1000000;
			if(elapsed > 400) {
				flinching = false;
			}
		}
		
		// if it hits a wall, go other direction
		if((right && dx == 0) || (x == tileMap.getWidth() - 2*width)) {
			right = false;
			left = true;
			facingRight = false;
		}
		else if(left && dx == 0) {
			right = true;
			left = false;
			facingRight = true;
		}
		
		// if about to fall off edge, go other direction
		if (!bottomLeft) {
			left = false;
			right = facingRight = true;
		}
		if (!bottomRight) {
			left = true;
			right = facingRight = false;
		}
		
		// update animation
		animation.update();
		
	}
	
	public void draw(Graphics g) {
		
		if(flinching) {
			long elapsed =
				(System.nanoTime() - flinchTimer) / 1000000;
			if(elapsed / 100 % 2 == 0) {
				return;
			}
		}
		setMapPosition();
		
		super.draw(g);
		
	}
}
