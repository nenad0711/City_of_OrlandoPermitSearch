package org.capstone.permit_locator;
import org.capstone.permit_locator.Model.Permit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

@Controller
public class PermitController {
	public Permit [] permits;
	private static final Logger log = LoggerFactory.getLogger(PermitLocatorApplication.class);
	@GetMapping ("/search")
	public String searchPermits(
			//@RequestParam(value = "address", required = false) String address,
			//@RequestParam(value = "ownerName", required = false) String ownerName,
			@RequestParam(value = "dropdown", defaultValue = "") String dropdown,
			Model model) {
		log.info(dropdown);
		//System.out.println(dropdown);
		// Construct the API URL based on the search parameters
		String apiUrl = "https://data.cityoforlando.net/resource/ryhf-m453.json?";
		/**if (address != null && !address.isEmpty()) {
			apiUrl += "permit_address=" + address;
		}
		if (ownerName != null && !ownerName.isEmpty()) {
			if (!apiUrl.endsWith("?")) {
				apiUrl += "&";
			}
			apiUrl += "property_owner_name=" + ownerName;
		}
		if (dropdown != null && !dropdown.isEmpty()) {
			if (!apiUrl.endsWith("?")) {
				apiUrl += "&";
			}**/
			apiUrl += "application_type=" + dropdown;


		RestTemplate template = new RestTemplate();
		ResponseEntity<Permit[]> response = template.getForEntity(apiUrl+"$limit=5", Permit[].class);
		permits = response.getBody();
		assert permits != null;
		// Retrieve permit data from the API based on the search parameters
		//List<Permit> permits = retrievePermitsFromApi(apiUrl);
		model.addAttribute("permits", permits);

		// Return the permits template to display the search results
		return "Results";
	}




/**
	@GetMapping("/permits")
	public String showPermits(Model model) {
		//Permit [] permits = searchPermits();
		model.addAttribute("permits", permits);
		// Return the name of the HTML template to render
		return "Results";
	}**/
	@GetMapping("/details/{id}")
	public String getPermitDetails(@PathVariable String id, Model model) {
		// Retrieve permit details for the given ID (from database, API, etc.)
		Permit permit = retrievePermitDetails(id);
		model.addAttribute("permit", permit);
		return "Additional_Info";
	}
	public Permit retrievePermitDetails(String id) {

		for (Permit perm: permits)
			if(perm.permit_number().equals(id)){
				return perm;
			}
		// Implementation to retrieve permit details for the given ID
		// For simplicity, you can use mock permit details here
		//return new Permit(id, "Permit " + id, id * 100);

        return null;
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
}
