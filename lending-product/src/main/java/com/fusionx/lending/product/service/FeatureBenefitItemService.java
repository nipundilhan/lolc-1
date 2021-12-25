package com.fusionx.lending.product.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.fusionx.lending.product.domain.FeatureBenefitItem;
import com.fusionx.lending.product.resources.FeatureBenefitItemAddResource;
import com.fusionx.lending.product.resources.FeatureBenefitItemUpdateResource;

@Service
public interface FeatureBenefitItemService {

	 
	public List<FeatureBenefitItem> findAll();
	
	
	public List<FeatureBenefitItem> findByStatus(String status);
	
	
	public Optional<FeatureBenefitItem> findById(Long id);
	
	
	public  List<FeatureBenefitItem> findByName(String name);
	
	
	public Optional<FeatureBenefitItem> findByCode(String code);
	
	
	public  List<FeatureBenefitItem> findByFeatureBenefitItemTypeCode(String featureBenefitItemTypeCode);
	
	
	public FeatureBenefitItem addFeatureBenefitItem(String tenantId, FeatureBenefitItemAddResource featureBenefitItemAddResource);
	
	 
	public FeatureBenefitItem updateFeatureBenefitItem(String tenantId, FeatureBenefitItemUpdateResource featureBenefitItemUpdateResource) ;
	
	 
	public boolean exists(Long id);
	
	public Optional<FeatureBenefitItem> findByCodeAndItemTypeId(String code, Long typeId);
	 
	
	

}
