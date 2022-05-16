package view;

import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

public class Player extends MapObject {
	
	// player stuff
	private int health;
	private int maxHealth;
	private int fire;
	private int maxFire;
	private boolean dead;
	private boolean flinching;
	private long flinchTimer;
	private int level;
	
	// fireball
	private boolean firing;
	private int fireCost;
	private int fireBallDamage;
	private ArrayList<FireBall> fireBalls;
	
	// scratch
	private boolean scratching;
	private int scratchDamage;
	private int scratchRange;
	
	// animations
	private ArrayList<BufferedImage[]> sprites;
	private final int[] numFrames = {
		2, 6, 1, 1, 2, 6
	};
	
	// animation actions
	private static final int IDLE = 0;
	private static final int WALKING = 1;
	private static final int JUMPING = 2;
	private static final int FALLING = 3;
	private static final int FIREBALL = 4;
	private static final int SCRATCHING = 5;
	
	public Player(TileMap tm) {
		super(tm);
		
		width = 56;
		height = 100;
		cwidth = 54;
		cheight = 96;
		level = 0;
		
		moveSpeed = 0.3;
		maxSpeed = 4.0;
		stopSpeed = 3.0;
		fallSpeed = 0.15;
		maxFallSpeed = 4.0;
		jumpStart = -6.8;
		stopJumpSpeed = 0.3;
		
		facingRight = true;
		
		health = maxHealth = 100;
		fire = maxFire = 1000;
		fireBalls = new ArrayList<FireBall>();
		
		fireCost = 100;
		fireBallDamage = 35;
		
		scratchDamage = 25;
		scratchRange = 130;
		
		// load sprites
		try {
			sprites = new ArrayList<BufferedImage[]>();
			for(int i = 0; i < 6; i++) {
				BufferedImage[] bi = new BufferedImage[numFrames[i]];
				
					if(i == IDLE) {
						for(int j = 0; j < numFrames[i]; j++) {
							String name = "src/main/resources/Animation/A0/" + j + ".png";
							bi[j] = ImageIO.read(new File(name));
						}
					}
					if(i == WALKING) {
						for(int j = 0; j < numFrames[i]; j++) {
							String name = "src/main/resources/Animation/A1/" + j + ".png";
							bi[j] = ImageIO.read(new File(name));
						}
					}
					if(i == JUMPING) {
						for(int j = 0; j < numFrames[i]; j++) {
							String name = "src/main/resources/Animation/A2/" + j + ".png";
							bi[j] = ImageIO.read(new File(name));
						}
					}
					if(i == FALLING) {
						for(int j = 0; j < numFrames[i]; j++) {
							String name = "src/main/resources/Animation/A3/" + j + ".png";
							bi[j] = ImageIO.read(new File(name));
						}
					}
					if(i == FIREBALL) {
						for(int j = 0; j < numFrames[i]; j++) {
							String name = "src/main/resources/Animation/A4/" + j + ".png";
							bi[j] = ImageIO.read(new File(name));
						}
					}
					if(i == SCRATCHING) {
						for(int j = 0; j < numFrames[i]; j++) {
							String name = "src/main/resources/Animation/A5/" + j + ".png";
							bi[j] = ImageIO.read(new File(name));
						}
					}
				sprites.add(bi);
				
			}
			
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
		animation = new Animation();
		currentAction = IDLE;
		animation.setFrames(sprites.get(IDLE));
		animation.setDelay(400);
	}
	
	public int getHealth() { return health; }
	public int getMaxHealth() { return maxHealth; }
	public int getFire() { return fire; }
	public int getMaxFire() { return maxFire; }
	public void setHealth(int health){this.health = health;}
	public void kill(){dead = true;}
	public boolean isDead(){return dead;}
	public int getLevel(){return level;}
	public void setLevel(int lvl) {
		this.level = lvl;
		}
	public void increaseHealth(int health) {
		this.health += health;
		if (this.health > maxHealth) this.health = maxHealth;
	}
	
	public void checkKits(ArrayList<Kits> kits) {
		for(int i = 0; i < kits.size(); i++) {
			Kits o = kits.get(i);
			if (intersects(o)) o.kill();
		}
	}

	public void setFiring() { 
		firing = true;
	}
	
	public void setScratching() {
		scratching = true;
	}
	
	public void checkAttack(ArrayList<Enemy> enemies) {

		// loop through enemies
		for (int i = 0; i < enemies.size(); i++) {

			Enemy e = enemies.get(i);

			// scratch attack
			if (scratching) {
				if (facingRight) {
					if (e.getx() > x && e.getx() < x + scratchRange && e.gety() > y - height / 2
							&& e.gety() < y + height / 2) {
						e.hit(scratchDamage);
					}
				} else {
					if (e.getx() < x && e.getx() > x - scratchRange && e.gety() > y - height / 2
							&& e.gety() < y + height / 2) {
						e.hit(scratchDamage);
					}
				}
			}

			// fireballs
			for (int j = 0; j < fireBalls.size(); j++) {
				if (fireBalls.get(j).intersects(e)) {
					e.hit(fireBallDamage);
					fireBalls.get(j).setHit();
					break;
				}
			}

			// check enemy collision
			if (intersects(e)) {
				hit(e.getDamage());
			}

		}

	}

	public void hit(int damage) {
		if (flinching)
			return;
		health -= damage;
		if (health < 0)
			health = 0;
		if (health == 0)
			dead = true;
		flinching = true;
		flinchTimer = System.nanoTime();
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
		else {
			if(dx > 0) {
				dx -= stopSpeed;
				if(dx < 0) {
					dx = 0;
				}
			}
			else if(dx < 0) {
				dx += stopSpeed;
				if(dx > 0) {
					dx = 0;
				}
			}
		}
		
		// cannot move while attacking, except in air
		if(
		(currentAction == SCRATCHING || currentAction == FIREBALL) && !(jumping || falling)) {
			dx = 0;
		}
		
		// jumping
		if(jumping && !falling) {
			dy = jumpStart;
			falling = true;
		}
		
		// falling
		if(falling) {
			
			dy += fallSpeed;
			
			if(dy > 0) jumping = false;
			if(dy < 0 && !jumping) dy += stopJumpSpeed;
			
			if(dy > maxFallSpeed) dy = maxFallSpeed;
			
		}
		
	}
	
	public void update() {
		
		// update position
		getNextPosition();
		checkTileMapCollision();
		setPosition(xtemp, ytemp);
		
		// check attack has stopped
		if(currentAction == SCRATCHING) {
			if(animation.hasPlayedOnce()) scratching = false;
		}
		if(currentAction == FIREBALL) {
			if(animation.hasPlayedOnce()) firing = false;
		}
		
		
		//fireball attack
		fire += 1;
		if(fire > maxFire) fire = maxFire;
		if(firing && currentAction != FIREBALL) {
			if(fire > fireCost) {
				fire -= fireCost;
				FireBall fb = new FireBall(tileMap, facingRight,"src/main/resources/Animation/A9/", 25, 3);
				fb.setPosition(x, y);
				fireBalls.add(fb);
			}
		}
		
		//fireball
		for(int i = 0; i < fireBalls.size(); i++) {
			fireBalls.get(i).update();
			if(fireBalls.get(i).shouldRemove()) {
				fireBalls.remove(i);
				i--;
			}
		}
		
		// check done flinching
		if(flinching) {
			long elapsed =
				(System.nanoTime() - flinchTimer) / 1000000;
			if(elapsed > 1000) {
				flinching = false;
			}
		}
		
		// set animation
		if(scratching) {
			if(currentAction != SCRATCHING) {
				currentAction = SCRATCHING;
				animation.setFrames(sprites.get(SCRATCHING));
				animation.setDelay(50);
				width = 126;
			}
		}
		else if(firing) {
			if(currentAction != FIREBALL) {
				currentAction = FIREBALL;
				animation.setFrames(sprites.get(FIREBALL));
				animation.setDelay(100);
				width = 54;
			}
		}
		else if(dy > 0) {
			if(currentAction != FALLING) {
				currentAction = FALLING;
				animation.setFrames(sprites.get(FALLING));
				animation.setDelay(100);
				width = 54;
			}
		}
		else if(dy < 0) {
			if(currentAction != JUMPING) {
				currentAction = JUMPING;
				animation.setFrames(sprites.get(JUMPING));
				animation.setDelay(-1);
				width = 54;
			}
		}
		else if(left || right) {
			if(currentAction != WALKING) {
				currentAction = WALKING;
				animation.setFrames(sprites.get(WALKING));
				animation.setDelay(40);
				width = 54;
			}
		}
		else {
			if(currentAction != IDLE) {
				currentAction = IDLE;
				animation.setFrames(sprites.get(IDLE));
				animation.setDelay(400);
				width = 54;
			}
		}
		
		animation.update();
		// set direction
		if(currentAction != SCRATCHING && currentAction != FIREBALL) {
			if(right) facingRight = true;
			if(left) facingRight = false;
		}
		
	}
	
	public void draw(Graphics g) {

		setMapPosition();

		// draw fireballs
		for (int i = 0; i < fireBalls.size(); i++) {
			fireBalls.get(i).draw(g);
		}

		// draw player
		if (flinching) {
			long elapsed = (System.nanoTime() - flinchTimer) / 1000000;
			if (elapsed / 100 % 2 == 0) {
				return;
			}
		}

		super.draw(g);

	}
}

















