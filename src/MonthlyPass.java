import java.time.LocalDate;

public class MonthlyPass extends Pass {

    private static final long serialVersionUID = 1L;

    private static final int VALIDITY_DAYS = 30;
    private static final double MONTHLY_COST = 50.0;

    public MonthlyPass(String passID, String memberID, LocalDate startDate) {
        super(
                passID,
                memberID,
                startDate,
                startDate.plusDays(VALIDITY_DAYS),
                MONTHLY_COST
        );
    }

    @Override
    public double calculateRenewalCost() {
        return MONTHLY_COST * 0.95;
    }

    @Override
    public String getPassType() {
        return "Monthly";
    }

    @Override
    public int getValidityDays() {
        return VALIDITY_DAYS;
    }

    @Override
    public String toString() {
        return "MonthlyPass{" + super.toString() + "}";
    }
}