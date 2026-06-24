import java.util.List;
import Payment.Payment;
public class GymReport {

    public static void generateMemberReport() {

        System.out.println("\n" + "=".repeat(50));
        System.out.println("MEMBER STATISTICS REPORT");
        System.out.println("=".repeat(50));

        int totalMembers =
                MemberManager.getTotalMembersCount();

        List<Member> members =
                MemberManager.getAllMembers();

        System.out.println("Total Members: " + totalMembers);
        System.out.println("-".repeat(50));

        System.out.printf(
                "%-15s %-20s %-15s %-15s%n",
                "Member ID",
                "Name",
                "Status",
                "Balance"
        );

        System.out.println("-".repeat(50));

        for (Member member : members) {

            System.out.printf(
                    "%-15s %-20s %-15s %-15.2f%n",
                    member.getMemberID(),
                    member.getName(),
                    member.getMemberStatus(),
                    member.getAccountBalance()
            );
        }

        System.out.println("=".repeat(50) + "\n");
    }

    
    public static void generatePassReport() {

        System.out.println("\n" + "=".repeat(50));
        System.out.println("PASS STATISTICS REPORT");
        System.out.println("=".repeat(50));

        int activePasses =
                PassManager.getActivePassesCount();

        List<Pass> allPasses =
                PassManager.getAllPasses();

        System.out.println(
                "Total Active Passes: "
                        + activePasses
        );

        System.out.println(
                "Total Passes (All): "
                        + allPasses.size()
        );

        System.out.println("-".repeat(50));

        System.out.printf(
                "%-15s %-15s %-15s %-15s %-15s%n",
                "Pass ID",
                "Member ID",
                "Type",
                "End Date",
                "Status"
        );

        System.out.println("-".repeat(50));

        for (Pass pass : allPasses) {

            System.out.printf(
                    "%-15s %-15s %-15s %-15s %-15s%n",
                    pass.getPassID(),
                    pass.getMemberID(),
                    pass.getPassType(),
                    pass.getEndDate(),
                    pass.getPassStatus()
            );
        }

        System.out.println("=".repeat(50) + "\n");
    }

    // ===== ATTENDANCE REPORT =====
    public static void generateAttendanceReport() {

        System.out.println("\n" + "=".repeat(70));
        System.out.println("ATTENDANCE REPORT");
        System.out.println("=".repeat(70));

        int totalCheckIns =
                AttendanceTracker.getTotalCheckInsCount();

        List<CheckIn> checkIns =
                AttendanceTracker.getAllCheckIns();

        System.out.println(
                "Total Check-ins: "
                        + totalCheckIns
        );

        System.out.println("-".repeat(70));

        System.out.printf(
                "%-15s %-15s %-20s %-15s %-10s%n",
                "Check-in ID",
                "Member ID",
                "Check-in Time",
                "Equipment",
                "Duration (min)"
        );

        System.out.println("-".repeat(70));

        for (CheckIn checkIn : checkIns) {

            System.out.printf(
                    "%-15s %-15s %-20s %-15s %-10.0f%n",
                    checkIn.getCheckInID(),
                    checkIn.getMemberID(),
                    checkIn.getCheckInTime(),
                    checkIn.getEquipmentUsed(),
                    checkIn.getDurationInMinutes()
            );
        }

        System.out.println("=".repeat(70) + "\n");
    }

    
    public static void generateFinancialReport() {

        System.out.println("\n" + "=".repeat(50));
        System.out.println("FINANCIAL REPORT");
        System.out.println("=".repeat(50));

        double totalRevenue =
                PaymentProcessor.getTotalRevenue();

        double passRevenue =
                PaymentProcessor.getRevenueByType(
                        "pass_purchase"
                );

        double renewalRevenue =
                PaymentProcessor.getRevenueByType(
                        "renewal"
                );

        System.out.println(
                "Total Revenue: $"
                        + String.format("%.2f", totalRevenue)
        );

        System.out.println(
                "Pass Purchase Revenue: $"
                        + String.format("%.2f", passRevenue)
        );

        System.out.println(
                "Renewal Revenue: $"
                        + String.format("%.2f", renewalRevenue)
        );

        System.out.println("-".repeat(50));

        List<Payment> payments =
                PaymentProcessor.getAllPayments();

        System.out.printf(
                "%-15s %-15s %-15s %-20s %-15s%n",
                "Payment ID",
                "Member ID",
                "Amount",
                "Date",
                "Type"
        );

        System.out.println("-".repeat(50));

        for (Payment payment : payments) {

            System.out.printf(
                    "%-15s %-15s $%-14.2f %-20s %-15s%n",
                    payment.getPaymentID(),
                    payment.getMemberID(),
                    payment.getAmount(),
                    payment.getPaymentDate(),
                    payment.getPaymentType()
            );
        }

        System.out.println("=".repeat(50) + "\n");
    }

    
    public static void generateComprehensiveReport() {

        System.out.println("\n\n");

        System.out.println(
                "╔" + "═".repeat(68) + "╗"
        );

        System.out.println(
                "║"
                        + " ".repeat(15)
                        + "SMART GYM PASS SYSTEM - COMPREHENSIVE REPORT"
                        + " ".repeat(11)
                        + "║"
        );

        System.out.println(
                "╚" + "═".repeat(68) + "╝"
        );

        generateMemberReport();
        generatePassReport();
        generateAttendanceReport();
        generateFinancialReport();

        System.out.println(
                "╔" + "═".repeat(68) + "╗"
        );

        System.out.println(
                "║"
                        + " ".repeat(28)
                        + "END OF REPORT"
                        + " ".repeat(27)
                        + "║"
        );

        System.out.println(
                "╚" + "═".repeat(68) + "╝\n"
        );
    }

    
    public static void displayReportMenu() {

        System.out.println("\n" + "=".repeat(50));
        System.out.println("AVAILABLE REPORTS");
        System.out.println("=".repeat(50));
        System.out.println("1. Member Statistics Report");
        System.out.println("2. Pass Statistics Report");
        System.out.println("3. Attendance Report");
        System.out.println("4. Finance Report");
        System.out.println("5. Comprehensive Report (All)");
        System.out.println("6. Exit");
        System.out.println("=".repeat(50));
    }
}
