import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Sequence;
import javax.sound.midi.Sequencer;


public class Sound{

    Sequence sequence;
    Sequencer sequencer;

	public  Sound(){

			try {
				File sound=new File("C:\\textures\\sound.mid");
				sequence = MidiSystem.getSequence(sound);
			 sequencer = MidiSystem.getSequencer();
		        sequencer.open();
		        sequencer.setSequence(sequence);
		        sequencer.start();
			} catch (InvalidMidiDataException | IOException | MidiUnavailableException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	        

	}
}