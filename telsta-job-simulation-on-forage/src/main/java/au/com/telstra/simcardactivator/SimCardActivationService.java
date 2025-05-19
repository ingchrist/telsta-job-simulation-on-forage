package au.com.telstra.simcardactivator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class SimCardActivationService {

    @Autowired
    private SimCardActivationRepository repository;

    public SimCardActivation activateSim(String iccid, String customerEmail) {
        boolean active = callActuator(iccid);
        SimCardActivation record = new SimCardActivation(iccid, customerEmail, active);
        return repository.save(record);
    }

    public Optional<SimCardActivation> getSimCardById(Long id) {
        return repository.findById(id);
    }

    private boolean callActuator(String iccid) {
        RestTemplate restTemplate = new RestTemplate();
        String actuatorUrl = "http://localhost:8444/actuate";
        Map<String, String> payload = new HashMap<>();
        payload.put("iccid", iccid);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Map<String, String>> request = new HttpEntity<>(payload, headers);
        try {
            ResponseEntity<ActuatorResponse> response = restTemplate.postForEntity(actuatorUrl, request, ActuatorResponse.class);
            return response.getBody() != null && response.getBody().isSuccess();
        } catch (Exception e) {
            System.out.println("Error communicating with actuator: " + e.getMessage());
            return false;
        }
    }

    static class ActuatorResponse {
        private boolean success;
        public boolean isSuccess() { return success; }
        public void setSuccess(boolean success) { this.success = success; }
    }
}
