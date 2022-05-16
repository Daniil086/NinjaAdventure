package view;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.File;

import javax.swing.*;

public class MenuState extends GameState {
	
    private String[] options = {"Continue", "New Game", "Quit"};
    private int currentSelection = 1;
    private Image img, image;
    private Background bg;
    private Color titleColor;
	private Font titleFont = null;
	private Font font = null;
    
    public MenuState(GameStateManager gsm) {
    	this.gsm = gsm;
    	init();
    }

    public void init() {
    	
    	try {
    		img = new ImageIcon("src/main/resources/Image/menu.jpg").getImage();
    		image = new ImageIcon("src/main/resources/Image/menu2.png").getImage();
    		bg = new Background("src/main/resources/Image/menu3.png", 1);
			bg.setVector(-0.1, 0);
			titleColor = new Color(255, 0, 0);
			titleFont = Font.createFont(Font.TRUETYPE_FONT, new File("src/main/resources/shrift.ttf")).deriveFont(125f);
			titleFont.deriveFont(Font.BOLD);
			font = Font.createFont(Font.TRUETYPE_FONT, new File("src/main/resources/shrift.ttf")).deriveFont(70f);
    	}
		catch(Exception e) {
			e.printStackTrace();
		}
    }
    
    public void update() {
    	bg.update();
    	
    }
    
    public void draw(Graphics g) {
        g.drawImage(img, 0, 0,MyPanel.getFrame().getWidth(), MyPanel.getFrame().getHeight(), null);
        bg.draw(g);
        g.drawImage(image, 0, 0,MyPanel.getFrame().getWidth(), MyPanel.getFrame().getHeight(), null);
        g.setColor(titleColor);
		g.setFont(titleFont);
		g.drawString("Adventure of ninja", 250, 100);
			for( int i = 0; i < options.length; i++){
				if(i == currentSelection) {
					g.setColor(Color.RED);
				} else {
					g.setColor(Color.WHITE);
				}
				g.setFont(font);
				g.drawString(options[i], MyPanel.getFrame().getWidth()/2 - 70, 560 + i * 75);
			}
        
    }
    
    public void keyPressed(int k) {
        if(k == KeyEvent.VK_DOWN) {
            currentSelection++;
            if(currentSelection >= options.length) {
                currentSelection = 0;
            } 
        } else if(k == KeyEvent.VK_UP) {
            currentSelection--;
            if(currentSelection < 0) {
                currentSelection = options.length - 1;
            } 
        }
        if(k == KeyEvent.VK_ENTER) {
            if(currentSelection == 0) {
            	gsm.setState(0);
            } else if(currentSelection == 1) {
            	gsm.setState(GameStateManager.getState2());
            } else if(currentSelection == 2) {
                System.exit(0);
            }
        }
    }
    public void keyReleased(int k) {
        
    }
}
