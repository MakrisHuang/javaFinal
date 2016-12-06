package millionSinger;

import javax.swing.JFrame;

public class millionSinger {
	public static void main(String args[]){
		Player player = new Player();
		player.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		player.setSize(800, 600);
		player.setVisible(true);
	}
}
