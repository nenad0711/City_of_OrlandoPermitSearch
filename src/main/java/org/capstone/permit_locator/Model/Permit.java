package org.capstone.permit_locator.Model;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.awt.*;
/**
 * A record representing a permit for various applications.
 *
 * @author [Nenad Jovanovic]
 * @version 1.0
 */


/**
 * Constructs a new Permit with the specified details.
 *
 * @param permit_number       The permit number.
 * @param application_type    The type of application.
 * @param property_owner_name The name of the property owner.
 * @param permit_address      The address associated with the permit.
 * @param worktype            The type of work.
 * @param application_status  The status of the application.
 * @param project_name        The name of the project
 * @param estimated_cost      The estimated cost of the project
 * @param processed_date      The date the application was processed
 * @param issue_permit_date   The date the permit was issued
 * @param final_date          The final date of processing
 * @param contractor          Contractor working
 * @param contractor_name     Contractor name
 * @param contractor_address  Contractor's address
 * @param contractor_phone_number Contractor's phone number
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public record Permit(String permit_number,
                     String application_type,
                     String property_owner_name,
                     String permit_address,
                     String worktype,
                     String application_status,
                     String project_name,
                     String estimated_cost,
                     String processed_date,
                     String issue_permit_date,
                     String final_date,
                     String contractor,
                     String contractor_name,

                     String contractor_address,
                     String contractor_phone_number

) {

}
