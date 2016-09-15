/*
 * SMAJ-RTNIDS 2012-2013
 */
package GUI;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
 
import javax.swing.JFrame;
import javax.swing.JPanel;
 
public class AnalyseChartPanel extends JPanel {
  private int[] values;
 
  private String[] names;
 
  private String title;
 
  public AnalyseChartPanel(int[] v, String[] n, String t) {
    names = n;
    values = v;
    title = t;
  }
 
  public void paintComponent(Graphics g) {
    super.paintComponent(g);
    if (values == null || values.length == 0)
      return;
    double minValue = 0;
    double maxValue = 0;
    for (int i = 0; i < values.length; i++) {
      if (minValue > values[i])
        minValue = values[i];
      if (maxValue < values[i])
        maxValue = values[i];
    }
 
    Dimension d = getSize();
    int clientWidth = d.width;
    int clientHeight = d.height;
    int barWidth = clientWidth / values.length;
 
    Font titleFont = new Font("SansSerif", Font.BOLD, 20);
    FontMetrics titleFontMetrics = g.getFontMetrics(titleFont);
    Font labelFont = new Font("SansSerif", Font.PLAIN, 10);
    FontMetrics labelFontMetrics = g.getFontMetrics(labelFont);
 
    int titleWidth = titleFontMetrics.stringWidth(title);
    int y = titleFontMetrics.getAscent();
    int x = (clientWidth - titleWidth) / 2;
    g.setFont(titleFont);
    g.drawString(title, x, y);
 
    int top = titleFontMetrics.getHeight();
    int bottom = labelFontMetrics.getHeight();
    if (maxValue == minValue)
      return;
    double scale = (clientHeight - top - bottom) / (maxValue - minValue);
    y = clientHeight - labelFontMetrics.getDescent();
    g.setFont(labelFont);
 
    for (int i = 0; i < values.length; i++) {
      int valueX = i * barWidth + 1;
      int valueY = top;
      int height = (int) (values[i] * scale);
      if (values[i] >= 0)
        valueY += (int) ((maxValue - values[i]) * scale);
      else {
        valueY += (int) (maxValue * scale);
        height = -height;
      }
 
      if(i%2==0)
      g.setColor(Color.GREEN);
      else
       g.setColor(Color.BLUE);
      g.fillRect(valueX, valueY, barWidth - 2, height);
      g.setColor(Color.black);
      g.drawRect(valueX, valueY, barWidth - 2, height);
      int labelWidth = labelFontMetrics.stringWidth(names[i]);
      x = i * barWidth + (barWidth - labelWidth) / 2;
      g.drawString(names[i], x, y);
    }
  }
 
  
  AnalyseChartPanel(kNNStart ks,kNNTrain kt)
  {
    final JFrame f = new JFrame();
    f.setSize(400, 300);
    int[] values = new int[6];
    String[] names = new String[6];
    
    values[0] = ks.doscrt;
    names[0] = "DOS DETECTED";
    
    values[1] = kt.DOSPKTS;
    names[1] = "DOS EXPECTED";
 
    values[2] = ks.probecrt;
    names[2] = "PROBE DETECTED";
    
    values[3] = kt.PROBEPKTS;
    names[3] = "PROBE EXPECTED";
    
    values[4] = ks.norcrt;
    names[4] = "NORMAL DETECTED";
    
     values[5] = kt.NORMALPKTS;
    names[5] = "NORMAL EXPECTED";
        
    f.getContentPane().add(new AnalyseChartPanel(values, names, "Graphical Representation of Result"));
 
    WindowListener wndCloser = new WindowAdapter() {
      public void windowClosing(WindowEvent e) {
        //f.dispose();
        f.setVisible(false);
        
      }
    };
    //f.addWindowListener(wndCloser);
    f.setVisible(true);
  }
}