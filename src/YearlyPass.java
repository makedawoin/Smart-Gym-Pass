import java.time.LocalDate;

public class YearlyPass extends Pass {

    private static final long serialVersionUID = 1L;

    private static final int VALIDITY_DAYS = 365;
    private static final double YEARLY_COST = 500.0;

    public YearlyPass(String passID, String memberID, LocalDate startDate) {
        super(
                passID,
                memberID,
                startDate,
                startDate.plusDays(VALIDITY_DAYS),
                YEARLY_COST
        );
    }

    @Override
    public double calculateRenewalCost() {
        return YEARLY_COST * 0.90;
    }

    @Override
    public String getPassType() {
        return "Yearly";
    }

    @Override
    public int getValidityDays() {
        return VALIDITY_DAYS;
    }

    @Override
    public String toString() {
        return "YearlyPass{" + super.toString() + "}";
    }
}