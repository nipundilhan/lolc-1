package com.fusionx.lending.origination.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fusionx.lending.origination.domain.ProductCategoryProductMapDetails;

@Repository
public interface ProductCategoryProductMapDetailsRepository extends JpaRepository<ProductCategoryProductMapDetails, Long> {

	List<ProductCategoryProductMapDetails> findByProductCategoryProductMapId(Long id);

}
