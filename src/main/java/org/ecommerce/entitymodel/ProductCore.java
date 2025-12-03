package org.ecommerce.entitymodel;

import java.io.Serializable;

import org.ecommerce.enums.Category;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@SuppressWarnings("serial")
@Table(name = "product_view")
public class ProductCore implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(name = "title")
	private String title;

	@Column(name = "price")
	private Double price;

	@Column(name = "description")
	private String description;

	@Column(name = "category")
	@Enumerated(EnumType.STRING)
	private Category category;

	@Column(name = "image")
	private String image;

	@Column(name = "rating_rate")
	private Double ratingRate;

	@Column(name = "rating_count")
	private Integer ratingCount;

	@Column(name = "brand")
	private String brand;

	@Column(name = "stock")
	private Integer stock;

}
