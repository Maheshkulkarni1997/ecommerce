package org.ecommerce.domainmodel;

import java.io.Serializable;

import lombok.Data;

@Data
@SuppressWarnings("serial")
public class ProductDTO implements Serializable{
    private Long id;
    private String title;
    private Double price;
    private String description;
    private String category;
    private String image;
    private RatingDTO rating;

 
}
