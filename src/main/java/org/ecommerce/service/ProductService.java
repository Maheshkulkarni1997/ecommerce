package org.ecommerce.service;

import java.util.List;

import org.ecommerce.domainmodel.ProductDTO;
import org.ecommerce.entitymodel.Product;
import org.ecommerce.entitymodel.ProductCore;
import org.ecommerce.enums.Category;
import org.ecommerce.mapper.ProductMapper;
import org.ecommerce.repository.ProductCoreRepository;
import org.ecommerce.repository.ProductRepository;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import jakarta.persistence.criteria.Predicate;

@Service
public class ProductService {

	private final ProductRepository productRepository;
	private final ProductMapper productMapper; // Spring injects the mapper

	private ProductCoreRepository productCoreRepository;

	public ProductService(ProductRepository productRepository, ProductMapper productMapper,
			ProductCoreRepository productCoreRepository) {
		this.productRepository = productRepository;
		this.productMapper = productMapper;
		this.productCoreRepository = productCoreRepository;
	}

	public List<ProductDTO> getAllProducts() {
		return productMapper.toProductDTOList(productRepository.findAll());
	}

	public ProductDTO getProductById(Long id) {
		Product product = productRepository.findById(id).orElseThrow(() -> new RuntimeException("Product not found"));
		return productMapper.toProductDTO(product);
	}

	public ProductDTO createProduct(ProductDTO dto) {
		Product product = productMapper.toProduct(dto);
		Product saved = productRepository.save(product);
		return productMapper.toProductDTO(saved);
	}

	public ProductDTO updateProduct(Long id, ProductDTO dto) {
		Product existing = productRepository.findById(id).orElseThrow(() -> new RuntimeException("Product not found"));
		productMapper.updateProductFromDTO(dto, existing);
		Product updated = productRepository.save(existing);
		return productMapper.toProductDTO(updated);
	}

	public void deleteProduct(Long id) {
		if (!productRepository.existsById(id)) {
			throw new RuntimeException("Product not found");
		}
		productRepository.deleteById(id);
	}

	public List<ProductCore> searchProducts(Category category, String brand, Double minPrice, Double maxPrice,
			String keyword, Boolean inStock) {
		return productCoreRepository.findAll(buildSpecification(category, brand, minPrice, maxPrice, keyword, inStock));
	}

	// Specification builder using individual parameters
	private Specification<ProductCore> buildSpecification(Category category, String brand, Double minPrice,
			Double maxPrice, String keyword, Boolean inStock) {
		return (root, query, cb) -> {
			Predicate predicate = cb.conjunction();

			if (category != null) {
				predicate = cb.and(predicate, cb.equal(root.get("category"), category));
			}
			if (brand != null && !brand.isEmpty()) {
				predicate = cb.and(predicate, cb.equal(root.get("brand"), brand));
			}
			if (minPrice != null) {
				predicate = cb.and(predicate, cb.ge(root.get("price"), minPrice));
			}
			if (maxPrice != null) {
				predicate = cb.and(predicate, cb.le(root.get("price"), maxPrice));
			}
			if (keyword != null && !keyword.isEmpty()) {
				predicate = cb.and(predicate, cb.like(cb.lower(root.get("title")), "%" + keyword.toLowerCase() + "%"));
			}
			if (inStock != null && inStock) {
				predicate = cb.and(predicate, cb.greaterThan(root.get("stock"), 0));
			}

			return predicate;
		};
	}

}
