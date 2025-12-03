package org.ecommerce.repository;

import org.ecommerce.entitymodel.ProductCore;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductCoreRepository extends JpaRepository<ProductCore, Long>, JpaSpecificationExecutor<ProductCore> {

}
