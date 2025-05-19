package au.com.telstra.simcardactivator;

import javax.persistence.*;

@Entity
public class SimCardActivation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String iccid;
    private String customerEmail;
    private boolean active;

    public SimCardActivation() {}

    public SimCardActivation(String iccid, String customerEmail, boolean active) {
        this.iccid = iccid;
        this.customerEmail = customerEmail;
        this.active = active;
    }

    public Long getId() { return id; }
    public String getIccid() { return iccid; }
    public void setIccid(String iccid) { this.iccid = iccid; }
    public String getCustomerEmail() { return customerEmail; }
    public void setCustomerEmail(String customerEmail) { this.customerEmail = customerEmail; }
    public boolean isActive() { return active; }
    public void setActive(boolean active) { this.active = active; }
}