import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseConnection {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/smart_gym_db";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "";

    private static Connection connection;

    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("✓ MySQL Driver loaded successfully");
        } catch (ClassNotFoundException e) {
            System.err.println("✗ MySQL Driver not found: " + e.getMessage());
        }
    }

    public static Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            try {
                connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
                System.out.println("✓ Connected to database: " + DB_URL);
            } catch (SQLException e) {
                System.err.println("✗ Connection failed: " + e.getMessage());
                throw e;
            }
        }
        return connection;
    }

    public static void initializeDatabase() {
        try {
            Connection conn = getConnection();
            Statement stmt = conn.createStatement();

            String createMembersTable = "CREATE TABLE IF NOT EXISTS members (" +
                    "memberID VARCHAR(20) PRIMARY KEY," +
                    "name VARCHAR(100) NOT NULL," +
                    "email VARCHAR(100)," +
                    "phone VARCHAR(20)," +
                    "joinDate DATE," +
                    "accountBalance DOUBLE," +
                    "memberStatus VARCHAR(20)" +
                    ")";
            stmt.execute(createMembersTable);

            String createPassesTable = "CREATE TABLE IF NOT EXISTS passes (" +
                    "passID VARCHAR(20) PRIMARY KEY," +
                    "memberID VARCHAR(20)," +
                    "passType VARCHAR(20)," +
                    "startDate DATE," +
                    "endDate DATE," +
                    "cost DOUBLE," +
                    "passStatus VARCHAR(20)," +
                    "FOREIGN KEY (memberID) REFERENCES members(memberID)" +
                    ")";
            stmt.execute(createPassesTable);

            String createCheckInsTable = "CREATE TABLE IF NOT EXISTS checkins (" +
                    "checkInID VARCHAR(20) PRIMARY KEY," +
                    "memberID VARCHAR(20)," +
                    "checkInTime DATETIME," +
                    "checkOutTime DATETIME," +
                    "equipmentUsed VARCHAR(100)," +
                    "durationInMinutes DOUBLE," +
                    "FOREIGN KEY (memberID) REFERENCES members(memberID)" +
                    ")";
            stmt.execute(createCheckInsTable);

            String createPaymentsTable = "CREATE TABLE IF NOT EXISTS payments (" +
                    "paymentID VARCHAR(20) PRIMARY KEY," +
                    "memberID VARCHAR(20)," +
                    "amount DOUBLE," +
                    "paymentDate DATETIME," +
                    "paymentType VARCHAR(50)," +
                    "paymentStatus VARCHAR(20)," +
                    "paymentMethod VARCHAR(20)," +
                    "FOREIGN KEY (memberID) REFERENCES members(memberID)" +
                    ")";
            stmt.execute(createPaymentsTable);

            System.out.println("✓ Database tables initialized successfully");
            stmt.close();

        } catch (SQLException e) {
            System.err.println("✗ Database initialization error: " + e.getMessage());
        }
    }

    public static void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                System.out.println("✓ Database connection closed");
            }
        } catch (SQLException e) {
            System.err.println("✗ Error closing connection: " + e.getMessage());
        }
    }
}