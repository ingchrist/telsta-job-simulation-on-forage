package au.com.telstra.simcardactivator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@RestController
@RequestMapping("/sim")
public class SimCardActivationController {

    @Autowired
    private SimCardActivationService service;

    @PostMapping("/activate")
    public ResponseEntity<?> activateSim(@RequestBody Map<String, String> request) {
        String iccid = request.get("iccid");
        String customerEmail = request.get("customerEmail");

        // Call SimCardActuator
        RestTemplate restTemplate = new RestTemplate();
        Map<String, String> actuatorRequest = new HashMap<>();
        actuatorRequest.put("iccid", iccid);

        boolean activated = false;
        try {
            ResponseEntity<String> actuatorResponse = restTemplate.postForEntity(
                    "http://localhost:8444/actuate", actuatorRequest, String.class);
            activated = actuatorResponse.getStatusCode().is2xxSuccessful();
        } catch (Exception e) {
            activated = false;
        }

        // Save to DB
        SimCardActivation activation = new SimCardActivation(iccid, customerEmail, activated);
        SimCardActivation saved = service.save(activation);

        Map<String, Object> response = new HashMap<>();
        response.put("id", saved.getId());
        response.put("iccid", saved.getIccid());
        response.put("customerEmail", saved.getCustomerEmail());
        response.put("active", saved.isActive());

        return ResponseEntity.ok(response);
    }

    @GetMapping("/query")
    public ResponseEntity<?> querySim(@RequestParam Long simCardId) {
        Optional<SimCardActivation> result = service.findById(simCardId);
        if (result.isPresent()) {
            SimCardActivation activation = result.get();
            Map<String, Object> response = new HashMap<>();
            response.put("iccid", activation.getIccid());
            response.put("customerEmail", activation.getCustomerEmail());
            response.put("active", activation.isActive());
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("SIM card not found");
        }
    }
}