import javax.swing.*;
import view.MyPanel;

public class main {
	public static void main(String[] args) {
		JFrame frame = new JFrame("Adventure of ninja");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		frame.setUndecorated(true);
		frame.add(new MyPanel(frame));
		frame.setVisible(true);
	}
}
