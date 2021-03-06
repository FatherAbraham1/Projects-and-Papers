/*
 * SMAJ-RTNIDS 2012-2013
 */
package GUI;


import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.*;

public class TestProgressBar {

    private static void createAndShowUI() {
        JFrame frame = new JFrame("Initialising...");
        frame.getContentPane().add(new TestPBGui().getMainPanel());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(new Runnable() {

            @Override
            public void run() {
                createAndShowUI();
            }
        });
    }

    TestProgressBar() {
    }
}

class TestPBGui {

    private JPanel mainPanel = new JPanel();
    

    public TestPBGui() {
        
        JButton myAttempt = new JButton("START UP");
        
        myAttempt.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                myAttemptActionPerformed();
            }
        });
        
        mainPanel.add(myAttempt);
    }

    private void myAttemptActionPerformed() {
        mainPanel.hide();
        mainPanel.getParent().setVisible(false);
        
        Window thisWin = SwingUtilities.getWindowAncestor(mainPanel);
        final JDialog progressDialog = new JDialog(thisWin, "Uploading...");
        JPanel contentPane = new JPanel();
        contentPane.setPreferredSize(new Dimension(300, 100));
        final JProgressBar bar = new JProgressBar(0, 100);
        bar.setIndeterminate(true);
        contentPane.add(bar);
        progressDialog.setContentPane(contentPane);
        progressDialog.pack();
        progressDialog.setLocationRelativeTo(null);
        final Task task = new Task("My attempt");
        task.addPropertyChangeListener(new PropertyChangeListener() {

            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                if (evt.getPropertyName().equalsIgnoreCase("progress")) {
                    int progress = task.getProgress();
                    if (progress == 0) {
                        bar.setIndeterminate(true);
                    } else {
                        bar.setIndeterminate(false);
                        bar.setValue(progress);
                        progressDialog.dispose();
                    }
                }
            }
        });
        task.execute();
        progressDialog.setVisible(true);
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }
}

class Task extends SwingWorker<Void, Void> {

    private static final long SLEEP_TIME = 4000;
    private String text;

    public Task(String text) {
        this.text = text;
    }

    @Override
    public Void doInBackground() {
        setProgress(0);
        try {
            Thread.sleep(SLEEP_TIME);// imitate a long-running task
        } catch (InterruptedException e) {
        }
        setProgress(100);
        return null;
    }

    @Override
    public void done() {
        System.out.println(text + " is done");
        Toolkit.getDefaultToolkit().beep();
    }
}