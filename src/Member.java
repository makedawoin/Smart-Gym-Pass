import java.io.Serializable;
import java.time.LocalDate;
public class Member implements Serializable {
    private static final long serialVersionUID = 1L;
    private String memberID;
    private String name;
    private String email;
    private String phone;

    private LocalDate joindate;
    private Pass currentPass;
    private double accountBalance;
    private String memberStatus;

    public Member(String memberID,String name, String email, String phone) {
        this.memberID = memberID;
        this.name = name;
        this.email = email;
        this.phone = phone;

        this.joindate = LocalDate.now();
        this.accountBalance = 0.0;
        this.memberStatus = "active";
        this.currentPass = null;

    }
    public void assignPass(Pass pass) throws InvalidPassException{
        if (pass == null) {
            throw new InvalidPassException("Pass cannot be null");
        }
        this.currentPass = pass;
    }
    public boolean canAccessGym() throws ExpiredPassException {
        if (currentPass == null) {
            throw new ExpiredPassException("No active pass assigned");
        }
        if (!currentPass.isPassValid()) {
            throw new ExpiredPassException("Pass has expired:" + currentPass.getEndDate());
        }
        return true;
    }
    public void addBalance(double amount) {
        if (amount > 0) {
            this.accountBalance += amount;
        }
    }
    public boolean deductBalance(double amount) throws InvalidPassException {
        if (amount > accountBalance) {
            throw new InvalidPassException(
                    "Insufficient balance.Required: $" + amount +
                            ", Available: $" + accountBalance
            );
        }
        this.accountBalance -= amount;
        return true;
    }
    public Pass getCurrentPass() {
        return currentPass;
    }
    public String getMemberID() {
        return memberID;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail( String email) {
        this.email = email;
    }
    public String getPhone() {
        return phone;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }
    public LocalDate getJoinDate(){
        return joindate;
    }
    public double getAccountBalance(){
        return accountBalance;
    }
    public void setAccountBalance(double accountBalance) {
        this.accountBalance = accountBalance;
    }
    public String getMemberStatus() {
        return memberStatus;
    }
    public void setMemberStatus(String memberStatus) {
        this.memberStatus = memberStatus;
    }
    @Override
    public String toString() {
        return "Member{" +
                "memberID='" + memberID + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", joinDate=" + joindate +
                ", currentPass=" + (currentPass != null ? currentPass.getPassType() : "None") +
                ", accountBalance=" + accountBalance +
                ", status='" + memberStatus + '\'' +
                '}';
    }
}
