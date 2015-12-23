import java.io.IOException;

import javax.swing.JFrame;

public class Main {

	public static void main(String[] args) {
		JFrame fr = new JFrame("StarWars v0.1");
		fr.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		fr.setSize(1200, 600);
	
		fr.add(new Map());

		fr.setVisible(true);

	}

}
