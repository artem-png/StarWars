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
	int stageBoss;

	int helper;
	User igrok = new User();
	Stage level = new Stage();
	KeyListener m = new myKeyAdapter2();
	Sound music;
	Thread EnemiesFactory = new Thread(this);
	String m0;
	String m1;
	String m2;
	String m3;
	String m4;
	Timer time = new Timer(20, this);

	Vector<Object> enemy1 = new Vector<Object>();
	Vector<Object> enemy2 = new Vector<Object>();

	Vector<Object> booms = new Vector<Object>();
	MatUImages mat;
	int stageBoss2 = 0;

	public Map() {
		mat = new MatUImages();
		EnemiesFactory.start();
		addKeyListener(new myKeyAdapter());
		setFocusable(true);
		time.start();
		try {
			img = ImageIO.read(new File("C:\\textures\\map.jpg"));
			img2 = ImageIO.read(new File("C:\\textures\\health.png"));
			img3 = ImageIO.read(new File("C:\\textures\\boom.png"));
		} catch (Exception e) {
			System.out.println(2);
		}

		;

	}

	private class myKeyAdapter extends KeyAdapter {
		public void keyPressed(KeyEvent e) {
			if (stageBoss == 0) {
				igrok.keyPressed(e);
			} else {
				int key = e.getKeyCode();
				if (key == KeyEvent.VK_SPACE) {

				}

				else {
					igrok.keyPressed(e);
				}
			}

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
		if (LoseWindow == 1) {
			healthhelper = 0;
			g = (Graphics2D) g;
			if (stageBoss == 0) {
				g.drawImage(img, igrok.layer1, 0, null);
				g.drawImage(img, igrok.layer2, 0, null);
				g.drawImage(igrok.img2, igrok.x, igrok.y, null);

				SpawnEnemiesAndHealthDecrease(g);
			} else {

				g.drawImage(mat.one, 0, 0, null);
				g.drawImage(mat.two, 0, 150, null);
				g.drawImage(mat.three, 0, 300, null);
				g.drawImage(mat.four, 0, 450, null);

				if (stageBoss2 == 1) {
					Boss r = (Boss) enemy2.get(0);
					r.x += 10;
					SpawnEnemiesAndHealthDecrease(g);
					if (r.x > 1400) {

						stageBoss = 0;
						stageBoss2 = 0;
						enemy2.remove(0);
					}
				} else {
					SpawnEnemiesAndHealthDecrease(g);
					SecondBoss(g);
				}
				g.drawImage(igrok.img2, igrok.x, igrok.y, null);

			}
			spawnPuli(g);
			boomPainter(g);
			scoreIndicator(g);
			healthChecking(g);
		}

	}

	public void actionPerformed(ActionEvent e) {
		igrok.move();
		repaint();

	}

	public void SpawnEnemiesAndHealthDecrease(Graphics g) {
		Iterator<Object> i = enemy1.iterator();
		while ((i.hasNext()) && (LoseWindow == 1)) {
			Enemies e = (Enemies) i.next(); // /Генерация Врага +
											// Коллизия(игрок,враги)
			if (e.getRectangle().intersects(igrok.getRectangle())) {
				igrok.health -= 1;
				booms.add(new BoomFromGun(img3, igrok.x, igrok.y));
				i.remove();
			} else {
				if (e.x < -50)
					i.remove();
				e.move();

				g.drawImage(e.img2, e.x, e.y, null);
			}
		}

		Iterator<Object> s = enemy2.iterator();
		while ((s.hasNext()) && (LoseWindow == 1)) {
			Boss q = (Boss) s.next();
			if (q.gun != null) {
				Iterator<Object> j = q.gun.iterator();
				while ((j.hasNext()) && (LoseWindow == 1)) { // Генерация Босса
																// +
																// Генерация
																// пуль +
																// Коллизия(игрок,пули)
					GunBoss n = (GunBoss) j.next();
					if (n.x < 0) {
						j.remove();
					}
					if (igrok.getRectangle().intersects(n.getRectangle())) {
						j.remove();
						igrok.health -= 1;

					}
					n.move();
					g.drawImage(n.img2, n.x, n.y, null);

				}
			}
			q.move();
			g.drawImage(q.img2, q.x, q.y, null);

		}

	}

	public void healthChecking(Graphics g) {
		if (igrok.health <= 0) {
			if (LoseWindow == 1) {
				Font f = new Font("Arial", Font.CENTER_BASELINE, 100);
				g.setColor(Color.RED);
				g.setFont(f);
				g.drawString("Вы проиграли.", 235, 150);

				g.setColor(Color.WHITE);
				f = new Font("Arial", Font.CENTER_BASELINE, 50);
				g.setFont(f);
				g.drawString("Score : " + igrok.score, 475, 280);

				g.setColor(Color.WHITE);
				f = new Font("Arial", Font.CENTER_BASELINE, 60);
				g.setFont(f);
				g.drawString("Нажмите ENTER для новой игры", 100, 360);
				LoseWindow = 0;
				time.stop();
				addKeyListener(m);
				EnemiesFactory.stop();

			}
		}
	}

	public void boomPainter(Graphics g) {
		Iterator<Object> i = booms.iterator(); // ///ТЕСТ НА ПРОИГРЫШЬ, И

		while ((i.hasNext()) && (LoseWindow == 1)) {
			BoomFromGun e = (BoomFromGun) i.next();
			if (e.huwMuch() == true)
				g.drawImage(e.img, e.x + 100, e.y + 10, null);
			else {
				i.remove();
			}
		}
	}

	public void scoreIndicator(Graphics g) {
		Font f = new Font("Arial", Font.CENTER_BASELINE, 15);
		g.setColor(Color.WHITE);
		g.setFont(f);
		g.drawString("Ваш счет: " + igrok.score, 5, 15);
	}

	public void spawnPuli(Graphics g) {
		Iterator<Object> j = igrok.gun.iterator(); // ///////////// Генерация
													// пуль и коллизия
		while ((j.hasNext()) && (LoseWindow == 1)) {

			Gun e = (Gun) j.next();
			Iterator<Object> k = enemy1.iterator(); // /////ТЕСТ НА ПУЛИ
			while ((k.hasNext()) && (LoseWindow == 1)) {
				Enemies z = (Enemies) k.next();
				if (e.getRectangle().intersects(z.getRectangle())) {
					z.health -= 1;
					if (z.health < 0) {
						z.delete();
						k.remove();
						igrok.score += 1;
						j.remove();
						booms.add(new BoomFromGun(img3, z.x + z.boomX, z.y
								+ z.boomY));
					} else {
						j.remove();
						booms.add(new BoomFromGun(img3, z.x + z.boomX, z.y
								+ z.boomY));
					}
				}
			}

			Iterator<Object> p = enemy2.iterator();
			while ((p.hasNext()) && (LoseWindow == 1)) {
				Boss r = (Boss) p.next();
				if (e.getRectangle().intersects(r.getRectangle())) {
					r.health -= 1;
					if (r.health < 0) {
						r.delete();
						p.remove();
						igrok.score += 1;
						j.remove();
						booms.add(new BoomFromGun(img3, r.x + r.boomX, r.y
								+ r.boomY));
					} else {
						j.remove();
						booms.add(new BoomFromGun(img3, r.x + r.boomX, r.y
								+ r.boomY));
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

			for (int i = 1; i <= 12; i++) {
				enemy1.add(level.stage1());
			}
			for (int i = 1; i <= 12; i++) {
				enemy1.add(level.stage2());
			}

			enemy2.add(level.stage3());
			while (enemy2.size() != 0) {
				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			for (int i = 1; i <= 12; i++) {
				enemy1.add(level.stage4());
			}
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			enemy2.add(new Boss(2));
			try {
				Thread.sleep(5200);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			stageBoss = 1;

			Boss r = (Boss) enemy2.get(0);
			r.changeimg("boss2Mat");
			while (r.health > 1) {
				mat.randomImg();
				int b = mat.randomMat();
				m0 = mat.mas[b][0];
				m1 = mat.mas[b][1];
				System.out.println("Ответ " + m1);
				String check = m1;
				m2 = mat.mas[b][2];
				m3 = mat.mas[b][3];
				m4 = mat.mas[b][4];
				int n = mat.k.nextInt(3);
				if (n == 0) {
					String q = m1;
					m1 = m2;
					m2 = q;
				}
				if (n == 1) {
					String q = m1;
					m1 = m3;
					m3 = q;
				}
				if (n == 2) {
					String q = m1;
					m1 = m4;
					m4 = q;
				}
				try {
					Thread.sleep(6000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}

				int isAlive = 0;
				if (check == m1) {
					helper = 1;
					if (mat.checkCollision(igrok.getRectangle(), 1) == true) {

						isAlive = 1;
						;

					}

				}
				if (check == m2) {
					helper = 2;
					if (mat.checkCollision(igrok.getRectangle(), 2) == true) {
						isAlive = 1;

					}

				}
				if (check == m3) {
					helper = 3;
					if (mat.checkCollision(igrok.getRectangle(), 3) == true) {
						isAlive = 1;

					}

				}
				if (check == m4) {
					helper = 4;
					if (mat.checkCollision(igrok.getRectangle(), 4) == true) {
						isAlive = 1;
					}
				}
				if (isAlive == 1)
					igrok.health = 0;

				r.health -= 1;

			}
			r.changeimg("boss2");
			stageBoss2 = 1;
			EnemiesFactory.stop();
		}

	}

	public void SecondBoss(Graphics g) {

		Font f = new Font("Arial", 120, 120);
		g.setColor(Color.WHITE);
		g.setFont(f);
		try{
		g.drawString(m1, 90, 115);
		g.drawString(m2, 90, 265);
		g.drawString(m3, 90, 415);
		g.drawString(m4, 90, 565);
		}catch(Exception e){};

		f = new Font("Arial", 35, 35);
		g.setColor(Color.BLACK);
		g.setFont(f);
		g.drawString(m0, 750, 65);
	}

	public void newGame() {
		EnemiesFactory = null;
		EnemiesFactory = new Thread(this);
		EnemiesFactory.start();
		enemy1.clear();
		enemy2.clear();
		LoseWindow = 1;
		igrok.restart();
		time.start();
		removeKeyListener(m);
		stageBoss = 0;
		stageBoss2 = 0;

	}

}
