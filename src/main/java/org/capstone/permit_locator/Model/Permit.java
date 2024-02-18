package org.capstone.permit_locator.Model;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.awt.*;

@JsonIgnoreProperties(ignoreUnknown = true)
public record Permit(String permit_number, String application_type, String property_owner_name, String permit_address, String worktype, String application_status) {
}
