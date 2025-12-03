package org.ecommerce.domainmodel;

import java.io.Serializable;

import org.ecommerce.enums.Category;

import lombok.Data;

@Data
@SuppressWarnings("serial")
public class ProductCore implements Serializable {

	private Long id;

	private String title;

	private Double price;

	private String description;

	private Category category;

	private String image;

	private Double ratingRate;

	private Integer ratingCount;

	private String brand;

	private Integer stock;

}
