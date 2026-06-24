import java.time.LocalDate;
import java.io.Serializable;

public abstract class Pass implements Serializable {

    private static final long serialVersionUID = 1L;

    private String passID;
    private String memberID;
    private LocalDate startDate;
    private LocalDate endDate;
    private double cost;
    private String passStatus;

    public Pass(String passID, String memberID, LocalDate startDate,
                LocalDate endDate, double cost) {

        this.passID = passID;
        this.memberID = memberID;
        this.startDate = startDate;
        this.endDate = endDate;
        this.cost = cost;
        this.passStatus = "active";
    }

    public abstract double calculateRenewalCost();

    public abstract String getPassType();

    public abstract int getValidityDays();

    public boolean isPassValid() {
        boolean notExpired = LocalDate.now().isBefore(endDate);
        boolean isActive = passStatus.equals("active");
        return notExpired && isActive;
    }

    public void expirePass() {
        this.passStatus = "expired";
    }

    public String getPassID() {
        return passID;
    }

    public void setPassID(String passID) {
        this.passID = passID;
    }

    public String getMemberID() {
        return memberID;
    }

    public void setMemberID(String memberID) {
        this.memberID = memberID;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public String getPassStatus() {
        return passStatus;
    }

    public void setPassStatus(String passStatus) {
        this.passStatus = passStatus;
    }

    @Override
    public String toString() {
        return "Pass{" +
                "passID='" + passID + '\'' +
                ", memberID='" + memberID + '\'' +
                ", passType='" + getPassType() + '\'' +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", cost=" + cost +
                ", status='" + passStatus + '\'' +
                '}';
    }
}