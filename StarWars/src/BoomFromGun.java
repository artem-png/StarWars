import java.awt.Image;


public class BoomFromGun {
    Image img;
    int x;
    int y;
    long Begin;
    double MAX= 150;
	public BoomFromGun(Image i,int x,int y){
		this.img=i;
		this.x=x;
		this.y=y;
		Begin = System.currentTimeMillis();

	}
	public boolean huwMuch(){
		long End = System.currentTimeMillis();
		if ((End-Begin)>MAX){
			return false;
		}
		else return true;
		
	}
	
}
