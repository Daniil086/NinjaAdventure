package view;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;

import javax.imageio.ImageIO;

public class Ghost extends Enemy {
	
	private BufferedImage[] sprites;
	private final int numFrames = 10;
	
	private int fire;
	private int maxFire;
	private int fireCost;
	private int fireBallDamage;
	private ArrayList<FireBall> fireBalls;
	
	public Ghost(TileMap tm) {
		super(tm);
		
		moveSpeed = 0.6;
		maxSpeed = 1.0;
		fallSpeed = 0.2;
		maxFallSpeed = 10.0;
		
		width = 112;
		height = 120;
		cwidth = 70;
		cheight = 120;
		
		health = maxHealth = 70;
		damage = 5;
		
		fire = maxFire = fireCost = 100;
		fireBalls = new ArrayList<FireBall>();
		fireBallDamage = 25;
		
		// load sprites
		try {
			sprites = new BufferedImage[numFrames];
			for(int i = 0; i < numFrames; i++) {
				String name = "src/main/resources/Animation/A6/" + i + ".png";
				sprites[i] = ImageIO.read(new File(name));
				}
			} catch(Exception e) {
			e.printStackTrace();
		}
		
		animation = new Animation();
		animation.setFrames(sprites);
		animation.setDelay(150);
		
		right = true;
		facingRight = true;
		
	}
	
	public void checkAttack(Player player) {
		// fireballs
		for (int j = 0; j < fireBalls.size(); j++) {
			if (fireBalls.get(j).intersects(player)) {
				player.hit(fireBallDamage);
				fireBalls.get(j).setHit();
				break;
			}
		}
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
		if(right && dx == 0) {
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
		
		// fireball attack
		fire += 1;
		if (fire > maxFire) fire = maxFire;
		if (fire >= fireCost) {
				fire -= fireCost;
				FireBall fb = new FireBall(tileMap, facingRight, "src/main/resources/Image/", 40, 1);
				fb.setPosition(x, y);
				fireBalls.add(fb);
			}

		// fireball
		for (int i = 0; i < fireBalls.size(); i++) {
			fireBalls.get(i).update();
			if (fireBalls.get(i).shouldRemove()) {
				fireBalls.remove(i);
				i--;
			}
		}
		
		// update animation
		animation.update();
		
	}
	
	public void draw(Graphics g) {
		
		// draw fireballs
		for (int i = 0; i < fireBalls.size(); i++) {
			fireBalls.get(i).draw(g);
		}
		
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











