package org.capstone.permit_locator;
import org.capstone.permit_locator.Model.Permit;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.client.RestTemplate;

@Controller
public class PermitController {
	public Permit [] permits = searchPermits();
	@GetMapping("/permits")
	public String showPermits(Model model) {
		//Permit [] permits = searchPermits();
		model.addAttribute("permits", permits);
		// Return the name of the HTML template to render
		return "Results";
	}
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

	public Permit[] searchPermits() {
		RestTemplate template = new RestTemplate();
		String url  ="https://data.cityoforlando.net/resource/ryhf-m453.json?$limit=10";
		ResponseEntity<Permit[]> response = template.getForEntity(url, Permit[].class);
		Permit [] permits = response.getBody();
		assert permits != null;
		return permits;
		/**Permit [] permitList = template.getForObject(url, Permit[].class);
		for (Permit permit : permitList) {
			System.out.println("Permit ID: " +   permit.permit_number());
			System.out.println("Permit Application number: " + permit.application_type());
			System.out.println("Permit Owner Name: " + permit.property_owner_name());
			System.out.println("Permit Address: " + permit.permit_address());
			System.out.println("Permit Worktype: " + permit.worktype());
			System.out.println("Permit Status: " + permit.application_status());
			System.out.println("------------------------------------------");
		}
		return permitList;**/
	}
}
