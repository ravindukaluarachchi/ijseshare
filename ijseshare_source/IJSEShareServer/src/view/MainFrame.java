/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import java.awt.Color;
import java.awt.GridLayout;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import javax.swing.plaf.nimbus.NimbusLookAndFeel;
import rmi.ControllerFileInterface;
import rmi.RemoteFileController;

/**
 *
 * @author ravindu
 */
public class MainFrame extends javax.swing.JFrame {

    private boolean running;
    private ControllerFileInterface fi;

    /**
     * Creates new form MainFrame
     */
    public MainFrame() {
        initComponents();
        setSize(640, 480);
        setLocationRelativeTo(null);
        panel.add(new OptionsPanel(this));
    }

    public void addToPanel(JPanel p) {
        panel.removeAll();
        panel.add(p);
        panel.repaint();
        panel.revalidate();
    }

    public void addFilesToPanel() {
        panel.removeAll();


//        for (int i = 0; i < size; i++) {
//            panel.add(new FileTile());
//        }
//        for (int i = 0; i < (5-size); i++) {
//            JPanel p = new  JPanel();
//            panel.add(p);
//        }
        try {
            List<File> files = fi.getFiles();
            int size = files.size();
            if (size >= 5) {
                panel.setLayout(new GridLayout(size, 1));
            } else {
                panel.setLayout(new GridLayout(5, 1));
            }

            for (File file : files) {
                panel.add(new FileTile(this, file));
            }

            for (int i = 0; i < (5 - size); i++) {
                JPanel p = new JPanel();
                panel.add(p);
            }

        } catch (RemoteException ex) {
            Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
        }

        panel.repaint();
        panel.revalidate();

    }

    public JPanel getPanel() {
        return panel;
    }

    public void setPanel(JPanel panel) {
        this.panel = panel;
    }

    public void connect(String port) {
        addToPanel(new ConnectingFrame());
        try {


            Registry reg = LocateRegistry.createRegistry(Integer.parseInt(port));

            ControllerFileInterface fi = new RemoteFileController();
            System.out.print("binding FileInterface...\t");

            reg.rebind("fi", fi);
            System.out.println("[done]");
            System.out.println("server is up and running.......");
            this.fi = fi;
            running = true;
            mnuAdd.setEnabled(true);
            addFilesToPanel();
        } catch (RemoteException ex) {
            //ex.printStackTrace();
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Connection error", JOptionPane.ERROR_MESSAGE);
            addToPanel(new OptionsPanel(this));

        }
    }

    public void exit() {
        if (running) {
            int i = JOptionPane.showConfirmDialog(this, "Stop the file sharing server?", "Exit Program", JOptionPane.OK_CANCEL_OPTION);
            if (i == JOptionPane.YES_OPTION) {
                System.exit(0);
            }
        } else {
            System.exit(0);
        }
    }

    public ControllerFileInterface getFi() {
        return fi;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane2 = new javax.swing.JScrollPane();
        panel = new javax.swing.JPanel();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        mnuAdd = new javax.swing.JMenuItem();
        jMenuItem2 = new javax.swing.JMenuItem();
        jSeparator1 = new javax.swing.JPopupMenu.Separator();
        jMenuItem3 = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setTitle("IJSEFileShare Server");
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        panel.setLayout(new java.awt.BorderLayout());
        jScrollPane2.setViewportView(panel);

        getContentPane().add(jScrollPane2, java.awt.BorderLayout.CENTER);

        jMenu1.setText("File");

        mnuAdd.setText("Add File");
        mnuAdd.setEnabled(false);
        mnuAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuAddActionPerformed(evt);
            }
        });
        jMenu1.add(mnuAdd);

        jMenuItem2.setText("Options");
        jMenuItem2.setEnabled(false);
        jMenu1.add(jMenuItem2);
        jMenu1.add(jSeparator1);

        jMenuItem3.setText("Exit");
        jMenuItem3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem3ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem3);

        jMenuBar1.add(jMenu1);

        setJMenuBar(jMenuBar1);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jMenuItem3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem3ActionPerformed
        exit();
    }//GEN-LAST:event_jMenuItem3ActionPerformed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        exit();
    }//GEN-LAST:event_formWindowClosing

    final void showError() {
        JOptionPane.showMessageDialog(this, "upload failed", "error", JOptionPane.ERROR_MESSAGE);
    }

    private void mnuAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuAddActionPerformed
        final JFileChooser chooser = new JFileChooser();
        chooser.showOpenDialog(this);
        final File file = chooser.getSelectedFile();
        if (file != null) {
            panel.removeAll();
            panel.add(new ConnectingFrame());
            panel.repaint();
            panel.revalidate();
            new Thread(new Runnable() {

                @Override
                public void run() {

                    try {

                        byte buffer[] = new byte[(int) file.length()];
                        BufferedInputStream input = new BufferedInputStream(new FileInputStream(file.getAbsolutePath()));
                        input.read(buffer, 0, buffer.length);
                        input.close();
                        fi.uploadFile(buffer, file.getName());
                        addFilesToPanel();
                    } catch (Exception e) {
                        e.printStackTrace();
                        showError();
                        addFilesToPanel();
                    }
                }
            }).start();
        }
    }//GEN-LAST:event_mnuAddActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /*
         * Set the Nimbus look and feel
         */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /*
         * If Nimbus (introduced in Java SE 6) is not available, stay with the
         * default look and feel. For details see
         * http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /*
         * Create and display the form
         */
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                try {
                    UIManager.setLookAndFeel(new NimbusLookAndFeel());
                } catch (UnsupportedLookAndFeelException ex) {
                    Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
                }
                new MainFrame().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JMenuItem jMenuItem3;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JPopupMenu.Separator jSeparator1;
    private javax.swing.JMenuItem mnuAdd;
    private javax.swing.JPanel panel;
    // End of variables declaration//GEN-END:variables
}
