package org.ecommerce.entitymodel;


import java.io.Serializable;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "product")
@Data
@SuppressWarnings("serial")
public class Product implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private Double price;
    private String description;
    private String category;
    private String image;
    @Column(name = "rating_rate")
    private Double ratingRate;
    
    @Column(name = "rating_count")
    private Integer ratingCount;
    private String brand;
    private Integer stock;
   
}
