/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import static controller.MD5Encrypt.encryptPass;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import model.Account;

/**
 *
 * @author TechCare
 */
public class Accountcontroller {

    Connection con;

    public static List<Account> GetListAccount() {
        ArrayList<Account> List = new ArrayList<>();
        try (Connection connection = connectDB.connectSQLServer()) {
            String sql = "select * from tblAccount";
            try (Statement statement = connection.createStatement()) {
                ResultSet rs = statement.executeQuery(sql);
                while (rs.next()) {
                    Account account = new Account(rs.getInt("AccID"), rs.getString("Username"), rs.getString("Passwords"), rs.getInt("EmpID"), rs.getInt("PosID"));
                    List.add(account);
                }
                rs.close();
            }
        } catch (SQLException ex) {
            Logger.getLogger(Accountcontroller.class.getName()).log(Level.SEVERE, null, ex);
        }
        return List;
    }

    public static Account GetAccountByUserName(String Username) {
        Account account = null;
        try (Connection connection = connectDB.connectSQLServer()) {
            String sql = "select * from tblAccount where Username = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setString(1, Username);
                try (ResultSet rs = preparedStatement.executeQuery()) {
                    account = null;
                    if (rs.next()) {
                        account = new Account(rs.getInt("AccID"), rs.getString("Username"), rs.getString("Passwords"), rs.getInt("EmpID"), rs.getInt("PosID"));
                    }
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(Accountcontroller.class.getName()).log(Level.SEVERE, null, ex);
        }
        return account;
    }

    public static boolean checkExistUsername(String username) {
        boolean check = true;
        try (Connection connection = connectDB.connectSQLServer()) {
            String sql = "select * from tblAccount where Username = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setString(1, username);
                try (ResultSet rs = preparedStatement.executeQuery()) {
                    check = rs.next();
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(Accountcontroller.class.getName()).log(Level.SEVERE, null, ex);
        }
        return check;
    }

    public static void AddAcount(Account newaccount) {
        try (Connection connection = connectDB.connectSQLServer()) {
            String sql = "insert into tblAccount(Username,Passwords,EmpID,PosID) values (?,?,?,?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setString(1, newaccount.getUsername());
                String MD5Passwords = MD5Encrypt.encryptPass(newaccount.getPassword());
                preparedStatement.setString(2, MD5Passwords);
                preparedStatement.setInt(3, newaccount.getEmpID());
                preparedStatement.setInt(4, newaccount.getPosID());
                preparedStatement.executeUpdate();
            }
        } catch (SQLException ex) {
            Logger.getLogger(Accountcontroller.class.getName()).log(Level.SEVERE, null, ex);
            return;
        }
        new view.Message("Thêm tài khoản thành công", 1, 4).setVisible(true);
    }

    public static void RemoveAccountByUsername(String Username) {
        try (Connection connection = connectDB.connectSQLServer()) {
            String sql = "delete tblAccount where Username = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setString(1, Username);
                preparedStatement.executeUpdate();
            }
        } catch (SQLException ex) {
            Logger.getLogger(Accountcontroller.class.getName()).log(Level.SEVERE, null, ex);
            return;
        }
        new view.Message("Xóa tài khoản thành công", 1, 4).setVisible(true);
    }

    public static void sendEmail(String smtpServer, String to, String from, String password, String subject, String body) {

        try {
            Authenticator pa = null;

            Properties props = System.getProperties();
            props.put("mail.smtp.host", smtpServer);
            props.put("mail.smtp.port", "587");
            props.put("mail.smtp.starttls.enable", "true");// enable TLS (Transport Layer Security)
            final String user = from;
            final String pwd = password;
            if (user != null && pwd != null) {// these local variables to check if arguments are passed to the method are null
                props.put("mail.smtp.auth", "true");
                pa = new Authenticator() {
                    @Override
                    public PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(user, pwd);
                    }
                };
            }

            Session session = Session.getInstance(props, pa);

            Message msg = new MimeMessage(session);

            msg.setFrom(new InternetAddress(from));
            msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to, false));
            msg.setSubject(subject);
            msg.setText(body);
            msg.setHeader("Sales Management", "Admin");
            msg.setSentDate(new Date());
            msg.saveChanges();

            Transport.send(msg);
            new view.Message("Đã gửi Email xác nhận. Hãy kiểm tra hộp thư của bạn", 1, 2).setVisible(true);
        } catch (MessagingException ex) {
            Logger.getLogger(Accountcontroller.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static String randomAlphaNumeric(int count) {
        String ALPHA_NUMERIC_STRING = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        StringBuilder builder = new StringBuilder();
        while (count-- != 0) {
            int character = (int) (Math.random() * ALPHA_NUMERIC_STRING.length());
            builder.append(ALPHA_NUMERIC_STRING.charAt(character));
        }
        return builder.toString();
    }

    public static String GetUserNameByEmail(String email) {
        String userName = "";
        try (Connection connection = connectDB.connectSQLServer()) {
            int empID = 0;
            String sql = "select EmpID from tblEmployee where Email = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setString(1, email);
                try (ResultSet rs = preparedStatement.executeQuery()) {
                    if (rs.next()) {
                        empID = rs.getInt("EmpID");
                    }
                }
            }
            sql = "select Username from tblAccount where EmpID = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setInt(1, empID);
                try (ResultSet rs = preparedStatement.executeQuery()) {
                    if (rs.next()) {
                        userName = rs.getString("Username");
                    }
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(Accountcontroller.class.getName()).log(Level.SEVERE, null, ex);
        }
        return userName;
    }

    public static Account getAccByID(int ID) {
        Account acc = null;
        try {
            Connection connection = connectDB.connectSQLServer();
            String sql = "select * from tblAccount where AccID=?";
            PreparedStatement ps = connection.prepareCall(sql);
            ps.setInt(1, ID);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                acc = new Account(rs.getInt("AccId"), rs.getString("Username"), rs.getString("Passwords"), rs.getInt("EmpID"), rs.getInt("PosID"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(EmployeeController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return acc;
    }

    public static void EditAccount(Account acc) {
        try (Connection connection = connectDB.connectSQLServer()) {
            String sql = "update tblAccount set Username = ? , Passwords = ? , EmpID = ? , PosID = ? where AccID = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setString(1, acc.getUsername());
                String MD5Passwords = MD5Encrypt.encryptPass(acc.getPassword());
                preparedStatement.setString(2, MD5Passwords);
                preparedStatement.setInt(3, acc.getEmpID());
                preparedStatement.setInt(4, acc.getPosID());
                preparedStatement.setInt(5, acc.getAccID());
                preparedStatement.executeUpdate();
            }
        } catch (SQLException ex) {
            Logger.getLogger(Accountcontroller.class.getName()).log(Level.SEVERE, null, ex);
            return;
        }
        new view.Message("Cập nhật dữ liệu thành công", 1, 4).setVisible(true);
    }

    public static void RemoveAccountByID(int AccID) {
        try (Connection connection = connectDB.connectSQLServer()) {
            String sql = "delete tblAccount  where AccID = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setInt(1, AccID);
                preparedStatement.executeUpdate();
            }
        } catch (SQLException ex) {
            Logger.getLogger(Accountcontroller.class.getName()).log(Level.SEVERE, null, ex);
            return;
        }
        new view.Message("Xóa dữ liệu thành công", 1, 4).setVisible(true);
    }

    public static void ChangePasswords(String Username, String NewPasswords) {
        try (Connection connection = connectDB.connectSQLServer()) {
            String sql = "update tblAccount set Passwords = ? where Username = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                String MD5Passwords = MD5Encrypt.encryptPass(NewPasswords);
                preparedStatement.setString(1, MD5Passwords);
                preparedStatement.setString(2, Username);
                preparedStatement.executeUpdate();
            }
        } catch (SQLException ex) {
            Logger.getLogger(Accountcontroller.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static boolean checkPassWord(String currentPW) {
        boolean check = true;
        try (Connection connection = connectDB.connectSQLServer()) {
            String sql = "select * from tblAccount where Passwords = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setString(1, encryptPass(currentPW));
                try (ResultSet rs = preparedStatement.executeQuery()) {
                    check = rs.next();
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(Accountcontroller.class.getName()).log(Level.SEVERE, null, ex);
        }
        return check;
    }

}
