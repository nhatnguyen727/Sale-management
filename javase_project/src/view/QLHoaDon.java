/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import controller.Validation;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Frame;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import model.Bill;
import model.BillDetails;
import model.Customer;
import model.Product;

/**
 *
 * @author proxc
 */
public class QLHoaDon extends javax.swing.JFrame {

    static DecimalFormat currencyFormatter = new DecimalFormat("###,###,###");
    static DateFormat dateFormatter = new SimpleDateFormat("dd/MM/yyyy");

    /**
     * Creates new form QLHoaDon
     */
    public QLHoaDon() {
        /* Set the Windows look and feel */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Windows".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(QLHoaDon.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }

        initComponents();
        loadtableBill();
        loadtableBillDetails();
        loadtableCustomer();
        CardLayout cardLayout = (CardLayout) jPanel_search.getLayout();
        cardLayout.show(jPanel_search, "searchBill");

        tb_bill.getTableHeader().setForeground(new Color(73, 61, 128));
        tb_bill.getTableHeader().setBackground(new Color(147, 122, 255));
        tb_bill.getTableHeader().setFont(new Font("Dialog", Font.BOLD, 12));
        tb_billdetail.getTableHeader().setForeground(new Color(73, 61, 128));
        tb_billdetail.getTableHeader().setBackground(new Color(147, 122, 255));
        tb_billdetail.getTableHeader().setFont(new Font("Dialog", Font.BOLD, 12));
        tb_customer.getTableHeader().setForeground(new Color(73, 61, 128));
        tb_customer.getTableHeader().setBackground(new Color(147, 122, 255));
        tb_customer.getTableHeader().setFont(new Font("Dialog", Font.BOLD, 12));
        tb_customer1.getTableHeader().setForeground(new Color(73, 61, 128));
        tb_customer1.getTableHeader().setBackground(new Color(147, 122, 255));
        tb_customer1.getTableHeader().setFont(new Font("Dialog", Font.BOLD, 12));
        tb_product.getTableHeader().setForeground(new Color(73, 61, 128));
        tb_product.getTableHeader().setBackground(new Color(147, 122, 255));
        tb_product.getTableHeader().setFont(new Font("Dialog", Font.BOLD, 12));
    }
    static Vector titlesKhachhang = new Vector();
    static Vector titlesProduct = new Vector();
    static Vector titlesHoadon = new Vector();
    static Vector titleschitietHoadon = new Vector();

    static TableModel modelKhachhang;
    static TableModel modelProduct;
    static TableModel modelHoadon;
    static TableModel modelchitietHoadon;

    public static void loadtableCustomer() {
        titlesKhachhang = new Vector();
        titlesKhachhang.add("ID Khách hàng");
        titlesKhachhang.add("Tên khách hàng");
        titlesKhachhang.add("Địa chỉ");
        titlesKhachhang.add("Số điện thoại");
        titlesKhachhang.add("Email");
        titlesKhachhang.add("Giới tính");
        Vector bangdulieu = new Vector();
        try {
            List<Customer> list = controller.CustomerController.GetListCustomer();
            for (Customer customer : list) {
                Vector dongdulieu = new Vector();
                dongdulieu.add(customer.getCustID());
                dongdulieu.add(customer.getCustName());
                dongdulieu.add(customer.getCustAddress());
                dongdulieu.add(customer.getTellNo());
                dongdulieu.add(customer.getEmail());
                dongdulieu.add(customer.getGender());
                bangdulieu.add(dongdulieu);
            }
        } catch (SQLException ex) {
            Logger.getLogger(QLHoaDon.class.getName()).log(Level.SEVERE, null, ex);
        }
        DefaultTableModel defaultTableModel = new DefaultTableModel(bangdulieu, titlesKhachhang) {
            @Override
            public boolean isCellEditable(int row, int col) {
                return false;
            }
        };
        tb_customer.setModel(defaultTableModel);
        modelKhachhang = tb_customer.getModel();
    }

    public static void loadtableProduct() {
        titlesProduct = new Vector();
        titlesProduct.add("ID Sản phẩm");
        titlesProduct.add("Tên Sản phẩm");
        titlesProduct.add("Giá (VNĐ)");
        titlesProduct.add("Số lượng");
        Vector bangdulieu = new Vector();
        List<Product> list = controller.ProductController.GetListProduct();
        for (Product product : list) {
            Vector dongdulieu = new Vector();
            dongdulieu.add(product.getProductID());
            dongdulieu.add(product.getProductName());
            dongdulieu.add(currencyFormatter.format(product.getUnitPrice()));
            dongdulieu.add(product.getQtyonHand());
            bangdulieu.add(dongdulieu);
        }
        DefaultTableModel defaultTableModel = new DefaultTableModel(bangdulieu, titlesProduct) {
            @Override
            public boolean isCellEditable(int row, int col) {
                return false;
            }
        };
        tb_product.setModel(defaultTableModel);
        modelProduct = tb_product.getModel();
    }

    public static void loadtableBill() {
        titlesHoadon = new Vector();
        titlesHoadon.add("ID Hóa đơn");
        titlesHoadon.add("ID Nhân viên");
        titlesHoadon.add("Tổng tiền");
        titlesHoadon.add("Ngày lập");
        titlesHoadon.add("Khách hàng");
        Vector bangdulieu = new Vector();
        try {
            List<Bill> list = controller.BillController.GetListBill();
            for (Bill bill : list) {
                Vector dongdulieu = new Vector();
                dongdulieu.add(bill.getBillID());
                dongdulieu.add(bill.getEmpID() + " (" + controller.EmployeeController.getEmpNamebyEmpID(bill.getEmpID()) + ")");
                dongdulieu.add(currencyFormatter.format(bill.getTotalMoney()));
                dongdulieu.add(dateFormatter.format(bill.getDates()));
                dongdulieu.add(controller.CustomerController.getCusNamebyCusid(bill.getCustID()));
                bangdulieu.add(dongdulieu);
            }
        } catch (SQLException ex) {
            Logger.getLogger(QLHoaDon.class.getName()).log(Level.SEVERE, null, ex);
        }
        DefaultTableModel defaultTableModel = new DefaultTableModel(bangdulieu, titlesHoadon) {
            @Override
            public boolean isCellEditable(int row, int col) {
                return false;
            }
        };
        tb_bill.setModel(defaultTableModel);
        modelHoadon = tb_bill.getModel();
    }

    public static void loadtableBillDetails() {
        titleschitietHoadon = new Vector();
        titleschitietHoadon.add("ID Hóa đơn");
        titleschitietHoadon.add("ID Sản phẩm");
        titleschitietHoadon.add("Số lượng");
        Vector bangdulieu = new Vector();
        try {
            List<BillDetails> list = controller.BillDetailsController.GetListBillDetails();
            for (BillDetails billDetails : list) {
                Vector dongdulieu = new Vector();
                dongdulieu.add(billDetails.getBillID());
                dongdulieu.add(billDetails.getProductID());
                dongdulieu.add(billDetails.getQtyonHand());
                bangdulieu.add(dongdulieu);
            }
        } catch (SQLException ex) {
            Logger.getLogger(QLHoaDon.class.getName()).log(Level.SEVERE, null, ex);
        }
        DefaultTableModel defaultTableModel = new DefaultTableModel(bangdulieu, titleschitietHoadon) {
            @Override
            public boolean isCellEditable(int row, int col) {
                return false;
            }
        };
        tb_billdetail.setModel(defaultTableModel);
        modelchitietHoadon = tb_billdetail.getModel();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPopupMenuBill = new javax.swing.JPopupMenu();
        jMenuItemDelete = new javax.swing.JMenuItem();
        jMenuItemAddDetail = new javax.swing.JMenuItem();
        jPopupMenuBillDetail = new javax.swing.JPopupMenu();
        jMenuItemEditDetail = new javax.swing.JMenuItem();
        jMenuItemDeleteDetail = new javax.swing.JMenuItem();
        jPopupMenuCustomer = new javax.swing.JPopupMenu();
        jMenuItemEditCustomer = new javax.swing.JMenuItem();
        jDialog1 = new javax.swing.JDialog();
        jPanel2 = new javax.swing.JPanel();
        jButton4 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel_close1 = new javax.swing.JLabel();
        dlg_customer = new javax.swing.JDialog();
        jPanel3 = new javax.swing.JPanel();
        jLabel_close2 = new javax.swing.JLabel();
        customer1 = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        tb_customer1 = new javax.swing.JTable();
        jLabel5 = new javax.swing.JLabel();
        jSeparator5 = new javax.swing.JSeparator();
        txt_search_customer1 = new javax.swing.JTextField();
        jLabel28 = new javax.swing.JLabel();
        dlg_product = new javax.swing.JDialog();
        jPanel4 = new javax.swing.JPanel();
        jLabel_close3 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jSeparator6 = new javax.swing.JSeparator();
        jTextField_product = new javax.swing.JTextField();
        jLabel29 = new javax.swing.JLabel();
        product = new javax.swing.JPanel();
        jScrollPane6 = new javax.swing.JScrollPane();
        tb_product = new javax.swing.JTable();
        sidebar = new javax.swing.JPanel();
        lbl_addBill = new javax.swing.JLabel();
        lbl_exit = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        lbl_addCustomer = new javax.swing.JLabel();
        header = new javax.swing.JPanel();
        jPanel_clomin = new javax.swing.JPanel();
        jLabel_mini = new javax.swing.JLabel();
        jLabel_close = new javax.swing.JLabel();
        btn_bill1 = new javax.swing.JLabel();
        btn_detail1 = new javax.swing.JLabel();
        btn_customer1 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        jLabelImgFind = new javax.swing.JLabel();
        jPanel_search = new javax.swing.JPanel();
        txt_search_billdetail = new javax.swing.JTextField();
        txt_search_customer = new javax.swing.JTextField();
        txt_searchbill = new javax.swing.JTextField();
        body = new javax.swing.JPanel();
        body_bill = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        btn_bill = new javax.swing.JLabel();
        btn_detail = new javax.swing.JLabel();
        lb_idnv_login = new javax.swing.JLabel();
        lb_khachhang = new javax.swing.JLabel();
        txt_addbill_custid = new javax.swing.JTextField();
        jSeparator3 = new javax.swing.JSeparator();
        btn_addbill = new javax.swing.JButton();
        btn_customer = new javax.swing.JLabel();
        body_billDetail = new javax.swing.JPanel();
        jLabel12 = new javax.swing.JLabel();
        btn_bill3 = new javax.swing.JLabel();
        btn_detail3 = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        txt_addbilldetail_soluong = new javax.swing.JTextField();
        jSeparator7 = new javax.swing.JSeparator();
        btn_addorupdatebilldetail = new javax.swing.JButton();
        lb_addbilldetail_billid = new javax.swing.JLabel();
        jLabel32 = new javax.swing.JLabel();
        btn_customer3 = new javax.swing.JLabel();
        txt_addbilldetail_productid = new javax.swing.JTextField();
        body_customer = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        txt_tellno = new javax.swing.JTextField();
        jLabel24 = new javax.swing.JLabel();
        txt_email = new javax.swing.JTextField();
        jLabel27 = new javax.swing.JLabel();
        txt_address = new javax.swing.JTextField();
        rbtn_nam = new javax.swing.JRadioButton();
        rbtn_nu = new javax.swing.JRadioButton();
        jSeparator4 = new javax.swing.JSeparator();
        jButton3 = new javax.swing.JButton();
        txt_custname = new javax.swing.JTextField();
        jLabel_custID = new javax.swing.JLabel();
        btn_bill2 = new javax.swing.JLabel();
        btn_detail2 = new javax.swing.JLabel();
        btn_customer2 = new javax.swing.JLabel();
        footer = new javax.swing.JPanel();
        bill = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tb_bill = new javax.swing.JTable();
        billDetail = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tb_billdetail = new javax.swing.JTable();
        customer = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tb_customer = new javax.swing.JTable();

        jMenuItemDelete.setIcon(new javax.swing.ImageIcon(getClass().getResource("/view/images/icons8-delete-18.png"))); // NOI18N
        jMenuItemDelete.setText("Xóa hóa đơn");
        jMenuItemDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemDeleteActionPerformed(evt);
            }
        });
        jPopupMenuBill.add(jMenuItemDelete);

        jMenuItemAddDetail.setIcon(new javax.swing.ImageIcon(getClass().getResource("/view/images/plus_18px.png"))); // NOI18N
        jMenuItemAddDetail.setText("Thêm chi tiết hóa đơn");
        jMenuItemAddDetail.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemAddDetailActionPerformed(evt);
            }
        });
        jPopupMenuBill.add(jMenuItemAddDetail);

        jMenuItemEditDetail.setIcon(new javax.swing.ImageIcon(getClass().getResource("/view/images/icons8-edit-18.png"))); // NOI18N
        jMenuItemEditDetail.setText("Sửa");
        jMenuItemEditDetail.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemEditDetailActionPerformed(evt);
            }
        });
        jPopupMenuBillDetail.add(jMenuItemEditDetail);

        jMenuItemDeleteDetail.setIcon(new javax.swing.ImageIcon(getClass().getResource("/view/images/icons8-delete-18.png"))); // NOI18N
        jMenuItemDeleteDetail.setText("Xóa");
        jMenuItemDeleteDetail.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemDeleteDetailActionPerformed(evt);
            }
        });
        jPopupMenuBillDetail.add(jMenuItemDeleteDetail);

        jMenuItemEditCustomer.setIcon(new javax.swing.ImageIcon(getClass().getResource("/view/images/icons8-edit-18.png"))); // NOI18N
        jMenuItemEditCustomer.setText("Sửa");
        jMenuItemEditCustomer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemEditCustomerActionPerformed(evt);
            }
        });
        jPopupMenuCustomer.add(jMenuItemEditCustomer);

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

        jPanel2.setBackground(new java.awt.Color(73, 61, 128));
        jPanel2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(147, 122, 255)));
        jPanel2.setPreferredSize(new java.awt.Dimension(451, 126));
        jPanel2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel2MouseClicked(evt);
            }
        });

        jButton4.setBackground(new java.awt.Color(73, 61, 128));
        jButton4.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jButton4.setForeground(new java.awt.Color(255, 255, 255));
        jButton4.setText("Lấy ID");
        jButton4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(147, 122, 255)));
        jButton4.setContentAreaFilled(false);
        jButton4.setFocusPainted(false);
        jButton4.setOpaque(true);
        jButton4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton4MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jButton4MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jButton4MouseExited(evt);
            }
        });

        jButton5.setBackground(new java.awt.Color(73, 61, 128));
        jButton5.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jButton5.setForeground(new java.awt.Color(255, 255, 255));
        jButton5.setText("Thêm khách hàng");
        jButton5.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(147, 122, 255)));
        jButton5.setContentAreaFilled(false);
        jButton5.setFocusPainted(false);
        jButton5.setOpaque(true);
        jButton5.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton5MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jButton5MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jButton5MouseExited(evt);
            }
        });

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/view/images/icons8-questions-25.png"))); // NOI18N

        jLabel4.setFont(new java.awt.Font("Segoe UI Light", 0, 14)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4.setText("Bạn muốn lấy ID từ khách hàng có sẵn hay thêm khách hàng mới ?");

        jLabel_close1.setBackground(new java.awt.Color(73, 61, 128));
        jLabel_close1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel_close1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel_close1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel_close1.setText("X");
        jLabel_close1.setOpaque(true);
        jLabel_close1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel_close1MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jLabel_close1MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jLabel_close1MouseExited(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(90, 90, 90)
                .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 402, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jLabel_close1, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jLabel_close1, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4)
                    .addComponent(jLabel1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18))
        );

        javax.swing.GroupLayout jDialog1Layout = new javax.swing.GroupLayout(jDialog1.getContentPane());
        jDialog1.getContentPane().setLayout(jDialog1Layout);
        jDialog1Layout.setHorizontalGroup(
            jDialog1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, 460, Short.MAX_VALUE)
        );
        jDialog1Layout.setVerticalGroup(
            jDialog1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        dlg_customer.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        dlg_customer.setModal(true);
        dlg_customer.setUndecorated(true);
        dlg_customer.setResizable(false);
        dlg_customer.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                dlg_customerMouseDragged(evt);
            }
        });
        dlg_customer.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                dlg_customerMousePressed(evt);
            }
        });

        jPanel3.setBackground(new java.awt.Color(73, 61, 128));
        jPanel3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(147, 122, 255)));
        jPanel3.setPreferredSize(new java.awt.Dimension(451, 126));

        jLabel_close2.setBackground(new java.awt.Color(73, 61, 128));
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

        customer1.setBackground(java.awt.SystemColor.controlShadow);
        customer1.setLayout(new javax.swing.BoxLayout(customer1, javax.swing.BoxLayout.LINE_AXIS));

        jScrollPane4.setBackground(java.awt.SystemColor.controlShadow);
        jScrollPane4.setBorder(null);

        tb_customer1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(147, 122, 255)));
        tb_customer1.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        tb_customer1.setForeground(new java.awt.Color(51, 51, 51));
        tb_customer1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID Khách Hàng", "Tên Khách Hàng", "Phone", "Email", "Ngày Sinh", "Giới Tính"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        tb_customer1.setGridColor(new java.awt.Color(147, 122, 255));
        tb_customer1.setRowHeight(20);
        tb_customer1.setSelectionBackground(new java.awt.Color(147, 122, 255));
        tb_customer1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tb_customer1MouseClicked(evt);
            }
        });
        jScrollPane4.setViewportView(tb_customer1);

        customer1.add(jScrollPane4);

        jLabel5.setFont(new java.awt.Font("Segoe UI Light", 0, 18)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("Danh sách khách hàng (double click vào bảng để lấy ID khách hàng) ");

        jSeparator5.setBackground(java.awt.SystemColor.inactiveCaption);
        jSeparator5.setForeground(java.awt.SystemColor.inactiveCaption);

        txt_search_customer1.setBackground(new java.awt.Color(73, 61, 128));
        txt_search_customer1.setFont(new java.awt.Font("Segoe UI Light", 2, 14)); // NOI18N
        txt_search_customer1.setForeground(new java.awt.Color(255, 255, 255));
        txt_search_customer1.setBorder(null);
        txt_search_customer1.setCaretColor(new java.awt.Color(255, 255, 255));
        txt_search_customer1.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        txt_search_customer1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txt_search_customer1MouseClicked(evt);
            }
        });
        txt_search_customer1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txt_search_customer1KeyReleased(evt);
            }
        });

        jLabel28.setIcon(new javax.swing.ImageIcon(getClass().getResource("/view/images/search_20px.png"))); // NOI18N

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(customer1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addComponent(jLabel28)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jSeparator5)
                            .addComponent(txt_search_customer1, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 142, Short.MAX_VALUE)
                        .addComponent(jLabel_close2, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel_close2, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 12, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(8, 8, 8)
                        .addComponent(jLabel28))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(txt_search_customer1, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(1, 1, 1)
                        .addComponent(jSeparator5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(customer1, javax.swing.GroupLayout.PREFERRED_SIZE, 421, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        javax.swing.GroupLayout dlg_customerLayout = new javax.swing.GroupLayout(dlg_customer.getContentPane());
        dlg_customer.getContentPane().setLayout(dlg_customerLayout);
        dlg_customerLayout.setHorizontalGroup(
            dlg_customerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(dlg_customerLayout.createSequentialGroup()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 700, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        dlg_customerLayout.setVerticalGroup(
            dlg_customerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(dlg_customerLayout.createSequentialGroup()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 500, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        dlg_product.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        dlg_product.setModal(true);
        dlg_product.setUndecorated(true);
        dlg_product.setResizable(false);
        dlg_product.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                dlg_productMouseDragged(evt);
            }
        });
        dlg_product.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                dlg_productMousePressed(evt);
            }
        });

        jPanel4.setBackground(new java.awt.Color(73, 61, 128));
        jPanel4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(147, 122, 255)));
        jPanel4.setPreferredSize(new java.awt.Dimension(451, 126));

        jLabel_close3.setBackground(new java.awt.Color(73, 61, 128));
        jLabel_close3.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel_close3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel_close3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel_close3.setText("X");
        jLabel_close3.setOpaque(true);
        jLabel_close3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel_close3MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jLabel_close3MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jLabel_close3MouseExited(evt);
            }
        });

        jLabel6.setFont(new java.awt.Font("Segoe UI Light", 0, 18)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("Danh sách sản phẩm (double click vào bảng để lấy ID sản phẩm) ");

        jSeparator6.setBackground(java.awt.SystemColor.inactiveCaption);
        jSeparator6.setForeground(java.awt.SystemColor.inactiveCaption);

        jTextField_product.setBackground(new java.awt.Color(73, 61, 128));
        jTextField_product.setFont(new java.awt.Font("Segoe UI Light", 2, 14)); // NOI18N
        jTextField_product.setForeground(new java.awt.Color(255, 255, 255));
        jTextField_product.setBorder(null);
        jTextField_product.setCaretColor(new java.awt.Color(255, 255, 255));
        jTextField_product.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        jTextField_product.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTextField_productFocusGained(evt);
            }
        });
        jTextField_product.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextField_productKeyReleased(evt);
            }
        });

        jLabel29.setIcon(new javax.swing.ImageIcon(getClass().getResource("/view/images/search_20px.png"))); // NOI18N

        product.setBackground(java.awt.SystemColor.controlShadow);
        product.setLayout(new javax.swing.BoxLayout(product, javax.swing.BoxLayout.LINE_AXIS));

        jScrollPane6.setBackground(java.awt.SystemColor.controlShadow);
        jScrollPane6.setBorder(null);

        tb_product.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(147, 122, 255)));
        tb_product.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        tb_product.setForeground(new java.awt.Color(51, 51, 51));
        tb_product.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Mã Sản Phẩm (ID)", "Tên Sản Phẩm", "Số Lượng", "Giá"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.Integer.class, java.lang.Integer.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        tb_product.setGridColor(new java.awt.Color(147, 122, 255));
        tb_product.setRowHeight(20);
        tb_product.setSelectionBackground(new java.awt.Color(147, 122, 255));
        tb_product.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tb_productMouseClicked(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                tb_productMousePressed(evt);
            }
        });
        jScrollPane6.setViewportView(tb_product);

        product.add(jScrollPane6);

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 170, Short.MAX_VALUE)
                        .addComponent(jLabel_close3, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel29)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jSeparator6)
                            .addComponent(jTextField_product, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE))))
            .addComponent(product, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel_close3, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6))
                .addGap(12, 12, 12)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(8, 8, 8)
                        .addComponent(jLabel29))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jTextField_product, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(1, 1, 1)
                        .addComponent(jSeparator6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(product, javax.swing.GroupLayout.PREFERRED_SIZE, 421, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout dlg_productLayout = new javax.swing.GroupLayout(dlg_product.getContentPane());
        dlg_product.getContentPane().setLayout(dlg_productLayout);
        dlg_productLayout.setHorizontalGroup(
            dlg_productLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(dlg_productLayout.createSequentialGroup()
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, 700, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        dlg_productLayout.setVerticalGroup(
            dlg_productLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(dlg_productLayout.createSequentialGroup()
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, 500, javax.swing.GroupLayout.PREFERRED_SIZE)
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
        sidebar.setPreferredSize(new java.awt.Dimension(245, 602));

        lbl_addBill.setBackground(new java.awt.Color(48, 201, 235));
        lbl_addBill.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lbl_addBill.setForeground(new java.awt.Color(166, 166, 166));
        lbl_addBill.setIcon(new javax.swing.ImageIcon(getClass().getResource("/view/images/plus_18px.png"))); // NOI18N
        lbl_addBill.setText("Thêm hóa đơn mới");
        lbl_addBill.setToolTipText("");
        lbl_addBill.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lbl_addBillMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                lbl_addBillMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                lbl_addBillMouseExited(evt);
            }
        });

        lbl_exit.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lbl_exit.setForeground(new java.awt.Color(166, 166, 166));
        lbl_exit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/view/images/icons8-left-18.png"))); // NOI18N
        lbl_exit.setText("Thoát");
        lbl_exit.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lbl_exitMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                lbl_exitMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                lbl_exitMouseExited(evt);
            }
        });

        jPanel1.setBackground(new java.awt.Color(22, 27, 33));

        jLabel2.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(147, 122, 255));
        jLabel2.setText("Sales Management ©");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(60, 60, 60)
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

        lbl_addCustomer.setBackground(new java.awt.Color(48, 201, 235));
        lbl_addCustomer.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lbl_addCustomer.setForeground(new java.awt.Color(166, 166, 166));
        lbl_addCustomer.setIcon(new javax.swing.ImageIcon(getClass().getResource("/view/images/plus_18px.png"))); // NOI18N
        lbl_addCustomer.setText("Thêm khách hàng mới");
        lbl_addCustomer.setToolTipText("");
        lbl_addCustomer.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lbl_addCustomerMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                lbl_addCustomerMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                lbl_addCustomerMouseExited(evt);
            }
        });

        javax.swing.GroupLayout sidebarLayout = new javax.swing.GroupLayout(sidebar);
        sidebar.setLayout(sidebarLayout);
        sidebarLayout.setHorizontalGroup(
            sidebarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, sidebarLayout.createSequentialGroup()
                .addContainerGap(40, Short.MAX_VALUE)
                .addGroup(sidebarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, sidebarLayout.createSequentialGroup()
                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(92, 92, 92))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, sidebarLayout.createSequentialGroup()
                        .addGroup(sidebarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(sidebarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(lbl_exit, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(lbl_addCustomer, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(lbl_addBill, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(37, 37, 37))))
        );
        sidebarLayout.setVerticalGroup(
            sidebarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(sidebarLayout.createSequentialGroup()
                .addGap(56, 56, 56)
                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(49, 49, 49)
                .addComponent(lbl_addBill, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lbl_addCustomer, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lbl_exit, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        header.setBackground(new java.awt.Color(34, 41, 50));
        header.setPreferredSize(new java.awt.Dimension(766, 65));

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

        btn_bill1.setBackground(new java.awt.Color(147, 122, 255));
        btn_bill1.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btn_bill1.setForeground(new java.awt.Color(255, 255, 255));
        btn_bill1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        btn_bill1.setText("Hóa Đơn");
        btn_bill1.setOpaque(true);
        btn_bill1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btn_bill1MouseClicked(evt);
            }
        });

        btn_detail1.setBackground(new java.awt.Color(34, 41, 50));
        btn_detail1.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btn_detail1.setForeground(new java.awt.Color(255, 255, 255));
        btn_detail1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        btn_detail1.setText("Chi Tiết Hóa Đơn");
        btn_detail1.setOpaque(true);
        btn_detail1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btn_detail1MouseClicked(evt);
            }
        });

        btn_customer1.setBackground(new java.awt.Color(34, 41, 50));
        btn_customer1.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btn_customer1.setForeground(new java.awt.Color(255, 255, 255));
        btn_customer1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        btn_customer1.setText("Khách hàng");
        btn_customer1.setOpaque(true);
        btn_customer1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btn_customer1MouseClicked(evt);
            }
        });

        jSeparator1.setBackground(java.awt.SystemColor.inactiveCaption);
        jSeparator1.setForeground(java.awt.SystemColor.inactiveCaption);

        jLabelImgFind.setIcon(new javax.swing.ImageIcon(getClass().getResource("/view/images/search_20px.png"))); // NOI18N

        jPanel_search.setBackground(new java.awt.Color(34, 41, 50));
        jPanel_search.setLayout(new java.awt.CardLayout());

        txt_search_billdetail.setBackground(new java.awt.Color(34, 41, 50));
        txt_search_billdetail.setFont(new java.awt.Font("Segoe UI Light", 2, 14)); // NOI18N
        txt_search_billdetail.setForeground(new java.awt.Color(255, 255, 255));
        txt_search_billdetail.setText("Tìm chi tiết hóa đơn");
        txt_search_billdetail.setBorder(null);
        txt_search_billdetail.setCaretColor(new java.awt.Color(255, 255, 255));
        txt_search_billdetail.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        txt_search_billdetail.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txt_search_billdetailMouseClicked(evt);
            }
        });
        txt_search_billdetail.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txt_search_billdetailKeyReleased(evt);
            }
        });
        jPanel_search.add(txt_search_billdetail, "searchBillDetail");

        txt_search_customer.setBackground(new java.awt.Color(34, 41, 50));
        txt_search_customer.setFont(new java.awt.Font("Segoe UI Light", 2, 14)); // NOI18N
        txt_search_customer.setForeground(new java.awt.Color(255, 255, 255));
        txt_search_customer.setText("Tìm khách hàng");
        txt_search_customer.setBorder(null);
        txt_search_customer.setCaretColor(new java.awt.Color(255, 255, 255));
        txt_search_customer.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        txt_search_customer.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txt_search_customerMouseClicked(evt);
            }
        });
        txt_search_customer.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txt_search_customerKeyReleased(evt);
            }
        });
        jPanel_search.add(txt_search_customer, "searchCustomer");

        txt_searchbill.setBackground(new java.awt.Color(34, 41, 50));
        txt_searchbill.setFont(new java.awt.Font("Segoe UI Light", 2, 14)); // NOI18N
        txt_searchbill.setForeground(new java.awt.Color(255, 255, 255));
        txt_searchbill.setText("Tìm hóa đơn");
        txt_searchbill.setBorder(null);
        txt_searchbill.setCaretColor(new java.awt.Color(255, 255, 255));
        txt_searchbill.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        txt_searchbill.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txt_searchbillMouseClicked(evt);
            }
        });
        txt_searchbill.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txt_searchbillKeyReleased(evt);
            }
        });
        jPanel_search.add(txt_searchbill, "searchBill");

        javax.swing.GroupLayout headerLayout = new javax.swing.GroupLayout(header);
        header.setLayout(headerLayout);
        headerLayout.setHorizontalGroup(
            headerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(headerLayout.createSequentialGroup()
                .addGap(740, 740, 740)
                .addComponent(jPanel_clomin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, headerLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btn_bill1, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(6, 6, 6)
                .addComponent(btn_detail1, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(6, 6, 6)
                .addComponent(btn_customer1, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabelImgFind)
                .addGap(6, 6, 6)
                .addGroup(headerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel_search, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        headerLayout.setVerticalGroup(
            headerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(headerLayout.createSequentialGroup()
                .addComponent(jPanel_clomin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(headerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel_search, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabelImgFind))
                .addGap(1, 1, 1)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(6, 6, 6))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, headerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(btn_detail1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(btn_bill1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(btn_customer1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        body.setBackground(new java.awt.Color(73, 61, 128));
        body.setPreferredSize(new java.awt.Dimension(838, 200));
        body.setVisible(false);
        body.setLayout(new java.awt.CardLayout());

        body_bill.setBackground(new java.awt.Color(73, 61, 128));
        body_bill.setPreferredSize(new java.awt.Dimension(838, 200));

        jLabel10.setFont(new java.awt.Font("Segoe UI", 0, 36)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(255, 255, 255));
        jLabel10.setText("Thêm hóa đơn mới");

        btn_bill.setBackground(new java.awt.Color(147, 122, 255));
        btn_bill.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btn_bill.setForeground(new java.awt.Color(255, 255, 255));
        btn_bill.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        btn_bill.setText("Hóa Đơn");
        btn_bill.setOpaque(true);
        btn_bill.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btn_billMouseClicked(evt);
            }
        });

        btn_detail.setBackground(new java.awt.Color(73, 61, 128));
        btn_detail.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btn_detail.setForeground(new java.awt.Color(255, 255, 255));
        btn_detail.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        btn_detail.setText("Chi Tiết Hóa Đơn");
        btn_detail.setOpaque(true);
        btn_detail.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btn_detailMouseClicked(evt);
            }
        });

        lb_idnv_login.setFont(new java.awt.Font("Segoe UI Light", 0, 14)); // NOI18N
        lb_idnv_login.setForeground(new java.awt.Color(255, 255, 255));
        lb_idnv_login.setText("Nhân viên :");

        lb_khachhang.setFont(new java.awt.Font("Segoe UI Light", 0, 14)); // NOI18N
        lb_khachhang.setForeground(new java.awt.Color(255, 255, 255));
        lb_khachhang.setText("ID khách hàng");

        txt_addbill_custid.setEditable(false);
        txt_addbill_custid.setBackground(new java.awt.Color(34, 41, 50));
        txt_addbill_custid.setFont(new java.awt.Font("Segoe UI Light", 2, 14)); // NOI18N
        txt_addbill_custid.setForeground(new java.awt.Color(153, 153, 153));
        txt_addbill_custid.setText("Nhấp chuột vào để lấy ID");
        txt_addbill_custid.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        txt_addbill_custid.setCaretColor(new java.awt.Color(255, 255, 255));
        txt_addbill_custid.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txt_addbill_custidMouseClicked(evt);
            }
        });

        btn_addbill.setBackground(new java.awt.Color(73, 61, 128));
        btn_addbill.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btn_addbill.setForeground(new java.awt.Color(255, 255, 255));
        btn_addbill.setText("Tạo hóa đơn");
        btn_addbill.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        btn_addbill.setContentAreaFilled(false);
        btn_addbill.setFocusPainted(false);
        btn_addbill.setOpaque(true);
        btn_addbill.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btn_addbillMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btn_addbillMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btn_addbillMouseExited(evt);
            }
        });

        btn_customer.setBackground(new java.awt.Color(73, 61, 128));
        btn_customer.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btn_customer.setForeground(new java.awt.Color(255, 255, 255));
        btn_customer.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        btn_customer.setText("Khách hàng");
        btn_customer.setOpaque(true);
        btn_customer.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btn_customerMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout body_billLayout = new javax.swing.GroupLayout(body_bill);
        body_bill.setLayout(body_billLayout);
        body_billLayout.setHorizontalGroup(
            body_billLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(body_billLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(body_billLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(body_billLayout.createSequentialGroup()
                        .addGroup(body_billLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel10)
                            .addComponent(lb_khachhang)
                            .addComponent(lb_idnv_login)
                            .addComponent(txt_addbill_custid, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 511, Short.MAX_VALUE))
                    .addGroup(body_billLayout.createSequentialGroup()
                        .addGroup(body_billLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jSeparator3)
                            .addGroup(body_billLayout.createSequentialGroup()
                                .addGroup(body_billLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(btn_addbill, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(btn_bill, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 146, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btn_detail, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btn_customer, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 358, Short.MAX_VALUE)))
                        .addContainerGap())))
        );
        body_billLayout.setVerticalGroup(
            body_billLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(body_billLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel10)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, 2, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lb_idnv_login)
                .addGap(18, 18, 18)
                .addComponent(lb_khachhang)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txt_addbill_custid, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btn_addbill, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 21, Short.MAX_VALUE)
                .addGroup(body_billLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btn_bill, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_detail, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_customer, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        body.add(body_bill, "bodyBill");

        body_billDetail.setBackground(new java.awt.Color(73, 61, 128));
        body_billDetail.setPreferredSize(new java.awt.Dimension(838, 200));

        jLabel12.setFont(new java.awt.Font("Segoe UI", 0, 36)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(255, 255, 255));
        jLabel12.setText("set text");

        btn_bill3.setBackground(new java.awt.Color(147, 122, 255));
        btn_bill3.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btn_bill3.setForeground(new java.awt.Color(255, 255, 255));
        btn_bill3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        btn_bill3.setText("Hóa Đơn");
        btn_bill3.setOpaque(true);
        btn_bill3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btn_bill3MouseClicked(evt);
            }
        });

        btn_detail3.setBackground(new java.awt.Color(73, 61, 128));
        btn_detail3.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btn_detail3.setForeground(new java.awt.Color(255, 255, 255));
        btn_detail3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        btn_detail3.setText("Chi Tiết Hóa Đơn");
        btn_detail3.setOpaque(true);
        btn_detail3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btn_detail3MouseClicked(evt);
            }
        });

        jLabel26.setFont(new java.awt.Font("Segoe UI Light", 0, 14)); // NOI18N
        jLabel26.setForeground(new java.awt.Color(255, 255, 255));
        jLabel26.setText("Số lượng");

        txt_addbilldetail_soluong.setBackground(new java.awt.Color(34, 41, 50));
        txt_addbilldetail_soluong.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        txt_addbilldetail_soluong.setForeground(new java.awt.Color(255, 255, 255));
        txt_addbilldetail_soluong.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        txt_addbilldetail_soluong.setCaretColor(new java.awt.Color(255, 255, 255));
        txt_addbilldetail_soluong.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_addbilldetail_soluongActionPerformed(evt);
            }
        });

        btn_addorupdatebilldetail.setBackground(new java.awt.Color(73, 61, 128));
        btn_addorupdatebilldetail.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btn_addorupdatebilldetail.setForeground(new java.awt.Color(255, 255, 255));
        btn_addorupdatebilldetail.setText("set text");
        btn_addorupdatebilldetail.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        btn_addorupdatebilldetail.setContentAreaFilled(false);
        btn_addorupdatebilldetail.setFocusPainted(false);
        btn_addorupdatebilldetail.setOpaque(true);
        btn_addorupdatebilldetail.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btn_addorupdatebilldetailMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btn_addorupdatebilldetailMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btn_addorupdatebilldetailMouseExited(evt);
            }
        });

        lb_addbilldetail_billid.setFont(new java.awt.Font("Segoe UI Light", 0, 18)); // NOI18N
        lb_addbilldetail_billid.setForeground(new java.awt.Color(255, 255, 255));
        lb_addbilldetail_billid.setText("ID hóa đơn : get ID hd");

        jLabel32.setFont(new java.awt.Font("Segoe UI Light", 0, 14)); // NOI18N
        jLabel32.setForeground(new java.awt.Color(255, 255, 255));
        jLabel32.setText("ID sản phẩm");

        btn_customer3.setBackground(new java.awt.Color(73, 61, 128));
        btn_customer3.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btn_customer3.setForeground(new java.awt.Color(255, 255, 255));
        btn_customer3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        btn_customer3.setText("Khách hàng");
        btn_customer3.setOpaque(true);
        btn_customer3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btn_customer3MouseClicked(evt);
            }
        });

        txt_addbilldetail_productid.setEditable(false);
        txt_addbilldetail_productid.setBackground(new java.awt.Color(34, 41, 50));
        txt_addbilldetail_productid.setFont(new java.awt.Font("Segoe UI Light", 2, 14)); // NOI18N
        txt_addbilldetail_productid.setForeground(new java.awt.Color(153, 153, 153));
        txt_addbilldetail_productid.setText("Nhấp chuột vào để lấy ID");
        txt_addbilldetail_productid.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        txt_addbilldetail_productid.setCaretColor(new java.awt.Color(255, 255, 255));
        txt_addbilldetail_productid.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txt_addbilldetail_productidMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout body_billDetailLayout = new javax.swing.GroupLayout(body_billDetail);
        body_billDetail.setLayout(body_billDetailLayout);
        body_billDetailLayout.setHorizontalGroup(
            body_billDetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(body_billDetailLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(body_billDetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(body_billDetailLayout.createSequentialGroup()
                        .addGroup(body_billDetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jSeparator7)
                            .addGroup(body_billDetailLayout.createSequentialGroup()
                                .addComponent(btn_bill3, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btn_detail3, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btn_customer3, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 358, Short.MAX_VALUE)))
                        .addContainerGap())
                    .addGroup(body_billDetailLayout.createSequentialGroup()
                        .addGroup(body_billDetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(body_billDetailLayout.createSequentialGroup()
                                .addComponent(jLabel12)
                                .addGap(18, 18, 18)
                                .addComponent(lb_addbilldetail_billid))
                            .addGroup(body_billDetailLayout.createSequentialGroup()
                                .addGroup(body_billDetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel32)
                                    .addComponent(txt_addbilldetail_productid, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(body_billDetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel26)
                                    .addComponent(txt_addbilldetail_soluong, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addComponent(btn_addorupdatebilldetail, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE))))
        );
        body_billDetailLayout.setVerticalGroup(
            body_billDetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(body_billDetailLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(body_billDetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel12)
                    .addComponent(lb_addbilldetail_billid))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator7, javax.swing.GroupLayout.PREFERRED_SIZE, 2, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(body_billDetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(body_billDetailLayout.createSequentialGroup()
                        .addComponent(jLabel26)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txt_addbilldetail_soluong, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(body_billDetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jLabel32)
                        .addGroup(body_billDetailLayout.createSequentialGroup()
                            .addGap(26, 26, 26)
                            .addComponent(txt_addbilldetail_productid, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 32, Short.MAX_VALUE)
                .addComponent(btn_addorupdatebilldetail, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(33, 33, 33)
                .addGroup(body_billDetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btn_bill3, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_detail3, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_customer3, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        body.add(body_billDetail, "bodyBillDetail");

        body_customer.setBackground(new java.awt.Color(73, 61, 128));
        body_customer.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                body_customerMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                body_customerMouseExited(evt);
            }
        });
        body_customer.setVisible(false);

        jLabel11.setFont(new java.awt.Font("Segoe UI", 0, 36)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(255, 255, 255));
        jLabel11.setText("set text");

        jLabel17.setFont(new java.awt.Font("Segoe UI Light", 0, 14)); // NOI18N
        jLabel17.setForeground(new java.awt.Color(255, 255, 255));
        jLabel17.setText("Tên khách hàng");

        jLabel23.setFont(new java.awt.Font("Segoe UI Light", 0, 14)); // NOI18N
        jLabel23.setForeground(new java.awt.Color(255, 255, 255));
        jLabel23.setText("Phone");

        txt_tellno.setBackground(new java.awt.Color(34, 41, 50));
        txt_tellno.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        txt_tellno.setForeground(new java.awt.Color(255, 255, 255));
        txt_tellno.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        txt_tellno.setCaretColor(new java.awt.Color(255, 255, 255));
        txt_tellno.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_tellnoActionPerformed(evt);
            }
        });

        jLabel24.setFont(new java.awt.Font("Segoe UI Light", 0, 14)); // NOI18N
        jLabel24.setForeground(new java.awt.Color(255, 255, 255));
        jLabel24.setText("Email");

        txt_email.setBackground(new java.awt.Color(34, 41, 50));
        txt_email.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        txt_email.setForeground(new java.awt.Color(255, 255, 255));
        txt_email.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        txt_email.setCaretColor(new java.awt.Color(255, 255, 255));
        txt_email.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_emailActionPerformed(evt);
            }
        });

        jLabel27.setFont(new java.awt.Font("Segoe UI Light", 0, 14)); // NOI18N
        jLabel27.setForeground(new java.awt.Color(255, 255, 255));
        jLabel27.setText("Địa chỉ");

        txt_address.setBackground(new java.awt.Color(34, 41, 50));
        txt_address.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        txt_address.setForeground(new java.awt.Color(255, 255, 255));
        txt_address.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        txt_address.setCaretColor(new java.awt.Color(255, 255, 255));
        txt_address.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_addressActionPerformed(evt);
            }
        });

        rbtn_nam.setBackground(new java.awt.Color(73, 61, 128));
        rbtn_nam.setFont(new java.awt.Font("Segoe UI Light", 0, 14)); // NOI18N
        rbtn_nam.setForeground(new java.awt.Color(255, 255, 255));
        rbtn_nam.setText("Nam");
        rbtn_nam.setBorder(null);
        rbtn_nam.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbtn_namActionPerformed(evt);
            }
        });

        rbtn_nu.setBackground(new java.awt.Color(73, 61, 128));
        rbtn_nu.setFont(new java.awt.Font("Segoe UI Light", 0, 14)); // NOI18N
        rbtn_nu.setForeground(new java.awt.Color(255, 255, 255));
        rbtn_nu.setText("Nữ");
        rbtn_nu.setBorder(null);
        rbtn_nu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbtn_nuActionPerformed(evt);
            }
        });

        jButton3.setBackground(new java.awt.Color(73, 61, 128));
        jButton3.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jButton3.setForeground(new java.awt.Color(255, 255, 255));
        jButton3.setText("set text");
        jButton3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        jButton3.setContentAreaFilled(false);
        jButton3.setFocusPainted(false);
        jButton3.setOpaque(true);
        jButton3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton3MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jButton3MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jButton3MouseExited(evt);
            }
        });

        txt_custname.setBackground(new java.awt.Color(34, 41, 50));
        txt_custname.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        txt_custname.setForeground(new java.awt.Color(255, 255, 255));
        txt_custname.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        txt_custname.setCaretColor(new java.awt.Color(255, 255, 255));
        txt_custname.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_custnameActionPerformed(evt);
            }
        });

        jLabel_custID.setFont(new java.awt.Font("Segoe UI Light", 0, 18)); // NOI18N
        jLabel_custID.setForeground(new java.awt.Color(255, 255, 255));
        jLabel_custID.setText("ID khách hàng : get ID kh");

        btn_bill2.setBackground(new java.awt.Color(147, 122, 255));
        btn_bill2.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btn_bill2.setForeground(new java.awt.Color(255, 255, 255));
        btn_bill2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        btn_bill2.setText("Hóa Đơn");
        btn_bill2.setOpaque(true);
        btn_bill2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btn_bill2MouseClicked(evt);
            }
        });

        btn_detail2.setBackground(new java.awt.Color(73, 61, 128));
        btn_detail2.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btn_detail2.setForeground(new java.awt.Color(255, 255, 255));
        btn_detail2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        btn_detail2.setText("Chi Tiết Hóa Đơn");
        btn_detail2.setOpaque(true);
        btn_detail2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btn_detail2MouseClicked(evt);
            }
        });

        btn_customer2.setBackground(new java.awt.Color(73, 61, 128));
        btn_customer2.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btn_customer2.setForeground(new java.awt.Color(255, 255, 255));
        btn_customer2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        btn_customer2.setText("Khách hàng");
        btn_customer2.setOpaque(true);
        btn_customer2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btn_customer2MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout body_customerLayout = new javax.swing.GroupLayout(body_customer);
        body_customer.setLayout(body_customerLayout);
        body_customerLayout.setHorizontalGroup(
            body_customerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(body_customerLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(body_customerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(body_customerLayout.createSequentialGroup()
                        .addGroup(body_customerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel17)
                            .addComponent(txt_custname, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(body_customerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txt_tellno, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel23))
                        .addGap(18, 18, 18)
                        .addGroup(body_customerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel24)
                            .addComponent(txt_email, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(body_customerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(body_customerLayout.createSequentialGroup()
                                .addComponent(jLabel27)
                                .addGap(0, 195, Short.MAX_VALUE))
                            .addGroup(body_customerLayout.createSequentialGroup()
                                .addComponent(txt_address)
                                .addContainerGap())))
                    .addGroup(body_customerLayout.createSequentialGroup()
                        .addGroup(body_customerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jSeparator4)
                            .addGroup(body_customerLayout.createSequentialGroup()
                                .addGroup(body_customerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(body_customerLayout.createSequentialGroup()
                                        .addComponent(jLabel11)
                                        .addGap(18, 18, 18)
                                        .addComponent(jLabel_custID))
                                    .addGroup(body_customerLayout.createSequentialGroup()
                                        .addComponent(rbtn_nam)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(rbtn_nu))
                                    .addGroup(body_customerLayout.createSequentialGroup()
                                        .addGroup(body_customerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                            .addComponent(jButton3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(btn_bill2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 146, Short.MAX_VALUE))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(btn_detail2, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(btn_customer2, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(0, 0, Short.MAX_VALUE)))
                        .addContainerGap())))
        );
        body_customerLayout.setVerticalGroup(
            body_customerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(body_customerLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(body_customerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(body_customerLayout.createSequentialGroup()
                        .addGroup(body_customerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel11)
                            .addComponent(jLabel_custID))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jSeparator4, javax.swing.GroupLayout.PREFERRED_SIZE, 2, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(body_customerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel17)
                            .addComponent(jLabel24, javax.swing.GroupLayout.Alignment.TRAILING))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(body_customerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txt_email, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txt_custname, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(body_customerLayout.createSequentialGroup()
                        .addComponent(jLabel23)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txt_tellno, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(body_customerLayout.createSequentialGroup()
                        .addComponent(jLabel27)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txt_address, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(body_customerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rbtn_nam)
                    .addComponent(rbtn_nu))
                .addGap(18, 18, 18)
                .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 22, Short.MAX_VALUE)
                .addGroup(body_customerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btn_bill2, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_detail2, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_customer2, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        body.add(body_customer, "bodyCustomer");

        footer.setPreferredSize(new java.awt.Dimension(766, 400));
        footer.setLayout(new java.awt.CardLayout());

        bill.setBackground(java.awt.SystemColor.controlShadow);
        bill.setLayout(new javax.swing.BoxLayout(bill, javax.swing.BoxLayout.LINE_AXIS));

        jScrollPane1.setBackground(java.awt.SystemColor.controlShadow);
        jScrollPane1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(147, 122, 255)));

        tb_bill.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        tb_bill.setForeground(new java.awt.Color(51, 51, 51));
        tb_bill.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID Hóa Đơn", "ID Nhân Viên", "Tổng Tiền", "Ngày Lập", "ID Khách Hàng"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.Integer.class, java.lang.Double.class, java.lang.String.class, java.lang.Integer.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        tb_bill.setGridColor(new java.awt.Color(147, 122, 255));
        tb_bill.setRowHeight(20);
        tb_bill.setSelectionBackground(new java.awt.Color(147, 122, 255));
        tb_bill.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tb_billMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tb_bill);

        bill.add(jScrollPane1);

        footer.add(bill, "footerBill");

        billDetail.setBackground(java.awt.SystemColor.controlShadow);
        billDetail.setLayout(new javax.swing.BoxLayout(billDetail, javax.swing.BoxLayout.LINE_AXIS));

        jScrollPane2.setBackground(java.awt.SystemColor.controlShadow);
        jScrollPane2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(147, 122, 255)));

        tb_billdetail.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        tb_billdetail.setForeground(new java.awt.Color(51, 51, 51));
        tb_billdetail.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID Hóa Đơn", "ID Sản Phẩm", "Số Lượng"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.Integer.class, java.lang.Integer.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        tb_billdetail.setGridColor(new java.awt.Color(147, 122, 255));
        tb_billdetail.setRowHeight(20);
        tb_billdetail.setSelectionBackground(new java.awt.Color(147, 122, 255));
        tb_billdetail.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tb_billdetailMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tb_billdetail);

        billDetail.add(jScrollPane2);

        footer.add(billDetail, "footerDetail");

        customer.setBackground(java.awt.SystemColor.controlShadow);
        customer.setLayout(new javax.swing.BoxLayout(customer, javax.swing.BoxLayout.LINE_AXIS));

        jScrollPane3.setBackground(java.awt.SystemColor.controlShadow);
        jScrollPane3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(147, 122, 255)));

        tb_customer.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        tb_customer.setForeground(new java.awt.Color(51, 51, 51));
        tb_customer.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID Khách Hàng", "Tên Khách Hàng", "Phone", "Email", "Ngày Sinh", "Giới Tính"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        tb_customer.setGridColor(new java.awt.Color(147, 122, 255));
        tb_customer.setRowHeight(20);
        tb_customer.setSelectionBackground(new java.awt.Color(147, 122, 255));
        tb_customer.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tb_customerMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(tb_customer);

        customer.add(jScrollPane3);

        footer.add(customer, "footerCustomer");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(sidebar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(footer, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(header, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 820, Short.MAX_VALUE)
                    .addComponent(body, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(sidebar, javax.swing.GroupLayout.DEFAULT_SIZE, 604, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addComponent(header, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(body, javax.swing.GroupLayout.PREFERRED_SIZE, 269, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(footer, javax.swing.GroupLayout.DEFAULT_SIZE, 252, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents


    private void btn_billMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_billMouseClicked
        setLblColorTab(btn_bill);
        resetLblColorTab(btn_detail);
        resetLblColorTab(btn_customer);
        //switch bettween Jpanels
        billDetail.setVisible(false);
        bill.setVisible(true);
        customer.setVisible(false);
        CardLayout cardLayout = (CardLayout) jPanel_search.getLayout();
        cardLayout.show(jPanel_search, "searchBill");

    }//GEN-LAST:event_btn_billMouseClicked

    private void btn_detailMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_detailMouseClicked
        resetLblColorTab(btn_bill);
        setLblColorTab(btn_detail);
        resetLblColorTab(btn_customer);
        //switch bettween Jpanels
        billDetail.setVisible(true);
        bill.setVisible(false);
        customer.setVisible(false);
        CardLayout cardLayout = (CardLayout) jPanel_search.getLayout();
        cardLayout.show(jPanel_search, "searchBillDetail");
    }//GEN-LAST:event_btn_detailMouseClicked

    private void btn_addbillMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_addbillMouseEntered
        btn_addbill.setBackground(new Color(147, 122, 255));
    }//GEN-LAST:event_btn_addbillMouseEntered

    private void btn_addbillMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_addbillMouseExited
        btn_addbill.setBackground(new Color(73, 61, 128));
    }//GEN-LAST:event_btn_addbillMouseExited

    private void jMenuItemDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemDeleteActionPerformed
        MessageConfirm confirmMsg = new MessageConfirm("<html> <div style='text-align: center;'> Xóa hóa đơn sẽ xóa tất cả chi tiết hóa đơn liên quan. <br> Bạn có chắc muốn xóa ?", 3);
        confirmMsg.setVisible(true);
        JButton check = confirmMsg.getjButtonOK();
        check.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                controller.BillController.RemoveBillByBillID(Integer.parseInt(tb_bill.getValueAt(tb_bill.getSelectedRow(), 0).toString()));
                new Message("Xóa thành công", 1, 3).setVisible(true);
                loadtableBill();
                loadtableBillDetails();
                confirmMsg.setVisible(false);
                confirmMsg.dispose();
            }
        });
    }//GEN-LAST:event_jMenuItemDeleteActionPerformed

    private void tb_billMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tb_billMouseClicked
        if (SwingUtilities.isRightMouseButton(evt)) {
            tb_bill.clearSelection();
            int row = tb_bill.rowAtPoint(evt.getPoint());
            tb_bill.setRowSelectionInterval(row, row);
            jPopupMenuBill.show(tb_bill, evt.getX(), evt.getY());
        }
    }//GEN-LAST:event_tb_billMouseClicked

    private void jLabel_miniMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel_miniMouseClicked
        setState(Frame.ICONIFIED);
    }//GEN-LAST:event_jLabel_miniMouseClicked

    private void jLabel_miniMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel_miniMouseEntered
        jLabel_mini.setBackground(new Color(147, 122, 255));
    }//GEN-LAST:event_jLabel_miniMouseEntered

    private void jLabel_miniMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel_miniMouseExited
        jLabel_mini.setBackground(new Color(34, 41, 50));
    }//GEN-LAST:event_jLabel_miniMouseExited

    private void jLabel_closeMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel_closeMouseClicked
        setVisible(false);
        dispose();
    }//GEN-LAST:event_jLabel_closeMouseClicked

    private void jLabel_closeMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel_closeMouseEntered
        jLabel_close.setBackground(new Color(147, 122, 255));
    }//GEN-LAST:event_jLabel_closeMouseEntered

    private void jLabel_closeMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel_closeMouseExited
        jLabel_close.setBackground(new Color(34, 41, 50));
    }//GEN-LAST:event_jLabel_closeMouseExited

    private void lbl_exitMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbl_exitMouseClicked
        setVisible(false);
        dispose();
    }//GEN-LAST:event_lbl_exitMouseClicked

    private void lbl_addBillMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbl_addBillMouseClicked
        jLabel10.setText("Thêm hóa đơn mới");
        txt_addbill_custid.setText("Nhấp chuột vào để lấy ID");
        CardLayout cardLayout = (CardLayout) footer.getLayout();
        cardLayout.show(footer, "footerBill");
        cardLayout = (CardLayout) body.getLayout();
        cardLayout.show(body, "bodyBill");
        body.setVisible(true);
        setLblColorTab(btn_bill);
        resetLblColorTab(btn_detail);
        resetLblColorTab(btn_customer);
        btn_bill1.setVisible(false);
        btn_detail1.setVisible(false);
        btn_customer1.setVisible(false);
        cardLayout = (CardLayout) jPanel_search.getLayout();
        cardLayout.show(jPanel_search, "searchBill");
        lb_idnv_login.setText("Nhân viên: " + controller.EmployeeController.getEmpNamebyEmpID(DangNhap.accountlogin.getEmpID()));
    }//GEN-LAST:event_lbl_addBillMouseClicked

    private void btn_bill1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_bill1MouseClicked
        setLblColorTab(btn_bill1);
        btn_detail1.setBackground(new Color(34, 41, 50));
        btn_customer1.setBackground(new Color(34, 41, 50));
        //switch bettween Jpanels
        billDetail.setVisible(false);
        bill.setVisible(true);
        customer.setVisible(false);
        CardLayout cardLayout = (CardLayout) jPanel_search.getLayout();
        cardLayout.show(jPanel_search, "searchBill");
    }//GEN-LAST:event_btn_bill1MouseClicked

    private void btn_detail1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_detail1MouseClicked
        btn_bill1.setBackground(new Color(34, 41, 50));
        setLblColorTab(btn_detail1);
        btn_customer1.setBackground(new Color(34, 41, 50));
        //switch bettween Jpanels
        billDetail.setVisible(true);
        bill.setVisible(false);
        customer.setVisible(false);
        CardLayout cardLayout = (CardLayout) jPanel_search.getLayout();
        cardLayout.show(jPanel_search, "searchBillDetail");
    }//GEN-LAST:event_btn_detail1MouseClicked

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

    private void tb_customerMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tb_customerMouseClicked
        if (SwingUtilities.isRightMouseButton(evt)) {
            tb_customer.clearSelection();
            int row = tb_customer.rowAtPoint(evt.getPoint());
            tb_customer.setRowSelectionInterval(row, row);
            jPopupMenuCustomer.show(tb_customer, evt.getX(), evt.getY());
        }
    }//GEN-LAST:event_tb_customerMouseClicked

    private void btn_customerMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_customerMouseClicked
        resetLblColorTab(btn_bill);
        resetLblColorTab(btn_detail);
        setLblColorTab(btn_customer);
        //switch bettween Jpanels
        billDetail.setVisible(false);
        bill.setVisible(false);
        customer.setVisible(true);
        CardLayout cardLayout = (CardLayout) jPanel_search.getLayout();
        cardLayout.show(jPanel_search, "searchCustomer");
    }//GEN-LAST:event_btn_customerMouseClicked

    private void btn_customer1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_customer1MouseClicked
        btn_bill1.setBackground(new Color(34, 41, 50));
        btn_detail1.setBackground(new Color(34, 41, 50));
        setLblColorTab(btn_customer1);
        //switch bettween Jpanels
        billDetail.setVisible(false);
        bill.setVisible(false);
        customer.setVisible(true);
        CardLayout cardLayout = (CardLayout) jPanel_search.getLayout();
        cardLayout.show(jPanel_search, "searchCustomer");
    }//GEN-LAST:event_btn_customer1MouseClicked

    private void lbl_addCustomerMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbl_addCustomerMouseClicked
        jLabel11.setText("Thêm khách hàng mới");
        jLabel_custID.setText("");
        txt_custname.setText("");
        txt_tellno.setText("");
        txt_email.setText("");
        txt_address.setText("");
        jButton3.setText("Thêm");
        CardLayout cardLayout = (CardLayout) footer.getLayout();
        cardLayout.show(footer, "footerCustomer");
        cardLayout = (CardLayout) body.getLayout();
        cardLayout.show(body, "bodyCustomer");
        body.setVisible(true);
        setLblColorTab(btn_customer2);
        resetLblColorTab(btn_detail2);
        resetLblColorTab(btn_bill2);
        btn_bill1.setVisible(false);
        btn_detail1.setVisible(false);
        btn_customer1.setVisible(false);
        cardLayout = (CardLayout) jPanel_search.getLayout();
        cardLayout.show(jPanel_search, "searchCustomer");
    }//GEN-LAST:event_lbl_addCustomerMouseClicked

    private void jMenuItemEditCustomerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemEditCustomerActionPerformed
        jLabel11.setText("Sửa thông tin khách hàng");
        jLabel_custID.setText("ID Khách hàng : " + tb_customer.getValueAt(tb_customer.getSelectedRow(), 0));
        txt_custname.setText(tb_customer.getValueAt(tb_customer.getSelectedRow(), 1).toString());
        txt_tellno.setText(tb_customer.getValueAt(tb_customer.getSelectedRow(), 3).toString());
        txt_email.setText(tb_customer.getValueAt(tb_customer.getSelectedRow(), 4).toString().trim());
        txt_address.setText(tb_customer.getValueAt(tb_customer.getSelectedRow(), 2).toString());
        if (tb_customer.getValueAt(tb_customer.getSelectedRow(), 5).toString().equalsIgnoreCase("Nam")) {
            rbtn_nam.setSelected(true);
            rbtn_nu.setSelected(false);
        } else {
            rbtn_nam.setSelected(false);
            rbtn_nu.setSelected(true);
        }
        jButton3.setText("Cập nhật");
        CardLayout cardLayout = (CardLayout) body.getLayout();
        cardLayout.show(body, "bodyCustomer");
        body.setVisible(true);
        setLblColorTab(btn_customer2);
        resetLblColorTab(btn_detail2);
        resetLblColorTab(btn_bill2);
        btn_bill1.setVisible(false);
        btn_detail1.setVisible(false);
        btn_customer1.setVisible(false);
        cardLayout = (CardLayout) jPanel_search.getLayout();
        cardLayout.show(jPanel_search, "searchCustomer");
    }//GEN-LAST:event_jMenuItemEditCustomerActionPerformed

    private void jButton4MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton4MouseEntered
        jButton4.setBackground(new Color(147, 122, 255));
    }//GEN-LAST:event_jButton4MouseEntered

    private void jButton4MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton4MouseExited
        jButton4.setBackground(new Color(73, 61, 128));
    }//GEN-LAST:event_jButton4MouseExited

    private void jButton5MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton5MouseEntered
        jButton5.setBackground(new Color(147, 122, 255));
    }//GEN-LAST:event_jButton5MouseEntered

    private void jButton5MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton5MouseExited
        jButton5.setBackground(new Color(73, 61, 128));
    }//GEN-LAST:event_jButton5MouseExited

    private void jLabel_close1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel_close1MouseClicked
        jDialog1.dispose();
    }//GEN-LAST:event_jLabel_close1MouseClicked

    private void jLabel_close1MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel_close1MouseEntered
        jLabel_close1.setBackground(new Color(147, 122, 255));
    }//GEN-LAST:event_jLabel_close1MouseEntered

    private void jLabel_close1MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel_close1MouseExited
        jLabel_close1.setBackground(new Color(73, 61, 128));
    }//GEN-LAST:event_jLabel_close1MouseExited

    private void jButton5MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton5MouseClicked
        jLabel11.setText("Thêm khách hàng");
        jLabel_custID.setText("");
        jButton3.setText("Thêm");
        CardLayout cardLayout = (CardLayout) footer.getLayout();
        cardLayout.show(footer, "footerCustomer");
        cardLayout = (CardLayout) body.getLayout();
        cardLayout.show(body, "bodyCustomer");
        body.setVisible(true);
        setLblColorTab(btn_customer2);
        resetLblColorTab(btn_detail2);
        resetLblColorTab(btn_bill2);
        jDialog1.dispose();
    }//GEN-LAST:event_jButton5MouseClicked

    private void jLabel_close2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel_close2MouseClicked
        dlg_customer.dispose();
    }//GEN-LAST:event_jLabel_close2MouseClicked

    private void jLabel_close2MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel_close2MouseEntered
        jLabel_close2.setBackground(new Color(147, 122, 255));
    }//GEN-LAST:event_jLabel_close2MouseEntered

    private void jLabel_close2MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel_close2MouseExited
        jLabel_close2.setBackground(new Color(73, 61, 128));
    }//GEN-LAST:event_jLabel_close2MouseExited

    private void jButton4MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton4MouseClicked
        Vector titlesKhachhang = new Vector();
        titlesKhachhang.add("ID Khách hàng");
        titlesKhachhang.add("Tên khách hàng");
        titlesKhachhang.add("Địa chỉ");
        titlesKhachhang.add("Số điện thoại");
        titlesKhachhang.add("Email");
        titlesKhachhang.add("Giới tính");
        Vector bangdulieu = new Vector();
        try {
            List<Customer> list = controller.CustomerController.GetListCustomer();
            for (Customer customer : list) {
                Vector dongdulieu = new Vector();
                dongdulieu.add(customer.getCustID());
                dongdulieu.add(customer.getCustName());
                dongdulieu.add(customer.getCustAddress());
                dongdulieu.add(customer.getTellNo());
                dongdulieu.add(customer.getEmail());
                dongdulieu.add(customer.getGender());
                bangdulieu.add(dongdulieu);
            }
        } catch (SQLException ex) {
            Logger.getLogger(QLHoaDon.class.getName()).log(Level.SEVERE, null, ex);
        }
        DefaultTableModel defaultTableModel = new DefaultTableModel(bangdulieu, titlesKhachhang) {
            @Override
            public boolean isCellEditable(int row, int col) {
                return false;
            }
        };
        tb_customer1.setModel(defaultTableModel);
        dlg_customer.pack();
        dlg_customer.setLocationRelativeTo(null);
        dlg_customer.setVisible(true);

        jDialog1.dispose();
    }//GEN-LAST:event_jButton4MouseClicked

    private void jLabel_close3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel_close3MouseClicked
        dlg_product.dispose();
    }//GEN-LAST:event_jLabel_close3MouseClicked

    private void jLabel_close3MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel_close3MouseEntered
        jLabel_close3.setBackground(new Color(147, 122, 255));
    }//GEN-LAST:event_jLabel_close3MouseEntered

    private void jLabel_close3MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel_close3MouseExited
        jLabel_close3.setBackground(new Color(73, 61, 128));
    }//GEN-LAST:event_jLabel_close3MouseExited

    private void jTextField_productFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextField_productFocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField_productFocusGained

    private void jDialog1MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jDialog1MousePressed
        getMouseLocation(evt, jDialog1);
    }//GEN-LAST:event_jDialog1MousePressed

    private void jDialog1MouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jDialog1MouseDragged
        setFrameLocation(evt, jDialog1);
    }//GEN-LAST:event_jDialog1MouseDragged

    private void dlg_customerMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_dlg_customerMousePressed
        getMouseLocation(evt, dlg_customer);
    }//GEN-LAST:event_dlg_customerMousePressed

    private void dlg_customerMouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_dlg_customerMouseDragged
        setFrameLocation(evt, dlg_customer);
    }//GEN-LAST:event_dlg_customerMouseDragged

    private void dlg_productMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_dlg_productMousePressed
        getMouseLocation(evt, dlg_product);
    }//GEN-LAST:event_dlg_productMousePressed

    private void dlg_productMouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_dlg_productMouseDragged
        setFrameLocation(evt, dlg_product);
    }//GEN-LAST:event_dlg_productMouseDragged

    private void jMenuItemAddDetailActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemAddDetailActionPerformed
        String selectedEmp = tb_bill.getValueAt(tb_bill.getSelectedRow(), 1).toString();
        int selectedEmpID = Integer.parseInt(selectedEmp.substring(0, selectedEmp.indexOf("(")).trim());
        if (DangNhap.accountlogin.getEmpID() == selectedEmpID) {
            jLabel12.setText("Thêm chi tiết hóa đơn");
            lb_addbilldetail_billid.setText("ID Hóa đơn: " + tb_bill.getValueAt(tb_bill.getSelectedRow(), 0));
            btn_addorupdatebilldetail.setText("Thêm");
            txt_addbilldetail_productid.setText("Nhấp chuột vào để lấy ID");
            txt_addbilldetail_soluong.setText("");
            CardLayout cardLayout = (CardLayout) body.getLayout();
            cardLayout.show(body, "bodyBillDetail");
            body.setVisible(true);
            resetLblColorTab(btn_bill3);
            setLblColorTab(btn_detail3);
            resetLblColorTab(btn_customer3);
            btn_bill1.setVisible(false);
            btn_detail1.setVisible(false);
            btn_customer1.setVisible(false);
            cardLayout = (CardLayout) footer.getLayout();
            cardLayout.show(footer, "footerDetail");
            cardLayout = (CardLayout) jPanel_search.getLayout();
            cardLayout.show(jPanel_search, "searchBillDetail");
        } else {
            new Message("Hóa đơn này được tạo bởi một nhân viên khác. Hãy tạo hóa đơn mới", 2, 3).setVisible(true);
        }
    }//GEN-LAST:event_jMenuItemAddDetailActionPerformed

    private void jMenuItemEditDetailActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemEditDetailActionPerformed
        jLabel12.setText("Sửa chi tiết hóa đơn");

        lb_addbilldetail_billid.setText("ID hóa đơn: " + tb_billdetail.getValueAt(tb_billdetail.getSelectedRow(), 0));
        txt_addbilldetail_productid.setText(tb_billdetail.getValueAt(tb_billdetail.getSelectedRow(), 1).toString());
        controller.BillDetailsController.EditBillDetails_ChangeQtyonHand(new BillDetails(
                Integer.parseInt(tb_billdetail.getValueAt(tb_billdetail.getSelectedRow(), 0).toString()),
                Integer.parseInt(tb_billdetail.getValueAt(tb_billdetail.getSelectedRow(), 1).toString()),
                0));
        txt_addbilldetail_soluong.setText("0");

        btn_addorupdatebilldetail.setText("Cập nhật");
        CardLayout cardLayout = (CardLayout) body.getLayout();
        cardLayout.show(body, "bodyBillDetail");
        body.setVisible(true);
        resetLblColorTab(btn_bill3);
        setLblColorTab(btn_detail3);
        resetLblColorTab(btn_customer3);
        btn_bill1.setVisible(false);
        btn_detail1.setVisible(false);
        btn_customer1.setVisible(false);
    }//GEN-LAST:event_jMenuItemEditDetailActionPerformed

    private void jMenuItemDeleteDetailActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemDeleteDetailActionPerformed
        // TODO add your handling code here:
        BillDetails deleteline = new BillDetails();
        deleteline.setBillID(Integer.parseInt(tb_billdetail.getValueAt(tb_billdetail.getSelectedRow(), 0).toString()));
        deleteline.setProductID(Integer.parseInt(tb_billdetail.getValueAt(tb_billdetail.getSelectedRow(), 1).toString()));
        deleteline.setQtyonHand(Integer.parseInt(tb_billdetail.getValueAt(tb_billdetail.getSelectedRow(), 2).toString()));
        try {
            controller.BillDetailsController.RemoveBillDetails(deleteline);
            new Message("Xóa thành công", 1, 3).setVisible(true);
            loadtableBillDetails();
            loadtableBill();
        } catch (SQLException ex) {
            Logger.getLogger(QLHoaDon.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jMenuItemDeleteDetailActionPerformed

    private void tb_billdetailMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tb_billdetailMouseClicked
        if (SwingUtilities.isRightMouseButton(evt)) {
            tb_billdetail.clearSelection();
            int row = tb_billdetail.rowAtPoint(evt.getPoint());
            tb_billdetail.setRowSelectionInterval(row, row);
            jPopupMenuBillDetail.show(tb_billdetail, evt.getX(), evt.getY());
        }
    }//GEN-LAST:event_tb_billdetailMouseClicked

    private void txt_addbilldetail_productidMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txt_addbilldetail_productidMouseClicked
        loadtableProduct();
        dlg_product.pack();
        dlg_product.setLocationRelativeTo(null);
        dlg_product.setVisible(true);
    }//GEN-LAST:event_txt_addbilldetail_productidMouseClicked

    private void btn_customer3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_customer3MouseClicked
        resetLblColorTab(btn_bill3);
        resetLblColorTab(btn_detail3);
        setLblColorTab(btn_customer3);
        //switch bettween Jpanels
        billDetail.setVisible(false);
        bill.setVisible(false);
        customer.setVisible(true);
        CardLayout cardLayout = (CardLayout) jPanel_search.getLayout();
        cardLayout.show(jPanel_search, "searchCustomer");
    }//GEN-LAST:event_btn_customer3MouseClicked

    private void btn_addorupdatebilldetailMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_addorupdatebilldetailMouseExited
        btn_addorupdatebilldetail.setBackground(new Color(73, 61, 128));
    }//GEN-LAST:event_btn_addorupdatebilldetailMouseExited

    private void btn_addorupdatebilldetailMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_addorupdatebilldetailMouseEntered
        btn_addorupdatebilldetail.setBackground(new Color(147, 122, 255));
    }//GEN-LAST:event_btn_addorupdatebilldetailMouseEntered

    private void txt_addbilldetail_soluongActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_addbilldetail_soluongActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_addbilldetail_soluongActionPerformed

    private void btn_detail3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_detail3MouseClicked
        resetLblColorTab(btn_bill3);
        setLblColorTab(btn_detail3);
        resetLblColorTab(btn_customer3);
        //switch bettween Jpanels
        billDetail.setVisible(true);
        bill.setVisible(false);
        customer.setVisible(false);
        CardLayout cardLayout = (CardLayout) jPanel_search.getLayout();
        cardLayout.show(jPanel_search, "searchBillDetail");
    }//GEN-LAST:event_btn_detail3MouseClicked

    private void btn_bill3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_bill3MouseClicked
        setLblColorTab(btn_bill3);
        resetLblColorTab(btn_detail3);
        resetLblColorTab(btn_customer3);
        //switch bettween Jpanels
        billDetail.setVisible(false);
        bill.setVisible(true);
        customer.setVisible(false);
        CardLayout cardLayout = (CardLayout) jPanel_search.getLayout();
        cardLayout.show(jPanel_search, "searchBill");
    }//GEN-LAST:event_btn_bill3MouseClicked

    private void tb_productMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tb_productMouseClicked
        if (evt.getClickCount() == 2 && !evt.isConsumed()) {
            txt_addbilldetail_productid.setText(tb_product.getValueAt(tb_product.getSelectedRow(), 0).toString());
            dlg_product.setVisible(false);
            evt.consume();
            //handle double click event.
        }

    }//GEN-LAST:event_tb_productMouseClicked

    private void tb_productMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tb_productMousePressed
        // TODO add your handling code here:
    }//GEN-LAST:event_tb_productMousePressed

    private void btn_addorupdatebilldetailMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_addorupdatebilldetailMouseClicked
        if (btn_addorupdatebilldetail.getText().trim().equalsIgnoreCase("Thêm")) {
            BillDetails billDetails = new BillDetails();
            if (Validation.checkinputInteger(txt_addbilldetail_soluong.getText()) == false) {
                new Message("Số lượng phải là số nguyên lớn hơn 0", 2, 3).setVisible(true);
            } else {
                if (Integer.parseInt(txt_addbilldetail_soluong.getText()) <= 0) {
                    new Message("Số lượng phải là số nguyên lớn hơn 0", 2, 3).setVisible(true);
                } else {
                    int soluonghienco = Integer.parseInt(tb_product.getValueAt(tb_product.getSelectedRow(), 3).toString());
                    int soluongnhapvao = Integer.parseInt(txt_addbilldetail_soluong.getText());
                    if (soluongnhapvao > soluonghienco) {
                        new Message("Số lượng còn lại là: " + tb_product.getValueAt(tb_product.getSelectedRow(), 3), 2, 3).setVisible(true);
                    } else {
                        if (controller.BillDetailsController.checkExistBillDetails(Integer.parseInt(lb_addbilldetail_billid.getText().substring(12)), Integer.parseInt(txt_addbilldetail_productid.getText()))) {
                            new Message("Sản phẩm này đã được thêm vào hóa đơn", 2, 3).setVisible(true);
                        } else {
                            billDetails.setBillID(Integer.parseInt(lb_addbilldetail_billid.getText().substring(12)));
                            billDetails.setProductID(Integer.parseInt(txt_addbilldetail_productid.getText()));
                            billDetails.setQtyonHand(soluongnhapvao);
                            try {
                                controller.BillDetailsController.AddBillDetails(billDetails);
                                loadtableBillDetails();
                                loadtableBill();
                                new Message("Thực hiện thành công", 1, 3).setVisible(true);
                            } catch (SQLException ex) {
                                Logger.getLogger(QLHoaDon.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                    }
                }
            }
        } else {
            BillDetails billDetails = new BillDetails();
            if (Validation.checkinputInteger(txt_addbilldetail_soluong.getText()) == false) {
                new Message("Số lượng phải là số nguyên lớn hơn 0", 2, 3).setVisible(true);
            } else {
                if (Integer.parseInt(txt_addbilldetail_soluong.getText()) <= 0) {
                    new Message("Số lượng phải là số nguyên lớn hơn 0", 2, 3).setVisible(true);
                } else {
                    int soluonghienco = controller.ProductController.getQtyOnHandByProductID(Integer.parseInt(txt_addbilldetail_productid.getText()));
                    int soluongnhapvao = Integer.parseInt(txt_addbilldetail_soluong.getText());
                    if (soluongnhapvao > soluonghienco) {
                        new Message("Số lượng còn lại là: " + soluonghienco, 2, 3).setVisible(true);
                    } else {
                        billDetails.setBillID(Integer.parseInt(lb_addbilldetail_billid.getText().substring(12)));
                        billDetails.setProductID(Integer.parseInt(txt_addbilldetail_productid.getText()));
                        billDetails.setQtyonHand(soluongnhapvao);
                        controller.BillDetailsController.EditBillDetails_ChangeQtyonHand(billDetails);
                        loadtableBillDetails();
                        loadtableBill();
                        new Message("Thực hiện thành công", 1, 3).setVisible(true);
                    }
                }
            }
        }
    }//GEN-LAST:event_btn_addorupdatebilldetailMouseClicked

    private void jPanel2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel2MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jPanel2MouseClicked

    private void tb_customer1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tb_customer1MouseClicked
        if (evt.getClickCount() == 2 && !evt.isConsumed()) {
            txt_addbill_custid.setText(tb_customer1.getValueAt(tb_customer1.getSelectedRow(), 0).toString());
            dlg_customer.setVisible(false);
            evt.consume();
            //handle double click event.
        }
    }//GEN-LAST:event_tb_customer1MouseClicked

    private void btn_addbillMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_addbillMouseClicked
        Bill newbill = new Bill();
        newbill.setCustID(Integer.parseInt(txt_addbill_custid.getText()));
        newbill.setEmpID(DangNhap.accountlogin.getEmpID());
        try {
            controller.BillController.AddBill(newbill);
            loadtableBill();
        } catch (SQLException ex) {
            Logger.getLogger(QLHoaDon.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btn_addbillMouseClicked

    private void txt_searchbillKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_searchbillKeyReleased
        // TODO add your handling code here:
        Vector data = new Vector();
        for (int i = 0; i < modelHoadon.getRowCount(); i++) {
            for (int j = 0; j < modelHoadon.getColumnCount(); j++) {
                if (modelHoadon.getValueAt(i, j).toString().toUpperCase().contains(txt_searchbill.getText().toUpperCase())) {
                    Vector dongdulieu = new Vector();
                    for (int k = 0; k < modelHoadon.getColumnCount(); k++) {
                        dongdulieu.add(modelHoadon.getValueAt(i, k));
                    }
                    data.add(dongdulieu);
                    break;
                }
            }
        }
        DefaultTableModel model = new DefaultTableModel(data, titlesHoadon) {
            @Override
            public boolean isCellEditable(int row, int col) {
                return false;
            }
        };
        tb_bill.setModel(model);
    }//GEN-LAST:event_txt_searchbillKeyReleased

    private void txt_search_billdetailKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_search_billdetailKeyReleased
        Vector data = new Vector();
        for (int i = 0; i < modelchitietHoadon.getRowCount(); i++) {
            for (int j = 0; j < modelchitietHoadon.getColumnCount(); j++) {
                if (modelchitietHoadon.getValueAt(i, j).toString().toUpperCase().contains(txt_search_billdetail.getText().toUpperCase())) {
                    Vector dongdulieu = new Vector();
                    for (int k = 0; k < modelchitietHoadon.getColumnCount(); k++) {
                        dongdulieu.add(modelchitietHoadon.getValueAt(i, k));
                    }
                    data.add(dongdulieu);
                    break;
                }
            }
        }
        DefaultTableModel model = new DefaultTableModel(data, titleschitietHoadon) {
            @Override
            public boolean isCellEditable(int row, int col) {
                return false;
            }
        };
        tb_billdetail.setModel(model);
    }//GEN-LAST:event_txt_search_billdetailKeyReleased

    private void txt_search_customerKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_search_customerKeyReleased
        // TODO add your handling code here:
        Vector data = new Vector();
        for (int i = 0; i < modelKhachhang.getRowCount(); i++) {
            for (int j = 0; j < modelKhachhang.getColumnCount(); j++) {
                if (modelKhachhang.getValueAt(i, j).toString().toUpperCase().contains(txt_search_customer.getText().toUpperCase())) {
                    Vector dongdulieu = new Vector();
                    for (int k = 0; k < modelKhachhang.getColumnCount(); k++) {
                        dongdulieu.add(modelKhachhang.getValueAt(i, k));
                    }
                    data.add(dongdulieu);
                    break;
                }
            }
        }
        DefaultTableModel model = new DefaultTableModel(data, titlesKhachhang) {
            @Override
            public boolean isCellEditable(int row, int col) {
                return false;
            }
        };
        tb_customer.setModel(model);
    }//GEN-LAST:event_txt_search_customerKeyReleased

    private void txt_addbill_custidMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txt_addbill_custidMouseClicked
        jDialog1.pack();
        jDialog1.setLocationRelativeTo(null);
        jButton4.setBackground(new Color(73, 61, 128));
        jButton5.setBackground(new Color(73, 61, 128));
        jDialog1.setVisible(true);
    }//GEN-LAST:event_txt_addbill_custidMouseClicked

    private void body_customerMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_body_customerMouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_body_customerMouseExited

    private void body_customerMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_body_customerMouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_body_customerMouseEntered

    private void btn_customer2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_customer2MouseClicked
        resetLblColorTab(btn_bill2);
        resetLblColorTab(btn_detail2);
        setLblColorTab(btn_customer2);
        //switch bettween Jpanels
        billDetail.setVisible(false);
        bill.setVisible(false);
        customer.setVisible(true);
        CardLayout cardLayout = (CardLayout) jPanel_search.getLayout();
        cardLayout.show(jPanel_search, "searchCustomer");
    }//GEN-LAST:event_btn_customer2MouseClicked

    private void btn_detail2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_detail2MouseClicked
        resetLblColorTab(btn_bill2);
        setLblColorTab(btn_detail2);
        resetLblColorTab(btn_customer2);
        //switch bettween Jpanels
        billDetail.setVisible(true);
        bill.setVisible(false);
        customer.setVisible(false);
        CardLayout cardLayout = (CardLayout) jPanel_search.getLayout();
        cardLayout.show(jPanel_search, "searchBillDetail");
    }//GEN-LAST:event_btn_detail2MouseClicked

    private void btn_bill2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_bill2MouseClicked
        setLblColorTab(btn_bill2);
        resetLblColorTab(btn_detail2);
        resetLblColorTab(btn_customer2);
        //switch bettween Jpanels
        billDetail.setVisible(false);
        bill.setVisible(true);
        customer.setVisible(false);
        CardLayout cardLayout = (CardLayout) jPanel_search.getLayout();
        cardLayout.show(jPanel_search, "searchBill");
    }//GEN-LAST:event_btn_bill2MouseClicked

    private void txt_custnameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_custnameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_custnameActionPerformed

    private void jButton3MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton3MouseExited
        jButton3.setBackground(new Color(73, 61, 128));
    }//GEN-LAST:event_jButton3MouseExited

    private void jButton3MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton3MouseEntered
        jButton3.setBackground(new Color(147, 122, 255));
    }//GEN-LAST:event_jButton3MouseEntered

    private void jButton3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton3MouseClicked
        if (jButton3.getText().trim().equalsIgnoreCase("Thêm")) {
            Customer newcus = new Customer();
            if ((txt_custname.getText().isEmpty()) || txt_address.getText().isEmpty() || txt_email.getText().isEmpty() || txt_tellno.getText().isEmpty() || ((rbtn_nam.isSelected() == false) && (rbtn_nu.isSelected() == false))) {
                new Message("Bạn cần nhập đầy đủ thông tin", 2, 3).setVisible(true);
            } else {
                if (Validation.isPhoneNumber(txt_tellno.getText().trim()) == false) {
                    new Message("Số điện thoại không đúng", 2, 3).setVisible(true);
                } else {
                    try {
                        if (controller.CustomerController.checkExistTellNoCust(txt_tellno.getText().trim())) {
                            new Message("Số điện thoại trùng với khách hàng khác", 2, 3).setVisible(true);
                        } else {
                            if (Validation.isValidMail(txt_email.getText().trim()) == false) {
                                new Message("Email không đúng", 2, 3).setVisible(true);
                            } else {
                                if (controller.CustomerController.checkExistEmailCust(txt_email.getText().trim())) {
                                    new Message("Email trùng với khách hàng khác", 2, 3).setVisible(true);
                                } else {
                                    newcus.setCustName(txt_custname.getText().trim());
                                    newcus.setTellNo(txt_tellno.getText().trim());
                                    newcus.setEmail(txt_email.getText().trim());
                                    newcus.setCustAddress(txt_address.getText().trim());
                                    if (rbtn_nam.isSelected()) {
                                        newcus.setGender("Nam");
                                    } else {
                                        newcus.setGender("Nữ");
                                    }
                                    controller.CustomerController.AddCustomer(newcus);
                                    new Message("Thêm khách hàng thành công", 1, 3).setVisible(true);
                                    loadtableCustomer();
                                }
                            }
                        }
                    } catch (SQLException ex) {
                        Logger.getLogger(QLHoaDon.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        }
        if (jButton3.getText().trim().equalsIgnoreCase("Cập nhật")) {
            Customer newcus = new Customer();
            if ((txt_custname.getText().isEmpty()) || txt_address.getText().isEmpty() || txt_email.getText().isEmpty() || txt_tellno.getText().isEmpty() || ((rbtn_nam.isSelected() == false) && (rbtn_nu.isSelected() == false))) {
                new Message("Bạn cần nhập đầy đủ thông tin", 2, 3).setVisible(true);
            } else {
                if (Validation.isPhoneNumber(txt_tellno.getText().trim()) == false) {
                    new Message("Số điện thoại không đúng", 2, 3).setVisible(true);
                } else {
                    if (Validation.isValidMail(txt_email.getText().trim()) == false) {
                        new Message("Địa chỉ email không đúng", 2, 3).setVisible(true);
                    } else {
                        if (controller.CustomerController.checkTellNoOnUpdate(Integer.parseInt(jLabel_custID.getText().substring(16)), txt_tellno.getText().trim()) == false) {
                            new Message("Số điện thoại đã được sử dụng", 2, 3).setVisible(true);
                        } else {
                            if (controller.CustomerController.checkEmailOnUpdate(Integer.parseInt(jLabel_custID.getText().substring(16)), txt_email.getText().trim()) == false) {
                                new Message("Địa chỉ email đã được sử dụng", 2, 3).setVisible(true);
                            } else {
                                newcus.setCustID(Integer.parseInt(jLabel_custID.getText().substring(16)));
                                newcus.setCustName(txt_custname.getText().trim());
                                newcus.setTellNo(txt_tellno.getText().trim());
                                newcus.setEmail(txt_email.getText().trim());
                                newcus.setCustAddress(txt_address.getText().trim());
                                if (rbtn_nam.isSelected()) {
                                    newcus.setGender("Nam");
                                } else {
                                    newcus.setGender("Nữ");
                                }
                                try {
                                    controller.CustomerController.EditCustomer(newcus);
                                } catch (SQLException ex) {
                                    Logger.getLogger(QLHoaDon.class.getName()).log(Level.SEVERE, null, ex);
                                }
                                new Message("Cập nhật khách hàng thành công", 1, 3).setVisible(true);
                                loadtableCustomer();
                            }
                        }
                    }
                }
            }
        }
    }//GEN-LAST:event_jButton3MouseClicked

    private void rbtn_nuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbtn_nuActionPerformed
        rbtn_nam.setSelected(false);
    }//GEN-LAST:event_rbtn_nuActionPerformed

    private void rbtn_namActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbtn_namActionPerformed
        rbtn_nu.setSelected(false);
    }//GEN-LAST:event_rbtn_namActionPerformed

    private void txt_addressActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_addressActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_addressActionPerformed

    private void txt_emailActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_emailActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_emailActionPerformed

    private void txt_tellnoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_tellnoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_tellnoActionPerformed

    private void txt_search_billdetailMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txt_search_billdetailMouseClicked
        txt_search_billdetail.setText("");
    }//GEN-LAST:event_txt_search_billdetailMouseClicked

    private void txt_search_customerMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txt_search_customerMouseClicked
        txt_search_customer.setText("");
    }//GEN-LAST:event_txt_search_customerMouseClicked

    private void txt_searchbillMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txt_searchbillMouseClicked
        txt_searchbill.setText("");
    }//GEN-LAST:event_txt_searchbillMouseClicked

    private void lbl_addBillMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbl_addBillMouseEntered
        setLblColor(lbl_addBill);
    }//GEN-LAST:event_lbl_addBillMouseEntered

    private void lbl_addBillMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbl_addBillMouseExited
        resetLblColor(lbl_addBill);
    }//GEN-LAST:event_lbl_addBillMouseExited

    private void lbl_addCustomerMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbl_addCustomerMouseEntered
        setLblColor(lbl_addCustomer);
    }//GEN-LAST:event_lbl_addCustomerMouseEntered

    private void lbl_addCustomerMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbl_addCustomerMouseExited
        resetLblColor(lbl_addCustomer);
    }//GEN-LAST:event_lbl_addCustomerMouseExited

    private void lbl_exitMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbl_exitMouseEntered
        setLblColor(lbl_exit);
    }//GEN-LAST:event_lbl_exitMouseEntered

    private void lbl_exitMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbl_exitMouseExited
        resetLblColor(lbl_exit);
    }//GEN-LAST:event_lbl_exitMouseExited

    private void txt_search_customer1KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_search_customer1KeyReleased
        Vector data = new Vector();
        for (int i = 0; i < modelKhachhang.getRowCount(); i++) {
            for (int j = 0; j < modelKhachhang.getColumnCount(); j++) {
                if (modelKhachhang.getValueAt(i, j).toString().toUpperCase().contains(txt_search_customer1.getText().toUpperCase())) {
                    Vector dongdulieu = new Vector();
                    for (int k = 0; k < modelKhachhang.getColumnCount(); k++) {
                        dongdulieu.add(modelKhachhang.getValueAt(i, k));
                    }
                    data.add(dongdulieu);
                    break;
                }
            }
        }
        DefaultTableModel model = new DefaultTableModel(data, titlesKhachhang) {
            @Override
            public boolean isCellEditable(int row, int col) {
                return false;
            }
        };
        tb_customer1.setModel(model);
    }//GEN-LAST:event_txt_search_customer1KeyReleased

    private void txt_search_customer1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txt_search_customer1MouseClicked
        txt_search_customer1.setText("");
    }//GEN-LAST:event_txt_search_customer1MouseClicked

    private void jTextField_productKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField_productKeyReleased
        Vector data = new Vector();
        for (int i = 0; i < modelProduct.getRowCount(); i++) {
            for (int j = 0; j < modelProduct.getColumnCount(); j++) {
                if (modelProduct.getValueAt(i, j).toString().toUpperCase().contains(jTextField_product.getText().toUpperCase())) {
                    Vector dongdulieu = new Vector();
                    for (int k = 0; k < modelProduct.getColumnCount(); k++) {
                        dongdulieu.add(modelProduct.getValueAt(i, k));
                    }
                    data.add(dongdulieu);
                    break;
                }
            }
        }
        DefaultTableModel model = new DefaultTableModel(data, titlesProduct) {
            @Override
            public boolean isCellEditable(int row, int col) {
                return false;
            }
        };
        tb_product.setModel(model);
    }//GEN-LAST:event_jTextField_productKeyReleased

    public void setLblColor(JLabel lbl) {
        lbl.setForeground(new Color(147, 122, 255));
    }

    public void resetLblColor(JLabel lbl) {
        lbl.setForeground(new Color(166, 166, 166));
    }

    // ------------switch between colors for Active/Inactive color
    public void setLblColorTab(JLabel lbl) {
        lbl.setBackground(new Color(147, 122, 255));
    }

    public void resetLblColorTab(JLabel lbl) {
        lbl.setBackground(new Color(73, 61, 128));
    }

    // These two methods make the window can be dragged (movable window)
    int xx;
    int xy;

    public void getMouseLocation(java.awt.event.MouseEvent evt, JDialog jdialog) {
        xx = evt.getX();
        xy = evt.getY();
    }

    public void setFrameLocation(java.awt.event.MouseEvent evt, JDialog jdialog) {
        int x = evt.getXOnScreen();
        int y = evt.getYOnScreen();
        jdialog.setLocation(x - xx, y - xy);
    }
    // These two methods make the window can be dragged (movable window)

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
            java.util.logging.Logger.getLogger(QLHoaDon.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new QLHoaDon().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel bill;
    private javax.swing.JPanel billDetail;
    private javax.swing.JPanel body;
    private javax.swing.JPanel body_bill;
    private javax.swing.JPanel body_billDetail;
    private javax.swing.JPanel body_customer;
    private javax.swing.JButton btn_addbill;
    private static javax.swing.JButton btn_addorupdatebilldetail;
    private javax.swing.JLabel btn_bill;
    private javax.swing.JLabel btn_bill1;
    private javax.swing.JLabel btn_bill2;
    private javax.swing.JLabel btn_bill3;
    private javax.swing.JLabel btn_customer;
    private javax.swing.JLabel btn_customer1;
    private javax.swing.JLabel btn_customer2;
    private javax.swing.JLabel btn_customer3;
    private javax.swing.JLabel btn_detail;
    private javax.swing.JLabel btn_detail1;
    private javax.swing.JLabel btn_detail2;
    private javax.swing.JLabel btn_detail3;
    private javax.swing.JPanel customer;
    private javax.swing.JPanel customer1;
    private javax.swing.JDialog dlg_customer;
    private static javax.swing.JDialog dlg_product;
    private javax.swing.JPanel footer;
    private javax.swing.JPanel header;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JDialog jDialog1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabelImgFind;
    private javax.swing.JLabel jLabel_close;
    private javax.swing.JLabel jLabel_close1;
    private javax.swing.JLabel jLabel_close2;
    private javax.swing.JLabel jLabel_close3;
    private javax.swing.JLabel jLabel_custID;
    private javax.swing.JLabel jLabel_mini;
    private javax.swing.JMenuItem jMenuItemAddDetail;
    private javax.swing.JMenuItem jMenuItemDelete;
    private javax.swing.JMenuItem jMenuItemDeleteDetail;
    private javax.swing.JMenuItem jMenuItemEditCustomer;
    private javax.swing.JMenuItem jMenuItemEditDetail;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel_clomin;
    private javax.swing.JPanel jPanel_search;
    private javax.swing.JPopupMenu jPopupMenuBill;
    private javax.swing.JPopupMenu jPopupMenuBillDetail;
    private javax.swing.JPopupMenu jPopupMenuCustomer;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JSeparator jSeparator5;
    private javax.swing.JSeparator jSeparator6;
    private javax.swing.JSeparator jSeparator7;
    private javax.swing.JTextField jTextField_product;
    public static javax.swing.JLabel lb_addbilldetail_billid;
    private static javax.swing.JLabel lb_idnv_login;
    private javax.swing.JLabel lb_khachhang;
    private javax.swing.JLabel lbl_addBill;
    private javax.swing.JLabel lbl_addCustomer;
    private javax.swing.JLabel lbl_exit;
    private javax.swing.JPanel product;
    public static javax.swing.JRadioButton rbtn_nam;
    public static javax.swing.JRadioButton rbtn_nu;
    private javax.swing.JPanel sidebar;
    public static javax.swing.JTable tb_bill;
    private static javax.swing.JTable tb_billdetail;
    private static javax.swing.JTable tb_customer;
    public static javax.swing.JTable tb_customer1;
    private static javax.swing.JTable tb_product;
    public static javax.swing.JTextField txt_addbill_custid;
    private javax.swing.JTextField txt_addbilldetail_productid;
    private javax.swing.JTextField txt_addbilldetail_soluong;
    private javax.swing.JTextField txt_address;
    private javax.swing.JTextField txt_custname;
    private javax.swing.JTextField txt_email;
    private javax.swing.JTextField txt_search_billdetail;
    private static javax.swing.JTextField txt_search_customer;
    private javax.swing.JTextField txt_search_customer1;
    private javax.swing.JTextField txt_searchbill;
    private javax.swing.JTextField txt_tellno;
    // End of variables declaration//GEN-END:variables
}
