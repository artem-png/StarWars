import java.awt.Image;
import java.awt.Rectangle;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Gun {
	Image img2;
	int x;
	int y;

	public Gun(int x, int y) {
		this.x = x;
		this.y = y;
		try {
			img2 = ImageIO.read(new File(
					"C:\\textures\\firstAtack.jpg"));
		} catch (IOException e) {
			System.out.println(5);
		}

	}
	
	public Rectangle getRectangle(){
		return new Rectangle(x,y,img2.getWidth(null),img2.getHeight(null));
	}
	public void move(){
		x+=10;
	}
}
