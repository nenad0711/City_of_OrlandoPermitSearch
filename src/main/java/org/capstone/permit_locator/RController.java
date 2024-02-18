package org.capstone.permit_locator;

import org.capstone.permit_locator.Model.Permit;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@Controller
public class RController {

    @GetMapping("/per")
    public Permit[] searchPermits() {
        RestTemplate template = new RestTemplate();
        String url  ="https://data.cityoforlando.net/resource/ryhf-m453.json?$limit=10";
        Permit [] permitList = template.getForObject(url, Permit[].class);
        //ResponseEntity<Permit[]> response = restTemplate.getForEntity(apiUrl, Permit[].class);
        //List<Permit> permitList = Arrays.asList(permits);
        //System.out.println(response.getStatusCode());
         // Permit [] permitsL = new Permit[]{template.getForObject(url,Permit.class)};
        //Permit [] permits = response.getBody();

/**
        if (permits != null && permits.length > 0) {
            // Return only the first permit
            return permits[0];
        } else {
            // Handle case where no permits are found
            return null;
        }**/

        assert permitList != null;
        // Print each permit's details to the console
        for (Permit permit : permitList) {
            System.out.println("Permit ID: " +   permit.permit_number());
            System.out.println("Permit Application number: " + permit.application_type());
            System.out.println("Permit Owner Name: " + permit.property_owner_name());
            System.out.println("Permit Address: " + permit.permit_address());
            System.out.println("Permit Worktype: " + permit.worktype());
            System.out.println("Permit Status: " + permit.application_status());
            System.out.println("------------------------------------------");
        }
        return permitList;
    }

}
