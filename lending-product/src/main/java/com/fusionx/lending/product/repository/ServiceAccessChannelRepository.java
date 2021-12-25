package com.fusionx.lending.product.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fusionx.lending.product.domain.Brand;
import com.fusionx.lending.product.domain.SalesAccessChannel;
import com.fusionx.lending.product.domain.ServiceAccessChannel;
import com.fusionx.lending.product.enums.CommonStatus;

/**
 * ServiceAccessChannel Repository
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   07-07-2021      		      			Nipun Dilhan      Created
 *    
 ********************************************************************************************************
 */

@Repository
public interface ServiceAccessChannelRepository extends JpaRepository<ServiceAccessChannel, Long> {

	public List<ServiceAccessChannel> findAll();
	
	public List<ServiceAccessChannel> findByNameContaining(String name);
	
	public List<ServiceAccessChannel> findByStatus(CommonStatus status);
	
	public Optional<ServiceAccessChannel> findByCode(String code);
	
	public Optional<ServiceAccessChannel> findByIdAndNameAndStatus(Long id,String name,CommonStatus status);
	

	

}
