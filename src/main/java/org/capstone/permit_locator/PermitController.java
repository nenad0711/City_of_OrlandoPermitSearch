package org.capstone.permit_locator;
import org.capstone.permit_locator.Model.Permit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

/**
 * The controller class responsible for handling permit-related operations.
 */
@Controller
public class PermitController {
    /**
     * An array containing permit information.
     */
    public Permit[] permits;
    /**
     * Logger instance for logging permit search information.
     */
    private static final Logger log = LoggerFactory.getLogger(PermitLocatorApplication.class);

    /**
     * Handles requests to the root URL.
     *
     * @param model The model attribute to be populated with data.
     * @return The name of the template to render the home page.
     */
    @GetMapping("/")
    public String index(Model model) {
        return "Home";
    }

    /**
     * Handles requests to search for permits based on various parameters.
     * @param address    The address parameter for permit search.
     * @param ownerName  The owner name parameter for permit search.
     * @param permitType The permit type parameter for permit search.
     * @param model      The model attribute to be populated with data.
     * @return The name of the template to render the search results.
     */
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
        // Prepare the HTTP headers
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("X-App-Token", "9w4z2kefpppYCRVoKJCGF6ZxL");

        // Prepare the HTTP entity with headers
        HttpEntity<?> entity = new HttpEntity<>(headers);
        // Retrieve permit data from the API based on the search parameters
        RestTemplate template = new RestTemplate();
        try {
            ResponseEntity<Permit[]> response = template.exchange(apiUrl, HttpMethod.GET, entity, Permit[].class);

            if (response.getStatusCode() == HttpStatus.OK) {
                log.info("Connection established");
                permits = response.getBody();
                if (permits == null || permits.length == 0) {
                    model.addAttribute("noPermitsMessage", "No permits found with given criteria.");
                } else {
                    model.addAttribute("permits", permits);
                }

                // Return the template to display the search results
                return "Results";
            }
            else {
                // Handle unexpected HTTP status code
                log.error("Unexpected HTTP status code: " + response.getStatusCodeValue());
                model.addAttribute("errorCode", response.getStatusCodeValue());
                // You may redirect to an error page or show an appropriate message to the user
                return "Error Page";
            }
        }
        catch (HttpClientErrorException | HttpServerErrorException e) {
            // Handle client and server errors
            log.error("HTTP error: " + e.getStatusCode() + " - " + e.getStatusText());
            model.addAttribute("errorCode", e.getStatusCode().value());
            // You may redirect to an error page or show an appropriate message to the user
            return "ErrorPage";
        }
        catch (Exception e) {
            // Handle other exceptions
            log.error("An unexpected error occurred: " + e.getMessage());
            model.addAttribute("errorCode", HttpStatus.INTERNAL_SERVER_ERROR.value());
            // You may redirect to an error page or show an appropriate message to the user
            return "ErrorPage";
        }
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






