package au.com.telstra.simcardactivator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class SimCardActivationController {

    @Autowired
    private SimCardActivationService service;

    // POST endpoint to activate SIM and save record
    @PostMapping("/activate")
    public ResponseEntity<SimCardActivationResponse> activateSim(@RequestBody SimCardActivationRequest request) {
        SimCardActivation record = service.activateSim(request.getIccid(), request.getCustomerEmail());
        SimCardActivationResponse response = new SimCardActivationResponse(
                record.getIccid(),
                record.getCustomerEmail(),
                record.isActive()
        );
        return ResponseEntity.ok(response);
    }

    // GET endpoint to query by simCardId
    @GetMapping("/query")
    public ResponseEntity<SimCardActivationResponse> querySimCard(@RequestParam Long simCardId) {
        return service.getSimCardById(simCardId)
                .map(record -> ResponseEntity.ok(new SimCardActivationResponse(
                        record.getIccid(),
                        record.getCustomerEmail(),
                        record.isActive()
                )))
                .orElse(ResponseEntity.notFound().build());
    }
}

// Request DTO
class SimCardActivationRequest {
    private String iccid;
    private String customerEmail;

    public String getIccid() { return iccid; }
    public void setIccid(String iccid) { this.iccid = iccid; }
    public String getCustomerEmail() { return customerEmail; }
    public void setCustomerEmail(String customerEmail) { this.customerEmail = customerEmail; }
}

// Response DTO
class SimCardActivationResponse {
    private String iccid;
    private String customerEmail;
    private boolean active;

    public SimCardActivationResponse(String iccid, String customerEmail, boolean active) {
        this.iccid = iccid;
        this.customerEmail = customerEmail;
        this.active = active;
    }

    public String getIccid() { return iccid; }
    public String getCustomerEmail() { return customerEmail; }
    public boolean isActive() { return active; }
}
