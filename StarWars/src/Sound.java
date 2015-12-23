import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.AudioDevice;
import javazoom.jl.player.JavaSoundAudioDevice;
import javazoom.jl.player.advanced.AdvancedPlayer;




public class Sound implements Runnable{
	 InputStream potok;
		AdvancedPlayer explay;
Thread t= new Thread(this);
String muss;
	public  Sound(int i){

		if (i==1){
			 muss = "C:\\textures\\1.mp3";
		
		t.start();
		}
		if (i==2){
			 muss = "C:\\textures\\2.mp3";
			
			t.start();
		}


	}
	
	public static void main(String[] args) {
		Sound s= new Sound(2);
	}
	
	public void stopp(){
		t.stop();
	}

	@Override
	public void run() {
		try {
			potok = new FileInputStream(muss);
			 AudioDevice auDev = new JavaSoundAudioDevice();
			 explay = new AdvancedPlayer(potok,auDev);
			explay.play();
		} catch (JavaLayerException | FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			Thread.sleep(307000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}