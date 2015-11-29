import java.awt.Image;
import java.awt.Rectangle;
import java.io.File;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;


public class EasyEnemy1 {
	public static final int MAX_TOP = 2;
	public static final int MAX_BOT = 490;
	Image img2;
	int x=1250;
    int y;
    int dy=0;
    int dx=0;
    int v=5;
    int isNeed=0;
    int health=1;
   
    public EasyEnemy1(){
    	 try {
			img2= ImageIO.read(new File("C:\\textures\\enemy1.png"));
		} catch (IOException e) {
			System.out.println(4);
		}
    	Random k=new Random();
    	y=k.nextInt(490);
    }
    
    public void move(){
    	x-=6;
    	}
    
    public Rectangle getRectangle(){
		return new Rectangle(x,y,img2.getWidth(null),img2.getHeight(null));
	}
	
	
}
