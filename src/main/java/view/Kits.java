package view;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

public class Kits extends MapObject {
	private boolean dead;
	private BufferedImage[] sprites;
	private int key;

	public Kits(TileMap tm, int key) {
		super(tm);
		this.key = key;
		
		fallSpeed = 0.2;
		maxFallSpeed = 0.3;
		sprites = new BufferedImage[1];
		if (this.key == 0) {
			width = 40;
			height = 32;
			cwidth = 45;
			cheight = 35;
			try {
				sprites[0] = ImageIO.read(new File("src/main/resources/Image/kit.png"));
			} catch(Exception e) {
			e.printStackTrace();
			}
		} else {
			width = 60;
			height = 55;
			cwidth = 65;
			cheight = 60;
			try {
				sprites[0] = ImageIO.read(new File("src/main/resources/Image/Bigkit.png"));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		animation = new Animation();
		animation.setFrames(sprites);
		animation.setDelay(40);	
		right = true;
		facingRight = true;
	}

	public void kill() {
		dead = true;
	}

	public boolean isDead() {
		return dead;
	}

	public int getKey() {
		return key;
	}

	private void getNextPosition() {
		if (y >= tileMap.getHeight() - height) {
			y = tileMap.getHeight() - height;
			falling = false;
		}
		// falling
		if (falling) {
			dy += fallSpeed;
		}

	}

	public void update() {
		// update position
		getNextPosition();
		checkTileMapCollision();
		setPosition(xtemp, ytemp);
		// update animation
		animation.update();
	}

	public void draw(Graphics g) {

		setMapPosition();
		super.draw(g);

	}

}
