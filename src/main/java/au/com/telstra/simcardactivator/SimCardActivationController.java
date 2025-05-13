package au.com.telstra.simcardactivator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class SimCardActivationController {
    @Autowired
    private SimCardActivationService activationService;

    @PostMapping("/activate")
    public ResponseEntity<String> activateSim(@RequestBody SimActivationRequest request) {
        boolean success = activationService.activateSim(request.getIccid());
        if (success) {
            System.out.println("Activation successful for ICCID: " + request.getIccid());
            return ResponseEntity.ok("Activation successful");
        } else {
            System.out.println("Activation failed for ICCID: " + request.getIccid());
            return ResponseEntity.status(500).body("Activation failed");
        }
    }
}

class SimActivationRequest {
    private String iccid;
    private String customerEmail;

    public String getIccid() { return iccid; }
    public void setIccid(String iccid) { this.iccid = iccid; }
    public String getCustomerEmail() { return customerEmail; }
    public void setCustomerEmail(String customerEmail) { this.customerEmail = customerEmail; }
}
