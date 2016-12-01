/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package textfrequensytable;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.util.Map;
import javax.swing.JFrame;
import static textfrequensytable.TextFrequensyTable.sortedMap;

/**
 *
 * @author sasha
 */
public class frameDiagramm extends JFrame {
    
    int width = 1280;
    int height = 768;
    
    public frameDiagramm () {
        super("Frequency diagram");
        setSize(1280, 768);
        setVisible(true); 
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        repaint();
    }
    
    @Override
    public void paint(Graphics g) {
        super.paint(g); 
        
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(
                RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);

        double max = 0;
        for (Map.Entry<String, Integer> e : sortedMap.entrySet()) {
            max = (double)e.getValue();
            break;
        }
        
        int x = 8;
        width = getWidth();
        height = getHeight();
        for (Map.Entry<String, Integer> e : sortedMap.entrySet()) {
            if (x >= width - 25) break;
            String text = e.getKey();
            int delta  = g.getFontMetrics().stringWidth(text) + 2;
            double scale = e.getValue() / max;
            int heig = (int)((height - 60) * scale);
            g2.setColor(Color.red);
            g2.fillRoundRect(x + delta/2 - 6, height - heig - 20, 12, heig, 6, 6);
            g2.setColor(Color.black);
            g2.drawString(text, x, height - 11);
            
            x = x + delta;
        }
    }
    
}
