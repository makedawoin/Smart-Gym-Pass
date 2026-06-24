import Payment.Payment;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PaymentProcessor {

    public static boolean processPayment(Payment payment) {
        String sql = "INSERT INTO payments (paymentID, memberID, amount, paymentDate, paymentType, paymentStatus, paymentMethod) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";

        try {
            Connection conn = DatabaseConnection.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql);

            pstmt.setString(1, payment.getPaymentID());
            pstmt.setString(2, payment.getMemberID());
            pstmt.setString(3, payment.getAmount());
            pstmt.setTimestamp(4, java.sql.Timestamp.valueOf(payment.getPaymentDate()));
            pstmt.setString(5, payment.getPaymentType());
            pstmt.setString(6, payment.getPaymentStatus());
            pstmt.setString(7, payment.getPaymentMethod());

            int rowsAffected = pstmt.executeUpdate();
            pstmt.close();

            if (rowsAffected > 0) {
                System.out.println("✓ Payment processed: " + payment.getPaymentID());
            }

            return rowsAffected > 0;

        } catch (SQLException e) {
            System.err.println("✗ Error processing payment: " + e.getMessage());
            return false;
        }
    }

    public static Payment getPaymentByID(String paymentID) {
        String sql = "SELECT * FROM payments WHERE paymentID = ?";

        try {
            Connection conn = DatabaseConnection.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, paymentID);

            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                Payment payment = new Payment(
                        rs.getString("paymentID"),
                        rs.getString("memberID"),
                        rs.getDouble("amount"),
                        rs.getString("paymentType"),
                        rs.getString("paymentMethod")
                );
                payment.setPaymentStatus(rs.getString("paymentStatus"));

                rs.close();
                pstmt.close();
                return payment;
            }

        } catch (SQLException e) {
            System.err.println("✗ Error fetching payment: " + e.getMessage());
        }

        return null;
    }

    public static List<Payment> getAllPayments() {
        List<Payment> payments = new ArrayList<>();
        String sql = "SELECT * FROM payments ORDER BY paymentDate DESC";

        try {
            Connection conn = DatabaseConnection.getConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                Payment payment = new Payment(
                        rs.getString("paymentID"),
                        rs.getString("memberID"),
                        rs.getDouble("amount"),
                        rs.getString("paymentType"),
                        rs.getString("paymentMethod")
                );
                payment.setPaymentStatus(rs.getString("paymentStatus"));
                payments.add(payment);
            }

            rs.close();
            stmt.close();

        } catch (SQLException e) {
            System.err.println("✗ Error retrieving all payments: " + e.getMessage());
        }

        return payments;
    }

    public static boolean updatePaymentStatus(String paymentID, String status) {
        String sql = "UPDATE payments SET paymentStatus=? WHERE paymentID=?";

        try {
            Connection conn = DatabaseConnection.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql);

            pstmt.setString(1, status);
            pstmt.setString(2, paymentID);

            int rowsAffected = pstmt.executeUpdate();
            pstmt.close();

            return rowsAffected > 0;
} catch (SQLException e) {
            System.err.println("✗ Error updating payment status: " + e.getMessage());
            return false;
        }
    }

    public static boolean deletePayment(String paymentID) {
        String sql = "DELETE FROM payments WHERE paymentID = ?";

        try {
            Connection conn = DatabaseConnection.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, paymentID);

            int rowsAffected = pstmt.executeUpdate();
            pstmt.close();

            return rowsAffected > 0;

        } catch (SQLException e) {
            System.err.println("✗ Error deleting payment: " + e.getMessage());
            return false;
        }
    }

    public static double getTotalRevenue() {
        String sql = "SELECT SUM(amount) FROM payments WHERE paymentStatus = 'completed'";

        try {
            Connection conn = DatabaseConnection.getConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            if (rs.next()) {
                double total = rs.getDouble(1);
                rs.close();
                stmt.close();
                return total;
            }

        } catch (SQLException e) {
            System.err.println("✗ Error calculating revenue: " + e.getMessage());
        }

        return 0.0;
    }

    public static double getRevenueByType(String paymentType) {
        String sql = "SELECT SUM(amount) FROM payments WHERE paymentType = ? AND paymentStatus = 'completed'";

        try {
            Connection conn = DatabaseConnection.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, paymentType);

            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                double total = rs.getDouble(1);
                rs.close();
                pstmt.close();
                return total;
            }

        } catch (SQLException e) {
            System.err.println("✗ Error calculating revenue by type: " + e.getMessage());
        }

        return 0.0;
    }
}
