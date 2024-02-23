package org.capstone.permit_locator;

import org.capstone.permit_locator.Model.Permit;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.ui.Model;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.ModelMap;
import org.springframework.web.util.UriComponentsBuilder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class PermitControllerTest {

    @Mock
    private ResponseEntity<Permit[]> responseEntity;

    @Mock
    private Model model;

    @InjectMocks
    private PermitController permitController;
    private String address;
    private String ownerName;
    private String permitType;

    @BeforeEach
    void setUp() {
        address = "pine";
        ownerName = "W. ROGER SMITH";
        permitType = "";
    }
// test if returnBody is not null and matches the max array length given by the limit filter
    @Test
    void testPermitNotNull(){
        String apiUrl = "https://data.cityoforlando.net/resource/ryhf-m453.json?$limit=5";
        RestTemplate template = new RestTemplate();
        responseEntity = template.getForEntity(apiUrl, Permit[].class);
        Permit[] testPer = responseEntity.getBody();
        assertNotNull(testPer);
        assertEquals(5,testPer.length);
        System.out.println("Number of permits retrieved (1000 max per page): " + testPer.length);
    }
// does Permit object match?
    @Test
    void testSearchPermits() {
        Permit[] permits = {new Permit("SBF26103-0", "Sewer Benefit Fees", " W. ROGER SMITH", "PINE STREET & 11TH AVENUE", "SF", "Open")}; // Provide sample permits
        //when(responseEntity.getBody()).thenReturn(permits);

        String result = permitController.searchPermits(address, ownerName, permitType, model);
        //assertEquals("Results", result);
        verify(model).addAttribute("permits", permits);
    }
}
