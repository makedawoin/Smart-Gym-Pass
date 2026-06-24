import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class MemberManager {
    public static boolean addMember(Member member) {
        String sql = "INSERT INTO members (memberID, name, email, phone, joinDate, accountBalance, memberStatus) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";
        try {
            Connection conn = DatabaseConnection.getConnection();

            PreparedStatement pstmt = conn.prepareStatement(sql);

            pstmt.setString(1, member.getMemberID());
            pstmt.setString(2, member.getName());
            pstmt.setString(3, member.getEmail());
            pstmt.setString(4, member.getPhone());
            pstmt.setDate(5, java.sql.Date.valueOf(member.getJoinDate()));
            pstmt.setDouble(6, member.getAccountBalance());
            pstmt.setString(7, member.getMemberStatus());

            int rowsAffected = pstmt.executeUpdate();
            pstmt.close();
            return rowsAffected > 0;

        } catch (SQLException e) {
            System.err.println(" Error adding member: " + e.getMessage());
            return false;
        }
    }
    public static Member getMemberByID(String memberID) {
        String sql = "SELECT * FROM members WHERE memberID = ?";
        try {
            Connection conn = DatabaseConnection.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, memberID);

            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                Member member = new Member(
                        rs.getString("memberID"),
                        rs.getString("name"),
                        rs.getString("email"),
                        rs.getString("phone")
                );
                member.setAccountBalance(rs.getDouble("accountBalance"));
                member.setMemberStatus(rs.getString("memberStatus"));
                rs.close();
                pstmt.close();

                return member;
            }

            rs.close();
            pstmt.close();

        } catch (SQLException e) {
            System.err.println("Error retrieving member: " + e.getMessage());
        }
        return null;
    }
    public static List<Member> getAllMembers() {
        List<Member> members = new ArrayList<>();
        String sql = "SELECT * FROM members";

        try {
            Connection conn = DatabaseConnection.getConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                Member member = new Member(
                        rs.getString("memberID"),
                        rs.getString("name"),
                        rs.getString("email"),
                        rs.getString("phone")
                );
                member.setAccountBalance(rs.getDouble("accountBalance"));
                member.setMemberStatus(rs.getString("memberStatus"));
                members.add(member);
            }

            rs.close();
            stmt.close();

        } catch (SQLException e) {
            System.err.println(" Error retrieving all members: " + e.getMessage());
        }
        return members;
    }
    public static boolean updateMember(Member member) {
        String sql = "UPDATE members SET name=?, email=?, phone=?, accountBalance=?, memberStatus=? " +
                "WHERE memberID=?";
        try {
            Connection conn = DatabaseConnection.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql);

            pstmt.setString(1, member.getName());
            pstmt.setString(2, member.getEmail());
            pstmt.setString(3, member.getPhone());
            pstmt.setDouble(4, member.getAccountBalance());
            pstmt.setString(5, member.getMemberStatus());
            pstmt.setString(6, member.getMemberID());

            int rowsAffected = pstmt.executeUpdate();
            pstmt.close();

            return rowsAffected > 0;

        } catch (SQLException e) {
            System.err.println("Error updating member: " + e.getMessage());
            return false;
        }

    }
    public static boolean deleteMember(String memberID) {
        String sql = "DELETE FROM members WHERE memberID = ?";
        try {
            Connection conn = DatabaseConnection.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, memberID);

            int rowsAffected = pstmt.executeUpdate();
            pstmt.close();

            return rowsAffected > 0;

        } catch (SQLException e) {
            System.err.println("Error deleting member: " + e.getMessage());
            return false;
        }
    }
    public static int getTotalMembersCount() {
        String sql = "SELECT COUNT(*) FROM members";
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
            System.err.println("Error counting members: " + e.getMessage());
        }

        return 0;
    }

}