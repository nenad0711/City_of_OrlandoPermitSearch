package org.capstone.permit_locator;
import org.capstone.permit_locator.Model.Permit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

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
        log.info(address+ownerName+permitType);

        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString("https://data.cityoforlando.net/resource/ryhf-m453.json");

        StringBuilder whereClause = new StringBuilder();

        if (address != null && !address.isEmpty()) {
            address = address.toUpperCase(); // Convert to uppercase here
            whereClause.append("permit_address like '").append(address).append("%'"); // Address starts with the input text
        }
        if (ownerName != null && !ownerName.isEmpty()) {
            ownerName = ownerName.toUpperCase(); // Convert to uppercase here
            if (!whereClause.isEmpty()) {
                whereClause.append(" AND ");
            }
            whereClause.append("property_owner_name like ' ").append(ownerName).append("%'"); // Name starts with the input text
        }
        if (permitType != null && !permitType.isEmpty()) {
            if (!whereClause.isEmpty()) {
                whereClause.append(" AND ");
            }
            whereClause.append("application_type = '").append(permitType).append("'");
        }

        if (!whereClause.isEmpty()) {
            builder.queryParam("$where", whereClause.toString());
        }

        String apiUrl = builder.build().toUriString(); // final URI
        System.out.println(apiUrl);
        // Retrieve permit data from the API based on the search parameters
        RestTemplate template = new RestTemplate();
        ResponseEntity<Permit[]> response = template.getForEntity(apiUrl, Permit[].class);
        permits = response.getBody();
        //assert permits != null;
        if (permits == null || permits.length == 0) {
            model.addAttribute("noPermitsMessage", "No permits found with given criteria.");
        } else {
            model.addAttribute("permits", permits);
        }

        // Return the template to display the search results
        return "Results";
    }
    /**
     * Get Endpoint that takes Permit ID from the Results.html page and provides permit details on the Additional_Info.html page
     * @param id Permit Id
     * @return Thymeleaf template Additional Info
     */
    @GetMapping("/details/{id}")
    public String getPermitDetails(@PathVariable String id, Model model) {
        Permit permit = retrievePermitDetails(id);
        model.addAttribute("permit", permit);
        return "Additional_Info";
    }

    /**
     *  Method to search through array of Permit objects
     * @param id Permit Id
     * @return Permit object
     */
    public Permit retrievePermitDetails(String id) {
        for (Permit perm : permits)
            if (perm.permit_number().equals(id)) {
                return perm;
            }
        return null;
    }
}

/**
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
        return "Results";**/





