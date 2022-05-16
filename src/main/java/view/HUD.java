package view;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

public class HUD {
	
	private Player player;
	
	private BufferedImage[] image;
	private BufferedImage[] hp;
	private Image hud, hud1;
	private int numFrames = 6;
	private Animation animation;
	private Font font;
	
	public HUD(Player p) {
		player = p;
		try {
			hud = new ImageIcon("src/main/resources/Image/Hud.png").getImage();
			hud1 = new ImageIcon("src/main/resources/Image/Hud1.png").getImage();
			font = Font.createFont(Font.TRUETYPE_FONT, new File("src/main/resources/shrift.ttf")).deriveFont(40f);
			image = new BufferedImage[numFrames];
			for(int i = 0; i < numFrames; i++) {
				String name = "src/main/resources/Animation/A7/" + i + ".png";
				image[i] = ImageIO.read(new File(name));
			}
			hp = new BufferedImage[11];
			for(int i = 0; i < 11; i++) {
				String name = "src/main/resources/Image/hud/" + i*10 + ".png";
				hp[i] = ImageIO.read(new File(name));
			}
			
			animation = new Animation();
			animation.setFrames(image);
			animation.setDelay(100);
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void update() {
		animation.update();
	}
	
	public void draw(Graphics g) {
		
		if (player.getHealth() == 100) g.drawImage(hp[10], 0, 0, null);
		if (player.getHealth() >= 90 && player.getHealth() < 100) g.drawImage(hp[9], 0, 0, null);
		if (player.getHealth() >= 80 && player.getHealth() < 90) g.drawImage(hp[8], 0, 0, null);
		if (player.getHealth() >= 70 && player.getHealth() < 80) g.drawImage(hp[7], 0, 0, null);
		if (player.getHealth() >= 60 && player.getHealth() < 70) g.drawImage(hp[6], 0, 0, null);
		if (player.getHealth() >= 50 && player.getHealth() < 60) g.drawImage(hp[5], 0, 0, null);
		if (player.getHealth() >= 40 && player.getHealth() < 50) g.drawImage(hp[4], 0, 0, null);
		if (player.getHealth() >= 30 && player.getHealth() < 40) g.drawImage(hp[3], 0, 0, null);
		if (player.getHealth() >= 20 && player.getHealth() < 30) g.drawImage(hp[2], 0, 0, null);
		if (player.getHealth() >= 10 && player.getHealth() < 20) g.drawImage(hp[1], 0, 0, null);
		if (player.getHealth() >= 0 && player.getHealth() < 10) g.drawImage(hp[0], 0, 0, null);
		g.drawImage(hud1, 80, 80, null);
		g.setFont(font);
		g.setColor(Color.WHITE);
		g.drawString(player.getFire() / 100 + "/" + player.getMaxFire() / 100, 135, 115);
		}
	
}













