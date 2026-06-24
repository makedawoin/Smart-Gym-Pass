import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Scanner;
import java.util.List;
import Payment.Payment;

public class Main {

    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("Initializing Smart Gym Pass System...");
        DatabaseConnection.initializeDatabase();

        createSampleData();

        boolean running = true;

        while (running) {
            displayMainMenu();
            int choice = getIntInput();

            switch (choice) {
                case 1:
                    memberOperations();
                    break;
                case 2:
                    passOperations();
                    break;
                case 3:
                    attendanceOperations();
                    break;
                case 4:
                    paymentOperations();
                    break;
                case 5:
                    reportOperations();
                    break;
                case 6:
                    running = false;
                    System.out.println("\n✓ Goodbye! Thanks for using Smart Gym Pass System");
                    break;
                default:
                    System.out.println("Invalid choice!");
            }
        }

        DatabaseConnection.closeConnection();
        scanner.close();
    }

    private static void memberOperations() {
        boolean back = false;

        while (!back) {
            System.out.println("\n" + "=".repeat(50));
            System.out.println("MEMBER OPERATIONS");
            System.out.println("=".repeat(50));
            System.out.println("1. Add New Member");
            System.out.println("2. View Member");
            System.out.println("3. Update Member");
            System.out.println("4. View All Members");
            System.out.println("5. Back to Main Menu");
            System.out.println("=".repeat(50));
            System.out.print("Select option: ");

            int choice = getIntInput();

            switch (choice) {
                case 1:
                    addNewMember();
                    break;
                case 2:
                    viewMember();
                    break;
                case 3:
                    updateMember();
                    break;
                case 4:
                    viewAllMembers();
                    break;
                case 5:
                    back = true;
                    break;
                default:
                    System.out.println("Invalid choice!");
            }
        }
    }

    private static void addNewMember() {
        System.out.print("\nEnter Member ID: ");
        String memberID = scanner.nextLine();

        System.out.print("Enter Name: ");
        String name = scanner.nextLine();

        System.out.print("Enter Email: ");
        String email = scanner.nextLine();

        System.out.print("Enter Phone: ");
        String phone = scanner.nextLine();

        Member member = new Member(memberID, name, email, phone);

        if (MemberManager.addMember(member)) {
            System.out.println(" Member added successfully!");
        } else {
            System.out.println(" Failed to add member");
        }
    }

    private static void viewMember() {
        System.out.print("\nEnter Member ID: ");
        String memberID = scanner.nextLine();

        Member member = MemberManager.getMemberByID(memberID);

        if (member != null) {
            System.out.println("\n" + member);

            Pass pass = PassManager.getMemberPass(memberID);
            if (pass != null) {
                System.out.println("Current Pass: " + pass.getPassType() +
                        " - Status: " + pass.getPassStatus());
            }
        } else {
            System.out.println(" Member not found!");
        }
    }
    private static void updateMember() {
        System.out.print("\nEnter Member ID: ");
        String memberID = scanner.nextLine();

        Member member = MemberManager.getMemberByID(memberID);

        if (member != null) {
            System.out.print("Enter new email (or press Enter to skip): ");
            String email = scanner.nextLine();
            if (!email.isEmpty()) {
                member.setEmail(email);
            }

            System.out.print("Enter new phone (or press Enter to skip): ");
            String phone = scanner.nextLine();
            if (!phone.isEmpty()) {
                member.setPhone(phone);
            }

            if (MemberManager.updateMember(member)) {
                System.out.println(" Member updated successfully!");
            } else {
                System.out.println(" Failed to update member");
            }
        } else {
            System.out.println(" Member not found!");
        }
    }

    private static void viewAllMembers() {
        System.out.println("\n" + "=".repeat(80));
        System.out.printf("%-15s %-20s %-25s %-15s%n",
                "Member ID", "Name", "Email", "Balance");
        System.out.println("=".repeat(80));

        List<Member> members = MemberManager.getAllMembers();

        for (Member m : members) {
            System.out.printf("%-15s %-20s %-25s $%-14.2f%n",
                    m.getMemberID(),
                    m.getName(),
                    m.getEmail(),
                    m.getAccountBalance());
        }
    }

    private static void passOperations() {
        boolean back = false;

        while (!back) {
            System.out.println("\n" + "=".repeat(50));
            System.out.println("PASS OPERATIONS");
            System.out.println("=".repeat(50));
            System.out.println("1. Issue New Pass");
            System.out.println("2. View Pass");
            System.out.println("3. Renew Pass");
            System.out.println("4. View All Passes");
            System.out.println("5. Back to Main Menu");
            System.out.println("=".repeat(50));
            System.out.print("Select option: ");

            int choice = getIntInput();

            switch (choice) {
                case 1:
                    issueNewPass();
                    break;
                case 2:
                    viewPass();
                    break;
                case 3:
                    renewPass();
                    break;
                case 4:
                    viewAllPasses();
                    break;
                case 5:
                    back = true;
                    break;
                default:
                    System.out.println(" Invalid choice!");
            }
        }
    }

    private static void issueNewPass() {
        System.out.print("\nEnter Member ID: ");
        String memberID = scanner.nextLine();

        Member member = MemberManager.getMemberByID(memberID);
        if (member == null) {
            System.out.println("Member not found!");
            return;
        }

        System.out.println("\nSelect Pass Type:");
        System.out.println("1. Trial (7 days - Free)");
        System.out.println("2. Monthly (30 days - $50)");
        System.out.println("3. Yearly (365 days - $500)");
        System.out.print("Select: ");

        int passType = getIntInput();
        Pass pass = null;
        String passID = "PASS" + System.currentTimeMillis();

        try {
            switch (passType) {
                case 1:
                    pass = new TrialPass(passID, memberID, LocalDate.now());
                    break;
                case 2:
                    pass = new MonthlyPass(passID, memberID, LocalDate.now());
                    break;
                case 3:
                    pass = new YearlyPass(passID, memberID, LocalDate.now());
                    break;
                default:
                    System.out.println(" Invalid pass type!");
                    return;
            }

            if (PassManager.addPass(pass)) {
                System.out.println(" Pass issued successfully!");
                System.out.println(pass);
            }
        } catch (Exception e) {
            System.out.println(" Error: " + e.getMessage());
        }
    }

    private static void viewPass() {
        System.out.print("\nEnter Pass ID: ");
        String passID = scanner.nextLine();

        Pass pass = PassManager.getPassByID(passID);

        if (pass != null) {
            System.out.println("\n" + pass);
            System.out.println("Valid: " + (pass.isPassValid() ? "Yes" : "No"));
            System.out.println("Renewal Cost: $" +
                    String.format("%.2f", pass.calculateRenewalCost()));
        } else {
            System.out.println("Pass not found!");
        }
    }

    private static void renewPass() {
        System.out.print("\nEnter Member ID: ");
        String memberID = scanner.nextLine();

        Pass oldPass = PassManager.getMemberPass(memberID);
        if (oldPass == null) {
            System.out.println(" No active pass found!");
            return;
        }

        double renewalCost = oldPass.calculateRenewalCost();
        System.out.println("Renewal Cost: $" + String.format("%.2f", renewalCost));

        String passID = "PASS" + System.currentTimeMillis();
        Pass newPass = new MonthlyPass(passID, memberID, LocalDate.now());

        if (PassManager.addPass(newPass)) {
            String paymentID = "PAY" + System.currentTimeMillis();
            Payment payment = new Payment(
                    paymentID,
                    memberID,
                    renewalCost,
                    "renewal",
                    "card"
            );
            PaymentProcessor.processPayment(payment);

            System.out.println("Pass renewed successfully!");
            System.out.println(newPass);
        }
    }

    private static void viewAllPasses() {
        System.out.println("\n" + "=".repeat(100));
        System.out.printf("%-15s %-15s %-15s %-15s %-15s %-15s%n",
                "Pass ID", "Member ID", "Type", "Start Date", "End Date", "Status");
        System.out.println("=".repeat(100));

        List<Pass> passes = PassManager.getAllPasses();

        for (Pass p : passes) {
            System.out.printf("%-15s %-15s %-15s %-15s %-15s %-15s%n",
                    p.getPassID(),
                    p.getMemberID(),
                    p.getPassType(),
                    p.getStartDate(),
                    p.getEndDate(),
                    p.getPassStatus());
        }
    }

    private static void attendanceOperations() {
        System.out.print("\nEnter Member ID: ");
        String memberID = scanner.nextLine();

        Member member = MemberManager.getMemberByID(memberID);
        if (member == null) {
            System.out.println(" Member not found!");
            return;
        }

        try {
            if (member.canAccessGym()) {
                System.out.print("Enter equipment used: ");
                String equipment = scanner.nextLine();

                String checkInID = "CHK" + System.currentTimeMillis();
                CheckIn checkIn = new CheckIn(
                        checkInID,
                        memberID,
                        LocalDateTime.now(),
                        equipment
                );

                if (AttendanceTracker.recordCheckIn(checkIn)) {
                    System.out.println("Check-in recorded!");
                    System.out.println("Welcome " + member.getName() + "!");
                }
            }
        } catch (ExpiredPassException e) {
            System.out.println( e.getMessage());
        }
    }

    private static void paymentOperations() {
        System.out.print("\nEnter Member ID: ");
        String memberID = scanner.nextLine();

        System.out.print("Enter Amount: $");
        double amount = getDoubleInput();

        String paymentID = "PAY" + System.currentTimeMillis();
        Payment payment = new Payment(
                paymentID,
                memberID,
                amount,
                "pass_purchase",
                "card"
        );

        if (PaymentProcessor.processPayment(payment)) {
            System.out.println(" Payment processed successfully!");
        } else {
            System.out.println("Payment failed!");
        }
    }

    private static void reportOperations() {
        GymReport.displayReportMenu();
        System.out.print("Select report: ");
        int choice = getIntInput();

        switch (choice) {
            case 1:
                GymReport.generateMemberReport();
                break;
            case 2:
                GymReport.generatePassReport();
                break;
            case 3:
                GymReport.generateAttendanceReport();
                break;
            case 4:
                GymReport.generateFinancialReport();
                break;
            case 5:
                GymReport.generateComprehensiveReport();
                break;
            case 6:
                break;
            default:
                System.out.println("Invalid choice!");
        }
    }

    private static void displayMainMenu() {
        System.out.println("\n" + "╔" + "═".repeat(48) + "╗");
        System.out.println("║" + " ".repeat(10) + "SMART GYM PASS SYSTEM" + " ".repeat(18) + "║");
        System.out.println("╚" + "═".repeat(48) + "╝");
        System.out.println("1. Member Operations");
        System.out.println("2. Pass Operations");
        System.out.println("3. Check-in/Attendance");
        System.out.println("4. Payment Operations");
        System.out.println("5. Reports & Analytics");
        System.out.println("6. Exit");
        System.out.println("=".repeat(50));
        System.out.print("Select option: ");
    }

    private static int getIntInput() {
        try {
            return Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println(" Invalid input!");
            return -1;
        }
    }

    private static double getDoubleInput() {
        try {
            return Double.parseDouble(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Invalid input!");
            return 0;
        }
    }

    private static void createSampleData() {
        System.out.println("\n✓ Creating sample data...\n");

        Member m1 = new Member("M001", "Abebe", "abebe@gmail.com", "0911222333");
        Member m2 = new Member("M002", "Almaz", "almaz@gmail.com", "0912333444");

        MemberManager.addMember(m1);
        MemberManager.addMember(m2);

        Pass p1 = new MonthlyPass("P001", "M001", LocalDate.now());
        Pass p2 = new YearlyPass("P002", "M002", LocalDate.now());

        PassManager.addPass(p1);
        PassManager.addPass(p2);

        System.out.println("Sample data created!\n");
    }
}