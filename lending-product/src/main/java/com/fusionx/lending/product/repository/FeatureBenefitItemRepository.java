package com.fusionx.lending.product.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fusionx.lending.product.domain.FeatureBenefitItem;


@Repository
public interface FeatureBenefitItemRepository extends JpaRepository<FeatureBenefitItem, Long> {
	
		
	List<FeatureBenefitItem> findByStatus(String status);
	
	Optional<FeatureBenefitItem> findByCode(String code);

	List<FeatureBenefitItem> findByName(String name);

	Optional<FeatureBenefitItem> findByCodeAndIdNotIn(String code, Long id);
	
	List<FeatureBenefitItem> findByFeatureBenefitItemTypeCode(String featureBenefitItemTypeCode);
	
	public Boolean existsByFeatureBenefitItemTypeId(Long featureBenefitItemTypeId);
	
	public Boolean existsByFeatureBenefitItemTypeIdAndIdNotIn(Long featureBenefitItemTypeId,Long id);

	Optional<FeatureBenefitItem> findByCodeAndFeatureBenefitItemTypeId(String code, Long featureBenefitItemTypeId);
}
