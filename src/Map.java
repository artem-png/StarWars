import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.Random;
import java.util.Vector;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Map extends JPanel implements ActionListener, Runnable {
	int LoseWindow = 1;
	int healthhelper = 0;
	Image img;
	Image img2;
	Image img3;
	User igrok = new User();
	KeyListener m;
    Sound music;
	Thread EnemiesFactory = new Thread(this);

	Timer time = new Timer(20, this);

	Vector<Object> enemy = new Vector<Object>();
	
	Vector<Object> booms = new Vector<Object>();

	public Map() {
		 music=new Sound();
	
		EnemiesFactory.start();
		addKeyListener(new myKeyAdapter());
		setFocusable(true);
		time.start();
		try {
			img = ImageIO.read(new File("C:\\textures\\map.jpg"));
			img2 = ImageIO.read(new File("C:\\textures\\health.jpg"));
			img3 = ImageIO.read(new File("C:\\textures\\boom.png"));
		} catch (Exception e) {
			System.out.println(2);
		}

		;

	}

	private class myKeyAdapter extends KeyAdapter {
		public void keyPressed(KeyEvent e) {
			igrok.keyPressed(e);
		}

		public void keyReleased(KeyEvent e) {
			igrok.keyReleased(e);
		}
	}

	private class myKeyAdapter2 extends KeyAdapter {
		public void keyPressed(KeyEvent e) {
			int key = e.getKeyCode();
			if (key == KeyEvent.VK_ENTER) {

				newGame();
			}
		}
	}

	public void paint(Graphics g) {
		healthhelper = 0;
		g = (Graphics2D) g;
		g.drawImage(img, igrok.layer1, 0, null);
		g.drawImage(img, igrok.layer2, 0, null);
		g.drawImage(igrok.img2, igrok.x, igrok.y, null);

		SpawnEnemiesAndHealthDecrease(g);
		spawnPuli(g);
		boomPainter(g);
		scoreIndicator(g);
		healthChecking(g);

	}

	public void actionPerformed(ActionEvent e) {
		igrok.move();
		repaint();

	}

	public void SpawnEnemiesAndHealthDecrease(Graphics g) {
		Iterator<Object> i = enemy.iterator(); // ///“≈—“ Õ¿ œ–Œ»√–€ÿ‹, »
												// √≈Õ≈–¿÷»ﬂ ¬–¿√Œ¬
		while ((i.hasNext()) && (LoseWindow == 1)) {
			EasyEnemy1 e = (EasyEnemy1) i.next();
			if (e.getRectangle().intersects(igrok.getRectangle())) {
				igrok.health -= 1;
				booms.add(new BoomFromGun(img3,igrok.x,igrok.y));
				i.remove();
			} else {
				if (e.x < -50)
					i.remove();
				e.move();
				g.drawImage(e.img2, e.x, e.y, null);
			}
		}
	}

	public void healthChecking(Graphics g) {
		if (igrok.health == 0) {
			if (LoseWindow == 1) {
				Font f = new Font("Arial", Font.CENTER_BASELINE, 100);
				g.setColor(Color.RED);
				g.setFont(f);
				g.drawString("¬˚ ÔÓË„‡ÎË.", 130, 150);
				
				g.setColor(Color.WHITE);
				f = new Font("Arial", Font.CENTER_BASELINE, 50);
				g.setFont(f);
				g.drawString("Score : "+igrok.score, 350, 280);
				
				g.setColor(Color.WHITE);
			    f = new Font("Arial", Font.CENTER_BASELINE, 60);
				g.setFont(f); 
				g.drawString("Õ‡ÊÏËÚÂ ENTER ‰Îˇ ÌÓ‚ÓÈ Ë„˚", 5, 360);
				LoseWindow = 0;
				time.stop();
				m = new myKeyAdapter2();
				addKeyListener(m);

			}
		}
	}
	
	public void boomPainter(Graphics g){
		Iterator<Object> i = booms.iterator(); // ///“≈—“ Õ¿ œ–Œ»√–€ÿ‹, »

        while ((i.hasNext()) && (LoseWindow == 1)) {
        	BoomFromGun e=(BoomFromGun) i.next();
        	if (e.huwMuch()==true) g.drawImage(e.img,e.x+100,e.y+10,null);
        	else {
        		i.remove();
        	}
        }
	}
	
	public void scoreIndicator(Graphics g){
		Font f = new Font("Arial",Font.CENTER_BASELINE, 15);
		g.setColor(Color.WHITE);
		g.setFont(f);
		g.drawString("¬‡¯ Ò˜ÂÚ: "+igrok.score, 5, 15);
	}

	public void spawnPuli(Graphics g) {
		Iterator<Object> j = igrok.gun.iterator(); // ///////////// √ÂÌÂ‡ˆËˇ
													// ÔÛÎ¸ Ë ÍÓÎÎËÁËˇ
		while ((j.hasNext()) && (LoseWindow == 1)) {

			Gun e = (Gun) j.next();
			Iterator<Object> k = enemy.iterator(); // /////“≈—“ Õ¿ œ”À»
			while ((k.hasNext()) && (LoseWindow == 1)) {
				EasyEnemy1 z = (EasyEnemy1) k.next();
				if (e.getRectangle().intersects(z.getRectangle())) {
					z.health -= 1;
					if (z.health < 0){
						k.remove();
						igrok.score+=1;
						j.remove();
						booms.add(new BoomFromGun(img3,z.x-130,z.y-20));
					}
					else{
					j.remove();
					booms.add(new BoomFromGun(img3,z.x-130,z.y-20));
					}
				}
			}
			if (e.x > 1200)
				j.remove();
			e.move();
			g.drawImage(e.img2, e.x, e.y, null);
		}
		for (int a = 1; a <= igrok.health; a++) {
			g.drawImage(img2, 900 + healthhelper, 10, null);
			healthhelper += 30;
		}
	}
	

	public void run() {
		while (true) {
			Random rand = new Random();
			try {
				Thread.sleep(rand.nextInt(3000) + 200);
			} catch (InterruptedException e) {
				System.out.println(3);
			}
			enemy.add(new EasyEnemy1());

		}
	}
	

	public void newGame() {
		enemy.clear();
		LoseWindow = 1;
		igrok.gun.clear();
		igrok.score=0;
		igrok = new User();
		time.start();
		removeKeyListener(m);
		music.sequencer.stop();
		music=new Sound();
		
	}

}
