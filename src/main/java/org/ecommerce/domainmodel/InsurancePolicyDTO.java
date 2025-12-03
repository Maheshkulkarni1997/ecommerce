/*
 * © 2024 SafeGuard
 * All rights reserved.
 *
 * Author: Mahesh
 *
 * DTO representing an insurance policy for Swagger documentation.
 */

package org.ecommerce.domainmodel;

import lombok.Data;


@Data
public class InsurancePolicyDTO {
    private Long id;
    private String policyNumber;
    private String policyHolderName;
    private Double coverageAmount;
    
}
