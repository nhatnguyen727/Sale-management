/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import controller.Accountcontroller;
import static controller.Accountcontroller.GetListAccount;
import controller.EmployeeController;
import static controller.PositionController.GetListPosition;
import controller.Validation;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Frame;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;
import model.Account;
import model.Employee;
import model.Position;

/**
 *
 * @author proxc
 */
public class QLNhanVien extends javax.swing.JFrame {

    Account acc;
    Employee objnv;
    String gender;
    DefaultTableModel modelStaff;
    DefaultTableModel modelStaffNotUser;
    DefaultTableModel modelUser;

    static DecimalFormat currencyFormatter = new DecimalFormat("###,###,###");
    static DateFormat dateFormatter = new SimpleDateFormat("dd/MM/yyyy");

    /**
     * Creates new form QLNhanVien
     */
    public QLNhanVien() {
        /* Set the Windows look and feel */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Windows".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(QLNhanVien.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        initComponents();
        createTableDataNV();
        createComboBoxData();
        createTableAccount();
        createPosition();

        jTableNV.getTableHeader().setForeground(new Color(56, 53, 140));
        jTableNV.getTableHeader().setBackground(new Color(139, 137, 217));
        jTableNV.getTableHeader().setFont(new Font("Dialog", Font.BOLD, 12));

        jTableUser.getTableHeader().setForeground(new Color(56, 53, 140));
        jTableUser.getTableHeader().setBackground(new Color(139, 137, 217));
        jTableUser.getTableHeader().setFont(new Font("Dialog", Font.BOLD, 12));

        jTablePos.getTableHeader().setForeground(new Color(56, 53, 140));
        jTablePos.getTableHeader().setBackground(new Color(139, 137, 217));
        jTablePos.getTableHeader().setFont(new Font("Dialog", Font.BOLD, 12));

        jTable_dlgStaff.getTableHeader().setForeground(new Color(56, 53, 140));
        jTable_dlgStaff.getTableHeader().setBackground(new Color(139, 137, 217));
        jTable_dlgStaff.getTableHeader().setFont(new Font("Dialog", Font.BOLD, 12));
    }

    private DefaultTableModel createModelStaff(JTable tbl) {
        DefaultTableModel model = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tbl.setModel(model);
        model.addColumn("ID");
        model.addColumn("Name");
        model.addColumn("DoB");
        model.addColumn("Gender");
        model.addColumn("Address");
        model.addColumn("Phone");
        model.addColumn("Email");
        model.addColumn("Salary");
        return model;
    }

    private void createTableDataNV() {
        modelStaff = createModelStaff(jTableNV);
        List<Employee> emps = EmployeeController.GetListEmployee();
        for (Employee emp : emps) {
            modelStaff.addRow(new Object[]{emp.getEmpID(), emp.getEmpName(), dateFormatter.format(emp.getDoB()), emp.getGender(),
                emp.getEmpAdress(), emp.getTellNo(), emp.getEmail(), currencyFormatter.format(emp.getLuong())});
        }
    }

    private DefaultTableModel createModelUser() {
        DefaultTableModel model = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        jTableUser.setModel(model);
        model.addColumn("ID");
        model.addColumn("Username");
        model.addColumn("Password");
        model.addColumn("EmpID");
        model.addColumn("PostID");
        return model;
    }

    private void createTableAccount() {
        modelUser = createModelUser();
        List<Account> list = GetListAccount();
        for (Account a : list) {
            modelUser.addRow(new Object[]{a.getAccID(), a.getUsername(), a.getPassword(),
                a.getEmpID(), a.getPosID()});
        }
    }

    private void createPosition() {
        DefaultTableModel defaultTableModel = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        jTablePos.setModel(defaultTableModel);
        defaultTableModel.addColumn("ID");
        defaultTableModel.addColumn("Quyền truy cập");
        List<Position> pos = GetListPosition();
        for (Position p : pos) {
            defaultTableModel.addRow(new Object[]{p.getPosId(), p.getPosName()});

        }
    }

    private void createComboBoxData() {
        /*Load dữ liệu lên combox phân quyền */
        cb_phanquyen.removeAllItems();
        ArrayList<Position> list = (ArrayList<Position>) controller.PositionController.GetListPosition();
        for (Position position : list) {
            cb_phanquyen.addItem(position.getPosName());
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

        buttonGroup1 = new javax.swing.ButtonGroup();
        jPopupMenuNV = new javax.swing.JPopupMenu();
        jMenuItemEditNV = new javax.swing.JMenuItem();
        jMenuItemDeleteNV = new javax.swing.JMenuItem();
        jPopupMenuUser = new javax.swing.JPopupMenu();
        jMenuItemEditUser = new javax.swing.JMenuItem();
        jMenuItemDeleteUser = new javax.swing.JMenuItem();
        jDialog1 = new javax.swing.JDialog();
        jPanelStaff = new javax.swing.JPanel();
        jLabel_close2 = new javax.swing.JLabel();
        tblPanel = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        jTable_dlgStaff = new javax.swing.JTable();
        jLabel9 = new javax.swing.JLabel();
        jSeparator6 = new javax.swing.JSeparator();
        jTextField_dlgSearch = new javax.swing.JTextField();
        jLabel28 = new javax.swing.JLabel();
        sidebar = new javax.swing.JPanel();
        lbl_addStaff = new javax.swing.JLabel();
        lbl_out = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        lbl_addUser = new javax.swing.JLabel();
        header = new javax.swing.JPanel();
        jTextField_searchNV = new javax.swing.JTextField();
        jSeparator1 = new javax.swing.JSeparator();
        jLabel16 = new javax.swing.JLabel();
        jPanel_clomin = new javax.swing.JPanel();
        jLabel_mini = new javax.swing.JLabel();
        jLabel_close = new javax.swing.JLabel();
        btn_staff1 = new javax.swing.JLabel();
        btn_users1 = new javax.swing.JLabel();
        btn_permission1 = new javax.swing.JLabel();
        jTextField_searchTK = new javax.swing.JTextField();
        body = new javax.swing.JPanel();
        control = new javax.swing.JPanel();
        body_staff = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        txt_Address = new javax.swing.JTextField();
        jLabel22 = new javax.swing.JLabel();
        txt_Luong = new javax.swing.JTextField();
        jRadioButtonMale = new javax.swing.JRadioButton();
        jRadioButtonFemale = new javax.swing.JRadioButton();
        jSeparator3 = new javax.swing.JSeparator();
        btn_ADDorEDIT = new javax.swing.JButton();
        txt_Name = new javax.swing.JTextField();
        jLabel_staffID = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        dateChooserCombo1 = new datechooser.beans.DateChooserCombo();
        jLabel24 = new javax.swing.JLabel();
        txt_Phone = new javax.swing.JTextField();
        jLabel25 = new javax.swing.JLabel();
        txt_Email = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        body_users = new javax.swing.JPanel();
        jLabel32 = new javax.swing.JLabel();
        jLabel34 = new javax.swing.JLabel();
        txt_staffID = new javax.swing.JTextField();
        jButton_user = new javax.swing.JButton();
        cb_phanquyen = new javax.swing.JComboBox<>();
        jLabel33 = new javax.swing.JLabel();
        jLabel37 = new javax.swing.JLabel();
        jLabel38 = new javax.swing.JLabel();
        txt_checkpassword = new javax.swing.JPasswordField();
        txt_password = new javax.swing.JPasswordField();
        txt_newuser = new javax.swing.JTextField();
        jSeparator4 = new javax.swing.JSeparator();
        jLabel11 = new javax.swing.JLabel();
        jLabel_userID = new javax.swing.JLabel();
        tabFooter = new javax.swing.JPanel();
        btn_staff = new javax.swing.JLabel();
        btn_users = new javax.swing.JLabel();
        btn_permission = new javax.swing.JLabel();
        footer = new javax.swing.JPanel();
        staff = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTableNV = new javax.swing.JTable();
        users = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTableUser = new javax.swing.JTable();
        userPermission = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTablePos = new javax.swing.JTable();

        jMenuItemEditNV.setIcon(new javax.swing.ImageIcon(getClass().getResource("/view/images/icons8-edit-18.png"))); // NOI18N
        jMenuItemEditNV.setText("Sửa");
        jMenuItemEditNV.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemEditNVActionPerformed(evt);
            }
        });
        jPopupMenuNV.add(jMenuItemEditNV);

        jMenuItemDeleteNV.setIcon(new javax.swing.ImageIcon(getClass().getResource("/view/images/icons8-delete-18.png"))); // NOI18N
        jMenuItemDeleteNV.setText("Xóa");
        jMenuItemDeleteNV.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemDeleteNVActionPerformed(evt);
            }
        });
        jPopupMenuNV.add(jMenuItemDeleteNV);

        jMenuItemEditUser.setIcon(new javax.swing.ImageIcon(getClass().getResource("/view/images/icons8-edit-18.png"))); // NOI18N
        jMenuItemEditUser.setText("Sửa");
        jMenuItemEditUser.setToolTipText("");
        jMenuItemEditUser.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemEditUserActionPerformed(evt);
            }
        });
        jPopupMenuUser.add(jMenuItemEditUser);

        jMenuItemDeleteUser.setIcon(new javax.swing.ImageIcon(getClass().getResource("/view/images/icons8-delete-18.png"))); // NOI18N
        jMenuItemDeleteUser.setText("Xóa");
        jMenuItemDeleteUser.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemDeleteUserActionPerformed(evt);
            }
        });
        jPopupMenuUser.add(jMenuItemDeleteUser);

        jDialog1.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        jDialog1.setModal(true);
        jDialog1.setUndecorated(true);
        jDialog1.setResizable(false);
        jDialog1.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                jDialog1MouseDragged(evt);
            }
        });
        jDialog1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jDialog1MousePressed(evt);
            }
        });

        jPanelStaff.setBackground(new java.awt.Color(56, 53, 140));
        jPanelStaff.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(139, 137, 217)));
        jPanelStaff.setPreferredSize(new java.awt.Dimension(678, 500));

        jLabel_close2.setBackground(new java.awt.Color(56, 53, 140));
        jLabel_close2.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel_close2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel_close2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel_close2.setText("X");
        jLabel_close2.setOpaque(true);
        jLabel_close2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel_close2MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jLabel_close2MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jLabel_close2MouseExited(evt);
            }
        });

        tblPanel.setBackground(java.awt.SystemColor.controlShadow);
        tblPanel.setPreferredSize(new java.awt.Dimension(678, 421));
        tblPanel.setLayout(new javax.swing.BoxLayout(tblPanel, javax.swing.BoxLayout.LINE_AXIS));

        jScrollPane4.setBackground(java.awt.SystemColor.controlShadow);
        jScrollPane4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(139, 137, 217)));

        jTable_dlgStaff.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jTable_dlgStaff.setForeground(new java.awt.Color(51, 51, 51));
        jTable_dlgStaff.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID Nhân Viên", "Tên Nhân Viên", "Ngày Sinh", "Giới Tính", "Phone", "Email", "Lương"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Float.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        jTable_dlgStaff.setGridColor(new java.awt.Color(139, 137, 217));
        jTable_dlgStaff.setRowHeight(20);
        jTable_dlgStaff.setSelectionBackground(new java.awt.Color(139, 137, 217));
        jTable_dlgStaff.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable_dlgStaffMouseClicked(evt);
            }
        });
        jScrollPane4.setViewportView(jTable_dlgStaff);
        jTable_dlgStaff.setDefaultEditor(Object.class, null);

        tblPanel.add(jScrollPane4);

        jLabel9.setFont(new java.awt.Font("Segoe UI Light", 0, 18)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setText("Danh sách nhân viên chưa đăng ký tài khoản (double click vào bảng để lấy ID) ");

        jSeparator6.setForeground(new java.awt.Color(139, 137, 217));

        jTextField_dlgSearch.setBackground(new java.awt.Color(56, 53, 140));
        jTextField_dlgSearch.setFont(new java.awt.Font("Segoe UI Light", 2, 14)); // NOI18N
        jTextField_dlgSearch.setForeground(new java.awt.Color(255, 255, 255));
        jTextField_dlgSearch.setText("Tìm nhân viên");
        jTextField_dlgSearch.setBorder(null);
        jTextField_dlgSearch.setCaretColor(new java.awt.Color(255, 255, 255));
        jTextField_dlgSearch.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        jTextField_dlgSearch.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTextField_dlgSearchMouseClicked(evt);
            }
        });
        jTextField_dlgSearch.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextField_dlgSearchKeyReleased(evt);
            }
        });

        jLabel28.setIcon(new javax.swing.ImageIcon(getClass().getResource("/view/images/search_20px.png"))); // NOI18N

        javax.swing.GroupLayout jPanelStaffLayout = new javax.swing.GroupLayout(jPanelStaff);
        jPanelStaff.setLayout(jPanelStaffLayout);
        jPanelStaffLayout.setHorizontalGroup(
            jPanelStaffLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelStaffLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelStaffLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelStaffLayout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addComponent(jLabel28)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanelStaffLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jSeparator6)
                            .addComponent(jTextField_dlgSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanelStaffLayout.createSequentialGroup()
                        .addComponent(jLabel9)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel_close2, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))))
            .addComponent(tblPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 716, Short.MAX_VALUE)
        );
        jPanelStaffLayout.setVerticalGroup(
            jPanelStaffLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelStaffLayout.createSequentialGroup()
                .addGroup(jPanelStaffLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelStaffLayout.createSequentialGroup()
                        .addComponent(jLabel_close2, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelStaffLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jLabel9)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanelStaffLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelStaffLayout.createSequentialGroup()
                        .addGap(8, 8, 8)
                        .addComponent(jLabel28))
                    .addGroup(jPanelStaffLayout.createSequentialGroup()
                        .addComponent(jTextField_dlgSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(1, 1, 1)
                        .addComponent(jSeparator6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(tblPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 421, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        javax.swing.GroupLayout jDialog1Layout = new javax.swing.GroupLayout(jDialog1.getContentPane());
        jDialog1.getContentPane().setLayout(jDialog1Layout);
        jDialog1Layout.setHorizontalGroup(
            jDialog1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jDialog1Layout.createSequentialGroup()
                .addComponent(jPanelStaff, javax.swing.GroupLayout.PREFERRED_SIZE, 718, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jDialog1Layout.setVerticalGroup(
            jDialog1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jDialog1Layout.createSequentialGroup()
                .addComponent(jPanelStaff, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(255, 255, 255));
        setLocationByPlatform(true);
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

        sidebar.setBackground(new java.awt.Color(34, 41, 50));
        sidebar.setPreferredSize(new java.awt.Dimension(251, 672));

        lbl_addStaff.setBackground(new java.awt.Color(48, 201, 235));
        lbl_addStaff.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lbl_addStaff.setForeground(new java.awt.Color(166, 166, 166));
        lbl_addStaff.setIcon(new javax.swing.ImageIcon(getClass().getResource("/view/images/plus_18px.png"))); // NOI18N
        lbl_addStaff.setText("Thêm nhân viên");
        lbl_addStaff.setToolTipText("");
        lbl_addStaff.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lbl_addStaffMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                lbl_addStaffMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                lbl_addStaffMouseExited(evt);
            }
        });

        lbl_out.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lbl_out.setForeground(new java.awt.Color(166, 166, 166));
        lbl_out.setIcon(new javax.swing.ImageIcon(getClass().getResource("/view/images/icons8-left-18.png"))); // NOI18N
        lbl_out.setText("Thoát");
        lbl_out.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lbl_outMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                lbl_outMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                lbl_outMouseExited(evt);
            }
        });

        jPanel1.setBackground(new java.awt.Color(22, 27, 33));

        jLabel2.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(139, 137, 217));
        jLabel2.setText("Sales Management ©");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(53, 53, 53)
                .addComponent(jLabel2)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(27, Short.MAX_VALUE)
                .addComponent(jLabel2)
                .addGap(24, 24, 24))
        );

        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/view/images/speech_bubble_25px.png"))); // NOI18N

        lbl_addUser.setBackground(new java.awt.Color(48, 201, 235));
        lbl_addUser.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lbl_addUser.setForeground(new java.awt.Color(166, 166, 166));
        lbl_addUser.setIcon(new javax.swing.ImageIcon(getClass().getResource("/view/images/plus_18px.png"))); // NOI18N
        lbl_addUser.setText("Đăng ký tài khoản");
        lbl_addUser.setToolTipText("");
        lbl_addUser.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lbl_addUserMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                lbl_addUserMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                lbl_addUserMouseExited(evt);
            }
        });

        javax.swing.GroupLayout sidebarLayout = new javax.swing.GroupLayout(sidebar);
        sidebar.setLayout(sidebarLayout);
        sidebarLayout.setHorizontalGroup(
            sidebarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, sidebarLayout.createSequentialGroup()
                .addContainerGap(35, Short.MAX_VALUE)
                .addGroup(sidebarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, sidebarLayout.createSequentialGroup()
                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(92, 92, 92))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, sidebarLayout.createSequentialGroup()
                        .addGroup(sidebarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(lbl_addUser, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lbl_out, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lbl_addStaff, javax.swing.GroupLayout.PREFERRED_SIZE, 186, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(30, 30, 30))))
        );
        sidebarLayout.setVerticalGroup(
            sidebarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(sidebarLayout.createSequentialGroup()
                .addGap(56, 56, 56)
                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(59, 59, 59)
                .addComponent(lbl_addStaff, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lbl_addUser, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lbl_out, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        header.setBackground(new java.awt.Color(34, 41, 50));
        header.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jTextField_searchNV.setBackground(new java.awt.Color(34, 41, 50));
        jTextField_searchNV.setFont(new java.awt.Font("Segoe UI Light", 2, 14)); // NOI18N
        jTextField_searchNV.setForeground(new java.awt.Color(255, 255, 255));
        jTextField_searchNV.setText("Tìm nhân viên");
        jTextField_searchNV.setBorder(null);
        jTextField_searchNV.setCaretColor(new java.awt.Color(255, 255, 255));
        jTextField_searchNV.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        jTextField_searchNV.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTextField_searchNVMouseClicked(evt);
            }
        });
        jTextField_searchNV.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextField_searchNVKeyReleased(evt);
            }
        });
        header.add(jTextField_searchNV, new org.netbeans.lib.awtextra.AbsoluteConstraints(526, 31, 250, 25));

        jSeparator1.setBackground(java.awt.SystemColor.inactiveCaption);
        jSeparator1.setForeground(java.awt.SystemColor.inactiveCaption);
        header.add(jSeparator1, new org.netbeans.lib.awtextra.AbsoluteConstraints(526, 57, 250, -1));

        jLabel16.setIcon(new javax.swing.ImageIcon(getClass().getResource("/view/images/search_20px.png"))); // NOI18N
        header.add(jLabel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 39, -1, -1));

        jPanel_clomin.setBackground(new java.awt.Color(34, 41, 50));
        jPanel_clomin.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel_mini.setBackground(new java.awt.Color(34, 41, 50));
        jLabel_mini.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel_mini.setForeground(new java.awt.Color(255, 255, 255));
        jLabel_mini.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel_mini.setText("-");
        jLabel_mini.setOpaque(true);
        jLabel_mini.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel_miniMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jLabel_miniMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jLabel_miniMouseExited(evt);
            }
        });
        jPanel_clomin.add(jLabel_mini, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 40, 25));

        jLabel_close.setBackground(new java.awt.Color(34, 41, 50));
        jLabel_close.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
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
        jPanel_clomin.add(jLabel_close, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 0, 40, 25));

        header.add(jPanel_clomin, new org.netbeans.lib.awtextra.AbsoluteConstraints(702, 0, -1, -1));

        btn_staff1.setBackground(new java.awt.Color(139, 137, 217));
        btn_staff1.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btn_staff1.setForeground(new java.awt.Color(255, 255, 255));
        btn_staff1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        btn_staff1.setText("Nhân Viên");
        btn_staff1.setOpaque(true);
        btn_staff1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btn_staff1MouseClicked(evt);
            }
        });
        header.add(btn_staff1, new org.netbeans.lib.awtextra.AbsoluteConstraints(6, 40, 146, 30));

        btn_users1.setBackground(new java.awt.Color(34, 41, 50));
        btn_users1.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btn_users1.setForeground(new java.awt.Color(255, 255, 255));
        btn_users1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        btn_users1.setText("Phân Quyền");
        btn_users1.setOpaque(true);
        btn_users1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btn_users1MouseClicked(evt);
            }
        });
        header.add(btn_users1, new org.netbeans.lib.awtextra.AbsoluteConstraints(158, 40, 146, 30));

        btn_permission1.setBackground(new java.awt.Color(34, 41, 50));
        btn_permission1.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btn_permission1.setForeground(new java.awt.Color(255, 255, 255));
        btn_permission1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        btn_permission1.setText("Quyền Truy Cập");
        btn_permission1.setOpaque(true);
        btn_permission1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btn_permission1MouseClicked(evt);
            }
        });
        header.add(btn_permission1, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 40, 146, 30));

        jTextField_searchTK.setBackground(new java.awt.Color(34, 41, 50));
        jTextField_searchTK.setFont(new java.awt.Font("Segoe UI Light", 2, 14)); // NOI18N
        jTextField_searchTK.setForeground(new java.awt.Color(255, 255, 255));
        jTextField_searchTK.setText("Tìm tài khoản");
        jTextField_searchTK.setBorder(null);
        jTextField_searchTK.setCaretColor(new java.awt.Color(255, 255, 255));
        jTextField_searchTK.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        jTextField_searchTK.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTextField_searchTKMouseClicked(evt);
            }
        });
        jTextField_searchTK.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextField_searchTKKeyReleased(evt);
            }
        });
        header.add(jTextField_searchTK, new org.netbeans.lib.awtextra.AbsoluteConstraints(526, 31, 250, 25));

        body.setBackground(new java.awt.Color(242, 158, 3));
        body.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        body.setVisible(false);

        control.setBackground(new java.awt.Color(242, 158, 3));
        control.setPreferredSize(new java.awt.Dimension(782, 252));
        control.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                controlMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                controlMouseExited(evt);
            }
        });
        control.setLayout(new java.awt.CardLayout());

        body_staff.setBackground(new java.awt.Color(56, 53, 140));
        body_staff.setPreferredSize(new java.awt.Dimension(782, 252));
        body_staff.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                body_staffMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                body_staffMouseExited(evt);
            }
        });

        jLabel10.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(255, 255, 255));
        jLabel10.setText("set text");

        jLabel14.setFont(new java.awt.Font("Segoe UI Light", 0, 14)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(255, 255, 255));
        jLabel14.setText("Tên nhân viên");

        jLabel20.setFont(new java.awt.Font("Segoe UI Light", 0, 14)); // NOI18N
        jLabel20.setForeground(new java.awt.Color(255, 255, 255));
        jLabel20.setText("Ngày sinh");

        jLabel21.setFont(new java.awt.Font("Segoe UI Light", 0, 14)); // NOI18N
        jLabel21.setForeground(new java.awt.Color(255, 255, 255));
        jLabel21.setText("Địa chỉ");

        txt_Address.setBackground(new java.awt.Color(240, 240, 240));
        txt_Address.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        txt_Address.setForeground(new java.awt.Color(34, 41, 50));
        txt_Address.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(139, 137, 217)));
        txt_Address.setCaretColor(new java.awt.Color(34, 41, 50));
        txt_Address.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_AddressActionPerformed(evt);
            }
        });

        jLabel22.setFont(new java.awt.Font("Segoe UI Light", 0, 14)); // NOI18N
        jLabel22.setForeground(new java.awt.Color(255, 255, 255));
        jLabel22.setText("Lương");

        txt_Luong.setBackground(new java.awt.Color(240, 240, 240));
        txt_Luong.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        txt_Luong.setForeground(new java.awt.Color(34, 41, 50));
        txt_Luong.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(139, 137, 217)));
        txt_Luong.setCaretColor(new java.awt.Color(34, 41, 50));
        txt_Luong.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_LuongActionPerformed(evt);
            }
        });

        jRadioButtonMale.setBackground(new java.awt.Color(56, 53, 140));
        buttonGroup1.add(jRadioButtonMale);
        jRadioButtonMale.setFont(new java.awt.Font("Segoe UI Light", 0, 14)); // NOI18N
        jRadioButtonMale.setForeground(new java.awt.Color(240, 240, 240));
        jRadioButtonMale.setText("Nam");
        jRadioButtonMale.setBorder(null);
        jRadioButtonMale.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButtonMaleActionPerformed(evt);
            }
        });

        jRadioButtonFemale.setBackground(new java.awt.Color(56, 53, 140));
        buttonGroup1.add(jRadioButtonFemale);
        jRadioButtonFemale.setFont(new java.awt.Font("Segoe UI Light", 0, 14)); // NOI18N
        jRadioButtonFemale.setForeground(new java.awt.Color(240, 240, 240));
        jRadioButtonFemale.setText("Nữ");
        jRadioButtonFemale.setBorder(null);

        btn_ADDorEDIT.setBackground(new java.awt.Color(56, 53, 140));
        btn_ADDorEDIT.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btn_ADDorEDIT.setForeground(new java.awt.Color(255, 255, 255));
        btn_ADDorEDIT.setText("set text");
        btn_ADDorEDIT.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(139, 137, 217)));
        btn_ADDorEDIT.setContentAreaFilled(false);
        btn_ADDorEDIT.setFocusPainted(false);
        btn_ADDorEDIT.setOpaque(true);
        btn_ADDorEDIT.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btn_ADDorEDITMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btn_ADDorEDITMouseExited(evt);
            }
        });
        btn_ADDorEDIT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_ADDorEDITActionPerformed(evt);
            }
        });

        txt_Name.setBackground(new java.awt.Color(240, 240, 240));
        txt_Name.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        txt_Name.setForeground(new java.awt.Color(34, 41, 50));
        txt_Name.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(139, 137, 217)));
        txt_Name.setCaretColor(new java.awt.Color(34, 41, 50));
        txt_Name.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_NameActionPerformed(evt);
            }
        });

        jLabel_staffID.setFont(new java.awt.Font("Segoe UI Light", 0, 18)); // NOI18N
        jLabel_staffID.setForeground(new java.awt.Color(255, 255, 255));
        jLabel_staffID.setText("ID nhân viên : get ID nv");

        jLabel23.setFont(new java.awt.Font("Segoe UI Light", 0, 14)); // NOI18N
        jLabel23.setForeground(new java.awt.Color(255, 255, 255));
        jLabel23.setText("Giới tính");

        dateChooserCombo1.setCurrentView(new datechooser.view.appearance.AppearancesList("Light",
            new datechooser.view.appearance.ViewAppearance("custom",
                new datechooser.view.appearance.swing.SwingCellAppearance(new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 14),
                    new java.awt.Color(0, 0, 0),
                    new java.awt.Color(0, 0, 255),
                    false,
                    true,
                    new datechooser.view.appearance.swing.ButtonPainter()),
                new datechooser.view.appearance.swing.SwingCellAppearance(new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 14),
                    new java.awt.Color(0, 0, 0),
                    new java.awt.Color(0, 0, 255),
                    true,
                    true,
                    new datechooser.view.appearance.swing.ButtonPainter()),
                new datechooser.view.appearance.swing.SwingCellAppearance(new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 14),
                    new java.awt.Color(0, 0, 255),
                    new java.awt.Color(0, 0, 255),
                    false,
                    true,
                    new datechooser.view.appearance.swing.ButtonPainter()),
                new datechooser.view.appearance.swing.SwingCellAppearance(new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 14),
                    new java.awt.Color(128, 128, 128),
                    new java.awt.Color(0, 0, 255),
                    false,
                    true,
                    new datechooser.view.appearance.swing.LabelPainter()),
                new datechooser.view.appearance.swing.SwingCellAppearance(new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 14),
                    new java.awt.Color(0, 0, 0),
                    new java.awt.Color(0, 0, 255),
                    false,
                    true,
                    new datechooser.view.appearance.swing.LabelPainter()),
                new datechooser.view.appearance.swing.SwingCellAppearance(new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 14),
                    new java.awt.Color(0, 0, 0),
                    new java.awt.Color(255, 0, 0),
                    false,
                    false,
                    new datechooser.view.appearance.swing.ButtonPainter()),
                (datechooser.view.BackRenderer)null,
                false,
                true)));
    dateChooserCombo1.setCalendarBackground(new java.awt.Color(56, 53, 140));
    dateChooserCombo1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(139, 137, 217), 1));
    dateChooserCombo1.setFieldFont(new java.awt.Font("SansSerif", java.awt.Font.PLAIN, 12));
    dateChooserCombo1.setLocale(new java.util.Locale("vi", "", ""));
    dateChooserCombo1.setNavigateFont(new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 11));

    jLabel24.setFont(new java.awt.Font("Segoe UI Light", 0, 14)); // NOI18N
    jLabel24.setForeground(new java.awt.Color(255, 255, 255));
    jLabel24.setText("Phone");

    txt_Phone.setBackground(new java.awt.Color(240, 240, 240));
    txt_Phone.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
    txt_Phone.setForeground(new java.awt.Color(34, 41, 50));
    txt_Phone.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(139, 137, 217)));
    txt_Phone.setCaretColor(new java.awt.Color(34, 41, 50));
    txt_Phone.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            txt_PhoneActionPerformed(evt);
        }
    });

    jLabel25.setFont(new java.awt.Font("Segoe UI Light", 0, 14)); // NOI18N
    jLabel25.setForeground(new java.awt.Color(255, 255, 255));
    jLabel25.setText("Email");

    txt_Email.setBackground(new java.awt.Color(240, 240, 240));
    txt_Email.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
    txt_Email.setForeground(new java.awt.Color(34, 41, 50));
    txt_Email.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(139, 137, 217)));
    txt_Email.setCaretColor(new java.awt.Color(34, 41, 50));
    txt_Email.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            txt_EmailActionPerformed(evt);
        }
    });

    jButton1.setText("jButton1");
    jButton1.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            jButton1ActionPerformed(evt);
        }
    });

    javax.swing.GroupLayout body_staffLayout = new javax.swing.GroupLayout(body_staff);
    body_staff.setLayout(body_staffLayout);
    body_staffLayout.setHorizontalGroup(
        body_staffLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(body_staffLayout.createSequentialGroup()
            .addContainerGap()
            .addGroup(body_staffLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(body_staffLayout.createSequentialGroup()
                    .addGroup(body_staffLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(body_staffLayout.createSequentialGroup()
                            .addComponent(jLabel10)
                            .addGap(18, 18, 18)
                            .addComponent(jLabel_staffID))
                        .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, 755, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGap(0, 0, Short.MAX_VALUE))
                .addGroup(body_staffLayout.createSequentialGroup()
                    .addGroup(body_staffLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jLabel14)
                        .addComponent(jLabel23)
                        .addComponent(txt_Name, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(body_staffLayout.createSequentialGroup()
                            .addComponent(jRadioButtonMale)
                            .addGap(18, 18, 18)
                            .addComponent(jRadioButtonFemale)))
                    .addGap(18, 18, 18)
                    .addGroup(body_staffLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jLabel20)
                        .addComponent(dateChooserCombo1, javax.swing.GroupLayout.PREFERRED_SIZE, 174, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(body_staffLayout.createSequentialGroup()
                            .addComponent(jLabel24)
                            .addGap(78, 78, 78)
                            .addComponent(jLabel25))
                        .addComponent(txt_Phone, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGap(18, 18, 18)
                    .addGroup(body_staffLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(body_staffLayout.createSequentialGroup()
                            .addComponent(jButton1)
                            .addGap(132, 132, 132)
                            .addComponent(btn_ADDorEDIT, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(body_staffLayout.createSequentialGroup()
                            .addComponent(txt_Email, javax.swing.GroupLayout.PREFERRED_SIZE, 188, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(18, 18, 18)
                            .addGroup(body_staffLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel22)
                                .addComponent(txt_Luong, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addComponent(jLabel21)
                        .addComponent(txt_Address, javax.swing.GroupLayout.PREFERRED_SIZE, 381, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addContainerGap(16, Short.MAX_VALUE))))
    );
    body_staffLayout.setVerticalGroup(
        body_staffLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(body_staffLayout.createSequentialGroup()
            .addContainerGap()
            .addGroup(body_staffLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                .addComponent(jButton1)
                .addGroup(body_staffLayout.createSequentialGroup()
                    .addGroup(body_staffLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel10)
                        .addComponent(jLabel_staffID))
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, 2, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addGroup(body_staffLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(body_staffLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel14)
                            .addComponent(jLabel20))
                        .addComponent(jLabel21, javax.swing.GroupLayout.Alignment.TRAILING))
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addGroup(body_staffLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txt_Address, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txt_Name, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(dateChooserCombo1, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(body_staffLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, body_staffLayout.createSequentialGroup()
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(jLabel22)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(txt_Luong, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(body_staffLayout.createSequentialGroup()
                            .addGap(6, 6, 6)
                            .addGroup(body_staffLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(body_staffLayout.createSequentialGroup()
                                    .addGroup(body_staffLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel24)
                                        .addComponent(jLabel25))
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addGroup(body_staffLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(txt_Phone, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(txt_Email, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGroup(body_staffLayout.createSequentialGroup()
                                    .addComponent(jLabel23)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addGroup(body_staffLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jRadioButtonMale)
                                        .addComponent(jRadioButtonFemale))))))
                    .addGap(18, 18, 18)
                    .addComponent(btn_ADDorEDIT, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)))
            .addContainerGap(25, Short.MAX_VALUE))
    );

    control.add(body_staff, "controlStaff");

    body_users.setBackground(new java.awt.Color(56, 53, 140));

    jLabel32.setFont(new java.awt.Font("Segoe UI Light", 0, 14)); // NOI18N
    jLabel32.setForeground(new java.awt.Color(242, 242, 242));
    jLabel32.setText("ID nhân viên");

    jLabel34.setFont(new java.awt.Font("Segoe UI Light", 0, 14)); // NOI18N
    jLabel34.setForeground(new java.awt.Color(242, 242, 242));
    jLabel34.setText("Phân quyền");

    txt_staffID.setEditable(false);
    txt_staffID.setFont(new java.awt.Font("Dialog", 2, 14)); // NOI18N
    txt_staffID.setForeground(new java.awt.Color(34, 41, 50));
    txt_staffID.setText("set text");
    txt_staffID.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(139, 137, 217)));
    txt_staffID.setCaretColor(new java.awt.Color(34, 41, 50));
    txt_staffID.setPreferredSize(new java.awt.Dimension(200, 30));
    txt_staffID.addMouseListener(new java.awt.event.MouseAdapter() {
        public void mouseClicked(java.awt.event.MouseEvent evt) {
            txt_staffIDMouseClicked(evt);
        }
    });

    jButton_user.setBackground(new java.awt.Color(56, 53, 140));
    jButton_user.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
    jButton_user.setForeground(new java.awt.Color(255, 255, 255));
    jButton_user.setText("set text");
    jButton_user.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(139, 137, 217)));
    jButton_user.setContentAreaFilled(false);
    jButton_user.setFocusPainted(false);
    jButton_user.setOpaque(true);
    jButton_user.addMouseListener(new java.awt.event.MouseAdapter() {
        public void mouseEntered(java.awt.event.MouseEvent evt) {
            jButton_userMouseEntered(evt);
        }
        public void mouseExited(java.awt.event.MouseEvent evt) {
            jButton_userMouseExited(evt);
        }
    });
    jButton_user.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            jButton_userActionPerformed(evt);
        }
    });

    cb_phanquyen.setBackground(new java.awt.Color(240, 240, 240));
    cb_phanquyen.setForeground(new java.awt.Color(34, 41, 50));
    cb_phanquyen.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
    cb_phanquyen.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(139, 137, 217)));
    cb_phanquyen.setPreferredSize(new java.awt.Dimension(250, 30));
    cb_phanquyen.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            cb_phanquyenActionPerformed(evt);
        }
    });

    jLabel33.setFont(new java.awt.Font("Segoe UI Light", 0, 14)); // NOI18N
    jLabel33.setForeground(new java.awt.Color(242, 242, 242));
    jLabel33.setText("Xác nhận mật khẩu");

    jLabel37.setFont(new java.awt.Font("Segoe UI Light", 0, 14)); // NOI18N
    jLabel37.setForeground(new java.awt.Color(242, 242, 242));
    jLabel37.setText("Mật khẩu");

    jLabel38.setFont(new java.awt.Font("Segoe UI Light", 0, 14)); // NOI18N
    jLabel38.setForeground(new java.awt.Color(242, 242, 242));
    jLabel38.setText("Tên tài khoản");

    txt_checkpassword.setBackground(new java.awt.Color(240, 240, 240));
    txt_checkpassword.setForeground(new java.awt.Color(34, 41, 50));
    txt_checkpassword.setText("jPasswordField1");
    txt_checkpassword.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(139, 137, 217)));
    txt_checkpassword.setCaretColor(new java.awt.Color(34, 41, 50));
    txt_checkpassword.setPreferredSize(new java.awt.Dimension(250, 30));
    txt_checkpassword.addFocusListener(new java.awt.event.FocusAdapter() {
        public void focusGained(java.awt.event.FocusEvent evt) {
            txt_checkpasswordFocusGained(evt);
        }
    });

    txt_password.setBackground(new java.awt.Color(240, 240, 240));
    txt_password.setForeground(new java.awt.Color(34, 41, 50));
    txt_password.setText("jPasswordField1");
    txt_password.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(139, 137, 217)));
    txt_password.setCaretColor(new java.awt.Color(34, 41, 50));
    txt_password.setPreferredSize(new java.awt.Dimension(250, 30));
    txt_password.addFocusListener(new java.awt.event.FocusAdapter() {
        public void focusGained(java.awt.event.FocusEvent evt) {
            txt_passwordFocusGained(evt);
        }
    });

    txt_newuser.setBackground(new java.awt.Color(240, 240, 240));
    txt_newuser.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
    txt_newuser.setForeground(new java.awt.Color(34, 41, 50));
    txt_newuser.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(139, 137, 217)));
    txt_newuser.setCaretColor(new java.awt.Color(34, 41, 50));
    txt_newuser.setPreferredSize(new java.awt.Dimension(200, 30));
    txt_newuser.addFocusListener(new java.awt.event.FocusAdapter() {
        public void focusGained(java.awt.event.FocusEvent evt) {
            txt_newuserFocusGained(evt);
        }
    });
    txt_newuser.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            txt_newuserActionPerformed(evt);
        }
    });

    jLabel11.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
    jLabel11.setForeground(new java.awt.Color(255, 255, 255));
    jLabel11.setText("set text");

    jLabel_userID.setFont(new java.awt.Font("Segoe UI Light", 0, 18)); // NOI18N
    jLabel_userID.setForeground(new java.awt.Color(255, 255, 255));
    jLabel_userID.setText("ID tài khoản : get ID tk");

    javax.swing.GroupLayout body_usersLayout = new javax.swing.GroupLayout(body_users);
    body_users.setLayout(body_usersLayout);
    body_usersLayout.setHorizontalGroup(
        body_usersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(body_usersLayout.createSequentialGroup()
            .addContainerGap()
            .addGroup(body_usersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                .addComponent(jButton_user, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(jLabel34)
                .addComponent(cb_phanquyen, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(jSeparator4, javax.swing.GroupLayout.PREFERRED_SIZE, 755, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(body_usersLayout.createSequentialGroup()
                    .addComponent(jLabel11)
                    .addGap(18, 18, 18)
                    .addComponent(jLabel_userID))
                .addGroup(body_usersLayout.createSequentialGroup()
                    .addGroup(body_usersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(body_usersLayout.createSequentialGroup()
                            .addGroup(body_usersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addGroup(body_usersLayout.createSequentialGroup()
                                    .addGap(218, 218, 218)
                                    .addComponent(jLabel38)
                                    .addGap(137, 137, 137))
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, body_usersLayout.createSequentialGroup()
                                    .addComponent(txt_staffID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(txt_newuser, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(18, 18, 18)))
                            .addGroup(body_usersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel37)
                                .addComponent(txt_password, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addComponent(jLabel32))
                    .addGap(18, 18, 18)
                    .addGroup(body_usersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jLabel33)
                        .addComponent(txt_checkpassword, javax.swing.GroupLayout.PREFERRED_SIZE, 1, Short.MAX_VALUE))))
            .addContainerGap(21, Short.MAX_VALUE))
    );
    body_usersLayout.setVerticalGroup(
        body_usersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(body_usersLayout.createSequentialGroup()
            .addContainerGap()
            .addGroup(body_usersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(jLabel11)
                .addComponent(jLabel_userID))
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(jSeparator4, javax.swing.GroupLayout.PREFERRED_SIZE, 2, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addGroup(body_usersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jLabel32, javax.swing.GroupLayout.Alignment.TRAILING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, body_usersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel37)
                    .addComponent(jLabel33))
                .addComponent(jLabel38, javax.swing.GroupLayout.Alignment.TRAILING))
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addGroup(body_usersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(txt_checkpassword, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(txt_newuser, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(txt_staffID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(txt_password, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(jLabel34)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(cb_phanquyen, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addGap(18, 18, 18)
            .addComponent(jButton_user, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addContainerGap(31, Short.MAX_VALUE))
    );

    control.add(body_users, "controlUsers");

    body.add(control, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, 260));

    tabFooter.setBackground(new java.awt.Color(56, 53, 140));
    tabFooter.setPreferredSize(new java.awt.Dimension(782, 46));

    btn_staff.setBackground(new java.awt.Color(139, 137, 217));
    btn_staff.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
    btn_staff.setForeground(new java.awt.Color(255, 255, 255));
    btn_staff.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
    btn_staff.setText("Nhân Viên");
    btn_staff.setOpaque(true);
    btn_staff.addMouseListener(new java.awt.event.MouseAdapter() {
        public void mouseClicked(java.awt.event.MouseEvent evt) {
            btn_staffMouseClicked(evt);
        }
    });

    btn_users.setBackground(new java.awt.Color(56, 53, 140));
    btn_users.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
    btn_users.setForeground(new java.awt.Color(255, 255, 255));
    btn_users.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
    btn_users.setText("Phân Quyền");
    btn_users.setOpaque(true);
    btn_users.addMouseListener(new java.awt.event.MouseAdapter() {
        public void mouseClicked(java.awt.event.MouseEvent evt) {
            btn_usersMouseClicked(evt);
        }
    });

    btn_permission.setBackground(new java.awt.Color(56, 53, 140));
    btn_permission.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
    btn_permission.setForeground(new java.awt.Color(255, 255, 255));
    btn_permission.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
    btn_permission.setText("Quyền Truy Cập");
    btn_permission.setOpaque(true);
    btn_permission.addMouseListener(new java.awt.event.MouseAdapter() {
        public void mouseClicked(java.awt.event.MouseEvent evt) {
            btn_permissionMouseClicked(evt);
        }
    });

    javax.swing.GroupLayout tabFooterLayout = new javax.swing.GroupLayout(tabFooter);
    tabFooter.setLayout(tabFooterLayout);
    tabFooterLayout.setHorizontalGroup(
        tabFooterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(tabFooterLayout.createSequentialGroup()
            .addContainerGap()
            .addComponent(btn_staff, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(btn_users, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(btn_permission, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
    );
    tabFooterLayout.setVerticalGroup(
        tabFooterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, tabFooterLayout.createSequentialGroup()
            .addGap(0, 20, Short.MAX_VALUE)
            .addGroup(tabFooterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(btn_staff, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(btn_users, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(btn_permission, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))
    );

    body.add(tabFooter, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 250, -1, 50));

    footer.setPreferredSize(new java.awt.Dimension(782, 400));
    footer.setLayout(new java.awt.CardLayout());

    staff.setBackground(java.awt.SystemColor.controlShadow);
    staff.setLayout(new javax.swing.BoxLayout(staff, javax.swing.BoxLayout.LINE_AXIS));

    jScrollPane1.setBackground(java.awt.SystemColor.controlShadow);
    jScrollPane1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(139, 137, 217)));

    jTableNV.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
    jTableNV.setForeground(new java.awt.Color(51, 51, 51));
    jTableNV.setModel(new javax.swing.table.DefaultTableModel(
        new Object [][] {

        },
        new String [] {
            "ID Nhân Viên", "Tên Nhân Viên", "Ngày Sinh", "Giới Tính", "Phone", "Địa Chỉ", "Email", "Lương"
        }
    ) {
        Class[] types = new Class [] {
            java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Double.class
        };

        public Class getColumnClass(int columnIndex) {
            return types [columnIndex];
        }
    });
    jTableNV.setGridColor(new java.awt.Color(139, 137, 217));
    jTableNV.setRowHeight(20);
    jTableNV.setSelectionBackground(new java.awt.Color(139, 137, 217));
    jTableNV.addMouseListener(new java.awt.event.MouseAdapter() {
        public void mouseClicked(java.awt.event.MouseEvent evt) {
            jTableNVMouseClicked(evt);
        }
    });
    jScrollPane1.setViewportView(jTableNV);

    staff.add(jScrollPane1);

    footer.add(staff, "footerStaff");

    users.setBackground(java.awt.SystemColor.controlShadow);
    users.setLayout(new javax.swing.BoxLayout(users, javax.swing.BoxLayout.LINE_AXIS));

    jScrollPane2.setBackground(java.awt.SystemColor.controlShadow);
    jScrollPane2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(139, 137, 217)));

    jTableUser.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
    jTableUser.setForeground(new java.awt.Color(51, 51, 51));
    jTableUser.setModel(new javax.swing.table.DefaultTableModel(
        new Object [][] {

        },
        new String [] {
            "ID Tài Khoản", "ID Nhân Viên", "Tên Tài Khoản", "Mật Khẩu", "ID Phân Quyền"
        }
    ) {
        Class[] types = new Class [] {
            java.lang.Integer.class, java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
        };

        public Class getColumnClass(int columnIndex) {
            return types [columnIndex];
        }
    });
    jTableUser.setGridColor(new java.awt.Color(139, 137, 217));
    jTableUser.setRowHeight(20);
    jTableUser.setSelectionBackground(new java.awt.Color(139, 137, 217));
    jTableUser.addMouseListener(new java.awt.event.MouseAdapter() {
        public void mouseClicked(java.awt.event.MouseEvent evt) {
            jTableUserMouseClicked(evt);
        }
    });
    jScrollPane2.setViewportView(jTableUser);

    users.add(jScrollPane2);

    footer.add(users, "footerUsers");

    userPermission.setBackground(java.awt.SystemColor.controlShadow);
    userPermission.setLayout(new javax.swing.BoxLayout(userPermission, javax.swing.BoxLayout.LINE_AXIS));

    jScrollPane3.setBackground(java.awt.SystemColor.controlShadow);
    jScrollPane3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(139, 137, 217)));

    jTablePos.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
    jTablePos.setForeground(new java.awt.Color(51, 51, 51));
    jTablePos.setModel(new javax.swing.table.DefaultTableModel(
        new Object [][] {

        },
        new String [] {
            "ID phân quyền", "Quyền truy cập"
        }
    ));
    jTablePos.setGridColor(new java.awt.Color(139, 137, 217));
    jTablePos.setRowHeight(20);
    jTablePos.setSelectionBackground(new java.awt.Color(139, 137, 217));
    jScrollPane3.setViewportView(jTablePos);

    userPermission.add(jScrollPane3);

    footer.add(userPermission, "footerPermission");

    javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
    getContentPane().setLayout(layout);
    layout.setHorizontalGroup(
        layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(layout.createSequentialGroup()
            .addComponent(sidebar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(header, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(footer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(body, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
    );
    layout.setVerticalGroup(
        layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(layout.createSequentialGroup()
            .addComponent(header, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(body, javax.swing.GroupLayout.PREFERRED_SIZE, 299, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(footer, javax.swing.GroupLayout.DEFAULT_SIZE, 291, Short.MAX_VALUE))
        .addComponent(sidebar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
    );

    pack();
    setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btn_staffMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_staffMouseClicked
        setLblColorTab(btn_staff);
        resetLblColorTab(btn_users);
        resetLblColorTab(btn_permission);
        //switch bettween Jpanels
        CardLayout cardLayout = (CardLayout) footer.getLayout();
        cardLayout.show(footer, "footerStaff");
        jTextField_searchNV.setVisible(true);
        jTextField_searchNV.setText("Tìm nhân viên");
        jTextField_searchTK.setVisible(false);
        jLabel16.setVisible(true);
        jSeparator1.setVisible(true);
    }//GEN-LAST:event_btn_staffMouseClicked

    private void btn_usersMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_usersMouseClicked
        resetLblColorTab(btn_staff);
        setLblColorTab(btn_users);
        resetLblColorTab(btn_permission);
        //switch bettween Jpanels
        CardLayout cardLayout = (CardLayout) footer.getLayout();
        cardLayout.show(footer, "footerUsers");
        jTextField_searchTK.setVisible(true);
        jTextField_searchTK.setText("Tìm tài khoản");
        jTextField_searchNV.setVisible(false);
        jLabel16.setVisible(true);
        jSeparator1.setVisible(true);
    }//GEN-LAST:event_btn_usersMouseClicked

    private void txt_AddressActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_AddressActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_AddressActionPerformed

    private void txt_LuongActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_LuongActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_LuongActionPerformed

    private void txt_NameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_NameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_NameActionPerformed

    private void body_staffMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_body_staffMouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_body_staffMouseEntered

    private void body_staffMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_body_staffMouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_body_staffMouseExited

    private void jMenuItemEditNVActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemEditNVActionPerformed
        resetLblColor(lbl_addStaff);
        resetLblColor(lbl_addUser);
        setLblColorTab(btn_staff);
        resetLblColorTab(btn_users);
        resetLblColorTab(btn_permission);
        jLabel10.setText("Sửa thông tin nhân viên");
        jLabel_staffID.setText("ID nhân viên : get ID nv");
        btn_ADDorEDIT.setText("Cập nhật");
        CardLayout cardLayout = (CardLayout) control.getLayout();
        cardLayout.show(control, "controlStaff");
        body.setVisible(true);
        btn_staff1.setVisible(false);
        btn_users1.setVisible(false);
        btn_permission1.setVisible(false);

        int row = jTableNV.getSelectedRow();
        String EmpID = jTableNV.getModel().getValueAt(row, 0).toString();// get value at selected row and '0' is the first column (ID)
        jLabel_staffID.setText("ID nhân viên : " + EmpID);
        objnv = controller.EmployeeController.getEmpByID(Integer.parseInt(EmpID));
        txt_Name.setText(objnv.getEmpName());
        txt_Address.setText(objnv.getEmpAdress());
        txt_Phone.setText(objnv.getTellNo());
        txt_Email.setText(objnv.getEmail());
        txt_Luong.setText(currencyFormatter.format(objnv.getLuong()).replaceAll(",", ""));
        if (objnv.getDoB() != null) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(objnv.getDoB());
            dateChooserCombo1.setSelectedDate(calendar);
        }
        if (objnv.getGender().equals("Nam")) {
            jRadioButtonMale.setSelected(true);
        } else {
            jRadioButtonFemale.setSelected(true);
        }
    }//GEN-LAST:event_jMenuItemEditNVActionPerformed

    private void jMenuItemDeleteNVActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemDeleteNVActionPerformed
        int row = jTableNV.getSelectedRow();
        int EmpID = Integer.valueOf(String.valueOf(jTableNV.getValueAt(row, 0)));
        controller.EmployeeController.xoanhanvien(EmpID);
        createTableDataNV();
    }//GEN-LAST:event_jMenuItemDeleteNVActionPerformed

    private void jTableNVMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTableNVMouseClicked
        if (SwingUtilities.isRightMouseButton(evt)) {
            jTableNV.clearSelection();
            int row = jTableNV.rowAtPoint(evt.getPoint());
            jTableNV.setRowSelectionInterval(row, row);
            jPopupMenuNV.show(jTableNV, evt.getX(), evt.getY());
        }
    }//GEN-LAST:event_jTableNVMouseClicked


    private void jLabel_miniMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel_miniMouseClicked
        setState(Frame.ICONIFIED);
    }//GEN-LAST:event_jLabel_miniMouseClicked

    private void jLabel_miniMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel_miniMouseEntered
        jLabel_mini.setBackground(new Color(139, 137, 217));
    }//GEN-LAST:event_jLabel_miniMouseEntered

    private void jLabel_miniMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel_miniMouseExited
        jLabel_mini.setBackground(new Color(34, 41, 50));
    }//GEN-LAST:event_jLabel_miniMouseExited

    private void jLabel_closeMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel_closeMouseClicked
        setVisible(false);
        dispose();
    }//GEN-LAST:event_jLabel_closeMouseClicked

    private void jLabel_closeMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel_closeMouseEntered
        jLabel_close.setBackground(new Color(139, 137, 217));
    }//GEN-LAST:event_jLabel_closeMouseEntered

    private void jLabel_closeMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel_closeMouseExited
        jLabel_close.setBackground(new Color(34, 41, 50));
    }//GEN-LAST:event_jLabel_closeMouseExited

    private void lbl_outMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbl_outMouseClicked
        setVisible(false);
        dispose();
    }//GEN-LAST:event_lbl_outMouseClicked

    private void lbl_addStaffMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbl_addStaffMouseClicked
        setLblColorTab(btn_staff);
        resetLblColorTab(btn_users);
        resetLblColorTab(btn_permission);
        jLabel10.setText("Thêm nhân viên");
        jLabel_staffID.setText("");
        btn_ADDorEDIT.setText("Thêm");
        CardLayout cardLayout = (CardLayout) control.getLayout();
        cardLayout.show(control, "controlStaff");
        cardLayout = (CardLayout) footer.getLayout();
        cardLayout.show(footer, "footerStaff");
        jTextField_searchNV.setVisible(true);
        jTextField_searchNV.setText("Tìm nhân viên");
        jTextField_searchTK.setVisible(false);
        jLabel16.setVisible(true);
        jSeparator1.setVisible(true);
        body.setVisible(true);
        btn_staff1.setVisible(false);
        btn_users1.setVisible(false);
        btn_permission1.setVisible(false);
        txt_Name.setText("");
        txt_Address.setText("");
        txt_Phone.setText("");
        txt_Email.setText("");
        txt_Luong.setText("");
        buttonGroup1.clearSelection();
    }//GEN-LAST:event_lbl_addStaffMouseClicked

    private void btn_staff1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_staff1MouseClicked
        setLblColorTab(btn_staff1);
        btn_users1.setBackground(new Color(34, 41, 50));
        btn_permission1.setBackground(new Color(34, 41, 50));
        //switch bettween Jpanels
        CardLayout cardLayout = (CardLayout) footer.getLayout();
        cardLayout.show(footer, "footerStaff");
        jTextField_searchNV.setVisible(true);
        jTextField_searchNV.setText("Tìm nhân viên");
        jTextField_searchTK.setVisible(false);
        jLabel16.setVisible(true);
        jSeparator1.setVisible(true);
    }//GEN-LAST:event_btn_staff1MouseClicked

    private void btn_users1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_users1MouseClicked
        btn_staff1.setBackground(new Color(34, 41, 50));
        setLblColorTab(btn_users1);
        btn_permission1.setBackground(new Color(34, 41, 50));
        //switch bettween Jpanels
        CardLayout cardLayout = (CardLayout) footer.getLayout();
        cardLayout.show(footer, "footerUsers");
        jTextField_searchTK.setVisible(true);
        jTextField_searchTK.setText("Tìm tài khoản");
        jTextField_searchNV.setVisible(false);
        jLabel16.setVisible(true);
        jSeparator1.setVisible(true);
    }//GEN-LAST:event_btn_users1MouseClicked

    int xx;
    int xy;

    private void formMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMousePressed
        xx = evt.getX();
        xy = evt.getY();
        // This makes the window can be dragged (movable window)
    }//GEN-LAST:event_formMousePressed

    private void formMouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseDragged
        int x = evt.getXOnScreen();
        int y = evt.getYOnScreen();
        this.setLocation(x - xx, y - xy);
        // This makes the window can be dragged (movable window)
    }//GEN-LAST:event_formMouseDragged

    private void btn_ADDorEDITActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_ADDorEDITActionPerformed
        if (txt_Name.getText().isEmpty() || txt_Address.getText().isEmpty()) {
            new Message("Bạn cần nhập đầy đủ thông tin", 2, 4).setVisible(true);
        } else {
            objnv = new Employee();
            objnv.setEmpName(txt_Name.getText());
            objnv.setEmpAdress(txt_Address.getText());
            if (Validation.isPhoneNumber(txt_Phone.getText().trim())) {
                if (btn_ADDorEDIT.getText().equals("Thêm")) {
                    if (controller.EmployeeController.checkTellNoEmp(txt_Phone.getText().trim())) {
                        new Message("Số điện thoại trùng với nhân viên khác", 2, 4).setVisible(true);
                        return;
                    }
                } else {
                    if (controller.EmployeeController.checkTellNoOnUpdate(Integer.parseInt(jLabel_staffID.getText().substring(15)), txt_Phone.getText().trim()) == false) {
                        new Message("Số điện thoại trùng với nhân viên khác", 2, 4).setVisible(true);
                        return;
                    }
                }
                objnv.setTellNo(txt_Phone.getText());
            } else {
                new Message("Số điện thoại không đúng", 2, 4).setVisible(true);
                return;
            }
            if (Validation.isValidMail(txt_Email.getText().trim())) {
                if (btn_ADDorEDIT.getText().equals("Thêm")) {
                    if (controller.EmployeeController.checkEmailEmp(txt_Email.getText().trim())) {
                        new Message("Email trùng với nhân viên khác", 2, 4).setVisible(true);
                        return;
                    } else {
                    }
                } else {
                    if (controller.EmployeeController.checkEmailOnUpdate(Integer.parseInt(jLabel_staffID.getText().substring(15)), txt_Email.getText().trim()) == false) {
                        new Message("Email trùng với nhân viên khác", 2, 4).setVisible(true);
                        return;
                    }
                }
                objnv.setEmail(txt_Email.getText());
            } else {
                new Message("Email không đúng", 2, 4).setVisible(true);
                return;
            }
            if (Validation.checkinputInteger(txt_Luong.getText())) {
                objnv.setLuong(Double.parseDouble(txt_Luong.getText()));
            } else {
                new Message("Lương phải là số nguyên dương", 2, 4).setVisible(true);
                return;
            }
            try {
                java.util.Date javaDate = new SimpleDateFormat("dd/MM/yyyy").parse(dateChooserCombo1.getText());
                java.sql.Date sqlDate = new java.sql.Date(javaDate.getTime());
                objnv.setDoB(sqlDate);
            } catch (ParseException ex) {
                new Message("Lỗi nhập ngày sinh", 2, 4).setVisible(true);
                Logger.getLogger(QLNhanVien.class.getName()).log(Level.SEVERE, null, ex);
                return;
            }
            if (jRadioButtonMale.isSelected() || jRadioButtonFemale.isSelected()) {
                if (jRadioButtonMale.isSelected()) {
                    gender = "Nam";
                } else {
                    gender = "Nữ";
                }
                objnv.setGender(gender);
            } else {
                new Message("Chưa chọn giới tính !", 2, 4).setVisible(true);
                return;
            }
            if (btn_ADDorEDIT.getText().equals("Thêm")) {
                EmployeeController.AddEmployee(objnv);
            } else {
                objnv.setEmpID(Integer.parseInt(jLabel_staffID.getText().substring(15)));
                EmployeeController.EditEmployee(objnv);
            }
            createTableDataNV();
        }
    }//GEN-LAST:event_btn_ADDorEDITActionPerformed

    private void jMenuItemEditUserActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemEditUserActionPerformed
        resetLblColor(lbl_addStaff);
        resetLblColor(lbl_addUser);
        resetLblColorTab(btn_staff);
        setLblColorTab(btn_users);
        resetLblColorTab(btn_permission);
        jLabel11.setText("Sửa tài khoản nhân viên");
        jLabel_userID.setText("ID tài khoản : " + jTableUser.getValueAt(jTableUser.getSelectedRow(), 0));
        txt_newuser.setText(jTableUser.getValueAt(jTableUser.getSelectedRow(), 1).toString());
        txt_password.setText("");
        txt_checkpassword.setText("");
        txt_staffID.setText(jTableUser.getValueAt(jTableUser.getSelectedRow(), 3).toString());
        int permissionID = Integer.parseInt(jTableUser.getValueAt(jTableUser.getSelectedRow(), 4).toString());
        if (permissionID == 1) {
            cb_phanquyen.setSelectedIndex(0);
        } else {
            cb_phanquyen.setSelectedIndex(1);
        }
        jButton_user.setText("Cập nhật");
        CardLayout cardLayout = (CardLayout) control.getLayout();
        cardLayout.show(control, "controlUsers");
        body.setVisible(true);
        btn_staff1.setVisible(false);
        btn_users1.setVisible(false);
        btn_permission1.setVisible(false);
    }//GEN-LAST:event_jMenuItemEditUserActionPerformed

    private void jMenuItemDeleteUserActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemDeleteUserActionPerformed
        int row = jTableUser.getSelectedRow();
        String id = jTableUser.getModel().getValueAt(row, 0).toString();
        Accountcontroller.RemoveAccountByID(Integer.parseInt(id));
        createTableAccount();
    }//GEN-LAST:event_jMenuItemDeleteUserActionPerformed

    private void btn_permission1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_permission1MouseClicked
        btn_staff1.setBackground(new Color(34, 41, 50));
        btn_users1.setBackground(new Color(34, 41, 50));
        setLblColorTab(btn_permission1);
        //switch bettween Jpanels
        CardLayout cardLayout = (CardLayout) footer.getLayout();
        cardLayout.show(footer, "footerPermission");
        jTextField_searchNV.setVisible(false);
        jTextField_searchTK.setVisible(false);
        jLabel16.setVisible(false);
        jSeparator1.setVisible(false);
    }//GEN-LAST:event_btn_permission1MouseClicked

    private void btn_permissionMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_permissionMouseClicked
        resetLblColorTab(btn_staff);
        resetLblColorTab(btn_users);
        setLblColorTab(btn_permission);
        //switch bettween Jpanels
        CardLayout cardLayout = (CardLayout) footer.getLayout();
        cardLayout.show(footer, "footerPermission");
        jTextField_searchTK.setVisible(false);
        jTextField_searchNV.setVisible(false);
        jLabel16.setVisible(false);
        jSeparator1.setVisible(false);
    }//GEN-LAST:event_btn_permissionMouseClicked

    private void controlMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_controlMouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_controlMouseEntered

    private void controlMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_controlMouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_controlMouseExited

    private void jButton_userMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton_userMouseEntered
        setBgBtn(jButton_user);
    }//GEN-LAST:event_jButton_userMouseEntered

    private void jButton_userMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton_userMouseExited
        resetBgBtn(jButton_user);
    }//GEN-LAST:event_jButton_userMouseExited

    private void lbl_addUserMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbl_addUserMouseClicked
        resetLblColorTab(btn_staff);
        setLblColorTab(btn_users);
        resetLblColorTab(btn_permission);
        jLabel11.setText("Đăng ký tài khoản cho nhân viên mới");
        jLabel_userID.setText("");
        jButton_user.setText("Đăng ký");
        txt_staffID.setText("Nhấp vào để chọn nhân viên");
        CardLayout cardLayout = (CardLayout) control.getLayout();
        cardLayout.show(control, "controlUsers");
        cardLayout = (CardLayout) footer.getLayout();
        cardLayout.show(footer, "footerUsers");
        jTextField_searchTK.setVisible(true);
        jTextField_searchTK.setText("Tìm tài khoản");
        jTextField_searchNV.setVisible(false);
        jLabel16.setVisible(true);
        jSeparator1.setVisible(true);
        body.setVisible(true);
        btn_staff1.setVisible(false);
        btn_users1.setVisible(false);
        btn_permission1.setVisible(false);
        txt_newuser.setText("");
        txt_password.setText("");
        txt_checkpassword.setText("");
    }//GEN-LAST:event_lbl_addUserMouseClicked

    private void cb_phanquyenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cb_phanquyenActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cb_phanquyenActionPerformed

    private void txt_newuserFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txt_newuserFocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_newuserFocusGained

    private void txt_newuserActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_newuserActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_newuserActionPerformed

    private void jTableUserMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTableUserMouseClicked
        if (SwingUtilities.isRightMouseButton(evt)) {
            jTableUser.clearSelection();
            int row = jTableUser.rowAtPoint(evt.getPoint());
            jTableUser.setRowSelectionInterval(row, row);
            jPopupMenuUser.show(jTableUser, evt.getX(), evt.getY());
        }
    }//GEN-LAST:event_jTableUserMouseClicked

    private void jLabel_close2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel_close2MouseClicked
        jDialog1.dispose();
    }//GEN-LAST:event_jLabel_close2MouseClicked

    private void jLabel_close2MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel_close2MouseEntered
        jLabel_close2.setBackground(new Color(139, 137, 217));
    }//GEN-LAST:event_jLabel_close2MouseEntered

    private void jLabel_close2MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel_close2MouseExited
        jLabel_close2.setBackground(new Color(56, 53, 140));
    }//GEN-LAST:event_jLabel_close2MouseExited

    private void jTable_dlgStaffMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable_dlgStaffMouseClicked
        if (evt.getClickCount() == 2 && jTable_dlgStaff.getSelectedRow() != -1) {
            int row = jTable_dlgStaff.getSelectedRow();
            String id = jTable_dlgStaff.getModel().getValueAt(row, 0).toString();// get value at selected row and '0' is the first column (ID)
            txt_staffID.setText(id);
            jDialog1.setVisible(false);
            jDialog1.dispose();
        }
    }//GEN-LAST:event_jTable_dlgStaffMouseClicked

    private void jDialog1MouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jDialog1MouseDragged
        setFrameLocation(evt, jDialog1);
    }//GEN-LAST:event_jDialog1MouseDragged

    private void jDialog1MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jDialog1MousePressed
        getMouseLocation(evt, jDialog1);
    }//GEN-LAST:event_jDialog1MousePressed

    public void getMouseLocation(java.awt.event.MouseEvent evt, JDialog jdialog) {
        xx = evt.getX();
        xy = evt.getY();
    }

    public void setFrameLocation(java.awt.event.MouseEvent evt, JDialog jdialog) {
        int x = evt.getXOnScreen();
        int y = evt.getYOnScreen();
        jdialog.setLocation(x - xx, y - xy);
    }

    private void txt_staffIDMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txt_staffIDMouseClicked
        if (jButton_user.getText().equals("Đăng ký")) {
            /*Load dữ liệu lên table nhân viên gồm các nhân viên chưa có tài khoản*/
            Object[][] dataTable = null;
            String[] headerTableDetail = new String[7];
            headerTableDetail[0] = "ID Nhân Viên";
            headerTableDetail[1] = "Tên Nhân Viên";
            headerTableDetail[2] = "Ngày Sinh";
            headerTableDetail[3] = "Giới Tính";
            headerTableDetail[4] = "Phone";
            headerTableDetail[5] = "Email";
            headerTableDetail[6] = "Lương";
            modelStaffNotUser = new DefaultTableModel(dataTable, headerTableDetail);
            try {
                try (Connection connection = controller.connectDB.connectSQLServer()) {
                    String sql = "select * from tblEmployee where EmpID not in (select EmpID from tblAccount) and EmpStatus is null";
                    try (Statement statement = connection.createStatement(); ResultSet rs = statement.executeQuery(sql)) {
                        while (rs.next()) {
                            modelStaffNotUser.addRow(new Object[]{rs.getString("EmpID"), rs.getString("EmpName"), dateFormatter.format(rs.getDate("DOB")),
                                rs.getString("Gender"), rs.getString("TellNo"), rs.getString("Email"), currencyFormatter.format(rs.getInt("Salary"))});
                        }
                    }
                }
                jTable_dlgStaff.setModel(modelStaffNotUser);
            } catch (SQLException ex) {
                Logger.getLogger(DangNhap.class.getName()).log(Level.SEVERE, null, ex);
                return;
            }
            jTextField_dlgSearch.setText("Tìm nhân viên");
            jDialog1.pack();
            jDialog1.setLocationRelativeTo(null);
            jDialog1.setVisible(true);
        }
    }//GEN-LAST:event_txt_staffIDMouseClicked

    private void jRadioButtonMaleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButtonMaleActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jRadioButtonMaleActionPerformed

    private void jButton_userActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_userActionPerformed
        if (txt_newuser.getText().isEmpty() || txt_password.getPassword().length == 0 || txt_checkpassword.getPassword().length == 0
                || Validation.checkinputInteger(txt_staffID.getText()) == false) {
            new view.Message("Bạn cần nhập đầy đủ thông tin", 2, 4).setVisible(true);
        } else {
            if (!Arrays.equals(txt_password.getPassword(), txt_checkpassword.getPassword())) {
                new view.Message("Mật khẩu nhập sai", 2, 4).setVisible(true);
                return;
            } else {
                int empid = Integer.parseInt(txt_staffID.getText());
                int posid = controller.PositionController.GetPosidbyPosname(cb_phanquyen.getItemAt(cb_phanquyen.getSelectedIndex()));
                if (jButton_user.getText().equals("Đăng ký")) {
                    if (controller.Accountcontroller.checkExistUsername(txt_newuser.getText()) == true) {
                        new view.Message("Tài khoản đã tồn tại", 2, 4).setVisible(true);
                        return;
                    }
                    acc = new Account(0, txt_newuser.getText(), new String(txt_password.getPassword()), empid, posid);
                    Accountcontroller.AddAcount(acc);
                } else {
                    if (controller.Accountcontroller.checkExistUsername(txt_newuser.getText()) == true) {
                        String userNameFromDB = Accountcontroller.getAccByID(Integer.parseInt(jLabel_userID.getText().substring(15))).getUsername();
                        if (txt_newuser.getText().equals(userNameFromDB)) {
                            acc = new Account(Integer.parseInt(jLabel_userID.getText().substring(15)), txt_newuser.getText(), new String(txt_password.getPassword()), empid, posid);
                            Accountcontroller.EditAccount(acc);
                        } else {
                            new view.Message("Tài khoản đã tồn tại", 2, 4).setVisible(true);
                            return;
                        }
                    } else {
                        acc = new Account(Integer.parseInt(jLabel_userID.getText().substring(15)), txt_newuser.getText(), new String(txt_password.getPassword()), empid, posid);
                        Accountcontroller.EditAccount(acc);
                    }
                }
            }
            createTableAccount();
        }
    }//GEN-LAST:event_jButton_userActionPerformed

    private void btn_ADDorEDITMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_ADDorEDITMouseEntered
        btn_ADDorEDIT.setBackground(new Color(139, 137, 217));
    }//GEN-LAST:event_btn_ADDorEDITMouseEntered

    private void btn_ADDorEDITMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_ADDorEDITMouseExited
        btn_ADDorEDIT.setBackground(new Color(56, 53, 140));
    }//GEN-LAST:event_btn_ADDorEDITMouseExited

    private void txt_passwordFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txt_passwordFocusGained
        txt_password.setText("");
    }//GEN-LAST:event_txt_passwordFocusGained

    private void txt_checkpasswordFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txt_checkpasswordFocusGained
        txt_checkpassword.setText("");
    }//GEN-LAST:event_txt_checkpasswordFocusGained

    private void txt_PhoneActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_PhoneActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_PhoneActionPerformed

    private void txt_EmailActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_EmailActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_EmailActionPerformed

    private void jTextField_searchNVKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField_searchNVKeyReleased
        DefaultTableModel model = createModelStaff(jTableNV);
        for (int i = 0; i < modelStaff.getRowCount(); i++) {
            for (int j = 0; j < modelStaff.getColumnCount(); j++) {
                if (modelStaff.getValueAt(i, j).toString().toUpperCase().contains(jTextField_searchNV.getText().toUpperCase())) {
                    Object[] row = new Object[modelStaff.getColumnCount()];
                    for (int k = 0; k < modelStaff.getColumnCount(); k++) {
                        row[k] = modelStaff.getValueAt(i, k);
                    }
                    model.addRow(row);
                    break;
                }
            }
        }
        jTableNV.setModel(model);
    }//GEN-LAST:event_jTextField_searchNVKeyReleased

    private void jTextField_searchNVMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTextField_searchNVMouseClicked
        jTextField_searchNV.setText("");
    }//GEN-LAST:event_jTextField_searchNVMouseClicked

    private void jTextField_searchTKMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTextField_searchTKMouseClicked
        jTextField_searchTK.setText("");
    }//GEN-LAST:event_jTextField_searchTKMouseClicked

    private void jTextField_searchTKKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField_searchTKKeyReleased
        DefaultTableModel model = createModelUser();
        for (int i = 0; i < modelUser.getRowCount(); i++) {
            for (int j = 0; j < modelUser.getColumnCount(); j++) {
                if (modelUser.getValueAt(i, j).toString().toUpperCase().contains(jTextField_searchTK.getText().toUpperCase())) {
                    Object[] row = new Object[modelUser.getColumnCount()];
                    for (int k = 0; k < modelUser.getColumnCount(); k++) {
                        row[k] = modelUser.getValueAt(i, k);
                    }
                    model.addRow(row);
                    break;
                }
            }
        }
        jTableUser.setModel(model);
    }//GEN-LAST:event_jTextField_searchTKKeyReleased

    private void jTextField_dlgSearchKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField_dlgSearchKeyReleased
        DefaultTableModel model = createModelStaff(jTable_dlgStaff);
        for (int i = 0; i < modelStaffNotUser.getRowCount(); i++) {
            for (int j = 0; j < modelStaffNotUser.getColumnCount(); j++) {
                if (modelStaffNotUser.getValueAt(i, j).toString().toUpperCase().contains(jTextField_dlgSearch.getText().toUpperCase())) {
                    Object[] row = new Object[modelStaffNotUser.getColumnCount()];
                    for (int k = 0; k < modelStaffNotUser.getColumnCount(); k++) {
                        row[k] = modelStaffNotUser.getValueAt(i, k);
                    }
                    model.addRow(row);
                    break;
                }
            }
        }
        jTable_dlgStaff.setModel(model);
    }//GEN-LAST:event_jTextField_dlgSearchKeyReleased

    private void jTextField_dlgSearchMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTextField_dlgSearchMouseClicked
        jTextField_dlgSearch.setText("");
    }//GEN-LAST:event_jTextField_dlgSearchMouseClicked

    private void lbl_addStaffMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbl_addStaffMouseEntered
        setLblColor(lbl_addStaff);
    }//GEN-LAST:event_lbl_addStaffMouseEntered

    private void lbl_addStaffMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbl_addStaffMouseExited
        resetLblColor(lbl_addStaff);
    }//GEN-LAST:event_lbl_addStaffMouseExited

    private void lbl_addUserMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbl_addUserMouseEntered
        setLblColor(lbl_addUser);
    }//GEN-LAST:event_lbl_addUserMouseEntered

    private void lbl_addUserMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbl_addUserMouseExited
        resetLblColor(lbl_addUser);
    }//GEN-LAST:event_lbl_addUserMouseExited

    private void lbl_outMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbl_outMouseEntered
        setLblColor(lbl_out);
    }//GEN-LAST:event_lbl_outMouseEntered

    private void lbl_outMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbl_outMouseExited
        resetLblColor(lbl_out);
    }//GEN-LAST:event_lbl_outMouseExited

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        
    }//GEN-LAST:event_jButton1ActionPerformed

    public void setLblColor(JLabel lbl) {
        lbl.setForeground(new Color(139, 137, 217));
    }

    public void resetLblColor(JLabel lbl) {
        lbl.setForeground(new Color(166, 166, 166));
    }

    public void setLblColorTab(JLabel lbl) {
        lbl.setBackground(new Color(139, 137, 217));
    }

    public void resetLblColorTab(JLabel lbl) {
        lbl.setBackground(new Color(56, 53, 140));
    }

    public void setBgBtn(JButton btn) {
        btn.setBackground(new Color(139, 137, 217));
    }

    public void resetBgBtn(JButton btn) {
        btn.setBackground(new Color(56, 53, 140));
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Windows look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Windows (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Windows".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(QLNhanVien.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new QLNhanVien().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel body;
    private javax.swing.JPanel body_staff;
    private javax.swing.JPanel body_users;
    private javax.swing.JButton btn_ADDorEDIT;
    private javax.swing.JLabel btn_permission;
    private javax.swing.JLabel btn_permission1;
    private javax.swing.JLabel btn_staff;
    private javax.swing.JLabel btn_staff1;
    private javax.swing.JLabel btn_users;
    private javax.swing.JLabel btn_users1;
    private javax.swing.ButtonGroup buttonGroup1;
    public static javax.swing.JComboBox<String> cb_phanquyen;
    private javax.swing.JPanel control;
    private datechooser.beans.DateChooserCombo dateChooserCombo1;
    private javax.swing.JPanel footer;
    private javax.swing.JPanel header;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton_user;
    private javax.swing.JDialog jDialog1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLabel jLabel_close;
    private javax.swing.JLabel jLabel_close2;
    private javax.swing.JLabel jLabel_mini;
    private javax.swing.JLabel jLabel_staffID;
    private javax.swing.JLabel jLabel_userID;
    private javax.swing.JMenuItem jMenuItemDeleteNV;
    private javax.swing.JMenuItem jMenuItemDeleteUser;
    private javax.swing.JMenuItem jMenuItemEditNV;
    private javax.swing.JMenuItem jMenuItemEditUser;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanelStaff;
    private javax.swing.JPanel jPanel_clomin;
    private javax.swing.JPopupMenu jPopupMenuNV;
    private javax.swing.JPopupMenu jPopupMenuUser;
    private javax.swing.JRadioButton jRadioButtonFemale;
    private javax.swing.JRadioButton jRadioButtonMale;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JSeparator jSeparator6;
    private javax.swing.JTable jTableNV;
    private javax.swing.JTable jTablePos;
    private javax.swing.JTable jTableUser;
    private javax.swing.JTable jTable_dlgStaff;
    private javax.swing.JTextField jTextField_dlgSearch;
    private javax.swing.JTextField jTextField_searchNV;
    private javax.swing.JTextField jTextField_searchTK;
    private javax.swing.JLabel lbl_addStaff;
    private javax.swing.JLabel lbl_addUser;
    private javax.swing.JLabel lbl_out;
    private javax.swing.JPanel sidebar;
    private javax.swing.JPanel staff;
    private javax.swing.JPanel tabFooter;
    private javax.swing.JPanel tblPanel;
    private javax.swing.JTextField txt_Address;
    private javax.swing.JTextField txt_Email;
    private javax.swing.JTextField txt_Luong;
    private javax.swing.JTextField txt_Name;
    private javax.swing.JTextField txt_Phone;
    private javax.swing.JPasswordField txt_checkpassword;
    private javax.swing.JTextField txt_newuser;
    private javax.swing.JPasswordField txt_password;
    private javax.swing.JTextField txt_staffID;
    private javax.swing.JPanel userPermission;
    private javax.swing.JPanel users;
    // End of variables declaration//GEN-END:variables
}
