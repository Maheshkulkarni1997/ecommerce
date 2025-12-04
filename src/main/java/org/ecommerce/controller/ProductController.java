package org.ecommerce.controller;

import java.util.List;

import org.ecommerce.domainmodel.ProductDTO;
import org.ecommerce.entitymodel.ProductCore;
import org.ecommerce.enums.Category;
import org.ecommerce.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/products")
public class ProductController {

	private ProductService productService;

	public ProductController(ProductService productService) {
		this.productService = productService;
	}

	@PostMapping
	public ResponseEntity<ProductDTO> createProduct(@RequestBody ProductDTO productDTO) {
		ProductDTO saved = productService.createProduct(productDTO);
		return new ResponseEntity<>(saved, HttpStatus.CREATED);
	}

	@GetMapping
	public ResponseEntity<List<ProductDTO>> getAllProducts() {
		List<ProductDTO> products = productService.getAllProducts();
		return ResponseEntity.ok(products);
	}

	@GetMapping("/{id}")
	public ResponseEntity<ProductDTO> getProductById(@PathVariable Long id) {
		ProductDTO product = productService.getProductById(id);
		return ResponseEntity.ok(product);
	}

	@PutMapping("/{id}")
	public ResponseEntity<ProductDTO> updateProduct(@PathVariable Long id, @RequestBody ProductDTO productDTO) {
		ProductDTO updated = productService.updateProduct(id, productDTO);
		return ResponseEntity.ok(updated);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteProduct(@PathVariable Long id) {
		productService.deleteProduct(id);
		return ResponseEntity.ok("Product deleted successfully!");
	}

	@GetMapping("/search")
	public ResponseEntity<List<ProductCore>> searchProducts(@RequestParam(required = false) Category category,
			@RequestParam(required = false) String brand, @RequestParam(required = false) Double minPrice,
			@RequestParam(required = false) Double maxPrice, @RequestParam(required = false) String keyword,
			@RequestParam(required = false) Boolean inStock) {
		List<ProductCore> products = productService.searchProducts(category, brand, minPrice, maxPrice, keyword,
				inStock);
		return ResponseEntity.ok(products);
	}

}
