import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.IOException;

import javax.swing.JFrame;

public class Main {

	public static void main(String[] args) {
		Sound s = new Sound(2);
		JFrame fr = new JFrame("StarWars v0.1");
		fr.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		fr.setSize(1200, 600);
		Menu m = new Menu();
		fr.add(m);
		fr.setVisible(true);
		fr.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {

				int key = e.getKeyCode();
				if (key == KeyEvent.VK_SPACE) {
					m.choice = 5;
					m.stop();
				}

			}
		});

		while (m.choice != 5)
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		s.stopp();
		s = new Sound(1);

		fr.dispose();

		fr = new JFrame("StarWars v0.1");
		fr.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		fr.setSize(1200, 630);
		fr.add(new Map());
		fr.setVisible(true);

	}

}
