package org.capstone.permit_locator;
import org.capstone.permit_locator.Model.Permit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@Controller
public class PermitController {
    public Permit[] permits;
    private static final Logger log = LoggerFactory.getLogger(PermitLocatorApplication.class);

    @GetMapping("/")
    public String index(Model model) {
        return "Home";
    }

    @GetMapping("/search")
    public String searchPermits(@RequestParam(name = "address") String address,
                                @RequestParam(name = "ownerName") String ownerName,
                                @RequestParam(name = "permitType") String permitType,
                                Model model) {

        log.info(address + " " + ownerName + " " + permitType);
        String apiUrl = "https://data.cityoforlando.net/resource/ryhf-m453.json?";
        boolean hasParameter = false;
        if (address != null && !address.isEmpty()) {
            apiUrl += "$where=permit_address like UPPER('" + address + "%')";
            hasParameter = true;
        }
        if (ownerName != null && !ownerName.isEmpty()) {
            if (hasParameter) {
                apiUrl += " AND ";
            } else {
                apiUrl += "$where=";
                hasParameter = true;
            }

            apiUrl += "property_owner_name like UPPER('" + ownerName + "%')";
        }
        if (permitType != null && !permitType.isEmpty()) {
            if (hasParameter) {
                apiUrl += " AND ";
            }
            apiUrl += "application_type='" + permitType + "'";
        }
        // Retrieve permit data from the API based on the search parameters
        RestTemplate template = new RestTemplate();
        ResponseEntity<Permit[]> response = template.getForEntity(apiUrl, Permit[].class);
        permits = response.getBody();
        assert permits != null;

        model.addAttribute("permits", permits);

        // Return the template to display the search results
        return "Results";
    }

    @GetMapping("/details/{id}")
    public String getPermitDetails(@PathVariable String id, Model model) {
        Permit permit = retrievePermitDetails(id);
        model.addAttribute("permit", permit);
        return "Additional_Info";
    }

    public Permit retrievePermitDetails(String id) {

        for (Permit perm : permits)
            if (perm.permit_number().equals(id)) {
                return perm;
            }
        return null;
    }
}
/**
 public Permit[] searchPermits() {
 RestTemplate template = new RestTemplate();
 String url  ="https://data.cityoforlando.net/resource/ryhf-m453.json?$limit=10";
 ResponseEntity<Permit[]> response = template.getForEntity(url, Permit[].class);
 Permit [] permits = response.getBody();
 assert permits != null;
 return permits;
 Permit [] permitList = template.getForObject(url, Permit[].class);
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
 }**/

