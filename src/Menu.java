import java.awt.Color;
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

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;


public class Menu  extends JPanel  implements Runnable{

	Thread t = new Thread(this);
	Image img1;
	Image img2;
	int choice=0;


	
	
	
	Menu(){
		try {

			img1 = ImageIO.read(new File("C:\\textures\\menu.jpg"));
		} catch (IOException e) {
			System.out.println(4);
		}
		try {

			img2 = ImageIO.read(new File("C:\\textures\\menu2.jpg"));
		} catch (IOException e) {
			System.out.println(4);
		}

		repaint();
		t.start();
		
	}
	
	public void paint(Graphics g) {

		g = (Graphics2D) g;
		if (choice==0){
		g.drawImage(img1,0,0,null);
		}
		if (choice==1){
		g.drawImage(img2,0,0,null);
		Font f= new Font("Serif", Font.PLAIN, 90);
		g.setFont(f);
        g.setColor(Color.WHITE);
		g.drawString("New Game", 400, 250);
		f= new Font("Arial", Font.PLAIN, 20);
		g.setFont(f);
        g.setColor(Color.WHITE);
        g.drawString("(space)", 560, 280);
		}
	}
	
	
	
	public void run() {
	   try {
		Thread.sleep(1000);
	} catch (InterruptedException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	   System.out.println("ALL OK");
	   choice=1;
		repaint();
	}
	
	public void stop(){
		t.stop();
		this.disable();
	}






}
