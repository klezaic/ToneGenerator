/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tonegenerator;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;

/**
 *
 * @author kr3s1m1r
 */
public class ToneThread extends Thread{
    
    public volatile int freq;
    public volatile int vol;
    public volatile boolean stop;
    public volatile boolean die;
    

    public ToneThread(int freq, int vol) {
        this.freq = freq;
        this.vol = vol;
        stop = false;
        die = false;
    }

    @Override
    public void run() {

        byte[] buf = new byte[ 1 ];
        AudioFormat af = new AudioFormat( (float )44100, 8, 1, true, false );
        SourceDataLine sdl = null;
        while(!die)
        {
            
            try {
                sdl = AudioSystem.getSourceDataLine( af );
            } catch (LineUnavailableException ex) {
                Logger.getLogger(ToneThread.class.getName()).log(Level.SEVERE, null, ex);
            }
            try {
                sdl.open();
            } catch (LineUnavailableException ex) {
                Logger.getLogger(ToneThread.class.getName()).log(Level.SEVERE, null, ex);
            }
            sdl.start();

            int i = 0;
            while(true)
            {
                playAudio(freq, vol, sdl, buf, i);
                i++;
            }
             
        }
        sdl.drain();
        sdl.stop();
    }
    
    private void playAudio(int freq, int vol, SourceDataLine sdl, byte[] buf, int i) 
    {
            double angle = i / ((float )44100 / freq ) * 2.0 * Math.PI;
            buf[ 0 ] = (byte )( Math.sin( angle ) * vol );
            sdl.write( buf, 0, 1 );
            i++;
    }
    
}
