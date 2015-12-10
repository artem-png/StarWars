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
	Stage level = new Stage();
	KeyListener m = new myKeyAdapter2();
	Sound music;
	Thread EnemiesFactory = new Thread(this);

	Timer time = new Timer(20, this);

	Vector<Object> enemy1 = new Vector<Object>();
	Vector<Object> enemy2 = new Vector<Object>();

	Vector<Object> booms = new Vector<Object>();

	public Map() {

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
		if (LoseWindow == 1) {
			healthhelper = 0;
			g = (Graphics2D) g;
			g.drawImage(img, igrok.layer1, 0, null);
			g.drawImage(img, igrok.layer2, 0, null);
			g.drawImage(igrok.img2, igrok.x, igrok.y, null);
			;
			SpawnEnemiesAndHealthDecrease(g);
			spawnPuli(g);
			boomPainter(g);
			scoreIndicator(g);
			healthChecking(g);
			// System.out.println("BPAGOV:"+enemy.size());
			// System.out.println("pul':"+igrok.gun.size());
			// System.out.println("Booms':"+booms.size());
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
			Iterator<Object> j = q.gun.iterator();
			while ((j.hasNext()) && (LoseWindow == 1)) { // Генерация Босса +
															// Генерация пуль +
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
			q.move();
			g.drawImage(q.img2, q.x, q.y, null);
		}

	}

	public void healthChecking(Graphics g) {
		if (igrok.health == 0) {
			if (LoseWindow == 1) {
				Font f = new Font("Arial", Font.CENTER_BASELINE, 100);
				g.setColor(Color.RED);
				g.setFont(f);
				g.drawString("Вы проиграли.", 130, 150);

				g.setColor(Color.WHITE);
				f = new Font("Arial", Font.CENTER_BASELINE, 50);
				g.setFont(f);
				g.drawString("Score : " + igrok.score, 350, 280);

				g.setColor(Color.WHITE);
				f = new Font("Arial", Font.CENTER_BASELINE, 60);
				g.setFont(f);
				g.drawString("Нажмите ENTER для новой игры", 5, 360);
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

			enemy2.add(new Boss());
			EnemiesFactory.stop();

		}
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

	}

}
