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
 
public class FinalAnalyseChartPanel extends JPanel {
  private int[] values;
 
  private String[] names;
 
  private String title;
 
  public FinalAnalyseChartPanel(int[] v, String[] n, String t) {
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
 
      if(i==0|| i==3 || i==6)
      g.setColor(Color.green);
      else
       g.setColor(Color.BLACK);
      g.fillRect(valueX, valueY, barWidth - 2, height);
      g.setColor(Color.black);
      g.drawRect(valueX, valueY, barWidth - 2, height);
      int labelWidth = labelFontMetrics.stringWidth(names[i]);
      x = i * barWidth + (barWidth - labelWidth) / 2;
      g.drawString(names[i], x, y);
    }
  }
 
  
  FinalAnalyseChartPanel(kNNStart ks,kNNTrain kt,DATAGEN dg)
  {
    final JFrame f = new JFrame();
    f.setSize(400, 300);
    int[] values = new int[9];
    String[] names = new String[9];
    
    //values[0] = kt.DOSPKTS;
    values[0] = dg.ExDos;
    names[0] = "DOS EXPECTED";
    values[1] = ks.doscrt;
    names[1] = "k-NN DETECTED";
    values[2]=dg.DetDos;
    names[2] = "DTREE Detected";
    
 
    //values[3] = kt.PROBEPKTS;
    values[3] = dg.ExProbe;
    names[3] = "PROBE EXPECTED";
    values[4] = ks.probecrt;
    names[4] = "k-NN DETECTED";
    values[5]=dg.DetProbe;
    names[5] = "DTREE Detected";
    
    
    //values[6] = kt.NORMALPKTS;
    values[6] = dg.ExNorm;
    names[6] = "NORMAL EXPECTED";
    values[7] = ks.norcrt;
    names[7] = "k-NN DETECTED";
    values[8]=dg.DetNorm;
    names[8] = "DTREE Detected";
        
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