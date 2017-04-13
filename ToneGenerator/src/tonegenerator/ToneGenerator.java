/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tonegenerator;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextArea;
import javax.swing.WindowConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 *
 * @author kr3s1m1r
 */
public class ToneGenerator {

    static int freqValue = 110;
    static int volValue = 50;
    
    static JTextArea status;
    static ToneThread tt;
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        
        
        JFrame frame = new JFrame("Tone Generator");
        frame.setSize(400, 200);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        //LogarithmicJSlider
        
        JSlider freq = new JSlider(JSlider.HORIZONTAL, 0, 20000, 110);
        freq.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                setFreqValue(freq.getValue());
            }
            });
        freq.setPreferredSize(new Dimension(200,50));
        
        JSlider vol = new JSlider(JSlider.HORIZONTAL, 0, 100, 50);
        vol.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                setVolValue(vol.getValue());
            }
            });
        vol.setPreferredSize(new Dimension(200,50));
        
        status = new JTextArea(freqValue + "Hz\t" + volValue + "%");
        status.setPreferredSize(new Dimension(200,50));
        status.setBackground(frame.getBackground());
        status.setAlignmentX(JTextArea.CENTER_ALIGNMENT);
        
        
        frame.getContentPane().add(freq, BorderLayout.NORTH);        
        frame.getContentPane().add(vol, BorderLayout.CENTER);
        frame.getContentPane().add(status, BorderLayout.SOUTH);
        
        frame.setEnabled(true);
        frame.setVisible(true);
        
        
        tt = new ToneThread(freqValue, volValue);
        tt.start();
    }
    
    
    public static void setFreqValue(int i)
    {
        tt.stop = true;
        freqValue = i;
        tt.freq = freqValue;
        tt.stop = false;
        status.setText(freqValue + "Hz\t" + volValue + "%");
        System.out.println(status.getText());
        status.update(status.getGraphics());
    }
    public static void setVolValue(int i)
    {
        tt.stop = true;
        volValue = i;
        tt.vol = volValue;
        tt.stop = false;
        status.setText(freqValue + "Hz\t" + volValue + "%");
        System.out.println(status.getText());
        status.update(status.getGraphics());
    }
}
