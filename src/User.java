import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.util.Vector;

import javax.imageio.ImageIO;

public class User  {
	public static final int MAX_TOP = 2;
	public static final int MAX_BOT = 490;
	Image img2;
	int x = 20;
	int y = 200;
	int dy = 0;
	int dx = 0;
	int score=0 ;
	boolean isGunOk = true;
	boolean isNeed = true;

	int health=2;
	int v = 5;

	Vector<Object> gun = new Vector();


	int layer1 = 0;
	int layer2 = 1200;

	public User() {

		try {
			img2 = ImageIO
					.read(new File("C:\\textures\\rocket.png"));
		} catch (IOException e) {
			System.out.println(1);
		}

	}
	
	public Rectangle getRectangle(){
		return new Rectangle(x+170,y+25,img2.getWidth(null)-220,img2.getHeight(null)-50);
	}
	
	public void restart(){
		x = 20;
		y = 200;
		dy = 0;
		dx = 0;
		score=0 ;
		isGunOk = true;
		health=2;
	    layer1 = 0;
		layer2 = 1200;
		gun.clear();
	}

	public void move() {
		y += dy;
		if (y < MAX_TOP)
			y = MAX_TOP;
		if (y > MAX_BOT)
			y = MAX_BOT;
		x += dx;
		if (x < 5)
			x = 5;
		if (x > 300)
			x = 300;
		if (layer2 - v <= 0) {
			layer1 = 0;
			layer2 = 1200;
		} else {
			layer1 -= v;
			layer2 -= v;
		}
	}

	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();
		if (key == KeyEvent.VK_DOWN) {
			if (dy < 8)
				dy += 8;
			try {
				img2 = ImageIO
						.read(new File("C:\\textures\\rocketup.png"));
			} catch (IOException n) {
				System.out.println(1);
			}

		}
		if (key == KeyEvent.VK_UP) {
			if (dy > -8)
				dy -= 8;
			try {
				img2 = ImageIO
						.read(new File("C:\\textures\\rocketdown.png"));
			} catch (IOException n) {
				System.out.println(1);
			}
		}
		if (key == KeyEvent.VK_RIGHT) {
			if (dx < 8)
				dx += 8;
			try {
				img2 = ImageIO 
						.read(new File("C:\\textures\\rocketspeed.png"));
			} catch (IOException n) {
				System.out.println(1);
			}
		}
		if (key == KeyEvent.VK_LEFT) {
			if (dx > -8)
				dx -= 8;
		}
		if (key == KeyEvent.VK_SPACE) {
			if (isGunOk == true) {
				Gun n = new Gun(x + 250, y + 40);
				gun.add(n);
				isGunOk=false;
			}
		}

	}

	public void keyReleased(KeyEvent e) {
		int key = e.getKeyCode();
		if (key == KeyEvent.VK_DOWN) {
			dy = 0;
			try {
				img2 = ImageIO
						.read(new File("C:\\textures\\rocket.png"));
			} catch (IOException n) {
				System.out.println(1);
			}
		}
		if (key == KeyEvent.VK_UP) {
			dy = 0;
			try {
				img2 = ImageIO
						.read(new File("C:\\textures\\rocket.png"));
			} catch (IOException n) {
				System.out.println(1);
			}
		}
		if (key == KeyEvent.VK_RIGHT) {
			dx = 0;
			try {
				img2 = ImageIO
						.read(new File("C:\\textures\\rocket.png"));
			} catch (IOException n) {
				System.out.println(1);
			}
		}
		if (key == KeyEvent.VK_LEFT) {
			dx = 0;
		}
		if (key == KeyEvent.VK_SPACE) {
			isGunOk=true;
		}

	}

	
	
	
}
