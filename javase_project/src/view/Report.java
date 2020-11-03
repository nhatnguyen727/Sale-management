/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Frame;
import java.sql.Date;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.table.DefaultTableModel;
import model.ProductReport;
import model.RevenueReport;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;

/**
 *
 * @author stfu
 */
public class Report extends javax.swing.JFrame {

    static DecimalFormat currencyFormatter = new DecimalFormat("###,###,###");
    static DecimalFormat currencyCompactFormatter = new DecimalFormat("###");
    static DateFormat dateFormatter = new SimpleDateFormat("dd/MM/yyyy");

    static Date oldestDayFromDB = controller.ReportController.GetBillOldestDay();

    static JFreeChart BarChart;
    static DefaultCategoryDataset barChartData = null;

    static DefaultPieDataset pieData = null;
    static JFreeChart pie3DChart = null;
    static ChartPanel pie3DChartPanel = null;

    static boolean blockDateChooserListenerAtTheMoment = false;

    /**
     * Creates new form Report
     */
    public Report() {
        /* Set the Windows look and feel */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Windows".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Report.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        initComponents();
        lbl_revenueRP.setForeground(new Color(139, 137, 217));
        CardLayout cardLayout = (CardLayout) container.getLayout();
        cardLayout.show(container, "controlRevenue");
        cardLayout = (CardLayout) footer.getLayout();
        cardLayout.show(footer, "footerRevenue");
        createYearComboBoxData();
        createTblDataProductRP(oldestDayFromDB, new Date(System.currentTimeMillis()));
        createBarChart();
        createPie3DChart();
        jTable_revenueReport.getTableHeader().setForeground(new Color(139, 137, 217));
        jTable_revenueReport.getTableHeader().setBackground(new Color(139, 137, 217));
        jTable_revenueReport.getTableHeader().setFont(new Font("Dialog", Font.BOLD, 12));
        jTable_productReport.getTableHeader().setForeground(new Color(139, 137, 217));
        jTable_productReport.getTableHeader().setBackground(new Color(139, 137, 217));
        jTable_productReport.getTableHeader().setFont(new Font("Dialog", Font.BOLD, 12));
    }

    private void createYearComboBoxData() {
        LocalDate localDate = oldestDayFromDB.toLocalDate();
        int startYear = localDate.getYear();
        int currentYear = Calendar.getInstance().get(Calendar.YEAR);
        int count = -1;
        for (int i = startYear; i <= currentYear; i++) {
            cb_yearPicker.addItem(String.valueOf(i));
            count++;
        }
        cb_yearPicker.setSelectedIndex(count);
    }

    private DefaultTableModel createModelRevenueRP() {
        DefaultTableModel model = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        jTable_revenueReport.setModel(model);
        model.addColumn("Ngày lập hóa đơn");
        model.addColumn("Tổng thu");
        model.addColumn("Tổng vốn");
        model.addColumn("Tổng lãi");
        return model;
    }

    private void createTblDataRevenueRP(Date dateFrom, Date dateTo) {
        DefaultTableModel model = createModelRevenueRP();
        List<RevenueReport> list = controller.ReportController.GetListRevenueRPByDay(dateFrom, dateTo);
        for (RevenueReport rev : list) {
            model.addRow(new Object[]{dateFormatter.format(rev.getDates()), currencyFormatter.format(rev.getTotalBill()),
                currencyFormatter.format(rev.getTotalFund()), currencyFormatter.format(rev.getTotalProfit())});
        }
        long sumTotalBill = 0;
        long sumTotalFund = 0;
        long sumTotalProfit = 0;
        //int range from -2,147,483,647 to 2,147,483,647. It will turn to negative value when int increment hit the range (Integer overflow). Use 'long' instead
        int totalRow = model.getRowCount();
        for (int i = 0; i < totalRow; i++) {
            sumTotalBill += Integer.valueOf(model.getValueAt(i, 1).toString().replaceAll(",", ""));
            sumTotalFund += Integer.valueOf(model.getValueAt(i, 2).toString().replaceAll(",", ""));
            sumTotalProfit += Integer.valueOf(model.getValueAt(i, 3).toString().replaceAll(",", ""));
        }
        model.addRow(new Object[]{"<html><b>Tổng cộng</b></html>", "<html><b>" + currencyFormatter.format(sumTotalBill) + "</b></html>",
            "<html><b>" + currencyFormatter.format(sumTotalFund) + "</b></html>", "<html><b>" + currencyFormatter.format(sumTotalProfit) + "</b></html>"});
    }

    private DefaultTableModel createModelProductRP() {
        DefaultTableModel model = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        jTable_productReport.setModel(model);
        model.addColumn("Tên sản phẩm");
        model.addColumn("Tổng số lượng bán");
        return model;
    }

    private void createTblDataProductRP(Date dateFrom, Date dateTo) {
        DefaultTableModel model = createModelProductRP();
        List<ProductReport> list = controller.ReportController.GetListProductRPByDay(dateFrom, dateTo);
        for (ProductReport pr : list) {
            model.addRow(new Object[]{pr.getProductName(), pr.getProductSold()});
        }
    }

    private void createPie3DChart() {
        Map<String, Integer> fullListMapDescBySold = controller.ReportController.getTopListProductSold(oldestDayFromDB, new Date(System.currentTimeMillis()));

        pieData = new DefaultPieDataset();
        pie3DChart = ChartFactory.createPieChart3D("", pieData);
        pie3DChart.setBackgroundPaint(new Color(247, 249, 250));
        pie3DChart.getPlot().setBackgroundPaint(new Color(247, 249, 250));
        pie3DChart.getPlot().setOutlineVisible(false);
//        pie3DChart.getLegend().setBackgroundPaint(new Color(247, 249, 250));
        pie3DChart.getLegend().setVisible(false);
        pie3DChart.getPlot().setForegroundAlpha(0.50f);
        pie3DChartPanel = new ChartPanel(pie3DChart);
        pie3DChartPanel.setMouseWheelEnabled(true);
        pnl_pie3DChart.add(pie3DChartPanel);
        pnl_pie3DChart.validate();
        getDataToThePie(fullListMapDescBySold);
    }

    private void getDataToThePie(Map<String, Integer> fullListMapDescBySold) {
        pieData.clear();
        PiePlot plot = (PiePlot) pie3DChart.getPlot();
        String[] plotName = new String[5];
        int[] plotValue = new int[5];
        int limit5 = 0;
        int percent100 = 0;
        Set<Entry<String, Integer>> entries = fullListMapDescBySold.entrySet();
        for (Entry<String, Integer> entry : entries) {
            if (limit5 < 5) {
                plotName[limit5] = entry.getKey();
                plotValue[limit5] = entry.getValue();
                percent100 += entry.getValue();
                limit5++;
            } else {
                break;
            }
        }
        plotValue[0] = (plotValue[0] * 100) / percent100;
        plotValue[1] = (plotValue[1] * 100) / percent100;
        plotValue[2] = (plotValue[2] * 100) / percent100;
        plotValue[3] = (plotValue[3] * 100) / percent100;
        plotValue[4] = (plotValue[4] * 100) / percent100;
        pieData.setValue(plotName[0], plotValue[0]);
        pieData.setValue(plotName[1], plotValue[1]);
        pieData.setValue(plotName[2], plotValue[2]);
        pieData.setValue(plotName[3], plotValue[3]);
        pieData.setValue(plotName[4], plotValue[4]);
        plot.setSectionPaint(plotName[0], new Color(251, 214, 79));
        plot.setSectionPaint(plotName[1], new Color(239, 134, 77));
        plot.setSectionPaint(plotName[2], new Color(106, 213, 161));
        plot.setSectionPaint(plotName[3], new Color(129, 123, 228));
        plot.setSectionPaint(plotName[4], new Color(76, 144, 209));
    }

    private void createBarChart() {
        BarChart = ChartFactory.createBarChart("", "", "Triệu (VNĐ)", barChartData);
        BarChart.setBackgroundPaint(new Color(247, 249, 250));
        BarChart.getPlot().setBackgroundPaint(new Color(34, 41, 50));
        BarChart.getPlot().setOutlineVisible(false);
        BarChart.getLegend().setBackgroundPaint(new Color(247, 249, 250));
        CategoryPlot categoryPlot = BarChart.getCategoryPlot();

        BarRenderer renderer = (BarRenderer) categoryPlot.getRenderer();
        renderer.setSeriesPaint(0, new Color(85, 85, 255));
        renderer.setSeriesPaint(1, new Color(255, 85, 85));
        renderer.setSeriesPaint(2, new Color(85, 255, 85));

        CategoryAxis categoryaxis = categoryPlot.getDomainAxis();
        categoryaxis.setCategoryMargin(0.3);
        ChartPanel barChartPanel = new ChartPanel(BarChart);
        barChartPanel.setMouseWheelEnabled(true);
        pnl_bar3DChart.add(barChartPanel);
        pnl_bar3DChart.validate();
        getDataToBarCategoryPlot();
        BarChart.getCategoryPlot().setDataset(barChartData);
    }

    private void getDataToBarCategoryPlot() {
        barChartData = new DefaultCategoryDataset();
        int totalRow = jTable_revenueReport.getModel().getRowCount();
        long iTotalBill;
        long iTotalFund;
        long iTotalProfit;
        String strTotalBill;
        String strTotalFund;
        String strTotalProfit;
        for (int i = 0; i < totalRow - 1; i++) {
            strTotalBill = jTable_revenueReport.getModel().getValueAt(i, 1).toString().replaceAll(",", "");
            strTotalFund = jTable_revenueReport.getModel().getValueAt(i, 2).toString().replaceAll(",", "");
            strTotalProfit = jTable_revenueReport.getModel().getValueAt(i, 3).toString().replaceAll(",", "");
            iTotalBill = Integer.valueOf(strTotalBill.substring(0, strTotalBill.length() - 6));//rút gọn đơn vị (triệu vnđ)
            iTotalFund = Integer.valueOf(strTotalFund.substring(0, strTotalFund.length() - 6));
            iTotalProfit = Integer.valueOf(strTotalProfit.substring(0, strTotalProfit.length() - 6));
            int month = i + 1;
            barChartData.setValue(iTotalBill, "Tổng thu", "" + month);
            barChartData.setValue(iTotalFund, "Tổng vốn", "" + month);
            barChartData.setValue(iTotalProfit, "Tổng lãi", "" + month);
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

        sidebar = new javax.swing.JPanel();
        jLabelImgIcon = new javax.swing.JLabel();
        lbl_revenueRP = new javax.swing.JLabel();
        lbl_productRP = new javax.swing.JLabel();
        lbl_out = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jLabelLogo = new javax.swing.JLabel();
        header = new javax.swing.JPanel();
        jPanel_clomin = new javax.swing.JPanel();
        jLabel_mini = new javax.swing.JLabel();
        jLabel_close = new javax.swing.JLabel();
        body = new javax.swing.JPanel();
        container = new javax.swing.JPanel();
        body_revenueReport = new javax.swing.JPanel();
        jLabel_titleRevenue = new javax.swing.JLabel();
        pnl_bar3DChart = new javax.swing.JPanel();
        cb_yearPicker = new javax.swing.JComboBox<>();
        body_productReport = new javax.swing.JPanel();
        jLabel_titleProduct = new javax.swing.JLabel();
        pnl_pie3DChart = new javax.swing.JPanel();
        controlFooter = new javax.swing.JPanel();
        dateChooserCombo1 = new datechooser.beans.DateChooserCombo();
        jLabelDateFrom = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        dateChooserCombo2 = new datechooser.beans.DateChooserCombo();
        jLabelDateTo = new javax.swing.JLabel();
        jCheckBox1 = new javax.swing.JCheckBox();
        footer = new javax.swing.JPanel();
        revenueReportTbl = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable_revenueReport = new javax.swing.JTable();
        productReportTbl = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTable_productReport = new javax.swing.JTable();

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

        jLabelImgIcon.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabelImgIcon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/view/images/speech_bubble_25px.png"))); // NOI18N

        lbl_revenueRP.setBackground(new java.awt.Color(48, 201, 235));
        lbl_revenueRP.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lbl_revenueRP.setForeground(new java.awt.Color(166, 166, 166));
        lbl_revenueRP.setIcon(new javax.swing.ImageIcon(getClass().getResource("/view/images/icons8-sales-performance-18.png"))); // NOI18N
        lbl_revenueRP.setText("Thống kê doanh thu");
        lbl_revenueRP.setToolTipText("");
        lbl_revenueRP.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lbl_revenueRPMouseClicked(evt);
            }
        });

        lbl_productRP.setBackground(new java.awt.Color(48, 201, 235));
        lbl_productRP.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lbl_productRP.setForeground(new java.awt.Color(166, 166, 166));
        lbl_productRP.setIcon(new javax.swing.ImageIcon(getClass().getResource("/view/images/icons8-sales-performance-18.png"))); // NOI18N
        lbl_productRP.setText("Thống kê sản phẩm");
        lbl_productRP.setToolTipText("");
        lbl_productRP.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lbl_productRPMouseClicked(evt);
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
        });

        jPanel1.setBackground(new java.awt.Color(22, 27, 33));

        jLabelLogo.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jLabelLogo.setForeground(new java.awt.Color(139, 137, 217));
        jLabelLogo.setText("Sales Management ©");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(53, 53, 53)
                .addComponent(jLabelLogo)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(27, Short.MAX_VALUE)
                .addComponent(jLabelLogo)
                .addGap(24, 24, 24))
        );

        javax.swing.GroupLayout sidebarLayout = new javax.swing.GroupLayout(sidebar);
        sidebar.setLayout(sidebarLayout);
        sidebarLayout.setHorizontalGroup(
            sidebarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, sidebarLayout.createSequentialGroup()
                .addContainerGap(35, Short.MAX_VALUE)
                .addGroup(sidebarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, sidebarLayout.createSequentialGroup()
                        .addComponent(jLabelImgIcon, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(92, 92, 92))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, sidebarLayout.createSequentialGroup()
                        .addGroup(sidebarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(lbl_productRP, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lbl_out, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lbl_revenueRP, javax.swing.GroupLayout.PREFERRED_SIZE, 186, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(30, 30, 30))))
        );
        sidebarLayout.setVerticalGroup(
            sidebarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(sidebarLayout.createSequentialGroup()
                .addGap(56, 56, 56)
                .addComponent(jLabelImgIcon, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(59, 59, 59)
                .addComponent(lbl_revenueRP, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lbl_productRP, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lbl_out, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 351, Short.MAX_VALUE)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        header.setBackground(new java.awt.Color(34, 41, 50));

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

        javax.swing.GroupLayout headerLayout = new javax.swing.GroupLayout(header);
        header.setLayout(headerLayout);
        headerLayout.setHorizontalGroup(
            headerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(headerLayout.createSequentialGroup()
                .addGap(702, 702, 702)
                .addComponent(jPanel_clomin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        headerLayout.setVerticalGroup(
            headerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel_clomin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        body.setBackground(new java.awt.Color(247, 249, 250));
        body.setPreferredSize(new java.awt.Dimension(782, 358));

        container.setBackground(new java.awt.Color(247, 249, 250));
        container.setPreferredSize(new java.awt.Dimension(782, 252));
        container.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                containerMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                containerMouseExited(evt);
            }
        });
        container.setLayout(new java.awt.CardLayout());

        body_revenueReport.setBackground(new java.awt.Color(247, 249, 250));
        body_revenueReport.setPreferredSize(new java.awt.Dimension(782, 252));
        body_revenueReport.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                body_revenueReportMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                body_revenueReportMouseExited(evt);
            }
        });

        jLabel_titleRevenue.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        jLabel_titleRevenue.setForeground(new java.awt.Color(139, 137, 217));
        jLabel_titleRevenue.setText("Biểu đồ thống kê doanh thu các tháng trong năm :");

        pnl_bar3DChart.setBackground(new java.awt.Color(247, 249, 250));
        pnl_bar3DChart.setPreferredSize(new java.awt.Dimension(782, 252));
        pnl_bar3DChart.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                pnl_bar3DChartMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                pnl_bar3DChartMouseExited(evt);
            }
        });
        pnl_bar3DChart.setLayout(new javax.swing.BoxLayout(pnl_bar3DChart, javax.swing.BoxLayout.LINE_AXIS));

        cb_yearPicker.setBackground(new java.awt.Color(247, 249, 250));
        cb_yearPicker.setFont(new java.awt.Font("Dialog", 2, 12)); // NOI18N
        cb_yearPicker.setForeground(new java.awt.Color(139, 137, 217));
        cb_yearPicker.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(139, 137, 217)));
        cb_yearPicker.setPreferredSize(new java.awt.Dimension(250, 30));
        cb_yearPicker.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cb_yearPickerItemStateChanged(evt);
            }
        });

        javax.swing.GroupLayout body_revenueReportLayout = new javax.swing.GroupLayout(body_revenueReport);
        body_revenueReport.setLayout(body_revenueReportLayout);
        body_revenueReportLayout.setHorizontalGroup(
            body_revenueReportLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(body_revenueReportLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel_titleRevenue)
                .addGap(18, 18, 18)
                .addComponent(cb_yearPicker, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(pnl_bar3DChart, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        body_revenueReportLayout.setVerticalGroup(
            body_revenueReportLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(body_revenueReportLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(body_revenueReportLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel_titleRevenue)
                    .addComponent(cb_yearPicker, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pnl_bar3DChart, javax.swing.GroupLayout.DEFAULT_SIZE, 320, Short.MAX_VALUE)
                .addContainerGap())
        );

        container.add(body_revenueReport, "controlRevenue");

        body_productReport.setBackground(new java.awt.Color(247, 249, 250));

        jLabel_titleProduct.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        jLabel_titleProduct.setForeground(new java.awt.Color(139, 137, 217));
        jLabel_titleProduct.setText("Top 5 sản phẩm bán chạy");

        pnl_pie3DChart.setBackground(new java.awt.Color(247, 249, 250));
        pnl_pie3DChart.setLayout(new javax.swing.BoxLayout(pnl_pie3DChart, javax.swing.BoxLayout.LINE_AXIS));

        javax.swing.GroupLayout body_productReportLayout = new javax.swing.GroupLayout(body_productReport);
        body_productReport.setLayout(body_productReportLayout);
        body_productReportLayout.setHorizontalGroup(
            body_productReportLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(body_productReportLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(body_productReportLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(pnl_pie3DChart, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(body_productReportLayout.createSequentialGroup()
                        .addComponent(jLabel_titleProduct)
                        .addGap(0, 495, Short.MAX_VALUE)))
                .addContainerGap())
        );
        body_productReportLayout.setVerticalGroup(
            body_productReportLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(body_productReportLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel_titleProduct)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pnl_pie3DChart, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        container.add(body_productReport, "controlProduct");

        controlFooter.setBackground(new java.awt.Color(247, 249, 250));
        controlFooter.setPreferredSize(new java.awt.Dimension(782, 71));

        dateChooserCombo1.setCurrentView(new datechooser.view.appearance.AppearancesList("Light",
            new datechooser.view.appearance.ViewAppearance("custom",
                new datechooser.view.appearance.swing.SwingCellAppearance(new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 14),
                    new java.awt.Color(187, 187, 187),
                    new java.awt.Color(0, 0, 255),
                    false,
                    true,
                    new datechooser.view.appearance.swing.ButtonPainter()),
                new datechooser.view.appearance.swing.SwingCellAppearance(new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 14),
                    new java.awt.Color(187, 187, 187),
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
                    new java.awt.Color(187, 187, 187),
                    new java.awt.Color(0, 0, 255),
                    false,
                    true,
                    new datechooser.view.appearance.swing.LabelPainter()),
                new datechooser.view.appearance.swing.SwingCellAppearance(new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 14),
                    new java.awt.Color(187, 187, 187),
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
    dateChooserCombo1.setBehavior(datechooser.model.multiple.MultyModelBehavior.SELECT_SINGLE);
    dateChooserCombo1.addCommitListener(new datechooser.events.CommitListener() {
        public void onCommit(datechooser.events.CommitEvent evt) {
            dateChooserCombo1OnCommit(evt);
        }
    });

    jLabelDateFrom.setFont(new java.awt.Font("Segoe UI Light", 0, 14)); // NOI18N
    jLabelDateFrom.setForeground(new java.awt.Color(139, 137, 217));
    jLabelDateFrom.setText("Từ ngày :");

    dateChooserCombo2.setCurrentView(new datechooser.view.appearance.AppearancesList("Light",
        new datechooser.view.appearance.ViewAppearance("custom",
            new datechooser.view.appearance.swing.SwingCellAppearance(new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 14),
                new java.awt.Color(187, 187, 187),
                new java.awt.Color(0, 0, 255),
                false,
                true,
                new datechooser.view.appearance.swing.ButtonPainter()),
            new datechooser.view.appearance.swing.SwingCellAppearance(new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 14),
                new java.awt.Color(187, 187, 187),
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
                new java.awt.Color(187, 187, 187),
                new java.awt.Color(0, 0, 255),
                false,
                true,
                new datechooser.view.appearance.swing.LabelPainter()),
            new datechooser.view.appearance.swing.SwingCellAppearance(new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 14),
                new java.awt.Color(187, 187, 187),
                new java.awt.Color(255, 0, 0),
                false,
                false,
                new datechooser.view.appearance.swing.ButtonPainter()),
            (datechooser.view.BackRenderer)null,
            false,
            true)));
dateChooserCombo2.setCalendarBackground(new java.awt.Color(56, 53, 140));
dateChooserCombo2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(139, 137, 217), 1));
dateChooserCombo2.setFieldFont(new java.awt.Font("SansSerif", java.awt.Font.PLAIN, 12));
dateChooserCombo2.setLocale(new java.util.Locale("vi", "", ""));
dateChooserCombo2.setNavigateFont(new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 11));
dateChooserCombo2.setBehavior(datechooser.model.multiple.MultyModelBehavior.SELECT_SINGLE);
dateChooserCombo2.addCommitListener(new datechooser.events.CommitListener() {
    public void onCommit(datechooser.events.CommitEvent evt) {
        dateChooserCombo2OnCommit(evt);
    }
    });

    jLabelDateTo.setFont(new java.awt.Font("Segoe UI Light", 0, 14)); // NOI18N
    jLabelDateTo.setForeground(new java.awt.Color(139, 137, 217));
    jLabelDateTo.setText("Đến ngày :");

    jCheckBox1.setBackground(new java.awt.Color(247, 249, 250));
    jCheckBox1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
    jCheckBox1.setForeground(new java.awt.Color(139, 137, 217));
    jCheckBox1.setText("Tất cả");
    jCheckBox1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(139, 137, 217)));
    jCheckBox1.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            jCheckBox1ActionPerformed(evt);
        }
    });

    javax.swing.GroupLayout controlFooterLayout = new javax.swing.GroupLayout(controlFooter);
    controlFooter.setLayout(controlFooterLayout);
    controlFooterLayout.setHorizontalGroup(
        controlFooterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(controlFooterLayout.createSequentialGroup()
            .addContainerGap()
            .addGroup(controlFooterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jSeparator1)
                .addGroup(controlFooterLayout.createSequentialGroup()
                    .addComponent(jLabelDateFrom)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(dateChooserCombo1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(18, 18, 18)
                    .addComponent(jLabelDateTo)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(dateChooserCombo2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(18, 18, 18)
                    .addComponent(jCheckBox1)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 240, Short.MAX_VALUE)))
            .addContainerGap())
    );
    controlFooterLayout.setVerticalGroup(
        controlFooterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(controlFooterLayout.createSequentialGroup()
            .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addGroup(controlFooterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                .addComponent(jCheckBox1)
                .addComponent(dateChooserCombo2, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(jLabelDateFrom, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(controlFooterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(dateChooserCombo1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 34, Short.MAX_VALUE)
                    .addComponent(jLabelDateTo, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
            .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
    );

    javax.swing.GroupLayout bodyLayout = new javax.swing.GroupLayout(body);
    body.setLayout(bodyLayout);
    bodyLayout.setHorizontalGroup(
        bodyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addComponent(container, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        .addComponent(controlFooter, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
    );
    bodyLayout.setVerticalGroup(
        bodyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(bodyLayout.createSequentialGroup()
            .addComponent(container, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(controlFooter, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE))
    );

    footer.setBackground(new java.awt.Color(247, 249, 250));
    footer.setPreferredSize(new java.awt.Dimension(782, 400));
    footer.setLayout(new java.awt.CardLayout());

    revenueReportTbl.setBackground(new java.awt.Color(247, 249, 250));
    revenueReportTbl.setPreferredSize(new java.awt.Dimension(782, 285));
    revenueReportTbl.setLayout(new javax.swing.BoxLayout(revenueReportTbl, javax.swing.BoxLayout.LINE_AXIS));

    jScrollPane1.setBackground(new java.awt.Color(247, 249, 250));
    jScrollPane1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(139, 137, 217)));

    jTable_revenueReport.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
    jTable_revenueReport.setForeground(new java.awt.Color(51, 51, 51));
    jTable_revenueReport.setModel(new javax.swing.table.DefaultTableModel(
        new Object [][] {

        },
        new String [] {
            "Ngày lập hóa đơn", "Tổng thu", "Tổng vốn", "Tổng lãi"
        }
    ) {
        Class[] types = new Class [] {
            java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
        };
        boolean[] canEdit = new boolean [] {
            false, false, false, false
        };

        public Class getColumnClass(int columnIndex) {
            return types [columnIndex];
        }

        public boolean isCellEditable(int rowIndex, int columnIndex) {
            return canEdit [columnIndex];
        }
    });
    jTable_revenueReport.setGridColor(new java.awt.Color(139, 137, 217));
    jTable_revenueReport.setRowHeight(20);
    jTable_revenueReport.setSelectionBackground(new java.awt.Color(139, 137, 217));
    jScrollPane1.setViewportView(jTable_revenueReport);

    revenueReportTbl.add(jScrollPane1);

    footer.add(revenueReportTbl, "footerRevenue");

    productReportTbl.setBackground(new java.awt.Color(247, 249, 250));
    productReportTbl.setLayout(new javax.swing.BoxLayout(productReportTbl, javax.swing.BoxLayout.LINE_AXIS));

    jScrollPane3.setBackground(new java.awt.Color(247, 249, 250));
    jScrollPane3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(139, 137, 217)));

    jTable_productReport.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
    jTable_productReport.setForeground(new java.awt.Color(51, 51, 51));
    jTable_productReport.setModel(new javax.swing.table.DefaultTableModel(
        new Object [][] {

        },
        new String [] {
            "Tên sản phẩm", "Tổng số lượng bán"
        }
    ) {
        Class[] types = new Class [] {
            java.lang.String.class, java.lang.String.class
        };
        boolean[] canEdit = new boolean [] {
            false, false
        };

        public Class getColumnClass(int columnIndex) {
            return types [columnIndex];
        }

        public boolean isCellEditable(int rowIndex, int columnIndex) {
            return canEdit [columnIndex];
        }
    });
    jTable_productReport.setGridColor(new java.awt.Color(139, 137, 217));
    jTable_productReport.setRowHeight(20);
    jTable_productReport.setSelectionBackground(new java.awt.Color(139, 137, 217));
    jScrollPane3.setViewportView(jTable_productReport);

    productReportTbl.add(jScrollPane3);

    footer.add(productReportTbl, "footerProduct");

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
            .addComponent(body, javax.swing.GroupLayout.DEFAULT_SIZE, 424, Short.MAX_VALUE)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(footer, javax.swing.GroupLayout.PREFERRED_SIZE, 225, javax.swing.GroupLayout.PREFERRED_SIZE))
        .addComponent(sidebar, javax.swing.GroupLayout.DEFAULT_SIZE, 686, Short.MAX_VALUE)
    );

    pack();
    setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void body_revenueReportMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_body_revenueReportMouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_body_revenueReportMouseEntered

    private void body_revenueReportMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_body_revenueReportMouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_body_revenueReportMouseExited


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

    private void containerMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_containerMouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_containerMouseEntered

    private void containerMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_containerMouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_containerMouseExited

    private void lbl_productRPMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbl_productRPMouseClicked
        resetLblColor(lbl_revenueRP);
        setLblColor(lbl_productRP);
        CardLayout cardLayout = (CardLayout) container.getLayout();
        cardLayout.show(container, "controlProduct");
        cardLayout = (CardLayout) footer.getLayout();
        cardLayout.show(footer, "footerProduct");
        dateChooserCombo1.setEnabled(false);
        dateChooserCombo2.setEnabled(false);
        jCheckBox1.setSelected(true);
        createTblDataProductRP(oldestDayFromDB, new Date(System.currentTimeMillis()));
        Map<String, Integer> fullListMapDescBySold = controller.ReportController.getTopListProductSold(oldestDayFromDB, new Date(System.currentTimeMillis()));
        getDataToThePie(fullListMapDescBySold);
    }//GEN-LAST:event_lbl_productRPMouseClicked

    public void getMouseLocation(java.awt.event.MouseEvent evt, JDialog jdialog) {
        xx = evt.getX();
        xy = evt.getY();
    }

    public void setFrameLocation(java.awt.event.MouseEvent evt, JDialog jdialog) {
        int x = evt.getXOnScreen();
        int y = evt.getYOnScreen();
        jdialog.setLocation(x - xx, y - xy);
    }

    private void lbl_revenueRPMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbl_revenueRPMouseClicked
        setLblColor(lbl_revenueRP);
        resetLblColor(lbl_productRP);
        CardLayout cardLayout = (CardLayout) container.getLayout();
        cardLayout.show(container, "controlRevenue");
        cardLayout = (CardLayout) footer.getLayout();
        cardLayout.show(footer, "footerRevenue");
        dateChooserCombo1.setEnabled(true);
        dateChooserCombo2.setEnabled(true);
        jCheckBox1.setSelected(false);
        getYearAndLoadData();
    }//GEN-LAST:event_lbl_revenueRPMouseClicked

    private void pnl_bar3DChartMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnl_bar3DChartMouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_pnl_bar3DChartMouseEntered

    private void pnl_bar3DChartMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnl_bar3DChartMouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_pnl_bar3DChartMouseExited

    private void getYearAndLoadData() {
        String chosenYear = String.valueOf(cb_yearPicker.getSelectedItem());
        if (chosenYear != null) {
            int chosenYearInt = Integer.parseInt(chosenYear);
            Calendar calendarFrom = Calendar.getInstance();
            calendarFrom.set(chosenYearInt, 00, 01);//months in Calendar starts with index 0 (January)
            dateChooserCombo1.setSelectedDate(calendarFrom);
            Calendar calendarTo = Calendar.getInstance();
            if (chosenYearInt == Calendar.getInstance().get(Calendar.YEAR)) {
                dateChooserCombo2.setSelectedDate(calendarTo);//current date
            } else {
                calendarTo.set(chosenYearInt, 11, 31);//11 is December
                dateChooserCombo2.setSelectedDate(calendarTo);
            }
            createTblDataRevenueRPByMonths(new Date(calendarFrom.getTimeInMillis()), new Date(calendarTo.getTimeInMillis()));
            jTable_revenueReport.getTableHeader().getColumnModel().getColumn(0).setHeaderValue("Tháng trong năm");
        }
    }

    private void jCheckBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBox1ActionPerformed
        Date dateFrom;
        Date dateTo;
        if (jCheckBox1.isSelected()) {
            dateChooserCombo1.setEnabled(false);
            dateChooserCombo2.setEnabled(false);
            dateFrom = oldestDayFromDB;
            dateTo = new Date(System.currentTimeMillis());
            if (body_revenueReport.isVisible()) {
                createTblDataRevenueRP(dateFrom, dateTo);
            }
            if (body_productReport.isVisible()) {
                createTblDataProductRP(dateFrom, dateTo);
                Map<String, Integer> fullListMapDescBySold = controller.ReportController.getTopListProductSold(dateFrom, dateTo);
                getDataToThePie(fullListMapDescBySold);
            }
        } else {
            dateChooserCombo1.setEnabled(true);
            dateChooserCombo2.setEnabled(true);
            dateFrom = new Date(dateChooserCombo1.getSelectedDate().getTimeInMillis());
            dateTo = new Date(dateChooserCombo2.getSelectedDate().getTimeInMillis());
            if (body_revenueReport.isVisible()) {
                createTblDataRevenueRP(dateFrom, dateTo);
            }
            if (body_productReport.isVisible()) {
                createTblDataProductRP(dateFrom, dateTo);
                Map<String, Integer> fullListMapDescBySold = controller.ReportController.getTopListProductSold(dateFrom, dateTo);
                getDataToThePie(fullListMapDescBySold);
            }
        }
        jTable_revenueReport.getTableHeader().getColumnModel().getColumn(0).setHeaderValue("Ngày lập hóa đơn");
    }//GEN-LAST:event_jCheckBox1ActionPerformed

    private void cb_yearPickerItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cb_yearPickerItemStateChanged
        blockDateChooserListenerAtTheMoment = true;
        getYearAndLoadData();
        blockDateChooserListenerAtTheMoment = false;
        if (barChartData != null) {
            getDataToBarCategoryPlot();
            BarChart.getCategoryPlot().setDataset(barChartData);
        }
    }//GEN-LAST:event_cb_yearPickerItemStateChanged

    private void dateChooserCombo2OnCommit(datechooser.events.CommitEvent evt) {//GEN-FIRST:event_dateChooserCombo2OnCommit
        Date dateFrom = new Date(dateChooserCombo1.getSelectedDate().getTimeInMillis());
        Date dateTo = new Date(dateChooserCombo2.getSelectedDate().getTimeInMillis());
        if (dateFrom.compareTo(dateTo) <= 0) {
            if (body_revenueReport.isVisible()) {
                createTblDataRevenueRP(dateFrom, dateTo);
            }
            if (body_productReport.isVisible()) {
                createTblDataProductRP(dateFrom, dateTo);
                Map<String, Integer> fullListMapDescBySold = controller.ReportController.getTopListProductSold(dateFrom, dateTo);
                getDataToThePie(fullListMapDescBySold);
            }
            jTable_revenueReport.getTableHeader().getColumnModel().getColumn(0).setHeaderValue("Ngày lập hóa đơn");
            jCheckBox1.setSelected(false);
            dateChooserCombo1.setEnabled(true);
            dateChooserCombo2.setEnabled(true);
        } else {
            if (blockDateChooserListenerAtTheMoment == false) {
                new view.Message("Ngày bắt đầu phải trước hoặc cùng ngày với ngày kết thúc", 2, 6).setVisible(true);
            }
        }
    }//GEN-LAST:event_dateChooserCombo2OnCommit

    private void dateChooserCombo1OnCommit(datechooser.events.CommitEvent evt) {//GEN-FIRST:event_dateChooserCombo1OnCommit
        Date dateFrom = new Date(dateChooserCombo1.getSelectedDate().getTimeInMillis());
        Date dateTo = new Date(dateChooserCombo2.getSelectedDate().getTimeInMillis());
        if (dateFrom.compareTo(dateTo) <= 0) {
            if (body_revenueReport.isVisible()) {
                createTblDataRevenueRP(dateFrom, dateTo);
            }
            if (body_productReport.isVisible()) {
                createTblDataProductRP(dateFrom, dateTo);
                Map<String, Integer> fullListMapDescBySold = controller.ReportController.getTopListProductSold(dateFrom, dateTo);
                getDataToThePie(fullListMapDescBySold);
            }
            jTable_revenueReport.getTableHeader().getColumnModel().getColumn(0).setHeaderValue("Ngày lập hóa đơn");
        } else {
            if (blockDateChooserListenerAtTheMoment == false) {
                new view.Message("Ngày bắt đầu phải trước hoặc cùng ngày với ngày kết thúc", 2, 6).setVisible(true);
            }
        }
    }//GEN-LAST:event_dateChooserCombo1OnCommit

    private void createTblDataRevenueRPByMonths(Date dateFrom, Date dateTo) {
        DefaultTableModel model = createModelRevenueRP();
        List<RevenueReport> list = controller.ReportController.GetListRevenueRPSumByMonths(dateFrom, dateTo);
        for (RevenueReport rev : list) {
            model.addRow(new Object[]{rev.getMonthsOfAYear(), currencyFormatter.format(rev.getTotalBill()),
                currencyFormatter.format(rev.getTotalFund()), currencyFormatter.format(rev.getTotalProfit())});
        }
        long sumTotalBill = 0;
        long sumTotalFund = 0;
        long sumTotalProfit = 0;
        //int range from -2,147,483,647 to 2,147,483,647. It will turn to negative value when int increment hit the range (Integer overflow). Use 'long' instead
        int totalRow = model.getRowCount();
        for (int i = 0; i < totalRow; i++) {
            sumTotalBill = sumTotalBill + Integer.valueOf(model.getValueAt(i, 1).toString().replaceAll(",", ""));
            sumTotalFund = sumTotalFund + Integer.valueOf(model.getValueAt(i, 2).toString().replaceAll(",", ""));
            sumTotalProfit = sumTotalProfit + Integer.valueOf(model.getValueAt(i, 3).toString().replaceAll(",", ""));
        }
        model.addRow(new Object[]{"<html><b>Tổng cộng</b></html>", "<html><b>" + currencyFormatter.format(sumTotalBill) + "</b></html>",
            "<html><b>" + currencyFormatter.format(sumTotalFund) + "</b></html>", "<html><b>" + currencyFormatter.format(sumTotalProfit) + "</b></html>"});
    }

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
        btn.setBackground(new Color(247, 249, 250));
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
            java.util.logging.Logger.getLogger(Report.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Report().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel body;
    private javax.swing.JPanel body_productReport;
    private javax.swing.JPanel body_revenueReport;
    public static javax.swing.JComboBox<String> cb_yearPicker;
    private javax.swing.JPanel container;
    private javax.swing.JPanel controlFooter;
    private datechooser.beans.DateChooserCombo dateChooserCombo1;
    private datechooser.beans.DateChooserCombo dateChooserCombo2;
    private javax.swing.JPanel footer;
    private javax.swing.JPanel header;
    private javax.swing.JCheckBox jCheckBox1;
    private javax.swing.JLabel jLabelDateFrom;
    private javax.swing.JLabel jLabelDateTo;
    private javax.swing.JLabel jLabelImgIcon;
    private javax.swing.JLabel jLabelLogo;
    private javax.swing.JLabel jLabel_close;
    private javax.swing.JLabel jLabel_mini;
    private javax.swing.JLabel jLabel_titleProduct;
    private javax.swing.JLabel jLabel_titleRevenue;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel_clomin;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JTable jTable_productReport;
    private javax.swing.JTable jTable_revenueReport;
    private javax.swing.JLabel lbl_out;
    private javax.swing.JLabel lbl_productRP;
    private javax.swing.JLabel lbl_revenueRP;
    private javax.swing.JPanel pnl_bar3DChart;
    private javax.swing.JPanel pnl_pie3DChart;
    private javax.swing.JPanel productReportTbl;
    private javax.swing.JPanel revenueReportTbl;
    private javax.swing.JPanel sidebar;
    // End of variables declaration//GEN-END:variables
}
