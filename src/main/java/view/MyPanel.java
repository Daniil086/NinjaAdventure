package view;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class MyPanel extends JPanel implements Runnable, KeyListener {

	private static JFrame frame;
	private Thread thread;
	private boolean isRunning;
	private int FPS = 60;
	private long targetTime = 1000 / FPS;

	private GameStateManager gsm;
	
	public MyPanel(JFrame frame) {
		this.frame = frame;
		addKeyListener(this);
        setFocusable(true);
		gsm = new GameStateManager();
		start();
	}
	
	public static JFrame getFrame() {
		return frame;
	}
	
	private void start() {
        isRunning = true;
        thread = new Thread(this);
        thread.start();
    }
	
    public void run() {
        long start, elapsed, wait;
        
        while(isRunning) {
            start = System.nanoTime();
            update();
            repaint();
            elapsed = System.nanoTime() - start;
            wait = targetTime - elapsed/1000000;
            if (wait < 0) {
                wait = 5;
            }
            try {
                thread.sleep(wait);
            } catch(Exception e){
                e.printStackTrace();
            }
        }
    }
    
    public void update() {
        gsm.update();
    }
    
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        gsm.draw(g);
    }
    public void keyPressed(KeyEvent e) {
        gsm.keyPressed(e.getKeyCode());
    }
    public void keyReleased(KeyEvent e) {
        gsm.keyReleased(e.getKeyCode());
    }
    public void keyTyped(KeyEvent e) {
        
    }

}
