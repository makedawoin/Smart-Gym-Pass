import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class PassManager {

    
    public static boolean addPass(Pass pass) {
        String sql = "INSERT INTO passes " +
                "(passID, memberID, passType, startDate, endDate, cost, passStatus) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";

        try {
            Connection conn = DatabaseConnection.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql);

            pstmt.setString(1, pass.getPassID());
            pstmt.setString(2, pass.getMemberID());
            pstmt.setString(3, pass.getPassType());
            pstmt.setDate(4, java.sql.Date.valueOf(pass.getStartDate()));
            pstmt.setDate(5, java.sql.Date.valueOf(pass.getEndDate()));
            pstmt.setDouble(6, pass.getCost());
            pstmt.setString(7, pass.getPassStatus());

            int rowsAffected = pstmt.executeUpdate();
            pstmt.close();

            return rowsAffected > 0;

        } catch (SQLException e) {
            System.err.println("Error adding pass: " + e.getMessage());
            return false;
        }
    }
    public static Pass getPassByID(String passID) {
        String sql = "SELECT * FROM passes WHERE passID = ?";

        try {
            Connection conn = DatabaseConnection.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql);

            pstmt.setString(1, passID);

            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                Pass pass = createPassFromResultSet(rs);

                rs.close();
                pstmt.close();

                return pass;
            }

            rs.close();
            pstmt.close();

        } catch (SQLException e) {
            System.err.println("Error retrieving pass: " + e.getMessage());
        }

        return null;
    }

    public static Pass getMemberPass(String memberID) {
        String sql =
                "SELECT * FROM passes " +
                "WHERE memberID = ? " +
                "ORDER BY endDate DESC LIMIT 1";

        try {
            Connection conn = DatabaseConnection.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql);

            pstmt.setString(1, memberID);

            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                Pass pass = createPassFromResultSet(rs);

                rs.close();
                pstmt.close();

                return pass;
            }

            rs.close();
            pstmt.close();

        } catch (SQLException e) {
            System.err.println("Error retrieving member pass: " + e.getMessage());
        }

        return null;
    }

    public static List<Pass> getAllPasses() {
        List<Pass> passes = new ArrayList<>();

        String sql = "SELECT * FROM passes";

        try {
            Connection conn = DatabaseConnection.getConnection();
            Statement stmt = conn.createStatement();

            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                Pass pass = createPassFromResultSet(rs);
                passes.add(pass);
            }

            rs.close();
            stmt.close();

        } catch (SQLException e) {
            System.err.println("Error retrieving all passes: " + e.getMessage());
        }

        return passes;
    }
    public static boolean updatePass(Pass pass) {
        String sql =
                "UPDATE passes " +
                "SET passStatus = ?, endDate = ? " +
                "WHERE passID = ?";

        try {
            Connection conn = DatabaseConnection.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql);

            pstmt.setString(1, pass.getPassStatus());
            pstmt.setDate(2, java.sql.Date.valueOf(pass.getEndDate()));
            pstmt.setString(3, pass.getPassID());

            int rowsAffected = pstmt.executeUpdate();

            pstmt.close();

            return rowsAffected > 0;

        } catch (SQLException e) {
            System.err.println("Error updating pass: " + e.getMessage());
            return false;
        }
    }

    public static boolean deletePass(String passID) {
        String sql = "DELETE FROM passes WHERE passID = ?";

        try {
            Connection conn = DatabaseConnection.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql);

            pstmt.setString(1, passID);

            int rowsAffected = pstmt.executeUpdate();

            pstmt.close();

            return rowsAffected > 0;

        } catch (SQLException e) {
            System.err.println("Error deleting pass: " + e.getMessage());
            return false;
        }
    }

    private static Pass createPassFromResultSet(ResultSet rs)
            throws SQLException {

        String passType = rs.getString("passType");
        String passID = rs.getString("passID");
        String memberID = rs.getString("memberID");
        LocalDate startDate =
                rs.getDate("startDate").toLocalDate();

        Pass pass = null;

        switch (passType) {
            case "Monthly":
                pass = new MonthlyPass(
                        passID,
                        memberID,
                        startDate
                );
                break;

            case "Yearly":
                pass = new YearlyPass(
                        passID,
                        memberID,
                        startDate
                );
                break;

            case "Trial":
                pass = new TrialPass(
                        passID,
                        memberID,
                        startDate
                );
                break;
        }

        if (pass != null) {
            pass.setPassStatus(
                    rs.getString("passStatus")
            );
        }

        return pass;
    }

    // ===== COUNT ACTIVE PASSES =====
    public static int getActivePassesCount() {
        String sql =
                "SELECT COUNT(*) FROM passes " +
                "WHERE passStatus = 'active'";

        try {
            Connection conn = DatabaseConnection.getConnection();
            Statement stmt = conn.createStatement();

            ResultSet rs = stmt.executeQuery(sql);

            if (rs.next()) {
                int count = rs.getInt(1);

                rs.close();
                stmt.close();

                return count;
            }

            rs.close();
            stmt.close();

        } catch (SQLException e) {
            System.err.println(
                    "Error counting active passes: "
                            + e.getMessage()
            );
        }

        return 0;
    }
}