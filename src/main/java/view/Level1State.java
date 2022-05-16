package view;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import javax.swing.ImageIcon;

public class Level1State extends GameState {
	
	private TileMap tileMap;
	private Background bg;
	private Player player;
	private ArrayList<Enemy> enemies;
	private ArrayList<Kits> kits;
	private HUD hud;
	private Image died;
	
	public Level1State(GameStateManager gsm) {
		this.gsm = gsm;
		init();
		player.setLevel(1);
	}
	
	public void init() {
		
		tileMap = new TileMap(64);
		tileMap.loadTiles("src/main/resources/Image/tile.png");
		tileMap.loadMap("src/main/resources/Image/level1.map");
		tileMap.setPosition(0, 0);
		tileMap.setTween(0.07);
		bg = new Background("src/main/resources/Image/lvl.jpg", 0.1);
		died = new ImageIcon("src/main/resources/Image/died.jpg").getImage();

		player = new Player(tileMap);
		player.setPosition(100, 580);
		hud = new HUD(player);
		populateEnemies();
		kits = new ArrayList<Kits>();
		kits(5910,589, 1);
		kits(4277,321, 0);
		kits(2686,525, 0);
		
		
	}
	
	private void populateEnemies() {
		
		enemies = new ArrayList<Enemy>();
		
		Ghost s;
		Point[] points = new Point[] {
			new Point(6364, 634),
			new Point(6900, 634),
			new Point(3820, 267),
			new Point(3670, 267),
			new Point(3800, 543),
			new Point(3987, 543),
		};
		for(int i = 0; i < points.length; i++) {
			s = new Ghost(tileMap);
			s.setPosition(points[i].x, points[i].y);
			enemies.add(s);
		}
		
		Wolf w;
		Point[] wpoints = new Point[] {
			new Point(829, 538),
			new Point(3444, 284),
			new Point(6728, 650),
			new Point(3600, 284),
			new Point(3500, 284),
			new Point(6800, 650),
		};
		for(int i = 0; i < wpoints.length; i++) {
			w = new Wolf(tileMap);
			w.setPosition(wpoints[i].x, wpoints[i].y);
			enemies.add(w);
		}
		
	}
	
	private void kits(int x, int y, int n) {
			Kits kit = new Kits(tileMap, n);
			kit.setPosition(x, y);
			kits.add(kit);
	}
	
	public Player getPlayer()
	{
		return player;
	}
	
	public void update() {
		
		if(player.getx() > 7358  && player.getx() < 7426 && player.gety() > 638 && player.gety() < 703) {
			gsm.setState(GameStateManager.getState2());
		}
		
		if (player.isDead()) {
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			gsm.setState(GameStateManager.getState0());
		}
		
		if (player.gety() >= tileMap.getHeight() - player.getHeight()) {
			player.kill();
		}
		// top of map
		if (player.gety() < 0) {
			player.setPosition(player.getx(), 0);
		}
		
		if (player.getHealth() == 0) {
			player.kill();
		}
		
		player.update();
		hud.update();
		tileMap.setPosition(MyPanel.getFrame().getWidth() / 2 - player.getx(),
				MyPanel.getFrame().getHeight() / 2 - player.gety());
		bg.setPosition(tileMap.getx(), tileMap.gety());
		// attack enemies
		player.checkAttack(enemies);
		
		for (int i = 0; i < enemies.size(); i++) {
			Enemy e = enemies.get(i);
			e.update();
			if (e.gety() >= tileMap.getHeight() - e.getHeight()) {
				e.setDead(true);
			}
			if (e.isDead()) { 
				enemies.remove(i);
				i--;
			} else e.checkAttack(player);
		}
		
		player.checkKits(kits);
		
		for (int i = 0; i < kits.size(); i++) {
			Kits hp = kits.get(i);
			hp.update();
			if (hp.isDead()) {
				if (kits.get(i).getKey() == 0) {
					player.increaseHealth(50);
				}
				else if (kits.get(i).getKey() == 1) { 
					player.increaseHealth(100);
			
				}
				kits.remove(i);
				i--;
			}
		}
	}
	
	public void draw(Graphics g) {
		bg.draw(g);
		// draw tilemap
		tileMap.draw(g);
		// draw player
		player.draw(g);
		hud.draw(g);
		// draw enemies
		for(int i = 0; i < enemies.size(); i++) {
			enemies.get(i).draw(g);
		}
		for(int i = 0; i<kits.size(); i++) {
			kits.get(i).draw(g);
		}
		if (player.isDead()) {
			g.drawImage(died, 0, 0, null);
		}
	}
	
	public void keyPressed(int k) {
		if(k == KeyEvent.VK_A) player.setLeft(true);
		if(k == KeyEvent.VK_D) player.setRight(true);
		if(k == KeyEvent.VK_W) player.setUp(true);
		if(k == KeyEvent.VK_S) player.setDown(true);
		if(k == KeyEvent.VK_SPACE) player.setJumping(true);
		if(k == KeyEvent.VK_E) player.setScratching();
		if(k == KeyEvent.VK_R) player.setFiring();
	}
	
	public void keyReleased(int k) {
		if(k == KeyEvent.VK_A) player.setLeft(false);
		if(k == KeyEvent.VK_D) player.setRight(false);
		if(k == KeyEvent.VK_W) player.setUp(false);
		if(k == KeyEvent.VK_S) player.setDown(false);
		if(k == KeyEvent.VK_SPACE) player.setJumping(false);
	}
	
}












