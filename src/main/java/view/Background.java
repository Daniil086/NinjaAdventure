package view;

import java.awt.*;
import javax.swing.ImageIcon;

public class Background {
	
	private Image image;
	private double x;
	private double y;
	private double dx;
	private double dy;
	private double moveScale;
	
	public Background(String s, double ms) {
		
		try {
			image = new ImageIcon(s).getImage();
			moveScale = ms;
		} catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public void setPosition(double x, double y) {
		this.x = (x * moveScale) % MyPanel.getFrame().getWidth();
		this.y = (y * moveScale) % MyPanel.getFrame().getHeight();
	}
	
	public void setVector(double dx, double dy) {
		this.dx = dx;
		this.dy = dy;
	}
	
	public void update() {
		x += dx;
		y += dy;
	}
	
	public void draw(Graphics g) {
		
		g.drawImage(image, (int)x, (int)y, null);
		
		if(x < 0) {
			g.drawImage(image, (int)x + MyPanel.getFrame().getWidth(), (int)y, null);
		}
		if(x > 0) {
			g.drawImage(image, (int)x - MyPanel.getFrame().getWidth(), (int)y, null);
		}
	}
	
}
