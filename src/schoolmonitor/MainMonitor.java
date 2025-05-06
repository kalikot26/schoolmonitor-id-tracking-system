/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package schoolmonitor;

import com.sun.glass.events.KeyEvent;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import javax.swing.ImageIcon;
import java.sql.*;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JLabel;
import javax.swing.Timer;
import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;

/**
 *
 * @author John Venice Almazan
 */
public class MainMonitor extends javax.swing.JFrame {

    /**
     * Creates new form MainMonitor
     */
    Connection myconObj = null;
    Statement mystatObj = null;
    ResultSet myresObj = null;
    int tries = 0;
    FileInputStream FIS;
    BufferedInputStream BIS;
    Player player;
    File myFile = null;

    public MainMonitor(String check) {
        this.setUndecorated(true);
        this.setResizable(false);
        initComponents();
        lblCheck.setText(check);
        setfullscreen();
        showDate();
        updateicons();
        bLogin.setVisible(false);
        lblSpec.setHorizontalAlignment(JLabel.CENTER);
        lblSpec.setVerticalAlignment(JLabel.CENTER);

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

    private void setfullscreen() {
        Toolkit tk = Toolkit.getDefaultToolkit();
        int xsize = (int) tk.getScreenSize().getWidth();
        int ysize = (int) tk.getScreenSize().getHeight();
        this.setSize(xsize, ysize);
    }

    private void updateicons() {
        String imgSchoolpath = "schoologo.png";
        String imgBinanpath = "Binan.png";
        String imgStudentpath = "UserLogo.png";

        ImageIcon School = new ImageIcon(imgSchoolpath);
        ImageIcon Binan = new ImageIcon(imgBinanpath);
        ImageIcon Student = new ImageIcon(imgStudentpath);

        Image imgSchool = School.getImage().getScaledInstance(SchoolLogo.getWidth(), SchoolLogo.getHeight(), Image.SCALE_SMOOTH);
        Image imgBinan = Binan.getImage().getScaledInstance(BinanLogo.getWidth(), BinanLogo.getHeight(), Image.SCALE_SMOOTH);
        Image imgStudent = Student.getImage().getScaledInstance(StudentLogo.getWidth(), StudentLogo.getHeight(), Image.SCALE_SMOOTH);

        ImageIcon SchoolLogoimg = new ImageIcon(imgSchool);
        ImageIcon BinanLogoimg = new ImageIcon(imgBinan);
        ImageIcon StudentLogoimg = new ImageIcon(imgStudent);

        SchoolLogo.setIcon(SchoolLogoimg);
        BinanLogo.setIcon(BinanLogoimg);
        StudentLogo.setIcon(StudentLogoimg);

    }

    //DISPLAY DATA
    /*
    public void display() {
        String url = "jdbc:derby://localhost:1527/SchoolMonitor";
        String user = "root";
        String pass = "password";
        String barcode = tIDNUMBER.getText();
        try {
            myconObj = DriverManager.getConnection(url, user, pass);
            mystatObj = myconObj.createStatement();
            char ch = barcode.charAt(0);
            if (ch == 'S') {
                myresObj = mystatObj.executeQuery("SELECT * FROM ROOT.STUDENTS WHERE BARCODE ='" + barcode + "'");

                while (myresObj.next()) {
                    String LRN = myresObj.getString("LRN");
                    String FN = myresObj.getString("FIRSTNAME");
                    String LN = myresObj.getString("LASTNAME");
                    String Name = LN + ", " + FN;
                    String ADV = myresObj.getString("ADVISER");
                    String GLEVEL = myresObj.getString("GRADE");
                    String SECTION = myresObj.getString("SECTION");
                    String GRSEC = GLEVEL + " - " + SECTION;
                    String CRED = myresObj.getString("CREDENTIAL");
                    String PHOTO = myresObj.getString("PHOTOLINK");

                    lblLRN.setText("LRN: " + LRN);
                    lblLRN.setVisible(true);
                    lblName.setText("Name: " + Name);
                    lblAdviser.setText("Adviser: " + ADV);
                    lblAdviser.setVisible(true);
                    lblGrSec.setText("Grade&Section: " + GRSEC);
                    lblGrSec.setVisible(true);
                    lblCredential.setText("Credential: " + CRED);

                    ImageIcon Student = new ImageIcon(PHOTO);
                    Image imgStudent = Student.getImage().getScaledInstance(StudentLogo.getWidth(), StudentLogo.getHeight(), Image.SCALE_SMOOTH);
                    ImageIcon StudentLogoimg = new ImageIcon(imgStudent);
                    StudentLogo.setIcon(StudentLogoimg);

                    showTime();

                }
            } else if (ch == 'B') {
                myresObj = mystatObj.executeQuery("SELECT * FROM ROOT.LOGIN WHERE BARCODE ='" + barcode + "'");
                String prefix = myresObj.getString("PREFIX");
                String Lastname = myresObj.getString("LASTNAME");
                String Name = prefix + " " + Lastname;
                String Credential = myresObj.getString("CREDENTIAL");
                String PHOTO = myresObj.getString("PHOTOLINK");

                lblName.setText("Name: " + Name);
                lblCredential.setText("Credential: " + Credential);
            } else {
                myresObj = mystatObj.executeQuery("SELECT * FROM ROOT.ADMIN WHERE BARCODE ='" + barcode + "'");
                String prefix = myresObj.getString("PREFIX");
                String Lastname = myresObj.getString("LASTNAME");
                String Name = prefix + " " + Lastname;
                String Credential = myresObj.getString("CREDENTIAL");
                String PHOTO = myresObj.getString("PHOTOLINK");

                lblName.setText("Name: " + Name);
                lblCredential.setText("Credential: " + Credential);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
     */
    public void showDate() {
        new Timer(0, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                Date d = new Date();
                SimpleDateFormat s = new SimpleDateFormat("MMM dd, YYYY - hh:mm aa");
                lblCurrentTIme.setText("Current Time: " + s.format(d));
                tIDNUMBER.requestFocus();
            }
        }).start();
    }

    public void showTime() {
        Date d = new Date();
        SimpleDateFormat s = new SimpleDateFormat("hh:mm aa");
        SimpleDateFormat t = new SimpleDateFormat("aa");
        if (lblCheck.equals("CheckIn")) {
            lblTime.setText("Check In: " + s.format(d));
        } else {
            lblTime.setText("Check Out: " + s.format(d));
        }
    }

    private void scan() {
        bLogin.setVisible(false);
        lblTime.setVisible(true);
        String url = "jdbc:derby://localhost:1527/SchoolMonitor";
        String user = "root";
        String pass = "password";
        String barcode = tIDNUMBER.getText();
        char ch = barcode.charAt(0);
        try {
            myconObj = DriverManager.getConnection(url, user, pass);
            mystatObj = myconObj.createStatement();

            switch (ch) {
                case 'S':
                    myresObj = mystatObj.executeQuery("SELECT * FROM ROOT.STUDENTS WHERE BARCODE ='" + barcode + "'");
                    while (myresObj.next()) {
                        String LRN = myresObj.getString("LRN");
                        String FN = myresObj.getString("FIRSTNAME");
                        String LN = myresObj.getString("LASTNAME");
                        String Name = LN + ", " + FN;
                        String ADV = myresObj.getString("ADVISER");
                        String GLEVEL = myresObj.getString("GRADELEVEL");
                        String SECTION = myresObj.getString("SECTION");
                        String GRSEC = GLEVEL + " - " + SECTION;
                        String CRED = myresObj.getString("CREDENTIAL");
                        String PHOTO = myresObj.getString("PHOTOLINK");
                        String SPEC = myresObj.getString("SPECIALIZATION");

                        if (null != CRED) {
                            switch (CRED) {
                                case "Student": {
                                    lblLRN.setVisible(true);
                                    lblAdviser.setVisible(true);
                                    lblGrSec.setVisible(true);
                                    lblLRN.setText("LRN: " + LRN);
                                    lblName.setText("Name: " + Name);
                                    lblAdviser.setText("Adviser: " + ADV);
                                    lblGrSec.setText("Grade&Section: " + GRSEC);
                                    lblCredential.setText("Credential: " + CRED);
                                    lblSpecial.setVisible(true);
                                    lblSpecial.setText(SPEC);
                                    ImageIcon Student = new ImageIcon(PHOTO);
                                    Image imgStudent = Student.getImage().getScaledInstance(StudentLogo.getWidth(), StudentLogo.getHeight(), Image.SCALE_SMOOTH);
                                    ImageIcon StudentLogoimg = new ImageIcon(imgStudent);
                                    StudentLogo.setIcon(StudentLogoimg);
                                    showTime();
                                    break;
                                }
                                case "Guard": {
                                    lblSpecial.setVisible(false);
                                    lblLRN.setVisible(false);
                                    lblName.setText("Name: " + Name);
                                    lblAdviser.setVisible(false);
                                    lblGrSec.setVisible(false);
                                    lblCredential.setText("Credential: " + CRED);
                                    ImageIcon Student = new ImageIcon(PHOTO);
                                    Image imgStudent = Student.getImage().getScaledInstance(StudentLogo.getWidth(), StudentLogo.getHeight(), Image.SCALE_SMOOTH);
                                    ImageIcon StudentLogoimg = new ImageIcon(imgStudent);
                                    StudentLogo.setIcon(StudentLogoimg);
                                    showTime();
                                    break;
                                }
                                case "Admin": {
                                    lblSpecial.setVisible(false);
                                    lblLRN.setVisible(false);
                                    lblName.setText("Name: " + Name);
                                    lblAdviser.setVisible(false);
                                    lblGrSec.setVisible(false);
                                    lblCredential.setText("Credential: " + CRED);
                                    ImageIcon Student = new ImageIcon(PHOTO);
                                    Image imgStudent = Student.getImage().getScaledInstance(StudentLogo.getWidth(), StudentLogo.getHeight(), Image.SCALE_SMOOTH);
                                    ImageIcon StudentLogoimg = new ImageIcon(imgStudent);
                                    StudentLogo.setIcon(StudentLogoimg);
                                    showTime();
                                    break;
                                }
                                case "Teacher": {
                                    lblSpecial.setVisible(false);
                                    lblLRN.setVisible(false);
                                    lblName.setText("Name: " + Name);
                                    lblAdviser.setVisible(false);
                                    lblGrSec.setVisible(false);
                                    lblCredential.setText("Credential: " + CRED);
                                    ImageIcon Student = new ImageIcon(PHOTO);
                                    Image imgStudent = Student.getImage().getScaledInstance(StudentLogo.getWidth(), StudentLogo.getHeight(), Image.SCALE_SMOOTH);
                                    ImageIcon StudentLogoimg = new ImageIcon(imgStudent);
                                    StudentLogo.setIcon(StudentLogoimg);
                                    showTime();
                                    break;
                                }
                                case "Visitor":
                                    String VStatus = "Deped";
                                    switch (VStatus) {
                                        //Do Execution here for Deped Visitor
                                        case "Deped":
                                            break;
                                        //Do Execution here for Alumni Students
                                        case "Alumni":
                                            break;
                                        //DO Execution here for Parent Visitor
                                        case "Parent":
                                            break;
                                        //Do Warning Executoion for Dropped Student trying to enter
                                        case "Dropped":
                                            break;
                                        default:
                                            break;
                                    }
                                    break;
                                default:
                                    break;
                            }
                        }

                    }
                    break;
                case 'B':
                    myresObj = mystatObj.executeQuery("SELECT * FROM ROOT.LOGIN WHERE BARCODE ='" + barcode + "'");
                    while (myresObj.next()) {
                        String prefix = myresObj.getString("PREFIX");
                        String Lastname = myresObj.getString("LASTNAME");
                        String Name = prefix + " " + Lastname;
                        String CRED = myresObj.getString("CREDENTIAL");
                        String PHOTO = myresObj.getString("PHOTOLINK");

                        lblName.setText("Name: " + Name);
                        lblCredential.setText("Credential: " + CRED);
                        if (null != CRED) {
                            switch (CRED) {
                                case "Guard": {
                                    lblSpecial.setVisible(false);
                                    lblLRN.setVisible(false);
                                    lblName.setText("Name: " + Name);
                                    lblAdviser.setVisible(false);
                                    lblGrSec.setVisible(false);
                                    lblCredential.setText("Credential: " + CRED);
                                    ImageIcon Student = new ImageIcon(PHOTO);
                                    Image imgStudent = Student.getImage().getScaledInstance(StudentLogo.getWidth(), StudentLogo.getHeight(), Image.SCALE_SMOOTH);
                                    ImageIcon StudentLogoimg = new ImageIcon(imgStudent);
                                    StudentLogo.setIcon(StudentLogoimg);
                                    lblTime.setVisible(false);
                                    break;
                                }
                                case "Admin": {
                                    lblSpecial.setVisible(false);
                                    lblLRN.setVisible(false);
                                    lblName.setText("Name: " + Name);
                                    lblAdviser.setVisible(false);
                                    lblGrSec.setVisible(false);
                                    lblCredential.setText("Credential: " + CRED);
                                    ImageIcon Student = new ImageIcon(PHOTO);
                                    Image imgStudent = Student.getImage().getScaledInstance(StudentLogo.getWidth(), StudentLogo.getHeight(), Image.SCALE_SMOOTH);
                                    ImageIcon StudentLogoimg = new ImageIcon(imgStudent);
                                    StudentLogo.setIcon(StudentLogoimg);
                                    showTime();
                                    break;
                                }
                                case "Teacher": {
                                    lblSpecial.setVisible(false);
                                    lblLRN.setVisible(false);
                                    lblName.setText("Name: " + Name);
                                    lblAdviser.setVisible(false);
                                    lblGrSec.setVisible(false);
                                    lblCredential.setText("Credential: " + CRED);
                                    ImageIcon Student = new ImageIcon(PHOTO);
                                    Image imgStudent = Student.getImage().getScaledInstance(StudentLogo.getWidth(), StudentLogo.getHeight(), Image.SCALE_SMOOTH);
                                    ImageIcon StudentLogoimg = new ImageIcon(imgStudent);
                                    StudentLogo.setIcon(StudentLogoimg);
                                    showTime();
                                    break;
                                }
                                case "Visitor":
                                    String VStatus = "Deped";
                                    switch (VStatus) {
                                        //Do Execution here for Deped Visitor
                                        case "Deped":
                                            break;
                                        //Do Execution here for Alumni Students
                                        case "Alumni":
                                            break;
                                        //DO Execution here for Parent Visitor
                                        case "Parent":
                                            break;
                                        //Do Warning Executoion for Dropped Student trying to enter
                                        case "Dropped":
                                            break;
                                        default:
                                            break;
                                    }
                                    break;
                                default:
                                    break;
                            }
                            if ("Teacher".equals(CRED) || "Admin".equals(CRED) || "Guard".equals(CRED)) {
                                bLogin.setVisible(true);
                            } else {
                                bLogin.setVisible(false);
                            }
                        }
                    }
                    break;
                default:
                    myresObj = mystatObj.executeQuery("SELECT * FROM ROOT.ADMIN WHERE BARCODE ='" + barcode + "'");
                    while (myresObj.next()) {


                        String Lastname = myresObj.getString("LASTNAME");
                        String CRED = myresObj.getString("CREDENTIAL");
                        String PHOTO = myresObj.getString("PHOTOLINK");

                        lblLRN.setVisible(false);
                        lblAdviser.setVisible(false);
                        lblGrSec.setVisible(false);
                        bLogin.setVisible(true);
                        lblSpecial.setVisible(false);
                        lblName.setText("Name: " + Lastname);
                        lblCredential.setText("Credential: " + CRED);
                        ImageIcon Student = new ImageIcon(PHOTO);
                        Image imgStudent = Student.getImage().getScaledInstance(StudentLogo.getWidth(), StudentLogo.getHeight(), Image.SCALE_SMOOTH);
                        ImageIcon StudentLogoimg = new ImageIcon(imgStudent);
                        StudentLogo.setIcon(StudentLogoimg);
                        showTime();
                    }
                    break;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        tIDNUMBER.setText("");
        tIDNUMBER.requestFocus();
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
        BinanLogo = new javax.swing.JLabel();
        SchoolLogo = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        lblCurrentTIme = new javax.swing.JLabel();
        StudentLogo = new javax.swing.JLabel();
        lblLRN = new javax.swing.JLabel();
        lblName = new javax.swing.JLabel();
        lblAdviser = new javax.swing.JLabel();
        lblGrSec = new javax.swing.JLabel();
        lblCredential = new javax.swing.JLabel();
        tIDNUMBER = new javax.swing.JTextField();
        bLogin = new javax.swing.JButton();
        lblSpec = new javax.swing.JLabel();
        lblTime = new javax.swing.JLabel();
        lblCheck = new javax.swing.JLabel();
        lblSpecial = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setPreferredSize(new java.awt.Dimension(1360, 696));

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 36)); // NOI18N
        jLabel3.setText("In/Out Monitor");

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel4.setText("Binan City Senior High School");

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel5.setText("San Antonio Campus");

        lblCurrentTIme.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        lblCurrentTIme.setText("Current Time: MMM DD, YYYY - 00:00 AA");

        lblLRN.setFont(new java.awt.Font("Tahoma", 0, 36)); // NOI18N
        lblLRN.setText("LRN:");

        lblName.setFont(new java.awt.Font("Tahoma", 0, 36)); // NOI18N
        lblName.setText("Name:");

        lblAdviser.setFont(new java.awt.Font("Tahoma", 0, 36)); // NOI18N
        lblAdviser.setText("Adviser:");

        lblGrSec.setFont(new java.awt.Font("Tahoma", 0, 36)); // NOI18N
        lblGrSec.setText("Grade&Section:");

        lblCredential.setFont(new java.awt.Font("Tahoma", 0, 36)); // NOI18N
        lblCredential.setText("Credential:");

        tIDNUMBER.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tIDNUMBERActionPerformed(evt);
            }
        });
        tIDNUMBER.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tIDNUMBERKeyPressed(evt);
            }
        });

        bLogin.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        bLogin.setText("Login");
        bLogin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bLoginActionPerformed(evt);
            }
        });

        lblSpec.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        lblSpec.setText("Specialization");
        lblSpec.setAlignmentX(0.5F);

        lblTime.setFont(new java.awt.Font("Tahoma", 0, 36)); // NOI18N
        lblTime.setText("Time In: ");

        lblCheck.setFont(new java.awt.Font("Tahoma", 0, 36)); // NOI18N
        lblCheck.setText("CheckIn");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(50, 50, 50)
                .addComponent(SchoolLogo, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(280, 280, 280)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(jLabel3)
                                        .addGap(37, 37, 37))
                                    .addComponent(jLabel4)
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                                        .addGap(28, 28, 28)
                                        .addComponent(jLabel5))))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(355, 355, 355)
                                .addComponent(lblCheck)))
                        .addGap(280, 280, 280)
                        .addComponent(BinanLogo, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(100, 100, 100))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(300, 300, 300)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblTime)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(jPanel1Layout.createSequentialGroup()
                                    .addComponent(lblCredential)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(bLogin, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(311, 311, 311))
                                .addGroup(jPanel1Layout.createSequentialGroup()
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(lblLRN)
                                        .addComponent(lblGrSec)
                                        .addComponent(lblName)
                                        .addComponent(lblAdviser))
                                    .addGap(630, 630, 630)))))))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(75, 75, 75)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblCurrentTIme)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(lblSpec, javax.swing.GroupLayout.DEFAULT_SIZE, 450, Short.MAX_VALUE)
                                .addComponent(StudentLogo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(215, 215, 215)
                        .addComponent(tIDNUMBER, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(895, 895, 895))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(BinanLogo, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(23, 23, 23))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGap(3, 3, 3)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(SchoolLogo, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(lblCurrentTIme))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(24, 24, 24)
                                .addComponent(jLabel3)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel4)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel5)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(lblCheck)))))
                .addGap(35, 35, 35)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(StudentLogo, javax.swing.GroupLayout.PREFERRED_SIZE, 410, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(lblSpec, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(tIDNUMBER, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(lblLRN)
                        .addGap(18, 18, 18)
                        .addComponent(lblName)
                        .addGap(18, 18, 18)
                        .addComponent(lblAdviser)
                        .addGap(18, 18, 18)
                        .addComponent(lblGrSec)
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblCredential)
                            .addComponent(bLogin, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(30, 30, 30)
                        .addComponent(lblTime)
                        .addGap(208, 208, 208))))
        );

        lblSpecial.setFont(new java.awt.Font("Tahoma", 0, 36)); // NOI18N
        lblSpecial.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblSpecial.setText("Specialization");
        lblSpecial.setToolTipText("");
        lblSpecial.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 1428, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addGap(78, 78, 78)
                .addComponent(lblSpecial, javax.swing.GroupLayout.PREFERRED_SIZE, 451, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lblSpecial)
                .addGap(869, 869, 869))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void tIDNUMBERKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tIDNUMBERKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            String url = "jdbc:derby://localhost:1527/SchoolMonitor";
            String user = "root";
            String pass = "password";

            try {
                myconObj = DriverManager.getConnection(url, user, pass);
                PreparedStatement PStatement = null;
                ResultSet Rs;
                char ch = tIDNUMBER.getText().charAt(0);
                if (ch == 'S') {
                    String sql = "select * FROM ROOT.STUDENTS WHERE BARCODE = ? AND DELETED = FALSE";
                    PStatement = myconObj.prepareStatement(sql);
                } else if (ch == 'B') {
                    String sql = "select * FROM ROOT.LOGIN WHERE BARCODE = ? AND DELETED = FALSE";
                    PStatement = myconObj.prepareStatement(sql);
                } else if (ch == 'A') {
                    String sql = "select * FROM ROOT.ADMIN WHERE BARCODE = ?";
                    PStatement = myconObj.prepareStatement(sql);
                } else {
                    String sql = "select * FROM ROOT.STUDENTS WHERE BARCODE = ? AND DELETED = FALSE";
                    PStatement = myconObj.prepareStatement(sql);
                    tIDNUMBER.setText("");
                    tIDNUMBER.requestFocus();
                    lblLRN.setText("LRN: INVALID BARCODE!");
                    lblName.setText("Name: INVALID BARDCODE!");
                    lblAdviser.setText("Adviser: INVALID BARCODE!");
                    lblGrSec.setText("Grade&Section: INVALID BARCODE!");
                    lblCredential.setText("Credential: INVALID BARCODE!");
                    lblTime.setText("Time In: INVALID BARCODE!");
                }
                PStatement.setString(1, tIDNUMBER.getText());
                Rs = PStatement.executeQuery();

                if (Rs.next()) {
                    scan();
                    tries = 0;
                } else {
                    tries++;
                    tIDNUMBER.setText("");
                    tIDNUMBER.requestFocus();
                    lblLRN.setText("LRN: INVALID BARCODE!");
                    lblName.setText("Name: INVALID BARDCODE!");
                    lblAdviser.setText("Adviser: INVALID BARCODE!");
                    lblGrSec.setText("Grade&Section: INVALID BARCODE!");
                    lblCredential.setText("Credential: INVALID BARCODE!");
                    lblTime.setText("Time In: INVALID BARCODE!");
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (tries == 3) {
            LoginStopMusic LSM = new LoginStopMusic();
            LSM.show();
            this.hide();
            //playmusic();
            // bStop.setVisible(true);
            //SET ALARM HERE
            //DISABLE SCAN
            //ENABLE GUARD LOGIN
        }
    }//GEN-LAST:event_tIDNUMBERKeyPressed

    private void bLoginActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bLoginActionPerformed
        // TODO add your handling code here:
        LoginPage Log = new LoginPage();
        Log.show();
        this.hide();
    }//GEN-LAST:event_bLoginActionPerformed

    private void tIDNUMBERActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tIDNUMBERActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tIDNUMBERActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(MainMonitor.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MainMonitor.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MainMonitor.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MainMonitor.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MainMonitor("CheckIn").setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel BinanLogo;
    private javax.swing.JLabel SchoolLogo;
    private javax.swing.JLabel StudentLogo;
    private javax.swing.JButton bLogin;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel lblAdviser;
    private javax.swing.JLabel lblCheck;
    private javax.swing.JLabel lblCredential;
    private javax.swing.JLabel lblCurrentTIme;
    private javax.swing.JLabel lblGrSec;
    private javax.swing.JLabel lblLRN;
    private javax.swing.JLabel lblName;
    private javax.swing.JLabel lblSpec;
    private javax.swing.JLabel lblSpecial;
    private javax.swing.JLabel lblTime;
    private javax.swing.JTextField tIDNUMBER;
    // End of variables declaration//GEN-END:variables
}
