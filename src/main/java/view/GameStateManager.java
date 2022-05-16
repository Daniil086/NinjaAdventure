package view;

import java.awt.Graphics;
import java.util.ArrayList;

public class GameStateManager {
	
	private GameState[] gameStates;
	private int currentState;
	private static final int NUMGAMESTATES = 3;
	private static final int MENUSTATE = 0;
	private static final int LEVEL1STATE = 1;
	private static final int LEVEL2STATE = 2;
	
	public GameStateManager() {
		gameStates = new GameState[NUMGAMESTATES];
		currentState = MENUSTATE;
		loadState(currentState);
	}
	
	public static int getState1() {
		return LEVEL1STATE;
	}
	
	public static int getState2() {
		return LEVEL2STATE;
	}
	
	public static int getState0() {
		return MENUSTATE;
	}
	
	private void loadState(int state) {
		if(state == MENUSTATE)
			gameStates[state] = new MenuState(this);
		if(state == LEVEL1STATE)
			gameStates[state] = new Level1State(this);
		if(state == LEVEL2STATE)
			gameStates[state] = new Level2State(this);
	}
	
	private void unloadState(int state) {
		gameStates[state] = null;
	}
	
	public void setState(int state) {
		unloadState(currentState);
		currentState = state;
		loadState(currentState);
	}
	
	public void update() {
		try {
			gameStates[currentState].update();
			} catch(Exception e) {
				e.printStackTrace();
			}
	}
	
	public void draw(Graphics g) {
		try {
			gameStates[currentState].draw(g);
			} catch(Exception e) {
			e.printStackTrace();
			}
	}
	
	public void keyPressed(int k) {
		gameStates[currentState].keyPressed(k);
	}
	
	public void keyReleased(int k) {
		gameStates[currentState].keyReleased(k);
	}
	
}









