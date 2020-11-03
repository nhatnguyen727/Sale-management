/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import java.awt.Color;
import java.awt.Component;
import javax.swing.JButton;
import javax.swing.border.LineBorder;

/**
 *
 * @author stfu
 */
public class Message extends javax.swing.JFrame {
    
    public JButton getjButtonAction() {
        return jButton1;
    }

    /**
     * Creates new form NewJFrame
     *
     * @param msgContent
     * @param msgType
     * @param formType
     */
    public Message(String msgContent, int msgType, int formType) {

        this.formType = formType;

        initComponents();
        // msgType : 0-null, 1-successful, 2-failed 
        // formType : 0-default, 1-dangnhap, 2-resetpw, 3-qlhoadonxuat/nhap, 4-qlnhanvien/phanquyen, 5-qlsanpham, 6-report, 7-main/setting
        if (msgType != 0) {
            if (msgType == 1) {
                jLabel_icon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/view/images/icons8-checked-25.png"))); // NOI18N
                jLabel_msg.setText(msgContent);
            } else {
                jLabel_icon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/view/images/icons8-error-25.png"))); // NOI18N
                jLabel_msg.setText(msgContent);
            }
        }
        switch (formType) {
            case 1:
                jPanel1.setBackground(new Color(108, 58, 167));
                jPanel1.setBorder(new LineBorder(new Color(0, 204, 204)));
                jLabel_close.setBackground(new Color(108, 58, 167));
                jButton1.setBackground(new Color(108, 58, 167));
                jButton1.setBorder(new LineBorder(new Color(0, 204, 204)));
                break;
            case 2:
                jPanel1.setBackground(new Color(36, 47, 65));
                jPanel1.setBorder(new LineBorder(new Color(255, 255, 255)));
                jLabel_close.setBackground(new Color(36, 47, 65));
                jButton1.setBackground(new Color(36, 47, 65));
                jButton1.setBorder(new LineBorder(new Color(255, 255, 255)));
                break;
            case 3:
                jPanel1.setBackground(new Color(73, 61, 128));
                jPanel1.setBorder(new LineBorder(new Color(147, 122, 255)));
                jLabel_close.setBackground(new Color(73, 61, 128));
                jButton1.setBackground(new Color(73, 61, 128));
                jButton1.setBorder(new LineBorder(new Color(147, 122, 255)));
                break;
            case 4:
                jPanel1.setBackground(new Color(56, 53, 140));
                jPanel1.setBorder(new LineBorder(new Color(139, 137, 217)));
                jLabel_close.setBackground(new Color(56, 53, 140));
                jButton1.setBackground(new Color(56, 53, 140));
                jButton1.setBorder(new LineBorder(new Color(139, 137, 217)));
                break;
            case 5:
                jPanel1.setBackground(new Color(3, 140, 90));
                jPanel1.setBorder(new LineBorder(new Color(1, 198, 83)));
                jLabel_close.setBackground(new Color(3, 140, 90));
                jButton1.setBackground(new Color(3, 140, 90));
                jButton1.setBorder(new LineBorder(new Color(1, 198, 83)));
                break;
            case 6:
                jPanel1.setBackground(new Color(247, 249, 250));
                jPanel1.setBorder(new LineBorder(new Color(139, 137, 217)));
                jLabel_close.setBackground(new Color(247, 249, 250));
                jButton1.setBackground(new Color(247, 249, 250));
                jButton1.setBorder(new LineBorder(new Color(139, 137, 217)));
                jButton1.setForeground(new Color(60, 63, 65));
                jLabel_msg.setForeground(new Color(60, 63, 65));
                jLabel_close.setForeground(new Color(60, 63, 65));
                break;
            case 7:
                jPanel1.setBackground(new Color(34, 41, 50));
                jPanel1.setBorder(new LineBorder(new Color(147, 122, 255)));
                jLabel_close.setBackground(new Color(34, 41, 50));
                jButton1.setBackground(new Color(34, 41, 50));
                jButton1.setBorder(new LineBorder(new Color(147, 122, 255)));
                break;
            default:
        }
    }

    int formType = 0;

    public void setBgr(Component comp) {
        switch (formType) {
            case 1:
                comp.setBackground(new Color(0, 204, 204));
                break;
            case 2:
                comp.setBackground(new Color(97, 212, 195));
                break;
            case 3:
                comp.setBackground(new Color(147, 122, 255));
                break;
            case 4:
                comp.setBackground(new Color(139, 137, 217));
                break;
            case 5:
                comp.setBackground(new Color(1, 198, 83));
                break;
            case 6:
                comp.setBackground(new Color(139, 137, 217));
                break;
            case 7:
                comp.setBackground(new Color(147, 122, 255));
                break;
            default:
        }
    }

    public void resetBgr(Component comp) {
        switch (formType) {
            case 1:
                comp.setBackground(new Color(108, 58, 167));
                break;
            case 2:
                comp.setBackground(new Color(36, 47, 65));
                break;
            case 3:
                comp.setBackground(new Color(73, 61, 128));
                break;
            case 4:
                comp.setBackground(new Color(56, 53, 140));
                break;
            case 5:
                comp.setBackground(new Color(3, 140, 90));
                break;
            case 6:
                comp.setBackground(new Color(247, 249, 250));
                break;
            case 7:
                comp.setBackground(new Color(34, 41, 50));
                break;
            default:
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
        jButton1 = new javax.swing.JButton();
        jLabel_icon = new javax.swing.JLabel();
        jLabel_msg = new javax.swing.JLabel();
        jLabel_close = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setAlwaysOnTop(true);
        setModalExclusionType(java.awt.Dialog.ModalExclusionType.APPLICATION_EXCLUDE);
        setUndecorated(true);
        addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                formMouseDragged(evt);
            }
        });
        addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                formMousePressed(evt);
            }
        });

        jPanel1.setPreferredSize(new java.awt.Dimension(451, 126));

        jButton1.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jButton1.setForeground(new java.awt.Color(255, 255, 255));
        jButton1.setText("OK");
        jButton1.setBorder(null);
        jButton1.setContentAreaFilled(false);
        jButton1.setFocusPainted(false);
        jButton1.setOpaque(true);
        jButton1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton1MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jButton1MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jButton1MouseExited(evt);
            }
        });

        jLabel_icon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/view/images/icons8-questions-25.png"))); // NOI18N

        jLabel_msg.setFont(new java.awt.Font("Segoe UI Light", 0, 14)); // NOI18N
        jLabel_msg.setForeground(new java.awt.Color(255, 255, 255));
        jLabel_msg.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel_msg.setText("set text");

        jLabel_close.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel_close.setForeground(new java.awt.Color(255, 255, 255));
        jLabel_close.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel_close.setText("X");
        jLabel_close.setOpaque(true);
        jLabel_close.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel_closeMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jLabel_closeMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jLabel_closeMouseExited(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(209, 209, 209)
                .addComponent(jLabel_icon)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 197, Short.MAX_VALUE)
                .addComponent(jLabel_close, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel_msg, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(161, 161, 161)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel_close, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel_icon)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel_msg, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 456, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton1MouseEntered
        setBgr(jButton1);
    }//GEN-LAST:event_jButton1MouseEntered

    private void jButton1MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton1MouseExited
        resetBgr(jButton1);
    }//GEN-LAST:event_jButton1MouseExited

    private void jLabel_closeMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel_closeMouseClicked
        setVisible(false);
        dispose();
    }//GEN-LAST:event_jLabel_closeMouseClicked

    private void jLabel_closeMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel_closeMouseEntered
        setBgr(jLabel_close);
    }//GEN-LAST:event_jLabel_closeMouseEntered

    private void jLabel_closeMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel_closeMouseExited
        resetBgr(jLabel_close);
    }//GEN-LAST:event_jLabel_closeMouseExited

    int xx;
    int xy;

    private void formMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMousePressed
        // TODO add your handling code here:
        xx = evt.getX();
        xy = evt.getY();
        // This makes the window can be dragged (movable window)
    }//GEN-LAST:event_formMousePressed

    private void formMouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseDragged
        // TODO add your handling code here:
        int x = evt.getXOnScreen();
        int y = evt.getYOnScreen();
        this.setLocation(x - xx, y - xy);
        // This makes the window can be dragged (movable window)
    }//GEN-LAST:event_formMouseDragged

    private void jButton1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton1MouseClicked
        setVisible(false);
        dispose();
    }//GEN-LAST:event_jButton1MouseClicked

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
            java.util.logging.Logger.getLogger(Message.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Message.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Message.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Message.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Message("", 1, 4).setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel_close;
    private javax.swing.JLabel jLabel_icon;
    private javax.swing.JLabel jLabel_msg;
    private javax.swing.JPanel jPanel1;
    // End of variables declaration//GEN-END:variables
}
