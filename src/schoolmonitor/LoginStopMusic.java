/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package schoolmonitor;

import com.sun.glass.events.KeyEvent;
import java.awt.Image;
import java.awt.Toolkit;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import javax.swing.ImageIcon;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;
import schoolmonitor.MainFrame;
import schoolmonitor.MainMonitor;

/**
 *
 * @author John Venice Almazan
 */
public class LoginStopMusic extends javax.swing.JFrame {

    /**
     * Creates new form LoginPage
     */
    Connection myconObj = null;
    Statement mystatObj = null;
    ResultSet myresObj = null;
    FileInputStream FIS;
    BufferedInputStream BIS;
    Player player;
    File myFile = null;

    public LoginStopMusic() {
        this.setUndecorated(true);
        this.setResizable(false);
        initComponents();
        setfullscreen();
        updateicons();
        playmusic();
    }

    private void setfullscreen() {
        Toolkit tk = Toolkit.getDefaultToolkit();
        int xsize = (int) tk.getScreenSize().getWidth();
        int ysize = (int) tk.getScreenSize().getHeight();
        this.setSize(xsize, ysize);
    }

    private void updateicons() {
        String imgLoginpath = "LoginLogo.png";
        String imgUserpath = "UserLogo.png";
        String imgPasswordpath = "PasswordLogo.png";

        ImageIcon login = new ImageIcon(imgLoginpath);
        ImageIcon User = new ImageIcon(imgUserpath);
        ImageIcon Pass = new ImageIcon(imgPasswordpath);

        Image imgLogin = login.getImage().getScaledInstance(LoginLogo.getWidth(), LoginLogo.getHeight(), Image.SCALE_SMOOTH);
        Image imgUser = User.getImage().getScaledInstance(UserLogo.getWidth(), UserLogo.getHeight(), Image.SCALE_SMOOTH);
        Image imgPass = Pass.getImage().getScaledInstance(PassLogo.getWidth(), PassLogo.getHeight(), Image.SCALE_SMOOTH);

        ImageIcon LoginLogoimg = new ImageIcon(imgLogin);
        ImageIcon UserLogoimg = new ImageIcon(imgUser);
        ImageIcon PassLogoimg = new ImageIcon(imgPass);

        LoginLogo.setIcon(LoginLogoimg);
        UserLogo.setIcon(UserLogoimg);
        PassLogo.setIcon(PassLogoimg);

    }

    public void playmusic() {
        String music = "siren.mp3";
        File musicpath = new File(music);
        myFile = musicpath;

        try {
            FIS = new FileInputStream(myFile);
            BIS = new BufferedInputStream(FIS);
            player = new Player(BIS);

            new Thread() {
                public void run() {
                    try {
                        player.play();
                    } catch (JavaLayerException ex) {
                        Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }.start();
        } catch (JavaLayerException e) {
            Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, e);
        } catch (IOException e) {
            Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, e);
        }

    }

    public void stopmusic() {
        if (player != null) {
            player.close();
        }
    }

    private void login() {
        String url = "jdbc:derby://localhost:1527/SchoolMonitor";
        String user = "root";
        String pass = "password";

        try {
            myconObj = DriverManager.getConnection(url, user, pass);
            PreparedStatement PStatement = null;
            ResultSet Rs;
            String sql = "select * FROM ROOT.LOGIN WHERE Username = ? and Password = ?";
            PStatement = myconObj.prepareStatement(sql);
            PStatement.setString(1, tUsername.getText());
            PStatement.setString(2, pfPassword.getText());
            Rs = PStatement.executeQuery();

            if (Rs.next()) {
                JOptionPane.showMessageDialog(null, "Login Successfully!");
                int p = JOptionPane.showConfirmDialog(null, "Would you like to stop the alarm?", "Stop Alarm", JOptionPane.YES_NO_OPTION);
                if (p == 0) {
                    stopmusic();

                    this.dispose();
                    MainMonitor mm = new MainMonitor("CheckIn");
                    mm.show();

                }
            } else {
                JOptionPane.showMessageDialog(null, "Username or Password is incorrect! Please try again.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        tUsername = new javax.swing.JTextField();
        pfPassword = new javax.swing.JPasswordField();
        PassLogo = new javax.swing.JLabel();
        UserLogo = new javax.swing.JLabel();
        LoginLogo = new javax.swing.JLabel();
        bLogin = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        pfPassword.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                pfPasswordActionPerformed(evt);
            }
        });
        pfPassword.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                pfPasswordKeyPressed(evt);
            }
        });

        bLogin.setText("LOGIN");
        bLogin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bLoginActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(526, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(UserLogo, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(PassLogo, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(tUsername, javax.swing.GroupLayout.PREFERRED_SIZE, 211, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(pfPassword, javax.swing.GroupLayout.PREFERRED_SIZE, 211, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(LoginLogo, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(61, 61, 61))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(bLogin)
                        .addGap(77, 77, 77)))
                .addGap(159, 159, 159))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(214, 214, 214)
                .addComponent(LoginLogo, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(tUsername)
                    .addComponent(UserLogo, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(29, 29, 29)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(pfPassword, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(PassLogo, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(bLogin)
                .addContainerGap(219, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void pfPasswordActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_pfPasswordActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_pfPasswordActionPerformed

    private void bLoginActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bLoginActionPerformed
        // TODO add your handling code here:
        login();

    }//GEN-LAST:event_bLoginActionPerformed

    private void pfPasswordKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_pfPasswordKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            login();
        }
    }//GEN-LAST:event_pfPasswordKeyPressed

    /**
     * @param args the command line arguments
     */

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel LoginLogo;
    private javax.swing.JLabel PassLogo;
    private javax.swing.JLabel UserLogo;
    private javax.swing.JButton bLogin;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPasswordField pfPassword;
    private javax.swing.JTextField tUsername;
    // End of variables declaration//GEN-END:variables
}
