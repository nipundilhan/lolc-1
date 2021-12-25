package com.fusionx.lending.origination.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fusionx.lending.origination.domain.ProductCategoryProductMap;
import com.fusionx.lending.origination.enums.CommonStatus;


@Repository
public interface ProductCategoryProductMapRepository extends JpaRepository<ProductCategoryProductMap, Long>{

	List<ProductCategoryProductMap> findByStatus(CommonStatus status);

	Optional<ProductCategoryProductMap> findByProductCategoryId(Long id);
}
