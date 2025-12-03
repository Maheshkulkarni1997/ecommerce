package org.ecommerce.domainmodel;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuppressWarnings("serial")
public class RatingDTO implements Serializable{
    private Double rate;
    private Integer count;

  
}
