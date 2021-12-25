package com.fusionx.lending.product.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fusionx.lending.product.domain.FeatureBenifitTemplate;
import com.fusionx.lending.product.domain.FeeCharge;
import com.fusionx.lending.product.enums.CommonStatus;

public interface FeatureBenifitTemplateRepository  extends JpaRepository<FeatureBenifitTemplate, Long> {
	
	public List<FeatureBenifitTemplate> findByStatus(CommonStatus status);

	public List<FeatureBenifitTemplate> findByNameContaining(String name);

	public Optional<FeatureBenifitTemplate> findByCode(String code);
	
	public Optional<FeatureBenifitTemplate> findByIdAndStatus(Long id, CommonStatus status);
	

}
