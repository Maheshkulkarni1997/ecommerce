package org.ecommerce.mapper;

import java.util.List;

import org.ecommerce.domainmodel.ProductDTO;
import org.ecommerce.entitymodel.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ProductMapper {

	@Mapping(source = "ratingRate", target = "rating.rate")
	@Mapping(source = "ratingCount", target = "rating.count")
	ProductDTO toProductDTO(Product product);

	@Mapping(source = "rating.rate", target = "ratingRate")
	@Mapping(source = "rating.count", target = "ratingCount")
	Product toProduct(ProductDTO productDTO);

	@Mapping(source = "rating.rate", target = "ratingRate")
	@Mapping(source = "rating.count", target = "ratingCount")
	void updateProductFromDTO(ProductDTO dto, @MappingTarget Product entity);

	List<ProductDTO> toProductDTOList(List<Product> productList);

	List<Product> toProductList(List<ProductDTO> productDTOList);
}
