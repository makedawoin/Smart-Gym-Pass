import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class CheckIn {
    private String checkInID;
    private String memberID;
    private LocalDateTime checkInTime;
    private LocalDateTime checkOutTime;
    private String equipmentUsed;
    private double durationInMinutes;

    public CheckIn(String checkInID, String memberID, LocalDateTime checkInTime, String equipmentUsed) {
        this.checkInID = checkInID;
        this.memberID = memberID;
        this.checkInTime = checkInTime;
        this.equipmentUsed = equipmentUsed;
        this.checkOutTime = null;
        this.durationInMinutes = 0;
    }

    public void checkOut() {
        this.checkOutTime = LocalDateTime.now();

        if (checkInTime != null && checkOutTime != null) {
            this.durationInMinutes = ChronoUnit.MINUTES.between(checkInTime, checkOutTime);
        }
    }

    public boolean isCheckedOut() {
        return checkOutTime != null;
    }

    public String getCheckInID() {
        return checkInID;
    }

    public String getMemberID() {
        return memberID;
    }

    public LocalDateTime getCheckInTime() {
        return checkInTime;
    }

    public LocalDateTime getCheckOutTime() {
        return checkOutTime;
    }

    public String getEquipmentUsed() {
        return equipmentUsed;
    }

    public void setEquipmentUsed(String equipmentUsed) {
        this.equipmentUsed = equipmentUsed;
    }

    public double getDurationInMinutes() {
        return durationInMinutes;
    }

    @Override
    public String toString() {
        return "CheckIn{" +
                "checkInID='" + checkInID + '\'' +
                ", memberID='" + memberID + '\'' +
                ", checkInTime=" + checkInTime +
                ", checkOutTime=" + checkOutTime +
                ", equipmentUsed='" + equipmentUsed + '\'' +
                ", durationInMinutes=" + durationInMinutes +
                '}';
    }
}
