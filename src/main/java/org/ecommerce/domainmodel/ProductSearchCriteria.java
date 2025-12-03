package org.ecommerce.domainmodel;

import java.io.Serializable;

import org.ecommerce.enums.Category;

import lombok.Data;

@Data
@SuppressWarnings("serial")
public class ProductSearchCriteria implements Serializable {

	private Category category;
	private String brand;
	private Double minPrice;
	private Double maxPrice;
	private String keyword;
	private Boolean inStock;

}