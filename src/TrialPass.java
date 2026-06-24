import java.time.LocalDate;

public class TrialPass extends Pass {

    private static final long serialVersionUID = 1L;

    private static final int VALIDITY_DAYS = 7;
    private static final double TRIAL_COST = 0.0;

    public TrialPass(String passID, String memberID, LocalDate startDate) {
        super(
                passID,
                memberID,
                startDate,
                startDate.plusDays(VALIDITY_DAYS),
                TRIAL_COST
        );
    }

    @Override
    public double calculateRenewalCost() {
        return 50.0;
    }

    @Override
    public String getPassType() {
        return "Trial";
    }

    @Override
    public int getValidityDays() {
        return VALIDITY_DAYS;
    }

    @Override
    public String toString() {
        return "TrialPass{" + super.toString() + "}";
    }
}
