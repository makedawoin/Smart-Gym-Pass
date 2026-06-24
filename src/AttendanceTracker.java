import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AttendanceTracker {

    public static boolean recordCheckIn(CheckIn checkIn) {
        String sql = "INSERT INTO checkins (checkInID, memberID, checkInTime, equipmentUsed) " +
                "VALUES (?, ?, ?, ?)";

        try {
            Connection conn = DatabaseConnection.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql);

            pstmt.setString(1, checkIn.getCheckInID());
            pstmt.setString(2, checkIn.getMemberID());
            pstmt.setTimestamp(3, java.sql.Timestamp.valueOf(checkIn.getCheckInTime()));
            pstmt.setString(4, checkIn.getEquipmentUsed());

            int rowsAffected = pstmt.executeUpdate();
            pstmt.close();

            if (rowsAffected > 0) {
                System.out.println(" Check-in recorded: " + checkIn.getCheckInID());
            }

            return rowsAffected > 0;

        } catch (SQLException e) {
            System.err.println(" Error recording check-in: " + e.getMessage());
            return false;
        }
    }

    public static boolean recordCheckOut(String checkInID, CheckIn checkIn) {
        String sql = "UPDATE checkins SET checkOutTime=?, durationInMinutes=? WHERE checkInID=?";

        try {
            checkIn.checkOut();

            Connection conn = DatabaseConnection.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql);

            pstmt.setTimestamp(1, java.sql.Timestamp.valueOf(checkIn.getCheckOutTime()));
            pstmt.setDouble(2, checkIn.getDurationInMinutes());
            pstmt.setString(3, checkInID);

            int rowsAffected = pstmt.executeUpdate();
            pstmt.close();

            if (rowsAffected > 0) {
                System.out.println("Check-out recorded: " + checkInID);
            }

            return rowsAffected > 0;

        } catch (SQLException e) {
            System.err.println(" Error recording check-out: " + e.getMessage());
            return false;
        }
    }

    public static CheckIn getCheckInByID(String checkInID) {
        String sql = "SELECT * FROM checkins WHERE checkInID = ?";

        try {
            Connection conn = DatabaseConnection.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, checkInID);

            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                CheckIn checkIn = new CheckIn(
                        rs.getString("checkInID"),
                        rs.getString("memberID"),
                        rs.getTimestamp("checkInTime").toLocalDateTime(),
                        rs.getString("equipmentUsed")
                );

                if (rs.getTimestamp("checkOutTime") != null) {
                    checkIn.checkOut();
                }

                rs.close();
                pstmt.close();
                return checkIn;
            }

        } catch (SQLException e) {
            System.err.println(" Error recovering check-in: " + e.getMessage());
        }

        return null;
    }

    public static List<CheckIn> getMemberCheckIns(String memberID) {
        List<CheckIn> checkIns = new ArrayList<>();
        String sql = "SELECT * FROM checkins WHERE memberID = ? ORDER BY checkInTime DESC";

        try {
            Connection conn = DatabaseConnection.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, memberID);

            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                CheckIn checkIn = new CheckIn(
                        rs.getString("checkInID"),
                        rs.getString("memberID"),
                        rs.getTimestamp("checkInTime").toLocalDateTime(),
                        rs.getString("equipmentUsed")
                );

                if (rs.getTimestamp("checkOutTime") != null) {
                    checkIn.checkOut();
                }

                checkIns.add(checkIn);
            }

            rs.close();
            pstmt.close();

        } catch (SQLException e) {
            System.err.println("Error retrieving member check-ins: " + e.getMessage());
        }

        return checkIns;
    }

    public static List<CheckIn> getAllCheckIns() {
        List<CheckIn> checkIns = new ArrayList<>();
        String sql = "SELECT * FROM checkins ORDER BY checkInTime DESC";

        try {
            Connection conn = DatabaseConnection.getConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                CheckIn checkIn = new CheckIn(
                        rs.getString("checkInID"),
                        rs.getString("memberID"),
                        rs.getTimestamp("checkInTime").toLocalDateTime(),
                        rs.getString("equipmentUsed")
                );

                if (rs.getTimestamp("checkOutTime") != null) {
                    checkIn.checkOut();
                }

                checkIns.add(checkIn);
            }

            rs.close();
            stmt.close();

        } catch (SQLException e) {
            System.err.println(" Error retrieving all check-ins: " + e.getMessage());
        }

        return checkIns;
    }

    public static boolean deleteCheckIn(String checkInID) {
        String sql = "DELETE FROM checkins WHERE checkInID = ?";

        try {
            Connection conn = DatabaseConnection.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, checkInID);

            int rowsAffected = pstmt.executeUpdate();
            pstmt.close();

            return rowsAffected > 0;

        } catch (SQLException e) {
            System.err.println("Error deleting check-in: " + e.getMessage());
            return false;
        }
    }

    public static int getTotalCheckInsCount() {
        String sql = "SELECT COUNT(*) FROM checkins";

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

        } catch (SQLException e) {
            System.err.println(" Error counting check-ins: " + e.getMessage());
        }

        return 0;
    }
}
