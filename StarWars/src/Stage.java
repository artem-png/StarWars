import java.util.Random;


public class Stage {
	
	public Object stage1(){
		Random rand = new Random();
		try {
			Thread.sleep(rand.nextInt(2000) + 200);
		} catch (InterruptedException e) {
			System.out.println(3);
		}
		return new Enemies(1);
	}
	
	public Object stage2(){
		Random rand = new Random();
		try {
			Thread.sleep(rand.nextInt(300) + 100);
		} catch (InterruptedException e) {
			System.out.println(3);
		}
		return new Enemies(1);
	}

}
