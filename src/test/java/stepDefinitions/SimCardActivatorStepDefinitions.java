package stepDefinitions;

import au.com.telstra.simcardactivator.SimCardActivator;
import io.cucumber.spring.CucumberContextConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootContextLoader;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.ContextConfiguration;

import io.cucumber.java.en.*;
import org.springframework.http.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.HashMap;
import java.util.Map;

@CucumberContextConfiguration
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@ContextConfiguration(classes = SimCardActivator.class, loader = SpringBootContextLoader.class)
public class SimCardActivatorStepDefinitions {
    @Autowired
    private TestRestTemplate restTemplate;

    private String iccid;
    private String customerEmail;
    private Long simCardId;

    @Given("I have a SIM card with ICCID {string} and customer email {string}")
    public void i_have_a_sim_card_with_iccid_and_customer_email(String iccid, String email) {
        this.iccid = iccid;
        this.customerEmail = email;
    }

    @When("I submit an activation request")
    public void i_submit_an_activation_request() {
        String url = "http://localhost:8080/sim/activate";
        Map<String, String> request = new HashMap<>();
        request.put("iccid", iccid);
        request.put("customerEmail", customerEmail);

        ResponseEntity<Map> response = restTemplate.postForEntity(url, request, Map.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        Map body = response.getBody();
        assertNotNull(body);
        Object idObj = body.get("id");
        if (idObj != null) {
            this.simCardId = ((Number) idObj).longValue();
        } else {
            this.simCardId = null;
        }
    }

    @Then("the activation should be successful")
    public void the_activation_should_be_successful() {
        assertNotNull(simCardId, "simCardId should not be null for successful activation");
        String url = "http://localhost:8080/sim/query?simCardId=" + simCardId;
        ResponseEntity<Map> response = restTemplate.getForEntity(url, Map.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        Map body = response.getBody();
        assertNotNull(body);
        assertTrue((Boolean) body.get("active"));
    }

    @Then("the activation should fail")
    public void the_activation_should_fail() {
        assertNull(simCardId, "simCardId should be null for failed activation");
        // Optionally, you can add more checks here if your API returns error details
    }
}