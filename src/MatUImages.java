import java.awt.Image;
import java.awt.Rectangle;
import java.io.File;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;

public class MatUImages {
	Image imgR;
	Image imgY;
	Image imgBlue;
	Image imgBrown;
	Image imgG;
	Image imgB;
	Image one;
	Image two;
	Image three;
	Image four;
	Random k = new Random();
	String[][] mas = new String[9][5];

	MatUImages() {
		try {

			imgR = ImageIO.read(new File("C:\\textures\\poleRed.jpg"));
			imgY = ImageIO.read(new File("C:\\textures\\poleYellow.jpg"));
			imgBlue = ImageIO.read(new File("C:\\textures\\poleBlue.jpg"));
			imgBrown = ImageIO.read(new File("C:\\textures\\poleBrown.jpg"));
			imgG = ImageIO.read(new File("C:\\textures\\poleGreen.jpg"));
			imgB = ImageIO.read(new File("C:\\textures\\poleBlack.jpg"));
		} catch (IOException e) {
			System.out.println(4);
		}
		mas[0][0] = "(1+1)*(7-3)";
		mas[0][1] = "8";
		mas[0][2] = "4";
		mas[0][3] = "14";
		mas[0][4] = "3";

		mas[1][0] = "7*7-11";
		mas[1][1] = "38";
		mas[1][2] = "48";
		mas[1][3] = "60";
		mas[1][4] = "39";

		mas[2][0] = "100-64-4";
		mas[2][1] = "32";
		mas[2][2] = "22";
		mas[2][3] = "36";
		mas[2][4] = "30";

		mas[3][0] = "1+1+1*0";
		mas[3][1] = "2";
		mas[3][2] = "0";
		mas[3][3] = "3";
		mas[3][4] = "1";

		mas[4][0] = "5*5*5-35";
		mas[4][1] = "90";
		mas[4][2] = "100";
		mas[4][3] = "105";
		mas[4][4] = "85";

		mas[5][0] = "34-4-26";
		mas[5][1] = "4";
		mas[5][2] = "-4";
		mas[5][3] = "2";
		mas[5][4] = "8";

		mas[6][0] = "(36-17)-2";
		mas[6][1] = "17";
		mas[6][2] = "19";
		mas[6][3] = "20";
		mas[6][4] = "15";

		mas[7][0] = "3*4-5-1";
		mas[7][1] = "6";
		mas[7][2] = "7";
		mas[7][3] = "8";
		mas[7][4] = "5";

		mas[8][0] = "(34-6)/2";
		mas[8][1] = "14";
		mas[8][2] = "16";
		mas[8][3] = "15,5";
		mas[8][4] = "12";

	}

	public int randomMat() {
		return k.nextInt(9);
	}

	public boolean checkCollision(Rectangle r, int m) {


		if (m == 1)
			if (r.intersects(new Rectangle(0, 150, two.getWidth(null), two
					.getHeight(null)))
					|| r.intersects(new Rectangle(0, 300, three.getWidth(null),
							three.getHeight(null)))
					|| r.intersects(new Rectangle(0, 450, four.getWidth(null),
							four.getHeight(null)))) {
				return true;
			}
		if (m == 2)
			if (r.intersects(new Rectangle(0, 0, one.getWidth(null), one
					.getHeight(null)))
					|| r.intersects(new Rectangle(0, 300, three.getWidth(null),
							three.getHeight(null)))
					|| r.intersects(new Rectangle(0, 450, four.getWidth(null),
							four.getHeight(null)))) {
				return true;
			}
		if (m == 3)
			if (r.intersects(new Rectangle(0, 150, two.getWidth(null), two
					.getHeight(null)))
					|| r.intersects(new Rectangle(0, 0, one.getWidth(null), one
							.getHeight(null)))
					|| r.intersects(new Rectangle(0, 450, four.getWidth(null),
							four.getHeight(null)))) {
				return true;
			}
		if (m == 4)
			if (r.intersects(new Rectangle(two.getWidth(null), two
					.getHeight(null)))
					|| r.intersects(new Rectangle(three.getWidth(null), three
							.getHeight(null)))
					|| r.intersects(new Rectangle(one.getWidth(null), one
							.getHeight(null)))) {
				return true;
			}
		return false;
	}

	public void randomImg() {
		int a = k.nextInt(9);
		if (a == 0) {
			one = imgR;
			two = imgG;
			three = imgBlue;
			four = imgY;
		}
		if (a == 1) {
			one = imgBrown;
			two = imgB;
			three = imgY;
			four = imgR;
		}
		if (a == 2) {
			one = imgY;
			two = imgBrown;
			three = imgG;
			four = imgB;
		}
		if (a == 3) {
			one = imgBlue;
			two = imgR;
			three = imgG;
			four = imgY;
		}
		if (a == 4) {
			one = imgBlue;
			two = imgR;
			three = imgBrown;
			four = imgB;
		}
		if (a == 5) {
			one = imgB;
			two = imgG;
			three = imgY;
			four = imgBrown;
		}
		if (a == 6) {
			one = imgR;
			two = imgB;
			three = imgG;
			four = imgBlue;
		}
		if (a == 7) {
			one = imgY;
			two = imgB;
			three = imgBrown;
			four = imgG;
		}
		if (a == 8) {
			one = imgB;
			two = imgR;
			three = imgBlue;
			four = imgBrown;
		}

	}
}
