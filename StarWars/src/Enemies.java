import java.awt.Image;
import java.awt.Rectangle;
import java.io.File;
import java.io.IOException;
import java.util.Random;
import java.util.Vector;

import javax.imageio.ImageIO;

public class Enemies {
	public static final int MAX_TOP = 2;
	public static final int MAX_BOT = 490;
	Image img2;
	int x = 1300;
	int y;
	int boomX;
	int boomY;
	int maxyup;
	int maxydown;
	int dy = 0;
	int dx = 0;
	int v = 5;
	int isNeed = 0;
	int schet = -1;
	int health;
	int id;
	Random k = new Random();
	Vector<Object> gun;

	public Enemies(int i) {
		if (i==1){
			id=1;
			try {
				img2 = ImageIO.read(new File("C:\\textures\\enemy1.png"));
			} catch (IOException e) {
				System.out.println(4);
			}
			y = k.nextInt(490);
			health = 1;
			boomXY(-130,-20);
		}
		if (i==2){
			id=2;
			try {
				img2 = ImageIO.read(new File("C:\\textures\\enemy1.png"));
			} catch (IOException e) {
				System.out.println(4);
			}
			y = k.nextInt(490);
			maxyup= y-50;
			maxydown= y+50;
			health = 1;
			boomXY(-130,-20);
		}
		
	}

	public void boomXY(int x,int y){
		this.boomX=x;
		this.boomY=y;
	}

	public void move() {
		if (id==1){
			x -= 6;
		}
		if (id==2){
			x -= 9;
			if (schet== -1) {
				dy= -3;
			}
			if (y>maxydown) dy*=-1;
			if (y<maxyup) dy*=-1;

			y += dy;
			schet=0;
		}
		
		

	}

	public Rectangle getRectangle() {
		return new Rectangle(x, y, img2.getWidth(null), img2.getHeight(null));
	}
	
	public void delete(){

	}


	
}
