package org.capstone.permit_locator;
import org.capstone.permit_locator.Model.Permit;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.client.RestTemplate;

@Controller
public class PermitController {

	@GetMapping("/permits")
	public String showPermits(Model model) {
		Permit [] permits = searchPermits();
		model.addAttribute("permits", permits);
		// Return the name of the HTML template to render
		return "Results";
	}

	public Permit[] searchPermits() {
		RestTemplate template = new RestTemplate();
		String url  ="https://data.cityoforlando.net/resource/ryhf-m453.json?$limit=10";

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
		return template.getForObject(url, Permit[].class);
	}
}
