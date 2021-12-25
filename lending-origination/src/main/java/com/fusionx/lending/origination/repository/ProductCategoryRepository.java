package com.fusionx.lending.origination.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fusionx.lending.origination.domain.ProductCategory;
import com.fusionx.lending.origination.enums.CommonStatus;



@Repository
public interface ProductCategoryRepository extends JpaRepository<ProductCategory, Long>{
	
	List<ProductCategory> findByStatus(String status);
	
	Optional<ProductCategory> findByCode(String code);

	List<ProductCategory> findByName(String name);

	Optional<ProductCategory> findByCodeAndIdNotIn(String code, Long id);

	Optional<ProductCategory> findByIdAndCodeAndStatus(Long id, String name, String status);

}
