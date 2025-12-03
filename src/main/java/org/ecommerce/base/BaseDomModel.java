package org.ecommerce.base;


import java.time.LocalDateTime;


import lombok.Data;

@Data
public abstract class BaseDomModel {

 
    private LocalDateTime createdDate;

    
    private LocalDateTime updatedDate;

   
    private String createdBy;

    
    private String updatedBy;

    private Boolean isActive;
}
