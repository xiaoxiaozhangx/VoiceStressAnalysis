package org.vsa.gui.tasks;

import java.awt.Cursor;
import java.io.IOException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JOptionPane;
import javax.swing.SwingWorker;
import org.vsa.api.VoiceStressAnalyser;
import org.vsa.audio.AudioException;
import org.vsa.gui.InterrogationWindow;
import org.vsa.util.PlotUtil;
import org.vsa.weka.VoiceStressInstance;

/**
 * DrawWaveFormTask
 */
public class DrawWaveFormTask extends SwingWorker<Void,Void> {
    
    /**
     * interrogation
     */
    private final InterrogationWindow window;

    /**
     * DrawWaveFormTask
     * 
     * @param window 
     */
    public DrawWaveFormTask(InterrogationWindow window) {
        this.window = window;
    }

    /**
     * doInBackground
     * 
     * @return
     * @throws Exception 
     */
    @Override
    protected Void doInBackground() throws Exception {
        // set wait cursor
        window.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));

        try {
            // get f0 vector
            VoiceStressInstance vsi = window.getSelectedInstance();

            // create voice stress analyser
            VoiceStressAnalyser vsa = new VoiceStressAnalyser(vsi.getPath());

            // draw it!
            PlotUtil.drawWaveForm(window, vsa.getSignal(), vsa.getSampleRate());
        } catch(IOException | UnsupportedAudioFileException | AudioException e) {
            
            // show exception message
            JOptionPane.showMessageDialog(window, e.getLocalizedMessage());
        }
        
        // set default cursor
        window.setCursor(null);

        // return
        return null;
    }
}
